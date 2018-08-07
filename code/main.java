//This class will test how to write to a .csv like SP500.csv
//Will then be implemented to get stock data from list and write to the list the values

//maybe not needed - but will be used as my main for now
//maybe delete this class once other classes are done to have a proper main
//or maybe rename this one

import java.io.File;
import java.io.FileNotFoundException;

//import com.etrade.etws.account.Account;

import java.util.*;

public class main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub		
		
		SP500Orders orders = new SP500Orders();
		
		//call method that is responsible for
		//finding stocks to trade and 
		//placing this orders for either buy or sell
		orders.SP500Orders();

	}

}
