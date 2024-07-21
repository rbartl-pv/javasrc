package ai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Constants {
    // tag::prompts[]
    public static final String TEXT_PROMPT =
     "What are the great themes in literature?";
    public static final String IMAGE_PROMPT =
     "Charles Darwin in modern Toronto, cartoon style";
    // end::prompts[]
    public static final String OPENAI_KEY_FILE = ".openai-apikey.txt";

    public static String getOpenAPIKey() {
        var home = System.getProperty("user.home");
        var file = home + "/" + OPENAI_KEY_FILE;
        try {
            return Files.readAllLines(Path.of(file)).getFirst();
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to read API Key File " + file, ex);
        }
    }
}
