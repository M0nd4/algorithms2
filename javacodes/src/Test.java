import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    static int l;
    static int numSeqs;

    /**
     * Read in data
     * 
     * @throws IOException
     */
    public static String[] readIn(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader br = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines.toArray(new String[lines.size()]);
    }

    public static void main(String[] args) throws IOException {
        String[] seqs = readIn(args[0]);
        System.out.println(seqs[0]);
        String[] test = seqs[0].split(" ");
        l = Integer.parseInt(test[0]);

        System.out.println(l);
        String[] tst = new String[] { "0 1 0 1 0 1", "1 1 1 1 1 1",
                "0 0 0 0 0 0" };
        char[] tstChar = tst[0].toCharArray();
        int[] d = new int[6];
        int count = 0;
        for (int i = 0; i < tst[0].length(); i++) {
            if (tst[0].charAt(i) != ' ') {
                d[count] = tst[0].charAt(i) - '0';
                count++;
            }
        }
        String tst2 = Arrays.toString(d);
        System.out.println(tst2 + " " + d.length);
        StringBuilder sb = new StringBuilder(d.length);
        for (int i = 0; i < d.length; i++) {
            sb.append(i);
            if (i < d.length - 1)
                sb.append(' ');

        }
        String s = sb.toString();
        System.out.println(s);

    }
}
