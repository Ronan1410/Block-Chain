import java.math.BigInteger;
import java.security.*;
import java.util.*;

public class Transaction {
    public String transactionID;
    public PublicKey sender;
    public PublicKey reciepient;
    public float value;
    public byte[] signature;
    public ArrayList<TransactionInput> inputs = new ArrayList<>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();
    public BigInteger encryptedFlag;
    private static int sequence = 0;

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs)
    {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;
    }

    private String calculateHash()
    {
        sequence++;
        return StringUtil.applySha256(StringUtil.getStringFromKey(sender)
                + StringUtil.getStringFromKey(reciepient)
                + Float.toString(value) + sequence);
    }

    public void generateSignature(PrivateKey privateKey)
    {
        String data = StringUtil.getStringFromKey(sender)
                + StringUtil.getStringFromKey(reciepient)
                + Float.toString(value);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    public void setEncryptedFlag(BigInteger[] publicKey, BigInteger[] secretKey, int bit)
    {
        this.encryptedFlag = LatticeEncryption.encrypt(publicKey, secretKey, bit);
    }

    public int getDecryptedFlag(BigInteger[] publicKey, BigInteger[] secretKey)
    {
        return LatticeEncryption.decrypt(encryptedFlag, publicKey, secretKey);
    }

    public boolean processTransaction()
    {
        if (!verifySignature())
        {
            System.out.println("Transaction Signature failed to verify");
            return false;
        }

        for (TransactionInput i : inputs)
        {
            i.UTXO = BlockChain.UTXOs.get(i.transactionOutputId);
        }

        if (getInputsValue() < BlockChain.minimumTransaction)
        {
            System.out.println("Transaction Value too low");
            return false;
        }

        float leftOver = getInputsValue() - value;
        transactionID = calculateHash();

        outputs.add(new TransactionOutput(this.reciepient, value, transactionID));
        outputs.add(new TransactionOutput(this.sender, leftOver, transactionID));

        for (TransactionOutput o : outputs)
        {
            BlockChain.UTXOs.put(o.id, o);
        }

        for (TransactionInput i : inputs)
        {
            if (i.UTXO == null) continue;
            BlockChain.UTXOs.remove(i.UTXO.id);
        }

        return true;
    }

    public float getInputsValue()
    {
        float total = 0;
        for (TransactionInput i : inputs)
        {
            if (i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }

    public float getOutputsValue()
    {
        float total = 0;
        for (TransactionOutput o : outputs)
        {
            total += o.value;
        }
        return total;
    }

    public boolean verifySignature()
    {
        String data = StringUtil.getStringFromKey(sender)
                + StringUtil.getStringFromKey(reciepient)
                + Float.toString(value);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }
}
