package card_game_resources;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> cards;
	private int maxSize;
	
	public Hand(int maxSize) {
		cards = new ArrayList<Card>();
		this.maxSize = maxSize;
	}
	
	public void addCard(Card card) {
		if(cards.size() + 1 <= maxSize)
			cards.add(card);
	}
	public void addCard(Card card, int n) {
		if(cards.size() + 1 <= maxSize)
			cards.add(n, card);
	}
	
	public int size() {
		return cards.size();
	}
	
	public Card nthCard(int n) {
		Card card = cards.get(0);
		if(n < cards.size())
			card = cards.get(n);
		return card;
	}
	
	public Card removeCard(int n) {
		return cards.remove(n);
	}
}
