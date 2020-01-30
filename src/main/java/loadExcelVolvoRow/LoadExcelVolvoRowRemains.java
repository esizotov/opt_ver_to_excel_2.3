package loadExcelVolvoRow;

import java.util.List;

public class LoadExcelVolvoRowRemains {

    private String partNumber;
    private String namePart;
    private String productGroup;
    private String functionalGroup;
    private String saleGroup;
    private String retailPrice;
    private String stockRemains;
    private String backOrderQty;
    private String qtyInTransit;

    public LoadExcelVolvoRowRemains(String partNumber, String namePart, String productGroup, String functionalGroup,
                                    String saleGroup, String retailPrice, String stockRemains, String backOrderQty,
                                    String qtyInTransit) {
        this.partNumber = partNumber;
        this.namePart = namePart;
        this.productGroup = productGroup;
        this.functionalGroup = functionalGroup;
        this.saleGroup = saleGroup;
        this.retailPrice = retailPrice;
        this.stockRemains = stockRemains;
        this.backOrderQty = backOrderQty;
        this.qtyInTransit = qtyInTransit;
    }

    public LoadExcelVolvoRowRemains(List data) {
        this.partNumber = (String) data.get(0);
        this.namePart = (String) data.get(1);
        this.productGroup = (String) data.get(2);
        this.functionalGroup = (String) data.get(3);
        this.saleGroup = (String) data.get(4);
        this.retailPrice = (String) data.get(5);
        this.stockRemains = (String) data.get(6);
        this.backOrderQty = (String) data.get(7);
        this.qtyInTransit = (String) data.get(8);
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getFunctionalGroup() {
        return functionalGroup;
    }

    public void setFunctionalGroup(String functionalGroup) {
        this.functionalGroup = functionalGroup;
    }

    public String getSaleGroup() {
        return saleGroup;
    }

    public void setSaleGroup(String saleGroup) {
        this.saleGroup = saleGroup;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getStockRemains() {
        return stockRemains;
    }

    public void setStockRemains(String stockRemains) {
        this.stockRemains = stockRemains;
    }

    public String getBackOrderQty() {
        return backOrderQty;
    }

    public void setBackOrderQty(String backOrderQty) {
        this.backOrderQty = backOrderQty;
    }

    public String getQtyInTransit() {
        return qtyInTransit;
    }

    public void setQtyInTransit(String qtyInTransit) {
        this.qtyInTransit = qtyInTransit;
    }
}
