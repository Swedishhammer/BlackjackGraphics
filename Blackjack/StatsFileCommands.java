import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StatsFileCommands {
    private static String[] statsFileLines = {"Username: ","Games Played: ","Blackjacks: ","Wins: ","Pushes: ","Losses: ","Money: "};
    private static boolean[] isInteger =     {false       ,true            ,true          ,true    ,true      ,true      ,true};
    public static String createFile(){
        String message = "";
        try {
            File fileObj = new File("C:\\Users\\oskar\\CodingProjects\\Blackjack\\StatsFile.txt");
            if (fileObj.createNewFile()) {
                message = "File created: " + fileObj.getName();
            } else {
                message = "File already exists.";
             }
            } catch (IOException e) {
                message = "An error occurred.";
                e.printStackTrace();
            }
        return message;
    }
    public static void setUpFile(){
        System.out.println("File being set up");
        String writing = "";
        try {
            FileWriter myWriter = new FileWriter("StatsFile.txt");
            for(int i = 0;i < statsFileLines.length -1;i++){
                writing += statsFileLines[i];
                if(isInteger[i]){
                        writing += "0";
                }
                writing += "\n";
            }
            writing += statsFileLines[statsFileLines.length - 1];
            //Starting money amount
            writing += "50";
            myWriter.write(writing);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //Checks if the StatsFile is formatted correctly
    public static boolean isStatsFileCorrect(){
        boolean isCorrect = true;
        for(int i = 0;i < statsFileLines.length;i++){
            String expectedLine = statsFileLines[i];
            String actualline = readFile(i + 1);
            if(actualline.length() < expectedLine.length()){
                isCorrect = false;
                break;
            }else{
                if(!actualline.substring(0, expectedLine.length()).equals(statsFileLines[i])){
                    isCorrect = false;
                    break;
                }
            }
        }
        return isCorrect;
    }
    //This is so that the stats can be printed out in a readable format which would otherwise cause problems for backend work when reading and checking
    //TLDR: Print fancy, actual content more practical
    public static void printStats(){
        System.out.println(readFile(1));
        System.out.println(readFile(2));
        System.out.println(readFile(3));

        int wins = getStringVal(readFile(4));
        int pushes = getStringVal(readFile(5));
        int losses = getStringVal(readFile(6));
        System.out.print("Wins/Pushes/Losses: ");
        System.out.println(wins + "/" + pushes + "/" + losses);
        
        System.out.println(readFile(7));
    }
    public static String readFile(){
        String text = "";
        try {
            File myObj = new File("C:\\Users\\oskar\\CodingProjects\\Blackjack\\StatsFile.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              text += data;
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            text = "An error occurred.";
            e.printStackTrace();
          }
        return text;
    }
    //WARNING: line numbers start at 1
    //Ex: if you want to read the 4th line, you have to call readFile(4);
    public static String readFile(int line){
        String text = "";
        try {
            File myObj = new File("C:\\Users\\oskar\\CodingProjects\\Blackjack\\StatsFile.txt");
            Scanner myReader = new Scanner(myObj);
            for(int i = 1;i < line;i++){
                myReader.nextLine();
            }
            if(myReader.hasNextLine()){
                text = myReader.nextLine();
            }else{
                text = "No such line exists.";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            text = "An error occurred.";
            e.printStackTrace();
        }
        return text;
    }
    public static String writeFile(int line, String text){
        if(line > numLinesInFile()){
            return "No such line exists";
        }
        String writing = "";
        try {
            for(int i = 1;i <= numLinesInFile();i++){
                if(i != line){
                    writing += readFile(i);
                   
                }else{
                    writing += text;
                }
                writing += "\n";
            }
            FileWriter myWriter = new FileWriter("StatsFile.txt");
            myWriter.write(writing);
            myWriter.close();
            return "Successfully wrote to the file.";
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred.";
        }
    }
    public static void changeMoney(int changeVal){
        writeFile(7, changeStringVal(readFile(7), changeVal));
    }
    public static void updateStats(int gameResult, int betVal){
        String gamesPlayed = changeStringVal(readFile(2), 1);
        writeFile(2, gamesPlayed);
        //Win
        if(gameResult == 0){
            writeFile(4, changeStringVal(readFile(4), 1));
            betVal *= 2;
        }
        //Push
        if(gameResult == 1){
            writeFile(5, changeStringVal(readFile(5), 1));
            betVal *= 1;
        }
        //Loss
        if(gameResult == 2){
            writeFile(6, changeStringVal(readFile(6), 1));
            betVal *= 0;
        }
        //Blackjack
        if(gameResult == 21){
            writeFile(3, changeStringVal(readFile(3),1));
            //Changing win stat as well since blackjack is a win
            //Note: might actually be a push if both dealer and player get blackjack
            //Too lazy to code right now
            writeFile(4, changeStringVal(readFile(4), 1));
            betVal *= 1.5;
        }
        writeFile(7, changeStringVal(readFile(7),betVal));
    }
    //Changes the numerical value of a string (following ": ") based on changeVal
    private static String changeStringVal(String str, int changeVal){
        int colonIndex = str.indexOf(":");
        //(colonIndex + 2) so as to "grab" the space after the colon as well
        String finStr = str.substring(0, colonIndex + 2);
        int tempVal = Integer.parseInt(str.substring(colonIndex + 2));
        tempVal += changeVal;
        finStr += tempVal;
        return finStr;
    }
    //Returns the integer value at the end of a string (following ": ")
    public static int getStringVal(String str){
        int colonIndex = str.indexOf(":");
        int val = Integer.parseInt(str.substring(colonIndex+2));
        return val;
    }
    private static int numLinesInFile(){
        int count = 0;
        try{
            File myObj = new File("C:\\Users\\oskar\\CodingProjects\\Blackjack\\StatsFile.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                myReader.nextLine();
                count++;
            }
            return count;
        }  catch(IOException e){
            return -1;
        }
    }
}
