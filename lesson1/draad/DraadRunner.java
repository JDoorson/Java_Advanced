package lesson1.draad;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class DraadRunner extends JFrame {

	public DraadRunner() {
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        initialize();
		    }
		});	
	}
	
	private void initialize() {
		setBounds(500,500,100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Draadje");
		add(new StopButtonPanel());
		setVisible(true);
		
	}
	public static void main(String[] args)  {
		
		new DraadRunner();

	}

}
