/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xps8900
 */
public class Spades {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Card c1 = new Card(1, 5);
        Card c2 = new Card(4, 2);
        Deck d1 = null;
        try {
            d1 = new Deck();
        } catch (Exception ex) {
            System.out.println("ya done Goofed");
        }
        d1.printDeck();
        System.out.println();
        System.out.println();
        d1.shuffleDeck();
        d1.printDeck();
        
    }
    
}
