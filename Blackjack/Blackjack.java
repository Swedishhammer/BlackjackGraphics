import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Blackjack{
    private static ArrayList<Card> deck = new ArrayList<>();
    private static ArrayList<Card> dealerHand = new ArrayList<>();
    private static ArrayList<Card> playerHand = new ArrayList<>();

    //Change to using switch cases in the future
    private static boolean quit = false;
    //0 = Win, 1 = Push[draw], 2 = Loss
    //-1 = Forfeit (quit)
    private static int gameResult;
    private static boolean writeToStatFile = true;
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String page = "MAIN MENU";
        boolean endProgram = false;
        while(!endProgram){
            switch(page){
            case("MAIN MENU"):
                System.out.println("     MAIN MENU        ");
                System.out.println();
                System.out.println("     Play Game        ");
                System.out.println("       Rules          ");
                System.out.println("     Settings         ");
                System.out.println("     Profile          ");
                page = input.nextLine().toUpperCase();
                if(page.equals("PLAY GAME")){
                    page = "GAME";
                }
                System.out.println(page);
                break;
            case("GAME"):
                System.out.println("You got to game");
                int betAmount = getBet();
                int gameResult = runGame();
                if(writeToStatFile){
                    StatsFileCommands.updateStats(gameResult, betAmount);
                }
                input = new Scanner(System.in);
                boolean responseValid = false;
                while(!responseValid){
                    System.out.println("Do you want to play again?(y/n)");
                    String response = input.nextLine().toUpperCase();
                    if(response.equals("Y") || response.equals("YES")){
                        page = "GAME";
                        responseValid = true;
                    }else if(response.equals("N") || response.equals("NO")){
                        page = "MAIN MENU";
                        responseValid = true;
                    }else{
                        System.out.println("Your response is not valid. Please use (y)es or (n)o.");
                    }
                }
                break;
            case("RULES"):
                String[] rules = {"The object is to have a hand with a total value higher than the dealer's without going over 21.",
                                  "Kings, Queens, Jacks and Tens are worth a value of 10. An Ace has the value of 1 or 11. The remaining cards are counted at face value.",
                                  "\n",
                                  "Place a bet. You and fellow players are dealt two cards each whilst the dealer is dealt one face up.",
                                  "If your first 2 cards add up to 21 (an Ace and a card valued 10), that's Blackjack! If they have any other total, decide whether you wish to 'draw' or 'stay'.",
                                  "You can continue to draw cards until you are happy with your hand.",
                                  "\n",
                                  "Once the round is done, whoever has a sum closer to, but not over, 21 than the dealer, wins. If you and the dealer have the same sum, its a tie and your bet is returned.",
                                  "Winning bets are paid out 1:1. For example, if you bet $10, you receive $20(10+10)) however, Blackjack gets paid out 3:2 which means the same $10 bet would give you $25."};
                                  
                System.out.println("                                             RULES                       ");
                for(int i = 0;i < rules.length;i++){
                    System.out.println(rules[i]);
                }
                String reponse = input.nextLine().toUpperCase();
                if(reponse.equals("MAIN MENU")){
                    page = "MAIN MENU";
                }
                break;
            case("SETTINGS"):
                //Implement Setting system
                System.out.println("You entered the settings page.");
                page = "MAIN MENU";
                break;
            case("PROFILE"):
                StatsFileCommands.printStats();
                responseValid = false;
                while(!responseValid){
                    System.out.println("Would you like to reset your stats (reset) or return to main menu (main menu)");
                    String response = input.nextLine().toUpperCase();
                    if(response.equals("RESET")){
                        //Do a double check if they truly want to
                        boolean innerResponseValid = false;
                        while(!innerResponseValid){
                            System.out.println("Are you sure you want to reset your Stats?(y/n)");
                            String innerResponse = input.nextLine().toUpperCase();
                            if(innerResponse.equals("Y") || innerResponse.equals("YES")){
                                StatsFileCommands.setUpFile();
                                page = "MAIN MENU";
                                innerResponseValid = true;
                            }else if(innerResponse.equals("N") || innerResponse.equals("NO")){
                                page = "PROFILE";
                                innerResponseValid = true;
                            }else{
                                System.out.println("Your response is not valid. Please use (y)es or (n)o.");
                            }
                        }
                        responseValid = true;
                    }else if(response.equals("MAIN MENU")){
                        page = "MAIN MENU";
                        responseValid = true;
                    }else{
                        System.out.println("Your response is not valid. Please use (y)es or (n)o.");
                    }
                }
                break;
            default:
                System.out.println("Something went wrong");
                endProgram = true;
                break;
        }
        }
        

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
                writeToStatFile = false;
            }
        }else{
            System.out.println("Your statistics file is good.");
            System.out.println("Here are your current stats:");
            StatsFileCommands.printStats();
        }
        
        if(!repairAnswer.equals("y")){
            System.out.println("Do you want to reset your statistics file?(y/n)");
            String resetResponse = input.nextLine().toLowerCase();
            if(resetResponse.equals("y")){
            StatsFileCommands.setUpFile();
        }
        }
        System.out.println("You Shouldn't be here.");
        runGame();
        

        
        input.close();
        
    }
    //This will run a round of blackjack 
    private static int runGame(){
        //Reseting variables
        quit = false;
        dealerHand.clear();
        playerHand.clear();
        Scanner gameInput = new Scanner(System.in);
        String playerAction = "";
        
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
            playerAction = gameInput.nextLine().toLowerCase();
            if(playerAction.equals("hit") || playerAction.equals("h")){
                hit();
            }else if(playerAction.equals("stand") || playerAction.equals("st")){
                stand();
            }else if(playerAction.equals("quit")){
                gameResult = -1;
                quit = true;
            }else{
                System.out.println("The action you chose is not valid.");
            }
        }
        //I am warned that not closing is a memory drain
        //however, if I do close, there seems to be problems with the main Scanner
        //So I'm not going to care about memory drain
        //gameInput.close();
        return gameResult;
    }
    private static int getBet(){
        Scanner betInput = new Scanner(System.in);
        //Placing bets
        boolean validBet = false;
        int betAmount = 0;
        int currentBalance = StatsFileCommands.getStringVal(StatsFileCommands.readFile(7));
        while(!validBet){
            System.out.println("How much do you want to bet?");
            System.out.println("You have: $" + currentBalance);
            String response = betInput.nextLine();
            //Print out how much money they have
            if(tryParse(response) == null){
                System.out.println("That is not a valid bet.");
            }else{
                betAmount = Integer.parseInt(response);
                if(betAmount < 0){
                    System.out.println("Hey! You can't bet negative money.");
                }else if(betAmount > currentBalance){
                    System.out.println("Woah there. You can't bet money you don't have.");
                }else{
                    validBet = true;
                }
            }
        }
        System.out.println("you bet: " + betAmount);
        StatsFileCommands.changeMoney(-betAmount);
        return betAmount;
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
    private static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    private static void clearScreen(){
        System.out.println(new String(new char[64]).replace("\0", "\r\n"));
    }
}