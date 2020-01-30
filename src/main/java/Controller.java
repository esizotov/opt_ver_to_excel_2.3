import dbController.DataBaseAudi;
import dbController.DataBaseJLR;
import dbController.DataBaseOutput;
import dbController.DataBaseVolvo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class Controller {

    @FXML
    Button search;

    @FXML
    Button fileChooser_1;

    @FXML
    Button fileChooser_2;

    @FXML
    Button buttonLoadAllPrice;

    @FXML
    Button buttonLoadStock;

    @FXML
    TextField textFieldID;

    @FXML
    TextField textFieldChooserAllPriceID;

    @FXML
    TextField textFieldChooserStockID;

    @FXML
    Button buttonPriceStockID;

    @FXML
    TextField textFieldSalePriceStockID;

    @FXML
    TextField textFieldSalePriceZSOID;

    @FXML
    Button buttonPriceZSOID;

    @FXML
    TextField textFieldSaleStock12GroupID;

    @FXML
    TextField textFieldSaleStock8GroupID;

    @FXML
    TextField textFieldSaleStock4GroupID;

    @FXML
    TextField textFieldSaleStock2GroupID;

    @FXML
    Button buttonPriceStockGroupID;

    @FXML
    TextField textFieldSaleZSO12GroupID;

    @FXML
    TextField textFieldSaleZSO8GroupID;

    @FXML
    TextField textFieldSaleZSO4GroupID;

    @FXML
    TextField textFieldSaleZSO2GroupID;

    @FXML
    Button buttonPriceZSOGroupID;

    @FXML
    private TableView<DataBaseOutput> tableViewID;

    @FXML
    private TableColumn<DataBaseOutput, String> columnPartNumberID;

    @FXML
    private TableColumn<DataBaseOutput, String> columnNamePartID;

    @FXML
    private TableColumn<DataBaseOutput, Double> columnPriceID;

    @FXML
    private TableColumn<DataBaseOutput, Integer> columnRemainsID;

    Window primaryStage;

    @FXML
    Button fileChooserVolvoAll;

    @FXML
    TextField textFieldChooserAllPriceVolvoID;

    @FXML
    Button buttonLoadAllPriceVolvoID;

    @FXML
    Button fileChooserVolvoStock;

    @FXML
    TextField textFieldChooserVolvoStockID;

    @FXML
    Button buttonLoadPartsCSVolvoID;

    @FXML
    TextField textFieldChooserVolvoSaleGroupID;

    @FXML
    Button fileChooserVolvoSaleGroupID;

    @FXML
    Button buttonLoadSaleGroupID;

    @FXML
    TextField textFieldDiscountStockVolvoID;

    @FXML
    Button buttonPriceStockVolvoID;

    @FXML
    TextField textFieldDiscountZSOVolvoID;

    @FXML
    Button buttonPriceZSOVolvoID;

    @FXML
    TextField textFieldChooserAllPriceJaguarID;

    @FXML
    Button fileChooserJaguarAllID;

    @FXML
    Button buttonLoadAllPriceJaguarID;

    @FXML
    TextField textFieldChooserLRStockID;

    @FXML
    Button fileChooserLRStockID;

    @FXML
    Button buttonLoadPartsCSLRID;

    @FXML
    TextField textFieldChooserJaguarStockID;

    @FXML
    Button fileChooserJaguarStockID;

    @FXML
    Button buttonLoadPartsCSJaguarID;

    @FXML
    TextField textFieldChooserAllPriceLRID;

    @FXML
    Button fileChooserLRAllID;

    @FXML
    Button buttonLoadAllPriceLRID;

    @FXML
    TextField textFieldDiscountStockJLRID;

    @FXML
    Button buttonPriceStockJLRID;

    @FXML
    TextField textFieldDiscountZSOJLRID;

    @FXML
    Button buttonPriceZSOJLRID;

    // заполнение tableView +++++
    @FXML
    private void initialize() {
        columnPartNumberID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, String>("partNumber"));
        columnNamePartID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, String>("namePart"));
        columnPriceID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, Double>("price"));
        columnRemainsID.setCellValueFactory(new PropertyValueFactory<DataBaseOutput, Integer>("remains"));
        tableViewID.setItems(DataBaseAudi.initTableView());
    }

    // выбор файлов загрузки общего прайса и остатков ЦС +++++
    @FXML
    public void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open resource file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Excel files", "*.xlsx"),
                new FileChooser.ExtensionFilter("Text files", "*.txt")
        );

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(fileChooser_1.isFocused()) {
            if (selectedFile != null)
                textFieldChooserAllPriceID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooser_2.isFocused()) {
            if (selectedFile != null)
                textFieldChooserStockID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserVolvoAll.isFocused()) {
            if (selectedFile != null)
                textFieldChooserAllPriceVolvoID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserVolvoStock.isFocused()) {
            if (selectedFile != null)
                textFieldChooserVolvoStockID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserVolvoSaleGroupID.isFocused()) {
            if (selectedFile != null)
                textFieldChooserVolvoSaleGroupID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserJaguarAllID.isFocused()) {
            if (selectedFile != null)
                textFieldChooserAllPriceJaguarID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserLRStockID.isFocused()) {
            if (selectedFile != null)
                textFieldChooserLRStockID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserJaguarStockID.isFocused()) {
            if (selectedFile != null)
                textFieldChooserJaguarStockID.appendText(selectedFile.getAbsolutePath());
        }
        if (fileChooserLRAllID.isFocused()) {
            if (selectedFile != null)
                textFieldChooserAllPriceLRID.appendText(selectedFile.getAbsolutePath());
        }
    }

    // загрузка общего прайс-листа Audi+++++
    @FXML
    public void buttonLoadAllParts() {
        DataBaseAudi.loadAllPrice(textFieldChooserAllPriceID.getText());
    }

    // загрузка остатков ЦС Audi+++++
    @FXML
    public void buttonLoadPartsCS() {
        DataBaseAudi.loadPartsCS(textFieldChooserStockID.getText());
    }

    // выгрузка прайса Сток ЦС (0-й) Audi +наценка/-скидка +++++
    @FXML
    public void priceStockCS() {
        DataBaseAudi.formPriceStockCS(textFieldSalePriceStockID.getText());
    }

    // выгрузка прайса Срочный ЦС (0-й) Audi +наценка/-скидка +++++
    @FXML
    public void priceZSOCS() {
        DataBaseAudi.formPriceZSOCS(textFieldSalePriceZSOID.getText());
    }

    // выгрузка прайса Сток ЦС по группам Audi +наценка/-скидка +++++
    @FXML
    public void priceStockGroup() {
        DataBaseAudi.formPriceStockGroups(textFieldSaleStock12GroupID.getText(), textFieldSaleStock8GroupID.getText(),
                textFieldSaleStock4GroupID.getText(), textFieldSaleStock2GroupID.getText());
    }

    // выгрузка прайса Срочный ЦС по группам Audi +наценка/-скидка +++++
    @FXML
    public void priceZSOGroup() {
        DataBaseAudi.formPriceZSOGroups(textFieldSaleZSO12GroupID.getText(), textFieldSaleZSO8GroupID.getText(),
                textFieldSaleZSO4GroupID.getText(), textFieldSaleZSO2GroupID.getText());
    }

    public void buttonSearch() {
    }

    // загрузка общего прайс-листа Volvo+++++
    @FXML
    public void buttonLoadAllPriceVolvo() {
        DataBaseVolvo.loadAllPriceVolvo(textFieldChooserAllPriceVolvoID.getText());
    }

    // загрузка остатков ЦС Volvo+++++
    @FXML
    public void buttonLoadPartsCSVolvo() {
        DataBaseVolvo.loadPartsCSVolvo(textFieldChooserVolvoStockID.getText());
    }

    // загрузка таблицы скидок Volvo+++++
    @FXML
    public void buttonLoadSaleGroup() {
        DataBaseVolvo.loadSalesDiscountMatrix(textFieldChooserVolvoSaleGroupID.getText());
    }

    // выгрузка прайса Сток ЦС Volvo +наценка/-скидка +++++
    @FXML
    public void priceStockVolvoCS() {
        DataBaseVolvo.formPriceStockCSVolvo(textFieldDiscountStockVolvoID.getText());
    }

    // выгрузка прайса Срочный ЦС Volvo +наценка/-скидка +++++
    @FXML
    public void priceZSOVolvoCS() {
        DataBaseVolvo.formPriceZSOCSVolvo(textFieldDiscountZSOVolvoID.getText());
    }

    // загрузка общего прайс-листа Jaguar+++++
    @FXML
    public void buttonLoadAllPriceJaguar() {
        DataBaseJLR.loadPartsAllJaguar(textFieldChooserAllPriceJaguarID.getText());
    }

    // загрузка общего прайс-листа LR+++++
    @FXML
    public void buttonLoadAllPriceLR() {
        DataBaseJLR.loadPartsAllLR(textFieldChooserAllPriceLRID.getText());
    }

    // загрузка остатков ЦС LR+++++
    @FXML
    public void buttonLoadPartsCSLR() {
        DataBaseJLR.loadPartsCSLR(textFieldChooserLRStockID.getText());
    }

    // загрузка остатков ЦС Jaguar+++++
    @FXML
    public void buttonLoadPartsCSJaguar() {
        DataBaseJLR.loadPartsCSJaguar(textFieldChooserJaguarStockID.getText());
    }

    // выгрузка прайса Сток ЦС JLR+++++
    @FXML
    public void priceStockJLRCS() {
        DataBaseJLR.formPriceStockCSJLR(textFieldDiscountStockJLRID.getText());
    }

    // выгрузка прайса Срочный ЦС JLR+++++
    @FXML
    public void priceZSOJLRCS() {
        DataBaseJLR.formPriceZSOCSJLR(textFieldDiscountZSOJLRID.getText());
    }
}
