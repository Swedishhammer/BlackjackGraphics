import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    
    public static String createFile(String path){
        String message = "";
        try {
            File fileObj = new File(path);
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
    public static String readFile(String path){
        String text = "";
        try {
            File myObj = new File(path);
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
    public static String readFile(String path, int line){
        String text = "";
        try {
            File myObj = new File(path);
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
    public static String writeFile(String path, int line, String text){
        String fileName = getFileFromPath(path);
        if(line > numLinesInFile(path)){
            return "No such line exists";
        }
        String writing = "";
        try {
            for(int i = 1;i <= numLinesInFile(path);i++){
                if(i != line){
                    writing += readFile(path,i);
                   
                }else{
                    writing += text;
                }
                writing += "\n";
            }
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(writing);
            myWriter.close();
            return "Successfully wrote to the file.";
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred.";
        }
    }
    private static int numLinesInFile(String path){
        int count = 0;
        try{
            File myObj = new File(path);
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
    private static String getFileFromPath(String path){
        int index = 0;
        boolean searching = true;
        while(searching){
            int tempIndex = path.indexOf("\\", index+1);
            if(tempIndex == -1){
                searching = false;
            }else{
                index = tempIndex;
            }
        }
        String fileName = path.substring(index+1);
        return fileName;
    }
}
