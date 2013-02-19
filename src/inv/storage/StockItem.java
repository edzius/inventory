package inv.storage;

public class StockItem extends ItemParser {

    private int index;
    private String model;
    private String title;
    private int haveAmount;
    private float marketPrice;
    private float minePrice;
    private boolean selling;
    private String tags;
    private String note;

    public StockItem(String line) {
        super(line);

        this.index = nextInt();
        this.model = nextString();
        this.title = nextString();
        this.haveAmount = nextInt();
        this.marketPrice = nextFloat();
        this.minePrice = nextFloat();
        this.selling = nextBoolean();
        this.tags = nextString();
        this.note = nextString();
    }

    public String toString() {
        String fmt = "| %-20s | %-30s | %4s | %7s | %7s | %c | %-30s | %-30s |";
        String value = String.format(fmt, 
                this.model, 
                this.title,
                this.haveAmount, 
                this.marketPrice < 0 ? "-" : this.marketPrice, 
                this.minePrice < 0 ? "-" : this.minePrice,
                this.selling ? 'X' : ' ',
                this.tags, 
                this.note);

        return value;
    }

}
