package applications.javaprojectanalyzer;

import java.util.List;

public class MethodFinder {

    private List<String> codeForJavaFile;
    private List<String> methods;
    int currentIndex = 0;

    public MethodFinder(List<String> codeForJavaFile) {
        this.codeForJavaFile = codeForJavaFile;
        methods = new ArrayList();
    }

    private void findAllMethods() {
        codeForJavaFile
    }

    private String findNextMethod() {
        int nextBracketOpen = findNext("(");
        int nextCurlyBracketOpen = findNext("{");
        int nextBracketClose = findNext(")");
        int nextCurlyBracketClose = findNext("}");
        // look for (
        // Check
        return "";
    }

    private int findNext(String word) {
        int index = currentIndex;
        while(index < codeForJavaFile.size()) {
            String currentLine = codeForJavaFile.get(currentIndex);
            if(currentLine.contains(word)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}