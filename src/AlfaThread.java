import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

class AlfaThread implements Runnable {
	WebDriver driver = new FirefoxDriver();
	ExchangeAlfa exchangeAlfa = new ExchangeAlfa(driver);
	Map<Object, Double> alfaHashMap;
	
	AlfaThread(Map<Object, Double> results) {
		alfaHashMap = results;
	}
	
	public void run() {
		driver.manage().window().maximize();
		Double resultValue = exchangeAlfa.exchangeCurrency(
				Currencies.PLN, Currencies.GBP,
				ExchangeComparator.CURRENCY_VALUE);
		alfaHashMap.put(exchangeAlfa, resultValue);
		ExchangeComparator.resultMarkAlfa = exchangeAlfa.getCurrencyMark();
		driver.close();
	}
}