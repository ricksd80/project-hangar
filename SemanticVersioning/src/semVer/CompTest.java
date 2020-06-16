package semVer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.Test;

public class CompTest {
	
	private static HashMap<String, String> inputMap = new HashMap<String, String>();
	
	@Test
	public void test() {
		SemVerAnalyzer semA = new SemVerAnalyzer();
		inputMap.put("1.0.0   1.0.0", "equal");
		inputMap.put("1.0.0+98567   1.0.0+89675", "equal");
		inputMap.put("1.0.0-alpha   1.0.0-beta", "before");
		inputMap.put("1.0.0", "invalid input");
		inputMap.put("1.0.0   1.0", "invalid input");
		inputMap.put("1.0.1   1.0.0", "after");
		inputMap.put("1.0.0   1.0.1", "before");
		inputMap.put("1.1.0   1.2.0", "before");
		inputMap.put("1.1.0-1.2.3   1.1.0-1.2.4", "before");
		inputMap.put("-1.0.0   1.0.0", "invalid input");
		inputMap.put("1.0.0   1.0.5", "before");
		inputMap.put("2.3.4-1.2.3+897566 2.3.4-1.2.3+743578", "equal");
		inputMap.put("0.3.4                 0.3.4-alpha", "after");
		inputMap.put("0.3.4-alpha                 0.3.4", "before");
		for(String key : inputMap.keySet()) {
			String [] arguments = new String[]{"-input", key};
			semA.processArgs(arguments);
			if(inputMap.get(key).contentEquals("invalid input")) {
				assertEquals(false, semA.validateInput(key));
			} else {
				assertEquals(inputMap.get(key), semA.evaluateInput(key));
			}
		}
		
		/*
		String fileName = "C:\\Users\\Daniel\\eclipse-workspace\\SemanticVersioning\\src\\semVer\\inputFile.txt";
		String [] arguments = new String[] {"-file", fileName};
		assertEquals("equal\nafter", SemVerAnalyzer.analyze(arguments));
		*/
	}

}
