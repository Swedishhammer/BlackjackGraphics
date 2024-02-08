import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class BlackjackBackup2{
    private static ArrayList<Card> deck = new ArrayList<>();
    private static ArrayList<Card> dealerHand = new ArrayList<>();
    private static ArrayList<Card> playerHand = new ArrayList<>();

    //Change to using switch cases in the future
    private static boolean quit = false;
    private static boolean continueToGame = false; 
    //0 = Win, 1 = Push[draw], 2 = Loss
    //-1 = Forfeit (quit)
    private static int gameResult;
    
    public static void main(String[] args) {
        boolean doNotWriteToFile = false;
        Scanner input = new Scanner(System.in);
        String repairAnswer = "";
        //Creates file if it doesn't exist
        String createFileMessage = StatsFileCommands.createFile();
        if(createFileMessage.substring(0,12).equals("File created:")){
            StatsFileCommands.setUpFile();
        }
        if(!StatsFileCommands.isStatsFileCorrect()){
            System.out.println("Your statistics file is broken.");
            System.out.println("Do you want to repair it?(y/n)");
            repairAnswer = input.nextLine().toLowerCase();
            if(repairAnswer.equals("y")){
                StatsFileCommands.setUpFile();
            }else{
                System.out.println("You're statistics will not be recorded.");
                doNotWriteToFile = true;
            }
        }else{
            System.out.println("Your statistics file is good.");
            System.out.println("Here are your current stats:");
            StatsFileCommands.printStats();
        }
        
        String playerAction = "";
        if(!repairAnswer.equals("y")){
            System.out.println("Do you want to reset your statistics file?(y/n)");
            String resetResponse = input.nextLine().toLowerCase();
            if(resetResponse.equals("y")){
            StatsFileCommands.setUpFile();
        }
        }
        
        createDeck();
        setUpGame();
        if(sumCards(dealerHand) == 21){
            displayCards(2);
            if(sumCards(playerHand) != 21){
                System.out.println("You lost. Dealer had 21.");
                gameResult = 2;
                quit = true;
            }else{
                System.out.println("It's a push.");
                gameResult = 1;
                quit = true;
            }
        }else if(sumCards(playerHand) == 21){
            displayCards(2);
            System.out.println("You win. You got 21.");
            gameResult = 0;
            quit = true;
        }
        while(!quit){
            displayCards(1);
            System.out.println("Do you want to hit or stand?");
            playerAction = input.nextLine().toLowerCase();
            if(playerAction.equals("hit")){
                hit();
            }else if(playerAction.equals("stand")){
                stand();
            }else if(playerAction.equals("quit")){
                gameResult = -1;
                quit = true;
            }else{
                System.out.println("The action you chose is not valid.");
            }
        }
        input.close();
        if(!doNotWriteToFile){
            StatsFileCommands.updateStats(gameResult);
        }
    }
    private static void hit(){
        playerHand.add(pickRandomCard());
        if(sumCards(playerHand) > 21){
            System.out.println("Sorry. You went bust.");
            gameResult = 2;
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
            gameResult = 0;
        }else if(sumCards(dealerHand) > sumCards(playerHand)){
            System.out.println("Dealer wins.");
            gameResult = 2;
        }else if(sumCards(dealerHand) < sumCards(playerHand)){
            System.out.println("You win.");
            gameResult = 0;
        }else{
            System.out.println("It's a push.");
            gameResult = 1;
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
           System.out.print(dealerHand.get(d).getFaceValue() + ", ");
        }
        System.out.print(dealerHand.get(numCardsShown - 1).getFaceValue());
        if(numCardsShown == 1){
            System.out.print(", [Hidden Card]");
        }
        
        System.out.println();
        System.out.print("Your Cards: ");
        System.out.print("(" + sumCards(playerHand) + ") ");
        for(int i = 0;i < playerHand.size()-1;i ++){
           System.out.print(playerHand.get(i).getFaceValue() + ", ");
        }
        System.out.print(playerHand.get(playerHand.size()-1).getFaceValue());
        System.out.println();
    }
    private static void createDeck(){
        String[] types = {"Spade","Heart","Diamond","Club"};
        for(int i = 1;i <= 13;i++){
            for(int j = 0; j < types.length;j++){
                Card card = new Card(i, types[j]);
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
    private static Card pickRandomCard(){
        int randomIndex = (int)(Math.random()*deck.size());
        Card card = deck.get(randomIndex);
        deck.remove(randomIndex);
        return card;
    }
    private static int sumCards(ArrayList<Card> cards){
        int sum = 0;
        boolean aceInHand = false;
        for(Card card:cards){
            sum += card.getValue(false);
            if(card.getFaceValue().equals("A")){
                aceInHand = true;
            }
        }
        if(!aceInHand){
            return sum;
        }else{
            int noAceSum = 0;
            int numAces = 0;
            for(int i = 0;i < cards.size();i++){
                if(!cards.get(i).getFaceValue().equals("A")){
                    noAceSum += cards.get(i).getValue(false);
                }else{
                    numAces ++;
                }
            }
            
            //If the sum of non-Ace cards and one 11-value Ace card is less than 21, return sum with one ace card worth 11
            if((noAceSum + 11 + (numAces - 1 * 1) < 21)){
                return (noAceSum + 11 + (numAces - 1 * 1));
            }
            //Since only one ace card can be 11 (2*11 = 22 > 21), then because we have checked if one 11-value card worked
            //and it didn't (because we are here), then we know we can return the sum with all aces being 1-value
            return noAceSum + (numAces * 1);
        }
    }
}