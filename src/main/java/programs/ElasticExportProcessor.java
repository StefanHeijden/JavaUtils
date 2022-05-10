package programs;

import java.nio.file.*;
import java.io.*;
import java.util.*;
import static java.util.stream.Collectors.*;

public class ElasticExportProcessor {
    List<String> lines;
    File targetFile;

    public ElasticExportProcessor(String resourcePath, String targetPath) {
        try {
            lines = Files.readAllLines(Paths.get(resourcePath));
            createNewFile(targetPath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
      }
    }

    public ElasticExportProcessor replace(final String from, final String to){
        lines = lines
                .stream()
                .map(e -> e.replace(from, to))
                .collect(toList());
        return this;
    }

    public ElasticExportProcessor makeDistinct(){
        lines = lines
                .stream()
                .distinct()
                .collect(toList());
        return this;
    }

    public ElasticExportProcessor getSubstring(final int start, final int end) {
        lines = lines
                .stream()
                .map(e -> e.substring(start, e.length() - end))
                .collect(toList());
        return this;
    }

    public ElasticExportProcessor scimJSONRequest(String jsonVariable){
        boolean saveNextLine = false;
	    List<String> savedLines = new ArrayList<>();
        for(String line : lines) {
            if(saveNextLine) {
		String newLine = line.trim();
                savedLines.add(newLine.substring(1, newLine.length() - 1));
                saveNextLine = false;
            }
            if(line.contains(jsonVariable)) {
              saveNextLine = true;
            }
        }
        lines = savedLines;
        return this;
    }

    public void start() throws IOException {
        try(FileWriter fw = new FileWriter(targetFile)) {
            for (String line : lines) {
                fw.write(line + "\n");
            }
        }
    }

    private void createNewFile(String pathName) {
        try {
            targetFile = new File(pathName);
            if (targetFile.createNewFile()) {
                System.out.println("File created: " + targetFile.getName());
            } else {
                System.out.println("File already exists. Clear content");
                PrintWriter writer = new PrintWriter(targetFile);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
