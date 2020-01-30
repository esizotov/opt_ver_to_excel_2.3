package loadExcelVolvoRow;

import java.util.List;

public class LoadExcelVolvoRow {

    private String productGroup;
    private String functionalGroup;
    private String partNumber;
    private String namePart;
    private String saleGroup;
    private String retailPrice;

    public LoadExcelVolvoRow(String productGroup, String functionalGroup, String partNumber, String namePart, String saleGroup, String retailPrice) {
        this.productGroup = productGroup;
        this.functionalGroup = functionalGroup;
        this.partNumber = partNumber;
        this.namePart = namePart;
        this.saleGroup = saleGroup;
        this.retailPrice = retailPrice;
    }

    public LoadExcelVolvoRow(List data) {
        this.productGroup = (String) data.get(0);
        this.functionalGroup = (String) data.get(1);
        this.partNumber = (String) data.get(2);
        this.namePart = (String) data.get(3);
        this.saleGroup = (String) data.get(4);
        this.retailPrice = (String) data.get(5);
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
}
