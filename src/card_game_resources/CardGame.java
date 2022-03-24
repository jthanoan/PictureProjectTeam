package card_game_resources;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import picture_project_resources.DigitalPicture;
import picture_project_resources.Pixel;

public interface CardGame {
	public void drawCard(Player player);
	public void draw(Graphics2D graphics, ImageObserver io);
	public void reset();
	public void mouseClickedAction(DigitalPicture pict, Pixel pix);
	public void mouseDraggedAction(DigitalPicture pict, Pixel pix);
	public int deckSize();
	public Card nthCard(int n);
	public boolean cardSelected();
	public InteractableObject selectedCard();
	public void setSelectedCardLocation(Coordinate c);
	public void returnSelectedCard(Pixel pix);
	public void mouseUpdateAction(boolean mouseDown, Pixel pix);
}
