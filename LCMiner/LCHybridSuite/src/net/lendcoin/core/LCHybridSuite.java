package net.lendcoin.core;

import net.lendcoin.client.LCClient;
import net.lendcoin.core.netcode.PeerList;
import net.lendcoin.miner.LCMiner;

public class LCHybridSuite {
	public static final void main(final String[] args)throws Exception
	{
		System.out.println("+===+==========================================================+===+");
		System.out.println("|L  |                                                          |L  |");
		System.out.println("|   |--Lendcoin Hybrid Suite v0.1.1, made with love by DaBois--|   |");
		System.out.println("|  C|                                                          |  C|");
		System.out.println("+===+==========================================================+===+");
		
		LCUtils.logEvent("INFO - Loading last known peers...");
		try {
			PeerList.loadPeers();
		} catch (Exception e) {
			LCUtils.logEvent("ERROR - Cannot load peer file list, please verify that 'peers.dat' exists in the same directory as the client and that it is accessible.");
		}
		
		
		LCUtils.logEvent("INFO - Locating local copy of blockchain...");
		BlockChain.MAIN_CHAIN = BlockChain.deserializeBlockchain();
		{
			
			if(BlockChain.MAIN_CHAIN.containedBlock != null)
			{
				BlockChain lastBlockChain = BlockChain.MAIN_CHAIN.findLongest();
				Block lastBlock = lastBlockChain.containedBlock;
				LCUtils.logEvent("INFO - Found copy of blockchain, last block number: " + lastBlock.blockNumber + ", Last block timestamp: " + lastBlock.blockTime);
			} else {
				LCUtils.logEvent("WARNING - Cannot locate local copy of blockchain...");
			}
		}
		
		LCClient.lccMain(args);
		Transaction tv = new Transaction(LCClient.MAIN_WALLET.PUB_KEY, LCClient.MAIN_WALLET.PUB_KEY, 20, 10, 20, 100, "00", "00");
		tv.updateReceiverConfirmation(LCClient.MAIN_WALLET.PUB_KEY);
		tv.updateSenderConfirmation(LCClient.MAIN_WALLET.PUB_KEY);
		System.out.println(tv.validateReceiver() + " " + tv.validateSender());
		
		
		//LCMiner.lcmMain(args);
	}
}
