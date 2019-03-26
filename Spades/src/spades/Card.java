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
    
    public Card(int iSuit, int iVal)
    {
        val = iVal;
        suit = iSuit;
    }
    
    public int getSuit()
    {
        return suit;
    }
    
    public int getVal()
    {
        return val;
    }
    
    
    public boolean isGreaterThan(Card c1)
    {
        if(suit == 4 && c1.getSuit() != 4)
            return true;
        else if(suit != 4 && c1.getSuit() == 4)
            return false;
        else if(suit == c1.getSuit())
        {
            if(val > c1.getVal())
                return true;
            else
                return false;
        }
        else
            return true;
                 
    }
}
