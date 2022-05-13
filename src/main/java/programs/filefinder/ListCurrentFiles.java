package programs.filefinder;

import utilities.UserInterface;

import java.util.Map;

public class ListCurrentFiles extends AbstractFileFinderProgram {

    @Override
    public boolean work(Map<String, Object> input) {
        foundFiles.forEach(UserInterface::printLine);
        return true;
    }
}
