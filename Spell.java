
/**
 * This program is a simple spell-checker that takes in user input as a file and compares to dictionary file
 * Jensen Medeiros 
 * #251234023
 * Assignment 2
 * CS 2210B
 * February 16, 2023
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;


public class Spell {
	
	private static Hashtable<String, Boolean> correctDict= new Hashtable<String, Boolean>();
	private static Hashtable<String, Boolean> fileToCheckDict= new Hashtable<String, Boolean>();
	public static  String  fileToCheck = "C:\\Users\\Jensen Medeiros\\OneDrive\\Desktop\\CS2210\\Assignment_1\\src\\fileToCheck.txt";
	public static  String dictionary = "C:\\Users\\Jensen Medeiros\\OneDrive\\Desktop\\CS2210\\Assignment_1\\src\\dictionary.txt";
	


    Spell(){
    	DictonaryOrganizer(dictionary);
    	try {
			spellCheckerHelper(dictionary, fileToCheck);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	
    }

    public static void main(String[] args) throws FileNotFoundException {
        if(args.length != 0) {
        	for(String str : args){
            	if(str.equals("dictionary.txt")) {
            		dictionary = str;
            	}else {
            		fileToCheck = str;
            	}
            }
        	
        	Spell spellObj = new Spell();
        	
        }else {
        	System.out.println("Please input a dictionary file and file to check");
        }

    		
    }
    
    
    
    
    
    // private helper dictionary organizer
    private static Hashtable<String, Boolean> DictonaryOrganizer(String dictionary) {
		
		try {
			int id = 1;
			File myObj = new File(dictionary);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNext()) {
				String data = myReader.next();
				
				
				// Remove commas from word
				int testData = data.indexOf(",");
				// if comma is found then remove it and add to dictionary
				if(testData == -1) {
					correctDict.put(data.toLowerCase(), true);
					id ++;	
				}
				else {
					String newData = data.replace(",", "");
					correctDict.put(newData.toLowerCase(), true);
					id ++;		
				}	
				
			}
			myReader.close();
		}catch(FileNotFoundException e) {
			System.out.println(e);
		}
		
		return correctDict;
		
		
		
	}	
    
    
    
    // This function check if the dictionay is loaded or not
    public static Hashtable<String, Boolean> getDictionary(){
    	return correctDict;    
    }

    
    
    
    
    
    // private helper method organizes fileToCheck
	private static Hashtable<String, Boolean> fileToCheckOrganizer(String fileToCheck) {
			
			try {
				int id = 1;
				File myObj = new File(fileToCheck);
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNext()) {
					String data = myReader.next();
					
					
					// Remove commas from word
					int testData = data.indexOf(",");
					// if comma is found then remove it and add to dictionary
					if(testData == -1) {
						fileToCheckDict.put(data.toLowerCase(), true);
						id ++;	
					}
					else {
						String newData = data.replace(",", "");
						fileToCheckDict.put(newData.toLowerCase(), true);
						id ++;		
					}	
					
				}
				myReader.close();
			}catch(FileNotFoundException e) {
				System.out.println("An error occured");
			}
			
			return fileToCheckDict;
			
			
			
		}

	private static Hashtable<String, Boolean> spellCheckerHelper(String dictionary, String fileToCheck) throws FileNotFoundException {
		Hashtable<String, Boolean> correctDictDummy = DictonaryOrganizer(dictionary);
		Hashtable<String, Boolean> fileToCheckDummy = fileToCheckOrganizer(fileToCheck);
		
		Iterator testDict = fileToCheckDummy.entrySet().iterator();
		
		
		// check if index of fileToCheckDummy is in dictonary
		for (Entry<String, Boolean> entry : fileToCheckDummy.entrySet()) {
			
			String mapElement = entry.getKey();
			Boolean mapKey = entry.getValue();
			
			if(correctDictDummy.get(mapElement) == null) {
//				//Remove word from FileToCheckDummy
				fileToCheckDict.put(mapElement, false);
			}else {
				//store value of right word
				fileToCheckDict.put(mapElement, true);
			}
			
		}
		
		return fileToCheckDict;
		
	}
    
    
    // This function takes a String word as an argument to check if the word exists in the dictionary. 
    // If the word exists, it will print it with a message "Incorrect Spelling:" to the console.
    // Else it will call the suggestCorrections function to provide the correct word from the words given in the dictionary file.
    public static boolean checkSpelling(String word) {
    	Pattern digit = Pattern.compile("[0-9]");
    	Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
    	Matcher hasDigit = digit.matcher(word);
    	Matcher hasSpecial = special.matcher(word);
    	if(hasDigit.find() | hasSpecial.find() ) {
    		return false;
    	}else {
    		Boolean finalVal = correctDict.get(word.toLowerCase());
        	
        	if(finalVal != null) {
        		return finalVal;
        	}
    	}
    	
		
		return false;
    		
    }

    // This function takes a String input word as argument.
    // It starts by printing the message word: Incorrect Spelling to the console.
    // The function also uses four different methods (correctSpellingWithSubstitution,
    // correctSpellingWithOmission, correctSpellingWithInsertion, correctSpellingWithReversal)
    // to generate possible corrected spellings for the input word.
    // The function then returns the suggestions list which contains the possible corrected spellings.
    public static boolean suggestCorrections(String word){
    	
    	Hashtable<String, Boolean> correctDictDummy = DictonaryOrganizer(dictionary);
		Hashtable<String, Boolean> fileToCheckDummy;
		try {
			fileToCheckDummy = spellCheckerHelper(dictionary, fileToCheck);
			
			
			
			// checks all the elements that don't have a !
			Iterator testDict2 = fileToCheckDummy.entrySet().iterator();
			while(testDict2.hasNext()) {
				Map.Entry mapElement  = (Map.Entry)testDict2.next();
				
				if(mapElement.getKey().toString().equals(word)) {
					
					if(fileToCheckDummy.get(word) == false) {
						String substitutionString = correctSpellingSubstitution(word);
						String letterOmissionString = correctSpellingWithOmission(word);
						ArrayList<String> letterInsertionString = correctSpellingWithInsertion(word);
						String letterReversalString = correctSpellingWithReversal(word);
						
						
						HashMap<String, Boolean> finalDict = new HashMap<String, Boolean>();
						
						if(substitutionString != null) {
							finalDict.put(substitutionString, false);
						}
						if(letterOmissionString != null) {
							finalDict.put(letterOmissionString, false);
						}
						for(int i = 0; i < letterInsertionString.size(); i++) {
							if(letterInsertionString.get(i) != null) {
								finalDict.put(letterInsertionString.get(i), false);
							}
						}
						if(letterReversalString != null) {
							finalDict.put(letterReversalString, false);
						
						}
						String testString = "";
							
						// Iterate through dictionary and pass values into string
						int i = 0;
						for (Entry<String, Boolean> entry : finalDict.entrySet()) {
							Boolean element = entry.getValue();
							String index = entry.getKey();
							System.out.println(finalDict.size());
							if(i != finalDict.size() -1) {
								testString += index + ", ";
								System.out.println(i);
								i++;
							}else {
								testString += index;
							}
		
						}
						if(testString.equals("")) {
							System.out.println(word + ": " + "Incorrect Spelling");
							System.out.println(word + " => " + "no suggestions");
							return false;
						}else {
							// print results
							System.out.println(word + ": " + "Incorrect Spelling");
							System.out.println(word + " => " + testString);
							return true;
							
						}
						
						
					}else {
						
						
						//prints out the output for the correct spelling
						StringBuilder correctString = new StringBuilder(mapElement.getValue().toString());
						System.out.println(word + ":" + " Correct Spelling");
						return true;
						
					}
		
					
				}	
			}
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	
		return true;
		
        
    }

    // This function takes in a string word and tries to correct the spelling by substituting letters and 
    // check if the resulting new word is in the dictionary.
    static String correctSpellingSubstitution(String StringToTest) {
    		String origString = StringToTest;
			
			// alphabet to be used
			HashMap<Character,Integer> alphabet = new HashMap<>();
			for (char ch = 'a'; ch <= 'z'; ++ch) 
				alphabet.put(ch, 0); 
			
			int i = 0;
			
			while(i < StringToTest.length()) {
				
				for(i = 0; i < StringToTest.length();i++) {
					
					//iterate through alphabet to make hashmap
					for (Entry<Character, Integer> entry : alphabet.entrySet()) {
					    Character letter = entry.getKey();
					    String dummyString = StringToTest;
					    StringBuilder finalString = new StringBuilder(dummyString);
					    finalString.setCharAt(i, letter);
					    
					    
					    //iterate through to see if in dictionary
					    for( Entry<String, Boolean> element : correctDict.entrySet()) {
					    	Boolean value = element.getValue();
					    	String key = element.getKey();
					    	if(key.equals(finalString.toString())) {
					    		StringToTest = key;
					    	}
					    }
					   
					}
				}
				
			}
			
			if(origString.equals(StringToTest)) {
				return null;
			}else {
				return StringToTest;
				
			}
    }

    // This function tries to omit (in turn, one by one) a single character in the misspelled word 
    // and check if the resulting new word is in the dictionary.
    static String correctSpellingWithOmission(String StringToTest) {
    	
		String origString = StringToTest;
				
				int i = 0;		
				while(i < StringToTest.length()) {
							//iterate through string to delete character
							for(i = 0; i < StringToTest.length();i++) {
								//remove letter from string
								String testString = StringToTest;
								StringBuilder dummyString = new StringBuilder(testString);
								dummyString.deleteCharAt(i);
							    //iterate through to see if in dictionary
							    for( Entry<String, Boolean> element : correctDict.entrySet()) {
							    	Boolean value = element.getValue();
							    	String key = element.getKey();
							    	if(key.equals(dummyString.toString())) {
							    		StringToTest = key;
							    	}
							    }
								   
								
							}
							
						}
				
				if(origString.equals(StringToTest)) {
					return null;
				}else {
					return StringToTest;
					
				}
    }
    
    
    
    
    
    //private helper method
    private static String letterInsertionHelper(String StringToTest) {
		
		String origString = StringToTest;
		
		HashMap<Character,Integer> alphabet = new HashMap<>();
		for (char ch = 'a'; ch <= 'z'; ++ch) 
			alphabet.put(ch, 0); 
		
		int i = 0;
		
		while(i < StringToTest.length()) {
			
			for(i = 0; i < StringToTest.length();i++) {
				
				//iterate through alphabet to make hashmap
				for (Entry<Character, Integer> entry : alphabet.entrySet()) {
				    Character letter = entry.getKey();
				    String dummyString = StringToTest;
				    StringBuilder finalString = new StringBuilder(dummyString);
				    
				    if(i == 0) {
				    	finalString.insert(i, letter);
				    }else {
				    	finalString.insert(i-1, letter);
				    }
				    
				    
				    
				    for( Entry<String, Boolean> element : correctDict.entrySet()) {
				    	Boolean value = element.getValue();
				    	String key = element.getKey();
				    	if(key.equals(finalString.toString())) {
				    		StringToTest = key;
				    	}
				    }
				   
				}
			}
			
		}
		
		if(origString.equals(StringToTest)) {
			return null;
		}else {
			return StringToTest;
			
		}
		
		
	}


    // This function tries to insert a letter in the misspelled word 
    // and check if the resulting new word is in the dictionary.
    static ArrayList<String> correctSpellingWithInsertion(String StringToTest) {
        
    	
    	String origString = StringToTest;
		String testString = StringToTest;
		// alphabet to be used
		HashMap<Character,Integer> alphabet = new HashMap<>();
		for (char ch = 'a'; ch <= 'z'; ++ch) 
			alphabet.put(ch, 0); 
		
		int i = 0;
		
		while(i < StringToTest.length()) {
			
			for(i = 0; i < StringToTest.length();i++) {
				
				//iterate through alphabet to make hashtable
				for (Entry<Character, Integer> entry : alphabet.entrySet()) {
				    Character letter = entry.getKey();
				    String dummyString = StringToTest;
				    StringBuilder finalString = new StringBuilder(dummyString);
				    finalString.insert(i+1, letter);
				    
				    
				    for( Entry<String, Boolean> element : correctDict.entrySet()) {
				    	Boolean value = element.getValue();
				    	String key = element.getKey();
				    	if(key.equals(finalString.toString())) {
				    		StringToTest = key;
				    	}
				    }
				   
				}
			}
			
		}
		// pass to beginning of letter helper method
		String finalTestString = letterInsertionHelper(testString);
		ArrayList<String> finalList = new ArrayList<String>();
		
		if(origString == StringToTest & origString !=finalTestString ) {
			finalList.add(finalTestString);
			return finalList;	
		}else if(origString != StringToTest & origString ==finalTestString) {
			finalList.add(StringToTest);
			return finalList;
		}
		else if(origString != StringToTest & origString !=finalTestString){
			finalList.add(StringToTest);
			finalList.add(finalTestString);
			return finalList;
		}else {
			return null;
		}
    	
    	
    	
    }
    
    // This function tries swapping every pair of adjacent characters 
    // and check if the resulting new word is in the dictionary.
    static String correctSpellingWithReversal(String StringToTest ) {
       
    	
    	String origString = StringToTest;
			
			int i = 0;
			
			while(i < StringToTest.length()) {
				
				//iterate through string to delete character
				for(i = 0; i < StringToTest.length();i++) {
					//remove letter from string
					String testString = StringToTest;
					StringBuilder dummyString = new StringBuilder(testString);
					
					//swapping characters
					if(i + 1 < StringToTest.length()) {
						Character leftVal = dummyString.charAt(i);
						Character rightVal = dummyString.charAt(i+1);
						dummyString.setCharAt(i, rightVal);
						dummyString.setCharAt(i+1, leftVal);
						
					}
				   for( Entry<String, Boolean> element : correctDict.entrySet()) {
				    	Boolean value = element.getValue();
				    	String key = element.getKey();
				    	if(key.equals(dummyString.toString())) {
				    		StringToTest = key;
				    	}
				    }
					   
					
				}
				
			}
			
			
			if(origString.equals(StringToTest)) {
				return null;
			}else {
				return StringToTest;
				
			}
	    }

}