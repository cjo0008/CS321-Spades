/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


/**
 *
 * @author xps8900
 */
public class Deck 
{
    private Card deck[];
    int nextInd;
    
    public Deck() throws Exception
    {
        buildDeck("CardList.txt");
        nextInd = 0;
    }
    
    private Card buildCard(String info)
    {
        String data[] = info.split(" ");
                
        String valS = data[0];
        String suitS = data[1];
        int valI;
        int suitI;
        switch (valS)
        {
            case "J":
                valI = 11;
                break;
            case "Q":
                valI = 12;
                break;
            case "K":
                valI = 13;  
                break;
            case "A":
                valI = 14;
                break;
            default: 
                valI = Integer.parseInt(valS);
            
        }
       
        switch(suitS)
        {
            case "H":
                suitI = 1;
                break;
            case "D":
                suitI = 2;
                break;
            case "C":
                suitI = 3;
                break;
            case "S":
                suitI = 4;
                break;
            default:
                suitI = 0;
        }
        if (suitI == 0 || valI < 0 || valI > 14)
            return null;
        Card temp = new Card(suitI, valI);
        return temp;
    }
    
    public void shuffle()
    {
        Collections.shuffle(Arrays.asList(deck));
        Collections.shuffle(Arrays.asList(deck));
    }
    
    private void buildDeck(String fileName) throws Exception
    {
        File file = new File(fileName); 
        Scanner sc = new Scanner(file); 
        Card c1;
        int index = 0;
        deck = new Card[52];
        
        
        while (sc.hasNextLine()){
            c1 = buildCard(sc.nextLine()); 
            if(c1 == null){
                System.out.println("bad Data");
                break;
            }
            else
            {
                deck[index] = c1;
                index++;
            }
                
        }
       
    }
    
    public void printDeck()
    {
        for(int i = 0; i < 52; i++)
        {
            System.out.println(deck[i]);
        }
    }
    
    public Card nextCard()
    {
        Card temp;
        if(nextInd == 52)
            return null;
        else
        {
            temp = deck[nextInd];
            nextInd++;
            return temp;
        }
        
    }
    
    public void resetDeck()
    {
        shuffle();
        nextInd = 0;
    }
}
