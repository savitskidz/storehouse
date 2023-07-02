package pl.home.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


import pl.home.view.EditMainWindowController;

public class Meth {

	Date d = new Date();
	EditMainWindowController editMwc;

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("PerUnit");
	EntityManager em = emf.createEntityManager();

//-----------------------------Meth TypeGoods------------------------------

	public void addType(TypeGoods type) {
		em.getTransaction().begin();
		em.persist(type);
		em.getTransaction().commit();
	}
//	public void addType(TypeGoods type) {
///		em.clear();
//		em.persist(type);
//		em.flush();
//	}

	public void addTypeStatic() {
//		em.clear();
		em.getTransaction().begin();
		em.persist(new TypeGoods("storage"));
		em.persist(new TypeGoods("input device"));
		em.persist(new TypeGoods("monitor"));
		em.persist(new TypeGoods("periphery"));
		em.persist(new TypeGoods("accessories"));
		em.persist(new TypeGoods("all goods"));
		em.getTransaction().commit();

	}

	public void addDiscountStatic() {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, 10);
//		em.clear();
		em.getTransaction().begin();
		em.persist(new Discount(d, c.getTime(), null, 0d, getType(6l)));
		em.persist(new Discount(d, c.getTime(), "manufacturer", 10d, getType(1l)));
		em.persist(new Discount(d, c.getTime(), "color", 40d, getType(2l)));
		em.persist(new Discount(d, c.getTime(), "diagonal", null, getType(3l)));
		em.persist(new Discount(d, c.getTime(), "default", 5d, getType(6l)));
		c.add(Calendar.DATE,  -20);
		em.persist(new Discount(d,c.getTime(), "default", 4d, getType(6l)));
		em.persist(new Discount(c.getTime(), d, "default", 3d, getType(6l)));
		em.getTransaction().commit();
	}

	public void deleteType(long id) {
		em.clear();
		em.getTransaction().begin();
		em.remove(getType(id));
		em.getTransaction().commit();
	}

	public TypeGoods getType(long id) {
		em.clear();
		TypeGoods tg = em.find(TypeGoods.class, id);
//		System.out.println("!!!getType(long "+id+")"+tg.getType()+" - "+tg.getId_TypeGoods());
		return tg;
	}

	public void updateType(TypeGoods type) {
		em.clear();
		em.getTransaction().begin();
		em.merge(type);
		em.getTransaction().commit();
	}

	public List<TypeGoods> getAllType() {							//получить все типы товаров
		em.clear();
		@SuppressWarnings("unchecked")
		List<TypeGoods> listType = (List<TypeGoods>) em.createQuery("From TypeGoods").getResultList();
		for (TypeGoods pl: listType ){
			System.out.println(pl);
		}
		return listType;
	}

	public List<String> getTypeString() {							//получить все string типов товаров
		em.clear();
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) em.createQuery("Select type From TypeGoods").getResultList();
		for (String pl: list ){
			System.out.println(pl);
		}
		return list;
	}

	public Long getIdSelectedType(String string) {					//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ id пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅ string
		em.clear();
		Long id = (Long) em.createQuery("Select min(c.id) From TypeGoods c "
				+ "Where c.type=:arg").setParameter("arg", string).getSingleResult();
		return id;
	}

	public TypeGoods getSelectedType(String string) {					//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ typegoods пїЅпїЅ пїЅпїЅпїЅ string
		em.clear();
		TypeGoods tp = (TypeGoods) em.createQuery("From TypeGoods c Where c.type=:arg").setParameter("arg", string).getSingleResult();
		return tp;
	}

	public String getStringSelectedType(TypeGoods tp) {				//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ string пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅ id
		em.clear();
		String str = (String) em.createQuery("Select c.type From TypeGoods c "
				+ "Where c.id=:arg").setParameter("arg", tp.getId_TypeGoods()).getSingleResult();
		return str;
	}

	public Boolean isDataInTypeGoods() {							//пїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅ typegoods
		Long t = (Long) em.createQuery("Select count(c.id) From TypeGoods c").getSingleResult();
		if (t == 0)
			return true;
		else
			return false;
	}

	public List<Goods> goodsDelFromBill(Long bill) {
		@SuppressWarnings("unchecked")
		List<Goods> list = em.createQuery("Select b.goodsByBill From Bill b Where b.bill=:arg").setParameter("arg", bill).getResultList();
		return list;
	}

//-----------------------------Meth Goods--------------------------------------------

	public void addGoods(Goods goods) {
		em.clear();
		em.getTransaction().begin();
		em.persist(goods);
		em.getTransaction().commit();
//		em.flush();
	}

	public void deleteGoods(long id) {
		em.clear();
		em.getTransaction().begin();
		em.remove(getGoods(id));
		em.getTransaction().commit();
	}

	public Goods getGoods(long id) {
		em.clear();
		return em.find(Goods.class, id);
	}

	public void updateGoods(Goods goods) {
		em.clear();
		em.getTransaction().begin();
		em.merge(goods);
		em.getTransaction().commit();
	}

	public List<Goods> getAllGoods() {							//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ goods
		em.clear();
		@SuppressWarnings("unchecked")
		List<Goods> listGoods = (List<Goods>) em.createQuery("from Goods").getResultList();
		for (Goods pl: listGoods ){
			System.out.println(pl);
		}
		return listGoods;
	}

	public Goods getGoodsById(Long i) {							//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ goods пїЅпїЅ пїЅпїЅпїЅ id
		em.clear();
//		@SuppressWarnings("unchecked")
		Goods goods = null;
		try {
			goods = (Goods) em.createQuery("From Goods Where id=:arg").setParameter("arg", i).getSingleResult();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return goods;
	}

	public List<Long> getGoodsid() {							//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ id пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
		em.clear();
		@SuppressWarnings("unchecked")
		List<Long> listGoods = (List<Long>) em.createQuery("Select id From Goods").getResultList();
		return listGoods;
	}

	public Double getKolGoods(Long id) {						//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ goods пїЅпїЅ пїЅпїЅпїЅ id
		em.clear();
//		@SuspressWarning("unchecked")
		Double kol = (Double) em.createQuery("Select sum(g.rest) From Goods g Where g.id =:arg").setParameter("arg", id).getSingleResult();
		System.out.println(kol);
		return kol;
	}

//-------------------------Meth Property----------------------------------------

	public void addProperty(Property property) {
		em.clear();
		em.getTransaction().begin();
		em.persist(property);
		em.getTransaction().commit();
	}

	public void deleteProperty(long id) {
		em.clear();
		em.getTransaction().begin();
		em.remove(getProperty(id));
		em.getTransaction().commit();
	}

	public Property getProperty(long id) {
		em.clear();
		return em.find(Property.class, id);
	}

	public void updateProperty(Property property) {
		em.clear();
		em.getTransaction().begin();
		em.merge(property);
		em.getTransaction().commit();
	}

	public List<Property> getAllProperty() {					//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ property
		em.clear();
		@SuppressWarnings("unchecked")
		List<Property> listProperty = (List<Property>) em.createQuery("From Property").getResultList();
		return listProperty;
	}

	public List<Long> getPropertyId() {							//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅ id пїЅпїЅпїЅпїЅ-пїЅпїЅпїЅ
		em.clear();
		@SuppressWarnings("unchecked")
		List<Long> listProperty = (List<Long>) em.createQuery("Select id From Property").getResultList();
		return listProperty;
	}

	public List<Property> getPropertyOfGoods(Long index) {		//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ property пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ
		em.clear();
		System.out.println("\n!!!!!!!!!пїЅпїЅпїЅпїЅпїЅпїЅ getPropertyOfGoods(Long index)");
		System.out.println("пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅ: "+ index);
		List<Property> listProperty = new ArrayList<Property>();
		Query query = em.createNativeQuery("SELECT p.id_Property, p.types_id_TypeGoods, p.property  FROM Property as p, Goods as g "
				+ "WHERE p.types_id_TypeGoods=g.types_id_TypeGoods AND g.id_Goods = :arg").setParameter("arg", index);
		@SuppressWarnings({"unchecked"})
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			Property prop = new Property();
			prop.setId_Property(Long.parseLong(String.valueOf(row[0])));				///http://www.journaldev.com/3422/hibernate-native-sql-query-example
			prop.setTypes(getType(Long.parseLong(String.valueOf(row[1]))));
			prop.setProperty(row[2].toString());
			listProperty.add(prop);
			System.out.println(prop);
		}
		return listProperty;

	}

	public Property getMaxIdProp() {										//пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ id property - пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ
		System.out.println("\n!!!!!!!!!пїЅпїЅпїЅпїЅпїЅпїЅ getMaxIdProp");
		Property id = (Property) em.createQuery("From Property p1 Where p1.id = (Select max(p.id) From Property p)").getSingleResult();
		System.out.println("\n!!!!!!!!! getMaxIdProp = "+id.getId_Property());
		return id;
	}

//---------------------------Meth Value----------------------------

	public void addValue(Value value) {
		em.clear();
		em.getTransaction().begin();
		em.persist(value);
		em.getTransaction().commit();
	}

	public void deleteValue(long id) {
		em.clear();
		em.getTransaction().begin();
		em.remove(getValue(id));
		em.getTransaction().commit();
	}

	public Value getValue(long id) {
		em.clear();
		return em.find(Value.class, id);
	}

	public void updateValue(Value value) {
		em.clear();
		em.getTransaction().begin();
		em.merge(value);
		em.getTransaction().commit();
	}

	public List<Value> getAllValue() {
		em.clear();
		@SuppressWarnings("unchecked")
		List<Value> listValue = (List<Value>) em.createQuery("From Value").getResultList();
		return listValue;
	}

	public List<Long> getValueId() {
		em.clear();
		@SuppressWarnings("unchecked")
		List<Long> listValue = (List<Long>) em.createQuery("Select id From Value").getResultList();
		return listValue;
	}

//	public List<Value> getValueOfGoods(Long index) {					//пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ value пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ
//		em.clear();
//		@SuppressWarnings("unchecked")
//		List<Value> listValue = (List<Value>) em.createQuery("Select value From Value Where goods Like :arg").setParameter("arg", index).getResultList();
//		return listValue;
//	}

//--------------------------------Meth Bill----------------------------------------

	public void addBill (Bill bill){
		em.clear();
		em.getTransaction().begin();
		em.persist(bill);
		em.getTransaction().commit();
	}

	public void deleteBill (Long bill) {						//пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ bill + пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅ
		em.clear();
		Goods goods = new Goods();
		System.out.println("пїЅпїЅпїЅпїЅпїЅпїЅ deleteBill("+bill+")");
		@SuppressWarnings("unchecked")
		List<Bill> delID = (List<Bill>) em.createQuery("From Bill b where b.bill=:arg").setParameter("arg", bill).getResultList();
		for (Bill i: delID){
			em.getTransaction().begin();
			goods = getGoods(i.getGoodsId());
			goods.setRest((Double)goods.getRest() + (Double)i.getKolvo());
			em.remove(em.find(Bill.class, i.getId_Bill()));
			em.getTransaction().commit();
		}
	}

	public void deleteSingleBill(Long id) {						//пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ
		em.clear();
		em.getTransaction().begin();
		em.remove(getBill(id));
		em.getTransaction().commit();
	}

	public Bill getBill(Long id) {
		em.clear();
		return em.find(Bill.class, id);
	}

	public void updateBill(Bill bill) {
		em.clear();
		em.getTransaction().begin();
		em.merge(bill);
		em.getTransaction().commit();
	}

	public List<Long> getAllBillid() {
		em.clear();
		@SuppressWarnings("unchecked")
		List<Long> listBill = (List<Long>) em.createQuery("Select id From Bill").getResultList();
		return listBill;
	}

	public List<Bill> getAllBill() {							//пїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅпїЅ пїЅпїЅ bill
		em.clear();
		List<Bill> listBill = new ArrayList<Bill>();
		@SuppressWarnings("unchecked")
//		List<Bill> listBill = (List<Bill>) em.createQuery("Select b.bill, max(b.dateBill), sum(b.price*b.kolvo) From Bill as b group by b.bill").getResultList();
//		List<Object[]> rows = em.createNativeQuery("Select b.bill, max(b.dateBill), sum(b.price*b.kolvo), sum(b.disc*b.kolvo) From Bill as b group by b.bill").getResultList();
		List<Object[]> rows = em.createNativeQuery("Select b.bill, max(b.dateBill), sum(b.price), sum(b.disc) From Bill b group by b.bill").getResultList();
		for (Object[] row: rows ){
			Bill bill = new Bill();
			bill.setBill(Long.parseLong(String.valueOf(row[0])));
			bill.setDateBill((Date) row[1]);
			bill.setPrice(Double.parseDouble(String.valueOf(row[2])));
			if (String.valueOf(row[3]) == "null") {
				bill.setDisc(0d);

			}
			else
				bill.setDisc(Double.parseDouble(String.valueOf(row[3])));
			listBill.add(bill);
		}
		return listBill;
	}

	public List<Bill> getAllB(Long index) {						//пїЅпїЅпїЅпїЅпїЅпїЅ id пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅ пїЅпїЅпїЅпїЅ bill
		em.clear();
		@SuppressWarnings("unchecked")
		List<Bill> listBill = em.createQuery("From Bill t1 Where t1.bill Like :arg").setParameter("arg", index).getResultList();
		for (Bill pl: listBill ){
			System.out.println(pl);
		}
		return listBill;
	}

	public Long getNextBill() {									//пїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ. пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅ
		em.clear();
//		@SuppressWarnings("unchecked")
		Object temp = em.createQuery("Select max(bill) From Bill").getSingleResult();
		if (temp == null)
			return 1l;
		Long maxBill = Long.parseLong(temp.toString());
		System.out.println(maxBill);
		return maxBill+1;
	}

//-----------------------------------Meth Discount----------------------

	public void addDiscount(Discount discount) {
		em.clear();
		em.getTransaction().begin();
		em.persist(discount);
		em.getTransaction().commit();
	}

	public void deleteDiscount(long id) {
		em.clear();
		em.getTransaction().begin();
		em.remove(getDiscount(id));
		em.getTransaction().commit();
	}

	public Discount getDiscount(long id) {
		em.clear();
		return em.find(Discount.class, id);
	}

	public void updateDiscount(Discount discount) {
		em.clear();
		em.getTransaction().begin();
		em.merge(discount);
		em.getTransaction().commit();
	}

	public List<Discount> getDiscByBill(TypeGoods id) {
		em.clear();
		@SuppressWarnings("unchecked")
		List<Discount> listDiscount = em.createQuery("From Discount d Where d.types = :arg").setParameter("arg", id).getResultList();
		return listDiscount;
	}

	public Discount getDefaultDisc(Date dat) {													//пїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅ default
		em.clear();
		Discount d = (Discount) em.createQuery("Select t1.id, t1.dateIn, t1.dateOut, t1.pv, max(t1.value), t1.typeGoods From "
				+ "(Select d.id, d.dateIn, d.dateOut, d.pv, d.value, d.typeGoods  From Discount d Where d.pv = 'default' And d.dateIn < :dat And d.dateOut > :dat) t1").setParameter("dat", dat).getSingleResult();
		return d;
	}


//-----------------------------------Meth PropVal---------------------------

	public List<PropVal> getPropVal(Long index) {
		em.clear();
		List<PropVal> list = new ArrayList<PropVal>();
		@SuppressWarnings("unchecked")
		List<Object[]> listPV = em.createQuery("FROM Property AS p INNER JOIN p.valuesByProperty AS v WHERE v.goods=:arg").setParameter("arg", index).getResultList();
		for (Object[] i: listPV) {
			list.add(new PropVal((Property)i[0], (Value)i[1]));
			System.out.println(i[0]);
			System.out.println(i[1]);;
			System.out.println(listPV);
		}
		return list;

	}
}
