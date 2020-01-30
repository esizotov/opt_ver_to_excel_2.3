package dbController;

import exportToExcel.ExportToExcel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loadExcel.LoadExcelHSSF;
import loadExcel.LoadExcelXSSF;
import loadExcelVolvoRow.LoadExcelVolvoRow;
import loadExcelVolvoRow.LoadExcelVolvoRowRemains;
import loadExcelVolvoRow.LoadExcelVolvoRowSaleGroup;
import messageWindows.ControllerMessage;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static dbController.DataBaseAudi.dateAndTime;

public class DataBaseVolvo {

    private static Connection connection;
    private static Statement statement;
    private static String message;
    private static String nameFile;


    //+++++
    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/opt_volvo?useUnicode=true&serverTimezone=UTC&useSSL=false",
                "root", "root");
        statement = connection.createStatement();
    }

//+++++
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

//загрузка полного прайс-листа Volvo +++++
    public static void loadAllPriceVolvo(String file) {
        try {
            connect();
            ObservableList<LoadExcelVolvoRow> loadExcelVolvoRows = FXCollections.observableArrayList();
            List dataAll = LoadExcelXSSF.openBookXSSF(file);
            for (int i = 4; i < dataAll.size(); i++) {
                List data = (List) dataAll.get(i);
                if (data.size() == 6) {
                    loadExcelVolvoRows.add(new LoadExcelVolvoRow(data));
                }
            }
            statement.execute("TRUNCATE TABLE price_cs_parts;");
            for (int i = 0; i < loadExcelVolvoRows.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO price_cs_parts VALUES (?, ?, ?, ?, ?, ?);");
                preparedStatement.setString(1, loadExcelVolvoRows.get(i).getProductGroup());
                preparedStatement.setString(2, loadExcelVolvoRows.get(i).getFunctionalGroup());
                preparedStatement.setString(3, loadExcelVolvoRows.get(i).getPartNumber());
                preparedStatement.setString(4, loadExcelVolvoRows.get(i).getNamePart());
                preparedStatement.setString(5, loadExcelVolvoRows.get(i).getSaleGroup());
                preparedStatement.setString(6, loadExcelVolvoRows.get(i).getRetailPrice());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM price_cs_parts");
            resultSet.next();
            message = "Загружено " + resultSet.getInt(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (IOException e){
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }
// загрузка остатков ЦС Volvo+++++
    public static void loadPartsCSVolvo(String file) {
        try {
            connect();
            ObservableList<LoadExcelVolvoRowRemains> loadExcelVolvoRowsRemains = FXCollections.observableArrayList();
            List dataAll = LoadExcelHSSF.openBook(file);
            for (int i = 1; i < dataAll.size(); i++) {
                List data = (List) dataAll.get(i);
                if (data.size() == 9) {
                    loadExcelVolvoRowsRemains.add(new LoadExcelVolvoRowRemains(data));
                }
            }
            statement.execute("TRUNCATE TABLE stock_cs;");
            for (int i = 0; i < loadExcelVolvoRowsRemains.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO stock_cs VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
                preparedStatement.setString(1, loadExcelVolvoRowsRemains.get(i).getPartNumber());
                preparedStatement.setString(2, loadExcelVolvoRowsRemains.get(i).getNamePart());
                preparedStatement.setString(3, loadExcelVolvoRowsRemains.get(i).getProductGroup());
                preparedStatement.setString(4, loadExcelVolvoRowsRemains.get(i).getFunctionalGroup());
                preparedStatement.setString(5, loadExcelVolvoRowsRemains.get(i).getSaleGroup());
                preparedStatement.setString(6, loadExcelVolvoRowsRemains.get(i).getRetailPrice());
                preparedStatement.setString(7, loadExcelVolvoRowsRemains.get(i).getStockRemains());
                preparedStatement.setString(8, loadExcelVolvoRowsRemains.get(i).getBackOrderQty());
                preparedStatement.setString(9, loadExcelVolvoRowsRemains.get(i).getQtyInTransit());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM stock_cs;");
            resultSet.next();
            message = "Загружено " + resultSet.getString(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (IOException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

// загрузка групп скидок Volvo+++++
    public static void loadSalesDiscountMatrix(String file) {
        try {
            connect();
            ObservableList<LoadExcelVolvoRowSaleGroup> loadExcelVolvoRowSaleGroup = FXCollections.observableArrayList();
            List dataAll = LoadExcelXSSF.openBookXSSF(file);
            for (int i = 1; i < dataAll.size(); i++) {
                List data = (List) dataAll.get(i);
                if (data.size() > 4) {
                    data.remove(0);
                    loadExcelVolvoRowSaleGroup.add(new LoadExcelVolvoRowSaleGroup(data));
                } else if (data.size() == 4) {
                    loadExcelVolvoRowSaleGroup.add(new LoadExcelVolvoRowSaleGroup(data));
                }
            }
            statement.execute("TRUNCATE TABLE sales_discount_matrix;");
            for (int i = 0; i < loadExcelVolvoRowSaleGroup.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO sales_discount_matrix VALUES (?, ?, ?, ?);");
                preparedStatement.setString(1, loadExcelVolvoRowSaleGroup.get(i).getNameProductGroup());
                preparedStatement.setString(2, loadExcelVolvoRowSaleGroup.get(i).getSaleGroup());
                preparedStatement.setString(3, loadExcelVolvoRowSaleGroup.get(i).getStockDiscount());
                preparedStatement.setString(4, loadExcelVolvoRowSaleGroup.get(i).getDailyDiscount());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM sales_discount_matrix;");
            resultSet.next();
            message = "Загружено " + resultSet.getString(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (IOException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }

    }

// выгрузка прайса Сток ЦС (0-й) +наценка/-скидка
    public static void formPriceStockCSVolvo(String discount) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_cs;");
            statement.execute("INSERT INTO price_cs " +
                    "SELECT stock_cs.part_number, stock_cs.name_part, 'Volvo', " +
                    "round(stock_cs.retail_price * (1 - sales_discount_matrix.stock_discount / 100) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), stock_cs.stock_remains " +
                    "FROM price_cs_parts, stock_cs, sales_discount_matrix " +
                    "WHERE price_cs_parts.part_number = stock_cs.part_number " +
                    "AND stock_cs.sale_group = sales_discount_matrix.sale_group;");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/STOCK_VOLVO_" + discount + "_" + dateAndTime() + ".xlsx";
            selectQuery(nameFile);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

// выгрузка прайса срочный ЦС (0-й) +наценка/-скидка
    public static void formPriceZSOCSVolvo(String discount) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_cs;");
            statement.execute("INSERT INTO price_cs " +
                    "SELECT stock_cs.part_number, stock_cs.name_part, 'Volvo', " +
                    "round(stock_cs.retail_price * (1 - sales_discount_matrix.daily_discount / 100) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), stock_cs.stock_remains " +
                    "FROM price_cs_parts, stock_cs, sales_discount_matrix " +
                    "WHERE price_cs_parts.part_number = stock_cs.part_number " +
                    "AND stock_cs.sale_group = sales_discount_matrix.sale_group;");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/ZSO_VOLVO_" + discount + "_" + dateAndTime() + ".xlsx";
            selectQuery(nameFile);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

// выгрузка в Excel+++++
    public static void selectQuery(String nameFile) {
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM price_cs;");
            ExportToExcel.exportToExcel(resultSet, nameFile);
            ResultSet rs = statement.executeQuery("SELECT count(*) FROM price_cs;");
            rs.next();
            message = "В прайсе " + rs.getString(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (IOException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

}
