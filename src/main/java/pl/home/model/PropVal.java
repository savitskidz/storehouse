package pl.home.model;

public class PropVal {
	public PropVal() {

	}

	public PropVal(Property property, Value value) {
		this.property = property;
		this.value = value;
	}

	private Property property;
	private Value value;

	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}

	public void dataForTable() {

	}

	public void setPropStr(String str) {
		this.property.setProperty(str);
	}

	public String getPropStr() {
		return this.property.getProperty();
	}

	public void setValueV(String id) {
		this.value.setValue(id);
	}

	public String getValueV() {
		return this.value.getValue();
	}

}
