

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
			if(dummyTest != -1) {
				substitutionDict(fileToCheckDummy.get(mapElement.getKey()));	
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
		
		// alphabet to be used
		HashMap<String,Integer> alphabet = new HashMap<>();
		for (char ch = 'a'; ch <= 'z'; ++ch) 
			alphabet.put(String.valueOf(ch), 0); 
		
		for(int i = 0; i < StringToTest.length();i++) {
			for (Entry<String, Integer> entry : alphabet.entrySet()) {
			    String letter = entry.getKey();
			   System.out.println(letter);
			    
			    // Do things with the list
			}
		}
		
		return "djskd";
		
		
	}
	
	
	

}
