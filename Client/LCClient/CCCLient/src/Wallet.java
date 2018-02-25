import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet 
{
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	
	public Wallet()
	{
		generateKeyPair();
	}
	
	
	public long getBalance() throws Exception
	{
		long amount = 0;
		if(Blockchain.isChainValid())
		{
			for (int i = 0; i < Blockchain.blockchain.size(); i++) 
			{
				for(int x =0; x < Blockchain.blockchain.get(i).transactionCount; x++)
				{
					// if you are the recipeint add amount
					if(equalB(Blockchain.blockchain.get(i).transactions[x].receiverAddress, publicKey.getEncoded()))
					{
						amount += Blockchain.blockchain.get(i).transactions[x].amount;
					}
					// if you are sendser subtract
					if(equalB(Blockchain.blockchain.get(i).transactions[x].senderAddress, publicKey.getEncoded()))
					{
						amount -= Blockchain.blockchain.get(i).transactions[x].amount;
					}					
				}
			}
		}
		
		return amount;
	}
	
	public boolean equalB(byte[] ar, byte[] ar2)
	{
		for(int i =0; i < ar.length; i++)
		{
			if(ar[i]!=ar2[i])
			{
				return false;
			}
		}
		return true;
	}
	
	
	public Transaction transferFunds(PublicKey recipient, long amount, long loanFee, int loanPaymentLength, int loanPaymentDelay) throws Exception
	{
		if(getBalance() < amount)
		{
			System.out.println("NOT ENOUGH FUNDS");
			return null;
		}
		
		Transaction temp = new Transaction(LCUtils.bytes2Hex(publicKey.getEncoded()), LCUtils.bytes2Hex(recipient.getEncoded()), loanFee, loanPaymentLength, loanPaymentDelay, amount, "", "");
		
		return temp;
	}
	
	
	
	
	
	
	
	public void generateKeyPair()
	{
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			  KeyPair keys = keyGen.genKeyPair();
			
			privateKey = keys.getPrivate();
			publicKey = keys.getPublic();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	
}
