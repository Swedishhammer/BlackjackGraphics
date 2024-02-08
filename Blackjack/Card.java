public class Card{
    private int faceValue;
    private String type;
    public Card(int faceValue, String type){
        this.faceValue = faceValue;
        this.type = type;
    }
    public String getFaceValue() {
        String[] faceCards = {"J","Q","K"};
        if(faceValue > 10){
            return faceCards[faceValue - 11];
        }else if(faceValue == 1){
            return "A";
        }else{
            return Integer.toString(faceValue);
        }
    }
    public int getValue(boolean ace11){
        if(faceValue > 1 && faceValue <= 10){
            return faceValue;
        }else if(faceValue > 10){
            return 10;
        }
        if(ace11 && faceValue == 1){
            return 11;
        }else{
            return 1;
        }
    }
    public String getType(){
        return type;
    }
}
