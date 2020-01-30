package exportToExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportToExcel {

    public static void exportToExcel(ResultSet resultSet, String nameFile) throws SQLException, IOException {

        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet("Лист1");
        Row rowHead = sheet.createRow(0);
        Cell partHead = rowHead.createCell(0);
        partHead.setCellValue("CODE");

        Cell nameHead = rowHead.createCell(1);
        nameHead.setCellValue("NAME");

        Cell catalogHead = rowHead.createCell(2);
        catalogHead.setCellValue("Catalog");

        Cell priceHead = rowHead.createCell(3);
        priceHead.setCellValue("Price");

        Cell remainsHead = rowHead.createCell(4);
        remainsHead.setCellValue("Stock_CS");

        int i = 1;

        while (resultSet.next()) {

            Row row = sheet.createRow(i);
            Cell part = row.createCell(0);
            part.setCellValue(resultSet.getString(1));

            Cell name = row.createCell(1);
            name.setCellValue(resultSet.getString(2));

            Cell catalog = row.createCell(2);
            catalog.setCellValue(resultSet.getString(3));

            Cell price = row.createCell(3);
            price.setCellValue(resultSet.getDouble(4));

            Cell remains = row.createCell(4);
            remains.setCellValue(resultSet.getInt(5));

            i++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);

        book.write(new FileOutputStream(new File(nameFile)));
        book.close();
    }
}
