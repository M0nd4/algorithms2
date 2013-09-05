/**
 * Format the graph from text file,
 * first line is the number of jobs,
 * then each line is a job where the first number is the job weight and the 
 * second number is the job length
 */
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextReader {

    public static String readFile(String filename) {
        String content = null;
        File file = new File(filename);
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) {
        final String filename = args[0];
        System.out.println(readFile(filename));
    }
}
