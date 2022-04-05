package card_games;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import card_game_resources.*;
import picture_project_resources.Picture;

public class SolveSolitaireButton extends Button{
	private final static int HEIGHT = 140;
	private final static int WIDTH = 100;
	
	private int x;
	private int y;
	
	private BufferedImage base = new Picture("GameResources\\General\\solve_button.png").getBufferedImage();
	
	public SolveSolitaireButton(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		super.switchVisibility();
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics graphics, ImageObserver io) {
		if(super.isVisible())
			graphics.drawImage(base, x, y, io);
	}
	
	

}
