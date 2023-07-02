package pl.home.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="discount")
public class Discount {

	@Id
	@Column (name = "id_Discount")
	@GeneratedValue
	private Long id;

	@ManyToOne
	private TypeGoods types;

	@Column
	private Double value;

	@Column
	private String pv;

	@Column//(columnDefinition = "DATE")
	private Date dateIn;

	@Column//(columnDefinition = "DATE")
	private Date dateOut;

	@OneToMany
	@JoinColumn (name = "TypeGoods")
	private List<TypeGoods> typeGoods;

	@OneToMany
	@JoinColumn (name = "id_Discount")
	private List<Bill> billsByDiscount;

	public Long getId_Discount() {
		return id;
	}

	public void setId_Discount(Long id_Discount) {
		this.id = id_Discount;
	}

	public TypeGoods getTypes() {
		return types;
	}

	public void setTypes(TypeGoods types) {
		this.types = types;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getPv() {
		return pv;
	}
	public void setPv(String pv){
		this.pv = pv;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public List<TypeGoods> getTypeGoods() {
		return typeGoods;
	}

	public void setTypeGoods(List<TypeGoods> typeGoods) {
		this.typeGoods = typeGoods;
	}
	public List<Bill> getBillsByDiscount() {
		return billsByDiscount;
	}

	public void setBillsByDiscount(List<Bill> billsByDiscount) {
		this.billsByDiscount = billsByDiscount;
	}

	public Discount(){

	}

	public Discount(Date dateIn, Date dateOut, String pv, Double value, TypeGoods type) {
		this.dateOut = dateOut;
		this.dateIn = dateIn;
		this.pv = pv;
		this.value = value;
		this.types = type;
	}

}
