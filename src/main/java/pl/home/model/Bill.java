package pl.home.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Bill {

	@Id
	@Column (name = "id_Bill")
	@GeneratedValue
	private Long id;

	@Column
	private Long bill;

	@ManyToOne //(mappedBy = "id")
	@JoinColumn (nullable = false)
	private Goods goodsByBill;

	@ManyToOne //(mappedBy = "id")
	@JoinColumn (nullable = false)
	private Discount discByBill;

	@Column
	private Double kolvo;

	@Column
	private Double price;

	@Column
	private Date dateBill;

	@Column
	private Double disc;






	public Long getId_Bill() {
		return id;
	}
	public void setId_Bill(Long id_Bill) {
		this.id = id_Bill;
	}
	public Long getBill() {
		return bill;
	}
	public void setBill(Long bill) {
		this.bill = bill;
	}
	public Double getKolvo() {
		return kolvo;
	}
	public void setKolvo(Double kolvo) {
		this.kolvo = kolvo;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getDateBill() {
		return dateBill;
	}
	public void setDateBill(Date dateBill) {
		this.dateBill = dateBill;
	}
	public Goods getGoodsByBill() {
		return goodsByBill;
	}
	public void setGoodsByBill(Goods goodsByBill) {
		this.goodsByBill = goodsByBill;
	}
//	public Long getDiscByBill() {
//		System.out.println("discByBill.getId_Discount() "+discByBill.getId_Discount());
//		if (discByBill.getId_Discount() == null)
//			return 0l;
//		else
//			return discByBill.getId_Discount();
//	}
	public Long getDiscByBill() {
		return discByBill.getId_Discount();
	}
	public void setDiscByBill(Discount discByBill) {
		this.discByBill = discByBill;
	}

	public Long getGoodsId() {
		return goodsByBill.getId_Goods();
	}
	public void setGoodsId(Long id) {
		this.goodsByBill.setId_Goods(id);
	}

	public Double getDisc() {
//		return price*discByBill.getValue()/100d;
		return disc;
	}
	public void setDisc(Double disc) {
		this.disc = disc;
	}


}
