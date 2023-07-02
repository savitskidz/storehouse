package pl.home.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="property")
public class Property {

	@Id
	@Column (name = "id_Property")
	@GeneratedValue
	private Long id;

//	@Column
//	private Long id_TypeGoods;
	@ManyToOne
	private TypeGoods types;

	@Column
	private String property;

//	@ManyToOne()
//	@JoinColumn (name = "goods")
//	private Goods goods;

	@OneToMany (mappedBy = "properties")
//	@JoinTable (joinColumns={@JoinColumn(name="id_Property", referencedColumnName="id_Property")},
//				 inverseJoinColumns={@JoinColumn(name="value", referencedColumnName="value")})
	private List<Value> valuesByProperty;




	public Long getId_Property() {
		return id;
	}
	public void setId_Property(Long id_Property) {
		this.id = id_Property;
	}
//	public Long getId_TypeGoods() {
//		return id_TypeGoods;
//	}
//	public void setId_TypeGoods(Long id_TypeGoods) {
//		this.id_TypeGoods = id_TypeGoods;
//	}
	public TypeGoods getTypes() {
		return types;
	}
	public void setTypes(TypeGoods types) {
		this.types = types;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
//	public Goods getGoods() {
//		return goods;
//	}
//	public void setGoods(Goods goods) {
//		this.goods = goods;
//	}
	public List<Value> getValuesByProperty() {
		return valuesByProperty;
	}
	public void setValuesByProperty(List<Value> valuesByProperty) {
		this.valuesByProperty = valuesByProperty;
	}
}
