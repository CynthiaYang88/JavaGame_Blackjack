package com.github.CynthiaYang88.blackJack;

import java.util.ArrayList;

/**
 * This class represents a hand of cards using a String ArrayList.
 * 
 * @author Cindi
 * @version 1.0
 */
public class Hand {
	private ArrayList<String> hand;
	private Card obCard;
	private ArrayList<Integer> valueCard;
	private int aceSel = 0;

	/**
	 * This constructor constructs a Hand by initializing empty ArrayLists.
	 * 
	 * @author Cindi
	 * @version 1.0
	 */
	public Hand() {
		hand = new ArrayList<String>();
		valueCard = new ArrayList<Integer>();
	}

	/**
	 * This method sets a card to the hand.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param crd an input card represented by a String
	 * @return void
	 */
	public void setCard(String crd) {
		hand.add(crd);
	}

	/**
	 * This method gets a card from the hand.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param psn the position of the card in the hand
	 * @return the length 3 String representation of the card at position "psn"
	 */
	public String getCard(int psn) {
		return hand.get(psn);
	}

	/**
	 * This method returns all the cards currently in the hand.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @return all the cards in the hand stored in an ArrayList
	 */
	public ArrayList<String> getAllCards() {
		return hand;
	}

	/**
	 * This method removes card from hand at position specified by "psn."
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param psn position of card to be removed from hand
	 * @return void
	 */
	public void removeCard(int psn) {
		hand.remove(psn);
	}

	/**
	 * This method return the number of cards in the hand.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @return the number of cards in the hand
	 */
	public int numCards() {
		return hand.size();
	}

	/**
	 * This method clears the hand.
	 * 
	 * @author Cindi
	 * @version 1.0
	 */
	public void clearArray() {
		hand.clear();
		valueCard.clear();
	}

	/**
	 * This method returns the total points of cards in the hand.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param ace the values assigned to the Aces in hand, 0 if no Aces
	 * @return the total point value of the hand
	 */
	public int getHandPointValue(ArrayList<Integer> ace) // hand, ace
	{
		int aceSum = 0; // initialize Ace sum
		valueCard.clear();

		// converts String card to Integer card value
		for (int i = 0; i < hand.size(); i++) {
			obCard = new Card(hand.get(i)); // card obj with String card
			valueCard.add(obCard.getValue()); // value of all cards incl A=0
		}

		// sum hand, Ace default = 0
		for (int i = 0; i < ace.size(); i++) {
			aceSum = aceSum + ace.get(i); // sum of A
		}

		// sum of hand + sum of A
		return valueCard.stream().mapToInt(value -> value).sum() + aceSum;
	}

	public int getAceSel() {
		return aceSel;
	}

	public void setAceSel(int aceSel) {
		this.aceSel = aceSel;
	}
}