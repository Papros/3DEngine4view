package View;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;


import Controller.ModelInput;
import Model.Vector2;


public class MainViewClass {
	
	
	private static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	
	private static final int SCREEN_WIDTH = (int) (WIDTH / 2.1);
	private static final int SCREEN_HEIGHT= (int) (HEIGHT / 2.5);
	
	private JFrame myFrame;
	private ModelInput MODEL;
	
	private MyScreen screen1;
	private MyScreen screen2,screen3,screen4;
	
	private Image camImg,centerImg,lightImg;
	JMenuBar menuBar;
	JMenu menuFile;
	JMenuItem mOpen,mSave,mClose,mReset;
	JPanel screenHolder;
	JSlider degres;
	
	JCheckBox checkShow1;
	JCheckBox checkShow2,checkShow3;
	
	CameraMovementController cameraController;
	Vector2[] camCord,centerCord;
	int camSize = 20;
	JButton cameraB[];
	JButton centerB[];
	int activeB,activeType; //index of active button, type of button 0 - camera, 1 - center
	SceenButtonListener bl;
	
	public MainViewClass(ModelInput m){
		
		this.MODEL = m;
		myFrame = new JFrame("Model Viewer");
		cameraController = new CameraMovementController(this);
		wczytaj();
		bl = new SceenButtonListener(this);
		cameraB = new JButton[3];
		centerB = new JButton[3];
		
		myFrame.addKeyListener(cameraController);
		myFrame.setLayout(new BorderLayout());
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setResizable(true);
		myFrame.setVisible(true);
		myFrame.setPreferredSize(new Dimension((int)(WIDTH),(int)(HEIGHT)));
		
		mOpen = new JMenuItem("Open",'O');
		mSave = new JMenuItem("Save",'S');
		mClose = new JMenuItem("Close");
		mReset = new JMenuItem("Refresh",'R');
		
		mOpen.setActionCommand("OPEN");
		mSave.setActionCommand("SAVE");
		mClose.setActionCommand("CLOSE");
		mReset.setActionCommand("RESET");;
		
		MenuListener menuL = new MenuListener(this);
		mOpen.addActionListener(menuL);
		mSave.addActionListener(menuL);
		mClose.addActionListener(menuL);
		mReset.addActionListener(menuL);
		
		mOpen.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		mSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		menuFile = new JMenu("PLIK");
		menuFile.add(mOpen);
		menuFile.add(mSave);
		menuFile.add(mReset);
		menuFile.add(mClose);
		menuBar = new JMenuBar();
		menuBar.add(menuFile);
		

		screen1 = new MyScreen();
		screen1.setPreferredSize(new Dimension((int)(SCREEN_WIDTH),(int)(SCREEN_HEIGHT)));
		
		screen2 = new MyScreen();
		screen2.setPreferredSize(new Dimension((int)(SCREEN_WIDTH),(int)(SCREEN_HEIGHT)));
		
		screen3 = new MyScreen();
		screen3.setPreferredSize(new Dimension((int)(SCREEN_WIDTH),(int)(SCREEN_HEIGHT)));
		
		screen4 = new MyScreen();
		screen4.setPreferredSize(new Dimension((int)(SCREEN_WIDTH),(int)(SCREEN_HEIGHT)));
		
		JPanel lowerBar = new JPanel();
		lowerBar.setPreferredSize(new Dimension((int)(WIDTH),(int)(HEIGHT/5.0)));
		
		degres = new JSlider(JSlider.HORIZONTAL,10,90,80);
		
		degres.setPreferredSize(new Dimension(  (int)(WIDTH/3.0),(int)(HEIGHT/10.0) ));
		degres.setMajorTickSpacing(10);
		degres.setMinorTickSpacing(1);
		degres.setPaintTicks(true);
		degres.setPaintLabels(true);
		degres.addChangeListener(new SliderController(this));
		degres.setBorder(BorderFactory.createTitledBorder("Fiel of view degress"));
		lowerBar.add(degres);
		
		CheckBoxListener checkL = new CheckBoxListener(this);
		
		checkShow1 = new JCheckBox("Edges");
		checkShow1.setSelected(true);
		checkShow1.setActionCommand("EDGES");
		checkShow1.addChangeListener(checkL);
		checkShow2 = new JCheckBox("Faces");
		checkShow2.setSelected(true);
		checkShow2.setActionCommand("FACES");
		checkShow2.addChangeListener(checkL);
		checkShow3 = new JCheckBox("Textures");
		checkShow3.setActionCommand("TEXTURES");
		checkShow3.setSelected(true);
		checkShow3.addChangeListener(checkL);
		
		JPanel checkContainer = new JPanel();
		checkContainer.add(checkShow1);
		checkContainer.add(checkShow2);
		checkContainer.add(checkShow3);
		
		checkContainer.setBorder(BorderFactory.createTitledBorder("DISPLAY:"));
		
		lowerBar.add(checkContainer);
		screenHolder = new JPanel();
		screenHolder.setPreferredSize(new Dimension(  (int)(WIDTH),(int)(HEIGHT) ));
		screenHolder.setBackground(Color.GRAY);
		screenHolder.add(screen1);
		screenHolder.add(screen2);
		screenHolder.add(screen3);
		screenHolder.add(screen4);
		screenHolder.add(lowerBar);
		myFrame.setJMenuBar(menuBar);
		myFrame.add(screenHolder);
		
		myFrame.pack();
		
	}
	
	
	private void wczytaj(){

		try {	
			camImg =  ImageIO.read(new File("image/camera.png")).getScaledInstance(camSize, camSize,1);
			centerImg =  ImageIO.read(new File("image/center.png")).getScaledInstance(camSize, camSize,1);
			lightImg =  ImageIO.read(new File("image/lightbulb.png")).getScaledInstance(camSize, camSize,1);
		
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
		}
		
	}
	
	

	public void msg(String string) {
		JOptionPane.showMessageDialog(null,string);
		
	}
	

	public void initiate() {
		MODEL.setDefaultSize(SCREEN_WIDTH,SCREEN_HEIGHT);
	}

	

	public void call(String a) {
		switch(a) {
		case "RESET":
			MODEL.refresh();
			break;
			
		case"OPEN":
			PickFile();
			
			break;
		case"SAVE":
			try {
				 String name = JOptionPane.showInputDialog("Proszê podaæ nazwê pliku: ");
				MODEL.saveFile(name);
				msg("Plik zosta³ zapisany poprawnie :) ");
			} catch (Exception e) {
			//catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				msg("B³¹d zapisu pliku, spróbuj ponownie :( ");
			}
			
			break;
		case"CLOSE":
			myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
			break;
			
		case"DEGREES":
			MODEL.setDegrees(degres.getValue());
			break;
			
		case"SHOW":
			MODEL.setShow(checkShow1.isSelected(),checkShow2.isSelected(),checkShow3.isSelected());
			break;
		}
		
	}
	
	public boolean PickFile() {
		
		JFileChooser fc = new JFileChooser();
		
		if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			try {
				
			if( MODEL.openFile(file) )
				return true;
			else
				return false;
			
			}catch(Exception e) {
				
				msg("Z³y format pliku lub plik uszkodzony");
				return false;
			}
		}
		
		
		return false;
	
	}

	
	
	
	public void display(BufferedImage img) {
		// TODO Auto-generated method stub
		screen1.setImage(img);
		screen2.setImage(img);
		screen3.setImage(img);
		screen4.setImage(img);
		
		removeButton();
		setButton();
		
		refresh();
	}


	public void refresh() {
		// TODO Auto-generated method stub
		screen1.repaint();
		screen2.repaint();
		screen3.repaint();
		screen4.repaint();
		myFrame.requestFocus();
	}


	public void moveCamera(int i, int j, int k) {
		MODEL.moveCamera(i,j,k);
	}

	public void moveCenter(int i, int j, int k) {
		MODEL.moveCenter(i,j,k);
	}

	public void printCam() {
		// TODO Auto-generated method stub
		MODEL.printCam();
	}


	public void printCenter() {
		// TODO Auto-generated method stub
		MODEL.printCenter();
	}
	
	public void rotate(int dir) {
		MODEL.rotate(dir);
	}


	public void rotateUp(int i) {
		// TODO Auto-generated method stub
		MODEL.rotateUp(i);
	}


	public void displayM(BufferedImage[] imgs) {
		// TODO Auto-generated method stub
		screen1.setImage(imgs[0]);
		screen2.setImage(imgs[1]);
		screen3.setImage(imgs[2]);
		screen4.setImage(imgs[3]);
		
		removeButton();
		setButton();
		refresh();
	}
	
	public void setButton() {
		
		for(int i=0;i<3;i++) {
			cameraB[i] = new JButton();
			centerB[i] = new JButton();
			cameraB[i].addActionListener(bl);
			centerB[i].addActionListener(bl);
			cameraB[i].setActionCommand("camera"+i);
			cameraB[i].addKeyListener(cameraController);
			centerB[i].setActionCommand("center"+i);
			centerB[i].addKeyListener(cameraController);
			cameraB[i].setPreferredSize(new Dimension(camSize,camSize));
			centerB[i].setPreferredSize(new Dimension(camSize,camSize));
			cameraB[i].setIcon(new ImageIcon(camImg));
			centerB[i].setIcon(new ImageIcon(lightImg));
			
			cameraB[i].setBounds((int)camCord[i].getX(),(int)camCord[i].getY(), camSize,camSize);
			centerB[i].setBounds((int)centerCord[i].getX(),(int)centerCord[i].getY(), camSize, camSize);
			
		}
		
		screen1.add(cameraB[0]);
		screen1.add(centerB[0]);
		screen2.add(cameraB[1]);
		screen2.add(centerB[1]);
		screen3.add(cameraB[2]);
		screen3.add(centerB[2]);
		
	}
	
	public void removeButton() {
		
		try {
		screen1.remove(cameraB[0]);
		screen1.remove(centerB[0]);
		screen2.remove(cameraB[1]);
		screen2.remove(centerB[1]);
		screen3.remove(cameraB[2]);
		screen3.remove(centerB[2]);
		}catch(Exception e) {
			
		}
		
		for(int i=0;i<3;i++) {
			cameraB[i] = null;
			centerB[i] = null;
		}
		
	}


	public void setCam(Vector2[] camCord, Vector2[] centerCord) {
		// TODO Auto-generated method stub\
		this.camCord = camCord;
		this.centerCord = centerCord;
		
	}

}
