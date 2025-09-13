import java.util.*;
import com.google.gson.GsonBuilder;

public class BlockChain
{
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static void main(String[] args)
    {
        blockchain.clear();
        blockchain.add(new Block("Block 1","0"));
        blockchain.add(new Block("Block 2",blockchain.get(blockchain.size()-1).hash));
        blockchain.add(new Block("Block 3",blockchain.get(blockchain.size()-2).hash));

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
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

            if(!currentBlock.hash.equals(previousBlock.hash))
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
