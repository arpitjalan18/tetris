package tetris;

import java.awt.Color;

public class box {

	Color color;
	int x;
	int y;
	public box( Color color, int x, int y) {
		
		this.color = color;
		this.x = x;
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public boolean full() {
		if (color != null){
			return true;
		}
		else {
			return false;
		}
	}
	public void gravity() {
		y++;
	}
	public String toString(){
		return(", " + x + ", " + y);
	}
}
