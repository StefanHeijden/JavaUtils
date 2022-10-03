package applications.javaprojectanalyzer;

import java.util.Arrays;

public class JavaClassMethodFinder {

    public static JavaClassFileWithMethodsToLookFor
    setMethodsToLookForThatStartWith(String methodsToLookForThatStartWith, String[] methodsToExclude,
                                     JavaClassFileWithMethodsToLookFor javaClassFile) {
        for (String method : javaClassFile.getMethods()) {
            if(method.startsWith(methodsToLookForThatStartWith)) {
                if(!Arrays.asList(methodsToExclude).contains(method)) {
                    javaClassFile.getMethods().add(method);
                }
            }
        }
        return javaClassFile;
    }
}
