package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public String getUserInput(String message) {
        try {
            System.out.println(message);
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "exit";
    }

    public void printWord(Object message) {
        System.out.print(message);
    }

    public void printLine(Object message) {
        System.out.println(message);
    }

    public void printWithTab(String firstMessage, String secondMessage, int tabLength){
        printWord(firstMessage);
        for(int i = firstMessage.length(); i< tabLength; i++) {
            System.out.print(" ");
        }
        printWord(secondMessage);
    }
}
