
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
		LCMiner.hex2Bytes(hexSenderAddr, senderAddress);
		LCMiner.hex2Bytes(hexReceiverAddr, receiverAddress);
		LCMiner.hex2Bytes(hexSenderConf, senderConfirmation);
		LCMiner.hex2Bytes(hexReceiverConf, receiverConfirmation);
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
		
		addedLoanFee = LCMiner.concatBigEndian(rawData, 512, 520);
		loanPaymentLength = (int)LCMiner.concatBigEndian(rawData, 520, 524);
		loanPaymentDelay = (int)LCMiner.concatBigEndian(rawData, 524, 528);
		amount = LCMiner.concatBigEndian(rawData, 528, 536);
		
		System.arraycopy(rawData, 536, senderConfirmation, 0, 256);
		System.arraycopy(rawData, 792, receiverConfirmation, 0, 256);
	}
	
	public byte[] exportBinary()
	{
		byte[] result = new byte[1048];
		System.arraycopy(senderAddress, 0, result, 0, 256);
		System.arraycopy(receiverAddress, 0, result, 256, 256);
		
		byte[] intBuffer = new byte[8];
		LCMiner.long2Bytes(addedLoanFee, intBuffer);
		System.arraycopy(intBuffer, 0,result, 512, 8);
		LCMiner.long2Bytes(loanPaymentLength, intBuffer);
		System.arraycopy(intBuffer, 4, result, 520, 4);
		LCMiner.long2Bytes(loanPaymentDelay, intBuffer);
		System.arraycopy(intBuffer, 4, result, 524, 4);
		LCMiner.long2Bytes(amount, intBuffer);
		System.arraycopy(intBuffer, 0, result, 528, 8);
		
		System.arraycopy(senderConfirmation, 0, result, 536, 256);
		System.arraycopy(receiverConfirmation, 0, result, 792, 256);
		
		return result;
	}
	
	@Override
	public String toString()
	{
		return String.format("{Sender: %s, Receiver: %s, AddedFee: %s, PaymentLength: %s, PaymentDelay: %s, Amount: %s, SenderConfirmation: %s, ReceiverConfirmation: %s}",
				LCMiner.bytes2Hex(senderAddress), LCMiner.bytes2Hex(receiverAddress), addedLoanFee, loanPaymentLength, loanPaymentDelay, amount, LCMiner.bytes2Hex(senderConfirmation), LCMiner.bytes2Hex(receiverConfirmation));
	}
}
