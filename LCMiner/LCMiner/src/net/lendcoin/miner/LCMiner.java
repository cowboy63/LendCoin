package net.lendcoin.miner;
import net.lendcoin.core.Block;
import net.lendcoin.core.LCUtils;
import net.lendcoin.core.Transaction;

public class LCMiner {
	public static final void main(final String[] args) throws Exception
	{
		System.out.println("+===============================================+");
		System.out.println("|Lendcoin Miner v0.1.1, made with love by DaBois|");
		System.out.println("+===============================================+");
		
		LCUtils.logEvent("INFO - Parsing mining parameters...");
		LCUtils.parseParameters(args);
		
		Transaction tc = new Transaction("428acdddde84", "494902409865", 30, 10, 5, 100, "093034", "4309548D55");
		Block bk = new Block(0, 1, 1, 19, LocalMinerConfig.MINER_ADDRESS, "891234", new Transaction[] {tc}, "13", "298455");
		SHA512MinerThread mainMiner = new SHA512MinerThread();
		mainMiner.block = bk.exportBinary();
		mainMiner.start();
		long lastCheck = System.currentTimeMillis();
		while(true)
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
	}
}
