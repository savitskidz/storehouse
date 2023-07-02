package pl.home.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="value")
public class Value {

	@Id
	@Column (name = "id_Value")
	@GeneratedValue
	private Long id;

	@Column
	private Long goods;

	@ManyToOne
	@JoinColumn(name="property_id")
	private Property properties;


	@Column
	private String value;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getGoods() {
		return goods;
	}


	public void setGoods(Long goods) {
		this.goods = goods;
	}


	public Property getProperties() {
		return properties;
	}


	public void setProperties(Property properties) {
		this.properties = properties;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

}
