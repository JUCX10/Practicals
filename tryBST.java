
class tNode {
    int data;
    tNode left, right;

    public tNode(int item) {
        data = item;
        left = right = null;
    }
}

public class tryBST {
    tNode root;

    public tryBST() {
        root = null;
    }

    // 1. Implement basic BST insertion
    public void insert(int data) {
        root = insertRec(root, data);
    }

    private tNode insertRec(tNode root, int data) {
        if (root == null) {
            root = new tNode(data);
            return root;
        }
        if (data < root.data)
            root.left = insertRec(root.left, data);
        else if (data > root.data)
            root.right = insertRec(root.right, data);

        return root;
    }

    // 2. Populate perfect BST by finding the middle and recursively inserting halves
    public void populatePerfect(int start, int end) {
        if (start > end) return;

        // Find the middle number
        int mid = start + (end - start) / 2;

        // T.insert(middle)
        insert(mid);

        // Recursively do the left and right halves
        populatePerfect(start, mid - 1);
        populatePerfect(mid + 1, end);
    }

    // 3. Test if the tree is a valid BST
    public boolean isBST() {
        return isBSTUtil(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTUtil(tNode node, int min, int max) {
        if (node == null) return true;
        if (node.data < min || node.data > max) return false;

        return isBSTUtil(node.left, min, node.data - 1) &&
                isBSTUtil(node.right, node.data + 1, max);
    }

    // 4. Implement basic BST deletion
    public void delete(int data) {
        root = deleteRec(root, data);
    }

    private tNode deleteRec(tNode root, int data) {
        if (root == null) return root;

        if (data < root.data)
            root.left = deleteRec(root.left, data);
        else if (data > root.data)
            root.right = deleteRec(root.right, data);
        else {
            // Node with only one child or no child
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;


            root.data = minValue(root.right);
            root.right = deleteRec(root.right, root.data);
        }
        return root;
    }

    private int minValue(tNode root) {
        int minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }


    public void removeEvens(int maxVal) {

        for (int i = 2; i <= maxVal; i += 2) {
            delete(i);
        }
    }


    public static double getMean(long[] data) {
        double sum = 0;
        for (long a : data) sum += a;
        return sum / data.length;
    }

    public static double getStdDev(long[] data, double mean) {
        double sum = 0;
        for (long a : data) sum += Math.pow(a - mean, 2);
        return Math.sqrt(sum / data.length);
    }

    public static void main(String[] args) {

        int n = 20;
        int maxKeys = (1 << n) - 1; // 2^n - 1
        int repetitions = 30;

        long[] popTimes = new long[repetitions];
        long[] remTimes = new long[repetitions];

        System.out.println("Running " + repetitions + " iterations with n = " + n + " (" + maxKeys + " keys)...");
        System.out.println("Please wait, this will take some time to generate reasonable stats.\n");

        for (int i = 0; i < repetitions; i++) {
            tryBST T = new tryBST();


            long startTime = System.currentTimeMillis();
            T.populatePerfect(1, maxKeys);
            long endTime = System.currentTimeMillis();
            popTimes[i] = endTime - startTime;


            if (i == 0) {
                System.out.println("Sanity Check -> isBST() returns: " + T.isBST() + "\n");
            }


            startTime = System.currentTimeMillis();
            T.removeEvens(maxKeys);
            endTime = System.currentTimeMillis();
            remTimes[i] = endTime - startTime;
        }


        double popMean = getMean(popTimes);
        double popStdDev = getStdDev(popTimes, popMean);
        double remMean = getMean(remTimes);
        double remStdDev = getStdDev(remTimes, remMean);


        System.out.printf("%-30s %-20s %-20s %-20s\n", "Method", "Number of keys n", "Average time in ms.", "Standard Deviation");
        System.out.printf("%-30s %-20d %-20.2f %-20.2f\n", "Populate tree", maxKeys, popMean, popStdDev);
        System.out.printf("%-30s %-20d %-20.2f %-20.2f\n", "Remove evens from the tree", maxKeys, remMean, remStdDev);
    }
}