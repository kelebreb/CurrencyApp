import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


class BetaThread implements Runnable {
	WebDriver driver = new FirefoxDriver();
	ExchangeBeta exchangeBeta = new ExchangeBeta(driver);
	Map<Object, Double> betaHashMap;
	
	BetaThread(Map<Object, Double> results) {
		betaHashMap = results;
	}

	public void run() {
		driver.manage().window().maximize();
		Double resultValue = exchangeBeta.exchangeCurrency(
				Currencies.PLN, Currencies.GBP,
				ExchangeComparator.CURRENCY_VALUE);
		betaHashMap.put(exchangeBeta, resultValue);
		ExchangeComparator.resultMarkBeta = exchangeBeta.getCurrencyMark();
		driver.close();
	}
}