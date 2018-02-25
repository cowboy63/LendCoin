import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

public class SHA512Miner {
	public static final byte[] hashNonce(byte[] block, int nonceIndex, byte[] nonce) throws NoSuchAlgorithmException
	{
		for(int b = 0; b + nonceIndex < block.length && b < nonce.length; b++)
		{
			block[nonceIndex + b] = nonce[b];
		}
		MessageDigest hash = MessageDigest.getInstance("SHA-512");
		return hash.digest(block);
	}
	
	public static final long countLeadingZeroes(byte[] hash)
	{
		int count = 0;
		for(int i = 0; i < hash.length; i++)
		{
			if(hash[i] != 0)
			{
				count += Integer.numberOfLeadingZeros(hash[i]);
				return count;
			}
			else
			{
				count += 8;
			}
		}
		return count;
	}
	
	public static final boolean checkNonce(byte[] hash, long difficulty)
	{
		return countLeadingZeroes(hash) >= difficulty;
	}
}
