package card_game_resources;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import picture_project_resources.*;

public class Stack implements InteractableObject{
	
	private ArrayList<Card> faceDownCards = new ArrayList<Card>();
	private ArrayList<Card> faceUpCards = new ArrayList<Card>();

	private BoundingBox dropbox;
	private BoundingBox hitbox;
	private BoundingBox faceUpHitbox;
	
	private int x;
	private int y;
	
	private int selectedIndex = 0;
	
	public Stack(int x, int y) {
		hitbox = new BoundingBox(x, y, x+Card.WIDTH, y+Card.HEIGHT);
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D graphics, ImageObserver io, int x, int y) {
		int yOffset = 0;
		for(int i = 0; i < faceDownCards.size(); i++) {
			Card c = faceDownCards.get(i);
			c.draw(graphics, io, x, y + yOffset);
			yOffset += 24;
		}
		for(int i = 0; i < faceUpCards.size(); i++) {
			Card c = faceUpCards.get(i);
			c.draw(graphics, io, x, y + yOffset);
			yOffset += 24;
		}
	}
	
	public void draw(Graphics2D graphics, ImageObserver io) {
		this.draw(graphics, io, this.x, this.y);
	}
	
	public void add(Card c) {
		if(c.flipped())
			faceDownCards.add(c);
		else
			faceUpCards.add(c);
	}
	
	public void add(Stack c) {
		for(int i = 0; i < c.size(); i++) {
			this.add(c.nthCard(i));
		}
	}
	
	public int size() {
		return faceDownCards.size() + faceUpCards.size();
	}
	
	public int getNumberOfFaceUpCards() {
		return faceUpCards.size();
	}
	
	public boolean wasClicked(Pixel pix) {
		updateHitbox();
		
		return hitbox.wasClicked(pix);
	}
	
	public boolean faceWasClicked(Pixel pix) {
		updateHitbox();
		return faceUpHitbox.wasClicked(pix);
	}
	private void updateHitbox() {
		int size = faceDownCards.size() + faceUpCards.size();
		hitbox = new BoundingBox(x,y,x+100, y +140+20*(size-1));
		faceUpHitbox = new BoundingBox(x, y + 20 * (faceDownCards.size()), x + 100, y + 140 + 20*(faceUpCards.size()-1) +  y + 20 * (faceDownCards.size()));
	}
	
	private void mouseClickedAction(Picture pict, Pixel pix) {
		
	}
	
	public Stack getSelectedStack(Pixel pix) {
		Stack selectedStack = new Stack(0,0);
		selectedIndex = calculateSelectedStackSize(pix);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int i = 0; i < selectedIndex; i++) {
			cards.add(faceUpCards.remove(faceUpCards.size()-1));
		}
		for(int i = 0; i < selectedIndex; i++)
			selectedStack.add(cards.get(cards.size()-1-i));
			
		return selectedStack;
	}
	
	private int calculateSelectedStackSize(Pixel pix) {
		int y = faceUpCards.size()-normalizeToStackBottom(pix.getY());
		return y;
	}
	
	private int normalizeToStackBottom(int y) {
		int normalizedY = trimValue(y, this.y + 24*faceDownCards.size(), this.y+24*(this.size()-1)) - this.y -24*faceDownCards.size();
		normalizedY/=24;
		return normalizedY;
	}
	
	private int trimValue(int value, int min, int max) {
		if(value > max)
			value = max;
		else if (value < min)
			value = min;
		return value;
	}
	
	public Card nthCard(int N) {
		Card nthCard = new Card(Card.Suit.NULL, 1, true);
		if(N < this.size() && N > -1) {
			if(N < faceDownCards.size())
				nthCard = faceDownCards.get(N);
			else if(N - faceDownCards.size() < faceUpCards.size())
				nthCard = faceUpCards.get(N - faceDownCards.size());
		}
		return nthCard;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public void flipNewCard(){
		if(faceDownCards.size() > 0) {
			Card topCard = faceDownCards.remove(faceDownCards.size()-1);
			topCard.flip();
			faceUpCards.add(topCard);
		}
	}
	
	
}
