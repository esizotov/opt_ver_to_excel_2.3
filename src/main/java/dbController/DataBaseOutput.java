package dbController;

public class DataBaseOutput {

    private String partNumber;
    private String namePart;
    private Double price;
    private Integer remains;

    public DataBaseOutput(String partNumber, String namePart, Double price, Integer remains) {
        this.partNumber = partNumber;
        this.namePart = namePart;
        this.price = price;
        this.remains = remains;
    }

    public DataBaseOutput() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRemains() {
        return remains;
    }

    public void setRemains(Integer remains) {
        this.remains = remains;
    }

}
