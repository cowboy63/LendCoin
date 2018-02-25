package net.lendcoin.client;

import java.math.BigInteger;
import java.util.Arrays;

public class Wallet {
	public byte[] PRIV_KEY;
	public byte[] PUB_KEY;
	
	@Override
	public int hashCode()
	{
		return Arrays.hashCode(PUB_KEY);
	}
	
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Wallet ? equals((Wallet)o) : false;
	}
	
	public boolean equals(Wallet o)
	{
		return Arrays.equals(PUB_KEY, o.PUB_KEY);
	}
}
