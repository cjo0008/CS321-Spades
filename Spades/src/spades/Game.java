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
public class Game 
{
    private Player p1,p2,p3,p4;
    private Deck gameDeck;
    
    
    public Game()
    {
        p1 = new Player();
        p2 = new Player();
        p3 = new Player();
        p4 = new Player();
        gameDeck = new Deck();
        gameDeck.resetDeck();
    }
    public void deal()
    {
        int numCards = 0;
        while(numCards < 13)
        {
            
            p1.addCard(gameDeck.nextCard());
            p2.addCard(gameDeck.nextCard());
            p3.addCard(gameDeck.nextCard());
            p4.addCard(gameDeck.nextCard());
            numCards++;
        }
        
        
    }
    
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
    
}
