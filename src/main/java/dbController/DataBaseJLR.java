package dbController;

import exportToExcel.ExportToExcel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loadExcel.LoadExcelHSSF;
import loadExcelJLRRow.LoadExcelJLRRow;
import messageWindows.ControllerMessage;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static dbController.DataBaseAudi.dateAndTime;

public class DataBaseJLR {

    private static Connection connection;
    private static Statement statement;
    private static String message;
    private static String nameFile;

    //+++++
    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/opt_jlr?useUnicode=true&serverTimezone=UTC&useSSL=false", "root", "root");
        statement = connection.createStatement();
    }

    //+++++
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // загрузка прайс-листа Jaguar+++++
    public static void loadPartsAllJaguar(String file) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_parts_jaguar;");
            statement.execute("LOAD DATA INFILE '" + file.replace("\\", "/") + "' " +
                    "IGNORE INTO TABLE price_parts_jaguar " +
                    "FIELDS TERMINATED BY ';';");
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM price_parts_jaguar;");
            resultSet.next();
            message = "Загружено " + resultSet.getInt(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

    // загрузка прайс-лста LR+++++
    public static void loadPartsAllLR(String file) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_parts_lr;");
            statement.execute("LOAD DATA INFILE '" + file.replace("\\", "/") + "' " +
                    "IGNORE INTO TABLE price_parts_lr " +
                    "FIELDS TERMINATED BY ';';");
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM price_parts_lr;");
            resultSet.next();
            message = "Загружено " + resultSet.getInt(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

    // загрузка остатков ЦС LR+++++
    public static void loadPartsCSLR(String file) {
        try {
            connect();
            ObservableList<LoadExcelJLRRow> loadExcelJLRRows = FXCollections.observableArrayList();
            List dataAll = LoadExcelHSSF.openBook(file);
            for (int i = 1; i < dataAll.size(); i++) {
                List data = (List) dataAll.get(i);
                loadExcelJLRRows.add(new LoadExcelJLRRow(data));
            }
            statement.execute("TRUNCATE TABLE stock_cs_lr;");
            for (int i = 0; i < loadExcelJLRRows.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO stock_cs_lr VALUES (?, ?);");
                preparedStatement.setString(1, loadExcelJLRRows.get(i).getPartNumber());
                preparedStatement.setString(2, loadExcelJLRRows.get(i).getStockRemains());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM stock_cs_lr;");
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

    // загрузка остатков ЦС Jaguar+++++
    public static void loadPartsCSJaguar(String file) {
        try {
            connect();
            ObservableList<LoadExcelJLRRow> loadExcelJLRRows = FXCollections.observableArrayList();
            List dataAll = LoadExcelHSSF.openBook(file);
            for (int i = 1; i < dataAll.size(); i++) {
                List data = (List) dataAll.get(i);
                loadExcelJLRRows.add(new LoadExcelJLRRow(data));
            }
            statement.execute("TRUNCATE TABLE stock_cs_jaguar;");
            for (int i = 0; i < loadExcelJLRRows.size(); i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT IGNORE INTO stock_cs_jaguar VALUES (?, ?);");
                preparedStatement.setString(1, loadExcelJLRRows.get(i).getPartNumber());
                preparedStatement.setString(2, loadExcelJLRRows.get(i).getStockRemains());
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
            }
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM stock_cs_jaguar;");
            resultSet.next();
            message = "Загружено " + resultSet.getString(1) + " строк!";
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

    // выгрузка прайса Сток ЦС JLR (0-й) +наценка/-скидка+++++
    public static void formPriceStockCSJLR(String discount) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_cs;");
            statement.execute("INSERT IGNORE INTO price_cs " +
                    "SELECT part_number, description, 'Land Rover', " +
                    "round(cast(dealer_stk_price as char) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs_lr, price_parts_lr " +
                    "WHERE part_number = material_no;");
            statement.execute("INSERT IGNORE INTO price_cs " +
                    "SELECT part_number, description, 'Jaguar', " +
                    "round(cast(dealer_stk_price as char) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs_jaguar, price_parts_jaguar " +
                    "WHERE part_number = material_no;");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/STOCK_JLR_" + discount + "_" + dateAndTime() + ".xlsx";
            selectQuery(nameFile);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

    //выгрузка прайса Срочный ЦС JLR+++++
    public static void formPriceZSOCSJLR(String discount) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_cs;");
            statement.execute("INSERT IGNORE INTO price_cs " +
                    "SELECT part_number, description, 'Land Rover', " +
                    "round(cast(dealer_vor_price as char) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs_lr, price_parts_lr " +
                    "WHERE part_number = material_no;");
            statement.execute("INSERT IGNORE INTO price_cs " +
                    "SELECT part_number, description, 'Jaguar', " +
                    "round(cast(dealer_vor_price as char) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs_jaguar, price_parts_jaguar " +
                    "WHERE part_number = material_no;");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/ZSO_JLR_" + discount + "_" + dateAndTime() + ".xlsx";
            selectQuery(nameFile);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

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
            ControllerMessage.messageWindowDone(message);
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(message);
        } catch (IOException e) {
            ControllerMessage.messageWindowDone(message);
        }
    }
}
