import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank 
{
	
    // Name of bank
	private String name;
	
	// Account holders of bank
	private ArrayList<User> users;
	
	// Bank accounts
	private ArrayList<Account> accounts;
	
	 /**
     * Create new Bank object with empty list of users and accounts
     * @param name : name of bank
     */
	public Bank(String name) 
	{
		this.name = name;
		
		// initialize users and accounts
		users = new ArrayList<User>();
		accounts = new ArrayList<Account>();
	}
	
	/**
     * Generates unique ID for user
     * @return the uuid
     */
	public String getNewUserUUID() 
	{
		// initialize
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		// continue looping until unique ID is created
		do 
		{
			// generate number
			uuid = "";
			for (int j = 0; j < len; j++) 
			{
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			// check to make sure it is unique
			nonUnique = false;
			for (User u : this.users) 
			{
				if (uuid.compareTo(u.getUUID()) == 0) 
				{
					nonUnique = true;
					break;
				}
			}
			
		} 
		while (nonUnique);
		
		return uuid;
	}
	
	/**
     * Generates unique ID for account
     * @return the uuid
     */
	public String getNewAccountUUID() 
	{
		// initialize
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique = false;
		
		// continue looping until unique ID is created
		do 
		{
			// generate ID
			uuid = "";
			for (int j = 0; j < len; j++) 
			{
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			// check to make sure it is unique
			for (Account a : this.accounts) 
			{
				if (uuid.compareTo(a.getUUID()) == 0) 
				{
					nonUnique = true;
					break;
				}
			}
			
		} 
		while (nonUnique);
		
		return uuid;
				
	}

	/**
     * Create new User of bank
     * @param firstName : user's first name
     * @param lastName : user's last name
     * @param pin : user's pin
     * @return : new user object
     */
	public User addUser(String firstName, String lastName, String pin) 
	{
		// create new User and add it to list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		// create savings account for user and add it to list
		Account newAccount = new Account("Savings", newUser, this);
		
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;
	}
	
	/**
	 * Add an existing account for a particular User
	 * @param newAccount : the account
	 */
	public void addAccount(Account newAccount) 
	{
		this.accounts.add(newAccount);
	}
	
	/**
     * Get user object associated with specific userID/pin, if they are valid
     * @param userID : UUID of user used to log in 
     * @param pin : pin of user
     * @return : user object if login is successful, otherwise null if not successful
     */
	public User userLogin(String userID, String pin) 
	{
		// search through list of users
		for (User u : this.users) 
		{
			/// check if user ID is correct, and return User
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) 
			{
				return u;
			}
		}
		
		// if user is not found or pin is incorrect
		return null;
		
	}
	
	/**
	 * Get name of bank
	 * @return the name
	 */
	public String getName() 
	{
		return this.name;
	}

}
