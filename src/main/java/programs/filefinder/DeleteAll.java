package programs.filefinder;

import programs.Program;
import utilities.UserInterface;

import java.util.Map;

public class DeleteAll implements Program {

    @Override
    public boolean run(Map<String, Object> input) {
        final String USER_DIR = (String) input.get("USER_DIR");
        final String TARGET_LOCATION = (String) input.get("TARGET_LOCATION");
        final String FILE_LOCATION = (String) input.get("FILE_LOCATION");
        final UserInterface ui = (UserInterface) input.get("UI");

        input.remove(INPUT_PARAMETER_1);
        return true;
    }
}

//    private FileFinder deleteAll() {
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(System.in));
//        getFoundFiles().forEach(System.out::println);
//        System.out.println("Delete these files?(" + foundFiles.size() + ")");
//        try {
//            if (reader.readLine().equals("y")) {
//                foundFiles.forEach(f -> {
//                            if (!f.toFile().delete()) {
//                                System.out.println("Failed to delete the file: " + f.toString());
//                            }
//                        }
//                );
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//            System.out.println("Failed to start delete");
//        }
//        return new FileFinder(new ArrayList<>());
//    }