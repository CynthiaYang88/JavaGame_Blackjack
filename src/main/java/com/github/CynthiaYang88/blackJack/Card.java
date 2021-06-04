package com.github.CynthiaYang88.blackJack;

/**
 * This class represents a poker card using a String, and returns the suit and
 * value of the card.
 * 
 * @author Cindi
 * @version 1.0
 */
public class Card 
	{ // Initialize variables
	private String theCard; // card
	private String suit; // suit
	private int value; // face value

	/**
	 * This constructor constructs a Card from the input parameter String crd.
	 */
	// Constructor
	public Card(String crd) {
		theCard = crd;
	}

	/**
	 * This method returns the suit of the card.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @return the suit of the card
	 */
	public String getSuit() {
		suit = theCard.substring(2);
		return suit;
	}

	/**
	 * This method returns the point value of the card.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @return the point value of the card
	 */
	public int getValue() {
		switch (theCard.substring(0, 1)) {
		case "1": {
			value = 10;
			break;
		}
		case "2": {
			value = 2;
			break;
		}
		case "3": {
			value = 3;
			break;
		}
		case "4": {
			value = 4;
			break;
		}
		case "5": {
			value = 5;
			break;
		}
		case "6": {
			value = 6;
			break;
		}
		case "7": {
			value = 7;
			break;
		}
		case "8": {
			value = 8;
			break;
		}
		case "9": {
			value = 9;
			break;
		}
		case "J": {
			value = 10;
			break;
		}
		case "Q": {
			value = 10;
			break;
		}
		case "K": {
			value = 10;
			break;
		}
		case "A": { // default 0, specify optimal value later based on circumstances
			value = 0; // 1 or 11
			break;
		}
		}
		return value;
	}
}