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
@Table(name="goods")
public class Goods {

	@Id
	@Column (name="id_Goods")
	@GeneratedValue
	private Long id;

//	@Column
//	private Long id_TypeGoods;
//	@ManyToOne
//	private TypeGoods id_TypeGoods;
	@ManyToOne
	private TypeGoods types;


	@Column
	private String country;

	@Column
	private Double rest;

	@Column
	private Double price;


//	@ManyToOne
//	private Bill bills;

	@OneToMany (mappedBy = "goods")
	//@JoinTable( joinColumns={@JoinColumn(name="id_Goods", referencedColumnName="id_Goods")},
	//			inverseJoinColumns={@JoinColumn(name="goods", referencedColumnName="goods")})
	private List<Value> valueByGoods;


	public Long getId_Goods() {
//		if (id == null){
//			return
//		}
		return id;
	}
	public void setId_Goods(Long id_Goods) {
		this.id = id_Goods;
	}
//	public TypeGoods getId_TypeGoods() {
//		return id_TypeGoods;
//	}
//	public void setId_TypeGoods(TypeGoods id_TypeGoods) {
//		this.id_TypeGoods = id_TypeGoods;
//	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Double getRest() {
		return rest;
	}
	public void setRest(Double rest) {
		this.rest = rest;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public TypeGoods getTypes() {
		return types;
	}
	public void setTypes(TypeGoods types) {
		this.types = types;
	}
	public List<Value> getValueByGoods() {
		return valueByGoods;
	}
	public void setValueByGoods(List<Value> valueByGoods) {
		this.valueByGoods = valueByGoods;
	}
//	public Bill getBills() {
//		return bills;
//	}
//	public void setBills(Bill bills) {
//		this.bills = bills;
//	}

	public String getTypesStr() {
		return types.getType();
	}
	public void setTypesStr(String str) {
		this.types.setType(str);
	}

}
