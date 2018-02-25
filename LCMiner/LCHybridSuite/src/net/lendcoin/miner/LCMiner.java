package net.lendcoin.miner;
import net.lendcoin.client.LCClient;
import net.lendcoin.core.Block;
import net.lendcoin.core.LCUtils;
import net.lendcoin.core.Transaction;

public class LCMiner {
	public static final void lcmMain(final String[] args) throws Exception
	{
		LocalMinerConfig.MINER_ADDRESS = LCUtils.bytes2Hex(LCClient.MAIN_WALLET.PUB_KEY);
		Block blc=new Block(1, 1519550136L,0,19,LocalMinerConfig.MINER_ADDRESS,"00",new Transaction[]{}, "0000102E0245443F2E882E8C5A2F4C564AE2DAE1A54C4A17E4CCC9FC3724AB260E862F037CE4B9FB844AF98F35EF6F4FD2E12BB75931550D8054D9571E93B953","00");
		
		SHA512MinerThread mainMiner = new SHA512MinerThread();
		mainMiner.block = blc;
		mainMiner.start();
		long lastCheck = System.currentTimeMillis();
		while(mainMiner.successes == 0)
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
