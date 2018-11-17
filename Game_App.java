import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Game_App {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_App window = new Game_App();
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
	public Game_App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 839, 536);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBallingIn = new JLabel("BALLING IN 60 SECONDS!");
		lblBallingIn.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallingIn.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 20));
		lblBallingIn.setBounds(170, 13, 453, 83);
		frame.getContentPane().add(lblBallingIn);
		
		JButton btnSinglePlayer = new JButton("SINGLE PLAYER");
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Single player mode picked, hit ok to continue");
				//After clicking ok, difficulty menu will show up where the player can pick the difficulty
				frame.dispose();
				DifficultyMenu D = new DifficultyMenu();
				D.openWindow();	
			}
		});
		btnSinglePlayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		btnSinglePlayer.setBounds(303, 161, 190, 64);
		frame.getContentPane().add(btnSinglePlayer);
		
		JButton btnNewButton = new JButton("MULTIPLAYER");
		btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		btnNewButton.setBounds(303, 274, 190, 66);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnGameRules = new JButton("GAME RULES");
		btnGameRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rules =
						  "1. Compete against others with a friend or alone\n"
						+ "2. Throw HARD!!! when POWER UP!!! LED is ON\n"
						+ "3. Throw SLOW!!! when NORMAL LED is ON\n"
						+ "4. HIT the TARGET that is LIT UP!!!\n"
						+ "5. SPIN before throwing the ball if SPIN LED is ON\n"
						+ "6. KEEP the STREAK LED on by hitting targets continously!\n"
						+ "7. You have 60 seconds to hit the targets!\n";
				JOptionPane.showMessageDialog(null, rules);
			}
		});
		btnGameRules.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		btnGameRules.setBounds(29, 408, 190, 66);
		frame.getContentPane().add(btnGameRules);
	}

}
