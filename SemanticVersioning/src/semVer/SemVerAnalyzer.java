package semVer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

/**
 * The SemVerAnalyzer program converts string input data into SemVer objects and then compares the given versions
 * outputting a String indicating whether the first version nuumber come before, after, or is equal to the second 
 * version number.
 * @author Daniel Ricks
 * @version 1.0.0
 * @since 2020-06-15
 *
 */
public class SemVerAnalyzer {
	private final static String equalOutput = "equal";
	private final static String beforeOutput = "before";
	private final static String afterOutput = "after";
	private final static String invalidOutput = "invalid";
	private boolean fileInput = false;
	private boolean directInput = false;
	private boolean testing = false;
	private String fileName = "";
	private String inputParam = "";
	
	public SemVerAnalyzer() {};
	
	//Compare input string to valid regex based on expected input for Semantic Versioning guidlines
	protected boolean validateInput(String input) {
		Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(-.*)*(\\+.*)*(\\s)+(\\d+)\\.(\\d+)\\.(\\d+)(-.*)*(\\+.*)*");
		Matcher match = pattern.matcher(input);
		return match.matches();
	}
	
	//Create SemVer objects based on input data and use the object comparator to determine order, returning correct string value
	protected String evaluateInput(String input) {
		String version1 = input.split("\\s+")[0];
		String version2 = input.split("\\s+")[1];
		SemVer ver1 = utils.stringToSemVer(version1);
		SemVer ver2 = utils.stringToSemVer(version2);
		if(ver1.compareTo(ver2) < 0) {
			return beforeOutput;
		}else if(ver1.compareTo(ver2) > 0) {
			return afterOutput;
		}else {
			return equalOutput;
		}
	}
	
	//Check if String is empty or null, if so, return empty String, if not and is valid input, return output of 
	//evaluteInput(String input), otherwise return invalidOutput value
	private String validateAndEvaluate(String input) {
		if(input== null || input.trim().isEmpty()) return "";
		else if(this.validateInput(input)) {
			return this.evaluateInput(input);
		}else {
			return invalidOutput;
		}
	}
	
	/** 
	 * Reads command line arguments passed to application and sets instance variables accordingly
	 * @param args  the array of command line args passed to the application
	 */
	public void processArgs(String[] args) {
		int i = 0;
		while(i < args.length && args[i].startsWith("-")) {
			if(args[i].equalsIgnoreCase("-file") && i < args.length - 1) {
				this.fileInput = true;
				this.fileName = args[++i];
			}else if(args[i].equalsIgnoreCase("-input") && i < args.length - 1) {
				this.directInput = true;
				this.inputParam = args[++i];
			}else if(args[i].equalsIgnoreCase("-test")) {
				this.testing = true;
				i++;
			}else {
				i++;
			}
		}
		
	}
	/**
	 * Analyzes the versioning information passed in as a string based on the current status of instance flags.
	 * Will either read information from user input on the command line, specified file path, or directly passed in
	 * arguments and prints to stdout indicating whether the first version is equal to, before, or after the second version
	 * @param args   	an array of Strings indicating input flag information and a String in the format 
	 * 					VERSION_NUMBER\\{whitespace\\}VERSION_NUMBER
	 * 
	 */
	public void analyze(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputData = "";
		
		//No special input flags indicated, get data from user on command line
		if(!this.fileInput && !this.directInput) {
			while(true) {
				System.out.print("Enter version data to analyze:  ");
				try {
					inputData = br.readLine().trim();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(this.validateInput(inputData)) {
					System.out.println(this.evaluateInput(inputData));
				}else {
					if(inputData.contains("stop") || inputData.contains("STOP")) break;
					if(inputData.trim().isEmpty()) continue;
					System.out.println(invalidOutput);
				}
			}
		}
		//File Input indicated, create new BufferedReader and read data from file line by line, aggregating the output string
		else if(fileInput) {
			BufferedReader reader;
			boolean verbose = true;
			String output = "";
			try {
				reader = new BufferedReader(new FileReader(fileName));
				String line = reader.readLine();
				while(line != null) {
					if(line.contains(",")) {
						line = line.split(",")[0];
						verbose = false;
					}
					if(verbose) {
						output = output + line + ": " + validateAndEvaluate(line) + "\n";
					} else {
						output = output + validateAndEvaluate(line) + "\n";
					}
					line = reader.readLine();
				}
				reader.close();
				System.out.println(output.trim());
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		//Direct input indicated, read input directly from arguments passed into application
		else {
			System.out.println(validateAndEvaluate(inputParam));
		}
	}
	
	/**
	 * Main class for SemVerAnalyzer.  Will direct to either the instance analyze function if the teseting flag is
	 * false, otherwise, returns the value of the analyze method based on given input parameters.
	 * @param args		an array of Strings indicating input flag information and a String in the format 
	 * 					VERSION_NUMBER\\{whitespace\\}VERSION_NUMBER
	 */
	public static void main(String[] args) {
		SemVerAnalyzer semAnalyze = new SemVerAnalyzer();
		semAnalyze.processArgs(args);
		if(semAnalyze.isTesting()) {
			JUnitCore junit = new JUnitCore();
			junit.addListener(new TextListener(System.out));
			junit.run(CompTest.class);
		} else {
			semAnalyze.analyze(args);
		}
	}
	/**
	 * Returns the value of the instance fileInput variable
	 * @return		boolean - whether the application should read input from file
	 */
	public boolean isFileInput() {
		return fileInput;
	}
	/**
	 * Set the value of the instance fileInput variable
	 * @param fileInput 	new value for fileInput
	 */
	public void setFileInput(boolean fileInput) {
		this.fileInput = fileInput;
	}
	/**
	 * Returns the value of the instance directInput variable
	 * @return		boolean - whether the application should read input from application parameters
	 */
	public boolean isDirectInput() {
		return directInput;
	}
	/**
	 * Set the value of the instance directInput variable
	 * @param directInput		boolean - new value for directInput 
	 */
	public void setDirectInput(boolean directInput) {
		this.directInput = directInput;
	}
	/**
	 * Returns the value of the instance isTesting variable
	 * @return 		boolean - whether the application should run in test mode
	 */
	public boolean isTesting() {
		return testing;
	}
	/**
	 * Set the value of the instance testing variable
	 * @param testing		boolean - new value for testing
	 */
	public void setTesting(boolean testing) {
		this.testing = testing;
	}
	/**
	 * Returns the value of the instance fileName variable
	 * @return		String -the fully qualified path of the expected input file
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Set the value of the instance fileName variable
	 * @param fileName		String - the fully qualified path of the expected input file
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * Returns the value of the instance inputParam variable
	 * @return		String - version information in the format VERSION_NUMBER\\{whitespace\\}VERSION_NUMBER
	 */
	public String getInputParam() {
		return inputParam;
	}
	/**
	 * Set the value of the instance inputParam variable
	 * @param inputParam		String - new value for inputParam
	 */
	public void setInputParam(String inputParam) {
		this.inputParam = inputParam;
	}

}
