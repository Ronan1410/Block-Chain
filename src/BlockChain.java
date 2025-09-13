import java.util.*;
import com.google.gson.GsonBuilder;

public class BlockChain
{
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

    public static int difficulty = 5;  // taken 5 to test
    public static float minimumTransaction= 0.1f;
    public static Wallet walletA;
    public static Wallet walletB;
    public static Transaction genesisTransaction;


    public static void main(String[] args)
    {
        blockchain.clear();
        blockchain.add(new Block("Block 1","0"));
        System.out.println("Trying to mine the first block");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Block 2",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to mine the second block");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Block 3",blockchain.get(blockchain.size()-2).hash));
        System.out.println("Trying to mine the third block");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlcokchain Validity: "+isValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println("The BlockChain: ");
        System.out.println(blockchainJson);
    }

    public static boolean isValid()
    {
        Block currentBlock;
        Block previousBlock;
        for(int i=1;i<blockchain.size();i++)
        {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);

            if(!currentBlock.hash.equals(previousBlock.calculateHash()))
            {
                System.out.println("The hashes are not equal");
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previous_hash))
            {
                System.out.println("The previous hashes are not equal");
                return false;
            }
        }
        return true;
    }
}
