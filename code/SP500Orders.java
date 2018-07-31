//this class will read the file 
//and create a ArrayList (queue) of Stock objects you want to trade
//return this list to main to be sent to create orders
//contain a method that returns order list
//contain a method that decides order quantity depending on AccountBalance

import com.etrade.etws.account.Account;
import com.etrade.etws.order.PlaceEquityOrderResponse;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.LinkedList;
import java.util.Queue;

import java.util.List;

public class SP500Orders {
	
	private Account acct;
	
	public void SP500Orders(){
		
		Scanner scan;
		
		this.acct = getAccount();
		
		//csv contains
		//MMM170.35175.555500.94657
		//Symbol # Current Price # Predicted Price # RSI # MACD # ProbScore
		
		
		ArrayList<String> list = new ArrayList<String>();
		
		ArrayList<String> symb = new ArrayList<String>();
		ArrayList<Double> price = new ArrayList<Double>();
		ArrayList<Double> estPrice = new ArrayList<Double>();
		ArrayList<Integer> RSI = new ArrayList<Integer>();
		ArrayList<Integer> MACD = new ArrayList<Integer>();
		ArrayList<Double> probScore = new ArrayList<Double>();
		
		try {
			scan = new Scanner(new File("SP500-Data.csv"));
		
			//used to process CSV file
			//the delimiters are either comma or newline
			scan.useDelimiter(",|\\n");
			
			//skip header
			scan.nextLine();
			
			//Read File and save entire list to one list
			while(scan.hasNext()){
				
				String stocks = scan.next();
				
				list.add(stocks);
				
			}
			
			//Process through entire list of all types and save them
			//to their appopriate list type
			for(int i = 0; i < list.size();){
				
				symb.add(list.get(i));
				price.add(Double.parseDouble(list.get(i+1)));
				estPrice.add(Double.parseDouble(list.get(i+2)));
				RSI.add(Integer.parseInt(list.get(i+3)));
				MACD.add(Integer.parseInt(list.get(i+4)));
				probScore.add(Double.parseDouble(list.get(i+5)));
				
				i += 6;
				
			}//end of for
			
			scan.close();
	
		}//end of try
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.print("Error in SP500Orders. Exiting Program!");
		
			System.exit(-1);
		}//end of catch
		
		//System.out.println(symb);
		//System.out.println(price);
		//System.out.println(estPrice);
		//System.out.println(RSI);
		//System.out.println(MACD);	
		//System.out.println(probScore);
		
		ArrayList<Integer> goodRSI = new ArrayList<Integer>();
		
		for(int i = 0; i < RSI.size(); i++){
			
			//overbought conditions
			if(RSI.get(i) >= 65)
				goodRSI.add(i); //saves index
			
			//oversold conditions
			if(RSI.get(i) <= 35)
				goodRSI.add(i); //saves index
			
			//neutral conditions
			//if(RSI.get(i) >= 40 && RSI.get(i) <= 60)
				//goodRSI.add(i); //saves index
			
		}
		
		ArrayList<Integer> tradeable = new ArrayList<Integer>();
		
		for(int i = 0; i < goodRSI.size(); i++){
			
			//this considers overbought conditions
			if(MACD.get(goodRSI.get(i)) == 1)
				tradeable.add(goodRSI.get(i));
			
			//this considers oversold conditions
			if(MACD.get(goodRSI.get(i)) == -1)
				tradeable.add(goodRSI.get(i));
			
			//this considers neutral conditions conditions
			//good for neutral market options strategy 
			//if(MACD.get(goodRSI.get(i)) == 0)
				//tradeable.add(goodRSI.get(i));
			
		}
		
		//consider prob score
		
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		
		for(int i = 0; i < tradeable.size(); i++){
			
			Stock stock = new Stock();
			
			stockList.add(stock);
			
			//Create stock objects and save them to list
			//gets the index saved in trabeable
			//uses index to retrieve the values from other lists
			stock.setSymbol(symb.get(tradeable.get(i)));
			stock.setBuyPrice(price.get(tradeable.get(i)));
			stock.setSellPrice(estPrice.get(tradeable.get(i)));
			stock.setRSI(RSI.get(tradeable.get(i)));
			stock.setMACD(MACD.get(tradeable.get(i)));
			stock.setProbScore(probScore.get(tradeable.get(i)));
			
			//Default of 100
			int quantity = 100;
				
			stock.setQuantity(quantity);	
			
		}//end of for
		
		//stockList constains all stock objects
		
		Queue<Stock> orders = new LinkedList();
		
		for(int i = 0; i < stockList.size(); i++){
			
			if( stockList.get(i).getBuyPrice() - stockList.get(i).getSellPrice() / stockList.get(i).getBuyPrice() >= .05 )
				orders.add(stockList.get(i));
			
		}
		
		//send Buy orders
		sendOrderBUY(orders);
		
		//send Sell orders request such as desired sell price
		//for owned shares
		sendOrderSELL(orders);
		
	}//End of SP500 Orders
	
	public void sendOrderBUY(Queue<Stock> orders){
		
		//while queue is not empty
		while(!orders.isEmpty()){
			
			Stock stock = orders.poll();
			
			PlaceEquityOrderResponse response = Orders.buy(stock, this.acct);
			
		}
		
	}//end of sendOrderBUY
	
	public void sendOrderSELL(Queue<Stock> orders){
		
		//while queue is not empty
				while(!orders.isEmpty()){
					
					Stock stock = orders.poll();
					
					PlaceEquityOrderResponse response = Orders.sell(stock, this.acct);
					
				}
		
	}//end of sendOrderSell
	
	private static Account getAccount(){
		
		List<Account> accounts = Accounts.getAccounts();
		
		Iterator<Account> it = accounts.iterator();
		
		while(it.hasNext()){
			
			//choose an account
			//since I only have one, that account will be chosen
			return it.next();
			
		}
		
		return null;
		
	}//end of getAccount
	
}
