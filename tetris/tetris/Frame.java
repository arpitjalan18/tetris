package tetris;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;


public class Frame implements ActionListener, KeyListener{
	
	static Frame tetris;
	public Color flatBlue = new Color(0x0AB7F2);
	public Color darkBlue = new Color(0x1E59CF);
	public Color orange = new Color(0xFB7C10);
	public Color yellow = new Color(0xF6BA25);
	public Color purple = new Color(0xC531A9);
	public Color green = new Color(0x66BF33);
	public Color red = new Color(0xDA2941);
	JFrame frame;
	Painter painter;
	ArrayList<box> boxes = new ArrayList<box>();
	public Timer timer = new Timer(20, this);
	public int ticks = 0;
	public ArrayList<box> setBoxes = new ArrayList<box>();
	public static void main(String args[]) {
		tetris = new Frame();
	}
	public int pieceIndex = 6;
	ArrayList<ArrayList<box>> pieces = new ArrayList<ArrayList<box>>();
	
	public Frame(){
		frame = new JFrame("");
		frame.setVisible(true);
		frame.setSize(320, 640);
		frame.setResizable(false);
		//frame.setLocationRelativeTo(null);
		frame.add(painter = new Painter());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		timer.start();
		initializePieces();
		startGame();
	}
	
	public void initializePieces() {
		
		ArrayList<box> flatBlueB = new ArrayList<box>();
		ArrayList<box> darkBlueB = new ArrayList<box>();
		ArrayList<box> orangeB = new ArrayList<box>();
		ArrayList<box> yellowB = new ArrayList<box>();
		ArrayList<box> purpleB = new ArrayList<box>();
		ArrayList<box> greenB = new ArrayList<box>();
		ArrayList<box> redB = new ArrayList<box>();
		
		flatBlueB.add(new box(flatBlue, 0 ,0));
		flatBlueB.add(new box(flatBlue, 1, 0));
		flatBlueB.add(new box(flatBlue, 2, 0));
		flatBlueB.add(new box(flatBlue, 3, 0));
		
		darkBlueB.add(new box(darkBlue, 0 ,0));
		darkBlueB.add(new box(darkBlue, 1, 0));
		darkBlueB.add(new box(darkBlue, 2, 0));
		darkBlueB.add(new box(darkBlue, 2, 1));
		
		orangeB.add(new box(orange, 0 ,0));
		orangeB.add(new box(orange, 1, 0));
		orangeB.add(new box(orange, 2, 0));
		orangeB.add(new box(orange, 0, 1));

		purpleB.add(new box(purple, 0 ,0));
		purpleB.add(new box(purple, 1, 0));
		purpleB.add(new box(purple, 2, 0));
		purpleB.add(new box(purple, 1, 1));
		
		yellowB.add(new box(yellow, 0 ,0));
		yellowB.add(new box(yellow, 1, 0));
		yellowB.add(new box(yellow, 1, 1));
		yellowB.add(new box(yellow, 0, 1));
		
		greenB.add(new box(green, 0 ,0));
		greenB.add(new box(green, 0, 1));
		greenB.add(new box(green, 1, 1));
		greenB.add(new box(green, 1, 2));
		
		redB.add(new box(red, 1 ,0));
		redB.add(new box(red, 1, 1));
		redB.add(new box(red, 0, 1));
		redB.add(new box(red, 0, 2));
		
		pieces.add(flatBlueB);
		pieces.add(darkBlueB);
		pieces.add(orangeB);
		pieces.add(purpleB);
		pieces.add(yellowB);
		pieces.add(greenB);
		pieces.add(redB);
	}

	public ArrayList<box> randomPiece(){
		
		ArrayList<box> sendPiece = pieces.get((int) (Math.random()*pieces.size()));
		pieces.remove(sendPiece);
		if(pieces.size() == 0) {
			initializePieces();
		}
		return sendPiece;
	}
	public void startGame() {
		
		ArrayList<box> piece = randomPiece();
		for (box temp: piece) {
			boxes.add(temp);
		}
	}
	public void newPiece() {
		
		for(int i = 0; i< 20; i++) {
			moveSidewaysOrDown(0,1);
		}
		while (boxes.size() > 0) {
			setBoxes.add(boxes.get(0));
			boxes.remove(boxes.get(0));
		}
		for (int i = 0; i < 20; i++) {
			int x = 0;
			for (box temp: setBoxes) {
				if (temp.y == i){
						x++;
				}
			}
			
			ArrayList<box> newSetBoxes = new ArrayList<box>();
			if (x > 9) {			
				int sizeOfSetBoxes = setBoxes.size();
				int index = 0;
				while (setBoxes.size() > sizeOfSetBoxes-10) {
					if (setBoxes.get(index).y == i) {
						setBoxes.remove(index);
					}
					else {
						//System.out.println(i + ", " + setBoxes.get(index).y);
						index++;
					}
				}
				int index2 =0;
				while(index2  < setBoxes.size()) {
					if (setBoxes.get(index2).y < i) {
						newSetBoxes.add(setBoxes.get(index2));
					}
					index2++;
					System.out.println("yeah we made it and broke it again bitch");
				}
				for(box temp: newSetBoxes) {
					setBoxes.remove(temp);
					System.out.println("im stuck here");
				}
				moveSidewaysOrDownButForSetBoxes(0,1,newSetBoxes);
			}
		}
		startGame();
	}
	
	public ArrayList<box> shadowBoxes = new ArrayList<box>();
	public void actionPerformed(ActionEvent e) {
		ticks++;
		painter.repaint();
		
		if (ticks%1 == 0) {
			while(shadowBoxes.size()> 0) {
				shadowBoxes.remove(0);
			}
			for (box temp: boxes) {
				shadowBoxes.add(temp);
			}
			for (int i = 0; i < 20; i++) {
				moveSidewaysOrDownShadowBoxes(0,1);
			}
			painter.repaint();
		}
		if(ticks % 30 == 0){
			moveSidewaysOrDown(0,1);
		}
	}
	
	public boolean isCollidingWithSet(int xModifier, int yModifier) {
		for(box temp: boxes) {
			for(box setTemp: setBoxes) {
				if (setTemp != null && setTemp.x == temp.x + xModifier && setTemp.y == temp.y + yModifier ) {
					System.out.println(temp + "\n" + setTemp);
					return true;		
				
				}
			}
			
		}
		return false;
	}
	public boolean isCollidingWithSetShadowBoxes(int xModifier, int yModifier) {
		for(box temp: shadowBoxes) {
			for(box setTemp: setBoxes) {
				if (setTemp != null && setTemp.x == temp.x + xModifier && setTemp.y == temp.y + yModifier ) {
					System.out.println(temp + "\n" + setTemp);
					return true;		
				
				}
			}
			
		}
		return false;
	}
	
	public void keyPressed(KeyEvent arg0) {
		int i = arg0.getKeyCode();
		if(i ==39) {
			//System.out.println("im registering");
			moveSidewaysOrDown(1, 0);

		}
		if(i == 37) {
			moveSidewaysOrDown(-1, 0);
		}
		if(i == 38) {
			if (boxes.get(1).color != yellow)
				rotate();
		}
		if(i == 40) {
			moveSidewaysOrDown(0, 1);
		}
		if(i == KeyEvent.VK_SPACE) {
			newPiece();
		}
		
	}

	public void moveSidewaysOrDownButForSetBoxes(int modifierX, int modifierY, ArrayList<box> newSetBoxes){
		
		ArrayList<box> tempBoxes = new ArrayList<box>();

		int index = 0;
		boolean failed = false;

		
		for (int i = 0; i < newSetBoxes.size(); i++) {
			if(newSetBoxes.get(i).x + modifierX < 0 || newSetBoxes.get(i).x + modifierX >= 10 || newSetBoxes.get(i).y + modifierY> 19 ) {
			
				failed = true;
			}
			
		}
		
		while (newSetBoxes.size() > 0) {	
			if(!failed) {
			
				tempBoxes.add(new box(newSetBoxes.get(index).getColor(), newSetBoxes.get(index).x + modifierX, newSetBoxes.get(index).y + modifierY));
				newSetBoxes.remove(index);

			}
			else {
				tempBoxes.add(new box(newSetBoxes.get(index).getColor(), newSetBoxes.get(index).x, newSetBoxes.get(index).y));
				newSetBoxes.remove(index);
			}
		}
		for (box temp: tempBoxes) {	
			setBoxes.add(temp);
		}
		
		painter.repaint();
	}
	
	public void moveSidewaysOrDown(int modifierX, int modifierY){
		
		ArrayList<box> tempBoxes = new ArrayList<box>();

		int index = 0;
		boolean failed = false;

		
		for (int i = 0; i < boxes.size(); i++) {
			if(boxes.get(i).x + modifierX < 0 || boxes.get(i).x + modifierX >= 10 || boxes.get(i).y + modifierY> 19 || isCollidingWithSet(modifierX, modifierY)) {
				//.out.println(boxes.get(i));
				failed = true;
			}
			
		}
		//System.out.println(failed);
		
		while (boxes.size() > 0) {	
			if(!failed) {
				
				tempBoxes.add(new box(boxes.get(index).getColor(), boxes.get(index).x + modifierX, boxes.get(index).y + modifierY));
				boxes.remove(index);

			}
			else {
				tempBoxes.add(new box(boxes.get(index).getColor(), boxes.get(index).x, boxes.get(index).y));
				boxes.remove(index);
			}
		}
		for (box temp: tempBoxes) {	
			boxes.add(temp);
		}
		
		painter.repaint();
	}
	
	public void moveSidewaysOrDownShadowBoxes(int modifierX, int modifierY){
		
		ArrayList<box> tempBoxes = new ArrayList<box>();

		int index = 0;
		boolean failed = false;

		
		for (int i = 0; i < shadowBoxes.size(); i++) {
			if(shadowBoxes.get(i).x + modifierX < 0 || shadowBoxes.get(i).x + modifierX >= 10 || shadowBoxes.get(i).y + modifierY> 19 || isCollidingWithSetShadowBoxes(modifierX, modifierY)) {
				//.out.println(boxes.get(i));
				failed = true;
			}
			
		}
		//System.out.println(failed);
		
		while (shadowBoxes.size() > 0) {	
			if(!failed) {
				
				tempBoxes.add(new box(shadowBoxes.get(index).getColor(), shadowBoxes.get(index).x + modifierX, shadowBoxes.get(index).y + modifierY));
				shadowBoxes.remove(index);

			}
			else {
				tempBoxes.add(new box(shadowBoxes.get(index).getColor(), shadowBoxes.get(index).x, shadowBoxes.get(index).y));
				shadowBoxes.remove(index);
			}
		}
		for (box temp: tempBoxes) {	
			shadowBoxes.add(temp);
		}
	}
	
	public double findCenterPointX() {
	
		double totalx= 0;
		for (box temp: boxes) {
			totalx += temp.x;
		}
		if (boxes.get(1).color == purple || boxes.get(1).color == darkBlue || boxes.get(1).color == orange) 
		{
			return boxes.get(1).x;
		}
		else {
			return totalx/4;
		}
	}
	public double findCenterPointY() {
		
		double totaly= 0;
		for (box temp: boxes) {
			totaly += temp.y;
		}
		if (boxes.get(1).color == purple ||  boxes.get(1).color == darkBlue || boxes.get(1).color == orange) 
		{	
			return boxes.get(1).y;
		}
		else{
			return totaly/4;
		}
	}
	
	int rotateIndexX = 0;
	int rotateIndexY = 0;
	public void rotate() {
		int index = 0;
		ArrayList<box> tempBoxes = new ArrayList<box>();
		double totalx = findCenterPointX();
		double totaly= findCenterPointY();
		int x;
		int y;
		Point middle = new Point();
		if (totalx != (int)totalx) {
			x =(int)(totalx + rotateIndexX%2);
			rotateIndexX++;
		}
		else {
			x = (int)totalx;
		}
		if(totaly != (int)totaly) {
			y = (int)(totaly + rotateIndexY%2);
			rotateIndexY++;
		}
		else {
			y = (int)totaly;
		}
		middle.move(x, y);
	
		while (boxes.size() > 0) {	

			int newY = (int)(boxes.get(index).x - middle.getX()) + (int)middle.getY();
			int newX = -(int)(boxes.get(index).y - middle.getY()) + (int)middle.getX();
			
			tempBoxes.add(new box(boxes.get(index).getColor(), newX, newY));
			boxes.remove(index);
		
		}
		for (box temp: tempBoxes) {	
			boxes.add(temp);
		}
		painter.repaint();
	}

	@Override
	
	public void keyReleased(KeyEvent arg0) {
		// TODO
	}
	
	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

