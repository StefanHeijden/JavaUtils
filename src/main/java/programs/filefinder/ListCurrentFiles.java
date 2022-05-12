package programs.filefinder;

import java.util.Map;

public class ListCurrentFiles extends AbstractFileFinderProgram {

    @Override
    public boolean work(Map<String, Object> input) {
        foundFiles.forEach(ui::printLine);
        return true;
    }
}
