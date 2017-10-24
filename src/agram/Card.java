package agram;

public class Card {

    int value;
    String suit;

    public int getValue() { return value; }

    public void setValue(int value) { this.value = value; }

    public String getSuit() { return suit; }

    public void setSuit(String suit) { this.suit = suit; }


    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;

    }

    // Modification from https://github.com/minneapolis-edu/Fish/blob/master/src/main/java/gofish/Card.java
    public int compareTo(Card otherCard) {

        int thisIndex = this.value;
        int otherIndex = otherCard.value;
        return otherIndex - thisIndex ;
    }

    // Reference from https://github.com/minneapolis-edu/Fish/blob/master/src/main/java/gofish/Card.java
    public String convertNumberToCardValue(int value) {
        if (value == 11) {
            return "ACE";
        }
        else {
            return Integer.toString(value);
        }
    }

    @Override
    public String toString(){

        return convertNumberToCardValue(value) + " of " + suit;
    }
}
