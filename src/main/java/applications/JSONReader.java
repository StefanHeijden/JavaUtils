package applications;

import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONReader {
    public JSONReader (Path filePath) throws IOException {
        InputStream is = Files.newInputStream(filePath.toFile().toPath());
        String jsonTxt = IOUtils.toString(is, StandardCharsets.UTF_8.toString());
        JSONObject json = new JSONObject(jsonTxt);
        json.getJSONArray("values");
    }

}
