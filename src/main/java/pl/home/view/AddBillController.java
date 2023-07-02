package pl.home.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.home.MainApp;
import pl.home.model.Bill;
import pl.home.model.Discount;
import pl.home.model.Goods;
import pl.home.model.Meth;
import pl.home.model.PropVal;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddBillController {

	public MainApp addMainApp;
	public Stage addStage;
	EditBillWindowController ebwc;
	Bill bill = new Bill();
	Date date = new Date();
	Meth meth;
	Boolean noDisc = false, defDisc = false;


	public void setMeth(Meth meth){
		this.meth = meth;
	}

	public MainApp getAddMainApp() {
		return addMainApp;
	}
	public void setAddMainApp(MainApp addMainApp) {
		this.addMainApp = addMainApp;
	}
	public Stage getAddStage() {
		return addStage;
	}
	public void setAddStage(Stage addStage) {
		this.addStage = addStage;
	}
	public EditBillWindowController getEbwc() {
		return ebwc;
	}
	public void setEbwc(EditBillWindowController ebwc) {
		this.ebwc = ebwc;
	}

	@FXML
	private TextField idField;
	@FXML
	private TextField goodsField;
	@FXML
	private TextField dateField;
	@FXML
	private TextField discountField;
	@FXML
	private TextField kolvoField;
	@FXML
	private TextField priceField;


	@FXML
	private void onSaveClick()	 throws ParseException {						//сохраняем bill
		Boolean err = false;

		if (isInputDataValid()) {												//все ввели?
			Bill saveBill = new Bill();
			Goods saveGoods = new Goods();
			saveBill.setGoodsByBill(meth.getGoodsById(Long.parseLong(goodsField.getText())));

			if (Double.parseDouble(kolvoField.getText()) > meth.getKolGoods(Long.parseLong(goodsField.getText()))) {		//вводимое значение больше остатка на складе?
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(addStage);
				alert.setTitle("!!!ERROR!!!");
				alert.setHeaderText("Incorrect data:");
				alert.setContentText("Quantity is more than the balance!!!");
				alert.showAndWait();
			}
			else {
				saveBill.setKolvo(Double.parseDouble(kolvoField.getText()));																		//нет, запивываем значение в чек
				saveGoods = meth.getGoodsById(Long.parseLong(goodsField.getText()));
				saveGoods.setRest(meth.getKolGoods(Long.parseLong(goodsField.getText())) - Double.parseDouble(kolvoField.getText()));				//расчет остатка на складе
			}

			saveBill.setPrice(saveBill.getGoodsByBill().getPrice()*saveBill.getKolvo());
			saveBill.setBill(ebwc.numberBill);

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH); 		//преобразование из string в date
			saveBill.setDateBill(format.parse(dateField.getText()));

			List<Discount> listDiscount = meth.getDiscByBill(saveBill.getGoodsByBill().getTypes());	//получаем список скидок для одного типа товара
			if (!listDiscount.isEmpty()){
				for (Discount d: listDiscount) {													//для каждого элемента списка
					if ((d.getDateIn().getTime() < saveBill.getDateBill().getTime())&&(saveBill.getDateBill().getTime() < d.getDateOut().getTime())) {
						if (d.getPv() == null) {													//пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ pv пїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅ bill
							Double dt = saveBill.getPrice()*d.getValue()*saveBill.getKolvo()/100d;
							saveBill.setDisc(dt);
							saveBill.setDiscByBill(d);
						}
						else {
							List<PropVal> listJ = meth.getPropVal(saveBill.getGoodsId());
							for (PropVal j: listJ) {
								if (j.getPropStr().equals(d.getPv())) {
									saveBill.setDisc(giveMeDiscountValue(j, saveGoods, d.getValue(), saveBill.getDateBill())*saveBill.getKolvo());
									if (noDisc) {														//товар попал на скидку?
										saveBill.setDiscByBill(meth.getDiscount(1l));
										noDisc = false;
									}
									else
										saveBill.setDiscByBill(d);
									if (defDisc) {														////скидка по default?
										saveBill.setDiscByBill(meth.getDefaultDisc(saveBill.getDateBill()));
										defDisc = false;
									}
								}
								else {
									err = true;
//									break;
								}
							}
						}
						break;
					}
					else {
						System.out.println("!!!\n!!!Date is incorrect!!!");
						saveBill.setDisc(0d);
						saveBill.setDiscByBill(meth.getDiscount(1l));
					}
				}
			}
			else {
				saveBill.setDisc(0d);
				saveBill.setDiscByBill(meth.getDiscount(1l));
			}

			if (ebwc.isNewClicked)																	//проверка новый чек или редактируемый
				meth.addBill(saveBill);
			else {
				saveBill.setId_Bill(Long.parseLong(idField.getText()));
				meth.updateBill(saveBill);
			}
			addStage.close();
		}
	}

	@FXML
	private void onCancelClick(){
		addStage.close();
	}

	@FXML
	private void initialize() {
		idField.setEditable(false);
		dateField.setEditable(false);
		discountField.setEditable(false);
		priceField.setEditable(false);

	}


//------------------Расчет значения скидки для каждого типа товара-------------------------------------------------------------
	public Double giveMeDiscountValue(PropVal pv, Goods saveGoods, Double ds, Date dat){
		if (saveGoods.getTypesStr().equals("storage")) {
			System.out.println("!!!!!!!!!!!!!!! pv.getValueV().toLowerCase() "+pv.getValueV().toLowerCase());
			switch (pv.getValueV().toLowerCase()) {
				case "seagate": {
					Double disc = (saveGoods.getPrice()*ds)/(100*1d);
					System.out.println("SEAGATE: "+disc);
					return disc;
				}
				case "kingstone": {
					Double disc = (saveGoods.getPrice()*ds)/(100*2d);
					System.out.println("KINGSTONE: "+disc);
					return disc;
				}
				case "samsung": {
					Double disc = (saveGoods.getPrice()*ds)/(100*3d);
					System.out.println("SAMSUNG: "+disc);
					return disc;
				}
				default:
					System.out.println("Brands don't match");
					defDisc = true;
					Discount disc = meth.getDefaultDisc(dat);
					return disc.getValue()*saveGoods.getPrice()/100d;
				}
		}

		if (saveGoods.getTypesStr().equals("monitor")) {
			Double disc = saveGoods.getPrice()*Double.parseDouble(pv.getValueV())/100d;
			return disc;
		}

		if (saveGoods.getTypesStr().equals("input device")) {
			switch (pv.getValueV().toLowerCase()) {
			case "pink": {
				Double disc = (saveGoods.getPrice()*ds)/(100d);
				return disc;
			}
			default:
				defDisc = true;
				Discount disc = meth.getDefaultDisc(dat);
				return disc.getValue()*saveGoods.getPrice()/100d;
			}
		}
		else {
			noDisc = true;
			return 0d;
		}
	}



	public void setDataToField() {												//загрузка данных в textfield
		if (!ebwc.isNewClicked) {
			Bill bill = ebwc.getRowData(ebwc.getRowNumber());
			idField.setText(bill.getId_Bill().toString());
			goodsField.setText(bill.getGoodsByBill().getId_Goods().toString());
			dateField.setText(bill.getDateBill().toString());
			if (bill.getDiscByBill() == null)
				discountField.setText(null);
			else
				discountField.setText(bill.getDiscByBill().toString());
			kolvoField.setText(bill.getKolvo().toString());
			priceField.setText(bill.getPrice().toString());
		}
		else {
			goodsField.setText(null);
//			dateField.setText(null);
			DateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			dateField.setText(ft.format(date).toString());
			discountField.setText(null);
			kolvoField.setText(null);
			priceField.setText(null);
		}
	}

	public boolean isInputDataValid() {							//проверка вводимых данных
		int i = 0;
		String alertText = "";
		if (goodsField.getText() == null) {
			alertText += "The string GOODS is empty!\n";
			i++;
		}
		if (dateField.getText() == null) {
			alertText += "he string DATE is empty!\n";
			i++;
		}
		if (kolvoField.getText() == null) {
			alertText += "The string KOL_VO is empty!\n";
			i++;
		}

		if (i == 0) {
			return true;
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(addStage);
			alert.setTitle("!!!ERROR!!!");
			alert.setHeaderText("Incorrect data: ");
			alert.setContentText(alertText);

			alert.showAndWait();
			return false;
		}

	}



}
