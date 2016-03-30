import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ExchangeComparator {

	public static final double CURRENCY_VALUE = 3000;
	public static String resultMarkAlfa;
	public static String resultMarkBeta;

	public static void main(String[] args) {
		Map<Object, Double> results = new HashMap<Object, Double>();
		BetaThread beta = new BetaThread(results);
		AlfaThread alfa = new AlfaThread(results);
		Thread alfaThread = new Thread(alfa);
		Thread betaThread = new Thread(beta);
		alfaThread.start();
		betaThread.start();

		checkStatus(alfaThread, betaThread);
		compareOffers(results);
	}

	public static void checkStatus(Thread alfa, Thread beta) {
		if (alfa.isAlive() || beta.isAlive()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
			}
			checkStatus(alfa, beta);
		} else
			return;
	}

	public static String compareMarks(String alfa, String beta) {

		return (alfa.equals(beta)) ? alfa
				: "Currency marks have different values";
	}

	public static void compareOffers(Map<Object, Double> resultsMap) {
		Entry<Object, Double> min = null;
		for (Entry<Object, Double> entry : resultsMap.entrySet()) {
			if (min == null || min.getValue() > entry.getValue()) {
				min = entry;
			}
		}
		
		String mark = compareMarks(resultMarkAlfa, resultMarkBeta);
		String name = min.getKey().toString()
				.substring(0, min.getKey().toString().indexOf('@'));

		String cheaperDeskription = String.join(" ", "better offer for", Double
				.toString(CURRENCY_VALUE), mark, "give you", name, min.getValue()
				.toString());
		System.out.println(cheaperDeskription);

	}
}
