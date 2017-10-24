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
import java.util.Random;
import java.util.Scanner;

public class Agram {

    Scanner input = new Scanner(System.in); // Input scanner

    ArrayList<Player> players = new ArrayList<>(); // List of players
    ArrayList<Card> table = new ArrayList<>(); // List of cards on the table/in play
    ArrayList<Integer> playOrder;

    Player player;
    Deck deck = new Deck();
 //   Card cards;
    Boolean gameOver = false;
    int trick = 1;
    int trickWinner;

    public static void main(String[] args) {
        new Agram().game();
    }
    private void game() {

        setup();

        while (!gameOver) {
            playingOrder();

            for (int playerNum: playOrder) {
                if (playerNum == 0) {
                    playerTurn();
                }
                else {
                    PCTurn(playerNum);
                }
            }
            System.out.println("Trick #" + trick + "'s Winner is " + players.get(trickWinner).getName() + "!!!\n");
            trick++;
            if (trick > 6) {
                gameOver = true;
            }
        }
        System.out.println("Congratulations!!! " + players.get(trickWinner).getName() + " has won the Game!!!");


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
                    player.hand.add(deck.newCard());
                }
            }
        }
    }

    //
    private void playingOrder() {

         // Create a default playOrder list, starting from 0, 1, 2... as many as number of players
         playOrder = new ArrayList<>();
         for (int x = 0; x < players.size(); x++) {
             playOrder.add(x);
         }

         // Move the winner of the previous trick into the front, so the winner gets to start the next trick
         playOrder.remove(trickWinner);
         playOrder.add(0, trickWinner);
    }

    private void playerTurn(){
        /*
        There are 3 different scenarios:
        1.1 A card is already in play within a trick and user has matching suit as the card in play.
            user must play the card of the same suit
            1.1.1 User takes over the trick winner spot (unless someone else beats it)
            1.1.2 Card is discarded without taking over the trick winner spot
        1.2 A card is already in play within a trick, but user doesn't have any matching suit as the card in play.
            user must discard a card and cannot win the trick
        2   User is leading the trick (either by winning the previous trick or leading the very first trick)
            user may select any card within his/her hand
         */

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

            Boolean cardSelected = false;

            // Checks if player has the same suit as the play card
            for (int x = 0; x < player.getHandSize(); x++) {
                String handSuit = player.hand.get(x).getSuit();

                // 1.1 A card is already in play within a trick and user has matching suit as the card in play.
                if (handSuit.equals(playSuit)) {
                    System.out.println("You have " + playSuit + " in your hands. You must play " + playSuit + " cards.");

                    System.out.println("Select a card to play: ");
                    choice = input.nextInt();

                    // Check if user has picked numbers outside of the range or has picked a different suit card
                    while (choice < 1 || choice > player.getHandSize() || !player.hand.get(choice).getSuit().equals(playSuit)) {
                        if (!player.hand.get(choice).getSuit().equals(playSuit)) {
                            System.out.println("You have " + playSuit + " in your hands. You must play " + playSuit + " cards.");
                        } else {
                            System.out.println("Enter a number between 1 to " + player.getHandSize());
                        }

                    }
                    choice = input.nextInt();
                    // 1.1.1 Add the card to the table if it has a greater value than the one on the table, else
                    // 1.1.2 just remove from the hand and keep the strongest card on the table ArrayList
                    if (player.hand.get(x).compareTo(table.get(trick-1)) < 0) {
                        table.set(trick - 1, player.hand.get(choice - 1));
                        trickWinner = 0; // Sets the trickWinner as user
                    }
                    System.out.println("You have played " + player.hand.get(choice-1));
                    player.hand.remove(choice-1);

                    cardSelected = true; // Flag that the card has been played so it doesn't go to step 1.2
                    break;
                }
            }

            // 1.2 A card is already in play within a trick, but user doesn't have any matching suit as the card in play.
            if (!cardSelected) {
                System.out.println("You don't have " + playSuit + " in your hands. Discard a card.");
                System.out.println("Select a card to play: ");
                choice = input.nextInt();

                while (choice < 1 || choice > player.getHandSize()) {
                    System.out.println("Enter a number between 1 to " + player.getHandSize());
                    choice = input.nextInt();
                }

                // Remove the card from the hand but won't add to the table
                System.out.println("You have played " + player.hand.get(choice-1));
                player.hand.remove(choice-1);
            }
        }

        // 2 User is leading the trick
        else {
            System.out.println("No cards are in play. You may choose a suit for this trick");
            System.out.println();
            System.out.println("Select a card to play: ");
            choice = input.nextInt();

            while (choice < 1 || choice > player.getHandSize()) {
                System.out.println("Enter a number between 1 to " + player.getHandSize());
                choice = input.nextInt();
            }

            // Adds the card to the table and removes from the hand
            table.add(player.hand.get(choice-1));
            trickWinner = 0;
            System.out.println("You have played " + player.hand.get(choice-1));
            player.hand.remove(choice-1);
        }


    }


    private void PCTurn(int playerNum) {

        /*
        The same 3 scenarios:
        1.1 A card is already in play within a trick and user has matching suit as the card in play.
            1.1.1 computer will try to beat the value
            1.1.2 if fails, play the lowest value card
        1.2 A card is already in play within a trick, but user doesn't have any matching suit as the card in play.
            computer will discard the lowest value
        2   User is leading the trick (either by winning the previous trick or leading the very first trick)
            computer will play a random card
         */

        player = players.get(playerNum); // Set player to PC

        // Checks if card is in play in current trick
        if (table.size() > trick-1) {
            String playSuit = table.get(trick - 1).getSuit();
            Boolean cardSelected = false;

            // Checks if player has the same suit as the play card
            for (int i = 0; i < player.getHandSize(); i++) {
                String handSuit = player.hand.get(i).getSuit();

                // 1.1 A card is already in play within a trick and user has matching suit as the card in play.
                if (handSuit.equals(playSuit)) {
                    int playValue = table.get(trick - 1).getValue();
                    int lowestCardIndex = 0;

                    // Try to find if there is a higher value card than the playing card with the same suit in the hand
                    for (int x = 0; x < player.getHandSize(); x++) {
                        int cardValue = player.hand.get(x).getValue();
                        String cardSuit = player.hand.get(x).getSuit();

                        // 1.1.1 If found a stronger card with the same suit as the play card,
                        //          replace the current table with the new card
                        if (cardValue > playValue && cardSuit.equals(playSuit)) {
                            table.set(trick - 1, player.hand.get(x));
                            trickWinner = playerNum; // Sets the trickWinner as user
                            System.out.println(player.getName() + " has played " + player.hand.get(x));
                            player.hand.remove(x);
                            cardSelected = true;
                            return;
                        }

                        // 1.1.2 if fails, play the lowest value card
                        int lowestCardValue = 11;

                        if (cardValue < playValue && cardSuit.equals(playSuit) && cardValue < lowestCardValue) {
                            lowestCardValue = cardValue;
                            lowestCardIndex = x;
                        }
                    }
                    System.out.println(player.getName() + " has played " + player.hand.get(lowestCardIndex));
                    player.hand.remove(lowestCardIndex);
                    cardSelected = true;

                }
            }

            //1.2 A card is already in play within a trick, but user doesn't have any matching suit as the card in play.
            int lowestCardIndex = 0;

            for (int x = 0; x < player.getHandSize(); x++) {
                int cardValue = player.hand.get(x).getValue();

                int lowestCardValue = 11;

                if (cardValue < lowestCardValue) {
                    lowestCardValue = cardValue;
                    lowestCardIndex = x;
                }
            }

            if (!cardSelected) {
                System.out.println(player.getName() + " has played " + player.hand.get(lowestCardIndex));
                player.hand.remove(lowestCardIndex);
                       cardSelected = true;
            }


        }


        // 2 User is leading the trick, PC will choose one card randomly
        else {
            Random rn = new Random();
            int randomNum =  rn.nextInt(player.getHandSize());
            System.out.println(player.getName() + " has played " + player.hand.get(randomNum));
            table.add(trick - 1, player.hand.get(randomNum));
            player.hand.remove(randomNum);
            trickWinner = playerNum;
        }


    }


}

