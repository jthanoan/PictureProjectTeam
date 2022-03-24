package card_games;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import card_game_resources.*;
import picture_project_resources.*;

public class DeckResetButton extends Button{
	
	private final static int HEIGHT = 140;
	private final static int WIDTH = 100;
	private int x;
	private int y;
	
	private final static int ICON_HEIGHT = 60;
	private final static int ICON_WIDTH = 60;
	
	private BufferedImage base = new Picture("GameResources\\General\\reset_button.png").getBufferedImage();
	private BufferedImage icon = new Picture("GameResources\\General\\reset_icon.png").getBufferedImage();

	
	
	public DeckResetButton(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics graphics, ImageObserver io) {
		// TODO Auto-generated method stub
		graphics.drawImage(base, x, y, io);
		graphics.drawImage(icon, x + WIDTH/2 - ICON_WIDTH/2, y + HEIGHT/2 - ICON_HEIGHT/2, io);
	}
	
}
