//This class is for market data from eTrade, maybe not used but still worth having for scalability of other features

import java.io.IOException;
import java.util.*;

import com.etrade.etws.market.DetailFlag;
//import com.etrade.etws.market.IntradayQuote;
import com.etrade.etws.market.QuoteData;
import com.etrade.etws.market.QuoteResponse;
import com.etrade.etws.sdk.client.ClientRequest;
import com.etrade.etws.sdk.client.MarketClient;
import com.etrade.etws.sdk.common.ETWSException;

public class Market {
	
	private static ClientRequest request;
	
	public Market(){
		
		request = Login.getRequest();
		
	}
	
	public static ArrayList<Stock> getStockData(ArrayList<String> symbol){
		
		Iterator<QuoteData> it = getQuotes(symbol);
		
		ArrayList<Stock> returnList = new ArrayList<Stock>();
		
		while(it.hasNext()){
			
			QuoteData quote = it.next();
			
			double marketPrice = quote.getIntraday().getLastTrade();
			
			//String companyName, String symbol, double buyPrice, double stopLossPrice, int quantity
			Stock s = new Stock(null, quote.getProduct().getSymbol(), marketPrice,0,0,0);
			
			returnList .add(s);
			
		}//end of while
	
		return returnList;
		
	}
	
	private static Iterator<QuoteData> getQuotes(ArrayList<String> symbol){
		
		if(request == null)
			request = Login.getRequest();
		
		MarketClient client = new MarketClient(request);
		
		QuoteResponse response = null;
		
		try{
			
			response = client.getQuote(symbol, false, DetailFlag.INTRADAY);
			
		}//end of try
		
		catch(IOException | ETWSException e){
			
			System.err.println("Error encountered in getQuotes. Exiting Program!");
			
			System.exit(-1);
			
		}//end of catch
		
		List<QuoteData> qd = response.getQuoteData();
		
		Iterator<QuoteData> it = qd.iterator();
		
		return it;
		
	}

}