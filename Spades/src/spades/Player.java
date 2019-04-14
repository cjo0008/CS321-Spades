/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;


import java.util.ArrayList;

/**
 * Creates a player with a hand of 13 cards
 * @author Colin Oberthur
 */
public class Player 
{
   
    private ArrayList<Card> hand; // the list of cards in this hand
    
    private final int playerNum; // The player number of this player 1-4, for ownership of cards
    
    /**
     * Creates a new player
     * @param num the player number of this player 1-4
     */
    public Player(int num)
    {
        hand = new ArrayList<>();
        
        playerNum = num;
        
    }
    
    /**
     * Adds a card to the hand of the player
     * @param c1 card to be added
     * @return if the card was added sucessfully 
     */
    public boolean addCard(Card c1)
    {
        if(hand.size() >= 13) // too many cards in the hand, can only be 13
            return false;
        else{
            c1.setOwner(playerNum); // Sets the owner of the card to this player
            hand.add(c1);
            
            return true;
        }
                    
    }
    
    /**
     * Removes the card if it is in the list
     * @param c1 the card to be played
     * @return the card to be played if it can be
     * 
     */
    public Card playCard(Card c1)
    {
        Card c;
        if(canPlay(c1)){
            hand.remove(c1);
            return c1;
        }
        else
            return null;
    }
    
    /**
     * Clears the hand so a new one can be added
     */
    public void clearHand()
    {
        hand.clear();
        
    }
   

    /**
     * If the card can be played, ie if the card exists in the hand
     * @param c1 the card to be played
     * @return if it exists in the hand
     */
    public boolean canPlay(Card c1)
    {
       return hand.contains(c1);
    }

    /**
     *  Sees if the player has a suit, this is for following suit in the game
     * @param s suit to be checked for
     * @return if the suit exists
     */
    public boolean hasSuit(int s)
    {
        for(Card c : hand){ // Could have done a fancy search but nah
            if(c.getSuit() == s)
                return true;
        }
        return false;
    }
    
    
    
    /**
     * Prints the hand as a string
     * For testing reasons
     */
    public void printHand()
    {
        for(int i = 0; i < 13; i++)
        {
            System.out.println(hand.get(i));
        }
    }
    
    /**
     * Sorts the hand by suit, with an insertion sort
     */
    public void sort() 
    { 
        int n = hand.size(); 
        for (int i = 1; i < n; ++i) { 
            Card key = hand.get(i); 
            int j = i - 1; 
  
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j >= 0 && hand.get(j).sortGreater(key)) { // Uses the other sort method in Card
                hand.set(j + 1, hand.get(j)); 
                j = j - 1; 
            } 
            hand.set(j + 1, key); 
        }
        
        //printHand();
    } 
    
    /**
     * Returns the list of cards in the hand
     * @return a copied list of cards
     */
    public ArrayList<Card> getHand()
    {
        ArrayList<Card> temp = new ArrayList<>(hand); // Copies so it doesn't break that rule
        //Collections.copy(temp,hand);
        
        //System.out.println("\n\n");
        //Collections.reverse(Arrays.asList(temp));
        return temp;
    }
    
    /**
     *  How many cards are in the hand
     * @return the number of cards
     */
    public int numCards()
    {
        return hand.size();
    }

    
}
