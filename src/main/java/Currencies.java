public enum Currencies {
	GBP("GBP"), PLN("PLN"), USD("USD"), EUR("EUR"), CHF("CHF"), CZK("CZK"), DKK(
			"DKK"), NOK("NOK"), SEK("SEK"), AUD("AUD"), CAD("CAD"), JPY("JPY"), BGN(
			"BGN"), HRK("HRK"), HUF("HUF"), RUB("RUB");

	Currencies(String currency) {
		this.currency = currency;
	}

	private String currency;

	public String getCurrencies() {
		return currency;
	}
}