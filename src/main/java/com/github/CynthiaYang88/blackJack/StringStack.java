package com.github.CynthiaYang88.blackJack;

/**
* This interface defines a string stack. 
*/

/**********************************************************
* This code is a modified version of the following code:
* Title: IntStack source code
* Author: Herbert Schildt
* Date: 2014
* Version: 1.0
* Availability: Java The Complete Reference, 9th ed, p.201
***********************************************************/
interface StringStack
{
 void push( String item ); // store an item
 String pop(); // retrieve an item
}