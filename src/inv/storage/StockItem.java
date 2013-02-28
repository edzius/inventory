package inv.storage;

public class StockItem extends ItemParser {

    private int index;
    private String type;
    private String manufacturer;
    private String model;
    private String title;
    private int haveAmount;
    private float marketPrice;
    private float minePrice;
    private boolean selling;
    private ItemTags tags;
    private String note;

    public StockItem(String line) {
        super(line);

        this.index = nextInt();
        this.type = nextString();
        this.manufacturer = nextString();
        this.model = nextString();
        this.title = nextString();
        this.haveAmount = nextInt();
        this.marketPrice = nextFloat();
        this.minePrice = nextFloat();
        this.selling = nextBoolean();
        this.tags = new ItemTags(nextString());
        this.note = nextString();
    }

    public StockItem(int index, String type, String manufacturer, String model, String title) {
        super("");

        this.index = index;
        this.type = type;
        this.manufacturer = manufacturer;
        this.model = model;
        this.title = title;
        this.haveAmount = -1;
        this.marketPrice = -1;
        this.minePrice = -1;
        this.selling = false;
        this.tags = new ItemTags();
        this.note = "";
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public void clearTags() {
        tags.clear();
    }

    public String[] getTags() {
        return tags.toArray();
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public void setNote(String text) {
        this.note = text;
    }

    public void setMinePrice(float value) {
        this.minePrice = value;
    }

    public void setMarketPrice(float value) {
        this.marketPrice = value;
    }

    public void setAmount(int value) {
        this.haveAmount = value;
    }

    public void toggleSelling() {
        this.selling = !this.selling;
    }

    public int getIndex() {
        return this.index;
    }

    public String summaryString() {
        String fmt = "%s: %s %s";
        String value = String.format(fmt, 
                this.type, 
                this.manufacturer, 
                this.model);

        return value;
    }

    public String fileString() {
        String fmt = "%4s | %-15s | %-15s | %-20s | %-30s | %4s | %7s | %7s | %c | %-30s | %-30s";
        String value = String.format(fmt, 
                this.index, 
                this.type, 
                this.manufacturer, 
                this.model, 
                this.title,
                this.haveAmount < 0 ? "" : this.haveAmount, 
                this.marketPrice < 0 ? "" : this.marketPrice, 
                this.minePrice < 0 ? "" : this.minePrice,
                this.selling ? 'y' : 'n',
                this.tags.toString(),
                this.note);

        return value;
    }

    public String toString() {
        String fmt = "| %4s | %-15s | %-15s | %-20s | %-30s | %4s | %7s | %7s | %c | %-30s | %-30s |";
        String value = String.format(fmt, 
                this.index, 
                this.type, 
                this.manufacturer, 
                this.model, 
                this.title,
                this.haveAmount < 0 ? "-" : this.haveAmount, 
                this.marketPrice < 0 ? "-" : this.marketPrice, 
                this.minePrice < 0 ? "-" : this.minePrice,
                this.selling ? 'X' : ' ',
                this.tags == null ? "" : this.tags, 
                this.note == null ? "" : this.note);

        return value;
    }

}
