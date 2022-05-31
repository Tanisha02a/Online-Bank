import java.util.Date;

public class Transaction 
{
	
	// Amount of transaction
	private double amount;
	
	// Time and date of transaction
    // looked up how to add timestamp online
	private Date timestamp;
	
	// Memo of transaction
	private String memo;
	
	// Account where transaction occurred
	private Account inAccount;
	
	/**
     * Create new transaction
     * @param amount : the amount of the transaction
     * @param inAccount : the account the transaction belongs to
     */
	public Transaction(double amount, Account inAccount) 
	{
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}
	
	/**
     * Create new transaction with memo
     * @param amount : amount of transaction
     * @param memo : memo for transaction
     * @param inAccount : the account the transaction belongs to
     */
	public Transaction(double amount, String memo, Account inAccount) 
	{
		// call constructor first
		this(amount, inAccount);
		
		// set memo
		this.memo = memo;
	}
	
	 /**
     * Get the amount of the transaction
     * @return amount
     */
	public double getAmount() 
	{
		return this.amount;
	}
	
	/**
     * Get a string that summarizes transaction
     * @return the summary string
     */
	public String getSummaryLine() 
	{
		
		if (this.amount >= 0) 
		{
			return String.format("%s, $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
		} 
		else 
		{
			return String.format("%s, $(%.02f) : %s", this.timestamp.toString(), -this.amount, this.memo);
		}
	}
}
