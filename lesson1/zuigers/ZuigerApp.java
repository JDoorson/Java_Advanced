package lesson1.zuigers;

import java.awt.GridLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ZuigerApp extends JFrame {

	public ZuigerApp() {

		setBounds(100, 100, 600, 600);
		setTitle("Zuigers in actie...");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLayout(new GridLayout(1,4));
		
		ZuigerPanel paneel1 = new ZuigerPanel(new Zuiger());
		ZuigerPanel paneel2 = new ZuigerPanel(new Zuiger());
		ZuigerPanel paneel3 = new ZuigerPanel(new Zuiger());
		ZuigerPanel paneel4 = new ZuigerPanel(new Zuiger());

		add(paneel1);
		add(paneel2);
		add(paneel3);
		add(paneel4);


		
		setVisible(true);
	}

	public static void main(String[] args) {
		new ZuigerApp();
	}

}
