package card_game_resources;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import picture_project_resources.*;

public abstract class Button {
	private BoundingBox hitbox;
	private boolean visible = true;
	
	public Button(int x, int y, int width, int height) {
		hitbox = new BoundingBox(x,y,x+width,x+height);
	} 
	
	public void switchVisibility() {
		visible = !visible;
	};
	
	public boolean wasClicked(Pixel pix) {
		System.out.println(pix.getX() + " " +pix.getY());
		return hitbox.wasClicked(pix);
	}
	
	public abstract void draw(Graphics graphics, ImageObserver io);

	public boolean isVisible() {
		return visible;
	}
}
