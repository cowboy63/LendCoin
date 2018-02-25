package net.lendcoin.client;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.lendcoin.core.Transaction;

public class LCClient {
	public static final Wallet MAIN_WALLET = new Wallet();
	
	public static final void lccMain(final String[] args)throws Exception
	{
		MAIN_WALLET.PRIV_KEY = Files.readAllBytes(Paths.get("wallet_key.dat"));
		MAIN_WALLET.PUB_KEY = Files.readAllBytes(Paths.get("wallet_address.dat"));
	}
	
	public void send(String destination, long amount, int delay, int duration, long fees)
	{
		
	}
	
	public void receive(Transaction inbound)
	{
		
	}
	
	public long queryBalance()
	{
		
	}
	
	public long queryDebt()
	{
		
	}
	
	public void startMining()
	{
		
	}
	
	public void stopMining()
	{
		
	}
}
