package semVer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class utils {
	
	//regex pattern representing valid input format
	public static Pattern semVersion = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(-.*)*(\\+.*)*");

	public static SemVer stringToSemVer(String version) {
		if(validateString(version)) {
			int major = getValueFromString(version, "major");
			int minor = getValueFromString(version, "minor");
			int patch = getValueFromString(version, "patch");
			String prerelease = getSubString(version, "prerelease");
			String metadata = getSubString(version, "metadata");
			return new SemVer(major, minor, patch, prerelease, metadata);
		} else {
			return null;
		}
	}
	
	public static boolean validateString(String version) {
		Matcher matcher = semVersion.matcher(version);
		return matcher.matches();
	}
	
	public static int getValueFromString(String input, String target) {
		String[] values = input.split("\\.");
		if(target.equals("major")) { 
			return Integer.valueOf(values[0]);
		}else if(target.equals("minor")) {
			return Integer.valueOf(values[1]);
		}else if(target.equals("patch")) {
			values[2] = values[2].contains("-") ? values[2].split("-")[0] : values[2];
			values[2] = values[2].contains("+") ? values[2].split("\\+")[0] : values[2];
			return Integer.valueOf(values[2]);
		}else {
			return -1;
		}
	}
	
	public static String getSubString(String version, String target) {
		String returnValue = "";
		if(target.equals("prerelease")) {
			returnValue = version.contains("-") ? version.split("-")[1] : returnValue;
			returnValue = returnValue.contains("+") ? returnValue.split("\\+")[0] : returnValue; 
		}
		if(target.contentEquals("metadata")) {
			returnValue = version.contains("+") ? version.split("\\+")[1] : returnValue;
		}
		return returnValue;
	}
	
}
