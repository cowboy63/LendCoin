package net.lendcoin.client;

<<<<<<< HEAD
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
=======
>>>>>>> 0e4e14312a773eba8277acbcacd8732fddb7ac9b
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
	
	public static Wallet generateWallet()throws Exception
	{
		KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
		gen.initialize(2048);
		KeyPair kp = gen.generateKeyPair();
		Wallet result = new Wallet();
		result.PRIV_KEY = ((RSAPrivateKey)kp.getPrivate()).getPrivateExponent().toByteArray();
		result.PUB_KEY = ((RSAKey)kp.getPublic()).getModulus().toByteArray();
		return result;
	}
}
