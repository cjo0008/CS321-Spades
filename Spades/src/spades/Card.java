/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

/**
 *
 * @author xps8900
 */
public class Card {

    //2-10, J-11, Q-12, K - 13, A-14. Aces are high in the game. 
    private final int val;
    //1-Heart, 2-Diamond, 3-Club, 4-Spade
    private final int suit;
    // Player number for the owner of the card 1-4
    private int owner;

    /**
     * Creates the card object
     * @param iSuit the suit of the card to be created 1-4
     * @param iVal the numerical value of the card 2-14
     */
    public Card(int iSuit, int iVal) {
        val = iVal;
        suit = iSuit;
        owner = 0;
    }

    /**
     * Return the suit of the card
     * @return the suit 1-4
     */
    public int getSuit() {
        return suit;
    }

    /**
     * Returns the value of the card
     * @return the value 1-14
     */
    public int getVal() {
        return val;
    }

    /**
     * Sets the owner of the card
     * @param p the player number of the owner 1-4
     */
    public void setOwner(int p) {
        owner = p;
    }

    /**
     *  Gets the owner of the card
     * @return the player number of the owner 1-4
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Sees if a given card is greater than this card
     * This is for determining the winner
     * Spades are always the biggest(suit value of 4)
     * @param c1 the card to be compared to 
     * @return if this card is larger than c1
     */
    public boolean isGreaterThan(Card c1) {
        // if one is a spade and the other isn't, the spade is the largest
        if (suit == 4 && c1.getSuit() != 4) { 
            return true;
        } else if (suit != 4 && c1.getSuit() == 4) {
            return false;
        } else if (suit == c1.getSuit()) { 
            // if they are the same suit the largest value wins, aces are high
            if (val > c1.getVal()) {
                return true;
            } else {
                return false;
            }
        } else {
            // if neither are spades and have different suit, one is not bigger than the other
            // This is implemented in the game findWinner() method because it is run from the 1st card to the last
            // Meaning that a card that is "thrown off" or has no meaning to the hand is simply ignored
            return false;
        }

    }

    /**
     * The greater than method for sorting the hands
     * This sorts the hands by suit numerically
     * @param c1 the card to be compared to 
     * @return if c1 is greater than this card
     */
    public boolean sortGreater(Card c1) {
        // Only for sorting the hand, smaller suit value the smaller the card
        if (suit > c1.getSuit()) { 
            return true;
        } else if (c1.getSuit() > suit) {
            return false;
        }
        else
            // If they have the same suit see if the value is bigger
            return val > c1.getVal();
    }

    @Override
    /**
     *  Converts the card to a string
     */
    public String toString() 
    {
        String valI; // string representation of the value
        String suitS = ""; // string representation of the suit
        switch (val) // settting the face cards and ace
        {
            case 11:
                valI = "J";
                break;
            case 12:
                valI = "Q";
                break;
            case 13:
                valI = "K";  
                break;
            case 14:
                valI = "A";
                break;
            default: 
                valI = val + ""; // if its not a face card its just the number
            
        }
        // Sets the unicode value for the suits
        switch (suit)
        {
            case 1:
                suitS = "♥";
                break;
            case 2:
                suitS = "♦";
                break;
            case 3:
                suitS = "♣";
                break;
            case 4:
                suitS = "♠";
                break;
            default:
                suitS += "";
                    
        }
        return valI + " " + suitS;
    }
    
    /**
     * Sees if two cards are equal
     * They should never be but still
     * Not sure if I actually use this method
     * @param c1 the card to be compared to
     * @return if the two cards are equal
     */
    public boolean equals(Card c1)
    {
        return (c1.getSuit() == suit) && (c1.getVal() == val);
    }
}
