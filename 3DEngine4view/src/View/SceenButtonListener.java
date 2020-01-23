package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.MainViewClass;

public class SceenButtonListener implements ActionListener{
	
	MainViewClass view;
	String actionCommand;
	
	boolean edit;
	
	public SceenButtonListener(MainViewClass mainViewClass) {
		// TODO Auto-generated constructor stub
		view = mainViewClass;
		edit = false;
	}

	public void ScreenButtonListener(){
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	System.out.println("click");
		if(!edit) {
			edit = true;
			actionCommand = e.getActionCommand();
		//	view.setMovingButton(e.getActionCommand());
		}else{
			edit = false;
			actionCommand = "";
			//view.callToStop();
			
		}
		
	}

}
