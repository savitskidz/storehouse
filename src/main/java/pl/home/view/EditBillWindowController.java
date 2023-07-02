package pl.home.view;

import java.io.IOException;
import java.util.Date;

import pl.home.MainApp;
import pl.home.model.Bill;
import pl.home.model.Goods;
import pl.home.model.Meth;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditBillWindowController {

	public MainApp mainApp;
	public Stage editStage;
	BillsWindowController bwc;
	public Boolean isNewClicked;
	Meth meth;
	Bill bill = new Bill();
	Goods goods = new Goods();
	Integer rowNumber;
	public Long numberBill;
	public Long indexProp;

	public BillsWindowController getBwc() {
		return bwc;
	}

	public void setMeth(Meth meth) {
		this.meth = meth;
	}

	public void setBwc(BillsWindowController bwc) {
		this.bwc = bwc;
		if (bwc.viewClicked) {
			editButton.setVisible(false);
			addButton.setVisible(false);
			saveButton.setVisible(false);
			deleteButton.setVisible(false);
		}
		else {
			editButton.setVisible(true);
			addButton.setVisible(true);
			saveButton.setVisible(false);
			deleteButton.setVisible(true);
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void setEditStage(Stage stage) {
		this.editStage = stage;
	}


	@FXML
	private TextField billField;
	@FXML
	private TextField dateField;

	@FXML
	private Button editButton;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button calcDiscount;

	@FXML
	private TableView<Bill> editBillTable;
	@FXML
	private TableColumn<Bill, Long>idColumn;
	@FXML
	private TableColumn<Bill, Long> goodsColumn;
	@FXML
	private TableColumn<Bill, Date> dateColumn;
	@FXML
	private TableColumn<Bill, Long> discountColumn;
	@FXML
	private TableColumn<Bill, Double> kolvoColumn;
	@FXML
	private TableColumn<Bill, Double> priceColumn;
	@FXML
	private TableColumn<Bill, Double> discMoneyColumn;

	@FXML
	private void onClickAddButton() {
		isNewClicked = true;

		showAddBill();
		editBillTable.setItems(null);
		editBillTable.setItems(bwc.getBillsByNomberData());
	}
	@FXML
	private void onClickEditButton(){
		isNewClicked = false;
		showAddBill();
		editBillTable.setItems(null);
		editBillTable.setItems(bwc.getBillsByNomberData());
	}
	@FXML
	private void onClickDeleteButton() {
		meth.deleteSingleBill(getRowData(getRowNumber()).getId_Bill());
		Goods goods = new Goods();
		goods = meth.getGoods(getRowData(getRowNumber()).getGoodsId());
		goods.setRest(goods.getRest() + getRowData(getRowNumber()).getKolvo());
		meth.updateGoods(goods);
		editBillTable.setItems(null);
		editBillTable.setItems(bwc.getBillsByNomberData());
	}
	@FXML
	private void onClicklSaveButton() {
//		Bill saveBill

	}
	@FXML
	private void onClickCancelButton() {
		editStage.close();
	}

/*	@FXML
	private void onClickDiscButton() {						//расчет скидки по товарам
		Double disc, discAll=0d, newPrice;
		for (Bill i: bwc.getEditBillsData()) {
			System.out.println("!!!!!!! товар в чеке: "+i);
			System.out.println("i.getGoods() "+i.getGoodsId().intValue());
			switch (i.getGoodsId().intValue()) {
				case 1: {
					List<Property> prop  = meth.getPropertyOfGoods(i.getGoodsId());		//получаем список property по id товара
					String str1 = new String("производитель");
					for (Property j: prop) {											//для каждого property
						String str2 = j.getProperty().toString();						//находим id свойства "производитель"
						if (str2.equals(str1)) {
							indexProp = j.getId_Property();
							System.out.println("id свойства производитель = "+indexProp);
						}
					}
					Value val = meth.getValue(indexProp);								//находим значение свойства "производитель"
					System.out.println("производитель: "+val.getValue());
					switch (val.getValue()) {											//действие в соответствии со значением
						case "kingston": {
							disc = i.getPrice()*(10d/(2*100));
							newPrice = i.getPrice()-disc;
							discAll += disc;
							System.out.println("kingstone newPrice "+newPrice+"\tdiscount: "+disc);
							break;
						}
						case "seagate": {
							disc = i.getPrice()*(10d/(1*100));
							newPrice = i.getPrice()-disc;
							discAll += disc;
							System.out.println("seagate newPrice "+newPrice+"\tdiscount: "+disc);
							break;
						}
						case "samsung": {
							disc = i.getPrice()*(10d/(3*100));
							newPrice = i.getPrice()-disc;
							discAll += disc;
							System.out.println("kingstone newPrice "+newPrice+"\tdiscount: "+disc);
							break;
						}
						default: {
							disc = defaultDisc(i);
							newPrice = i.getPrice() - disc;
							discAll += disc;
						}
					}
					break;
				}
				case 2: {
					List<Property> prop = meth.getPropertyOfGoods(i.getGoodsId());
					String srt1 = new String("цвет");
					for (Property j: prop) {
						if (j.getProperty().equals(srt1))
							indexProp = j.getId_Property();
					}
					Value val = meth.getValue(indexProp);
					String str3 = new String("розовый");
					if (val.getValue().equals(str3)) {
						disc = i.getPrice()*0.4;
						newPrice = i.getPrice()-disc;
						discAll += disc;
						System.out.println("Устр. ввода розовое newPrice "+newPrice+"\tdiscount: "+disc);
					}
					else {
						disc = defaultDisc(i);
						newPrice = i.getPrice() - disc;
						discAll += disc;
					}
					break;
				}
				case 3:	{
					List<Property> prop = meth.getPropertyOfGoods(i.getGoodsId());
					String str1 = "диагональ";
					for (Property j: prop) {
						if (j.getProperty().equals(str1))
							indexProp = j.getId_Property();
					}
					Value val = meth.getValue(indexProp);
					System.out.println("Double.parseDouble(val.getValue()) = "+Double.parseDouble(val.getValue()));
					System.out.println("Double.parseDouble(val.getValue())/100 = "+Double.parseDouble(val.getValue())/100);
					disc = Double.parseDouble(val.getValue())/100d*i.getPrice();
					newPrice = i.getPrice() - disc;
					discAll += disc;
					System.out.println("Монитор "+val.getValue()+" дюйм newPrice "+newPrice+"\tdiscount: "+disc);
					break;
				}
				default: {
					disc = defaultDisc(i);
					newPrice = i.getPrice() - disc;
					discAll += disc;

				}
			}
		}
		System.out.println("Общая скидка составляет: "+discAll);
	}
*/

	@FXML
	private void onClickDiscButton() {
		double disc, discAll = 0;
		for (Bill i: bwc.getEditBillsData()) {

		}
	}

	@FXML
	private void initialize() {
		idColumn.setCellValueFactory(new PropertyValueFactory<Bill, Long>("id_Bill"));
		goodsColumn.setCellValueFactory(new PropertyValueFactory<Bill, Long>("GoodsId"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Bill, Date>("dateBill"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<Bill, Long>("discByBill"));
		kolvoColumn.setCellValueFactory(new PropertyValueFactory<Bill, Double>("kolvo"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Bill, Double>("price"));
		discMoneyColumn.setCellValueFactory(new PropertyValueFactory<Bill, Double>("disc"));
	}

	public Double defaultDisc(Bill i) {
		Double t = 5d;
		Double temp = i.getPrice() * t/100d;
		return temp;
	}


	public void setDataToFields() {
		if (!bwc.newClicked) {
			editBillTable.setItems(bwc.getEditBillsData());
			bill = bwc.getRowData(bwc.getRowNumber());
			billField.setText(bill.getBill().toString());
			dateField.setText(bill.getDateBill().toString());
		}
		else {
			editBillTable.setId(null);
//			billField.setText(bwc.nextBill.toString());
			billField.setText(meth.getNextBill().toString());
		}
		numberBill = Long.parseLong(billField.getText());
		bwc.numberBill = numberBill;
	}

	public void showAddBill() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/AddBill.fxml"));
			Pane bpage = (Pane) loader.load();

			Stage abStage = new Stage();
			abStage.setTitle("Add/Edit check");
			abStage.initModality(Modality.WINDOW_MODAL);
			abStage.initOwner(editStage);
			Scene abScene = new Scene(bpage);
			abStage.setScene(abScene);

			AddBillController controller = loader.getController();
			controller.setMeth(meth);
			controller.setEbwc(this);
			controller.setDataToField();
			controller.setAddStage(abStage);

			abStage.showAndWait();

		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getRowNumber() {
		rowNumber = editBillTable.getSelectionModel().getSelectedIndex();
		if (rowNumber >= 0) {
			return(rowNumber);
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(editStage);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Choose string in the table!!");
			alert.showAndWait();
			return null;
		}
	}

	public Bill getRowData(Integer rowIndex) {
		return editBillTable.getItems().get(rowIndex);
	}
}
