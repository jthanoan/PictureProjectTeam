package card_game_resources;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import picture_project_resources.Pixel;

public interface InteractableObject {
	public void draw(Graphics2D graphics, ImageObserver io, int x, int y);
}
