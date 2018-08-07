//Stock object to hold data about stocks and utilized when producing order and receiept
public class Stock {
	
	private String companyName;
	private String symbol;
	private double buyPrice;
	private double sellPrice;
	private double stopLossPrice;
	private int quantity;
	private int RSI;
	private int MACD;
	
	public Stock(){
		
		this.companyName = null;
		this.symbol = null;
		this.buyPrice = 0;
		this.sellPrice = 0;
		this.stopLossPrice = 0;
		this.quantity = 0;
		
	}
	
	public Stock(String companyName, String symbol, double buyPrice, double sellPrice, double stopLossPrice, int quantity){
		
		this.companyName = companyName;
		this.symbol = symbol;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.stopLossPrice = stopLossPrice;
		this.quantity = quantity;
		
	}//end of constructor
	
	//setters
	public void setCompanyName(String companyName){
			
		this.companyName = companyName;
			
	}
		
	public void setSymbol(String symbol){
			
		this.symbol = symbol;
			
	}
		
	public void setBuyPrice(double buyPrice){
			
		this.buyPrice = buyPrice;
			
	}

	//aim for a profit of > 8%
	public void setSellPrice(double sellPrice){
			
		this.sellPrice = sellPrice;
			
	}
		
	//aim for a stop loss of 5% of total order value
	public void setStopLoss(double stopLossPrice){
			
		this.stopLossPrice = stopLossPrice;
			
	}
		
	public void setQuantity(int quantity){
			
		this.quantity = quantity;
			
	}
	
	public void setRSI(int RSI){
		
		this.RSI = RSI;
		
	}
	
	public void setMACD(int MACD){
		
		this.MACD = MACD;
		
	}
	
	//getters
	public String getCompanyName(){
		
		return this.companyName;
		
	}
	
	public String getSymbol(){
		
		return this.symbol;
		
	}
	
	public double getBuyPrice(){
		
		return this.buyPrice;
		
	}
	
	public double getSellPrice(){
		
		return this.sellPrice;
		
	}
	
	public double getStopLossPrice(){
		
		return this.stopLossPrice;
		
	}
	
	public int getQuantity(){
		
		return this.quantity;
		
	}
	
	public int getRSI(){
		
		return this.RSI;
		
	}
	
	public int getMACD(){
		
		return this.MACD;
		
	}

}
