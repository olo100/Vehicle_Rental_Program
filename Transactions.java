import java.util.*;

public class Transactions {
    
    private ArrayList<Transaction> transactions;
    private int i;

    public Transactions() {
        transactions = new ArrayList<>();
        i = 0;
    }

    public void add(Transaction t) {
        transactions.add(t);
    }

    public void reset() { i = 0; }
        
    public boolean hasNext() { return i < transactions.size(); } 

    public Transaction getNext() { return transactions.get(i++); }
    
}
