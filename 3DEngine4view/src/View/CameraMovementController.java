package View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CameraMovementController implements KeyListener{

	MainViewClass view;
	
	public CameraMovementController(MainViewClass view) {
		this.view = view;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W: view.moveCamera(0,0,1);break;
		case KeyEvent.VK_A: view.moveCamera(1,0,0);  break;
		case KeyEvent.VK_S: view.moveCamera(0,0,-1);  break;
		case KeyEvent.VK_D: view.moveCamera(-1,0,0); break;
		case KeyEvent.VK_E: view.moveCamera(0,-1,0);  break;
		case KeyEvent.VK_Q: view.moveCamera(0,1,0); break;
		
		case KeyEvent.VK_P: view.printCam(); break;
		case KeyEvent.VK_O: view.printCenter(); break;
		
		case KeyEvent.VK_LEFT: view.rotate(-1); break;
		case KeyEvent.VK_RIGHT: view.rotate(1); break;
		case KeyEvent.VK_UP: view.rotateUp(1);  break;
		case KeyEvent.VK_DOWN: view.rotateUp(-1); break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}
