import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OneTimePass {


/**
 * Adds a space after every 8 characters. This is useful for translating binary to individual characters.
 *
 * @param s String represented by binary.
 */
	public static String splitStringEvery(String s) {
		int interval = 8;

		String result = "";
		for(int i = 0; i < s.length(); i+=interval)
		{
			String temp = s.substring(i,i+interval);

				result += temp + " ";
		}
		return result;
	}
/**
 * Converts characters in strings to binary
 *
 * @param text some random string
 */	
	public static String stringToBinary(String text)
	 {
	     String bString="";
	     String temp="";
	     for(int i=0;i<text.length();i++)
	     {
	         temp=Integer.toBinaryString(text.charAt(i));
	         for(int j=temp.length();j<8;j++)
	         {
	             temp="0"+temp;
	         }
	         bString+=temp;
	     }
	     return bString;
	 }
/**
 * Converts a binary string to readable characters
 *
 * @param binaryCode string that has binary
 */	
	 public static String binaryToString(String binaryCode)
	 {
	     String[] code = binaryCode.split(" ");
	     String word="";
	     for(int i=0;i<code.length;i++)
	     {
	         word+= (char)Integer.parseInt(code[i],2);
	     }
	     return word;
	 }
/**
 * Performs the One Time Pass on a binary string given a key
 *
 * @param binaryString string that is represented in binary
 * @param keyString a key to be used in one time pad
 */	
	 public static String otp(String binaryString, String keyString)
	 {
		 String conCatString = "";
		 for(int i = 0; i < binaryString.length(); i++)
		 {
			 char binaryStringChar = binaryString.charAt(i);
			 int binStringint = Character.getNumericValue(binaryStringChar);
			 char keyStringChar = keyString.charAt(i);
			 int keyStringint =  Character.getNumericValue(keyStringChar);
			 int otpInt = binStringint ^ keyStringint;
			 String temp = Integer.toString(otpInt);
			 conCatString = conCatString + temp;
		 }
		 return conCatString;
	 }
/**
 * Encrypts using One Time Pass. Outputs ciphertext in binary and UTF-8 to a file in the data directory.
 * "This is your ciphertext in binary " is included along with another string with the ciphertexts to clarify.
 * @param plainTextFileName string that will be encrypted
 * @param keyString a key to be used in one time pad
 */	
	public static void encrypt(String plainTextFileName, String key) throws IOException
	{
		File plainTextFile = new File(plainTextFileName);
		File keyFile = new File(key);
		
		FileInputStream plainTextfis = null;
		plainTextfis = new FileInputStream(plainTextFile);
		
		FileInputStream keyfis = null;
		keyfis = new FileInputStream(keyFile);
		
		BufferedReader plainTextReader = new BufferedReader(new InputStreamReader(plainTextfis, "UTF-8"));
		BufferedReader keyFileReader = new BufferedReader(new InputStreamReader(keyfis,"UTF-8"));

		try {
		  while (true) {
		    String plainTextline = plainTextReader.readLine();
		    if (plainTextline == null) break;
		    String binaryString = stringToBinary(plainTextline);
		    System.out.println("This is your plaintext in binary " + binaryString);
		    
		    String keyline = keyFileReader.readLine();
		    if(keyline == null) break;
		    System.out.println("This is your key " + keyline);
		    
		    if(binaryString.length() == keyline.length())
		    {
		    	String encryptedBin = otp(binaryString,keyline);
		    	System.out.println("Ciphertext in binary is " + encryptedBin);
		    	String encryptedString = binaryToString(splitStringEvery(encryptedBin));
		    	System.out.println("Ciphertext in UTF-8 is " + encryptedString);
		    	PrintWriter writer = new PrintWriter("../data/ciphertext.txt", "UTF-8");
		    	writer.println("This is your ciphertext in binary " + encryptedBin);
		    	writer.print("This is your ciphertext in UTF-8 " + encryptedString);
		    	writer.close();
		    }
		    else
		    {
		    	System.out.println("error: length is incorrect");
		    	//as per the requirements an empty file if key length doesn't match plaintext length
		    	PrintWriter writer = new PrintWriter("../data/ciphertext.txt", "UTF-8");
		    	writer.close();
		    }
		    
		  }
		} finally {
		  plainTextReader.close();
		  keyFileReader.close();
		}
	}
/**
 * Decrypts using One Time Pass. Outputs plaintext in UTF-8 to a file in the data directory.
 * "This is your plaintext in UTF-8 " is included along with another string with the plaintexts to clarify.
 * @param cipherTextFileName string that will be decrypted
 * @param keyString a key to be used in one time pad
 */	
	public static void decrypt(String cipherTextFileName, String key) throws IOException
	{
		File cipherTextFile = new File(cipherTextFileName);
		File keyFile = new File(key);
		
		FileInputStream cipherTextfis = null;
		cipherTextfis = new FileInputStream(cipherTextFile);
		
		FileInputStream keyfis = null;
		keyfis = new FileInputStream(keyFile);
		
		BufferedReader cipherTextReader = new BufferedReader(new InputStreamReader(cipherTextfis, "UTF-8"));
		BufferedReader keyFileReader = new BufferedReader(new InputStreamReader(keyfis,"UTF-8"));
		
		try {
			  while (true) {
			    String cipherTextline = cipherTextReader.readLine();
			    if (cipherTextline == null) break;
			    String binaryString = stringToBinary(cipherTextline);
			    System.out.println("This is your ciphertext in binary " + binaryString);
			    
			    String keyline = keyFileReader.readLine();
			    if(keyline == null) break;
			    System.out.println("This is your key " + keyline);
			    
			    if(binaryString.length() == keyline.length())
			    {
			    	String decryptedString = binaryToString(splitStringEvery(otp(binaryString,keyline)));
			    	PrintWriter writer = new PrintWriter("../data/result.txt", "UTF-8");
			    	writer.println("This is your plaintext in UTF-8 " + decryptedString);
			    	writer.close();
			    	System.out.println("This is your plaintext in UTF-8 " + decryptedString);
			    }
			    else
			    {
			    	System.out.println("error: length is incorrect");
			    	//creating an empty result file if length is incorrect
			    	PrintWriter writer = new PrintWriter("../data/result.txt", "UTF-8");
			    	writer.close();
			    }
			    
			  }
			} finally {
			  cipherTextReader.close();
			  keyFileReader.close();
			}
	}
/**
 * Generates a random key based on a lambda. Outputs key to a file.
 * @param lambda a value that will determine how long the key will be
 */	
	public static void keygen(int lambda) throws IOException
	{
		String key = "";
		Random rand = new Random();
		if(lambda >= 1 && lambda <= 128)
		{
			for(int i = 0; i < lambda; i++)
			{
				int temp = rand.nextInt(2);
				key += Integer.toString(temp);
			}
		}
		else
		{
			System.out.println("Oh no you didn't enter a number between 1 and 128. Try again please.:(");
			return;
		}
		PrintWriter writer = new PrintWriter("../data/newkey.txt", "UTF-8");
    	writer.print("This is your random key " + key);
    	writer.close();
    	System.out.println("This is your random key " + key);
	}

	public static void main(String[] Args) throws IOException
	{
		//tells whether you want to encrypt/decrypt/key generation
		String choice = Args[0];
		//path to plaintext/ciphertext(if you are encrypting use a path to a plaintext, if decrypting use a path to a ciphertext otherwise unused if using keygen)
		String file = Args[1];
		String key = "";
		//used if using keygen
		String lambda = Args[1];

		switch(choice.toLowerCase())
		{
		case "encrypt":
			key = Args[2];
			encrypt(file,key);
			break;
		case "decrypt":
			key = Args[2];
			decrypt(file,key);
			break;
		case "keygen":
			keygen(Integer.parseInt(lambda));
			break;
		}
	}
}
