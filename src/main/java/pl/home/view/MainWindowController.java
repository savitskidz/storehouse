package pl.home.view;

import static java.lang.Math.toIntExact;
import java.io.IOException;

import pl.home.MainApp;
import pl.home.model.Bill;
import pl.home.model.Goods;
import pl.home.model.Meth;
import pl.home.model.PropVal;
import pl.home.model.Property;
import pl.home.model.TypeGoods;
import pl.home.model.Value;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController {

	private ObservableList<Bill> billsData = FXCollections.observableArrayList();
	private ObservableList<Property> propData = FXCollections.observableArrayList();
	private ObservableList<Value> valueData = FXCollections.observableArrayList();
	private ObservableList<PropVal> propvalData = FXCollections.observableArrayList();

	private MainApp mWMainApp;
	private Stage primaryStage;
	public static boolean newClicked;
	public static Goods selectedTypeClick;
	Meth meth;
	Goods goods;
	PropVal propVal = new PropVal();

	public void setMainApp(MainApp goodsMainApp) {
		this.mWMainApp = goodsMainApp;
		goodsTable.setItems(goodsMainApp.getGoodsData());					//������������� tableview �������
	}

	public void setMeth(Meth meth) {
		this.meth = meth;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public static boolean isNewClicked() {
		return newClicked;
	}

	@FXML
	private TableView<Goods> goodsTable;
	@FXML
	private TableColumn<Goods, Long> idColumn;
	@FXML
	private TableColumn<TypeGoods, String> idTypeColumn;
	@FXML
	private TableColumn<Goods, String> countryColumn;
	@FXML
	private TableColumn<Goods, Double> restColumn;
	@FXML
	private TableColumn<Goods, Double> priceColumn;


	@FXML
	private void OnClickAddButton() {									//������ ADD
		newClicked = true;
		showEditMainWindow();
		goodsTable.setItems(null);										//����������������� tableview �������
		goodsTable.setItems(mWMainApp.getGoodsData());
	}

	@FXML
	private void OnClickEditButton() {									//������ EDIT
		newClicked = false;

		if (getRowNumber() != null) {									//�������� ������� �� ������ � �������
			showEditMainWindow();
			goodsTable.setItems(null);
			goodsTable.setItems(mWMainApp.getGoodsData());
		}
	}

	@FXML
	private void OnClickCancelButton() {
		primaryStage.close();
	}

	@FXML
	private void OnClickBillsButton() {									//������� � �����
//		primaryStage.close();
		showBillsWindow();
		goodsTable.setItems(null);
		goodsTable.setItems(mWMainApp.getGoodsData());
	}

	@FXML
	private void OnClickDeleteButton() {								//������ DELETE
		if (getRowNumber() != null) {									//�������� �������� ������
			meth.deleteGoods(getRowData(getRowNumber()).getId_Goods());	///�������� �� id_goods
			goodsTable.setItems(null);
			goodsTable.setItems(mWMainApp.getGoodsData());
		}
	}

	@FXML
	private void OnClickViewPropButton() {								//������� � ��������������� ������
		if (getRowNumber() != null) {									//�������� ��������� ������
			showViewPropWindow();
		}
	}

	@FXML
	private void onMouseGoodsPressed (MouseEvent event) {				//�������� �������������� ������ �� ������� ������ ����
		selectedTypeClick = null;
		if (event.isPrimaryButtonDown() && event.getClickCount() == 2 && getRowNumber() != null) {
			selectedTypeClick = goodsTable.getSelectionModel().getSelectedItem();
			showEditMainWindow();
			goodsTable.setItems(null);
			goodsTable.setItems(mWMainApp.getGoodsData());
		}
	}

	@FXML
	private void initialize() {											//�������������� ������� � TableView
		idColumn.setCellValueFactory(new PropertyValueFactory<Goods, Long>("id_Goods"));
//		idTypeColumn.setCellValueFactory(new PropertyValueFactory<Goods, Long>("id_TypeGoods"));
		idTypeColumn.setCellValueFactory(new PropertyValueFactory<TypeGoods, String>("TypesStr"));
		countryColumn.setCellValueFactory(new PropertyValueFactory<Goods, String>("country"));
		restColumn.setCellValueFactory(new PropertyValueFactory<Goods, Double>("rest"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Goods, Double>("price"));
	}

	public void showBillsWindow() {										//�������� ���� �����
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/BillsWindow.fxml"));
			Pane page = (Pane) loader.load();

			Stage billsStage = new Stage();
			billsStage.setTitle("Bills Archive");
			billsStage.initModality(Modality.WINDOW_MODAL);
			billsStage.initOwner(mWMainApp.primaryStage);
			Scene scene = new Scene(page);
			billsStage.setScene(scene);

			BillsWindowController controller = loader.getController();
			controller.setMeth(meth);
			controller.setMwc(this);
			controller.setBWStage(billsStage);

			billsStage.showAndWait();

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public void showEditMainWindow() {										//�������� ���� �������������� ������
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/EditMainWindow.fxml"));
			Pane empage = (Pane) loader.load();

			Stage editMainStage = new Stage();
			editMainStage.setTitle("View|Edit");
			editMainStage.initModality(Modality.WINDOW_MODAL);
			editMainStage.initOwner(mWMainApp.primaryStage);
			Scene editMainScene = new Scene(empage);
			editMainStage.setScene(editMainScene);

			EditMainWindowController econtroller = loader.getController();
			econtroller.setMeth(meth);
			econtroller.setMwc(this);
			econtroller.setDataToFields();
			econtroller.setEditStage(editMainStage);

			editMainStage.showAndWait();

		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void showViewPropWindow() {										//��������� ���� �� ���������� � ����������
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/PropertyWindow.fxml"));
			Pane page = (Pane) loader.load();

			Stage billsStage = new Stage();
			billsStage.setTitle("Goods properties");
			billsStage.initModality(Modality.WINDOW_MODAL);
			billsStage.initOwner(mWMainApp.primaryStage);
			Scene scene = new Scene(page);
			billsStage.setScene(scene);

			PropWindowsController controller = loader.getController();
			controller.setMeth(meth);
			controller.setMwc(this);
			controller.setDataToFields();
			controller.setPropStage(billsStage);

			billsStage.showAndWait();

		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public ObservableList<Bill> getBillsData() {
		ObservableList<Bill> listBill = FXCollections.observableArrayList(meth.getAllBill());
		billsData.setAll(listBill);
		return billsData;
	}

//	public ObservableList<Property> getPropData() {
//		ObservableList<Property> listProp = FXCollections.observableArrayList(meth.getPropertyOfGoods(getRowData(getRowNumber()).getId_Goods()));
//		propData.setAll(listProp);
//		return propData;
//	}
//
//	public ObservableList<Value> getValueData() {
//		ObservableList<Value> listValue = FXCollections.observableArrayList(goods.getValueByGoods());
//		valueData.setAll(listValue);
//		return valueData;
//	}

	public ObservableList<PropVal> getPropValData() {
		System.out.println(getRowData(getRowNumber()).getId_Goods());
		ObservableList<PropVal> listPV = FXCollections.observableArrayList(meth.getPropVal(getRowData(getRowNumber()).getId_Goods()));
		propvalData.setAll(listPV);
		return propvalData;
	}

	public Long getRowNumber() {												//�������� ��������� ������

		long temp = goodsTable.getSelectionModel().getSelectedIndex();
		if (temp >= 0) {
			return temp;
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(primaryStage);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Choose string in the table!");
			alert.showAndWait();
			return null;
		}
	}

	public Goods getRowData(Long rowIndex) {									//��������� goods �� ��������� ������
		int it = toIntExact(rowIndex);
		return goodsTable.getItems().get(it);
	}


}