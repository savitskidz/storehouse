package pl.home.view;

import pl.home.MainApp;
import pl.home.model.Goods;
import pl.home.model.Meth;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditMainWindowController {

	public MainApp eMainApp;
	public Stage editStage;
	MainWindowController mwc;
	BillsWindowController bwc;
	Meth meth;
	public static String selectedType;

	public MainWindowController getMwc() {
		return mwc;
	}

	public void setMeth(Meth meth) {
		this.meth = meth;
	}

	public void setMwc(MainWindowController mwc) {
		this.mwc = mwc;
		comboType.getItems().addAll(getTypeDataCombo());
	}

	public void setEMainApp(MainApp eMainApp) {
		this.eMainApp = eMainApp;
	}

	public void setEditStage(Stage stage) {
		this.editStage = stage;
	}

	@FXML
	private TextField idField;
	@FXML
	private ComboBox<String> comboType;
	@FXML
	private TextField countryField;
	@FXML
	private TextField restField;
	@FXML
	private TextField priceField;

	@FXML
	private void OnClickSaveButton() {
		if (isInputDataValid()) {
			Goods saveGoods = new Goods();
//			saveGoods.setId_TypeGoods(meth.getIdSelectedType(getSelectTypeCombo()));
			saveGoods.setTypes(meth.getSelectedType(getSelectTypeCombo()));
			saveGoods.setCountry(countryField.getText());
			saveGoods.setRest(Double.parseDouble(restField.getText()));
			saveGoods.setPrice(Double.parseDouble(priceField.getText()));

			if (!mwc.newClicked && mwc.getRowNumber() != null) {
				saveGoods.setId_Goods(mwc.getRowData(mwc.getRowNumber()).getId_Goods());
				meth.updateGoods(saveGoods);
			}
			else {
				meth.addGoods(saveGoods);
			}
			editStage.close();
		}
	}

	@FXML
	private void OnClickCancelButton() {
		editStage.close();
	}

	@FXML
	private void initialize() {
		idField.setEditable(false);
	}

	public ObservableList<String> getTypeDataCombo() {
		ObservableList<String> list = FXCollections.observableArrayList(meth.getTypeString());
		for (String pl: list ){
			System.out.println(pl);
		}
		return list;
	}

	public String getSelectTypeCombo() {
		String par = new String();
		par = (String) comboType.getValue();
		selectedType = par;
		return par;
	}

	public void setDataToFields() {								//вставляем данные в поля
		if (mwc.isNewClicked() == false) {						//нажата кнопла edit
			Long rowNumber = mwc.getRowNumber();
			if (rowNumber != null) {
				Goods rowData = mwc.getRowData(rowNumber);
				idField.setText(rowData.getId_Goods().toString());
				comboType.setValue(meth.getStringSelectedType(rowData.getTypes()));
				countryField.setText(rowData.getCountry());
				priceField.setText(rowData.getPrice().toString());
				restField.setText(rowData.getRest().toString());
			}
		}
		else {													//нажата кнопка add
			comboType.setValue(null);
			countryField.setText(null);
			priceField.setText(null);
			restField.setText(null);
		}
	}

	public boolean isInputDataValid() {							//проверка вводимых данных
		int i = 0;
		String alertText = "";
		if (comboType.getValue() == null) {
			alertText += "Choose type of goods\n";
			i++;
		}
		if (countryField.getText() == null) {
			alertText += "Empty string COUNTRY!\n";
			i++;
		}
		if (restField.getText() == null) {
			alertText += "Empty string REST!\n";
			i++;
		}
		if (priceField.getText() == null) {
			alertText += "Empty string PRICE!\n";
			i++;
		}

		if (i == 0) {
			return true;
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(editStage);
			alert.setTitle("!!!ERROR!!!");
			alert.setHeaderText("Incorrect data input: ");
			alert.setContentText(alertText);

			alert.showAndWait();
			return false;
		}

	}

}

