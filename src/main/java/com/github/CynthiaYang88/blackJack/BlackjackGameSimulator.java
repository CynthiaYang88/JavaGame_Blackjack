package com.github.CynthiaYang88.blackJack;

import java.util.*; //Scanner;
import java.io.IOException;
/*import java.util.ArrayList;
import java.util.List;
import java.util.Collections; 
import java.util.Arrays;
import java.util.stream.*; */

/**
 * This class runs the BlackJack simulation by using the static method main.
 * 
 * @author Cindi
 * @version 1.0
 */
@SuppressWarnings("unused")
public class BlackjackGameSimulator {
	/**
	 * The main method interacts with player by welcoming him/her to the BlackJack
	 * game simulator, prompts for user input of name and initial bankroll amount,
	 * updates the player's bankroll after each game by using the results returned
	 * by the method playBJ(), and finally asks if the player wants to continue
	 * playing BlackJack.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		boolean playerContinue = true;
		double betAmount = 0;
		String name = "";
		double bank = 0;
		PlayerChipCounter currentBank = new PlayerChipCounter();
		double winLossAmnt = 0;
		ArrayList<ArrayList<Double>> outcome = new ArrayList<ArrayList<Double>>();
		double updateAmount = 0;
		String input = "C:/Users/music/Documents/workspace-spring-tool-suite-4-4.6.1.RELEASE/blackJack/input.csv";

		//System.out.println("two" + input);
		IO inputOutput = new IO();
		inputOutput.readFromCSV(input);
		// Display welcome message
		name = inputOutput.getName();// +
		System.out.println("\n Hello " + name + ", Welcome to Blackjack's World. \n");

		// Enter bankroll
		bank = inputOutput.getBank();// +

		// Display Blackjack game is starting
		System.out.println("\n" + name + ", your Blackjack game has started.\n");

		// Update bankroll based on outcome
		while (bank > 0 && playerContinue) {
			System.out.println(name + ", your current bankroll is $" + bank);

			// Ask bet amount
			Scanner in = new Scanner(System.in);
			System.out.println(" ");
			System.out.print("\n Enter bet amount: $");
			betAmount = in.nextInt();

			// Check if bet amount is valid
			if (betAmount > bank) {
				System.out.print("\n Bet amount cannot be greater than bank: $");
				betAmount = in.nextInt();
			}
			
			// Run the BlackJack simulator, and return the game results to outcome
			/* 0) Push, 1) Player Natural BlackJack, 2) Player wins, 3) Dealer wins */
			outcome = playBJ(name, betAmount, in);

			// for each hand j generated due to card pair splitting
			for (int j = 0; j < outcome.size(); j++) { // switch case statment for each outcome possibility
				switch (outcome.get(j).get(0).intValue()) { // Natural Black Jack on players initial two cards
				case 0: {
					System.out.println(" ");
					System.out.println("Push");
					updateAmount = 0 * betAmount; // no update
					bank = currentBank.updateBalance(bank, updateAmount);
					break;
				}
				case 1: {
					System.out.println(" ");
					System.out.println("Natural Blackjack, " + name + "_" + j + " Won!");
					updateAmount = 1.5 * betAmount; // pays +1.5x
					bank = currentBank.updateBalance(bank, updateAmount);
					break;
				}
				// Player Wins
				case 2: { // pays +1.0x player earns orig bet amount
					System.out.println(" ");
					System.out.print(name + "_" + j + " Won!");
					updateAmount = betAmount;
					/*
					 * Accounts for insurance against Dealer BlackJack when a Dealer initially has a
					 * face-up Ace. If Dealer has face up Ace, if player selected insurance of half
					 * the original bet amount, but dealer did not get BlackJack, and player won,
					 * then player loses insurance amount, and wins the original bet amount.
					 */

					// If had insurance on A, and player wins
					if (outcome.get(j).get(1).intValue() > 0) { // pays +0.5x player earns half original bet amount
						updateAmount = 0.5 * betAmount;
					}
					bank = currentBank.updateBalance(bank, updateAmount);
					break;
				}
				// Dealer Wins
				case 3: {
					System.out.println(" ");
					System.out.print("Try Again, Dealer Won");// System.out.print("Sorry " + name + "_" + j + ", Dealer Won.");
					/*
					 * Accounts for insurance against Dealer BlackJack when a Dealer initially has a
					 * face-up Ace. If Dealer has face up Ace, if player selected insurance of half
					 * the original bet amount, and dealer did get BlackJack, then player loses
					 * original bet amount, but wins the insurance bet which pays 2 to 1.
					 */
					if (outcome.get(j).get(1).intValue() > 0 && outcome.get(j).get(2).intValue() == 1) { // pays +0x (no
																											// change in
																											// player
																											// bankroll
																											// balance)
						updateAmount = 0;// *betAmount;
					}
					/*
					 * If Dealer has face up Ace, if player selected insurance of half the original
					 * bet amount, but dealer did not get BlackJack, and the dealer still won, then
					 * player loses insurance amount, and the original bet amount.
					 */

					// Dealer does not have BJ, player still put insurance
					else if (outcome.get(j).get(1).intValue() > 0 && outcome.get(j).get(2).intValue() == 0) { // pays
																												// -1.5x
																												// (player
																												// loses
																												// money)
						updateAmount = -1.5 * betAmount;
					}
					/*
					 * Dealer does not have initial face up Ace, and there is no insurance involved
					 * in computing earnings. Dealer won.
					 */
					else { // pays -1.0x (player loses money)
						updateAmount = -betAmount;// (-1)*betAmount;
					}
					bank = currentBank.updateBalance(bank, updateAmount);
					break;
				}
				}
			}

			// Asks player whether he/she wants to continue game
				System.out.println(" ");
				System.out.println("(1) = continue game, (0) = quit");
				int cont = in.nextInt();

				if (cont == 1) {
					playerContinue = true;
				} else {
					playerContinue = false;
					System.out.println(name + ", your current bankroll is $" + bank);
					inputOutput.writeToServer(bank);
				}

		}
	}

	/**
	 * This method controls the flow of the BlackJack game.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param nm     the name of the player
	 * @param btAmnt the original bet amount
	 * @return the outcome of the BlackJack game
	 */
	static ArrayList<ArrayList<Double>> playBJ(String nm, double btAmnt, Scanner in) { // Initialize Variables
		Deck objDeck; // object of Deck
		StackOfCards deck; // object of Stack of Cards
		ArrayList<String> cards = new ArrayList<String>(); // player ArrayList of cards
		ArrayList<String> cardsD; // dealer ArrayList of cards
		Card objCard; // object of Card
		// Player ArrayList of Hand objects, may have multiple hands from splitting
		ArrayList<Hand> player = new ArrayList<Hand>();
		Hand dealer; // dealer hand
		ArrayList<Integer> playerHandValue = new ArrayList<Integer>(); // point value of hand(s)
		int dealerHandValue = 0; // dealer point value
		ArrayList<Integer> aceP; // ArrayList to store values assigned to player Ace(s)
		ArrayList<Integer> aceD; // ArrayList to store values assigned to dealer Ace(s)
		ArrayList<Integer> aceValueP = new ArrayList<Integer>(); // temporary storage
		ArrayList<Integer> aceValueD = new ArrayList<Integer>(); // temporary storage
		double insuranceAmount = 0; // insurance bet amount
		int numAceP = 0; // number of player Aces
		int numAceD = 0; // number of dealer Aces
		// two dimensional ArrayList containig outcome of Blackjack game
		ArrayList<ArrayList<Double>> playerResults = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> results1 = new ArrayList<Double>(); // results for each different outcome.
		ArrayList<Double> results2 = new ArrayList<Double>(); // results have different names, because
		ArrayList<Double> results3 = new ArrayList<Double>(); // only reference is copied, and not the
		ArrayList<Double> results4 = new ArrayList<Double>(); // actual values.
		ArrayList<Double> results5 = new ArrayList<Double>();
		ArrayList<Double> results6 = new ArrayList<Double>();
		ArrayList<Double> results7 = new ArrayList<Double>();
		double zero = 0; // creates zero
		double one = 1; // creates one
		double two = 2; // creates two
		double three = 3;// creates three
		int decisionInsur; // decision for insurance
		int decisionSplit; // decision for splitting cards in hand
		int numCardsInHand = 0; // number of cards in hand
		ArrayList<Double> testPlayerBust = new ArrayList<Double>(); // if player busts

		// Deal Card
		objDeck = new Deck();
		deck = objDeck.createDeck();
		cards.add(0, objDeck.getCard(deck)); // get card
		cards.add(1, objDeck.getCard(deck)); // get card

		// Display Cards
		player.add(new Hand()); // 0 index
		importCard2Hand(cards, player.get(0));
		System.out.println(nm + "'s Cards");
		displayHand(player.get(0).getAllCards(), false);

		// Check if Ace in first player two cards
		aceP = new ArrayList<Integer>();
		numAceP = checkAce(player.get(0), aceP, in);

		// Assigns values to Ace
		if (numAceP == 2) {
			aceValueP.add(aceP.get(0)); // import A
			aceValueP.add(aceP.get(1));
		} else if (numAceP == 1) {
			aceValueP.add(aceP.get(0));
		} else {
			numAceP = 0;
		}

		// Check if Natural Blackjack
		playerHandValue.add(player.get(0).getHandPointValue(aceValueP));
		if (playerHandValue.get(0) == 21) {
			results1.add(0, one); // Player Natural Blackjack
			results1.add(1, zero); // Dealer Ace Blackjack insurance amount
			results1.add(2, zero); // Dealer Blackjack
			playerResults.add(results1); // import results to return 2D ArrayList
			System.out.println("Natural Blackjack   ");
			return playerResults;
		}

		// Dealer gets one card face up, and one down
		cardsD = new ArrayList<String>();
		cardsD.add(0, objDeck.getCard(deck));
		cardsD.add(1, objDeck.getCard(deck));

		// New Hand() object created
		dealer = new Hand();
		importCard2Hand(cardsD, dealer);
		System.out.println("Dealer's Cards");
		displayHand(cardsD, true);

		// If Dealer has Ace, prompt insurance
		if (dealer.getCard(0).substring(0, 1).equals("A")) {
			// Ask if player wants insurance
			System.out.println(" ");
			System.out.print("\n Enter (1) if want insurance, or (0) if not: $");
			decisionInsur = in.nextInt();
			// If player agreed to insurance
			if (decisionInsur == 1) {
				insuranceAmount = insuranceDealerBJ(btAmnt); // set insurance amount
			}
		}

		// Split hand if initial cards are same
		if (player.get(0).getCard(0).substring(0, 1).equals(player.get(0).getCard(1).substring(0, 1))) {
			// Prompt if split
			System.out.println(" ");
			System.out.println(" Enter (1) to split, and (0) otherwise: ");
			decisionSplit = in.nextInt();
			// Perform split if player agreed to it
			if (decisionSplit == 1) {
				player.add(new Hand()); // initialize new player hand
				split(player.get(0), player.get(1)); // splits pair to hand(0) hand(1)
				for (int i = 0; i < player.size(); i++) { // Display two new hands
					System.out.println(nm + "_" + i + "'s Cards");
					displayHand(player.get(i).getAllCards(), false);
				}
			}
		}

		// loop for each player hand
		for (int i = 0; i < player.size(); i++) { // while hand value is less than 21, prompt player "hit" or "stay"
			while (playerHandValue.get(i) < 21) { // Prompt player "hit" or "stay"
				System.out.println(nm + "_" + i + "'s Hand ");
				if (hitStay(in) == 0) {
					break;
				}
				// adds new card, to player hand
				player.get(i).setCard(objDeck.getCard(deck));

				// Display Cards
				System.out.println(nm + "_" + i + "'s Cards");
				displayHand(player.get(i).getAllCards(), false);

				// Check if Ace, specify value of Ace, return number of Aces in hand
				numAceP = checkAce(player.get(i), aceP, in);

				// Returns number cards in hand for player hand(i)
				numCardsInHand = player.get(i).numCards();

				/*
				 * Split hands again if same card as original (10, J, Q, K are not counted the
				 * same) Checks to see if most recent card is the same as the original split
				 * card
				 */
				if (player.get(i).getCard(0).substring(0, 1)
						.equals(player.get(i).getCard(numCardsInHand - 1).substring(0, 1))) {
					// Prompt player if split
					System.out.println(" ");
					System.out.println(" Enter (1) to split, and (0) otherwise: ");
					decisionSplit = in.nextInt();

					// Create new player hand if player responded split again
					if (decisionSplit == 1) {
						player.add(new Hand()); // initialize new player
						split(player.get(i), player.get(i + 1));
						System.out.println(nm + i + "s Cards");
						displayHand(player.get(i).getAllCards(), false);
						System.out.println(nm + (player.size()) + "s Cards");
						// adds new hand to handsize index
						displayHand(player.get(player.size()).getAllCards(), false);
					}
				}

				// add new player had value points
				playerHandValue.add(i, player.get(i).getHandPointValue(aceP));

				// if player hand value is greater than 21, then bust
				if (playerHandValue.get(i) > 21) {
					System.out.println(nm + "_" + i + "Busted");
					results2.add(0, three); // Player busted, dealer wins
					// if dealer has Ace and player wants insurance, otherwise 0
					results2.add(1, insuranceAmount);
					results2.add(2, zero); // dealer no Blackjack
					playerResults.add(results2);
				}
			}
			aceP.clear(); // clear ArrayList for next player hand loop
		}

		// returns 1st element of each player hands ( whether they busted or not )
		for (int i = 0; i < playerResults.size(); i++) {
			testPlayerBust.add(playerResults.get(i).get(0));
		}

		/*
		 * Stop game, return playerResults if all player hands busted by getting
		 * frequency of busts in all player's hands results.
		 */
		if (Collections.frequency(testPlayerBust, 3) == testPlayerBust.size() && testPlayerBust.size() != 0) { 
			// terminate game, and return results if all player hands busted
																												
			System.out.println(" all hands busted ");
			return playerResults;
		} else {
			// Dealer reveals second card
			System.out.println("Dealer's Cards");
			displayHand(cardsD, false);

			// Dealer checks for Ace
			aceD = new ArrayList<Integer>();
			numAceD = checkAceDealer(dealer, aceD);

			// Assigns the Ace values
			if (numAceD == 2) {
				aceValueD.add(aceD.get(0));
				aceValueD.add(aceD.get(1));
			} else if (numAceD == 1) {
				aceValueD.add(aceD.get(0)); // set ace value to
			} else {
				aceValueD.add(0);
			}

			// Check if Dealer Natural Blackjack
			dealerHandValue = dealer.getHandPointValue(aceValueD);
			if (dealerHandValue == 21) // must double check
			{
				results3.add(0, three);
				results3.add(1, insuranceAmount); // set insurance, otherwise 0
				results3.add(2, zero);
				playerResults.add(results3);
				System.out.println(" dealer Blackjack  ");
				return playerResults; // dealer wins
			}

			// while dealer < 17 , hit
			while (dealerHandValue < 17) {
				dealer.clearArray(); // Clears dealer hand ArrayList after each loop
				cardsD.add(objDeck.getCard(deck)); // add card
				importCard2Hand(cardsD, dealer); // import card to dealer's hand
				System.out.println("Dealer's Cards");
				displayHand(cardsD, false); // Display dealer's cards
				numAceD = checkAceDealer(dealer, aceD); // set A value, return # of A
				dealerHandValue = dealer.getHandPointValue(aceD); // dealer hand points

				// Dealer bust if over 21 points
				if (dealerHandValue > 21) {
					System.out.println("Dealer Busted");
					results4.add(0, two);
					results4.add(1, insuranceAmount); // set insurance, otherwise 0
					results4.add(2, zero);
					playerResults.add(results4);
					return playerResults; // player won
				}

				// Slow dealer loop for player to enjoy game
				try {
					Thread.sleep(700);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}

			}

			// Calculate results of BlackJack game for each player hand
			for (int i = 0; i < player.size(); i++) { // if dealer hand value greater than player hand value
				if (dealerHandValue > playerHandValue.get(i)) {
					results5.add(0, three); // dealer > player = dealer win
					results5.add(1, insuranceAmount); // set insurance, otherwise 0
					if (dealerHandValue == 21) // if dealer blackjack
					{
						results5.add(2, one);
					} else {
						results5.add(2, zero);
					}
					playerResults.add(results5);
				}
				// else if dealer hand value equal to player hand value
				else if (dealerHandValue == playerHandValue.get(i)) {
					results7.add(0, zero); // player = dealer, push
					results7.add(1, insuranceAmount); // set insurance, otherwise 0
					results7.add(2, zero); // dealer no Blackjack
					playerResults.add(results7);
				} else {
					results6.add(0, two); // player > dealer = player win
					results6.add(1, insuranceAmount); // set insurance, otherwise 0
					results6.add(2, zero); // dealer no Blackjack
					playerResults.add(results6);
				}
			}
			return playerResults;
		}
	}

	/**
	 * This method creates a card shaped output display on the console.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param crds     an ArrayList containing String representations of cards
	 * @param facedown a boolean value specifying whether the second card is face
	 *                 down
	 * @return void, but displays card representation to java console
	 */
	static void displayHand(ArrayList<String> crds, boolean facedown) { // Initialize variables for displaying wall
																		// dividers, roof and floor for card
		String wallDisp = "";
		String roofFloor = "";
		// wall
		if (facedown == true) {
			wallDisp = "|" + crds.get(0) + "|  " + "| * |  ";
			roofFloor = "  -      -    ";
		} else {
			for (int i = 0; i < crds.size(); i++) {
				wallDisp = wallDisp + "|" + crds.get(i) + "|  ";
				roofFloor = roofFloor + "  -    ";
			}
		}
		System.out.println(roofFloor + "\n" + wallDisp + "\n" + roofFloor + "\n\n");
	}

	/**
	 * This method imports cards to the Hand object.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param crds an ArrayList containing String representations of cards
	 * @param plyr a Hand object (can be player or dealer hand)
	 * @return void
	 */
	static void importCard2Hand(ArrayList<String> crds, Hand plyr) {
		for (int i = 0; i < crds.size(); i++) {
			plyr.setCard(crds.get(i));
		}
	}

	/**
	 * This method returns the insurance bet amount when the Dealer has an initial
	 * face up Ace.
	 * 
	 * @author Cindi
	 * @verion 1.0
	 * @param betAmnt the original bet amount placed by the player
	 * @return the insurance bet amount equal to half the original bet amount
	 */
	static double insuranceDealerBJ(double betAmnt) {
		return 0.5 * betAmnt;
	}

	/**
	 * This method splits a pair of identical cards, and imports them into separate
	 * hands.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param plyrHand    original hand that had the pair of identical cards
	 * @param newPlyrHand new hand that will contain the splitted card
	 * @return void
	 */
	static void split(Hand plyrHand, Hand newPlyrHand) {
		ArrayList<String> tempHand = new ArrayList<String>(); // ini temp ArrayList
		tempHand.add(plyrHand.getCard(1)); // converts String to ArrayList
		importCard2Hand(tempHand, newPlyrHand); // moves card 1 to specified new hand
		tempHand.clear(); // clears temporary hand for reuse
		plyrHand.removeCard(1); // removes moved card from original hand
	}

	/**
	 * This method prompts the player for "hit" or "stay" and returns the decision.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @return the decision of the player where "1" is hit, and "0" is stay
	 */
	static int hitStay(Scanner inputHitStay) {
		int hitStay = 0;
			System.out.println(" ");
			System.out.print("Enter (1) to hit, or (0) to stay.  ");
			hitStay = inputHitStay.nextInt();
		return hitStay;
	}

	/**
	 * This method checks the player's hand to see if there are any Aces, and
	 * assigns user input values.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param plyr a player's Hand object
	 * @param a    an empty ArrayList for saving the values assigned to "A" (either
	 *             1 or 11)
	 * @return the number of "A" in hand
	 */
	static int checkAce(Hand plyr, ArrayList<Integer> a, Scanner in) {
		int cntr = 0; // initializer counter for number of Aces
		for (int i = 0; i < plyr.numCards(); i++) // length correct
		{
			if (plyr.getCard(i).substring(0, 1).equals("A")) {
				// Ace valued 1 or 11
				System.out.println(" ");
				System.out.println("Enter (1) or (11) for card number " + (i + 1) + ": ");
				// imports the designated Ace values to ArrayList a
				a.add(in.nextInt());
				cntr = cntr + 1;
			}
		}
		// returns number of aces in hand
		return cntr;
	}

	/**
	 * This method checks to see if there are Aces in the dealer's hand, and
	 * automatically assigns values to the Aces based on what is best for the
	 * dealer's interests.
	 * 
	 * @author Cindi
	 * @version 1.0
	 * @param plyr a dealer's Hand object
	 * @param a    an empty ArrayList that will contain the values of the "A's"
	 */
	static int checkAceDealer(Hand plyr, ArrayList<Integer> a) { // Initialize variables
		Card tempCard; // temporary card
		ArrayList<Integer> acePos = new ArrayList<Integer>(); // ace position
		int[] cards = new int[plyr.numCards()]; // number of cards
		ArrayList<Integer> sum_A_possibilities = new ArrayList<Integer>(); // sum of Ace
		ArrayList<Integer> sumHandPoint_possibilities = new ArrayList<Integer>(); // sum hand
		ArrayList<Integer> difference = new ArrayList<Integer>(); // diff 21-sum Hand
		ArrayList<Integer> notBustPoints = new ArrayList<Integer>();// points that won't bust
		int indexOptimComb; // index of optimal point value possition
		Integer twentyOne = new Integer(21); // int to Integer 21
		int sum = 0; // sum of hand

		// scan through hand, find ace card
		for (int i = 0; i < plyr.numCards(); i++) { // if A located at i
			if (plyr.getCard(i).substring(0, 1).equals("A")) { // index of A in dealer hand
				acePos.add(i);
			}
			tempCard = new Card(plyr.getCard(i)); // returns string
			cards[i] = tempCard.getValue(); // returns int value of card value
		}

		// number of aces in hand
		if (acePos.size() == 0) { // no aces
			return 0;
		}
		// hand contains "A's"
		else { // total value of cards in hand excluding Ace, Ace is initialized to 0
			sum = 0;// --IntStream.of(cards).sum();
			// for all aces found
			for (int i = 0; i < (acePos.size() + 1); i++) { // Sum of Ace possibilities
				sum_A_possibilities.add((acePos.size() - i) * 11 + (acePos.size() - (acePos.size() - i)) * 1);
			}

			// for each of possible Ace combination, compute sum of total hand pts
			for (int i = 0; i < sum_A_possibilities.size(); i++) { // Sum of Hand including Ace
				sumHandPoint_possibilities.add(sum + sum_A_possibilities.get(i));
			}

			// see which hand point sum is closest to 21, but not over
			for (int i = 0; i < sum_A_possibilities.size(); i++) { // returns 21 - handPts
				difference.add(twentyOne - sumHandPoint_possibilities.get(i));
			}

			// for each possible Ace combination
			for (int i = 0; i < difference.size(); i++) { // (21-pts) must be pos. or equal to 0, so pnts are less than
															// or equal 21
				if (difference.get(i) >= 0) { // saves the possitive or 0 differences into ArrayList
					notBustPoints.add(difference.get(i));
				}
			}

			// returns hand point value that is closest to 21, minimum difference
			Integer minDiffFrom21 = Collections.min(notBustPoints);
			// returns index of optimal combination from sum_A_possibilities
			indexOptimComb = difference.indexOf(minDiffFrom21);

			// Assign Optimal values to Aces, import to ArrayList "a"
			for (int i = 0; i < indexOptimComb; i++) // for each A
			{
				a.add(1); // set Ace = 1, for indexOptimComb number of cards
			}
			for (int i = 0; i < (acePos.size() - indexOptimComb); i++) {
				a.add(11); // set rest of cards to 11
			}

			// clear ArrayLists for repeated use
			sum_A_possibilities.clear();
			sumHandPoint_possibilities.clear();
			difference.clear();
			notBustPoints.clear();

			// returns number of aces in hand
			return acePos.size();
		}
	}
}
