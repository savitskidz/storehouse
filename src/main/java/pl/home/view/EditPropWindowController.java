package pl.home.view;

import pl.home.MainApp;
import pl.home.model.Meth;
import pl.home.model.PropVal;
import pl.home.model.Property;
import pl.home.model.Value;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditPropWindowController {

	public MainApp mainApp;
	public Stage editPropStage;
	PropWindowsController pwc;
	MainWindowController mwc;
	Meth meth;


	public void setMeth(Meth meth) {
		this.meth = meth;
	}
	public void setPwc(PropWindowsController pwc) {
		this.pwc = pwc;
	}
	public PropWindowsController getPwc() {
		return pwc;
	}
	public void setEditPropStage(Stage editPropStage) {
		this.editPropStage = editPropStage;
	}
	public Stage getEditPropStage() {
		return editPropStage;
	}

	@FXML
	private TextField propField;
	@FXML
	private TextField valueField;

	@FXML
	private void onSaveClick() {
		if (isInputDataValid()) {
//			PropVal savePV = new PropVal();

			Property saveP = new Property();
			Value saveV = new Value();
			saveP.setProperty(propField.getText());
			saveP.setTypes(pwc.rowData.getTypes());
			if (pwc.isNewClicked == false) {
				saveP.setId_Property(pwc.getRowData(pwc.getRowNumber().intValue()).getProperty().getId_Property());
				meth.updateProperty(saveP);
			}
			else {
				meth.addProperty(saveP);
			}

			saveV.setValue(valueField.getText());
			saveV.setGoods(pwc.rowData.getId_Goods());
			if (!pwc.isNewClicked) {
				saveV.setId(pwc.getRowData(pwc.getRowNumber().intValue()).getValue().getId());
				saveV.setProperties(saveP);
				meth.updateValue(saveV);
			}
			else {
				saveV.setProperties(meth.getMaxIdProp());
				meth.addValue(saveV);
			}
			
			editPropStage.close();
		}
	}

	@FXML
	private void onCancelClick() {
		editPropStage.close();
	}

	@FXML
	private void initialize() {

	}

	public void setDataToTextField() {
		if (!pwc.isNewClicked) {
			PropVal pv = pwc.getRowData(pwc.getRowNumber().intValue());
			propField.setText(pv.getPropStr());
			valueField.setText(pv.getValueV());
//			pwc.isNewClicked = true;
		}
		else {
			propField.setText(null);
			valueField.setText(null);
		}

	}

	public boolean isInputDataValid() {							//проверка вводимых данных
		int i = 0;
		String alertText = "";
		if (propField.getText() == null) {
			alertText += "Empty string PROPERTY!\n";
			i++;
		}
		if (valueField.getText() == null) {
			alertText += "Empty string VALUE!\n";
			i++;
		}

		if (i == 0) {
			return true;
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(editPropStage);
			alert.setTitle("!!!ERROR!!!");
			alert.setHeaderText("Incorrect data input: ");
			alert.setContentText(alertText);

			alert.showAndWait();
			return false;
		}

	}

}
