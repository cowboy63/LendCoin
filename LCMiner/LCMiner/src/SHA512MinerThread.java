import java.util.Random;

public class SHA512MinerThread extends Thread {
	protected Random randomNonceProvider = new Random();
	public volatile long totalHashes = 0;
	protected boolean done = false;
	protected byte[] block;
	public void run()
	{
		final byte[] nonceBuffer = new byte[16];
		while(!done)
		{
			long diff = LCUtils.concatBigEndian(block, 128, 144);
			for(int i = 0; i < 16; i++)
			{
				nonceBuffer[i] = (byte)randomNonceProvider.nextInt();
			}
			try {
				byte[] hash = SHA512Miner.hashNonce(block, 2240, nonceBuffer);
				if(SHA512Miner.checkNonce(hash, diff))
				{
					LCUtils.logEvent("SUCCESS - Block mined");
				}
				totalHashes++;
			} catch (Exception e) {
				LCUtils.logEvent("ERROR - Cannot perform block hashing; please make sure that your computer supports SHA-512 hashing");
			}
		}
	}
}
