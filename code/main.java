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
