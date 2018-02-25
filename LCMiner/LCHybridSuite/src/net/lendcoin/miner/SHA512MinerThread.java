package net.lendcoin.miner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import net.lendcoin.core.Block;
import net.lendcoin.core.BlockChain;
import net.lendcoin.core.LCUtils;

public class SHA512MinerThread extends Thread {
	protected Random randomNonceProvider = new Random();
	public volatile long totalHashes = 0;
	public volatile long lastHashes = 0;
	public volatile int successes = 0;
	protected boolean done = false;
	public Block block;
	public void run()
	{
		int ct = 0;
		while(!done)
		{
			try {
				long diff = block.difficulty;
				byte[] hash = hashNonce(block, randomNonceProvider);
				if(checkNonce(hash, diff))
				{
					block.solutionHash = hash;
					BlockChain.MAIN_CHAIN.appendBlock(block);
					successes++;
					LCUtils.logEvent("SUCCESS - Block mined, solution nonce: " + LCUtils.bytes2Hex(block.nonce));
					BlockChain.MAIN_CHAIN.serializeBlockchain();
					return;
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
	
	public static final byte[] hashNonce(Block block, Random nonceProvider) throws Exception
	{
		for(int i = 0; i < block.nonce.length; i++)
		{
			block.nonce[i] = (byte)nonceProvider.nextInt();
		}
		return block.computeHash();
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
