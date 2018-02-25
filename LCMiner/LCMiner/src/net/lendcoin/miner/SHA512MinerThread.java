package net.lendcoin.miner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import net.lendcoin.core.LCUtils;

public class SHA512MinerThread extends Thread {
	protected Random randomNonceProvider = new Random();
	public volatile long totalHashes = 0;
	public volatile long lastHashes = 0;
	protected boolean done = false;
	public byte[] block;
	public void run()
	{
		int ct = 0;
		while(!done)
		{
			try {
				long diff = LCUtils.concatBigEndian(block, 16, 24);
				byte[] hash = hashNonce(block, 280, 16, randomNonceProvider);
				if(checkNonce(hash, diff))
				{
					LCUtils.logEvent("SUCCESS - Block mined");
				}
				lastHashes++;
				totalHashes++;
			} catch (Exception e) {
				e.printStackTrace();
				LCUtils.logEvent("ERROR - Cannot perform block hashing; please make sure that your computer supports SHA-512 hashing");
				done = true;
			}
		}
	}
	
	public static final byte[] hashNonce(byte[] block, int nonceIndex, int nonceLength, Random nonceProvider) throws NoSuchAlgorithmException
	{
		MessageDigest hash = MessageDigest.getInstance("SHA-512");
		for(int i = 0; i < 16; i++)
		{
			block[nonceIndex + i] = (byte)nonceProvider.nextInt();
		}
		return hash.digest(block);
	}
	
	public static final long countLeadingZeroes(byte[] hash)
	{
		int count = 0;
		for(int i = 0; i < hash.length; i++)
		{
			if(hash[i] != 0)
			{
				count += Integer.numberOfLeadingZeros(hash[i] & 0xFF) - 24;
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
