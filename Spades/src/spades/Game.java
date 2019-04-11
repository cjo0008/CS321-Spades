/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.util.Iterator;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author xps8900
 */
public class Game extends JPanel {

    
    private Deck gameDeck;
    private LinkedList<Card> middle;
    private List<Player> players;
    private Map<Card, Rectangle> mapCards;
    private Card selected;
    private int currTurn;
    
    private boolean spadeBroke;
    private Scoreboard s1;
    private String errorMessage;
    private int team1Tricks, team2Tricks;
    private int winner;
    
    
    public Game() {
        players = new ArrayList<>();
        for(int i = 1; i < 5; i++)
            players.add(new Player(i));
        middle = new LinkedList<>();
        spadeBroke = false;
        team1Tricks = team2Tricks = 0;
        gameDeck = new Deck();
        gameDeck.resetDeck();
        winner = 0;
        deal();
        errorMessage = "";
        
        mapCards = new HashMap<>(players.size() * 13);
        currTurn = 1;
        s1 = new Scoreboard();
        

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if (selected != null) {
                    Rectangle bounds = mapCards.get(selected);
                    bounds = deselRec(bounds, selected.getOwner());
                    if (bounds.contains(e.getPoint())) {

                        System.out.println(players.get(selected.getOwner() - 1).canPlay(selected));
                        if (selected.getOwner() == currTurn) {
                            
                            if (layCard(selected)) {
                                System.out.println("The Card: " + selected + " Has been Played");

                            }
                        }
                    }
                    repaint();

                }
                selected = null;
                // This is done backwards, as the last card is on
                // top.  Of course you could render the cards
                // in reverse order, but you get the idea
                
                
                ArrayList<Card> list = players.get(currTurn - 1).getHand();
                Collections.reverse(list);

                for (Card card : list) {
                    Rectangle bounds = mapCards.get(card);

                    if (bounds.contains(e.getPoint())) {
                        selected = card;
                        bounds = selRec(bounds, selected.getOwner());

                        System.out.println("The Card: " + selected + "Has been deselected");
                        repaint();
                        break;
                    }

                }
                

            }
            
        });
        

    }

    private void newHand() {
        resetHands();
        clearMiddle();
        s1.updateScore(team1Tricks, team2Tricks);
        team1Tricks = team2Tricks = 0;
        deal();
        invalidate();
        currTurn = 1;
        spadeBroke = false;
    }

    private Rectangle selRec(Rectangle bounds, int owner) {
        switch (owner) {
            case 1:
                bounds.y -= 20;
                break;
            case 2:
                bounds.x -= 20;
                break;
            case 3:
                bounds.y += 20;
                break;
            case 4:
                bounds.x += 20;
                break;

        }
        return bounds;
    }

    private Rectangle deselRec(Rectangle bounds, int owner) {
        switch (owner) {
            case 1:
                bounds.y += 20;
                break;
            case 2:
                bounds.x += 20;
                break;
            case 3:
                bounds.y -= 20;
                break;
            case 4:
                bounds.x -= 20;
                break;

        }
        return bounds;
    }

    /*
    Deals out all the cards to the 4 players
     */
    private void deal() {
        gameDeck = new Deck();
        gameDeck.resetDeck();
        

        int numCards = 0;
        while (numCards < 52) {
            for (int i = 0; i < 4; i++) {

                players.get(i).addCard(gameDeck.nextCard());
                numCards++;
            }
        }
        players.get(0).sort();
        players.get(1).sort();
        players.get(2).sort();
        players.get(3).sort();

    }

    private void resetHands() {
        for(Player p: players)
            p.clearHand();
    }

    // TODO Add error checking if the card can be played
    public boolean layCard(Card c1) 
    {
        
        
        int own = c1.getOwner();
        Player curr = players.get(own-1);
        Card lead = middle.peek();
        if(!s1.doneBid())
        {
            System.out.println("You need to bid first");
            errorMessage = "Bidding Hasn't occured";
            return false;
        }
        if (lead != null) {
            if (c1.getSuit() != lead.getSuit() && curr.hasSuit(lead.getSuit())) {
                errorMessage = "You need to follow Suit";
                System.out.println("Need to follow Suit");
                return false;

            }
        }
        if(lead == null && c1.getSuit() == 4 && !spadeBroke){
            errorMessage = "Spades Has Not been Broken";
            System.out.println("Spades hasn't been broken, can't leaad a spade");
            return false;
        }

        switch (own) {
            case 1:
                middle.add(players.get(0).playCard(c1));
                break;
            case 2:
                middle.add(players.get(1).playCard(c1));
                break;
            case 3:
                middle.add(players.get(2).playCard(c1));
                break;
            case 4:
                middle.add(players.get(3).playCard(c1));
                break;

        }
        invalidate();
        currTurn++;
        if (currTurn > 4) {
            currTurn = 1;
        }
        if(c1.getSuit() == 4)
            spadeBroke = true;
        errorMessage = " ";
        return true;

    }

    private boolean allLaid() {
        if (middle.size() == 4) {
            return true;
        } else {
            return false;
        }
    }

    private void clearMiddle() {
        middle.clear();
    }

    public int findWinner() {
        Card biggest = middle.remove();
        Iterator i1 = middle.iterator();
        while (i1.hasNext()) {
            Card temp = middle.remove();
            if (temp.isGreaterThan(biggest)) {
                biggest = temp;
            }
        }
        return biggest.getOwner();

    }

    public void displayHands() {
        for(Player P : players)
            System.out.println(P.numCards());
    }
    
    private boolean allPlayed()
    {
        for(Player p : players){
            if(p.numCards() != 0)
                return false;
        }
        currTurn = 1;
        return true;
    }
    
    private void displayWinner(Graphics2D g2d)
    {
        if(allLaid()){
            int winner = findWinner();
            if(winner%2 == 1)
                team1Tricks++;
            else
                team2Tricks++;
            g2d.drawString("The winning player was: " + winner, 15,30);
            
            currTurn = winner;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    @Override
    public void invalidate() {
        
        int cardWidth, cardHeight, xDelta, xPos, yPos;
        xPos = 0;
        yPos = 0;
        super.invalidate();
        mapCards.clear();
        cardHeight = (getHeight() - 20) / 6;
        cardWidth = (int) (cardHeight * 0.6);
        int xPosTemp = 0;
        int yPosTemp = 0;
        xDelta = cardWidth / 3;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0: {
                    xPos = (int) ((getWidth() / 2) - (cardWidth * (13 / 5.0)));
                    yPos = (getHeight() - 20) - cardHeight;
                    break;
                }
                case 1: {
                    xPosTemp = xPos;
                    cardWidth = (getHeight() - 20) / 6;
                    cardHeight = (int) (cardWidth * 0.6);

                    xPos = (getWidth() - 20) - cardWidth;
                    yPos = yPos - cardHeight;
                    break;
                }
                case 2: {
                    yPosTemp = yPos;
                    //System.out.println(yPosTemp);
                    cardHeight = (getHeight() - 20) / 6;
                    cardWidth = (int) (cardHeight * 0.6);

                    xPos = xPosTemp + xDelta;
                    yPos = 20;
                    break;
                }
                case 3: {
                    cardWidth = (getHeight() - 20) / 6;
                    cardHeight = (int) (cardWidth * 0.6);

                    xPos = 20;
                    yPos = yPosTemp - xDelta;
                    break;
                }
                default: {
                    System.out.println("Case 2");
                    xPos = 0;
                    yPos = 0;
                    xDelta = 5;
                }
            }
            Player p = players.get(i);

            for (Card card : p.getHand()) {
                switch (i) {
                    case 0:
                        xPos += xDelta;
                        break;
                    case 1:
                        yPos -= xDelta;
                        break;
                    case 2:
                        xPos -= xDelta;
                        break;
                    case 3:
                        yPos += xDelta;
                }
                Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
                mapCards.put(card, bounds);

            }
            // Middle Checking

            for (Card c : middle) {
                //System.out.println("In Here");
                int xPo = 0;
                int yPo = 0;
                switch (c.getOwner()) {
                    case 1:
                        cardHeight = (getHeight() - 20) / 6;
                        cardWidth = (int) (cardHeight * 0.6);

                        xPo = getWidth() / 2 - cardWidth / 2;
                        yPo = getHeight() / 2 + cardWidth;
                        //System.out.println("We here");
                        break;
                    case 2:
                        cardWidth = (getHeight() - 20) / 6;
                        cardHeight = (int) (cardWidth * 0.6);

                        xPo = getWidth() / 2 + cardHeight;
                        yPo = getHeight() / 2 - cardHeight / 2;
                        break;
                    case 3:
                        cardHeight = (getHeight() - 20) / 6;
                        cardWidth = (int) (cardHeight * 0.6);

                        xPo = getWidth() / 2 - cardWidth / 2;
                        yPo = getHeight() / 2 - cardHeight - cardWidth;
                        break;
                    case 4:
                        cardWidth = (getHeight() - 20) / 6;
                        cardHeight = (int) (cardWidth * 0.6);
                        xPo = getWidth() / 2 - cardWidth - cardHeight;
                        yPo = getHeight() / 2 - cardHeight / 2;
                        break;

                }
                // Switch card Width and height for cases 1 & 3 TODO************************
                Rectangle bounds = new Rectangle(xPo, yPo, cardWidth, cardHeight);
                mapCards.put(c, bounds);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        displayWinner(g2d);
        if (allPlayed()) {
            newHand();
        }
        g2d.drawString("It is currently player " + currTurn + " turn", 15, 15);
        g2d.drawString("Team 1 has: " + team1Tricks + " tricks", 15, 50);
        g2d.drawString("Team 2 has: " + team2Tricks + " tricks", 15, 70);
        g2d.drawString(errorMessage, 15, getHeight()-25);
        for (int i = 0; i < 4; i++) {
            Player hand = players.get(i);
            for (Card card : hand.getHand()) {
                Rectangle bounds = mapCards.get(card);
                //System.out.println(bounds);
                if (bounds != null) {
                    g2d.setColor(Color.WHITE);
                    g2d.fill(bounds);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);

                    Graphics2D copy = (Graphics2D) g2d.create();
                    paintCard(copy, card, bounds);
                    copy.dispose();
                }
            }
        }
        for (Card c : middle) {
            Rectangle bounds = mapCards.get(c);
            //System.out.println(bounds);
            if (bounds != null) {
                g2d.setColor(Color.WHITE);
                g2d.fill(bounds);
                g2d.setColor(Color.BLACK);
                g2d.draw(bounds);

                Graphics2D copy = (Graphics2D) g2d.create();
                paintCard(copy, c, bounds);
                copy.dispose();
            }
        }
        
        
        
        g2d.dispose();
    }

    protected void paintCard(Graphics2D g2d, Card card, Rectangle bounds) {
        int cardOwner = card.getOwner();
        FontMetrics fm = g2d.getFontMetrics();
        String text = card.toString();
        switch (cardOwner) {
            case 1:
                g2d.translate(bounds.x + 5, bounds.y + 5);

                break;
            case 2:
                g2d.translate(bounds.x + 5, bounds.y + bounds.height - fm.getDescent());
                g2d.rotate(-Math.PI / 2);
                break;
            case 3:
                g2d.translate(bounds.x + bounds.width - 5, bounds.y + bounds.height - fm.getDescent());
                g2d.rotate(Math.PI);
                break;
            case 4:
                g2d.translate(bounds.x + bounds.width - 5, bounds.y + 5);
                g2d.rotate(Math.PI / 2);
                break;

        }
        g2d.setClip(0, 0, bounds.width - 5, bounds.height - 5);
        if (card.getSuit() < 3) {
            g2d.setColor(Color.RED);
        }
        else if(card.getSuit() == 3)
            g2d.setColor(Color.CYAN);

        //System.out.println("We in here");
        g2d.drawString(text, 0, fm.getAscent());
    }
    
    
    
    public Scoreboard getScoreboard()
    {
        return s1;
    }
}
