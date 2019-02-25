package tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Painter extends JPanel{
	
	public Color Back = new Color(0x252525);
	public Color grey1 = new Color(0x2F2F2F);
	public Color grey2 = new Color(0x2B2B2B);
	public Color flatBlue = new Color(0x0AB7F2);
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Back);
		g.fillRect(0, 0, 550, 1050);
		
		for (int i = 0; i < 10; i+=1) {
			for(int j = 0; j < 20; j+=1) {
				if((i+j)%2==0) {
					g.setColor(grey1);
					g.fillRect(50*i+5, 50*j+5, 45, 45);
				}
				else {
					g.setColor(grey2);
					g.fillRect(50*i+5, 50*j+5, 45, 45);
				}
			}
		} 
		Frame tetris = Frame.tetris;
		
		for (int i = 0; i < tetris.shadowBoxes.size(); i+=1) {
			if(tetris.shadowBoxes.get(i) != null) {
				g.setColor(Color.WHITE);
				g.fillRect(50*tetris.shadowBoxes.get(i).x+5, 50*tetris.shadowBoxes.get(i).y+5, 45, 45);
			}
		
	}
		for (int i = 0; i < tetris.boxes.size(); i+=1) {
			if(tetris.boxes.get(i) != null) {
				g.setColor(tetris.boxes.get(i).getColor());
				g.fillRect(50*tetris.boxes.get(i).x+5, 50*tetris.boxes.get(i).y+5, 45, 45);
			}
		
	}
		for (int i = 0; i < tetris.setBoxes.size(); i+=1) {
			if(tetris.setBoxes.get(i) != null) {
				g.setColor(tetris.setBoxes.get(i).getColor());
				g.fillRect(50*tetris.setBoxes.get(i).x+5, 50*tetris.setBoxes.get(i).y+5, 45, 45);
			}
		
	}
		
		
	}
	
}