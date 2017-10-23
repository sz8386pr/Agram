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

    public static void main(String[] args) {
        new Agram().game();
    }
    private void game() {

        setup();

        while (gameOver) {
            playerTurn();
            //PCTurn();
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

        System.out.println();
        player.playerHand(); // Player hand status
        System.out.println();
        System.out.println("Select a card to play: ");
        int choice = input.nextInt();

        while (choice < 1 || choice > player.getHandSize()) {
            System.out.println("Enter a number between 1 to " + player.getHandSize());
            choice = input.nextInt();
        }




        table.add(player.hand.get(choice-1));
        System.out.println("You have played " + player.hand.get(choice-1));
        player.hand.remove(choice-1);



    }

}

