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
public class Spades {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Card c1 = new Card(1, 5);
        Card c2 = new Card(4, 2);
        Card nums[] = new Card[2];
        nums[0] = c1;
        nums[1] = c2;
        System.out.println(c1.isGreaterThan(c2));
        
    }
    
}
