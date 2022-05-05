package utilities;

import java.io.IOException;

public abstract class AbstractUserInputReader {
    public static final String START_MESSAGE = "What do you want to do? (type help or ? for help)";
    String inputReaderTitle = "";
    UserInterface ui = new UserInterface();

    void startReadingInputFromUser() {
        boolean keepGoing = true;
        while(keepGoing) {
            try {
                keepGoing = readSingleInputFromUser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    boolean readSingleInputFromUser(Object... input) throws IOException {
        switch (getCommandIndex(ui.getUserInput(inputReaderTitle + ": " + START_MESSAGE))) {
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
                ui.printLine(inputReaderTitle + ": Command was not found.");
                return true;
        }
    }

    abstract boolean firstMethod(Object... input);
    abstract boolean secondMethod(Object... input);
    abstract boolean thirdMethod(Object... input);
    abstract boolean fourthMethod(Object... input);
    abstract boolean fifthMethod(Object... input);
    abstract boolean sixthMethod(Object... input);
    abstract boolean seventhMethod(Object... input);
    abstract boolean eigthMethod(Object... input);
    abstract boolean ninthMethod(Object... input);
    abstract boolean tenthMethod(Object... input);

    int getCommandIndex(String action) {
        for(String[] entry : getEntries()) {
            String[] commands = entry[0].split("\\|");
            for (String command : commands) {
                if (action.equalsIgnoreCase(command.trim())) {
                    return Integer.parseInt(entry[2]);
                }
            }
        }
        ui.printLine(inputReaderTitle + ": Command was not found.");
        return 0;
    }

    void printHelpList(){
        for(String[] entry : getEntries()) {
            ui.printWithTab(entry[0], entry[1], 25);
        }
        ui.printLine("_________________________________________________________________");
    }

    String[][] getEntries() {
        return new String[][]{
                {"Command__________________", "Description_____________________________", "-1"},
                {"exit", "exit", "-1"},
                {"help", "List with commands", "0"},
        };
    }
}
