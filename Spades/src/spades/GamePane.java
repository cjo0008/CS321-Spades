/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author xps8900
 */
public class GamePane extends JPanel {

    private List<Player> players;

    private Map<Card, Rectangle> mapCards;

    private Card selected;

    /**
     *
     * @param players
     */
    public GamePane(List<Player> players) {
        this.players = players;
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
