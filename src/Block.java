import java.util.*;
public class Block
{
    public String hash;
    public String previous_hash;
    private String data;
    private long time_data;

    public Block(String data, String previous_hash)
    {
        this.data = data;
        this.previous_hash = previous_hash;
        this.time_data = new Date().getTime();
    }

}
