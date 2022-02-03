/*
Group
Rameswariben Bhoi
You Pan
Albelis Becea
Description: Fortis Bank application
Date: Nov 21 2021


*/
package client;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import bus.Account;
import bus.AccountList;
import bus.Customer;
import bus.CustomerList;
import bus.DataReader;
import bus.InputMismatchException;
import bus.RaiseException;
import bus.Transaction;
import bus.TransactionList;


public class TesterCustomerSide {



//menu--input data during run-time

public static void main(String[] args)throws IOException, ClassNotFoundException, InputMismatchException, RaiseException, ParseException {


String customerNumber;
String customerPin;
AccountList accounts = new AccountList();
Customer currentCustomer;
CustomerList cusList = new CustomerList();
cusList = DataReader.readAllDataFromCustomerFile();
int keepLooping=0;
int option,operation;
boolean operationd;
String choice;
Double money=(double)0;
Transaction transc;
TransactionList translist =new TransactionList();




Scanner scan = new Scanner(System.in);
do {
	System.out.println("Please enter your Client number: ");
	customerNumber=scan.next();
	currentCustomer=cusList.Searchs(customerNumber);
	
	if(currentCustomer==null)
	{
		System.out.println("Client number not found,Try again");
	}

	else {
	System.out.println("Welcome "+currentCustomer.getcName());
	}
}while(currentCustomer==null);
do {	
	System.out.println("\nPlease enter your pin: ");
	customerPin=scan.next();
	
	if(currentCustomer.getcPin().compareTo(customerPin)!=0)
	{
		System.out.println("Wrong pin! Try again");
	}
}while(currentCustomer.getcPin().compareTo(customerPin)!=0);
System.out.println("\nYour Accounts:");
accounts=DataReader.readAllDataFromAccountFile(currentCustomer.getcId());
for(Account c : accounts.getAccountList())
{
	System.out.println(c.getaType());
}
Account selectedAc;
do {
	  do {
			System.out.println("Please input an account to do the operations: \n");//later we can change this to select in windows builder
			
			choice=scan.next();
			
			selectedAc=accounts.searchsByType(choice);
			if(selectedAc==null)
			{
				System.out.println("Wrong input");
			}
		}while(selectedAc==null);
	
	
	  do {
	System.out.println("Select Operation from Menu: \n"
						+"1.Withdraw\n"
						+"2.Deposite\n"
						+"3.Display Transactions\n"
						+"4.Quit\n");
	
	option = BankOperation.GetChoiceMenu(4, 1);
	
	switch(option)
	{
		case 1:
			do {
				
				boolean valid = false;		
				do
				{
					money=BankOperation.getAmount("Please enter amount to Withdraw: \n");
					valid =  true;	
				}while(!valid);
				
				operation=selectedAc.Withdraw(money);
				if(operation==2) {
					System.out.println("Please Enter amount bigger than 20 CAD");
					
				}
				else if(operation==1)
				{
					System.out.println("Please Enter amount lower than 500 CAD");
					
				}
				else if(operation==-1)
				{
					System.out.println("Please Enter a multiple of 20");
				
				}
				else if(operation==-2)
				{System.out.println("Insuficient Balance");
				}
				else {
				
				System.out.println("New account Balance is :"+selectedAc.getaBalance());
				transc = (new Transaction(translist.NumberOfTransactions()+1,"Operation in account","Whitdraw",money));
				translist.Add(transc);}
			}while(operation!=0);
		break;
		case 2:
			
			do {
				boolean valid = false;		
				do
				{
					money=BankOperation.getAmount("Please enter amount to Deposit: \n");
					valid =  true;	
				}while(!valid);
			
			operationd=selectedAc.Deposit(money);
			if(operationd==true)
			{
				
				System.out.println("New account Balance is :"+selectedAc.getaBalance());
				transc = (new Transaction(translist.NumberOfTransactions()+1,"Operation in account","Deposit",money));
				translist.Add(transc);
			}
			else {System.out.println("Please Enter amount between 2 CAD and 20000 CAD");
			;
			}
			}while(operationd==false);
		break;
		case 3:
		if(TransactionList.getTransactionList().isEmpty())
		{
			System.out.println("There is not Transactions to show");
		}
		else {System.out.println(translist.Display());}
		break;
		case 4:
			
			System.exit(0);
			break;
	
	}
	
	System.out.println("Select next Operation? [type 1- to continue with this account,2- select other account, 0-to quit]: ");
	keepLooping=scan.nextInt();
	} while(keepLooping==1);
}while(keepLooping==2);

if(keepLooping==0);
{
scan.close();
System.exit(0);
}

}
}


