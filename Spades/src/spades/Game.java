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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author xps8900
 */

public class Game extends JPanel {

    private Player p1, p2, p3, p4;
    private Deck gameDeck;
    private LinkedList<Card> middle;
    private List<Player> players;
    private Map<Card, Rectangle> mapCards;
    private Card selected;

    public Game() {
        players = new ArrayList<Player>();
        p1 = new Player(1);
        players.add(p1);
        p2 = new Player(2);
        players.add(p2);
        p3 = new Player(3);
        players.add(p3);
        p4 = new Player(4);
        players.add(p4);
        middle = new LinkedList<Card>();

        gameDeck = new Deck();
        gameDeck.resetDeck();
        deal();

        mapCards = new HashMap<>(players.size() * 5);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selected != null) {
                    Rectangle bounds = mapCards.get(selected);
                    bounds.y += 20;
                    repaint();

                }
                selected = null;
                // This is done backwards, as the last card is on
                // top.  Of course you could render the cards
                // in reverse order, but you get the idea

                for (Card card : players.get(0).getHand()) {
                    Rectangle bounds = mapCards.get(card);
                    if (bounds.contains(e.getPoint())) {
                        selected = card;
                        bounds.y -= 20;
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    public void newHand() {
        resetHands();
        clearMiddle();
        deal();
    }

    /*
    Deals out all the cards to the 4 players
     */
    private void deal() {
        gameDeck.resetDeck();
        gameDeck.resetDeck();

        int numCards = 0;
        while (numCards < 13) {

            players.get(0).addCard(gameDeck.nextCard());
            players.get(1).addCard(gameDeck.nextCard());
            players.get(2).addCard(gameDeck.nextCard());
            players.get(3).addCard(gameDeck.nextCard());
            numCards++;
        }
        players.get(0).sort();
        players.get(1).sort();
        players.get(2).sort();
        players.get(3).sort();
        players.get(0).printHand();

    }

    private void resetHands() {
        p1.clearHand();
        p2.clearHand();
        p3.clearHand();
        p4.clearHand();
    }

    // TODO Add error checking if the card can be played
    public boolean layCard(int playerNum, int cardNum) {

        switch (playerNum) {
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
        p1.printHand();
        System.out.println();
        p2.printHand();
        System.out.println();
        p3.printHand();
        System.out.println();
        p4.printHand();
    }

    public void sortTest() {

        p1.sort();
    }

    /*
    Mostly just to make sure play card works
    Just for testing
     */
    public void playHands() {
        int numCards = 0;
        while (numCards < 13) {

            p1.playCard(numCards);
            p2.playCard(numCards);
            p3.playCard(numCards);
            p4.playCard(numCards);
            numCards++;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 500);
    }

    @Override
    public void invalidate() {

        super.invalidate();
        mapCards.clear();
        Player hand = players.get(0);
        int cardHeight = (getHeight() - 20) / 3;
        int cardWidth = (int) (cardHeight * 0.6);
        int xDelta = cardWidth / 2;
        int xPos = (int) ((getWidth() / 2) - (cardWidth * (13 / 5.0)));
        int yPos = (getHeight() - 20) - cardHeight;
        for (Card card : hand.getHand()) {
            Rectangle bounds = new Rectangle(xPos, yPos, cardWidth, cardHeight);
            mapCards.put(card, bounds);
            xPos += xDelta;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        Player hand = players.get(0);
        for (Card card : hand.getHand()) {
            Rectangle bounds = mapCards.get(card);
            System.out.println(bounds);
            if (bounds != null) {
                g2d.setColor(Color.WHITE);
                g2d.fill(bounds);
                g2d.setColor(Color.GREEN);
                g2d.draw(bounds);
                Graphics2D copy = (Graphics2D) g2d.create();
                paintCard(copy, card, bounds);
                copy.dispose();
            }
        }
        g2d.dispose();
    }

    protected void paintCard(Graphics2D g2d, Card card, Rectangle bounds) {
        g2d.translate(bounds.x + 5, bounds.y + 5);
        g2d.setClip(0, 0, bounds.width - 5, bounds.height - 5);

        String text = card.toString();
        System.out.println("We in here");
        FontMetrics fm = g2d.getFontMetrics();

        g2d.drawString(text, 0, fm.getAscent());
    }

}
