import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExchangeAlfa {

	private static String website;
	private static final String CURRENCY_AMOUNT_INPUT = "c_amount";
	private static final String CURRENCY_MARK_INPUT = "c_currency";
	private static final String CALCULATE_RESULT_BUTTON = "calcBtn";
	private static final String ORIGINAL_AMOUNT = "calc_amount";
	private static final String CURRENCY_RESULT = "calc_value";
	private static String currencyMark = "Currency not set yet";

	private WebDriver driver;
	Properties prop = new Properties();
	InputStream input = null;

	public ExchangeAlfa(WebDriver driver) {
		this.driver = driver;
		try {
			input = new FileInputStream(".properties");
			// load a properties file
			prop.load(input);
			// get the property value = website address
			website = prop.getProperty("alfa");
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
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(
				By.id(ORIGINAL_AMOUNT), Double.toString(calculatedAmount)));

		String result = driver.findElement(By.id(CURRENCY_RESULT)).getText();

		return Helpers.makeResultDouble(result);
	}

	private void setValueToExchange(double value) {
		int newValue = (int)value;
		driver.findElement(By.id(CURRENCY_AMOUNT_INPUT)).clear();
		driver.findElement(By.id(CURRENCY_AMOUNT_INPUT)).sendKeys(
				Integer.toString(newValue));
	}

	private void selectNewCurrency(Currencies fixCurrency) {
		WebElement currencyItem = driver
				.findElement(By.id(CURRENCY_MARK_INPUT));
		Select dropdown = new Select(currencyItem);
		dropdown.selectByValue(fixCurrency.getCurrencies());
		currencyMark = fixCurrency.getCurrencies();
	}
}
