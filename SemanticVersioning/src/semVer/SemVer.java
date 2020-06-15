package semVer;

public class SemVer implements Comparable<SemVer>{
	private int major;
	private int minor;
	private int patch;
	private String preRelease;
	private String metadata;
	
	public SemVer(int x, int y, int z, String pre, String meta) {
		this.setMajor(x);
		this.setMinor(y);
		this.setPatch(z);
		this.setPreRelease(pre);
		this.setMetadata(meta);
	}
	
	public SemVer(int x, int y, int z, String pre) {
		this(x, y, z, pre, "");
	}
	public SemVer(int x, int y, int z) {
		this(x, y, z, "", "");
	}
	
	public int getMajor() {
		return major;
	}
	
	public void setMajor(int major) {
		this.major = major;
	}
	
	public int getMinor() {
		return minor;
	}
	
	public void setMinor(int minor) {
		this.minor = minor;
	}
	
	public int getPatch() {
		return patch;
	}
	
	public void setPatch(int patch) {
		this.patch = patch;
	}
	
	public String getPreRelease() {
		return preRelease;
	}
	
	public void setPreRelease(String preRelease) {
		this.preRelease = preRelease;
	}
	
	public String getMetadata() {
		return metadata;
	}
	
	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	/**
	 * Compare this semVer object to another given object.  Comparisons are based on instance major value, then minor, then patch, and
	 * finally the pre rekease values. Values are calculated im ascending numeric order for integer values, and lexicographically for 
	 * String values 
	 * @return  	int - a value less than 0 if object 1 comes before object 2, greater than 0 if object 1 comes after object 2, and 0 if 
	 * 				they are equal
	 */
	@Override
	public int compareTo(SemVer o) {
		if(this.getMajor() != o.getMajor()) return this.getMajor() - o.getMajor();
		if(this.getMinor() != o.getMinor()) return this.getMinor() - o.getMinor();
		if(this.getPatch() != o.getPatch()) return this.getPatch() - o.getPatch();
		if(!this.getPreRelease().equals(o.getPreRelease())) {
			if(this.getPreRelease().equals("")) {
				return 1;
			} else if(o.getPreRelease().equals("")) {
				return -1;
			} else {
				return this.getPreRelease().compareTo(o.getPreRelease());
			}
		}
		return 0;
	}

}
