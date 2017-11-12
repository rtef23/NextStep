/**
 copyright by NAVER corp
 writer : 송주용
 date : 2017-11-12
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author 송주용
 */
public class StringCalculatorTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private StringCalculator stringCalculator;

	@Before
	public void setup() {
		stringCalculator = new StringCalculator();
	}

	@Test
	public void addTest() {
		//given
		String testCase = "1,2:3:4";

		//when
		int result = stringCalculator.add(testCase);

		//then
		Assert.assertEquals(10, result);
	}

	@Test
	public void addTestWithCumstomSeperator() {
		//given
		String testCase = "//,;\n1;2;3;4";

		//when
		int result = stringCalculator.add(testCase);

		//then
		Assert.assertEquals(10, result);
	}

	@Test
	public void addTestWithMinus() {
		//given
		expectedException.expect(RuntimeException.class);
		String testCase = "1,-1,2,3";

		//when
		int result = stringCalculator.add(testCase);

		//then
		//Expect RuntimeException
	}

	@Test
	public void addTestWithEmptyTestCase() {
		//given
		String testCase = "";

		//when
		int result = stringCalculator.add(testCase);

		//then
		Assert.assertEquals(0, result);
	}

	@Test
	public void addTestWithSingleInteger() {
		//given
		String testCase = "1";

		//when
		int result = stringCalculator.add(testCase);

		//then
		Assert.assertEquals(1, result);
	}
}
