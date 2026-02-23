import java.io.*;
import java.util.*;

// Default visibility (package-private)
class Node {
    int key;
    String data;

    public Node(int key, String data) {
        this.key = key;
        this.data = data;
    }
}

public class SearchComparison {

    // FIX: Remove 'public' from methods to match 'Node' visibility
    static String linearSearch(Node[] array, int target) {
        for (Node node : array) {
            if (node.key == target) return node.data;
        }
        return null;
    }

    static String binarySearch(Node[] array, int target) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid].key == target) return array[mid].data;
            if (array[mid].key < target) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    }

    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<>();

        try (Scanner sc = new Scanner(new File("ulysses.numbered"))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    nodeList.add(new Node(Integer.parseInt(parts[0]), parts[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: ulysses.numbered not found.");
            return;
        }

        Node[] nodes = nodeList.toArray(new Node[0]);
        if (nodes.length == 0) return;

        int testKey = nodes[nodes.length / 2].key;

        // Measures and prints to clear "Result ignored" warnings
        long start = System.nanoTime();
        String res1 = linearSearch(nodes, testKey);
        long linearTime = System.nanoTime() - start;

        Arrays.sort(nodes, Comparator.comparingInt(n -> n.key));

        start = System.nanoTime();
        String res2 = binarySearch(nodes, testKey);
        long binaryTime = System.nanoTime() - start;

        System.out.println("Linear Search: " + (res1 != null) + " (" + linearTime + " ns)");
        System.out.println("Binary Search: " + (res2 != null) + " (" + binaryTime + " ns)");
    }
}
