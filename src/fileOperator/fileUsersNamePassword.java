package fileOperator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.PersonsController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class fileUsersNamePassword {
    public void  jasonWriter(PersonsController perons){
        GsonBuilder builder=new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        String s=gson.toJson(perons);
        writeToFile(s,false);

    }
    public int writeToFile(String string,boolean append){
        try {
            File file =new File("resource\\"+"user.txt");
            if(!file.exists())file.createNewFile();
            FileWriter fileWriter=new FileWriter(file);
            fileWriter.write(string);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public PersonsController readFile( PersonsController persons){
        File file =new File("resource\\"+"user.txt");
        String output="";
        try {
            Scanner scanner =new Scanner(file);
            while (scanner.hasNextLine()){
                output+=scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            persons.CurrentUser=null;
            persons.isAnyOneInTheGame=false;
            persons.users=new ArrayList<>();
            e.printStackTrace();
        }
        GsonBuilder builder=new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        persons=gson.fromJson(output, PersonsController.class);
        return persons;
    }

}