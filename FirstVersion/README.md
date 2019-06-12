# SP500-Trader

This program utilizes E-Trade's API to execute orders in the stock market with your E-Trade account. It focuses on data collected from Stock Screener to create orders based on constituents of the S&P 500 index. 

This program can easily be updated to trade any sort of equity, not only from the S&P 500 index. It can also has support to trade options. 
I created this as a way to familiarize myself with the E-Trade API using a simple strategy to trade stocks listed under the S&P 500. 

The Strategy I'm using utilizes data from Stock Screener such as the MACD and RSI to determine if the stock is oversold or overbought. 

To run this program or utilize the E-Trade API you must have external JAR's added to your program.

In addition, you must have a file in your program named "local.properties", this file should contain your keys from E-Trade that you can request if you have an E-Trade account. The format of this file should be:

consumer_key = ################
consumer_secret = ##################

where the "#" is your key. Also you should edit the program if your are using the SANDBOX or LIVE enviroment. The SANDBOX enviroment is a way to test that all your calls to E-Trade are functioning correct and the LIVE enviroment executes real trades in your account with real money. 

# Useful information:
https://www.investopedia.com/ask/answers/040215/what-does-sp-500-index-measure-and-how-it-calculated.asp
https://www.investopedia.com/terms/m/montecarlosimulation.asp
https://www.investopedia.com/terms/r/rsi.asp
https://www.investopedia.com/terms/m/macd.asp
https://developer.etrade.com/
