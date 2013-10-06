
/**
 * Java implementation of merge sort, its got a bug though
 */
public class MergeSort {

    private static boolean isSorted(int[] a, int lo, int hi) {
        for (int k = lo; k <= hi; k++) {
            if (k == hi)
                return true;
            if (a[k + 1] < a[k])
                return false;
        }
        return true;
    }

    private static void sort(int[] a, int[] aux, int lo, int hi) {
        if (hi <= lo)
            return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void sort(int[] a) {
        int[] aux = a.clone();
        sort(a, aux, 0, a.length - 1);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        for (int k = lo; k <= hi; k++)
            // copy
            aux[k] = a[k];

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)
                a[k] = aux[j++];
            if (j > hi)
                a[k] = aux[i++];
            else if (aux[j] < aux[i])
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }

        assert isSorted(a, lo, hi);
    }

    /**
     * Test client
     */
    public static void main(String[] args) {
        int[] tst = new int[] { 5, 4, 3, 6, 1, 0, 2, 7, 8, 9, 20, 89, 55, 13 };
        int[] orig = tst.clone();
        System.out.println("array length: " + tst.length);
        sort(tst);
        for (int i = 0; i < tst.length; i++)
            System.out.print(tst[i] + " ");
        System.out.println("--Should be sorted");
        for (int i = 0; i < orig.length; i++)
            System.out.print(orig[i] + " ");
        System.out.println("--Original input\n" + 5 / 3);
    }

}
