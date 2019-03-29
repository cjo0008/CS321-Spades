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
    private int playerNum;
    
    public Player(int num)
    {
        hand = new Card[13];
        myTurn = false;
        playerNum = num;
        nextInd = 0;
    }
    
    public boolean addCard(Card c1)
    {
        if(nextInd >= 13)
            return false;
        else{
            c1.setOwner(playerNum);
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
            System.out.println(temp);
            return temp;
        }
    }
    
    public void clearHand()
    {
        for(int i = 0; i < 13; i++)
        {
            hand[i] = null;
        }
        nextInd = 0;
    }
    
    public boolean canPlay(int cardNum)
    {
        if(hand[cardNum] != null)
            return true;
        else
            return false;
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
    
    public void sort() 
    { 
        int n = hand.length; 
        for (int i = 1; i < n; ++i) { 
            Card key = hand[i]; 
            int j = i - 1; 
  
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j >= 0 && hand[j].sortGreater(key)) { 
                hand[j + 1] = hand[j]; 
                j = j - 1; 
            } 
            hand[j + 1] = key; 
        }
        //printHand();
    } 
    

    
}
