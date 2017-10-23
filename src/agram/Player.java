package agram;

import java.util.ArrayList;

public class Player {

    String name;
    Deck deck = new Deck();
    ArrayList<Card> hand;
    Boolean trickWinner;

    private static int index = 0;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ArrayList<Card> getHand() { return hand; }

    public void setHand(ArrayList<Card> hand) { this.hand = hand; }

    public int getHandSize() { return hand.size(); }

    public int getValue(int index) { return hand.get(index).getValue();}

    public String getSuit(int index) { return hand.get(index).getSuit();}

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public void addCard() {
        hand.add(deck.addCard());
    }

    public String addPC() {
        index++;
        return "PC " + index;
    }

    public void playerHand() {

        if (hand.size() == 1) {
            System.out.println("You have " + hand.size() + " card in your hand.");
        }
        else {
            System.out.println("You have " + hand.size() + " cards in your hand.");
        }


        int cardNumber = 1;

        for (Card c: hand) {
            System.out.println(cardNumber + ": "+ c);
            cardNumber++;

        }

    }






}
