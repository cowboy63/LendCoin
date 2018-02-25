package net.lendcoin.miner;
import net.lendcoin.core.Block;

public class SHA512Miner {
	public SHA512MinerThread[] minerThreads;
	public SHA512Miner(int threads)
	{
		minerThreads = new SHA512MinerThread[threads];
		for(int i = 0; i < threads; i++)
		{
			minerThreads[i] = new SHA512MinerThread();
		}
	}
	
	public SHA512Miner()
	{
		this(1);
	}
	
	public void beginMining()
	{
		for(SHA512MinerThread tr : minerThreads)
		{
			tr.start();
		}
	}
	
	public void stopMining()
	{
		for(SHA512MinerThread tr : minerThreads)
		{
			tr.interrupt();
		}
	}
	
	public void setTargetBlock(Block block)
	{
		stopMining();
		for(int i = 0; i < minerThreads.length; i++)
		{
			minerThreads[i] = new SHA512MinerThread();
			minerThreads[i].block = new Block(block.exportBinary());
		}
		beginMining();
	}
}
