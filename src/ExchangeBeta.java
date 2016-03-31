import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExchangeBeta {

	private static String website;
	private static final String CURRENCY_AMOUNT_INPUT = "calcAmountOut";
	private static final String CURRENCY_MARK_INPUT = "calcCurrencyOut";
	private static final String CURRENCY_MARK_OUTPUT = "calcCurrencyIn";
	private static final String CALCULATE_RESULT_BUTTON = "calcSubmit";
	private static final String RESULT_RATE = "resultRate";
	private static final String CURRENCY_RESULT = "calcAmountIn";
	private static String currencyMark = "Currency not set yet";

	private WebDriver driver;
	Properties prop = new Properties();
	FileInputStream input = null;

	public ExchangeBeta(WebDriver driver) {
		this.driver = driver;
		try {
			input = new FileInputStream(".properties");
			// load a properties file
			prop.load(input);
			// get the property value = website address
			website = prop.getProperty("beta");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public double exchangeCurrency(Currencies originalCurrency,
			Currencies newCurrency, double amount) {
		openWebsiteInBrowser();
		setValueToExchange(amount);
		selectOriginalCurrency(originalCurrency);
		selectNewCurrency(newCurrency);
		return recalculateValue(amount);
	}

	public String getCurrencyMark() {
		return currencyMark;
	}

	private void openWebsiteInBrowser() {
		driver.get(website);
	}

	private double recalculateValue(double calculatedAmount) {
		driver.findElement(By.id(CALCULATE_RESULT_BUTTON)).click();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions
				.visibilityOfElementLocated(By.id(RESULT_RATE)));
		String result = driver.findElement(By.id(CURRENCY_RESULT))
				.getAttribute("value");
		return Helpers.makeResultDouble(result);
	}

	private void selectOriginalCurrency(Currencies fixCurrency) {
		WebElement currencyItem = driver.findElement(By
				.id(CURRENCY_MARK_OUTPUT));
		Select dropdown = new Select(currencyItem);
		dropdown.selectByVisibleText(fixCurrency.getCurrencies());
	}

	private void selectNewCurrency(Currencies fixCurrency) {
		WebElement currencyItem = driver
				.findElement(By.id(CURRENCY_MARK_INPUT));
		Select dropdown = new Select(currencyItem);
		dropdown.selectByVisibleText(fixCurrency.getCurrencies());
		currencyMark = fixCurrency.getCurrencies();
	}

	private void setValueToExchange(double value) {
		driver.findElement(By.id(CURRENCY_AMOUNT_INPUT)).clear();
		driver.findElement(By.id(CURRENCY_AMOUNT_INPUT)).sendKeys(
				Double.toString(value));
	}
}