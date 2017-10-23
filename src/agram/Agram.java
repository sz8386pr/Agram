/*
Agram Rules

THE PACK
The kings, queens, jacks, the 2s of all suits and the ace of spades are removed from the deck. The cards of each suit rank,
from high to low: A, 10, 9, 8, 7, 6, 5, 4, 3. Because the ace of spades (called "Chief"') is removed from the deck,
the highest card in the spade suit is the 10.

THE DEAL
The dealer will deal six cards to each player, three at a time.

THE PLAY
The player to the left of the dealer leads with a card of their choice. The next player to the left then follows with their card.
If possible they must follow suit. However, if they cannot, they may play a card of any suit.
If the card played does not belong to the original suit, it has no value. After all players have played their card,
the player who has the highest card of the original suit (suit of the leading card of the round) wins the trick.

The winner of the trick leads any card from his hand to begin the next trick, playing it face up on top of the pile.
Once again, the other players must each play a card of the same suit as the card that was led, if possible.
Otherwise they may play any card.

This continues until six tricks have been played. Whoever wins the sixth and last trick wins the game.

 */


package agram;

import java.util.ArrayList;
import java.util.Scanner;

public class Agram {

    Scanner input = new Scanner(System.in); // Input scanner

    ArrayList<Player> players = new ArrayList<>(); // List of players
    ArrayList<Card> table = new ArrayList<>(); // List of cards on the table/in play

    Player player;
    Deck deck;
    Card cards;
    Boolean gameOver = true;
    int trick = 1;

    public static void main(String[] args) {
        new Agram().game();
    }
    private void game() {

        setup();

        while (gameOver) {
            playerTurn();
            //PCTurn();
            trick++;
        }


       /*
        System.out.println(players.get(0).getHand().get(0).getSuit());
        cards = players.get(0).hand.get(0);
        System.out.println(cards.compareTo(players.get(0).hand.get(1)));
        System.out.println(players.get(0).getHand());

        player.playerHand();
        */
    }

    // Initial setup phase
    private void setup() {
        // Let the player pick the number of players for the game (2-5)
        int numberOfPlayers = 0;
        while (numberOfPlayers < 2 || numberOfPlayers > 5) {
            System.out.println("Enter number of players (2-5): ");
            numberOfPlayers = input.nextInt();
        }
        // Add human player
        System.out.println("Enter your name: ");
        String name = input.next();
        player = new Player(name);
        players.add(player);

        // Add PC players
        for (int x=0; x < numberOfPlayers-1; x++) {
            String pc = player.addPC();
            players.add(new Player(pc));
        }

        // Distribute 6 cards to each players -- 3 cards first and then another 3 cards to each players
        for (int outer2 = 0; outer2 < 2; outer2++) {
            for (Player p: players) {
                player = p;
                for (int inner3 = 0; inner3 < 3; inner3++){
                    player.addCard();
                }
            }
        }
    }

    private void playerTurn(){


        // Set the player to the user
        player = players.get(0);
        int choice = 0;

        System.out.println("\nTrick #" + trick);

        player.playerHand();
        System.out.println();// Player hand status

        // Checks if card is in play in current trick
        if (table.size() > trick-1) {
            String playSuit = table.get(trick - 1).getSuit();
            System.out.println(playSuit + " is in play");

            Boolean cardSelected = true;

            // Checks if player has the same suit as the play card
            for (int x = 0; x < player.getHandSize(); x++) {
                String handSuit = player.hand.get(x).getSuit();

                // If user has any cards with the same suit as that of the current playing card, user must follow suit
                if (handSuit.contains(playSuit)) {
                    System.out.println("You have " + playSuit + " in your hands. You must play " + playSuit + " cards.");

                    System.out.println("Select a card to play: ");
                    choice = input.nextInt();

                    // If user has picked numbers outside of the range or has picked a different suit card
                    while (choice < 1 || choice > player.getHandSize() || !player.hand.get(choice).getSuit().contains(playSuit)) {
                        if (!player.hand.get(choice).getSuit().contains(playSuit)) {
                            System.out.println("You have " + playSuit + " in your hands. You must play " + playSuit + " cards.");
                        } else {
                            System.out.println("Enter a number between 1 to " + player.getHandSize());
                        }
                        choice = input.nextInt();
                    }

                    cardSelected = false;
                    break;
                }
            }

            // If user has no cards of the same suit as the playing suit has been found
            if (!cardSelected) {
                System.out.println("You don't have " + playSuit + " in your hands. Discard a card.");
                System.out.println("Select a card to play: ");
                choice = input.nextInt();

                while (choice < 1 || choice > player.getHandSize()) {
                    System.out.println("Enter a number between 1 to " + player.getHandSize());
                    choice = input.nextInt();
                }
            }
        }

        // If user is leading the trick
        else {
            System.out.println("No cards are in play. You may choose a suit for this trick");
            System.out.println();
            System.out.println("Select a card to play: ");
            choice = input.nextInt();

            while (choice < 1 || choice > player.getHandSize()) {
                System.out.println("Enter a number between 1 to " + player.getHandSize());
                choice = input.nextInt();
            }
        }

        // Adds the card to the table ArrayList and removes from the hand
        table.add(player.hand.get(choice-1));
        System.out.println("You have played " + player.hand.get(choice-1));
        player.hand.remove(choice-1);
    }


}

