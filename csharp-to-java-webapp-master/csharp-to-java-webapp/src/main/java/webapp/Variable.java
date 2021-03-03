package webapp;

public class Variable {
	String type, value, name;
	
	Variable() {
		type = "-1";
		value = "-1";
		name = "-1";
	}
	
	Variable(String type, String value, String name) {
		this.type = type;
		this.value = value;
		this.name = name;
	}
	
	public String getType() {
		return this.type;
	}
	public String getValue() {
		return this.value;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
