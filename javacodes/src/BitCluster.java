import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given many sequences of binary code that are the same length, cluster the
 * sequences by the hamming distance between them (the number of differing bits)
 * to find the maximum number of clusters such that no two sequences are
 * separated if they contain 2 or fewer differences
 */
public class BitCluster {
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
            lines.add(line.trim());
        }
        br.close();
        String[] s = lines.toArray(new String[lines.size()]);
        numSeqs = Integer.parseInt(s[0].split(" ")[0]);
        l = Integer.parseInt(s[0].split(" ")[1]);
        String[] r = new String[numSeqs];
        for (int i = 1; i <= numSeqs; i++) {
            r[i - 1] = s[i];
        }
        return r;
    }

    /**
     * Convert binary string to array of integers, removes whitespace
     * characters.
     */
    public static int[] convertBinStr(String seq, int l) {
        int[] origSeq = new int[l];
        int count = 0;
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) != ' ') {
                origSeq[count] = seq.charAt(i) - '0';
                count++;
            }
        }
        return origSeq;
    }

    /**
     * Convert array of 0s and 1s to String with integers separated by a space.
     */
    public static String binArrayToStr(int[] binArray) {
        int l = binArray.length;
        StringBuilder sb = new StringBuilder(l + l - 1);
        for (int i = 0; i < l; i++) {
            sb.append(binArray[i]);
            if (i < l - 1)
                sb.append(" ");
        }
        String s = sb.toString();
        return s;
    }

    /**
     * Return sequences that differ by only one or two bits. Returns an array of
     * sequences as strings with spaces between integers.
     */
    public static String[] makeBitDiff(String seq, int l) {
        int[] orig = convertBinStr(seq, l);
        String[] output = new String[l + (l * (l - 1)) / 2];
        // make 1 and 2 bit different sequences
        int count = 0;
        for (int i = 0; i < l; i++) {
            for (int j = i; j < l; j++) {
                int[] newSeq = orig.clone();
                if (i != j) {
                    newSeq[i] = (newSeq[i] + 1) % 2;
                    newSeq[j] = (newSeq[j] + 1) % 2;
                } else {
                    newSeq[i] = (newSeq[i] + 1) % 2;
                }
                // convert newSeq to string and add to output
                String nSeq = binArrayToStr(newSeq);
                output[count] = nSeq;
                count++;
            }
        }
        return output;
    }

    /**
     * Test Client
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int count = 0;
        String[] seqs = readIn(args[0]);
        System.out.println("Original number of sequences: " + seqs.length);
        String[] tst = makeBitDiff(seqs[1], l);
        System.out.println("Length of diffs: " + tst.length);
        System.out.println(seqs[1]);
        System.out.println(tst[0]);
        System.out.println(tst[tst.length - 1]);

        // this is dumb, but make a hash map to get the number of unique entries
        // and then make another one with the right values
        Map<String, Integer> seqMap1 = new HashMap<String, Integer>();
        for (int i = 0; i < seqs.length; i++) {
            seqMap1.put(seqs[i], i);
        }
        numSeqs = seqMap1.size();
        // load sequences into hash map and enumerate them
        int val = 0;
        Map<String, Integer> seqMap = new HashMap<String, Integer>();
        for (String key : seqMap1.keySet()) {
            seqMap.put(key, val);
            val++;
        }
        System.out.println("There are " + numSeqs
                + " unique sequences of length " + l + ".");
        QuickUnionPathCompressionUF uf = new QuickUnionPathCompressionUF(
                numSeqs);
        System.out.println("The starting number of disjoint sets is: "
                + uf.count());
        // Iterate of array of sequences, compute similar pairs for each and see
        // if any of the similar pairs match other sequences. If they do, union
        // them
        for (String key : seqMap.keySet()) {
            String[] closeSeqs = makeBitDiff(key, l);
            if (count % 100000 == 0)
                System.out.println((double) 100 * count / numSeqs
                        + "% completed");
            for (int j = 0; j < closeSeqs.length; j++) {
                String k = closeSeqs[j];
                if (seqMap.containsKey(k)) {
                    uf.union(seqMap.get(key), seqMap.get(k));
                    count++;
                    if (uf.count() % 100000 == 0)
                        System.out.println("UF count is: " + uf.count()
                                + " and count is: " + count + ".");
                }
            }
        }
        System.out
                .println("The number max number of sets such that all pairs 2 "
                        + "units distant are joined is: " + uf.count());
        System.out.println("The number of unions calls was " + count);
    }
}