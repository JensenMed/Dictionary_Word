

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner; // Import the Scanner class to read text files



public class Spell {
	
	private static HashMap<Integer, String> correctDict= new HashMap<Integer, String>();
	private static HashMap<Integer, String> fileToCheckDict= new HashMap<Integer, String>();
	
	
	
	public Spell(String dictionary, String fileToCheck) throws FileNotFoundException {
		HashMap<Integer, String> correctDictDummy = DictonaryOrganizer(dictionary);
		HashMap<Integer, String> fileToCheckDummy = fileToCheckOrganizer(fileToCheck);
		
		Iterator testDict = fileToCheckDummy.entrySet().iterator();
		
		// check if index of fileToCheckDummy is in dictonary
		while(testDict.hasNext()) {
			Map.Entry mapElement  = (Map.Entry)testDict.next();
			if(correctDictDummy.containsValue(mapElement.getValue())) {
				//store value of right word
				String correctWord = fileToCheckDummy.get(mapElement.getKey());
				//Remove word from FileToCheckDummy
				fileToCheckDummy.remove(mapElement.getKey());
				//insert is back in with !
				fileToCheckDummy.put((Integer) mapElement.getKey(), correctWord + "!");
				
			}
		}
		
		// checks all the elements that don't have a !
		Iterator testDict2 = fileToCheckDummy.entrySet().iterator();
		while(testDict2.hasNext()) {
			Map.Entry mapElement  = (Map.Entry)testDict2.next();
			
			// if contains ! then leave be (it's in dictionary) else pass to substitution method
			int dummyTest = fileToCheckDummy.get(mapElement.getKey()).indexOf("!");
			if(dummyTest == -1) {
				String substitutionString = substitutionDict(fileToCheckDummy.get(mapElement.getKey()));
				String letterOmissionString = letterOmission(fileToCheckDummy.get(mapElement.getKey()));
				String letterInsertionString = letterInsertion(fileToCheckDummy.get(mapElement.getKey()));
				String letterReversalString = letterReversal(fileToCheckDummy.get(mapElement.getKey()));
				
				HashMap<Integer, String> finalDict = new HashMap<Integer, String>();
				
				if(substitutionString != null) {
					finalDict.put(0, substitutionString);
				}
				if(letterOmissionString != null) {
					finalDict.put(1, letterOmissionString);
				}
				if(letterInsertionString != null) {
					finalDict.put(2, letterInsertionString);
				}
				if(letterReversalString != null) {
					finalDict.put(3, letterReversalString);
				
				}
					
				// just pass into for loop and should be good
				for (Entry<Integer, String> entry : finalDict.entrySet()) {
					String element = entry.getValue();
					
					
				}
//				
//				String finalString = substitutionString + "?" +  letterOmissionString + "?" + letterInsertionString + "?" + letterReversalString;
//				System.out.println(finalString);
				
//				for(int i = 0; i < finalString.length(); i++) {
//					if(finalString.charAt(i) == '?') {
//						
//					}
//				}
				
			} else {
				
				//prints out the output for the correct spelling
				StringBuilder correctString = new StringBuilder(mapElement.getValue().toString());
				correctString.deleteCharAt(dummyTest);
				System.out.println(correctString.toString() + ":" + " Correct Spelling");
			}
			
		}
		
	}
	
	
	private HashMap<Integer, String> DictonaryOrganizer(String dictionary) {
		
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
					correctDict.put(id, data.toLowerCase());
					id ++;	
				}
				else {
					String newData = data.replace(",", "");
					correctDict.put(id, newData.toLowerCase());
					id ++;		
				}	
				
			}
			myReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("An error occured");
		}
		
		return correctDict;
		
		
		
	}	
	
	
	// file 
	private HashMap<Integer, String> fileToCheckOrganizer(String fileToCheck) {
		
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
					fileToCheckDict.put(id, data.toLowerCase());
					id ++;	
				}
				else {
					String newData = data.replace(",", "");
					fileToCheckDict.put(id, newData.toLowerCase());
					id ++;		
				}	
				
			}
			myReader.close();
		}catch(FileNotFoundException e) {
			System.out.println("An error occured");
		}
		
		return fileToCheckDict;
		
		
		
	}
	
	private String substitutionDict(String StringToTest) {
		
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
				    for( Entry<Integer, String> element : correctDict.entrySet()) {
				    	String value = element.getValue();
				    	Integer key = element.getKey();
				    	if(value.equals(finalString.toString())) {
				    		StringToTest = value;
				    		
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
	
	
	//Method that performs letter omission tasks
	private String letterOmission(String StringToTest) {
		
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
					    for( Entry<Integer, String> element : correctDict.entrySet()) {
					    	String value = element.getValue();
					    	Integer key = element.getKey();
					    	if(value.equals(dummyString.toString())) {
					    		StringToTest = value;
					    		
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
	
	
	//letter insertion helper method to perform insertions in the beginning of letters
	private String letterInsertionHelper(String StringToTest) {
		
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
				    
				    
				    
				    //iterate through to see if in dictionary
				    for( Entry<Integer, String> element : correctDict.entrySet()) {
				    	String value = element.getValue();
				    	Integer key = element.getKey();
				    	if(value.equals(finalString.toString())) {
				    		StringToTest = value;
				    		
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
	
	//Method to do letterInsertion at end of letters
	private String letterInsertion(String StringToTest) {
		String origString = StringToTest;
		String testString = StringToTest;
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
				    finalString.insert(i+1, letter);
				    
				    
				    
				    //iterate through to see if in dictionary
				    for( Entry<Integer, String> element : correctDict.entrySet()) {
				    	String value = element.getValue();
				    	Integer key = element.getKey();
				    	if(value.equals(finalString.toString())) {
				    		StringToTest = value;
				    		
				    	}
				    }
				   
				}
			}
			
		}
		// pass to beginning of letter helper method
		String finalTestString = letterInsertionHelper(testString);
		
		if(origString.equals(finalTestString + StringToTest)) {
			return null;
		}else {
			if(finalTestString != null) {
				return StringToTest + "?" + finalTestString;
				
			}else {
				return StringToTest;
			}
			
		}
		
	}
	
	
	//Private method that swaps the two characters adjacent to eachother
	private String letterReversal(String StringToTest) {
		
		
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
				
			    //iterate through to see if in dictionary
			    for( Entry<Integer, String> element : correctDict.entrySet()) {
			    	String value = element.getValue();
			    	Integer key = element.getKey();
			    	if(value.equals(dummyString.toString())) {
			    		StringToTest = value;
			    		
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
