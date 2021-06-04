package com.github.CynthiaYang88.blackJack;



/**
* This class computes the updated player bankroll balance, by inputting the current balance and 
* new update amount.
* @author Cindi
* @version 1.0
*/
public class PlayerChipCounter
{   // Initialize balance
   private double balance; // original balance
   private double newBalance; // new balance
   /**
    * This is a constructor for PlayerChipCounter that initiates "balance" to "0," if no input 
    * parameters are specified. 
    */
   public PlayerChipCounter()
   {
       balance = 0;
   }

   /**
    * This is a constructor for PlayerChipCounter that initiates "balance" to "b".
    * @param b    the player's current balance
    */
   public PlayerChipCounter( double b )
   {
       balance = b;
   }

   /**
    * This method computes the player's new bankroll balance.
    * @author Cindi
    * @version 1.0
    * @param balance       the player's current balance
    * @param updateAmount  the balance update amount, can be positive or negative
    */
   public double updateBalance( double balance, double updateAmount )
   {
       newBalance = balance + updateAmount; 
       return newBalance;
   }
}