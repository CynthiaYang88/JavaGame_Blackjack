package com.github.CynthiaYang88.blackJack;

import java.util.*;

/**
* This class represents a deck of cards, by creating a deck, shuffleing the deck, and dealing a card
* @author Cindi
* @version 1.0
*/
class Deck 
{
   // Initialize Variables
   private StackOfCards myDeck1 = new StackOfCards( 52 ); // deck of cards
   private ArrayList<String> tempDeck = new ArrayList<String>();  //temporary deck
   private String suit = ""; // initialize suit String
   private char charSuit ; // initialize suit char
   private String cardString = ""; // intialize String representing card
   private String nonNumVal = ""; // value of non-numerical cards, face-cards

   /**
    * This method creates a deck of cards stored in a stack. 
    * @author Cindi
    * @version 1.0
    * @return    a stack representation of a deck of cards
    */
   public StackOfCards createDeck( )
   {   // Creates 4 suits 
       for( int i = 0; i < 4; i++ )
       {   // Creates 13 face values 2-10,J,Q,K,A
           for( int j = 1; j < 14; j++ )
           {   /* each card is represented by a String (length 3) where first
               two positions represent the value the card, and last position 
               represents the suit.  */
               switch ( i )
               {
                   case 0:
                   {
                       charSuit = '\u2665'; // unicode heart
                       suit = Character.toString(charSuit);
                       break;
                   }
                   case 1:
                   {
                       charSuit = '\u2660'; // unicode spade
                       suit = Character.toString(charSuit);
                       break;                        
                   }
                   case 2:
                   {
                       charSuit = '\u2663'; // unicode club
                       suit = Character.toString(charSuit);
                       break;                        
                   }
                   case 3:
                   {
                       charSuit = '\u2666'; // unicode diamond
                       suit = Character.toString(charSuit);
                       break;                        
                   }
               }

               // face card if "j" greater than 10
               if ( j > 10 )
               {  
                   switch( j )
                   {   
                       case 11:
                       {
                           nonNumVal = "J ";  
                           break;
                       }
                       case 12:
                       {
                           nonNumVal = "Q ";
                           break;                            
                       }
                       case 13:
                       {
                           nonNumVal = "K ";
                           break;                            
                       }
                   }
                   cardString = nonNumVal + suit;
                   tempDeck.add( cardString ); // 1-13 4 times                  
               }

               // face value = 10
               else if ( j == 10 )
               {
                   cardString = j + suit;
                   tempDeck.add( cardString ); // 1-13 4 times  
               }

               // face value = A
               else if ( j == 1 )
               {
                   cardString = "A " + suit;
                   tempDeck.add( cardString ); // 1-13 4 times                      
               }

               // face value = j
               else
               {
                   cardString = j + " "  + suit;
                   tempDeck.add( cardString ); // 1-13 4 times                  
               }
           }
       }
       // Shuffle Deck of cards
       Collections.shuffle(tempDeck); 
       for( int k = 0; k < 52; k++ )
       {   // moves shuffled cards into stack
           myDeck1.push( tempDeck.get( k ) ); 
       }
       return myDeck1; // returns deck of cards
   }

   /**
    * This method deals a card from the top of the deck.
    * @author Cindi
    * @version 1.0
    * @param myD    the deck of cards
    * @return       a String representation of a card on the top of the deck
    */
   public String getCard( StackOfCards myD )
   {
       myDeck1 = myD;
       return myDeck1.pop();      // returns card on top of deck
   }
}