package com.github.CynthiaYang88.blackJack;

/**
* This class implements the interface StringStack, and creates a deck of 52 cards.
*/

/***********************************************************
* This code is a modified version of the following code:
* Title: FixedStack source code
* Author: Herbert Schildt
* Date: 2014
* Version: 1.0
* Availability: Java The Complete Reference, 9th ed, p.201
***********************************************************/
class StackOfCards implements StringStack
{   // implementation of StringStack generating a deck of 52 cards
   private String stck[];  // stack
   private int tos;

   /**
    * This constructor allocates memory and initializes a stack.
    * @param size    the desired size of the stack
    */
   StackOfCards( int size )
   {
       stck = new String[ size ];
       tos = -1;
   }

   /**
    * This method pushes an item onto the stack.
    * @param item    the item that is going to be pushed onto the stack
    * @return void
    */
   public void push( String item )
   {
       if( tos==stck.length-1 ) 
       {
           System.out.println( "Deck is full." );
       }
       else
       {
           stck[ ++tos ] = item;
       }
   }

   /**
    * This method pops an item out of the stack.
    * @return    the item popped off the stack
    */
   public String pop()
   {
       if( tos < 0 )
       {
           System.out.println( "Stack underflow." );
           return " ";
       }
       else
       {
           return stck[ tos-- ];
       }
   }
}