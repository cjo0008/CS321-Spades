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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game g = new Game();
        g.deal();
        g.displayHands();
        g.playHands();
        g.displayHands();
        
    }
    
}
