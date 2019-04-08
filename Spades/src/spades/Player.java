/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

/**
 *
 * @author xps8900
 */
public class Player 
{
    private boolean myTurn;
    private ArrayList<Card> hand;
    private int nextInd;
    private final int playerNum;
    
    public Player(int num)
    {
        hand = new ArrayList<>();
        myTurn = false;
        playerNum = num;
        nextInd = 0;
    }
    
    public boolean addCard(Card c1)
    {
        if(hand.size() >= 13)
            return false;
        else{
            c1.setOwner(playerNum);
            hand.add(c1);
            nextInd++;
            return true;
        }
                    
    }
    
    public Card playCard(Card c1)
    {
        if(canPlay(c1))
            hand.remove(c1);
        return c1;
    }
    
    public void clearHand()
    {
        hand.clear();
        nextInd = 0;
    }
    // TODO change for following suit rules and leading trump
    public boolean canPlay(Card c1)
    {
       return hand.contains(c1);
    }
    public boolean hasSuit(int s)
    {
        for(Card c : hand){
            if(c.getSuit() == s)
                return true;
        }
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
            System.out.println(hand.get(i));
        }
    }
    
    public void sort() 
    { 
        int n = hand.size(); 
        for (int i = 1; i < n; ++i) { 
            Card key = hand.get(i); 
            int j = i - 1; 
  
            /* Move elements of arr[0..i-1], that are 
               greater than key, to one position ahead 
               of their current position */
            while (j >= 0 && hand.get(j).sortGreater(key)) { 
                hand.set(j + 1, hand.get(j)); 
                j = j - 1; 
            } 
            hand.set(j + 1, key); 
        }
        
        //printHand();
    } 
    
    public ArrayList<Card> getHand()
    {
        ArrayList<Card> temp = new ArrayList<>(hand);
        //Collections.copy(temp,hand);
        
        //System.out.println("\n\n");
        //Collections.reverse(Arrays.asList(temp));
        return temp;
    }
    
    public int numCards()
    {
        return hand.size();
    }

    
}
