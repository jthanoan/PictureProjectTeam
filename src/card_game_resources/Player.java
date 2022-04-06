package card_game_resources;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import picture_project_resources.Pixel;

public interface Player {
	
	public void initializeHand(int maxHandSize, int x, int y, int cardOffset);
	
	public void addCard(Card c);
	
	public void takeCard(Card c);
	
	public void draw(Graphics2D graphics, ImageObserver io);
	
	public boolean wasClicked(Pixel pix);
	
	public int handSize();
	
	public void mouseClickedAction(Pixel pix);
}
