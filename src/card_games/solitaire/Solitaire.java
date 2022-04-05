package card_games.solitaire;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;

import card_game_resources.*;
import picture_project_resources.*;

public class Solitaire implements CardGame, ImageObserver{
	
	private static final int xOrigin = 100;
	private static final int yOrigin = 10;
	
	private SolitairePlayer solitairePlayer = new SolitairePlayer(xOrigin, yOrigin);
	private Deck solitaireDeck = new SolitaireDeck(xOrigin, yOrigin);
	
	private DeckResetButton resetButton = new DeckResetButton(xOrigin + 30, yOrigin + 50);
	private SolveSolitaireButton solveButton = new SolveSolitaireButton(xOrigin + 200, yOrigin+100);
	
	private InteractableObject selectedCard = new Stack(0,0);// = new Card(Card.Suit.SPADES, Card.ACE, false);
	private boolean cardSelected = false;
	private Coordinate selectedCardLocation = new Coordinate(0,0);
	private boolean cardFromHand = false;
	private int cardStackOrigin = 0;
	
	private ArrayList<Stack> stacks = new ArrayList<Stack>();
	
	private SolitaireDropZone hearts = new SolitaireDropZone(xOrigin + 360, yOrigin + 50, Card.Suit.HEARTS);
	private SolitaireDropZone diamonds = new SolitaireDropZone(xOrigin + 470, yOrigin + 50, Card.Suit.DIAMONDS);
	private SolitaireDropZone spades = new SolitaireDropZone(xOrigin + 580, yOrigin + 50, Card.Suit.SPADES);
	private SolitaireDropZone clubs = new SolitaireDropZone(xOrigin + 690, yOrigin + 50, Card.Suit.CLUBS);
	private ArrayList<DropZone> dropZones = new ArrayList<DropZone>(Arrays.asList(hearts, diamonds, spades, clubs));
	
	private CardApplication container;
	
	public Solitaire(CardApplication ca){
		container = ca;
		initializeStacks();
	}
	
	//Adds the appropriate number of cards to each stack - one face up and i more face down
	public void initializeStacks() {
		for(int i = 0; i < 7; i++) {
			stacks.add(new Stack(xOrigin + 30 + 110*i, yOrigin + 210));
			stacks.get(i).add(solitaireDeck.getTopCard());
			for(int j = 0; j < i; j++) {
				Card drawnCard = solitaireDeck.getTopCard();
				drawnCard.flip();
				stacks.get(i).add(drawnCard);
			}
		}
	}

	//Probably delete
	@Override
	public void drawCard(Player player) {

	}

	//Draws each game element
	//TODO  Optimize
	@Override
	public void draw(Graphics2D graphics, ImageObserver io) {

		for(DropZone d: dropZones) d.draw(graphics, io);
		for(Stack s : stacks) s.draw(graphics, io);
		//solveButton.draw(graphics, io);
		resetButton.draw(graphics, io);
		solitaireDeck.draw(graphics, io);
		solitairePlayer.draw(graphics, io);
		if(cardSelected)
			selectedCard.draw(graphics, io, selectedCardLocation.getX()-Card.WIDTH/2, selectedCardLocation.getY()-Card.HEIGHT/2);
	}

	//Probably delete?
	@Override
	public void reset() {

	}

	//On mouse click, execute result based on pixel input
	@Override
	public void mouseClickedAction(DigitalPicture pict, Pixel pix) {
		if(solitaireDeck.wasClicked(pix) && solitaireDeck.size() > 0) {
			for(int i = 0; i < 1; i++) {
				if(solitaireDeck.size() > 0)
					solitairePlayer.addCard(solitaireDeck.getTopCard());
				solitaireDeck.updateSize();
			}	
		}
		else if(resetButton.wasClicked(pix)) {
			System.out.println("RESET");;
			int iterations = solitairePlayer.handSize();
			for(int i = 0; i < iterations; i++) {
				Card returningCard = solitairePlayer.getTopCard();
				returningCard.flip();
				solitaireDeck.returnCard(returningCard);
			}
		}
		/*
		else if(solveButton.wasClicked(pix) && solveButton.isVisible()) {
			this.solve(pict, pix);
		}
		*/

	
		
	}

	//Returns size of deck
	public int deckSize() {
		return solitaireDeck.size();
	}

	//Returns nth card in deck
	public Card nthCard(int n) {
		return solitaireDeck.nthCard(n);
	}

	//returns true if a card is currently selected
	@Override
	public boolean cardSelected() {
		return cardSelected;
	}

	//returns the current object selected
	@Override
	public InteractableObject selectedCard() {
		return selectedCard;
	}

	//sets the selected card to a Card object
	public void selectCard(Card c) {
		selectedCard = c;
	}

	//
	@Override
	public void mouseDraggedAction(DigitalPicture pict, Pixel pix) {
		if(!cardSelected && solitairePlayer.handSize() > 0 && solitairePlayer.wasClicked(pix)){
			selectedCard = solitairePlayer.getTopCard();
			cardSelected = true;
			cardFromHand = true;
		}
		else {
			for(Stack stack: stacks) {
				if(!cardSelected && stack.size() > 0 && stack.faceWasClicked(pix)){
					selectedCard = stack.getSelectedStack(pix);
					cardSelected = true;
					cardFromHand = false;
					cardStackOrigin = stacks.indexOf(stack);
				}
			}
		}
	}
	
	@Override
	public void returnSelectedCard(Pixel pix) {
		// TODO  Optimize
		if(cardSelected) {
			int stackIndex = overStack(pix);
			int dropZoneIndex = overDropZone(pix);
			
			boolean dropped = false;
			if(dropZoneIndex != -1) {
				DropZone dropZone = dropZones.get(dropZoneIndex);
				if(cardFromHand) {
					if(dropZone.validDrop((Card) selectedCard)) {
						dropZone.addCard((Card) selectedCard);
						dropped = true;
					}
				}
				
				else
					if(((Stack) selectedCard).size() == 1) {
						Card convertedStack = ((Stack) selectedCard).nthCard(0);
						if(dropZone.validDrop(convertedStack)) {
							dropZone.addCard(convertedStack);
							dropped = true;
							if(stacks.get(cardStackOrigin).getNumberOfFaceUpCards() == 0)
								stacks.get(cardStackOrigin).flipNewCard();
						}
					}

			}
			if(!dropped) {
				if(stackIndex!=-1 && checkAddLegality(stackIndex)) {
					if(cardFromHand)
						stacks.get(stackIndex).add((Card) selectedCard);
					else {
						stacks.get(stackIndex).add((Stack) selectedCard);
						if(stackIndex != cardStackOrigin && stacks.get(cardStackOrigin).getNumberOfFaceUpCards() == 0)
							stacks.get(cardStackOrigin).flipNewCard();
					}
				}
				else if(cardFromHand) {
					solitairePlayer.returnCard((Card) selectedCard);
				}
				else {
					
					stacks.get(cardStackOrigin).add((Stack) selectedCard);
				}
			}
			cardSelected = false;
		}
	}
	
	private int overStack(Pixel pix) {
		int pixelOverStackIndex = -1;
		for(int i = 0; i < stacks.size() && pixelOverStackIndex == -1; i++) {
			if(stacks.get(i).wasClicked(pix))
				pixelOverStackIndex = i;
		}
		return pixelOverStackIndex;
	}
	
	private int overDropZone(Pixel pix) {
		int pixelOverDropZone = -1;
		for(int i = 0; i < dropZones.size() && pixelOverDropZone == -1; i++) {
			if(dropZones.get(i).wasClicked(pix)) 
				pixelOverDropZone = i;
		}
		return pixelOverDropZone;
	}
	
	
	private boolean checkAddLegality(int stackIndex) {
		// TODO  Optimize
		Card reference;
		if(cardFromHand)
			reference = (Card) selectedCard;
		else
			reference = ((Stack) selectedCard).nthCard(0);

		Card stackTop = stacks.get(stackIndex).nthCard(stacks.get(stackIndex).size()-1);
		boolean legal = false;
		if(stackTop.suit() == Card.Suit.NULL && reference.value() == Card.KING) {
			legal = true;
		}
		else if(stackTop.value() == reference.value() + 1) {
			if(stackTop.suit() == Card.Suit.CLUBS || stackTop.suit() == Card.Suit.SPADES) {
				if(reference.suit() == Card.Suit.DIAMONDS || reference.suit() == Card.Suit.HEARTS) {
					legal = true;
				}
			}
			else if(stackTop.suit() == Card.Suit.DIAMONDS || stackTop.suit() == Card.Suit.HEARTS) {
				if(reference.suit() == Card.Suit.CLUBS || reference.suit() == Card.Suit.SPADES) {
					legal = true;
				}
			}
			
		}
		return legal;
	}
	
	public void solve(final DigitalPicture pict, final Pixel pix) {
		Thread t = new Thread() {
			public void run(){
				int totalNumberOfCards = getTotalStackSize();
				for(int i = 0; i < totalNumberOfCards; i++)
			      solveSmallestCard(pict, pix);
			}
		};
		t.start();
	}
	
	public void solveSmallestCard(DigitalPicture pict, Pixel pix) {
		// TODO  Get working
		Card smallestCard = findSmallestCard();
		Coordinate cardPosition = smallestCard.getCenterPoint();
		Coordinate destination;

		switch (smallestCard.suit()) {
			case CLUBS:
				destination = clubs.getCenterPoint();
				break;
			case DIAMONDS:
				destination = diamonds.getCenterPoint();
				break;
			case HEARTS:
				destination = hearts.getCenterPoint();
				break;
			case SPADES:
				destination = spades.getCenterPoint();
				break;
			default:
				destination = new Coordinate(0,0);
				break;
		}
		
		int movementSpeed = 20;
		int distance = cardPosition.getDistanceTo(destination);
		int numberOfFrames = distance/movementSpeed;
		int horizontalDistance = destination.getX() - cardPosition.getX();
		int verticalDistance = destination.getY() - cardPosition.getY();
		
		for(int j = 0; j < numberOfFrames; j++) {
			Picture display = new Picture(HEIGHT, WIDTH);
			Graphics2D graphics = display.createGraphics();
			graphics.drawImage(new Picture("GameResources\\CardResources\\face.png").getBufferedImage(), cardPosition.getX(), cardPosition.getY(),this);
			graphics.drawImage(new Picture("GameResources\\General\\table.png").getBufferedImage(),0,0,this);
			
			pix = new Pixel(display, cardPosition.getX() + j*(int) (movementSpeed * horizontalDistance/distance) + xOrigin, cardPosition.getY() + j*(int) (movementSpeed * verticalDistance/distance) + yOrigin);
			container.mouseDraggedAction(display, pix);
		}
	}
	
	private Card findSmallestCard() {
		ArrayList<Card> topCards = new ArrayList<Card>();
		for(Stack s: stacks) {
			topCards.add(s.nthCard(s.size()-1));
		}
		
		int smallestCardIndex = 0;
		int smallestCardValue = 14;
		for(Card c: topCards) {
			if(c.value() < smallestCardValue && c.suit() != Card.Suit.NULL) {
				smallestCardValue = c.value();
				smallestCardIndex = topCards.indexOf(c);
			}
		}
		
		Card smallestCard = topCards.get(smallestCardIndex);
		return smallestCard;
	}
	
	private int getTotalStackSize() {
		int numberOfCards = 0;
		for(Stack s: stacks) {
			numberOfCards += s.size()-1;
		}
		return numberOfCards;
	}
	@Override
	public void setSelectedCardLocation(Coordinate c) {
		selectedCardLocation = c;
	}
	
	public void mouseUpdateAction(boolean mouseDown, Pixel pix) {
		if(solitaireDeck.size() == 0 && solitairePlayer.handSize() == 0 && !solveButton.isVisible()) {
			solveButton.switchVisibility();
		}
	}


	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}
