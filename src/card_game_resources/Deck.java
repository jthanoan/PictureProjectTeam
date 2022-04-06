package card_game_resources;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;

import picture_project_resources.DigitalPicture;
import picture_project_resources.Picture;
import picture_project_resources.Pixel;

public class Deck {
	private final int HEIGHT = 160;
	private final int WIDTH = 120;
	private ArrayList<Card> deck;
	private final String path = "GameResources\\CardResources\\deck.png";
	private final BufferedImage fullDeckImage = new Picture(path).getBufferedImage();
	private BufferedImage deckImage = new Picture(path).getBufferedImage();
	private int x, y;
	private int yOffset=0;
	
	private BoundingBox hitbox;
	
	private boolean sizeUpdated = false;
	
	public Deck(int numberOfDecks, boolean shuffled, int x, int y){
		generateNewDeck(numberOfDecks, shuffled);
		this.x = x;
		this.y = y;
		hitbox = new BoundingBox(x,y,x+WIDTH,y+HEIGHT);
	}
	
	public void updateSize() {
		int offset = 2*(10-(deck.size()+3)/5);
		deckImage = fullDeckImage.getSubimage(0 + offset, 0, WIDTH-offset, HEIGHT-offset);
		yOffset = offset;
	}
	
	public void draw(Graphics2D graphics, ImageObserver io) {
		this.updateSize();
		if(this.size() > 0)
			graphics.drawImage(deckImage, x, y+yOffset,io);
		
	}
	
	private ArrayList<Card> generateNewDeck(int numberOfDecks, boolean shuffled) {
		deck = new ArrayList<Card>();
		Card.Suit[] suits = {Card.Suit.HEARTS, Card.Suit.DIAMONDS, Card.Suit.SPADES, Card.Suit.CLUBS};
		int[] values = {Card.ACE,2,3,4,5,6,7,8,9,10,Card.JACK,Card.QUEEN,Card.KING};
		for(int i = 0; i < numberOfDecks; i++) {
			for(Card.Suit suit: suits) {
				for(int value: values) {
					deck.add(new Card(suit, value, true));
				}
			}
		}
		
		if(shuffled) shuffle(deck);
		return deck;
	}
	
	public boolean canDrawCard() {
		boolean drawable = deck.size() > 0;
		
		return drawable;
	}
	
	public Card getTopCard() {
		Card topCard = deck.remove(0);
		topCard.flip();
		sizeUpdated = false;
		return topCard;
	}
	
	public void shuffle(ArrayList<Card> deck) {
		Collections.shuffle(deck);
	}
	
	public void reset(int numberOfDecks, boolean shuffled) {
		generateNewDeck(numberOfDecks, shuffled);
	}
	
	public int size() {
		return deck.size();
	}
	
	public Card nthCard(int n) {
		return deck.get(n);
	}
	
	public boolean wasClicked(Pixel pix) {
		return hitbox.wasClicked(pix);
	}
	
	public String toString() {
		String str = "";
		for(Card card : deck) {
			str += card.value() + " of  " + card.suit() + " / ";
		}
		return str;
	}
	
	public void returnCard(Card c) {
		deck.add(0, c);
	}

	
}
