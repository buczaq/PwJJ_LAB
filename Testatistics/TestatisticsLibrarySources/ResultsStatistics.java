package utility;

import java.util.TreeMap;
import java.util.Vector;

/**
 * 
 * Klasa opracowująca statystyki wyników
 */
public class ResultsStatistics {
        
        /**
         * 
         * Przygotowuje dane do histogramu z ocenami
         * @param markHistogramData dane do histogramu
         */
	public void fillMarkHistogram(TreeMap<Float, Integer> markHistogramData)
	{
		markHistogramData.put(2.0f, 0);
		markHistogramData.put(3.0f, 0);
		markHistogramData.put(3.5f, 0);
		markHistogramData.put(4.0f, 0);
		markHistogramData.put(4.5f, 0);
		markHistogramData.put(5.0f, 0);
		markHistogramData.put(5.5f, 0);
	}
        
        /**
         * 
         * Przygotowuje dane do histogramu punktowego
         * @param answerPattern szablon odpowiedzi - potrzebny, by określić wielkość histogramu
         * @param histogramData dane do histogramu
         */
        public void fillResultsHistogram(TreeMap<String, String> answerPattern, TreeMap<Integer, Integer> histogramData)
        {
            int size = answerPattern.size();
            for (int i = 0; i < size; i++) {
                histogramData.put(i, 0);
            }
        }
	
        /**
         * 
         * Oblicza dane do histogramu punktowego
         * @param answerPattern         szablon odpowiedzi
         * @param answerCardCollection  zbiór kart odpowiedzi
         * @return                      dane umożliwiające wyrysowanie histogramu punktowego
         */
	public TreeMap<Integer, Integer> createResultsHistogram(TreeMap<String, String> answerPattern, Vector<TreeMap<String, String>> answerCardCollection)
	{
            TreeMap<Integer, Integer> histogramData = new TreeMap<Integer, Integer>();
		for (TreeMap<String, String> a : answerCardCollection) {
				ResultCalculator r = new ResultCalculator();
				Integer numberOfPoints = r.calculateResult(answerPattern, a);
				Integer s = histogramData.get(numberOfPoints);
				if (s != null) {
					histogramData.put(numberOfPoints, ++s);
				} else histogramData.put(numberOfPoints, 1);
		}
		return histogramData;
	}
	
        /**
         * 
         * Oblicza dane do histogramu z ocenami
         * @param answerPattern         szablon odpowiedzi
         * @param answerCardCollection  zbiór kart odpowiedzi
         * @return                      dane umożliwiające wyrysowanie histogramu ocen
         */
	public TreeMap<Float, Integer> createMarksHistogram(TreeMap<String, String> answerPattern, Vector<TreeMap<String, String>> answerCardCollection)
	{
            	TreeMap<Float, Integer> markHistogramData = new TreeMap<>();
                fillMarkHistogram(markHistogramData);
		TreeMap<Integer, Integer> results = createResultsHistogram(answerPattern, answerCardCollection);
		int maxPoints = answerPattern.size();
		int treshold30 = (int)Math.ceil(0.5 * maxPoints);
		int treshold35 = (int)Math.ceil(0.625 * maxPoints);
		int treshold40 = (int)Math.ceil(0.75 * maxPoints);
		int treshold45 = (int)Math.ceil(0.825 * maxPoints);
		int treshold50 = (int)Math.ceil(0.9 * maxPoints);
                int treshold55 = maxPoints;
		for (int i : results.keySet()) {
			if (i < treshold30) {
				markHistogramData.put(2.0f, markHistogramData.get(2.0f) + results.get(i));
			}
			else if (i >= treshold30 && i < treshold35) {
				markHistogramData.put(3.0f, markHistogramData.get(3.0f) + results.get(i));
			} else if (i >= treshold35 && i < treshold40) {
				markHistogramData.put(3.5f, markHistogramData.get(3.5f) + results.get(i));
			} else if (i >= treshold40 && i < treshold45) {
				markHistogramData.put(4.0f, markHistogramData.get(4.0f) + results.get(i));
			} else if (i >= treshold45 && i < treshold50) {
				markHistogramData.put(4.5f, markHistogramData.get(4.5f) + results.get(i));
			} else if (i >= treshold50 && i < treshold55) {
				markHistogramData.put(5.0f, markHistogramData.get(5.0f) + results.get(i));
			} else {
				markHistogramData.put(5.5f, markHistogramData.get(5.5f) + results.get(i));
			}
		}
		return markHistogramData;
	}
        
        /**
         * 
         * Tworzy histogram informujący ile odpowiedzi na dane pytanie było poprawnych
         * @param answerPattern         szablon odpowiedzi
         * @param answerCardCollection  zbiór kart odpowiedzi
         * @return                      dane umożliwiające wyrysowanie histogramu dla poszczególnych pytań
         */
        public TreeMap<Integer, Integer> createGoodAnswersHistogram(TreeMap<String, String> answerPattern, Vector<TreeMap<String, String>> answerCardCollection)
        {
            TreeMap<Integer, Integer> goodAnswersHistogramData = new TreeMap<>();
            for (String key : answerPattern.keySet()) {
                for (TreeMap<String, String> ac : answerCardCollection) {
                    if (ac.get(key).equals(answerPattern.get(key))) {
                        if (goodAnswersHistogramData.get(Integer.parseInt(key)) != null) {
                            goodAnswersHistogramData.put(Integer.parseInt(key), (goodAnswersHistogramData.get(Integer.parseInt(key)) + 1));
                        } else goodAnswersHistogramData.put(Integer.parseInt(key), 1);
                    }
                }
            }
            return goodAnswersHistogramData;
        }
}
