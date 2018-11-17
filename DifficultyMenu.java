import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


//User chooses the difficulty on the gui interface and 
//the difficulty is set accordingly

public class DifficultyMenu {

	private JFrame frame;
	protected Player p; 
	protected Command c;
	protected static int difficulty;
	
	public static int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int d) {
		this.difficulty = d;
	}
	
	/**
	 * Launch the application.
	 */
	public void openWindow(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DifficultyMenu window = new DifficultyMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DifficultyMenu() {
		p =  new Player();
		c =  new Command(p);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 843, 539);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 252, 432, 1);
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panel);
		
		JLabel label = new JLabel("SELECT DIFFICULTY");
		label.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
		label.setBounds(289, 26, 254, 99);
		panel.add(label);
		
		JButton button = new JButton("EASY");
		button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button.setBounds(299, 116, 190, 64);
		panel.add(button);
		
		JButton button_1 = new JButton("EXPERT");
		button_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_1.setBounds(299, 387, 190, 64);
		panel.add(button_1);
		
		JButton button_2 = new JButton("HARD");
		button_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_2.setBounds(299, 295, 190, 64);
		panel.add(button_2);
		
		JButton button_3 = new JButton("NORMAL");
		button_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_3.setBounds(299, 205, 190, 64);
		panel.add(button_3);
		
		JButton button_4 = new JButton("Next ->");
		button_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_4.setBounds(684, 431, 126, 46);
		panel.add(button_4);
		
		JLabel label_1 = new JLabel("SELECT DIFFICULTY");
		label_1.setFont(new Font("Comic Sans MS", Font.ITALIC, 20));
		label_1.setBounds(280, 13, 254, 99);
		frame.getContentPane().add(label_1);
		
		JButton button_5 = new JButton("EASY");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hit next to continue");
				difficulty = 0;
				
			}
		});
		button_5.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_5.setBounds(290, 101, 190, 64);
		frame.getContentPane().add(button_5);
		
		JButton button_6 = new JButton("NORMAL");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hit next to continue");
				difficulty = 1;
			}
		});
		button_6.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_6.setBounds(290, 178, 190, 64);
		frame.getContentPane().add(button_6);
		
		JButton button_7 = new JButton("HARD");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hit next to continue");
				difficulty = 2;
			}
		});
		button_7.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_7.setBounds(290, 252, 190, 64);
		frame.getContentPane().add(button_7);
		
		JButton button_8 = new JButton("EXPERT");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hit next to continue");
				difficulty = 3;
			}
		});
		button_8.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_8.setBounds(290, 329, 190, 64);
		frame.getContentPane().add(button_8);
		
		JButton button_9 = new JButton("Next ->");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				GameStart g = new GameStart();
				g.openWindow();
			}
		});
		button_9.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button_9.setBounds(687, 433, 126, 46);
		frame.getContentPane().add(button_9);
	}
	
	

}
