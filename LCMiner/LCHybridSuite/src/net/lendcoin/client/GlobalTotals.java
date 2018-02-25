package net.lendcoin.client;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.lendcoin.core.Transaction;

public final class GlobalTotals {
	protected static final HashMap<BigInteger, Long> peers = new HashMap<>();
	protected static final HashMap<BigInteger, List<DebtCredit>> dcs = new HashMap<>();
	
	public boolean addTransaction(Transaction t, int block) throws Exception
	{
		if(!t.validateReceiver() || !t.validateSender()) return false;
		BigInteger sender = new BigInteger(1, t.senderAddress);
		BigInteger receiver = new BigInteger(1, t.receiverAddress);
		if(!peers.containsKey(sender)) peers.put(sender, 0L);
		if(!peers.containsKey(receiver)) peers.put(receiver, 0L);
		if(peers.get(sender) < t.amount) return false;
		else {
			peers.put(receiver, peers.get(receiver) + t.amount);
			peers.put(sender, peers.get(sender) - t.amount);
			if(t.loanPaymentDelay != 0)
			{
				if(!dcs.containsKey(sender)) dcs.put(sender, new ArrayList<DebtCredit>());
				if(!dcs.containsKey(receiver)) dcs.put(receiver, new ArrayList<DebtCredit>());
				dcs.get(sender).add(new DebtCredit(t.amount, block + t.loanPaymentDelay, t.addedLoanFee, t.loanPaymentLength));
				dcs.get(receiver).add(new DebtCredit(-t.amount, block + t.loanPaymentDelay, -t.addedLoanFee, t.loanPaymentLength));
			}
			return true;
		}
	}
	
	public void accrueFees(int blockNumber) throws Exception
	{
		for(Entry<BigInteger, List<DebtCredit>> entry : dcs.entrySet())
		{
			for(DebtCredit dcr : entry.getValue())
			{
				if(blockNumber >= dcr.start && blockNumber < dcr.start + dcr.length)
				{
					peers.put(entry.getKey(), peers.get(entry.getKey()) + (dcr.fee + dcr.principal) / dcr.length);
				}
			}
		}
	}
}
