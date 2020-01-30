package loadExcelJLRRow;

import java.util.List;

public class LoadExcelJLRRow {

    private String partNumber;
    private String stockRemains;

    public LoadExcelJLRRow(String partNumber, String stockRemains) {
        this.partNumber = partNumber;
        this.stockRemains = stockRemains;
    }

    public LoadExcelJLRRow(List data) {
        this.partNumber = (String) data.get(0);
        this.stockRemains = (String) data.get(1);
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getStockRemains() {
        return stockRemains;
    }

    public void setStockRemains(String stockRemains) {
        this.stockRemains = stockRemains;
    }
}
