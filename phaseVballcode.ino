#include <SoftwareSerial.h>
#include <LSM9DS1_Registers.h>
#include <LSM9DS1_Types.h>
#include <SparkFunLSM9DS1.h>
#include <Wire.h>
#include <SPI.h>
LSM9DS1 imu;
#define LSM9DS1_M 0x1E // Would be 0x1C if SDO_M is LOW
#define LSM9DS1_AG 0x6B // Would be 0x6A if SDO_AG is LOW
//necessary includes and definitions

int findmax(int x, int y, int z)
{
  int max = x;
  if(y > max) max = y;
  if(z > max) max = z;
  return max;
}

SoftwareSerial mySerial(10, 11); // RX, TX  //set up comms
void setup() {
  // Open serial communications and wait for port to open:
  Serial.begin(9600);
  imu.settings.device.commInterface = IMU_MODE_I2C; //initialize settings for i2c comms
  imu.settings.device.mAddress = LSM9DS1_M; //addresses for 9dof
  imu.settings.device.agAddress = LSM9DS1_AG;
  imu.settings.accel.scale = 8; // Set accel scale to +/-8g.
  while (!Serial)
    ; // wait for serial port to connect. Needed for native USB port only
  mySerial.begin(9600);
  Serial.println("I am ready");
  if (!imu.begin()) { //to test if 9dof device is ready or not
    Serial.println("Failed to communicate with LSM9DS1.");
    Serial.println("Double-check wiring.");
    Serial.println("Default settings in this sketch will "\
                   "work for an out of the box LSM9DS1 "\
                   "Breakout, but may need to be modified "\
                   "if the board jumpers are.");
    while (1) //block if setup failed or device is not connected properly
      ;
  }
}

void loop() {
  byte serialread;                //variable to hold received byte from java
  if(mySerial.available())  //check if a byte is received from java
  {
   // serialread = 0b11000000;  //test byte, expert difficulty, low jerk, no spin
    serialread = mySerial.read();   //read byte 
    Serial.println("Recieved Byte");
    Serial.println(serialread);
    bool jerk = false;
    bool spin = false;
    int score = 0;
    bool jerk_required;
    bool spin_required;
    byte diff_byte;
    int difficulty = 100;
    /*the byte has bits 7 6 5 4 3 2 1 0
      bit 7 and 6 - difficulty settings - 00 easy mode 5 sec/shot, 01 normal mode 4 sec/shot, 10 hard mode 3 sec/shot, 11 expert mode 2.5 sec/shot  
      bit 5 - [NOT USED FOR BALL]
      bit 4 - [NOT USED FOR BALL]
      bit 3 - high/low jerk - if 1, required jerk/force from player is high, if 0 then low. required for calculation of score
      bit 2 - spin - if 1, gyro reading is considered while calcuating score
      bit 1 - [NOT USED FOR BALL]
      bit 0 - [NOT USED FOR BALL]
    */
    //mask bits (perform bitwise AND) to get bit value
    diff_byte = serialread & 0b11000000;  //read bit 7,6 to find out difficulty
    if(diff_byte == 0b00000000) difficulty = difficulty * 5;  //samples 5 * 100 = 500 times with 10ms delay between samples -> 5 seconds total sampling
    else if(diff_byte == 0b01000000) difficulty = difficulty * 4; //4 seconds 
    else if(diff_byte == 0b10000000) difficulty = difficulty * 3; //3 seconds
    else difficulty = difficulty * 2.5;                           //2.5 seconds
    
    if (0b00001000 == (serialread & 0b00001000))  //check bit 3 for value of jerk/force required from player
    {
      jerk_required = true; //High Jerk
    }
  
    if (0b00000100 == (serialread & 0b00000100))  // Read Bit 2 and to determine spin wanted
    { //NOT USED IN CURRENT REVISION OF CODE
      spin_required = true; //Use GYRO to see if player spins
    }
    Serial.print("Spin =");
    Serial.println(spin_required);
    Serial.print("Jerk =");
    Serial.println(jerk_required);
    imu.readAccel();  //
    imu.readGyro();   //
    //set baseline values for max and min acceleration readings
    float max_accel_x = abs(imu.calcAccel(imu.ax));  //
    float max_accel_y = abs(imu.calcAccel(imu.ay));
    float max_accel_z = abs(imu.calcAccel(imu.az));
//    float max_gyro_x = abs(imu.gx);
//    float max_gyro_y = abs(imu.gy);
//    float max_gyro_z = abs(imu.gz);
    float temp_x;
    float temp_y;
    float temp_z;
    Serial.println("START");
    //sample accelerometer for 4 seconds
    for (int i = 0; i < difficulty; i++) {  //difficulty is the number of can be either 500 or 400 or 300 or 250
      imu.readAccel();
      imu.readGyro();
      temp_x = abs(imu.calcAccel(imu.ax));
      temp_y = abs(imu.calcAccel(imu.ay));
      temp_z = abs(imu.calcAccel(imu.az));
      //find max and min acc for all axes
      if (max_accel_x < temp_x) {
        max_accel_x = temp_x;
      }
      if (max_accel_y < temp_y) {
        max_accel_y = temp_y;
      }
      if (max_accel_z < temp_z) {
        max_accel_z = temp_z;
      }
//      if (max_gyro_x < abs(imu.gx)) {
//        max_gyro_x = abs(imu.gx);
//      }
//      if (max_gyro_y < abs(imu.gy)) {
//      max_gyro_y = abs(imu.gy);
//      }
//      if (max_gyro_z < abs(imu.gz)) {
//        max_gyro_z = abs(imu.gz);
//      }
//      Serial.print("t_x"); Serial.println(temp_x);
//      Serial.print("t_y"); Serial.println(temp_y);
//      Serial.print("t_z"); Serial.println(temp_z);
      delay(10);
    }
//    int x_accel = (imu.calcAccel(max_accel_x));
//    int y_accel = (imu.calcAccel(max_accel_y));
//    int z_accel = (imu.calcAccel(max_accel_z));

    //for testing
    Serial.println("----------------------------------------------");
    Serial.print("Max Accel Values are: X axis= ");
    Serial.print(max_accel_x);
    Serial.print(" Y axis= ");
    Serial.print(max_accel_y);
    Serial.print(" Z axis= ");
    Serial.println(max_accel_z);
//    Serial.print("Max Gyro Values are: X axis= ");
//    Serial.print(x_spin);
//    Serial.print(" Y axis= ");
//    Serial.print(y_spin);
//    Serial.print(" Z axis= ");
//    Serial.println(z_spin);
    
    //0 to 6.0g is low jerk, anything above is high jerk
    int max_accel = findmax(max_accel_x, max_accel_y, max_accel_z); //find max value out of all accel values
    jerk = (max_accel > 6.0); //jerk is the force generated by the player, true if the highest acc is greater than 6.0g
    //jerk is compared to the jerk required 
    Serial.println("max accel = ");
    Serial.println(max_accel);
    if(jerk) Serial.println("high jerk");
    else Serial.println("low jerk");
//    jerk = (imu.calcAccel(max_accel_x) > 7.0 || imu.calcAccel(max_accel_z) > 7.0 || imu.calcAccel(max_accel_y) > 7.0); // True if high jerk
    if (jerk_required  == jerk )  //if jerk by player is not the same as the jerk required, score remains 0
    {
      if (jerk_required == true)  //scoring for highjerk throws
      {
        score = (1.0 - ((8.01 - max_accel)/2.0))*63; //the range of high jerk throws is 6 - 8g, higher the accel value in that range, higher the score is out of 63
      } //8.01 because if the player gets exactly 6.0g as accel then score will be 0
      else  //scoring for low jerk
      {
        score = ((6.0 - max_accel)/6.0)*63;  //the range of low jerk is 0 - 6g, the lower the accel value in that range, the higher the score is out of 63
      }
    }
    Serial.println("score = ");
    Serial.println(score);
  
    byte SendByte =  0b00000000;  //byte to be sent back to java with score info
    byte ball_id = 0b01000000;    //2 bit id of ball
    int int_score = score;  //converting float score to int score out of 63, as our score is a 6 bit value and 63 is thr max value represented by a 6 bit number
    byte byte_score = (byte)int_score;  //convert score out of 63 into a byte  
    SendByte  = SendByte | byte_score;  //mask byte to be sent with score (bitwise OR)
    SendByte = SendByte | ball_id;      //mask byte to be sent with ball_id (bitwise OR)
    mySerial.write(SendByte); //send byte to java
    Serial.println("Byte Sent");
    Serial.println(SendByte);
  }
  else
  {
    //do something else
  }
} 
