package net.lendcoin.client;

public class DebtCredit {
	public long principal;
	public int start;
	public int length;
	public long fee;
	public DebtCredit(long p, int s, long f, int l)
	{
		principal = p;
		start = s;
		length = l;
		fee = f;
	}
}
