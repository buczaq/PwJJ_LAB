package utility;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class ResultCalculator {
	int result = 0;
	public int calculateResult(Map<String, String> answerPattern, TreeMap<String, String> answerCard)
	{
		for (String key : answerPattern.keySet()) {
			if (answerPattern.get(key).equals(answerCard.get(key))) {
				result++;
			}
		}
		return result;
	}
}
