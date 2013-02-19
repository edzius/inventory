package inv.storage;

public class SoldItem extends ItemParser {

    private int index;
    private String model;
    private int soldAmount;
    private float minePrice;
    private float soldPrice;
    private boolean soldout;

    public SoldItem(String line) {
        super(line);

        this.index = nextInt();
        this.model = nextString();
        this.soldAmount = nextInt();
        this.minePrice = nextFloat();
        this.soldPrice = nextFloat();
        this.soldout = nextBoolean();
    }

    public String toString() {
        String fmt = "| %-20s | %4s | %7s | %7s | %c |";
        String value = String.format(fmt, 
                this.model, 
                this.soldAmount < 0 ? "-" : this.soldAmount,
                this.minePrice < 0 ? "-" : this.minePrice,
                this.soldPrice < 0 ? "-" : this.soldPrice,
                this.soldout ? 'X' : ' ');

        return value;
    }
};


