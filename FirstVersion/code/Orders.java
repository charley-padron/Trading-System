//Provides everything necessary for order execution

import com.etrade.etws.account.Account;

//used for receipt
//import com.etrade.etws.account.GetTransactionHistoryResponse;
//import com.etrade.etws.account.GetTransactionDetailsResponse;

import com.etrade.etws.order.EquityOrderAction;
import com.etrade.etws.order.EquityOrderRequest;
import com.etrade.etws.order.EquityOrderRoutingDestination;
import com.etrade.etws.order.EquityOrderTerm;
import com.etrade.etws.order.EquityPriceType;
import com.etrade.etws.order.MarketSession;
//import com.etrade.etws.order.OrderDetails;
//import com.etrade.etws.order.OrderListRequest;
import com.etrade.etws.order.PlaceEquityOrder;
import com.etrade.etws.order.PlaceEquityOrderResponse;
import com.etrade.etws.sdk.client.ClientRequest;
import com.etrade.etws.sdk.client.OrderClient;
import com.etrade.etws.sdk.common.ETWSException;

import java.io.IOException;

//Used in setting quantity
import java.math.BigInteger;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

//Used in custom ID for order
import java.util.Date;

public class Orders {
	
	private static ClientRequest request = Login.getRequest();
	
	//Place Buy order method
	public static PlaceEquityOrderResponse buy(Stock stock, Account acct){
		
		EquityOrderRequest eqOrderReq = new EquityOrderRequest();
		
		eqOrderReq.setSymbol(stock.getSymbol());
		eqOrderReq.setOrderAction(EquityOrderAction.BUY);
		//buy at Market Price
		eqOrderReq.setPriceType(EquityPriceType.MARKET);
		eqOrderReq.setQuantity(new BigInteger(String.valueOf(stock.getQuantity())));
		
		return placeOrder(acct, eqOrderReq);
		
	}//end of buy
	
	//Place Sell order method
	public static PlaceEquityOrderResponse sell(Stock stock, Account acct){
		
		EquityOrderRequest eqOrderReq = new EquityOrderRequest();
		
		eqOrderReq.setSymbol(stock.getSymbol());
		eqOrderReq.setOrderAction(EquityOrderAction.SELL);
		eqOrderReq.setPriceType(EquityPriceType.STOP);
		eqOrderReq.setStopPrice(new BigDecimal(String.valueOf(stock.getSellPrice())));
		eqOrderReq.setQuantity(new BigInteger(String.valueOf(stock.getQuantity())));
		
		return placeOrder(acct, eqOrderReq);
		
	}
	
	//Create unique order ID so that no order is repeated
	//maximum of 20 alphanumeric 
	private static String createClientOrderID(EquityOrderRequest eqOrderReq){ 
		
		SimpleDateFormat date = new SimpleDateFormat("MMddhhmm");
		
		//MMDDHHMM (8)
		String dateString = date.format(new Date());
		
		//BUY or SELL (3 or 4)
		String orderAction = eqOrderReq.getOrderAction().toString();
		
		//QQQQ (4 or less)
		String quantity = eqOrderReq.getQuantity().toString();
		
		//SYMBOL (4)
		String symbol = eqOrderReq.getSymbol().toString(); 
		
		//Final format BUY or SELL SYMBL QQQQ MMDDHHMM
		//Sample: BUYAAPL10007301035 -> BUY AAPL (Apple) 100 quantity on 07/30 at 10:35
		return String.format("%s%s%s%s", orderAction, symbol, quantity, dateString);
		
	}
	
	//Send order request to client
	private static PlaceEquityOrderResponse placeOrder(Account a, EquityOrderRequest orderReq){
		
		OrderClient client = new OrderClient(request);
		
		PlaceEquityOrder orderRequest = new PlaceEquityOrder();
		
		orderReq.setAccountId(a.getAccountId());
		orderReq.setAllOrNone("FALSE");
		orderReq.setOrderTerm(EquityOrderTerm.GOOD_FOR_DAY);
		orderReq.setMarketSession(MarketSession.REGULAR);
		//orderReq.setPriceType(EquityPriceType.MARKET);
		orderReq.setRoutingDestination(EquityOrderRoutingDestination.AUTO.value());
		orderReq.setReserveOrder("FALSE");
		
		String id = createClientOrderID(orderReq);
		
		orderReq.setClientOrderId(id);
		orderRequest.setEquityOrderRequest(orderReq);
		
		try{
			
			return client.placeEquityOrder(orderRequest);
			
		}//end of try
		catch(IOException | ETWSException e){
			
			System.err.println("Error encountered in placeOrder. Exiting Program!");
			
			System.exit(-1);
			
		}//end of catch
		
		//never reaches here since program will exit in try-catch
		return null;
		
	}//end of placeOrder

}
