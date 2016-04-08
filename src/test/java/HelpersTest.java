import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class HelpersTest {

	private static final Double DELTA = 0.001;

	@Test
	public void shouldParseTheNumberWithComaSeparator() {
		double expectedResult = 1000.01;
		double actualResult = Helpers.makeResultDouble("1000,01");
		assertEquals(actualResult, expectedResult, DELTA);
	}

	@Test
	public void shouldParseTheNumberWithDotSeparator() {
		double expectedResult = 1000.01;
		double actualResult = Helpers.makeResultDouble("1000.01");
		assertEquals(actualResult, expectedResult, DELTA);
	}

	@Test
	public void shouldParseTheNaturalNumber() {
		double expectedResult = 23;
		double actualResult = Helpers.makeResultDouble("23");
		assertEquals(actualResult, expectedResult, DELTA);
	}

	@Test
	public void shouldReturnZero() {
		double expectedResult = 0;
		double actualResult = Helpers.makeResultDouble("0");
		assertEquals(actualResult, expectedResult, DELTA);
	}

}
