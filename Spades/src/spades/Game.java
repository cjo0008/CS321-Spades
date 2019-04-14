/*
 * This is where the magic happens

 */
package spades;

import java.util.Iterator;
import java.util.LinkedList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class does it all
 * The main contorller and view for the event driven programming
 * Creates and maintains a game
 * @author xps8900
 */
public class Game extends JPanel {

    
    private Deck gameDeck; // deck of cards
    private LinkedList<Card> middle; // list of cards in the middle i.e. the cards laid for a given round
    private List<Player> players; // List of the 4 players
    private Map<Card, Rectangle> mapCards; // Mapping a card to a rectangle so they can be displayed, may have been easier to have card extend rectangle but who cares
    private Card selected; // the selected card, it must be clicked twice to play
    private int currTurn; // the player whose turn it is 1-4
    
    private boolean spadeBroke; // If spades has been broken, it must be before it can be lead
    private Scoreboard s1; // the scoreboard 
    private String errorMessage; // any errors like following suit or not bidding
    private int team1Tricks, team2Tricks; // the number of tricks each team has won
   
    
    /**
     * This creates a new game
     * initilizes all the players and varialbles
     * In charge of dealing with the action listener for the clicking on the cards
     */
    public Game() {
        // General initliizations
        players = new ArrayList<>();
        for(int i = 1; i < 5; i++) // 1-4 for player numbers
            players.add(new Player(i)); // but is added 0-3 in the list, confusion!!
        middle = new LinkedList<>();
        spadeBroke = false;
        team1Tricks = team2Tricks = 0;
        gameDeck = new Deck();
        gameDeck.resetDeck(); // Shuffles the deck
        
        deal(); // deals the cards
        errorMessage = "";
        
        mapCards = new HashMap<>(players.size() * 13); // creates the new hash map of 52 cards
        currTurn = 1;
        s1 = new Scoreboard();
        
        // Core of the event driven programming, the mouse clicked function
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // If a card was previously selected
                if (selected != null) {
                    Rectangle bounds = mapCards.get(selected); // the the card associated with the rectangle clicked
                    bounds = deselRec(bounds, selected.getOwner()); // moves the card down so it is no longer selected
                    if (bounds.contains(e.getPoint())) { 
                        //The card was clicked on again
                        //System.out.println(players.get(selected.getOwner() - 1).canPlay(selected)); // Testing if the card can be played
                        if (selected.getOwner() == currTurn) { // if it is the player whos card was selected turns
                            
                            if (layCard(selected)) { // If that card was sucessfully played
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
                // So this is so that the card on top is the 1st one selected, not the ones behind
                
                ArrayList<Card> list = players.get(currTurn - 1).getHand();// Only cares about whos turn it is
                Collections.reverse(list); // reverses the hand for rendering

                for (Card card : list) { // loops through the hand
                    Rectangle bounds = mapCards.get(card); // finds the rectangle associated with the card

                    if (bounds.contains(e.getPoint())) { // we clicked on that rectangle
                        selected = card;
                        bounds = selRec(bounds, selected.getOwner()); // selected the card, moves it up

                        //System.out.println("The Card: " + selected + "Has been deselected");
                        
                        break;
                    }

                }
                
                repaint(); 
            }
           
        });
        

    }
    /**
     * Creates a new hand
     * Deals out the cards and resets the tricks taken to 0
     * Resets if spades have been broken
     * Also updates the score with the previous hands score, easiest way to know its done
     */
    
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
    /**
     * Selectes the rectangle by moving the card "up"
     * @param bounds the rectangle to be moved
     * @param owner the owner of the card, so we know which way is "up"
     * @return the moved rectangle
     */
    private Rectangle selRec(Rectangle bounds, int owner) {
        switch (owner) { // Each player has its own "up", basically perpendicular to the orentaion of the cards
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
    /**
     * Opposite of selRec
     * @param bounds the rectangle to be moved
     * @param owner the owner of the card
     * @return  the moved rectangle
     */
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

    /**
     * Deals out all the cards to the players
     */
    private void deal() {
        gameDeck = new Deck(); // replenishes the deck with cards
        gameDeck.resetDeck(); // shuffles it
        
        // Deals out one card to each player untill all cards are used.
        int numCards = 0;
        while (numCards < 52) {
            for (int i = 0; i < 4; i++) {

                players.get(i).addCard(gameDeck.nextCard());
                numCards++;
            }
        }
        // Sorts the hand to see it easier
        players.get(0).sort();
        players.get(1).sort();
        players.get(2).sort();
        players.get(3).sort();

    }
    /**
     * Clears the current hands of the players
     */
    private void resetHands() {
        for(Player p: players)
            p.clearHand();
    }

    // TODO Add error checking if the card can be played

    /**
     * Plays a card to the middle
     * Also sets the error message if a card cannot be played
     * @param c1 the card to lay
     * @return if the card was layed sucessfully
     */
    public boolean layCard(Card c1) 
    {
        
        
        int own = c1.getOwner();
        Player curr = players.get(own-1);
        Card lead = middle.peek(); // The first card laid determines the suit to follow
        if(!s1.doneBid()) // Bidding must occur before the 1st card can be played
        {
            System.out.println("You need to bid first");
            errorMessage = "Bidding Hasn't occured";
            return false;
        }
        if (lead != null) { // If this isn't the leading card
            if (c1.getSuit() != lead.getSuit() && curr.hasSuit(lead.getSuit())) { 
                //The suit trying to be played is not the leading suit and the player has a card of that suit
                // Basically not following suit when you can
                errorMessage = "You need to follow Suit";
                System.out.println("Need to follow Suit");
                return false;

            }
        }
        if(lead == null && c1.getSuit() == 4 && !spadeBroke){
            // Here if the card trying to be led is a spade
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
        if (currTurn > 4) { // loops back through whose turn it is
            currTurn = 1;
        }
        if(c1.getSuit() == 4)
            spadeBroke = true;
        errorMessage = " ";
        return true;

    }
    /**
     * Sees if all 4 cards have been played
     * @return true if all the cards have been laid in the middle,
     * false if they havent
     */
    private boolean allLaid() {
        if (middle.size() == 4) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Clears the middle to reset the stack of cards
     */
    private void clearMiddle() {
        middle.clear();
    }

    /**
     * Finds the winner of a trick
     * @return the player number of the winner 1-4
     */
    public int findWinner() {
        Card biggest = middle.remove();
        Iterator i1 = middle.iterator();
        while (i1.hasNext()) {
            // Loops through the cards played
            Card temp = middle.remove();
            if (temp.isGreaterThan(biggest)) {
                biggest = temp;
            }
        }
        return biggest.getOwner();

    }

    /**
     * Testing method printing off all the hands as strings
     */
    public void displayHands() {
        for(Player P : players)
            System.out.println(P.numCards());
    }
    
    /**
     * Sees if all 52 cards have been played
     * This is to determine if another hand must start
     * @return if all cards have been played
     */
    private boolean allPlayed()
    {
        for(Player p : players){
            if(p.numCards() != 0)
                return false;
        }
        currTurn = 1;
        return true;
    }
    /**
     * Displays the winner of the trick as a string 
     * @param g2d Graphics for drawing
     */
    private void displayWinner(Graphics2D g2d)
    {
        if(allLaid()){
            int winner = findWinner(); // the winner of the round
            if(winner%2 == 1) // players 1 0r 3
                team1Tricks++;
            else // players 2 or 4
                team2Tricks++;
            g2d.drawString("The winning player was: " + winner, 15,30); // draws the winning message
            
            currTurn = winner;
        }
    }

    @Override
    /**
     * Sets the size of the Jframe
     */
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }

    @Override
    /**
     * Ok so this runs when the size of the window or something has changed
     * Basically it changes the size of the cards, proportional to the height of the window
     * It also puts the cards in the correct location before being drawn
     * Also slight witchcraft
     */
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
            //Loops through each player and figures out where the starting postions for each hand are on the window
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
            // Each card in the players hand gets moved a certain amount based on the orentation of the hand on the window
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
                mapCards.put(card, bounds); // maps the card to the newly created rectangle

            }
            // Middle Checking
            // Sets the card in the middle the same as it would be for the hand
            // Now just anew starting point
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
                
                Rectangle bounds = new Rectangle(xPo, yPo, cardWidth, cardHeight);
                mapCards.put(c, bounds);
                // Maps the card to the new rectangle
            }
        }
    }
    /**
     * Creates a new game if the player wants to
     */
    private void newGame()
    {
        int option = JOptionPane.showConfirmDialog(null, "Would You Like to Play Again?", "Another Game?", 2);
        System.out.println(option);
        if(option != 0)
            System.exit(0);
        else{
            s1.reset();
            newHand();
        }
        
            
    }

    @Override
    /**
     * Paints all the objects on the screen
     * Also checks for new hand because i can
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g.create();
        displayWinner(g2d); // display the winner if there is one if not its just an empty string
        if (allPlayed()) { // checks for new hand, not sure why i do it here
            newHand();
            if(s1.win())
                newGame();
        }
        // Draws the status of the hand, like turn and number of tricks won
        g2d.drawString("It is currently player " + currTurn + " turn", 15, 15);
        g2d.drawString("Team 1 has: " + team1Tricks + " tricks", 15, 50);
        g2d.drawString("Team 2 has: " + team2Tricks + " tricks", 15, 70);
        g2d.drawString(errorMessage, 15, getHeight()-25);
        // This draws all the cards
        for (int i = 0; i < 4; i++) {
            Player hand = players.get(i);
            // Loops through each players hand
            for (Card card : hand.getHand()) {
                Rectangle bounds = mapCards.get(card);
                //Associated rectangle with the card via the map
                //System.out.println(bounds);
                if (bounds != null) {
                   if(card.getOwner() == currTurn || !s1.doneBid())
                       // Only let who needs to see the cards
                        g2d.setColor(Color.WHITE); // background to white
                    else{
                       // For the memes gradient painting the cards whose turn it isn't if the bidding has already occured
                        GradientPaint redtowhite = new GradientPaint(bounds.x,bounds.y,Color.MAGENTA, bounds.x + getWidth()/8, bounds.y+getHeight()/8,Color.CYAN);
                        g2d.setPaint(redtowhite);
                    }
                   // Draws tehe background and border
                    g2d.fill(bounds);
                    g2d.setColor(Color.BLACK);
                    g2d.draw(bounds);

                    Graphics2D copy = (Graphics2D) g2d.create();
                    //Creates the suit and value of the card on the rectangle
                    paintCard(copy, card, bounds, false); 
                    copy.dispose();
                }
            }
        }
        // Same things for the middle except they can always be seen
        for (Card c : middle) {
            Rectangle bounds = mapCards.get(c);
            //System.out.println(bounds);
            if (bounds != null) {
                g2d.setColor(Color.WHITE);
                g2d.fill(bounds);
                g2d.setColor(Color.BLACK);
                g2d.draw(bounds);

                Graphics2D copy = (Graphics2D) g2d.create();
                paintCard(copy, c, bounds, true);
                copy.dispose();
            }
        }
        
        
        
        g2d.dispose();
    }

    /**
     * Paints the suit and value of a given card
     * @param g2d graphics
     * @param card the card to be painted
     * @param bounds the rectangle where the things are to be painted on
     * @param mid if the card is in the middle or not, so it is displayed 
     */
    protected void paintCard(Graphics2D g2d, Card card, Rectangle bounds, boolean mid) {
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
        // Suit colors just so they are easier to distinguish
        switch (card.getSuit()) {
            case 1:
                g2d.setColor(Color.RED);
                break;
            case 2:
                g2d.setColor(Color.GREEN);
                 break;
            // Spades are blue becasue the unicode symbols are poo
            case 3:
                g2d.setColor(Color.BLUE);
                break;
            default:
                break;
        }

        //System.out.println("We in here");
        // So we don't draw the values on what we want the backs of the cards to look like
        if(!s1.doneBid() || currTurn == cardOwner || mid)
            g2d.drawString(text, 0, fm.getAscent());
    }
    
    /**
     * Returns the scoreboard so it can be added to the JFrame in the Frame class
     * @return the scoreboard being used.
     */
    public Scoreboard getScoreboard()
    {
        return s1;
    }
}
