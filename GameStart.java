import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameStart extends DifficultyMenu {

	private JFrame frame;
	private JTextField playerName;
	int d;
	Leaderboards lb;
	
	

	/**
	 * Launch the application.
	 */
	public void openWindow(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameStart window = new GameStart();
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
	public GameStart() {
		lb = new Leaderboards();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 841, 537);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterYourName = new JLabel("ENTER YOUR NAME AND CLICK START TO ROLL !!!");
		lblEnterYourName.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		lblEnterYourName.setBounds(140, 24, 578, 118);
		frame.getContentPane().add(lblEnterYourName);
		
		playerName = new JTextField();
		playerName.setBounds(274, 155, 270, 47);
		frame.getContentPane().add(playerName);
		playerName.setColumns(10);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
			     p.setName(playerName.getText() + " ");
				}
				catch(Exception e2) {
					JOptionPane.showMessageDialog(null, "invalid input");
				}
				//After clicking start, run the game thread 
				CommunicationThread CT = new CommunicationThread(p, c);
				//After the game is over, display the player's score
				lb.updateRecords(p);
				JOptionPane.showMessageDialog(null, "Score: " + p.getFinalScore());
				}
		});
		btnStart.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 20));
		btnStart.setBounds(331, 237, 172, 61);
		frame.getContentPane().add(btnStart);
		
		JButton button = new JButton("DISPLAY LEADERBOARDS");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Records = Leaderboards.getRecords();
				JOptionPane.showMessageDialog(null, Records);
			}
		});
		button.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button.setBounds(281, 323, 284, 61);
		frame.getContentPane().add(button);
	}

}
