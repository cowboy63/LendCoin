package net.lendcoin.core;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

public class Transaction {
	public byte[] senderAddress;
	public byte[] receiverAddress;
	public long addedLoanFee;
	public int loanPaymentLength;
	public int loanPaymentDelay;
	public long amount;
	public byte[] senderConfirmation;
	public byte[] receiverConfirmation;
	
	public Transaction()
	{
		senderAddress = new byte[256];
		receiverAddress = new byte[256];
		senderConfirmation = new byte[256];
		receiverConfirmation = new byte[256];
	}
	
	public Transaction(String hexSenderAddr, String hexReceiverAddr, long loanFee, int paymentLength, int delay, long amt, String hexSenderConf, String hexReceiverConf)
	{
		this();
		LCUtils.hex2Bytes(hexSenderAddr, senderAddress);
		LCUtils.hex2Bytes(hexReceiverAddr, receiverAddress);
		LCUtils.hex2Bytes(hexSenderConf, senderConfirmation);
		LCUtils.hex2Bytes(hexReceiverConf, receiverConfirmation);
		addedLoanFee = loanFee;
		loanPaymentLength = paymentLength;
		loanPaymentDelay = delay;
		amount = amt;
	}
	
	public Transaction(byte[] hexSenderAddr, byte[] hexReceiverAddr, long loanFee, int paymentLength, int delay, long amt, String hexSenderConf, String hexReceiverConf)
	{
		this();
		senderAddress = hexSenderAddr;
		receiverAddress = hexReceiverAddr;
		LCUtils.hex2Bytes(hexSenderConf, senderConfirmation);
		LCUtils.hex2Bytes(hexReceiverConf, receiverConfirmation);
		addedLoanFee = loanFee;
		loanPaymentLength = paymentLength;
		loanPaymentDelay = delay;
		amount = amt;
	}
	
	public Transaction(byte[] rawData)
	{
		this();
		System.arraycopy(rawData, 0, senderAddress, 0, 256);
		System.arraycopy(rawData, 256, receiverAddress, 0, 256);
		
		addedLoanFee = LCUtils.concatBigEndian(rawData, 512, 520);
		loanPaymentLength = (int)LCUtils.concatBigEndian(rawData, 520, 524);
		loanPaymentDelay = (int)LCUtils.concatBigEndian(rawData, 524, 528);
		amount = LCUtils.concatBigEndian(rawData, 528, 536);
		
		System.arraycopy(rawData, 536, senderConfirmation, 0, 256);
		System.arraycopy(rawData, 792, receiverConfirmation, 0, 256);
	}
	
	public byte[] exportBinary()
	{
		byte[] result = new byte[1048];
		System.arraycopy(senderAddress, 0, result, 0, 256);
		System.arraycopy(receiverAddress, 0, result, 256, 256);
		
		byte[] intBuffer = new byte[8];
		LCUtils.long2Bytes(addedLoanFee, intBuffer);
		System.arraycopy(intBuffer, 0,result, 512, 8);
		LCUtils.long2Bytes(loanPaymentLength, intBuffer);
		System.arraycopy(intBuffer, 4, result, 520, 4);
		LCUtils.long2Bytes(loanPaymentDelay, intBuffer);
		System.arraycopy(intBuffer, 4, result, 524, 4);
		LCUtils.long2Bytes(amount, intBuffer);
		System.arraycopy(intBuffer, 0, result, 528, 8);
		
		System.arraycopy(senderConfirmation, 0, result, 536, 256);
		System.arraycopy(receiverConfirmation, 0, result, 792, 256);
		
		return result;
	}
	
	@Override
	public String toString()
	{
		return String.format("{Sender: %s, Receiver: %s, AddedFee: %s, PaymentLength: %s, PaymentDelay: %s, Amount: %s, SenderConfirmation: %s, ReceiverConfirmation: %s}",
				LCUtils.bytes2Hex(senderAddress), LCUtils.bytes2Hex(receiverAddress), addedLoanFee, loanPaymentLength, loanPaymentDelay, amount, LCUtils.bytes2Hex(senderConfirmation), LCUtils.bytes2Hex(receiverConfirmation));
	}
	
	public byte[] computeBodyHash() throws Exception
	{
		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
		byte[] transactionData = Arrays.copyOfRange(exportBinary(), 0, 536);
		return hasher.digest(transactionData);
	}
	
	public void updateSenderConfirmation(byte[] senderPrivateKey) throws Exception
	{
		BigInteger privKey = new BigInteger(1, senderPrivateKey);
		BigInteger pubKey = new BigInteger(1, senderAddress);
		BigInteger plainHash = new BigInteger(1, computeBodyHash());
		
		BigInteger senderConf = plainHash.modPow(privKey, pubKey);
		byte[] confBuffer = senderConf.toByteArray();
		
		for(int i = 0; i < confBuffer.length; i++)
		{
			senderConfirmation[senderConfirmation.length - i - 1] = confBuffer[confBuffer.length - i - 1];
		}
	}
	
	public void updateReceiverConfirmation(byte[] receiverPrivateKey) throws Exception
	{
		BigInteger privKey = new BigInteger(1, receiverPrivateKey);
		BigInteger pubKey = new BigInteger(1, receiverAddress);
		BigInteger plainHash = new BigInteger(1, computeBodyHash());
		
		BigInteger receiverConf = plainHash.modPow(privKey, pubKey);
		byte[] confBuffer = receiverConf.toByteArray();
		
		for(int i = 0; i < confBuffer.length; i++)
		{
			receiverConfirmation[receiverConfirmation.length - i - 1] = confBuffer[confBuffer.length - i - 1];
		}
	}
	
	public boolean validateReceiver() throws Exception
	{
		BigInteger plainHash = new BigInteger(1, computeBodyHash());
		BigInteger pubKey = new BigInteger(1, receiverAddress);
		BigInteger expected = new BigInteger(1, receiverConfirmation);
		return expected.modPow(LCUtils.STANDARD_EXPONENT, pubKey).equals(plainHash);
	}
	
	public boolean validateSender() throws Exception
	{
		BigInteger plainHash = new BigInteger(1, computeBodyHash());
		BigInteger pubKey = new BigInteger(1, senderAddress);
		BigInteger expected = new BigInteger(1, senderConfirmation);
		return expected.modPow(LCUtils.STANDARD_EXPONENT, pubKey).equals(plainHash);
	}
}
