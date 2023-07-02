package pl.home.view;

import java.io.IOException;

import pl.home.MainApp;
import pl.home.model.Bill;
import pl.home.model.Goods;
import pl.home.model.Meth;
import pl.home.model.PropVal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PropWindowsController {

	public MainWindowController mwc;
	public MainApp mainApp;
	public Stage propStage;
	Meth meth;
	public Boolean isNewClicked = false;
	public Goods rowData = new Goods();

	public void setMeth(Meth meth) {
		this.meth = meth;
	}

	public MainWindowController getMwc() {
		return mwc;
	}

	public void setMwc(MainWindowController mwc) {
		this.mwc = mwc;
		meth.getPropVal(mwc.getRowData(mwc.getRowNumber()).getId_Goods());
//		propTable.setItems(mwc.getPropData());
		propTable.setItems(mwc.getPropValData());
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	public void setPropStage(Stage stage) {
		this.propStage = stage;
	}



	@FXML
	private TableView<PropVal> propTable;
	@FXML
	private TableColumn<PropVal, String> propColumn;
	@FXML
	private TableColumn<PropVal, String> valueColumn;
	@FXML
	private TextField typeField;
	@FXML
	private TextField countryField;
	@FXML
	private TextField priceField;

	@FXML
	public void onAddClick() {
		isNewClicked = true;
		showEditPropWindow();
		propTable.setItems(null);
		propTable.setItems(mwc.getPropValData());
	}
	@FXML
	public void onEditClick() {
		isNewClicked = false;
		showEditPropWindow();
		propTable.setItems(null);
		propTable.setItems(mwc.getPropValData());
	}
	@FXML
	public void onDeleteClick() {
		PropVal t1 = getRowData(getRowNumber().intValue());
		meth.deleteValue(t1.getValue().getId());
		meth.deleteProperty(t1.getProperty().getId_Property());

		propTable.setItems(null);
		propTable.setItems(mwc.getPropValData());
	}
	@FXML
	public void onCancelClick() {
		propStage.close();
	}


	@FXML
	private void initialize(){
		propColumn.setCellValueFactory(new PropertyValueFactory<PropVal, String>("propStr"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<PropVal, String>("valueV"));
	}

	public void setDataToFields() {
		Long rowNumber = mwc.getRowNumber();
		if (rowNumber != null) {
			rowData = mwc.getRowData(rowNumber);
			typeField.setText(meth.getStringSelectedType(rowData.getTypes()));
			countryField.setText(rowData.getCountry());
			priceField.setText(rowData.getPrice().toString());
		}
	}

	public void showEditPropWindow() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/EditPropertyWindow.fxml"));
			Pane pane = (Pane) loader.load();

			Stage editPropStage = new Stage();
			editPropStage.setTitle("Add/Edit property");
			editPropStage.initModality(Modality.WINDOW_MODAL);
			editPropStage.initOwner(propStage);
			Scene scene = new Scene(pane);
			editPropStage.setScene(scene);

			EditPropWindowController controller = loader.getController();
			controller.setMeth(meth);
			controller.setPwc(this);
			controller.setDataToTextField();
			controller.setEditPropStage(editPropStage);

			editPropStage.showAndWait();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Long getRowNumber() {												//проверка выбранной строки
		long temp = propTable.getSelectionModel().getSelectedIndex();
		if (temp >= 0) {
			return temp;
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(propStage);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Choose string in the table!");
			alert.showAndWait();
			return null;
		}
	}
	public PropVal getRowData(Integer rowIndex) {
		return propTable.getItems().get(rowIndex);
	}
}
