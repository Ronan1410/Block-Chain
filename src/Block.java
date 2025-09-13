import java.util.*;
public class Block
{
    public String hash;
    public String previous_hash;
    private String data;
    private long time_data;
    int nonce;

    public Block(String data, String previous_hash)
    {
        this.data = data;
        this.previous_hash = previous_hash;
        this.time_data = new Date().getTime();

        this.hash=calculateHash();
    }

    public String calculateHash()
    {
        String caclculated_hash = StringUtil.applySSha256(previous_hash + Long.toString(time_data) + Integer.toString(nonce) + data);
        return caclculated_hash;
    }

    public void mineBlock(int difficulty)
    {
        String target =  new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(target))
        {
            nonce ++;
            hash= calculateHash();
        }
        System.out.println("Block Mined: "+ hash);
    }
}
