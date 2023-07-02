package pl.home;

import java.io.IOException;
import java.util.Date;

import pl.home.model.Goods;
import pl.home.model.Meth;
import pl.home.view.MainWindowController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private ObservableList<Goods> goodsData = FXCollections.observableArrayList();

	public Stage primaryStage;
	public Pane rootLayout;
	Meth meth = new Meth();
	Date date = new Date();

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Storehouse");

		if (meth.isDataInTypeGoods() == true) {
			meth.addTypeStatic();
			meth.addDiscountStatic();
		}
		showMainWindow();

	}

	public void showMainWindow() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/MainWindow.fxml"));
			rootLayout = (Pane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			MainWindowController controller = loader.getController();
			controller.setMeth(meth);
			controller.setMainApp(this);
			controller.setPrimaryStage(primaryStage);
//			meth.addDiscountStatic();

		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}


	public ObservableList<Goods> getGoodsData() {
		ObservableList<Goods> listGoods = FXCollections.observableArrayList(meth.getAllGoods());
		goodsData.setAll(listGoods);
		return goodsData;
	}

}
