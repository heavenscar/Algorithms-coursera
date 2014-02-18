import java.util.Iterator;

public class Subset {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> a = new RandomizedQueue<String>();
        
        while (!StdIn.isEmpty()) {
            a.enqueue(StdIn.readString());
        }
        
        Iterator<String> ai = a.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(ai.next());
        }

    }

}
