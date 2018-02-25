package net.lendcoin.core;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BlockChain {
	public static BlockChain MAIN_CHAIN;
	public Block containedBlock;
	public BlockChain prev;
	public List<BlockChain> next = new ArrayList<BlockChain>();
	public BlockChain lastKnownLongest;
	
	public BlockChain findLongest()
	{
		if(this.containedBlock == null) return this;
		long bestLength = 0;
		long bestTime = Long.MAX_VALUE;
		BlockChain best = null;
		Queue<BlockChain> space1 = new LinkedList<>();
		Queue<Long> space2 = new LinkedList<>();
		space1.add(this);
		space2.add(containedBlock.difficulty);
		while(!space1.isEmpty())
		{
			BlockChain blk = space1.remove();
			long dist = space2.remove();
			if(blk.next.size() == 0)
			{
				if(dist > bestLength)
				{
					best = blk;
					bestLength = dist;
					bestTime = blk.containedBlock.blockTime;
				} else if (dist == bestLength) {
					if(blk.containedBlock.blockTime < bestTime) {
						best = blk;
						bestLength = dist;
						bestTime = blk.containedBlock.blockTime;
					} else if (blk.containedBlock.blockTime == bestTime && Math.random() < .5) {
						best = blk;
						bestLength = dist;
						bestTime = blk.containedBlock.blockTime;
					}
				}
			} else {
				for(BlockChain nx : blk.next)
				{
					space1.add(nx);
					space2.add(dist + nx.containedBlock.difficulty);
				}
			}
		}
		lastKnownLongest = best;
		return best;
	}
	
	public boolean appendBlock(Block blk)
	{
		BlockChain last = findLongest();
		if(last.containedBlock == null) { 
			last.containedBlock = blk;
			return true;
		}
		if(last.containedBlock.blockNumber + 1 == blk.blockNumber && Arrays.equals(last.containedBlock.solutionHash, blk.prevHash))
		{
			BlockChain bc = new BlockChain();
			bc.containedBlock = blk;
			last.next.add(bc);
			return true;
		} else return false;
	}
	
	public void serializeBlockchain() throws Exception
	{
		Queue<BlockChain> space = new LinkedList<>();
		space.add(this);
		if(!new File("blockchain").exists())
		{
			new File("blockchain").mkdir();
		}
		while(!space.isEmpty())
		{
			BlockChain blk = space.remove();
			int epoch = blk.containedBlock.blockNumber >>> 10;
			if(!new File("blockchain/" + epoch).exists())
			{
				new File("blockchain/" + epoch).mkdir();
			}
			FileOutputStream output = new FileOutputStream("blockchain/" + epoch + "/" + blk.containedBlock.blockNumber % 1024 + ".blk");
			output.write(blk.containedBlock.exportBinary());
			output.close();
			for(BlockChain nx : blk.next)
			{
				space.add(nx);
			}
		}
	}
	
	public static BlockChain deserializeBlockchain() throws Exception
	{
		BlockChain head = new BlockChain();
		BlockChain tail = head;
		for(int i = 0;; i++)
		{
			int epoch = i >>> 10;
			if(!new File("blockchain/" + epoch + "/" + i + ".blk").exists())
			{
				break;
			} else {
				byte[] rawFile = Files.readAllBytes(Paths.get("blockchain/" + epoch + "/" + i + ".blk"));
				if(tail.containedBlock == null)
				{
					tail.containedBlock = new Block(rawFile);
				} else {
					BlockChain nx = new BlockChain();
					nx.containedBlock = new Block(rawFile);
					tail.next.add(nx);
					tail = nx;
				}
			}
		}
		return head;
	}
	
	@Override
	public String toString()
	{
		String res = "";
		Queue<BlockChain> space = new LinkedList<>();
		space.add(this);
		while(!space.isEmpty())
		{
			BlockChain blk = space.remove();
			res += blk.containedBlock + "\n";
			for(BlockChain bln : blk.next)
			{
				space.add(bln);
			}
		}
		return res;
	}
}
