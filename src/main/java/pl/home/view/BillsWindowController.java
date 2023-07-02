package pl.home.view;

import java.io.IOException;
import java.util.Date;

import pl.home.MainApp;
import pl.home.model.Bill;
import pl.home.model.Meth;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BillsWindowController {

	public MainApp bWMainApp;
	public Stage bWStage;
	MainWindowController mwc;
	EditBillWindowController ebwc;
	Meth meth;
	public static boolean newClicked;
	public static boolean viewClicked;
	Integer rowNumber;
	Long nextBill;
	public Long numberBill;

	ObservableList<Bill> editBillsData = FXCollections.observableArrayList();
	ObservableList<Bill> editBillsByNomberData = FXCollections.observableArrayList();

	public void setMeth(Meth meth) {
		this.meth = meth;
	}

	public MainWindowController getMwc() {
		return mwc;
	}

	public void setMwc (MainWindowController mWController) {
		this.mwc = mWController;
		billsTable.setItems(mwc.getBillsData());
	}

	public void setBWStage (Stage bWStage) {
		this.bWStage = bWStage;

	}

	public void setMainApp(MainApp mainApp){
		this.bWMainApp = mainApp;
	}

	@FXML
	private TableView<Bill> billsTable;
	@FXML
	private TableColumn<Bill, Long> billColumn;
	@FXML
	private TableColumn<Bill, Date> dateColumn;
	@FXML
	private TableColumn<Bill, Double> priceColumn;
	@FXML
	private TableColumn<Bill, Double> discColumn;

	@FXML
	private void OnClickNewButton() {
		viewClicked = false;
		newClicked = true;
		nextBill = meth.getNextBill();
		showEditBillWindow();
		billsTable.setItems(null);
		billsTable.setItems(mwc.getBillsData());
	}
	@FXML
	private void OnClickEditButton() {
		viewClicked = false;
		newClicked = false;
		showEditBillWindow();
		billsTable.setItems(null);
		billsTable.setItems(mwc.getBillsData());
	}
	@FXML
	private void OnClickViewButton() {
		viewClicked = true;
		showEditBillWindow();
	}
	@FXML
	private void OnClickDeleteButton() {
		Long delBill = getRowData(getRowNumber()).getBill();
		meth.deleteBill(delBill);

		billsTable.setItems(null);
		billsTable.setItems(mwc.getBillsData());
	}

	@FXML
	private void OnClickCancelButton() {
		bWStage.close();
	}

	@FXML
	private void initialize() {
		billColumn.setCellValueFactory(new PropertyValueFactory<Bill, Long>("bill"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Bill, Date>("dateBill"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Bill, Double>("Price"));
		discColumn.setCellValueFactory(new PropertyValueFactory<Bill, Double>("disc"));
	}

	public void showEditBillWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/EditBillWindow.fxml"));
			Pane bpage = (Pane) loader.load();

			Stage editBillStage = new Stage();
			editBillStage.setTitle("Edit window");
			editBillStage.initModality(Modality.WINDOW_MODAL);
			editBillStage.initOwner(bWStage);
			Scene editBillScene = new Scene(bpage);
			editBillStage.setScene(editBillScene);

			EditBillWindowController controller = loader.getController();
			controller.setMeth(meth);
			controller.setBwc(this);
			controller.setDataToFields();
			controller.setEditStage(editBillStage);

			editBillStage.showAndWait();

		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getRowNumber() {
		rowNumber = billsTable.getSelectionModel().getSelectedIndex();
		if (rowNumber >= 0) {
			return(rowNumber);
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(bWStage);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Choose string in the table!!");
			alert.showAndWait();
			return null;
		}
	}

	public Bill getRowData(Integer rowIndex) {
		if (rowIndex != null)
			return billsTable.getItems().get(rowIndex);
		else
			return billsTable.getItems().get(rowNumber);
	}

	public ObservableList<Bill> getEditBillsData() {

		ObservableList<Bill> listEData = FXCollections.observableArrayList(meth.getAllB(getRowData(getRowNumber()).getBill()));
		editBillsData.setAll(listEData);
		return editBillsData;
	}

	public ObservableList<Bill> getBillsByNomberData() {
		ObservableList<Bill> listData = FXCollections.observableArrayList(meth.getAllB(numberBill));
		editBillsByNomberData.setAll(listData);
		return editBillsByNomberData;
	}

}
