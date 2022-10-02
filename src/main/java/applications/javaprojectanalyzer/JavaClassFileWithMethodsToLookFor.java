package applications.javaprojectanalyzer;

import java.util.ArrayList;
import java.util.List;

public class JavaClassFileWithMethodsToLookFor extends  JavaClassFile {
    private List<String> methodsToLookFor;

    public JavaClassFileWithMethodsToLookFor(List<String> implementsClasses, List<String> methods,
                                             String className, String parentClassName) {
        super(implementsClasses, methods, className, parentClassName);
        methodsToLookFor = new ArrayList<>();
    }

    public List<String> getMethodsToLookFor() {
        List<String> methodsToLookForCopy = new ArrayList<>();
        methodsToLookForCopy.addAll(methodsToLookFor);
        return methodsToLookForCopy;
    }

    public void setMethodsToLookFor(List<String> methodsToLookFor) {
        this.methodsToLookFor.removeAll(methodsToLookFor);
        addMethods(methodsToLookFor);
    }

    public void addMethodsToLookFor(List<String> methodsToLookFor) {
        this.methodsToLookFor.addAll(methodsToLookFor);
    }

}
