
public class Entry 
{
	String lender;
	String borrower;
	long loan_fee;
	int loan_payment_rate;
	int loan_payment_delay;
	long ammount;
	String loaner_confirmation;
	String borrower_confirmation;
	
	public Entry(String lender, String borrower, long loan_fee, int loan_payment_rate, int loan_payment_delay, long ammount, String loaner_confirmation, String borrower_confirmation)
	{
		this.lender = lender;
		this.borrower = borrower;
		this.loan_fee = loan_fee;
		this.loan_payment_rate = loan_payment_rate;
		this.loan_payment_delay = loan_payment_delay;
		this.ammount = ammount;
		this.loaner_confirmation = loaner_confirmation;
		this.borrower_confirmation = borrower_confirmation;
	}
}
