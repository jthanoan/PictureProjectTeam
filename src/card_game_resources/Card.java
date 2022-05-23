package card_game_resources;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import picture_project_resources.*;

public class Card implements InteractableObject, ImageObserver{
	private Suit suit;
	private Color color;
	private int value;
	private boolean flipped;
	
	public static final int WIDTH = 100;
	public static final int HEIGHT = 140;
	
	public static final int ACE = 1;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	
	public static final Image back = new Picture("GameResources\\CardResources\\back.png").getBufferedImage();
	
	private Image face;
	
	private Image display = back;
	
	private static final BufferedImage NUMBERS = new Picture("GameResources\\CardResources\\numbers_final.png").getBufferedImage();
	private static final BufferedImage SMALL_ICONS = new Picture("GameResources\\CardResources\\icons_small.png").getBufferedImage();
	private static final BufferedImage LARGE_ICONS = new Picture("GameResources\\CardResources\\icons_large.png").getBufferedImage();
	private static final BufferedImage JACK_FACE_BLACK = new Picture("GameResources\\CardResources\\jack_face_black.png").getBufferedImage();
	private static final BufferedImage QUEEN_FACE_BLACK = new Picture("GameResources\\CardResources\\queen_face_black.png").getBufferedImage();
	private static final BufferedImage KING_FACE_BLACK = new Picture("GameResources\\CardResources\\king_face_black.png").getBufferedImage();
	private static final BufferedImage JACK_FACE_RED = new Picture("GameResources\\CardResources\\jack_face_red.png").getBufferedImage();
	private static final BufferedImage QUEEN_FACE_RED = new Picture("GameResources\\CardResources\\queen_face_red.png").getBufferedImage();
	private static final BufferedImage KING_FACE_RED = new Picture("GameResources\\CardResources\\king_face_red.png").getBufferedImage();

	private static final int numberHeight = 15;
	private static final int numberWidth = 13;
	
	private static final int smallIconHeight = 12;
	private static final int smallIconWidth = 12;
	
	private static final int largeIconHeight = 20;
	private static final int largeIconWidth = 20;
	
	private int x = 0;
	private int y = 0;
	
	private static final Coordinate[][] iconMap =
		{
			//ACE
			{
				normalizeToIconSize(WIDTH/2,HEIGHT/2)
			},
			//TWO
			{
				normalizeToIconSize(WIDTH/2,HEIGHT/4),
				normalizeToIconSize(WIDTH/2,3*HEIGHT/4)
			},
			//THREE
			{
				normalizeToIconSize(WIDTH/2,HEIGHT/4),
				normalizeToIconSize(WIDTH/2,2*HEIGHT/4),
				normalizeToIconSize(WIDTH/2,3*HEIGHT/4)
			},
			//FOUR
			{
				normalizeToIconSize(WIDTH/3,HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,HEIGHT/4),
				normalizeToIconSize(WIDTH/3,3*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,3*HEIGHT/4)
			},
			//FIVE
			{
				normalizeToIconSize(WIDTH/3,HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,HEIGHT/4),
				normalizeToIconSize(WIDTH/2,HEIGHT/2),
				normalizeToIconSize(WIDTH/3,3*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,3*HEIGHT/4)
			},
			//SIX
			{
				normalizeToIconSize(WIDTH/3,HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,HEIGHT/4),
				normalizeToIconSize(WIDTH/3, 2*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3, 2*HEIGHT/4),				
				normalizeToIconSize(WIDTH/3,3*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,3*HEIGHT/4)
			},
			//SEVEN
			{
				normalizeToIconSize(WIDTH/3,HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,HEIGHT/4),
				normalizeToIconSize(WIDTH/3, 2*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3, 2*HEIGHT/4),
				normalizeToIconSize(WIDTH/2, 3*HEIGHT/8),
				normalizeToIconSize(WIDTH/3,3*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,3*HEIGHT/4)
			},
			//EIGHT
			{
				normalizeToIconSize(WIDTH/3,HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,HEIGHT/4),
				normalizeToIconSize(WIDTH/3, 2*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3, 2*HEIGHT/4),
				normalizeToIconSize(WIDTH/2, 3*HEIGHT/8),
				normalizeToIconSize(WIDTH/2, 5*HEIGHT/8),
				normalizeToIconSize(WIDTH/3,3*HEIGHT/4),
				normalizeToIconSize(2*WIDTH/3,3*HEIGHT/4)
			},
			//NINE
			{
				normalizeToIconSize(WIDTH/3,3*HEIGHT/16),
				normalizeToIconSize(2*WIDTH/3,3*HEIGHT/16),
				normalizeToIconSize(WIDTH/3, 3*HEIGHT/8),
				normalizeToIconSize(2*WIDTH/3, 3*HEIGHT/8),
				normalizeToIconSize(WIDTH/3, 5*HEIGHT/8),
				normalizeToIconSize(2*WIDTH/3, 5*HEIGHT/8),
				normalizeToIconSize(WIDTH/3,13*HEIGHT/16),
				normalizeToIconSize(2*WIDTH/3,13*HEIGHT/16),
				normalizeToIconSize(WIDTH/2,HEIGHT/2)

			},
			//TEN
			{
				normalizeToIconSize(WIDTH/3,3*HEIGHT/16),
				normalizeToIconSize(2*WIDTH/3,3*HEIGHT/16),
				normalizeToIconSize(WIDTH/3, 13*HEIGHT/32),
				normalizeToIconSize(2*WIDTH/3, 13*HEIGHT/32),
				normalizeToIconSize(WIDTH/3, 19*HEIGHT/32),
				normalizeToIconSize(2*WIDTH/3, 19*HEIGHT/32),
				normalizeToIconSize(WIDTH/3,13*HEIGHT/16),
				normalizeToIconSize(2*WIDTH/3,13*HEIGHT/16),
				normalizeToIconSize(WIDTH/2,5*HEIGHT/16),
				normalizeToIconSize(WIDTH/2,11*HEIGHT/16)
			}
		};
	
	public enum Suit{
		HEARTS,
		DIAMONDS,
		SPADES,
		CLUBS,
		NULL
	}

	public enum Color{
		RED,
		BLACK
	}
	
	public Card(Suit suit, int value, boolean flipped) {
		this.suit = suit;
		this.value = value;
		this.flipped = flipped;

		if(suit == Suit.HEARTS || suit == Suit.DIAMONDS)
			this.color = Color.RED;
		else
			this.color = Color.BLACK;

		initializeCardFace();



	}
	
	public void initializeCardFace() {
		int numberColorOffset = 0;
		int suitOffset = 0;
		
		switch(suit) {
			case HEARTS:
				numberColorOffset = 0;
				suitOffset = 0;
				break;
			case DIAMONDS:
				numberColorOffset = 0;
				suitOffset = 1;
				break;
			case SPADES:
				numberColorOffset = NUMBERS.getHeight()/2;
				suitOffset = 2;
				break;
			case CLUBS:
				numberColorOffset = NUMBERS.getHeight()/2;
				suitOffset = 3;
				break;
			default:
				break;
		
		}
		
		BufferedImage base = new Picture("GameResources\\CardResources\\face.png").getBufferedImage();
		Graphics2D graphics = base.createGraphics();
		
		BufferedImage number = NUMBERS.getSubimage((value-1)*(numberWidth), numberColorOffset, numberWidth, numberHeight);
		BufferedImage invertedNumber = NUMBERS.getSubimage((value-1)*(numberWidth), numberColorOffset+numberHeight, numberWidth, numberHeight);
		
		BufferedImage smallIcon = SMALL_ICONS.getSubimage(suitOffset*smallIconWidth, 0, smallIconWidth, smallIconHeight);
		BufferedImage invertedSmallIcon = SMALL_ICONS.getSubimage(suitOffset*smallIconWidth, smallIconHeight, smallIconWidth, smallIconHeight);
		
		BufferedImage largeIcon = LARGE_ICONS.getSubimage(suitOffset*largeIconWidth, 0, largeIconWidth, largeIconHeight);
		BufferedImage invertedLargeIcon = LARGE_ICONS.getSubimage(suitOffset*largeIconWidth, largeIconHeight, largeIconWidth, largeIconHeight);
		
		ImageObserver io = this;
		
		graphics.drawImage(number, 4, 6, io);
		graphics.drawImage(invertedNumber, base.getWidth() - numberWidth - 4, base.getHeight() - numberHeight - 6, io);
		graphics.drawImage(smallIcon, 5, 24, io);
		graphics.drawImage(invertedSmallIcon, base.getWidth() - smallIconWidth -5, base.getHeight() - smallIconHeight-24, io);
		
		if(value<=10) {
			for(Coordinate c: iconMap[value-1]) {
				if(c.getY() < HEIGHT/2)
					graphics.drawImage(largeIcon, c.getX(), c.getY(), io);
				else
					graphics.drawImage(invertedLargeIcon, c.getX(), c.getY(), io);
			}
		}

		else if(value == JACK){
			if(this.color() == Color.RED)
				graphics.drawImage(JACK_FACE_RED, 20,20,io);
			else
				graphics.drawImage(JACK_FACE_BLACK, 20,20,io);
		}
		else if(value == QUEEN){
			if(this.color() == Color.RED)
				graphics.drawImage(QUEEN_FACE_RED, 20,20,io);
			else
				graphics.drawImage(QUEEN_FACE_BLACK, 20,20,io);
		}
		else if(value == KING){
			if(this.color() == Color.RED)
				graphics.drawImage(KING_FACE_RED, 20,20,io);
			else
				graphics.drawImage(KING_FACE_BLACK, 20,20,io);

		}

		face = base;
	}
	
	public int value() {
		return value;
	}
	
	public Suit suit() {
		return suit;
	}

	public Color color(){
		return color;
	}

	public void flip() {
		flipped = !flipped;

	}
	
	public String toString() {
		return value + " of " + suit;
	}
	
	public boolean flipped() {
		return flipped;
	}
	
	public boolean equals(Card card) {
		boolean equals = true;
		if(this.value() == card.value() && this.suit() == card.suit())
			equals = false;
		return equals;
	}
	
	
	private static Coordinate normalizeToIconSize(int x, int y) {
		return new Coordinate(x-largeIconWidth/2, y-largeIconHeight/2);
	}
	
	public void draw(Graphics2D graphics, ImageObserver io, int x, int y) {
		this.x = x;
		this.y = y;
		if(flipped)
			display = back;
		else
			display = face;
		graphics.drawImage(display, x, y, io);
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
	public Coordinate getCenterPoint() {
		return new Coordinate(this.x + WIDTH/2, this.y + HEIGHT/2);
	}

}