import java.util.ArrayList;
import java.util.Scanner;
public class BlackjackBackup{
    private static ArrayList<CardBackup> deck = new ArrayList<CardBackup>();
    private static ArrayList<CardBackup> dealerHand = new ArrayList<CardBackup>();
    private static ArrayList<CardBackup> playerHand = new ArrayList<CardBackup>();
    private static boolean quit = false;
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        String playerAction = "";
        createDeck();
        setUpGame();
        if(sumCards(dealerHand) == 21){
            displayCards(2);
            if(sumCards(playerHand) != 21){
                System.out.println("You lost. Dealer had 21.");
                quit = true;
            }else{
                System.out.println("It's a push.");
                quit = true;
            }
        }else if(sumCards(playerHand) == 21){
            displayCards(2);
            System.out.println("You win. You got 21.");
            quit = true;
        }
        while(!quit){
            displayCards(1);
            System.out.println("Do you want to hit or stand?");
            playerAction = input.nextLine().toUpperCase();
            if(playerAction.equals("HIT")){
                hit();
            }else if(playerAction.equals("STAND")){
                stand();
            }else if(playerAction.equals("QUIT")){
                quit = true;
            }else{
                System.out.println("The action you chose is not valid.");
            }
        }
        input.close();
    }
    private static void hit(){
        playerHand.add(pickRandomCard());
        if(sumCards(playerHand) > 21){
            System.out.println("Sorry. You went bust.");
            quit = true;
        }else{
        }
    }
    private static void stand(){
        displayCards(dealerHand.size());
        while(sumCards(dealerHand) < 17){
            dealerHand.add(pickRandomCard());
            displayCards(dealerHand.size());
        }

        if(sumCards(dealerHand) > 21){
            System.out.println("You win! Dealer went bust.");
        }else if(sumCards(dealerHand) > sumCards(playerHand)){
            System.out.println("Dealer wins.");
        }else if(sumCards(dealerHand) < sumCards(playerHand)){
            System.out.println("You win.");
        }else{
            System.out.println("It's a push.");
        }
        quit = true;

    }
    //Note: numCardsShown only applies to the dealer
    private static void displayCards(int numCardsShown){
        System.out.print("Dealer's Cards: ");
        if(numCardsShown != 1){
            System.out.print("(" + sumCards(dealerHand) + ") ");
        }
        for(int d = 0;d < numCardsShown - 1;d ++){
           System.out.print(dealerHand.get(d).getValue() + ", ");
        }
        System.out.print(dealerHand.get(numCardsShown - 1).getValue());
        if(numCardsShown == 1){
            System.out.print(", [Hidden Card]");
        }
        
        System.out.println();
        System.out.print("Your Cards: ");
        System.out.print("(" + sumCards(playerHand) + ") ");
        for(int i = 0;i < playerHand.size()-1;i ++){
           System.out.print(playerHand.get(i).getValue() + ", ");
        }
        System.out.print(playerHand.get(playerHand.size()-1).getValue());
        System.out.println();
    }
    private static void createDeck(){
        String[] types = {"Spade","Heart","Diamond","Club"};
        for(int i = 1;i <= 13;i++){
            for(int j = 0; j < types.length;j++){
                CardBackup card = new CardBackup(i, types[j]);
                deck.add(card);
            }
        }
    }
    private static void setUpGame(){
        playerHand.add(pickRandomCard());
        dealerHand.add(pickRandomCard());
        playerHand.add(pickRandomCard());
        dealerHand.add(pickRandomCard());
    }
    private static CardBackup pickRandomCard(){
        int randomIndex = (int)(Math.random()*deck.size());
        CardBackup card = deck.get(randomIndex);
        deck.remove(randomIndex);
        return card;
    }
    private static int sumCards(ArrayList<CardBackup> cards){
        int sum = 0;
        for(CardBackup card:cards){
            sum += card.getValue();
        }
        return sum;
    }
}