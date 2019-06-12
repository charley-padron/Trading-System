//Testing the sandbox environment

import java.io.IOException;

//import com.etrade.etws.account.Account; 
//import com.etrade.etws.account.AccountListResponse; 
import com.etrade.etws.oauth.sdk.client.IOAuthClient; 
import com.etrade.etws.oauth.sdk.client.OAuthClientImpl; 
import com.etrade.etws.oauth.sdk.common.Token; 
import com.etrade.etws.sdk.client.ClientRequest;
import com.etrade.etws.sdk.client.Environment;
import com.etrade.etws.sdk.common.ETWSException; 

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Scanner;

import java.util.Properties;
import java.io.FileInputStream;

public class Login {
	
	// Variables 
	public static IOAuthClient client = null; 
	public static ClientRequest request = null; 
	public static Token token = null; 
	public static String oauth_consumer_key = null;  
	public static String oauth_consumer_secret = null;  
	public static String oauth_request_token = null; // Request token  
	public static String oauth_request_token_secret = null; // Request token secret 
	
	public static String oauth_access_token = null; // Variable to store access token 
	public static String oauth_access_token_secret = null; // Variable to store access token secret
	public static String oauth_verify_code = null; // Should contain the Verification Code received from the authorization step
	
	public static void login(){
		
		getProps();
		
		client = OAuthClientImpl.getInstance(); // Instantiate IOAUthClient 
		request = new ClientRequest(); // Instantiate ClientRequest 
		request.setEnv(Environment.SANDBOX); // Use sandbox environment 
	 
		request.setConsumerKey(oauth_consumer_key); //Set consumer key 
		request.setConsumerSecret(oauth_consumer_secret); // Set consumer secret 
	
		try{
			token= client.getRequestToken(request); // Get request-token object 
			oauth_request_token  = token.getToken(); // Get token string 
			oauth_request_token_secret = token.getSecret(); // Get token secret
		}//end of try
	
		catch(IOException | ETWSException e){
		
			System.err.println("Error encountered in Login. Exiting Program!");
			
			System.exit(-1);
		
		}//end of catch
	
		request.setToken(token.getToken());
		request.setTokenSecret(token.getSecret());
		oauth_verify_code = getVerificationCode();
		getAccessToken();
	
		}//end of login
	
	private static String getVerificationCode(){
		
		String authorizeURL = null;
		
		try{
			
			authorizeURL = client.getAuthorizeUrl(request);
			
			URI uri = new java.net.URI(authorizeURL);
			Desktop desktop = Desktop.getDesktop();
			desktop.browse(uri);
			
		}//end of try
		catch(IOException | ETWSException | URISyntaxException e){
			
			System.err.println("Error encountered in getting Verification Code!");
			
			System.exit(-1);
			
		}//end of catch
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input the verification code: ");
		String authCode = scan.next();
		scan.close();
		
		return authCode;
		
	}//end of getVerificationCode
	
	//Has verification code from authorize step
	private static void getAccessToken(){
		
		request = new ClientRequest();//Instantiate Client Request
		request.setEnv(Environment.SANDBOX);
		
		//Prepare request
		request.setConsumerKey(oauth_consumer_key);
		request.setConsumerSecret(oauth_consumer_secret);
		request.setToken(oauth_request_token);
		request.setTokenSecret(oauth_request_token_secret);
		request.setVerifierCode(oauth_verify_code);
		
		try{
			
			token = client.getAccessToken(request);
			
		}//end of try
		catch(IOException | ETWSException e){
			
			System.err.println("Error encountered in getting Access Token!");
			
			System.exit(-1);
			
		}//end of catch
		
		oauth_access_token = token.getToken();
		oauth_access_token_secret = token.getSecret();
		
	}//end of getAccessToken
	
	private static void getProps(){
		
		Properties prop = new Properties();
		
		try{
			
			prop.load(new FileInputStream("local.properties"));
			
			
		}//end of try
		catch(IOException e){
			
			System.out.println("Error encountered in getting Properties!");
			
			e.printStackTrace();
			
			System.exit(-1);
			
		}//end of catch
		
		oauth_consumer_secret=prop.getProperty("consumer_secret");
		oauth_consumer_key=prop.getProperty("consumer_key");
		
	}//end of getProps
	
	public static ClientRequest getRequest(){
		
		if(request == null){
			
			System.out.println("Logging in. You will have to enter the verification code: ");
			login();
			request = new ClientRequest(); //Instantiate ClientRequest
			request.setEnv(Environment.SANDBOX);
			
			//Prepare request
			request.setConsumerKey(oauth_consumer_key);
			request.setConsumerSecret(oauth_consumer_secret);
			request.setToken(oauth_request_token);
			request.setTokenSecret(oauth_request_token_secret);
			
			return request;
			
		}//end of if
		
		return request;
		
	}
	
}
