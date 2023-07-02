package pl.home.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="typegoods")
public class TypeGoods {

	@Id
	@Column (name = "id_TypeGoods")
	@GeneratedValue
	private Long id;

	@Column
	private String type;

	@OneToMany (mappedBy = "types")
	private List<Goods> goodsByType;

	@OneToMany (mappedBy = "types")
	private List<Property> propertyByType;


	public List<Property> getProperties() {
		return propertyByType;
	}
	public void setPropertyByType(List<Property> propertyByType) {
		this.propertyByType = propertyByType;
	}
	public Long getId_TypeGoods() {
		return id;
	}
	public void setId_TypeGoods(Long id_TypeGoods) {
		this.id = id_TypeGoods;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Goods> getGoodsByType() {
		return goodsByType;
	}
	public void setGoodsByType(List<Goods> goodsByType) {
		this.goodsByType = goodsByType;
	}

	public TypeGoods(String type) {
		this.type = type;
	}

	public TypeGoods() {

	}
}
