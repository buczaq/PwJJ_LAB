package utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;

/**
 * 
 * Klasa odpowiedzialna za parsowanie pliku z kartami odpowiedzi (csv)
 */
public class AnswerCardParser
{
	/**
         * 
         * Zwraca przetworzony zbiór kart odpowiedzi
         * @param filename  nazwa/ścieżka bezwględna do pliku
         * @return          wektor kart odpowiedzi reprezentowanych jako mapy, w których element 0 to numer indeksu studenta
         */
        public Vector<TreeMap<String, String>> generateAnswerCardCollection(String filename)
	{
            Vector<TreeMap<String, String>> answerCardCollection = new Vector<TreeMap<String, String>>();
	    String csvFile = filename;
	    BufferedReader br = null;
	    String line = "";
	    String cvsSplitBy = ",";
	
	    try {
	        br = new BufferedReader(new FileReader(csvFile));
	        while ((line = br.readLine()) != null) {
	        	TreeMap<String, String> answerCard = new TreeMap<>();
	        	String[] answer = line.split(cvsSplitBy);
	        	int i = 0;
	        	answerCard.put(Integer.toString(i), answer[0]);
	        	for (String a : answer) {
	        		if (i != 0) {
	        		answerCard.put(Integer.toString(i), a);
	        		}
	        		i++;
	        	}
	        	answerCardCollection.add(answerCard);
	        }
	
	    } catch (FileNotFoundException fnfe) {
	        fnfe.printStackTrace();
	    } catch (IOException ioe) {
	        ioe.printStackTrace();
	    } finally {
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	   return answerCardCollection;
	}
}
