import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileArrayProvider {

    public static String[] readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    public static void main(String[] args) throws IOException {
        final String filename = args[0];
        File file = new File(filename);
        System.out.println(file.length());
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();
        String[] valueStr = new String(bytes).trim().split("\\s+");
        int[] tall = new int[valueStr.length];
        for (int i = 0; i < valueStr.length; i++)
            tall[i] = Integer.parseInt(valueStr[i]);
        System.out.println(Arrays.asList(tall[1]));
        System.out.println(tall.length);
        String[] tst = readLines(filename);

        System.out.println(tst[1]);
    }
}