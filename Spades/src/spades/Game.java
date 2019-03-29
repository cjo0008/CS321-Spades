/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.util.Iterator;
import java.util.LinkedList;


/**
 *
 * @author xps8900
 */
public class Game 
{
    private Player p1,p2,p3,p4;
    private Deck gameDeck;
    private LinkedList<Card> middle;
    
    
    public Game()
    {
        p1 = new Player(1);
        p2 = new Player(2);
        p3 = new Player(3);
        p4 = new Player(4);
        middle = new LinkedList<Card>();
        
        gameDeck = new Deck();
        gameDeck.resetDeck();
    }
    
    public void newHand()
    {
        resetHands();
        clearMiddle();
        deal();
    }
    
    /*
    Deals out all the cards to the 4 players
    */
    private void deal()
    {
        gameDeck.resetDeck();
        gameDeck.resetDeck();
        
        int numCards = 0;
        while(numCards < 13)
        {
            
            p1.addCard(gameDeck.nextCard());
            p2.addCard(gameDeck.nextCard());
            p3.addCard(gameDeck.nextCard());
            p4.addCard(gameDeck.nextCard());
            numCards++;
        }
        p1.sort();
        p2.sort();
        p3.sort();
        p4.sort();
        
        
    }
    
    private void resetHands()
    {
        p1.clearHand();
        p2.clearHand();
        p3.clearHand();
        p4.clearHand();
    }
    
    
    /*
    Mostly just to make sure play card works
    */
    public void playHands()
    {
        int numCards = 0;
        while(numCards < 13)
        {
            
            p1.playCard(numCards);
            p2.playCard(numCards);
            p3.playCard(numCards);
            p4.playCard(numCards);
            numCards++;
        }
    }
    // TODO Add error checking if the card can be played
    public boolean layCard(int playerNum, int cardNum)
    {
        
        switch (playerNum)
        {
            case 1:
                middle.add(p1.playCard(cardNum));
                break;
            case 2:
                middle.add(p2.playCard(cardNum));
                break;
            case 3:
               middle.add(p3.playCard(cardNum));
                break;
            case 4:
                middle.add(p4.playCard(cardNum));
                break;
                
        }
        return allLaid();
                
    }
    
    
    private boolean allLaid()
    {
        if(middle.size() == 4)
            return true;
        else
            return false;
    }
    
    
    private void clearMiddle()
    {
        middle.clear();
    }
    
    public int findWinner()
    {
        Card biggest = middle.remove();
        Iterator i1 = middle.iterator();
        while(i1.hasNext())
        {
            Card temp = middle.remove();
            if(temp.isGreaterThan(biggest))
                biggest = temp;
        }
        return biggest.getOwner();
                        
    }
    
    public void displayHands()
    {
        p1.printHand();
        System.out.println();
        p2.printHand();
        System.out.println();
        p3.printHand();
        System.out.println();
        p4.printHand();
    }
    
    public void sortTest(){
        
        p1.sort();
    }
    
}
