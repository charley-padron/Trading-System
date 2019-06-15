# Trading System

This is the new version of the automated trading system using E-Trade's API. This version is written in Python for now, may add support
Java later. This program uses E-Trade's API to get access to an account to perform automated trades. 

There are two version to this program; the automated version and the command line version. 

## Automated Trading System
In this version, Selenium is used to automate the login process required for the API. Using a password manager such as LastPass or your browser's password manager login can be automated. E-Trade's website has an option to remember user ID. A password manager and the remember user ID are used to avoid storing the user ID and password in the program. Selenium is used to visit the site and enter logon with the autofilled user ID and password from the browser, accepting the request and retrieving the access code. Screenshots below show how the process is done manually and how Selenium automates it.

After login and authentication, the program reads a CSV from created by another program that contains stock symbols, MACD and RSI of the consituents of the S&P 500. IF conditions are met, the program creates and executes orders.

Future feature include adding a database, reciept of transactions, and others. Additionally, I would like to use this to execute trades of other stocks besides those in the S&P 500. This is another program that is still in development. 

## Trading System with Command Line
The version of the program provides a command line interface to access all of the features of the E-Trade API. 
Code will be added soon.

## Running the program:

#### Automated Trading System
In order to run the automated version you will need to install libraries that are used. These libraries are requests, pandas and selenium.
You can download these using the command line and entering pip install "Library Name". For example, to install pandas you would need to enter pip install pandas. 

After you have these libraries you'll need to enter your consumer key and consumer secret from E-Trade into the config file after the equals sing. Information on how to obtain the keys can be found here: https://developer.etrade.com/getting-started

Once you have the keys, make sure you the automatedTrading.py, config.ini and SP500Data.csv in the same directory. Edit the pyhton file to include the path of the webdriver and the path for the CSV file. 

In the command line enter the directory where these files are. Once you are there, enter "python automatedTrading.py". This will begin the program and execute trades. I recommend changing the SP500Data.csv with your own CSV file or other format that contains the symbols you would like to trade.

#### Command Line Version
Details coming soon.
