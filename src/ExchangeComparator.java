import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExchangeComparator {

	public static final double CURRENCY_VALUE = 3000;
	public static double resultValueAlfa;
	public static double resultValueBeta;
	public static String resultMarkAlfa;
	public static String resultMarkBeta;

	public static void main(String[] args) {
		AlfaThread alfa = new AlfaThread();
		BetaThread beta = new BetaThread();
		Thread alfaThread = new Thread(alfa);
		Thread betaThread = new Thread(beta);
		alfaThread.start();
		betaThread.start();

		checkStatus(alfaThread, betaThread);

		String result = ExchangeComparator.compareOffers(resultValueAlfa,
				resultValueBeta);
		System.out.println(result);
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

	public static String compareOffers(double alfa, double beta) {
		String mark = compareMarks(resultMarkAlfa, resultMarkBeta);
		String cheaperDeskription = String.join(" ", "better offer for",
				Double.toString(CURRENCY_VALUE), mark, "give you ");

		return (alfa < beta) ? cheaperDeskription + "Alfa " + alfa
				: cheaperDeskription + "Beta " + beta;
	}

	private static String compareMarks(String alfa, String beta) {

		return (alfa.equals(beta)) ? alfa
				: "Currency marks have different values";
	}
}

class AlfaThread implements Runnable {
	WebDriver driver = new FirefoxDriver();
	ExchangeAlfa exchangeAlfa = new ExchangeAlfa(driver);

	public void run() {
		driver.manage().window().maximize();
		ExchangeComparator.resultValueAlfa = exchangeAlfa.exchangeCurrency(
				Currencies.PLN, Currencies.GBP,
				ExchangeComparator.CURRENCY_VALUE);
		ExchangeComparator.resultMarkAlfa = exchangeAlfa.getCurrencyMark();
		driver.close();
	}
}

class BetaThread implements Runnable {
	WebDriver driver = new FirefoxDriver();
	ExchangeBeta exchangeBeta = new ExchangeBeta(driver);

	public void run() {
		driver.manage().window().maximize();
		ExchangeComparator.resultValueBeta = exchangeBeta.exchangeCurrency(
				Currencies.PLN, Currencies.GBP,
				ExchangeComparator.CURRENCY_VALUE);
		ExchangeComparator.resultMarkBeta = exchangeBeta.getCurrencyMark();
		driver.close();
	}
}