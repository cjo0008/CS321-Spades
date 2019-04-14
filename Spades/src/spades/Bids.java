/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import javax.swing.JOptionPane;

/**
 * This is the bids class which is takes in all the bids of the players
 * @author xps8900
 */

public class Bids 
{
    private int team1; // the total bid for team 1 players(1 and 3)
    private int team2; // the total bid for team 2 players (2 and 4)
    private boolean hasBid;// if the bidding has occured, necessary for the game
    
    /**
     * Creates a set of bids and initilizes the total bid to 0
     */
    public Bids()
    {
        team1 = 0;
        team2 = 0;
        hasBid = false;
               
    }

    /**
     * Returns the total bid for team 1
     * @return the total bid 1-13
     */
    public int team1Bid()
    {
        return team1;
    }

    /**
     * Returns the total bid for team 2
     * @return the total bid 1-13
     */
    public int team2Bid()
    {
        return team2;
    }
    
    /**
     * Starts the bidding
     * Creates 4 seperate joptionpane windows for the bidding to occur
     * Player1 always starts the bidding; 
     * doesn't bid if it already has been done
     */
    public void bid()
    {
        if(hasBid())
            return;
        team1 += Integer.parseInt(JOptionPane.showInputDialog("Player 1 bid"));
        team2 += Integer.parseInt(JOptionPane.showInputDialog("Player 2 bid"));
        team1 += Integer.parseInt(JOptionPane.showInputDialog("Player 3 bid"));
        team2 += Integer.parseInt(JOptionPane.showInputDialog("Player 4 bid"));
        hasBid = true;
    }
    
    /**
     * Returns if the bidding has already occured
     * @return if the bidding is done
     */
    public boolean hasBid()
    {
        return hasBid;
    }
}
