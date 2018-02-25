import java.security.MessageDigest;

public class Block {
	public int blockNumber;
	public long blockTime;
	public int transactionCount;
	public long difficulty;
	public byte[] minerID;
	public byte[] nonce;
	public Transaction[] transactions;
	public byte[] prevHash;
	public byte[] solutionHash;
	
	public Block()
	{
		minerID = new byte[256];
		nonce = new byte[16];
		transactions = new Transaction[6144];
		prevHash = new byte[64];
		solutionHash = new byte[64];
	}
	
	public Block(int number, long time, int transCount, long diff, String minerID, String nonce, Transaction[] txs, String prev, String solution)
	{
		this();
		blockNumber = number;
		blockTime = time;
		transactionCount = transCount;
		difficulty = diff;
		LCUtils.hex2Bytes(minerID, this.minerID);
		LCUtils.hex2Bytes(nonce, this.nonce);
		System.arraycopy(txs, 0, transactions, 0, transactionCount);
		LCUtils.hex2Bytes(prev, prevHash);
		LCUtils.hex2Bytes(solution, solutionHash);
	}
	
	public Block(byte[] rawData)
	{
		this();
	}
	
	public byte[] exportBinary()
	{
		int txSize = 1048 * transactionCount;
		byte[] result = new byte[txSize + 424];
		
		byte[] intBuffer = new byte[8];
		LCUtils.long2Bytes(blockNumber, intBuffer);
		System.arraycopy(intBuffer, 4, result, 0, 4);
		LCUtils.long2Bytes(blockTime, intBuffer);
		System.arraycopy(intBuffer, 0, result, 4, 8);
		LCUtils.long2Bytes(transactionCount, intBuffer);
		System.arraycopy(intBuffer, 4, result, 12, 4);
		LCUtils.long2Bytes(difficulty, intBuffer);
		System.arraycopy(intBuffer, 0, result, 16, 8);
		
		System.arraycopy(minerID, 0, result, 24, 256);
		System.arraycopy(nonce, 0, result, 280, 16);
		
		for(int i = 0; i < transactionCount; i++)
		{
			byte[] transactionBinary = transactions[i].exportBinary();
			System.arraycopy(transactionBinary, 0, result, 296 + i * 1048, 1048);
		}
		
		System.arraycopy(prevHash, 0, result, txSize + 296, 64);
		System.arraycopy(solutionHash, 0, result, txSize + 360, 64);
		
		return result;
	}
	
	@Override
	public String toString()
	{
		String txString = "";
		if(transactionCount > 0) txString += transactions[0].toString();
		for(int i = 1; i < transactionCount; i++) txString += ", " + transactions[i].toString();
		return String.format("{BlockNumber: %s, Time: %s, TransactionCount: %s, Diff: %s, MinerID: %s, Nonce: %s, Transactions: [%s], Prev: %s, Hash: %s}",
				blockNumber, blockTime, transactionCount, difficulty, LCUtils.bytes2Hex(minerID), LCUtils.bytes2Hex(nonce), txString, LCUtils.bytes2Hex(prevHash), LCUtils.bytes2Hex(solutionHash));
	}
	
	public byte[] computeHash() throws Exception
	{
		MessageDigest hasher = MessageDigest.getInstance("SHA-512");
		byte[] blockData = exportBinary();
		hasher.update(blockData, 0, blockData.length - 64);
		return hasher.digest();
	}
}
