import java.io.FileWriter;
import java.io.IOException;

public class IOUtilities {
    /**
     * Method for writing output to file
     * @param output the output
     * @param fileName the file name (can be path as well)
     */
    static void writeFile(String output, String fileName) {
        try (final FileWriter writer = new FileWriter(fileName, false)) {
            writer.write(output);
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
