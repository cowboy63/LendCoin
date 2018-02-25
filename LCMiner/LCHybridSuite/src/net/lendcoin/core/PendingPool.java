package net.lendcoin.core;

import java.util.PriorityQueue;

public final class PendingPool {
	private PendingPool()
	{
	}
	
	public static final PriorityQueue<Transaction> transactionQueue = new PriorityQueue<>((x, y) -> -Long.compare(x.amount, y.amount));
	
	public static final int dequeueBlock(Transaction[] blockTransactions)
	{
		int i = 0;
		for(; i < blockTransactions.length && !transactionQueue.isEmpty(); i++)
		{
			blockTransactions[i] = transactionQueue.poll();
		}
		return i;
	}
}
