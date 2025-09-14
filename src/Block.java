import java.math.BigInteger;
import java.util.*;

public class Block {
    public String hash;
    public String previous_hash;
    private long time_data;
    int nonce;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    public BigInteger encryptedBlockFlag;

    public Block(String previous_hash) {
        this.previous_hash = previous_hash;
        this.time_data = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        return StringUtil.applySha256(previous_hash + Long.toString(time_data) + Integer.toString(nonce) + merkleRoot);
    }

    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty);
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calculateHash();
        }
        System.out.println("Block Mined: " + hash);
    }

    public boolean addTransaction(Transaction transaction) {
        if (transaction == null) return false;
        if (!previous_hash.equals("0")) {
            if (!transaction.processTransaction()) {
                System.out.println("Transaction Failed");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Success");
        return true;
    }
}