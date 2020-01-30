package loadExcelVolvoRow;

import java.util.List;

public class LoadExcelVolvoRowSaleGroup {

    private String nameProductGroup;
    private String saleGroup;
    private String stockDiscount;
    private String dailyDiscount;

    public LoadExcelVolvoRowSaleGroup(String nameProductGroup, String saleGroup, String stockDiscount, String dailyDiscount) {
        this.nameProductGroup = nameProductGroup;
        this.saleGroup = saleGroup;
        this.stockDiscount = stockDiscount;
        this.dailyDiscount = dailyDiscount;
    }

    public LoadExcelVolvoRowSaleGroup(List data) {
        this.nameProductGroup = (String) data.get(0);
        this.saleGroup = (String) data.get(1);
        this.stockDiscount = (String) data.get(2);
        this.dailyDiscount = (String) data.get(3);
    }

    public String getNameProductGroup() {
        return nameProductGroup;
    }

    public void setNameProductGroup(String nameProductGroup) {
        this.nameProductGroup = nameProductGroup;
    }

    public String getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(String saleGroup) {
        this.saleGroup = saleGroup;
    }

    public String getStockDiscount() {
        return stockDiscount;
    }

    public void setStockDiscount(String stockDiscount) {
        this.stockDiscount = stockDiscount;
    }

    public String getDailyDiscount() {
        return dailyDiscount;
    }

    public void setDailyDiscount(String dailyDiscount) {
        this.dailyDiscount = dailyDiscount;
    }
}
