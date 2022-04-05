package card_games.solitaire;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import card_game_resources.*;
import picture_project_resources.*;

public class SolitaireDropZone extends DropZone{
	private static final int WIDTH = 100;
	private static final int HEIGHT = 140;
	private static final int ICON_WIDTH = 36;
	private static final int ICON_HEIGHT = 36;


	private ArrayList<Card> cards = new ArrayList<Card>();
	
	private BufferedImage empty = new Picture("GameResources\\General\\drop_zone.png").getBufferedImage();
	private BufferedImage suitIcon = new Picture("GameResources\\General\\drop_zone_icons.png").getBufferedImage();
	
	private Card.Suit suit;
	
	private BoundingBox hitbox;
	
	private int x;
	private int y;
	
	public SolitaireDropZone(int x, int y, Card.Suit suit) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.suit = suit;
		initializeSuitIcon();
		hitbox = new BoundingBox(x,y,x+WIDTH,y+HEIGHT);
		
	}
	
	private void initializeSuitIcon() {
		
		int iconOffset = 0;
		switch(suit) {
			case HEARTS:
				iconOffset = 0;
				break;
			case DIAMONDS:
				iconOffset = 1;
				break;
			case SPADES:
				iconOffset = 2;
				break;
			case CLUBS:
				iconOffset = 3;
				break;
			default:
				break;
		}
		suitIcon = suitIcon.getSubimage(iconOffset * ICON_WIDTH,0,ICON_WIDTH,ICON_HEIGHT);
	}
	
	@Override
	public void draw(Graphics2D graphics, ImageObserver io) {
		if(cards.size() == 0) {
			graphics.drawImage(empty, super.getX(), super.getY(), io);
			graphics.drawImage(suitIcon, super.getX()+WIDTH/2-ICON_WIDTH/2, super.getY()+HEIGHT/2-ICON_HEIGHT/2, io);
		} else {
			cards.get(cards.size()-1).draw(graphics, io, getX(), getY());
		}
	}

	@Override
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public Card.Suit getSuit(){
		return suit;
	}
	
	public boolean wasClicked(Pixel pix) {
		return hitbox.wasClicked(pix);
	}

	@Override
	public boolean validDrop(Card c) {
		boolean valid = false;
		if(c.suit() == this.suit) {
			if(cards.size() == 0 && c.value() == Card.ACE) 
				valid = true;
			else if(cards.size() != 0 && c.value() == cards.get(cards.size()-1).value() + 1) 
				valid = true;
		}
		return valid;
	}
	
	public Coordinate getCenterPoint() {
		return new Coordinate(this.x + WIDTH/2, this.y + HEIGHT/2);
	}
	
	

}
