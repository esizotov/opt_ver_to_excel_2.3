package loadExcel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoadExcelXSSF {

    public static List openBookXSSF(String file) throws IOException {

        List dataAll = new ArrayList();

        FileInputStream fileInputStream = new FileInputStream(new File(file));
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator iterator = sheet.rowIterator();
        while (iterator.hasNext()) {
            Row row = (Row) iterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List data = new ArrayList();
            while (cellIterator.hasNext()) {
                XSSFCell cell = (XSSFCell) cellIterator.next();
                CellType cellType = cell.getCellTypeEnum();

                switch (cellType) {
                    case _NONE:
                        data.add("NONE");
                        break;
                    case BOOLEAN:
                        data.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    case BLANK:
                        break;
                    case FORMULA:
                        data.add(cell.getCellFormula());
                        break;
                    case NUMERIC:
                        data.add(String.valueOf(cell.getNumericCellValue()));
                        break;
                    case STRING:
                        data.add(cell.getStringCellValue());
                        break;
                    case ERROR:
                        break;
                }
            }
            dataAll.add(data);
        }
        fileInputStream.close();
        return dataAll;
    }
}
