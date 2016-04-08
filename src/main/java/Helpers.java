public class Helpers {

	public static double makeResultDouble(String valueToConvert) {
		valueToConvert = (valueToConvert.replace(',', '.')).replaceAll("\\s+",
				"");
		return Double.parseDouble(valueToConvert);
	}
	
}
