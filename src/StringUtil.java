import java.security.*;
public class StringUtil
{
    public static String applySSha256(String str)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes());
            StringBuffer hexString = new StringBuffer(); // Stores hash as hexadecimal
            for (int i = 0; i < hash.length; i++)
            {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
