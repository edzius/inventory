package storage;

public class SoldItem extends ItemParser {

    private int index;
    private int itemId;
    // Auxiliary, not required
    private String type;
    private String brand;
    private String model;
    // --------
    private int soldAmount;
    private float soldPrice;

    public SoldItem(String line) {
        super(line);

        this.index = nextInt();
        this.itemId = nextInt();

        this.type = nextString();
        this.brand = nextString();
        this.model = nextString();

        this.soldAmount = nextInt();
        this.soldPrice = nextFloat();
    }

    public SoldItem(StockItem item, float price, int amount) {
        super("");

        this.index = -1;
        this.itemId = item.getIndex();
        this.type = item.getType();
        this.brand = item.getBrand();
        this.model = item.getModel();
        this.soldAmount = amount;
        this.soldPrice = price;
    }

    public void updateIndex(int index) {
        if (this.index != -1)
            return;

        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public int getItemId() {
        return this.itemId;
    }

    public int getSoldAmount() {
        return this.soldAmount;
    }

    public float getSoldPrice() {
        return this.soldPrice;
    }

    public String fileString() {
        String fmt = "%4s | %4s | %-15s | %-15s | %-20s | %7s | %7s";
        String value = String.format(fmt, 
                this.index, 
                this.itemId,
                this.type, 
                this.brand, 
                this.model, 
                this.soldAmount,
                this.soldPrice < 0 ? "" : this.soldPrice);

        return value;
    }

    public String toString() {
        String fmt = "| %4s | %-15s | %-15s | %-20s | %7s | %7s |";
        String value = String.format(fmt, 
                this.index, 
                this.itemId,
                this.type, 
                this.brand, 
                this.model, 
                this.soldAmount,
                this.soldPrice < 0 ? "-" : this.soldPrice);

        return value;
    }

};


