package storage;

public class SellingItem extends ItemParser {

    private int index;
    private int itemId;
    // Auxiliary, not required
    private String type;
    private String brand;
    private String model;
    // --------
    private float sellPrice;
    private float marketPrice;

    public SellingItem(String line) {
        super(line);

        this.index = nextInt();
        this.itemId = nextInt();

        this.type = nextString();
        this.brand = nextString();
        this.model = nextString();

        this.sellPrice = nextFloat();
        this.marketPrice = nextFloat();
    }

    public SellingItem(StockItem item, float price) {
        super("");

        this.index = -1;
        this.itemId = item.getIndex();
        this.type = item.getType();
        this.brand = item.getBrand();
        this.model = item.getModel();
        this.sellPrice = price;
        this.marketPrice = -1;
    }

    public void updateIndex(int index) {
        if (this.index != -1)
            return;

        this.index = index;
    }

    public void setSellPrice(float value) {
        this.sellPrice = value;
    }

    public void setMarketPrice(float value) {
        this.marketPrice = value;
    }

    public int getIndex() {
        return this.index;
    }

    public float getSellPrice() {
        return this.sellPrice;
    }

    public float getMarketPrice() {
        return this.marketPrice;
    }

    public String fileString() {
        String fmt = "%4s | %-15s | %-15s | %-20s | %7s | %7s";
        String value = String.format(fmt, 
                this.index, 
                this.type, 
                this.brand, 
                this.model, 
                this.sellPrice,
                this.marketPrice < 0 ? "" : this.marketPrice);

        return value;
    }

    public String toString() {
        String fmt = "| %4s | %4s | %-15s | %-15s | %-20s | %7s | %7s |";
        String value = String.format(fmt, 
                this.index, 
                this,itemId,
                this.type, 
                this.brand, 
                this.model, 
                this.sellPrice,
                this.marketPrice < 0 ? "-" : this.marketPrice);

        return value;
    }

}
