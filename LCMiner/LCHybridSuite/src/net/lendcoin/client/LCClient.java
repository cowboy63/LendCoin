package net.lendcoin.client;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.lendcoin.core.BlockChain;
import net.lendcoin.core.Transaction;
import net.lendcoin.miner.LCMiner;

public class LCClient {
	public static Wallet MAIN_WALLET = new Wallet();
	
	public static final void lccMain(final String[] args)throws Exception
	{
		MAIN_WALLET.PRIV_KEY = Files.readAllBytes(Paths.get("wallet_key.dat"));
		MAIN_WALLET.PUB_KEY = Files.readAllBytes(Paths.get("wallet_address.dat"));
	}
	
	public static void send(String destination, long amount, int delay, int duration, long fees)
	{
		
	}
	
	public static void receive(Transaction inbound)
	{
		
	}
	
	public static long queryBalance()
	{
		return GlobalTotals.queryBalance(MAIN_WALLET.PUB_KEY);
	}
	
	public static long queryDebt()
	{
		BlockChain bc = BlockChain.MAIN_CHAIN.findLongest();
		return GlobalTotals.queryCredit(MAIN_WALLET.PUB_KEY, bc == null || bc.containedBlock == null ? 0 : bc.containedBlock.blockNumber);
	}
	
	static Thread miner;
	
	public static void startMining()
	{
		LCMiner.mining = true;
		miner = new Thread() {
			public void run()
			{
				try {
					new LCMiner().lcmMain(new String[0]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		miner.start();
	}
	
	public static void stopMining()
	{
		LCMiner.mining = false;
		miner = null;
	}
}
