package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener  implements ActionListener{

	MainViewClass view;
	
	boolean edit;
	
	public MenuListener(MainViewClass mainViewClass) {
		// TODO Auto-generated constructor stub
		view = mainViewClass;
		edit = false;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		view.call(e.getActionCommand());
		
	}

}
