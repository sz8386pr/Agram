package agram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

    public ArrayList<Card> getCards() { return cards; }

    private ArrayList<Card> cards;

    private static int index = -1;

    public static String[] getSuits() { return suits; }

    public static int[] getValues() { return values; }

    final static String[] suits = {"Hearts", "Cloves", "Diamonds", "Spades"};
   // final static String[] values = {"ACE", "3", "4", "5", "6", "7", "8", "9", "10"};
    final static int[] values = {3, 4, 5, 6, 7, 8, 9, 10, 11};

    public Deck() {

        cards = new ArrayList<Card>();

        for (String suit: suits) {

            // Skip the 11/ACE if the suit is Spades
            if (suit.equals("Spades") ) {
                for (int x = 0; x < values.length-1; x++) {
                    cards.add(new Card(suit, values[x]));
                }
            }
            else {
                for (int value: values) {
                cards.add(new Card(suit, value));
                }
            }
            // Shuffle the deck
            Collections.shuffle(cards);

        }
    }

    // Shuffled while creating the deck and additional shuffling/randomizing while distributing the card
    public Card newCard() {

        Random rn = new Random();
        int randomNum =  rn.nextInt(cards.size());
        Card newCard = cards.get(randomNum);
        cards.remove(randomNum);
        return newCard;


    }






}
