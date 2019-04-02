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
    private int val;
    //1-Heart, 2-Diamond, 3-Club, 4-Spade
    private int suit;
    private int owner;

    public Card(int iSuit, int iVal) {
        val = iVal;
        suit = iSuit;
        owner = 0;
    }

    public int getSuit() {
        return suit;
    }

    public int getVal() {
        return val;
    }

    public void setOwner(int p) {
        owner = p;
    }

    public int getOwner() {
        return owner;
    }

    public boolean isGreaterThan(Card c1) {
        if (suit == 4 && c1.getSuit() != 4) {
            return true;
        } else if (suit != 4 && c1.getSuit() == 4) {
            return false;
        } else if (suit == c1.getSuit()) {
            if (val > c1.getVal()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public boolean sortGreater(Card c1) {
        if (suit > c1.getSuit()) {
            return true;
        } else if (c1.getSuit() > suit) {
            return false;
        }
        else
            return val > c1.getVal();
    }

    @Override
    public String toString() 
    {
        String valI;
        String suitS = "";
        switch (val)
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
                valI = val + "";
            
        }
        
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
    
    
    public boolean equals(Card c1)
    {
        return (c1.getSuit() == suit) && (c1.getVal() == val);
    }
}
