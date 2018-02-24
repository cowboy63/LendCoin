import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
	private String transactionID;
	private PublicKey sender;
	private PublicKey reciepient;
	private long ammount;
	private int loan_payment_rate;
	private int loan_payment_delay;
	String loaner_confirmation;
	String borrower_confirmation;
	private byte[] signature;
	
	private ArrayList<TransactionInput> inputs = new ArrayList<>();
	private ArrayList<TransactionOutput> outputs = new ArrayList<>();
	
	private static int sequence = 0;
	
	public Transaction(PublicKey from, PublicKey to, long amount, int lpr, int lpd, ArrayList<TransactionInput>)
	{
		
	}
	
	
	
	
}
