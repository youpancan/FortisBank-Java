/*
Group
Rameswariben Bhoi
You Pan
Albelis Becea
Description: Fortis Bank application
Date: Dec 12 2021


*/
package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import bus.Account;
import bus.AccountNumberComparator;
import bus.CheckingAccount;
import bus.CreditAccount;
import bus.Customer;
import bus.CustomerIDComparator;
import bus.DataReaderSingleton;
import bus.InputMismatchException;
import bus.RaiseException;
import bus.SavingAccount;
import bus.SingletonBank;

public class Singleton_TesterManager {

	public static void DisplayMenu() {
		System.out.println("1 - Add Customer");
		System.out.println("2 - Display Customer List");
		System.out.println("3 - Display Customer List sort by client ID");
		System.out.println("4 - Search Customer");
		System.out.println("5 - Delete Customer");
		System.out.println("6 - Display List of Accounts");
		System.out.println("7 - Display List of Accounts sort by Account Number");
		System.out.println("8 - Display List of Customers from ser file");
		System.out.println("9 - Display List of Accounts from ser file");
		System.out.println("10 - Exit");
	}
	
	public static void main(String[] args) throws ClassNotFoundException, IOException, InputMismatchException, RaiseException {
		
		
		Scanner scan = new Scanner(System.in);
		String accnumber,status;
		int openOtherOption;
		
		Customer cust;
		
		status="Active";
		
		Boolean isExit= false;
		int choice;
		String customerNumber;
		
		

		do {
			DisplayMenu();
			
			choice = BankOperation.GetChoiceMenu(10, 1);
			
			switch(choice)
			{
				case 1:
					do {
						System.out.println("Register a new Customer: ");
						cust = new Customer();
						boolean valid = false;		
						do
						{
							try
							{
								System.out.println("Enter Customer Identification Number: ");
								cust.setcId(scan.next());
								valid =  true;				
							}
							catch(RaiseException ex)
							{
								System.out.println(ex.getMessage());				
							}	
						}while(!valid);
					
						
						valid = false;
						scan.nextLine();
						do
						{
							try
							{
							System.out.println("Enter Customer Name and Last Name: ");
								
								cust.setcName(scan.nextLine());
								
								valid =  true;				
							}
							catch(RaiseException ex)
							{
								System.out.println(ex.getMessage());				
							}
						}while(!valid);
					
						
						valid = false;
						
						do
						{
							try
							{
								System.out.println("Enter Customer Pin: ");
								cust.setcPin(scan.next());
								valid =  true;				
							}
							catch(RaiseException ex)
							{
								System.out.println(ex.getMessage());				
							}	
						}while(!valid);
					
						
						valid = false;
						

						
						System.out.println("Select Customer account type: ");

						do {
							int selectType=0;
							do {
								System.out.println("1-Checking\n2-Saving\n3-Credit");
								selectType = BankOperation.GetChoiceMenu(3, 1);
								valid=true;
							}while(!valid);
							
							valid=false;
							System.out.println("Assing Account number: ");
							accnumber=scan.next();
							switch(selectType)
							{
							//	USING THE SINGLETON
							case 1:
								SingletonBank.getSingleInstance().addCheckingAcc(cust.getcId(), new CheckingAccount(accnumber,status,cust.getcId()));
							//customerAccs.Add(new CheckingAccount(accnumber,status,cust.getcId()));
							break;
	
							case 2:
								SingletonBank.getSingleInstance().addSavingAcc(cust.getcId(), new SavingAccount(accnumber,status,cust.getcId()));
							//customerAccs.Add(new SavingAccount(accnumber,status,cust.getcId()));
							case 3:
								SingletonBank.getSingleInstance().addCreditAcc(cust.getcId(),new CreditAccount(accnumber,status,cust.getcId()));
							//customerAccs.Add(new CurrencyAccount(accnumber,status,cust.getcId()));
							
							}
	
							do {
								System.out.println("Do you want to open another account for this customer? [1 open new account, 2 continue] ");
								openOtherOption=BankOperation.GetChoiceMenu(2, 1);
								valid=true;
							}while(!valid);
							valid=false;
							}while(openOtherOption==1);
							
							cust.setcStatus(status);
							
							//	USING THE SINGLETON
							
							SingletonBank.getSingleInstance().addCustomer(cust.getcId(), cust);
							
							
							System.out.println("Register Completed! ");
	
							
	
							do {
							System.out.println("Do you want to register another customer? [2 add new customer, 1 exit to menu] ");
							openOtherOption=BankOperation.GetChoiceMenu(2, 1);
							valid=true;
							}while(!valid);
						}while(openOtherOption==2);
						break;
				case 2:
					
					System.out.println("List Of Customers ");
				
					if(SingletonBank.getSingleInstance().getlistOfCustomers().values().isEmpty()) {
						System.out.println("There is not customers on the list\n");
					}
					else {
						for(Customer aCust : SingletonBank.getSingleInstance().getlistOfCustomers().values()) {
							System.out.println(aCust);
						}
					}
					
					break;
				case 3:
					
					System.out.println("List Of Customers Sort by Customer ID Number");
				
					if(SingletonBank.getSingleInstance().getlistOfCustomers().values().isEmpty()) {
						System.out.println("There is not customers on the list\n");
					}
					else {
						ArrayList<Customer> sortingList = new ArrayList<Customer>(SingletonBank.getSingleInstance().getlistOfCustomers().values());
						
						
						Collections.sort(sortingList, new CustomerIDComparator());
						for(Customer aCust : sortingList) {
							System.out.println(aCust);
						}
					}
					
					break;
				case 4:
					System.out.println("Enter Your Number for search :");
					customerNumber = scan.next();
					
					cust = SingletonBank.getSingleInstance().searchCustomerList(customerNumber);
					if(cust == null)
					{
						System.out.println("No data found");
					}
					else {
						System.out.println(cust.toString());
					}
					
					break;
		
				case 5:
					String cusNumber;
					System.out.println("Remove a Customer: ");
					
					System.out.println("Enter the identifier of the customer to remove: ");
					cusNumber=scan.next();
					
					cust=SingletonBank.getSingleInstance().searchCustomerList(cusNumber);
					if(cust!=null)
					{
						
						if(SingletonBank.getSingleInstance().remove(cust))
						{
							System.out.println(" This customer has been removed");
						}
					}
					else {System.out.println("Wrong customer identifier doesn't exist");}
					
					break;
				case 6:
					System.out.println("List Of Accounts");
					
					if(SingletonBank.getSingleInstance().getlistOfAcounts().values().isEmpty()) {
						System.out.println("There is no accounts for this customer\n");
					}
					else {
						for(Account aAcc : SingletonBank.getSingleInstance().getlistOfAcounts().values()) {
							System.out.println(aAcc);
						}
					}
					break;
				
				case 7:
					System.out.println("List Of Accounts");
					
					if(SingletonBank.getSingleInstance().getlistOfAcounts().values().isEmpty()) {
						System.out.println("There is no accounts for this customer\n");
					}
					else {
						ArrayList<Account> sortingList = new ArrayList<Account>(SingletonBank.getSingleInstance().getlistOfAcounts().values());
						
						
						Collections.sort(sortingList, new AccountNumberComparator());
						for(Account aAcc : sortingList) {
							System.out.println(aAcc);
						}
					}
					break;
				case 8:
					System.out.println("\n READ CUSTOMERS FROM SER FILE");	
					System.out.println("\n List of Customers from Hard Drive");
					for(Customer aCust : DataReaderSingleton.readCustomersFromSerializedFile().values())
					{
						System.out.println(aCust);			
					}
					break;
				case 9:
					System.out.println("\n READ ACCOUNTS FROM SER FILE");	
					System.out.println("\n List of Accounts from Hard Drive");
					for(Account aAcc : DataReaderSingleton.readAccountFromSerializedFile().values())
					{
						System.out.println(aAcc);			
					}
					break;
				case 10:
					isExit = true;
				
										
					DataReaderSingleton.writeCustomerToSerializedFile((HashMap<String, Customer>)(SingletonBank.getSingleInstance().getlistOfCustomers()));  
					DataReaderSingleton.writeAccountToSerializedFile((HashMap<String, Account>)(SingletonBank.getSingleInstance().getlistOfAcounts()));
					  System.exit(0);		
					break;
			}
		}while(isExit == false);
		
		scan.close();
	}
		
		
		
		
	}


