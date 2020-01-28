package View;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CheckBoxListener implements ChangeListener{

	
	MainViewClass view;
	
	public CheckBoxListener(MainViewClass v) {
	view = v;	
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		switch( ( (JCheckBox) e.getSource()).getActionCommand() ) {
		case "EDGES": view.call("SHOW"); break;
		case "FACES": view.call("SHOW"); break;
		case "TEXTURES": view.call("SHOW"); break;
		case "FLAT":
		case "GOURAUD":
		case "PHONG":  view.call("SHADOW"); break;
		}
	}

	
	
}
