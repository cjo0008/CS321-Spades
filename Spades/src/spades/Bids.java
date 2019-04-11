/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import javax.swing.JOptionPane;

/**
 *
 * @author xps8900
 */
// TODO********** Write this to take in bids
public class Bids 
{
    private int team1;
    private int team2;
    private boolean hasBid;
    
    public Bids()
    {
        team1 = 0;
        team2 = 0;
        hasBid = false;
               
    }
    public int team1Bid()
    {
        return team1;
    }
    public int team2Bid()
    {
        return team2;
    }
    
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
    
    public boolean hasBid()
    {
        return hasBid;
    }
}
