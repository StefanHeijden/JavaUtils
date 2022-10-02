package applications.javaprojectanalyzer;

import java.util.ArrayList;
import java.util.List;

public class JavaClassMethodsUtils {

    public static List<String> extractMethodFromLine(String line, List<String> methods) {
        if(line.contains("{")) {
            if(!JavaClassFileUtils.containsKeyWord(line)) {
                String[] codePieces = line.split("\\s+");
                if(codePieces.length > 0) {
                    String methodName = "";
                    for(int i=0;i<codePieces.length && !methodName.contains("(");i++) {
                        methodName = codePieces[i];
                    }
                    if(methodName.contains("(")) {
                        methodName = methodName.substring(0, methodName.indexOf("("));
                    }
                    methods.add(methodName);
                }
            }
        }
        return methods;
    }

}
