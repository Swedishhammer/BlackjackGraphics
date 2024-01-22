import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    
    String path;
    String fileName;
    public FileManager(String path){
        this.path = path;
        fileName = getFileFromPath();
    }
    
    //Creates a file for the object's path if it doesn't already exist
    public String createFile(){
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

    //Returns the full file
    public String readFile(){
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
    
    //Reads a specific line; WARNING: line numbers start at 1
    //Ex: if you want to read the 4th line, you have to call readFile(4);
    public String readFile(int line){
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
    public String writeFile(int line, String text){
        int numLines = numLinesInFile();
        if(line > numLines){
            return "No such line exists";
        }
        String writing = "";
        try {
            for(int i = 1;i <= numLines;i++){
                if(i != line){
                    writing += readFile(i);
                   
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
    private int numLinesInFile(){
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
    private String getFileFromPath(){
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
