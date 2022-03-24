package card_game_resources;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import picture_project_resources.*;

public abstract class DropZone {
	private Coordinate location;
	
	public DropZone(int x, int y) {
		location = new Coordinate(x,y);
	}
	
	public int getX() {
		return location.getX();
	}
	
	public int getY() {
		return location.getY();
	}
	
	
	
	public abstract void draw(Graphics2D graphics, ImageObserver io);
	
	public abstract void addCard(Card c);
	
	public abstract boolean validDrop(Card c);
	
	public abstract boolean wasClicked(Pixel pix);
}
