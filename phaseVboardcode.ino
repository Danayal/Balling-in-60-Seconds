#include <SoftwareSerial.h>
SoftwareSerial mySerial(10, 11); // RX, TX  //set up comms
void setup() {
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  while (!Serial)
    ; // wait for serial port to connect. Needed for native USB port only
  mySerial.begin(9600);
  pinMode(5, OUTPUT); //led for pressure sensor 0
  pinMode(4, OUTPUT); //led for pressure sensor 1
  pinMode(3, OUTPUT); //led for pressure sensor 2
  pinMode(2, OUTPUT); //led for pressure sensor 3
  pinMode(6, OUTPUT); //led for high jerk
  pinMode(7, OUTPUT); //led for low jerk
  pinMode(8, OUTPUT); //led for spin
  pinMode(12, OUTPUT); //led for streak
  //test all leds, make it look nice too
  digitalWrite(2, HIGH);
  delay(150);
  digitalWrite(2, LOW);
  
  digitalWrite(3, HIGH);
  delay(150);
  digitalWrite(3, LOW);
  
  digitalWrite(4, HIGH);
  delay(150);
  digitalWrite(4, LOW);
  
  digitalWrite(5, HIGH);
  delay(150);
  digitalWrite(5, LOW);
  
  digitalWrite(6, HIGH);
  delay(150);
  digitalWrite(6, LOW);
  
  digitalWrite(7, HIGH);
  delay(150);
  digitalWrite(7, LOW);
  
  digitalWrite(8, HIGH);
  delay(150);
  digitalWrite(8, LOW);
  
  digitalWrite(12, HIGH);
  delay(150);
  digitalWrite(12, LOW);
  
  Serial.println("I am ready");
}

void setLEDlow()
{
  digitalWrite(2, LOW);
  digitalWrite(3, LOW);
  digitalWrite(4, LOW);
  digitalWrite(5, LOW);
  digitalWrite(6, LOW);
  digitalWrite(7, LOW);
  digitalWrite(8, LOW);
  digitalWrite(12, LOW);  
}

void loop() {
  //variable to hold received byte from java

  if(mySerial.available())
  {
    byte serialread = mySerial.read();
    Serial.println(serialread);
    byte target_led[4] = {0b00000000, 0b00000001, 0b00000010, 0b00000011};  //for led
    bool target_required[4] = {false, false, false, false};
    bool jerk_required = false; //for led
    bool on_streak = false;
    bool spin_required = false; //for led
    bool correct_target_hit = false, p_hit[4] = { false, false, false, false };
    int difficulty = 100;
    /*the byte has bits 7 6 5 4 3 2 1 0
      bit 7, 6 - difficulty settings - 00 easy mode 5 sec/shot, 01 normal mode 4 sec/shot, 10 hard mode 3 sec/shot, 11 expert mode 2.5 sec/shot
      bit 5 - 
      bit 4 - multiplier led, if 1, then the player is on a streak and gets bonus points for each successive target hit correctly
      bit 3 - high/low jerk - if 1, required jerk/force from player is high, if 0 then low. required for calculation of score
      bit 2 - spin - if 1, gyro reading is considered while calcuating score
      bit 1, 0 - target to be hit, 00 -> target 0, 01 -> target 1, 10  -> target 2, 11 -> target 3  
    */
    //mask bits (perform bitwise AND) to get bit value
    byte diff_byte = serialread & 0b11000000;  //read bit 7,6 to find out difficulty
    if(diff_byte == 0b00000000) difficulty = difficulty * 5;  //samples 5 * 100 = 500 times with 10ms delay between samples -> 5 seconds total sampling
    else if(diff_byte == 0b01000000) difficulty = difficulty * 4; //4 seconds 
    else if(diff_byte == 0b10000000) difficulty = difficulty * 3; //3 seconds
    else difficulty = difficulty * 2.5;                           //2.5 seconds
    
    for(int i = 0; i < 4; i++)  //check bits  1 and 0 for target number
    {
      if(target_led[i] == (serialread & 0b00000011))  //find out which target the last 2 bits represent
      {
        target_required[i] = true;
        if(i == 0) digitalWrite(2, HIGH);             //turn on led for target 0
        if(i == 1) digitalWrite(3, HIGH);             //turn on led for target 1
        if(i == 2) digitalWrite(4, HIGH);             //turn on led for target 2
        if(i == 3) digitalWrite(5, HIGH);             //turn on led for target 3
      }
    }

    if(0b00010000 == (serialread & 0b00010000))         //find out if bit 4 is 1 for streak led to be on
    {
      on_streak = true;
      Serial.println("on streak");
    }
    if(on_streak) digitalWrite(12, HIGH);
    else digitalWrite(12, LOW);

  
    if (0b00001000 == (serialread & 0b00001000))  //check bit 3 for value of jerk/force required from player
    {
      jerk_required = true; //High Jerk
    }
    if(jerk_required) 
    { 
      digitalWrite(6, HIGH); 
      digitalWrite(7, LOW); 
    }
    else 
    { 
      digitalWrite(6, LOW); 
      digitalWrite(7, HIGH); 
    }
  
    if (0b00000100 == (serialread & 0b00000100))  // Read Bit 2 and to determine spin wanted
    { //NOT USED IN CURRENT REVISION OF CODE
      spin_required = true; //Use GYRO to see if player spins
    }
    if(spin_required) digitalWrite(8, HIGH);
    else digitalWrite(8, LOW);
    
    Serial.print("Spin =");
    Serial.println(spin_required);
    Serial.print("Jerk =");
    Serial.println(jerk_required);

    //get baseline values
    //p0m is min value, this changes during sampling
    //p0init is the initial value, if there is a big change from this value, then we can say that the pressure sensor was activated
    int p0m = analogRead(A0);  
    int p1m = analogRead(A1);
    int p2m = analogRead(A2);
    int p3m = analogRead(A3);
  
    int p0init = p0m;  
    int p1init = p1m;
    int p2init = p2m;
    int p3init = p3m;
    int p0r, p1r, p2r, p3r;
    //sample pressure sensors for 4 seconds
    for (int i = 0; i < difficulty; i++) {  //difficulty can be 500, 400, 300, 250 samples 
      //read all pressure sensors
      p0r = analogRead(A0);
      p1r = analogRead(A1);
      p2r = analogRead(A2);
      p3r = analogRead(A3);
    
      //compare current pressure sensor reading with min, if current reading is lesser then update min
      if(p0m > p0r) p0m = p0r;
      if(p1m > p0r) p1m = p1r;
      if(p2m > p2r) p2m = p2r;
      if(p3m > p3r) p3m = p3r; 
      delay(10);
    }

    //if min pressure sensor reading is much lesser than initial value, pressure sensor was activated 
    if(p0m < (p0init - 20)) { p_hit[0] = true; Serial.println("HIT"); }
    if(p1m < (p1init - 20)) p_hit[1] = true;
    if(p2m < (p2init - 20)) p_hit[2] = true;
    if(p3m < (p3init - 20)) p_hit[3] = true;

    for(int i = 0; i < 4; i++)
    {
      if(target_required[i] == true && p_hit[i] == true)    //if the target to be hit is the same as the target hit by the player
      {
        correct_target_hit = true;
        Serial.println("Success");
      }
    }

  if(correct_target_hit)
    { //if correct target was hit, flash all target leds for 1 second
      for(int i = 0; i < 5; i++)
      {
        digitalWrite(2, HIGH);
        digitalWrite(3, HIGH);
        digitalWrite(4, HIGH);
        digitalWrite(5, HIGH);
        delay(200);
        digitalWrite(2, LOW);
        digitalWrite(3, LOW);
        digitalWrite(4, LOW);
        digitalWrite(5, LOW);
        delay(200);
      }
    }
  
    byte SendByte =  0b00000000;  //byte to be sent back to java with score info
    byte board_id = 0b11000000;    //2 bit id of board, 11
    SendByte = SendByte | board_id;
    if(correct_target_hit) SendByte = (SendByte | 0b00100000);  //if correct target was hit, bit 5 is 1, else 0
    setLEDlow();
    mySerial.write(SendByte); //send byte to java
    Serial.println("Byte Sent");
    Serial.println(SendByte);
    setLEDlow();
  }
  else {
    //do something else
    setLEDlow();
    
  }
}
