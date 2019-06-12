
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

import com.etrade.etws.account.Account;
import com.etrade.etws.account.AccountBalanceResponse;
import com.etrade.etws.account.AccountListResponse;
import com.etrade.etws.account.AccountPositionsRequest;
import com.etrade.etws.account.AccountPositionsResponse;
import com.etrade.etws.sdk.client.ClientRequest;
import com.etrade.etws.sdk.client.AccountsClient;
import com.etrade.etws.sdk.common.ETWSException;


public class Accounts {
	
	public static List<Account> getAccounts(){
		
		AccountsClient account_client = getAccountsClient();
		
		try{
			
			AccountListResponse response = account_client.getAccountList();
			List<Account> alist = response.getResponse();
			return alist;
			
		}//end of try
		
		catch(Exception e){
			
			System.out.println("Error encountered in getting Accounts!");
			
			System.exit(-1);
			
		}//end of catch
		
		return null; //return in try or ends in catch - never returns null
		
	}//end of getAccounts
	
	private static AccountsClient getAccountsClient(){
		
		ClientRequest r = Login.getRequest();
		
		return new AccountsClient(r);
		
	}//end of getAccountsClient
	
	public static AccountBalanceResponse getAccountBalance(Account a){
		
		return getAccountBalance(a.getAccountId());
		
	}//end of getAccountBalance
	
	public static AccountBalanceResponse getAccountBalance(String accountID){
	
		try{
			
			return getAccountsClient().getAccountBalance(accountID);
			
		}//end of try
		
		catch(IOException | ETWSException e){
			
			System.out.println("Error encountered in getting Account Balance!");
			
			System.exit(-1);
			
		}//end of catch
		
		return null;//never returns null
		
	}//end of getAccountBalance
	
	public static AccountPositionsResponse getAcctPosition(String acct){
		
		AccountsClient client = getAccountsClient();
		AccountPositionsResponse aprs = null;
		AccountPositionsRequest apr = new AccountPositionsRequest();
		
		try{
			
			aprs = client.getAccountPositions(acct, apr);
			
		}//end of try
		
		catch(IOException | ETWSException e){
			
			System.out.println("Error encountered in getting Account Positions!");
			
			System.exit(-1);
			
		}//end of catch
		
		return aprs;
		
	}//end of getAcctPosition
	
	public static AccountPositionsResponse getAcctPosition(Account a){
		
		return getAcctPosition(a.getAccountId());
		
	}//end of getAcctPosition

}
