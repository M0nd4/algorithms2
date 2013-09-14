import java.util.HashMap;
import java.util.Map;

public class Hashing {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String[] seqs = { "a", "b", "c", "d", "e", "f", "0 0 0", "1 1 1" };
        Map<String, Integer> seqMap = new HashMap<String, Integer>();
        for (int i = 0; i < seqs.length; i++) {
            seqMap.put(seqs[i], i);
        }
        String[] tst = { "a", "b", "Z", "0 0 0", "1 1 1", "1 0 1" };
        for (String key : seqMap.keySet())
            for (int i = 0; i < tst.length; i++) {
                if (seqMap.containsKey(tst[i]))
                    System.out.println(seqMap.get(tst[i]));
            }
    }

}
