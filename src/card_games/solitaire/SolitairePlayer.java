package card_games.solitaire;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import card_game_resources.*;
import picture_project_resources.Pixel;

public class SolitairePlayer implements Player{
	private final static int MAX_HAND_SIZE = 255;
	private final static int X_OFFSET = 220;
	private final static int Y_OFFSET = 50;
	private final static int CARD_OFFSET = 1;
	
	private Hand hand;
	private Coordinate handLocation;
	private BoundingBox hitbox;
	//Can all cards be interacted with or just the top card
	
	public SolitairePlayer(int x, int y) {
		initializeHand(MAX_HAND_SIZE, x + X_OFFSET, y + Y_OFFSET, CARD_OFFSET);
	}
	
	@Override
	public void initializeHand(int maxHandSize, int x, int y, int cardOffset) {
		this.hand = new Hand(maxHandSize);
		this.handLocation = new Coordinate(x,y);
	}

	@Override
	public void mouseClickedAction(Pixel pix) {
	}

	@Override
	public void draw(Graphics2D graphics, ImageObserver io) {
		
		int handSize = trim(hand.size(), 0, 3);
		ArrayList<Card> displayedCards = new ArrayList<Card>();
		
		for(int i = 0; i < handSize; i++) {
			Card c = hand.nthCard(hand.size()-(i+1));
			displayedCards.add(c);
		}
		
		for(int i = handSize-1; i > -1; i--) {
			int x = handLocation.getX()-i*20;
			int y = handLocation.getY();
			displayedCards.get(i).draw(graphics, io, x, y);
		}
		
	}
	
	@Override
	public void addCard(Card c) {
		hand.addCard(c);
	}
	
	@Override
	public void takeCard(Card c) {

	}
	
	@Override
	public boolean wasClicked(Pixel pix) {
		int handSize = trim(hand.size(), 0, 3);
		boolean clicked = false;
		int x = handLocation.getX();
		int y = handLocation.getY();
		hitbox = new BoundingBox(x, y, Card.WIDTH + x, Card.HEIGHT + y);
		if(hitbox.wasClicked(pix)) clicked = true;
		return clicked;
	}
	
	private int trim(int i, int min, int max) {
		return Math.min(max, Math.max(i, min));
	}

	@Override
	public int handSize() {
		return hand.size();
	}
	public void returnCard(Card c) {
		hand.addCard(c);
	}
	
	public Card getTopCard() {
		return hand.removeCard(hand.size()-1);
	}
	
	public Card getBottomCard() {
		return hand.removeCard(0);
	}
	
	

}
