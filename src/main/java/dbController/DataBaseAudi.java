package dbController;

import exportToExcel.ExportToExcel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import messageWindows.ControllerMessage;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBaseAudi {

    private static Connection connection;
    private static Statement statement;
    private static String message;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_S");
    private static String nameFile;

    //+++++
    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/opt?useUnicode=true&serverTimezone=UTC&useSSL=false", "root", "root");
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

    // определение даты и времени +++++
    public static String dateAndTime() {
        String dateAndTime = dateFormat.format(new Date(System.currentTimeMillis()));
        return dateAndTime;
    }

    // заполнение TableViw +++++
    public static ObservableList<DataBaseOutput> initTableView() {
        ObservableList<DataBaseOutput> dataBaseOutputs = FXCollections.observableArrayList();
        try {
            connect();
            ResultSet resultSet = statement.executeQuery("SELECT AX_TNR, name, " +
                    "ROUND(cast(AN_DMEXP as char) / 100 * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts " +
                    "WHERE AX_TNR = TRIM(part_number) " +
                    "AND ck != '10' and ck != '12' and ck != '13' and ck != '14' and ck != '16';");
            while (resultSet.next()) {
                dataBaseOutputs.add(new DataBaseOutput(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getInt(4)));
            }
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
        return dataBaseOutputs;
    }
    // загрузка полного прайса +++++
    public static void loadAllPrice(String loadFileAllPrice) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_cs_parts;");
            statement.execute("LOAD DATA INFILE '" + loadFileAllPrice.replace("\\", "/") + "' " +
                    "IGNORE INTO TABLE price_cs_parts " +
                    "FIELDS TERMINATED BY ';';");
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM price_cs_parts;");
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

    // загрузка остатков ЦС +++++
    public static void loadPartsCS(String loadFileStockCS) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE stock_cs;");
            statement.execute("LOAD DATA INFILE '" + loadFileStockCS.replace("\\", "/") + "' " +
                    "IGNORE INTO TABLE stock_cs " +
                    "FIELDS TERMINATED BY '|' IGNORE 6 ROWS;");
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM stock_cs;");
            resultSet.next();
            message = "Загружено " + resultSet.getString(1) + " строк!";
            ControllerMessage.messageWindowDone(message);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

    // выгрузка прайса Сток ЦС (0-й) +наценка/-скидка
    public static void formPriceStockCS(String discount) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_stock_cs;");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', round((cast(AN_DMEXP as char) / 100 - cast(AN_DMEXP as char) / 100 * percent_sale / 100) * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale " +
                    "WHERE AX_TNR = trim(part_number) AND AN_RAGR = group_sale;");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/STOCK_" + discount + "_" + dateAndTime() + ".xlsx";
            selectQuery(nameFile);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

    // выгрузка прайса Срочный ЦС (0-й) +наценка/-скидка
    public static void formPriceZSOCS(String discount) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_stock_cs;");
            statement.execute("INSERT INTO price_stock_cs " +
                    "select AX_TNR, name, 'VAG', round(cast(AN_DMEXP as char) / 100 * " +
                    (1 + Double.valueOf(discount) / 100) + " * 1.2, 2), remains_of_cs " +
                    "from stock_cs, price_cs_parts " +
                    "where AX_TNR = trim(part_number) AND ck != '10' and ck != '12' and ck != '13' and ck != '14' and ck != '16';");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/ZSO_" + discount + "_" + dateAndTime() + ".xlsx";
            selectQuery(nameFile);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

    // выгрузка прайса Сток ЦС по группам +наценка/-скидка +++++
    public static void formPriceStockGroups(String discount12, String discount8, String discount4, String discount2) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_stock_cs;");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', round((cast(AN_DMEXP as char) / 100 - cast(AN_DMEXP as char) / 100 * percent_sale / 100) * " +
                    (1 + Double.valueOf(discount12) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_ti_bonus_12 " +
                    "WHERE AX_TNR = trim(part_number) AND AN_RAGR = group_sale " +
                    "AND concat(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_ti;");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', round((cast(AN_DMEXP as char) / 100 - cast(AN_DMEXP as char) / 100 * percent_sale / 100) * " +
                    (1 + Double.valueOf(discount8) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_wd_bonus_8 " +
                    "WHERE AX_TNR = trim(part_number) AND AN_RAGR = group_sale " +
                    "AND concat(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_wd;");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', ROUND((cast(AN_DMEXP as char) / 100 - cast(AN_DMEXP as char) / 100 * percent_sale / 100) * " +
                    (1 + Double.valueOf(discount4) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_rp_bonus_6 " +
                    "WHERE AX_TNR = TRIM(part_number) AND AN_RAGR = group_sale " +
                    "AND CONCAT(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_rp;");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', ROUND((cast(AN_DMEXP as char) / 100 - cast(AN_DMEXP as char) / 100 * percent_sale / 100) * " +
                    (1 + Double.valueOf(discount2) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_od_bonus_4 " +
                    "WHERE AX_TNR = TRIM(part_number) AND AN_RAGR = group_sale " +
                    " AND CONCAT(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_od;");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/STOCK_GROUPS_" + dateAndTime() + ".xlsx";
            selectQuery(nameFile);
            disconnect();
        } catch (SQLException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        } catch (ClassNotFoundException e) {
            ControllerMessage.messageWindowDone(String.valueOf(e));
        }
    }

    // выгрузка прайса Срочный ЦС по группам +наценка/-скидка +++++
    public static void formPriceZSOGroups(String discount12, String discount8, String discount4, String discount2) {
        try {
            connect();
            statement.execute("TRUNCATE TABLE price_stock_cs;");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', round(cast(AN_DMEXP as char) / 100 * " +
                    (1 + Double.valueOf(discount12) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_ti_bonus_12 " +
                    "WHERE AX_TNR = trim(part_number) AND AN_RAGR = group_sale " +
                    "AND concat(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_ti " +
                    "AND ck != '10' AND ck != '12' AND ck != '13' AND ck != '14' AND ck != '16';");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', round(cast(AN_DMEXP as char) / 100 * " +
                    (1 + Double.valueOf(discount8) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_wd_bonus_8 " +
                    "WHERE AX_TNR = trim(part_number) AND AN_RAGR = group_sale " +
                    "AND concat(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_wd " +
                    "AND ck != '10' AND ck != '12' AND ck != '13' AND ck != '14' AND ck != '16';");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', ROUND(cast(AN_DMEXP as char) / 100 * " +
                    (1 + Double.valueOf(discount4) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_rp_bonus_6 " +
                    "WHERE AX_TNR = TRIM(part_number) AND AN_RAGR = group_sale " +
                    "AND CONCAT(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_rp " +
                    "AND ck != '10' AND ck != '12' AND ck != '13' AND ck != '14' AND ck != '16';");
            statement.execute("INSERT INTO price_stock_cs " +
                    "SELECT AX_TNR, name, 'VAG', ROUND(cast(AN_DMEXP as char) / 100 * " +
                    (1 + Double.valueOf(discount2) / 100) + " * 1.2, 2), remains_of_cs " +
                    "FROM stock_cs, price_cs_parts, stock_sale, ex_od_bonus_4 " +
                    "WHERE AX_TNR = TRIM(part_number) AND AN_RAGR = group_sale " +
                    "AND CONCAT(AX_REP_1, AX_REP_2, AX_REP_3) = gomo_group_od " +
                    "AND ck != '10' AND ck != '12' AND ck != '13' AND ck != '14' AND ck != '16';");
            nameFile = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/Out/ZSO_GROUPS_" + dateAndTime() + ".xlsx";
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM price_stock_cs;");
            ExportToExcel.exportToExcel(resultSet, nameFile);
            ResultSet rs = statement.executeQuery("SELECT count(*) FROM price_stock_cs;");
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
