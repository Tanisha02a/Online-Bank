import java.security.MessageDigest;
import java.util.ArrayList;

public class User 
{

	// First name of user
	private String firstName;
	
	// Last name of user
	private String lastName;
	
	// ID number of user
	private String uuid;
	
	// MD5 hash of user's pin number
    // I looked this up online
	private byte pinHash[];
	
	// List of accounts of user
	private ArrayList<Account> accounts;
	
	
	/** create new user
     * @param firstName : first name of user
     * @param lastName : last name of user
     * @param pin : user's account pin number
     * @param theBank : Bank object that user is a customer of
     * 
     * I looked at this link to help code this: 
     * https://docs.oracle.com/javase/7/docs/api/java/security/MessageDigest.html
     */
	public User (String firstName, String lastName, String pin, Bank theBank) 
	{
		// set user's name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store pin's MD5 hash for security reasons
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} 
		catch (Exception e) 
		{
			System.err.println("error, caught exeption : " + e.getMessage());
			System.exit(1);
		}
		
		// get new unique ID for user
		this.uuid = theBank.getNewUserUUID();
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);
	}
	
	/**
	 * Get the user ID number
	 * @return	the uuid
	 */
	public String getUUID() 
	{
		return this.uuid;
	}
	
	/**
     * add account for user
     * @param anAcct : the account to add
     */
	public void addAccount(Account anAcct) 
	{
		this.accounts.add(anAcct);
	}
	
    /**
     * Get the number of accounts of user
     * @return number of accounts
     */
	public int numAccounts() 
	{
		return this.accounts.size();
	}
	
	/**
     * Get the balance of a particular account
     * @param acctIdx : the index of the account to use
     * @return the balance of the account
     */
	public double getAcctBalance(int acctIdx) 
	{
		return this.accounts.get(acctIdx).getBalance();
	}
	
	/**
     * Get UUID of a particular account
     * @param acctIdx : index of the account to use
     * @return the UUID of account
     */
	public String getAcctUUID(int acctIdx) 
	{
		return this.accounts.get(acctIdx).getUUID();
	}
	
	/**
     * Print transaction history for a particular amount
     * @param acctIdx : the index of the account to use
     */
	public void printAcctTransHistory(int acctIdx) 
	{
		this.accounts.get(acctIdx).printTransHistory();
	}
	
	/**
     * Add transaction to a particular amount
     * @param acctIdx : index of account
     * @param amount : amount of transaction
     * @param memo : memo of transaction
     */
	public void addAcctTransaction(int acctIdx, double amount, String memo) 
	{
		this.accounts.get(acctIdx).addTransaction(amount, memo);
	}
	
    /**
     * Check whether given pin matches real User pin
     * @param aPin : the pin to check
     * @return : whether pin is valid or not
     */
	public boolean validatePin(String aPin) 
	{
		
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} 
		catch (Exception e) 
		{
			System.err.println("error, caught exeption : " + e.getMessage());
			System.exit(1);
		}
		
		return false;
	}
	
	// Print summaries for accounts of user
	public void printAccountsSummary() 
	{
		
		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) 
		{
			System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();
		
	}
}
