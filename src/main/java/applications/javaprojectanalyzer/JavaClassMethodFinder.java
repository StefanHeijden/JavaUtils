package applications.javaprojectanalyzer;

import java.util.Arrays;
import java.util.List;

public class JavaClassMethodFinder {

    public List<String> setMethodsToLookFor(List<String> currentMethodsToLookFor, List<String> methodsToLookFor, List<String> methods) {
        currentMethodsToLookFor.addAll(methods);
        currentMethodsToLookFor.retainAll(methodsToLookFor);
        return currentMethodsToLookFor;
    }

    public void setMethodsToLookForThatStartWith(String methodsToLookForThatStartWith, String[] methodsToExclude) {
        for (String method : methods) {
            if(method.startsWith(methodsToLookForThatStartWith)) {
                if(!Arrays.asList(methodsToExclude).contains(method)) {
                    methodsToLookFor.add(method);
                }
            }
        }
    }

    public boolean hasMethodToLookFor(String method) {
        boolean containsMethod = methods.contains(method);
        for(String line: codeForJavaFile) {
            if(line.contains(method)) {
                containsMethod = true;
            }
        }
        return  containsMethod;
    }
}
