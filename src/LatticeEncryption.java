import java.math.BigInteger;
import java.security.SecureRandom;

public class LatticeEncryption
{
    private static final int n = 256;
    private static final BigInteger q = BigInteger.valueOf(4096);
    private static final SecureRandom rand = new SecureRandom();

    public static BigInteger[] generateSecretKey()
    {
        BigInteger[] s = new BigInteger[n];
        for (int i = 0; i < n; i++)
        {
            s[i] = new BigInteger(q.bitLength(), rand).mod(q);
        }
        return s;
    }

    public static BigInteger[] generatePublicKey(BigInteger[] s)
    {
        BigInteger[] A = new BigInteger[n];
        for (int i = 0; i < n; i++)
        {
            A[i] = new BigInteger(q.bitLength(), rand).mod(q);
        }
        return A;
    }

    public static BigInteger encrypt(BigInteger[] A, BigInteger[] s, int bit)
    {
        BigInteger e = BigInteger.valueOf(rand.nextInt(3));
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < n; i++)
        {
            sum = sum.add(A[i].multiply(s[i]));
        }
        return sum.add(BigInteger.valueOf(bit)).add(e).mod(q);
    }

    public static int decrypt(BigInteger ciphertext, BigInteger[] A, BigInteger[] s)
    {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < n; i++)
        {
            sum = sum.add(A[i].multiply(s[i]));
        }
        BigInteger diff = ciphertext.subtract(sum).mod(q);
        return diff.compareTo(q.divide(BigInteger.valueOf(2))) < 0 ? 0 : 1;
    }
}