package Model;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Controller.ModelInput;
import Controller.ViewInput;
import View.MainViewClass;

public class Engine {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		 java.awt.EventQueue.invokeLater(new Runnable() {
			 @Override
		      public void run() {
		    	  
		    	  try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                    ex.printStackTrace();
	                }
		    	  
					ViewInput view = new ViewInput();
					ModelInput model = new ModelInput();
					
					MainViewClass frame = new MainViewClass(model);
					ModelMainClass engine = new ModelMainClass(view);
					
					view.setView(frame);
					model.setModel(engine);
					
					frame.initiate();
					engine.repaint();
			 }
		 }); 
		
	}

}
