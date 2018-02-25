package net.lendcoin.miner;
import net.lendcoin.client.LCClient;
import net.lendcoin.core.Block;
import net.lendcoin.core.LCUtils;
import net.lendcoin.core.Transaction;

public class LCMiner {
	public static boolean mining = true;
	public final void lcmMain(final String[] args) throws Exception
	{
		LocalMinerConfig.MINER_ADDRESS = LCUtils.bytes2Hex(LCClient.MAIN_WALLET.PUB_KEY);
		
		SHA512MinerThread mainMiner = new SHA512MinerThread();
		mainMiner.block = null;
		mainMiner.start();
		long lastCheck = System.currentTimeMillis();
		while(mining)
		{
			long newTime = System.currentTimeMillis();
			long diff = newTime - lastCheck;
			if(diff > 5000)
			{
				String info = String.format("INFO - Current Hashrate: %,.2f H/s", (double)mainMiner.lastHashes * 1000 / diff);
				mainMiner.lastHashes = 0;
				LCUtils.logEvent(info);
				lastCheck = newTime;
			}
		}
		mainMiner.interrupt();
		return;
	}
}
