import java.security.*;
import java.util.*;
import com.google.gson.*;

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
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();
        Wallet coinbase = new Wallet();

        System.out.println("Private and Public Keys:");
        System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
        System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

        Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey,5,null);
        transaction.generateSignature(walletA.privateKey);

        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());
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
