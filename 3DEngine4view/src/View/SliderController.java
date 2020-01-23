package View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderController implements ChangeListener{
	
	MainViewClass view;
	
	public SliderController(MainViewClass v) {
		view = v;
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		view.call("DEGREES");
		
	}

}
