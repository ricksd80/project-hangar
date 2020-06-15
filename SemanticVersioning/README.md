# SemanticVersioning

SemanticVersioning is a java library for creating SemVer objects and comparing the versioning data between 2 different version inputs

## Usage

<b>An executable jar file is included with the package in the target folder</b>
<p style = "text-indent: 20px">to run from the Unix command line:</p>
	<b><p style = "text-indent: 40px">java -jar target/semVer_1.0.0.jar "ARGUMENTS"</b></p>
	
ARGUMENTS:<br>
	<b>&emsp;&emsp;-test</b>&emsp;run application in test mode using test input found in src/CompTest.java<br>
	<b>&emsp;&emsp;-file  "FILENAME"</b>&emsp;read input data from given FILENAME.  File data should be in format "VERSION\{whitespace\}VERSION,testValue" <br>&emsp;&emsp;&emsp;&emsp;or "VERSION\{whitespace\}VERSION"<br>
	<b>&emsp;&emsp;-input "INPUT"</b>&emsp;read input passed directly in as a parameter EXAMPLE:   java -jar semVer_1.0.0.jar -input "1.0.0 1.1.0"<br>
	
If file is run without command line arguments, the module will prompt the user to enter input to check on the command line. To terminate application, enter "stop" or "STOP" on the command line.

## Testing

All test inputs can be viewed in the file src/CompTest.java<br>
When running the application in test mode, input should be in the format "STRING TO TEST","EXPECTED OUTPUT"<br>
&emsp;&emsp;EXAMPLE: "1.0.0      1.0.1,before"
