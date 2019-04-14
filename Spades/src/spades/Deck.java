/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;



/**
 *  The deck of cards class
 * @author xps8900
 */
public class Deck 
{
    private Card deck[]; // Array of cards represent the deck
    int nextInd;
    
    /**
     * Builds the deck from a file in the same folder
     * If the file is bad, make sure it exists and the name is the same
     */
    public Deck()
    {
        try {
            buildDeck("CardList.txt");
        } catch (Exception ex) {
            System.out.println("Bad File Name");
        }
        nextInd = 0;
    }
    /**
     * Builds a card from a string
     * @param info the string for the card like J H or  10 C
     * @return the card that is built
     */
    private Card buildCard(String info)
    {
        String data[] = info.split(" ");
                
        String valS = data[0]; // The value is the first part of the string
        String suitS = data[1]; // The suit is the 2nd part
        int valI;
        int suitI;
        switch (valS) // Setting the letter to a value for the card
        {
            case "J":
                valI = 11;
                break;
            case "Q":
                valI = 12;
                break;
            case "K":
                valI = 13;  
                break;
            case "A":
                valI = 14;
                break;
            default: 
                valI = Integer.parseInt(valS);
            
        }
       
        switch(suitS) // Same for the suit
        {
            case "H":
                suitI = 1;
                break;
            case "D":
                suitI = 2;
                break;
            case "C":
                suitI = 3;
                break;
            case "S":
                suitI = 4;
                break;
            default:
                suitI = 0;
        }
        if (suitI == 0 || valI < 0 || valI > 14) // Checks for bad cards
            return null;
        Card temp = new Card(suitI, valI); 
        return temp;
    }
    
    /**
     * Shuffles the deck using the collections functions for a list
     */
    private void shuffleDeck()
    {
        Collections.shuffle(Arrays.asList(deck));
        Collections.shuffle(Arrays.asList(deck));
    }
    /**
     * Builds the deck from the file
     * @param fileName name of the file to be read in
     * @throws Exception if the file name is bad
     */
    private void buildDeck(String fileName) throws Exception
    {
        File file = new File(fileName); 
        Scanner sc = new Scanner(file); 
        Card c1;
        int index = 0;// starts the index of cards at 0
        deck = new Card[52]; // 52 cards in a deck
        
        
        while (sc.hasNextLine()){ // Loops through the file and builds a card at each line
            c1 = buildCard(sc.nextLine()); 
            if(c1 == null){
                System.out.println("bad Data");
                break;
            }
            else
            {
                // Adds the card to the deck
                deck[index] = c1;
                index++;
            }
                
        }
       
    }
    
    /**
     * Prints the deck as a string
     * MOstly a testing function
     */
    public void printDeck()
    {
        for(int i = 0; i < 52; i++)
        {
            System.out.println(deck[i]);
        }
    }
    
    /**
     * Returns the next card in the deck
     * @return the next card
     */
    public Card nextCard()
    {
        Card temp;
        if(nextInd == 52) // end of the deck
        {
            System.out.println("No More Cards");
            return null;
        }
            
        else
        {
            temp = deck[nextInd];
            nextInd++; // increments through the array
            return temp;
        }
        
    }
    
    /**
     * This is the public function to reset the deck
     * The main difference between this and shuffle is resetting the next card index
     * since spades is played with the full deck, it needs to be reset after each hand
     */
    public void resetDeck()
    {
        shuffleDeck();
        nextInd = 0;
    }
}
