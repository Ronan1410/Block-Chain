import java.security.*;
import java.util.*;

public class StringUtil
{
    public static String applySha256(String str)
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
    public static byte[] applyECDSASig(PrivateKey privateKey, String input)
    {
        Signature dsa;
        byte [] output = new byte[0];
        try
        {
            dsa = Signature.getInstance("ECDSA","BC");
            dsa.initSign(privateKey);
            byte strByte[]= input.getBytes();
            dsa.update(strByte);
            byte realsig[]= dsa.sign();
            output=realsig;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return output;
    }
    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature)
    {
        try
        {
            Signature ecdsaVerify = Signature.getInstance("ECDSA","BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    public static String getStringFromKey(Key key)
    {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
