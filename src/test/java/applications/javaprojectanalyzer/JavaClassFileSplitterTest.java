package applications.javaprojectanalyzer;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class JavaClassFileSplitterTest {

    @Test
    void test(String sourceFile, List<String> javaClassFiles) {
        String splitSourceFile = JavaClassFileFileSplitter.splitFilePerClass(Paths.get(sourceFile));
    }
}
