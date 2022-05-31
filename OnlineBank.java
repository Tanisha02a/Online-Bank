import java.util.Scanner;

public class OnlineBank 
{

	public static void main(String[] args) 
	{
		
		// initialize Scanner
		Scanner sc = new Scanner(System.in);
		
		// initialize Bank
		Bank theBank = new Bank("the Bank of TanTan");
		
		// add user, which also creates a savings account
		System.out.println("Enter your full name: ");
		sc.nextLine();
		User aUser = theBank.addUser("Joseph", "Svoboda", "1234");
		
		// add a checking account for our user
		Account newAccount = new Account("Checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		
		// continue looping forever
		while (true) 
		{
			// stay in login prompt until successful
			curUser = OnlineBank.mainMenuPrompt(theBank, sc);
			
			// stay in main menu until user quits
			OnlineBank.printUserMenu(curUser, sc);
			
		}

	}
	
    /**
     * Print the login menu
     * @param theBank : the bank object whose acccounts to use
     * @param sc : Scanner object for user input
     * @return : authenticated user
     */
	public static User mainMenuPrompt(Bank theBank, Scanner sc) 
	{
		// initialize
		String userID;
		String pin;
		User authUser;
		
		// prompt user for ID/pin combination until correct, and continue until successful login
		do 
		{
			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());		
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();
			
			// attempt to get user object corresponding to the ID and pin combo
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) 
			{
				System.out.println("Incorrect user ID/pin combination. Please try again.");
			}
			
		} 
		while(authUser == null);
		
		return authUser;
		
	}
	
	/**
	 * Print the menu for user actions.
	 * @param theUser : the logged in User
	 * @param sc : Scanner for user input
	 */
	public static void printUserMenu(User theUser, Scanner sc) 
	{
		// print a summary of the user's accounts
		theUser.printAccountsSummary();
		
		// initialize
		int choice;
		
		// user menu
		do 
		{
			System.out.println("What would you like to do?");
			System.out.println("  1) Show account transaction history");
			System.out.println("  2) Withdraw");
			System.out.println("  3) Deposit");
			System.out.println("  4) Transfer");
			System.out.println("  5) Quit");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 5) 
			{
				System.out.println("Invalid choice. Please choose 1-5.");
			}
			
		} 
		while (choice < 1 || choice > 5);
		
		// process the choice
		switch (choice) 
		{
		
		case 1:
			OnlineBank.showTransHistory(theUser, sc);
			break;
		case 2:
			OnlineBank.withdrawFunds(theUser, sc);
			break;
		case 3:
			OnlineBank.depositFunds(theUser, sc);
			break;
		case 4:
			OnlineBank.transferFunds(theUser, sc);
			break;
		case 5:
		    
			sc.nextLine();
			break;
		}
		
		// redisplay this menu unless the user wants to quit
		if (choice != 5) 
		{
			OnlineBank.printUserMenu(theUser, sc);
		}
		
	}
	
	/**
	 * Process transferring funds from one account to another.
	 * @param theUser : the logged-in User object
	 * @param sc : the Scanner object used for user input
	 */
	public static void transferFunds(User theUser, Scanner sc) 
	{
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		// get account to transfer from
		do 
		{
			System.out.printf("Enter the number (1-%d) of the account to transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account. Please try again.");
			}
		} 
		while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get account to transfer to
		do 
		{
			System.out.printf("Enter the number (1-%d) of the account to transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account. Please try again.");
			}
		} 
		while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		// get amount to transfer
		do 
		{
			System.out.printf("Enter the amount to transfer (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) 
			{
				System.out.println("Amount must be greater than zero.");
			} 
			else if (amount > acctBal) 
			{
				System.out.printf("Amount must not be greater than balance of $.02f.\n", acctBal);
			}
		} 
		while (amount < 0 || amount > acctBal);
		
		// finally, do the transfer 
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format("Transfer from account %s", theUser.getAcctUUID(fromAcct)));
		
	}
	
	/**
	 * Process a fund withdraw from an account.
	 * @param theUser : the logged-in User object
	 * @param sc : the Scanner object used for user input
	 */
	public static void withdrawFunds(User theUser, Scanner sc) 
	{
		
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		// get account to withdraw from
		do 
		{
			System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account. Please try again.");
			}
		} 
		while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max $%.02f): $", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) 
			{
				System.out.println("Amount must be greater than zero.");
			} 
			else if (amount > acctBal) 
			{
				System.out.printf("Amount must not be greater than balance of $%.02f.\n", acctBal);
			}
		} 
		while (amount < 0 || amount > acctBal);
		
		sc.nextLine();
		
		// get a memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		// do the withdrwal
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
		
	}
	
	/**
	 * Process a fund deposit to an account.
	 * @param theUser : the logged-in User object
	 * @param sc : the Scanner object used for user input
	 */
	public static void depositFunds(User theUser, Scanner sc) 
	{
		
		int toAcct;
		double amount;
		String memo;
		
		// get account to withdraw from
		do 
		{
			System.out.printf("Enter the number (1-%d) of the account to deposit to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account. Please try again.");
			}
		} 
		while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		// get amount to transfer
		do 
		{
		    System.out.printf("Enter the amount to deposit: $");
			amount = sc.nextDouble();
			if (amount < 0) 
			{
		    System.out.println("Amount must be greater than zero.");
			} 
		} 
		while (amount < 0);
		
		sc.nextLine();
		
		// get a memo
		System.out.print("Enter a memo: ");
		memo = sc.nextLine();
		
		// do the deposit
		theUser.addAcctTransaction(toAcct, amount, memo);
		
	}
	
	/**
	 * Show the transaction history for an account.
	 * @param theUser : the logged-in User object
	 * @param sc : the Scanner object used for user input
	 */
	public static void showTransHistory(User theUser, Scanner sc) 
	{
		int theAcct;
		
		// get account whose transactions to print
		do 
		{
			System.out.printf("Enter the number (1-%d) of the account\nwhose transactions you want to see: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account. Please try again.");
			}
		} 
		while (theAcct < 0 || theAcct >= theUser.numAccounts());
		
		// print the transaction history
		theUser.printAcctTransHistory(theAcct);
		
	}

}
