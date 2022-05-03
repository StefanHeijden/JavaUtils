package utilities;

import java.io.IOException;

/*
    How to use:
    Implement in a class
    Override the abstract methods
    Override the getEntries to set commands for particular methods
    Override the printHelp() method if help should display the commands
 */
public interface UserInputReader {
    String START_MESSAGE = "What do you want to do? (type help or ? for help)";
    UserInterface ui = new UserInterface();

    default void startReadingInputFromUser() {
        boolean keepGoing = true;
        while(keepGoing) {
            try {
                keepGoing = readSingleInputFromUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    default boolean readSingleInputFromUser(Object... input) throws IOException {
        switch (getCommandIndex(ui.getUserInput(START_MESSAGE))) {
            case -1:
                return false;
            case 0:
                printHelpList();
                return true;
            case 1:
                return firstMethod(input);
            case 2:
                return secondMethod(input);
            case 3:
                return thirdMethod(input);
            case 4:
                return fourthMethod(input);
            case 5:
                return fifthMethod(input);
            default:
                ui.printLine("Command was not found.");
                return true;
        }
    }

    boolean firstMethod(Object... input);
    boolean secondMethod(Object... input);
    boolean thirdMethod(Object... input);
    boolean fourthMethod(Object... input);
    boolean fifthMethod(Object... input);

    default int getCommandIndex(String action) {
        for(String[] entry : getEntries()) {
            String[] commands = entry[0].split("\\|");
            for (String command : commands) {
                if (action.equalsIgnoreCase(command.trim())) {
                    return Integer.parseInt(entry[2]);
                }
            }
        }
        ui.printLine("Command was not found.");
        return 0;
    }

    default void printHelpList(){
        for(String[] entry : getEntries()) {
            ui.printWithTab(entry[0], entry[1], 25);
        }
        ui.printLine("_________________________________________________________________");
    }

    default String[][] getEntries() {
        return new String[][]{
                {"Command__________________", "Description_____________________________", "-1"},
                {"exit", "exit", "-1"},
                {"help", "List with commands", "0"},
        };
    }

}
