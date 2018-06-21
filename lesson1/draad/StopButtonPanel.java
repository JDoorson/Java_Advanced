package lesson1.draad;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StopButtonPanel extends JPanel implements ActionListener{

	PrintDraadje d,e;
	public StopButtonPanel() {
		
		setLayout(new GridLayout(1, 1));
		JButton button = new JButton("stop");
		button.setFocusable(false);
		button.addActionListener(this);
		add(button);
		
		d = new PrintDraadje("+");
		e = new PrintDraadje("-");
				
		
	}
	@Override
	public void actionPerformed(ActionEvent event) {

		d.pleaseStop();
		e.pleaseStop();
	
		
	}

}
