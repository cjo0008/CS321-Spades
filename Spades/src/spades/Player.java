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
public class Player 
{
    private boolean myTurn;
    private Card hand[];
    private int nextInd;
    
    public Player()
    {
        hand = new Card[13];
        myTurn = false;
        nextInd = 0;
    }
    
    public boolean addCard(Card c1)
    {
        if(nextInd >= 13)
            return false;
        else{
            hand[nextInd] = c1;
            nextInd++;
            return true;
        }
                    
    }
    
    public Card playCard(int index)
    {
        Card temp;
        if(index >= 13 || index < 0)
            return null;
        else
        {
            temp = hand[index];
            hand[index] = null;
            return temp;
        }
    }
    
    
    
    
    public void setTurn()
    {
        myTurn = !myTurn;
    }
    
    public boolean isTurn()
    {
        return myTurn;
    }
    
    public void printHand()
    {
        for(int i = 0; i < 13; i++)
        {
            System.out.println(hand[i]);
        }
    }
    

    
}
