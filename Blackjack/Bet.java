public class Bet {
    int betAmount;
    boolean canDouble;
    public Bet(int betAmount, boolean canDouble){
        this.betAmount = betAmount;
        this.canDouble = canDouble;
    }
    
    public int getBetAmount(){
        return betAmount;
    }
    public void doubleBet(){
        betAmount *= 2;
    }
    public boolean getCanDouble(){
        return canDouble;
    }
}
