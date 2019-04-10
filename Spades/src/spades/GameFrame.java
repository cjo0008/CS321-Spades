/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spades;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author xps8900
 */
public class GameFrame {

    public GameFrame() {
        Deck d1 = new Deck();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                   /*
                d1.resetDeck();
                List<Player> players = new ArrayList<>(5);
                for (int index = 1; index < 5; index++) {
                    players.add(new Player(index));
                }

                for (int index = 0; index < 13; index++) {
                    for (Player hand : players) {
                        hand.addCard(d1.nextCard());
                    }
                }
                //players.get(0).printHand();
                */
                Game g1 = new Game();
                JFrame frame = new JFrame("Lets Play Spades");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(g1, "Center");
                frame.add(g1.getScoreboard(), "North");
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }

}
