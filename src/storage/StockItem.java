package storage;

public class StockItem extends ItemParser {

    private int index;
    private String type;
    private String brand;
    private String model;
    private String title;
    private int haveAmount;
    private int boughtPrice;
    private ItemTags tags;
    private String note;

    public StockItem(String line) {
        super(line);

        this.index = nextInt();
        this.type = nextString();
        this.brand = nextString();
        this.model = nextString();
        this.title = nextString();
        this.haveAmount = nextInt();
        this.boughtPrice = nextInt();
        this.tags = new ItemTags(nextString());
        this.note = nextString();
    }

    public StockItem(int index, String type, String brand, String model, String title) {
        super("");

        this.index = index;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.title = title;
        this.haveAmount = -1;
        this.boughtPrice = -1;
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

    public void setAmount(int value) {
        this.haveAmount = value;
    }

    public int getIndex() {
        return this.index;
    }

    public String summaryString() {
        String fmt = "%s: %s %s";
        String value = String.format(fmt, 
                this.type, 
                this.brand, 
                this.model);

        return value;
    }

    public String fileString() {
        String fmt = "%4s | %-15s | %-15s | %-20s | %-30s | %4s | %7s | %-30s | %-30s";
        String value = String.format(fmt, 
                this.index, 
                this.type, 
                this.brand, 
                this.model, 
                this.title,
                this.haveAmount < 0 ? "" : this.haveAmount, 
                this.boughtPrice < 0 ? "" : this.boughtPrice, 
                this.tags.toString(),
                this.note);

        return value;
    }

    public String toString() {
        String fmt = "| %4s | %-15s | %-15s | %-20s | %-30s | %4s | %7s | %-30s | %-30s |";
        String value = String.format(fmt, 
                this.index, 
                this.type, 
                this.brand, 
                this.model, 
                this.title,
                this.haveAmount < 0 ? "-" : this.haveAmount, 
                this.boughtPrice < 0 ? "" : this.boughtPrice, 
                this.tags.toString(), 
                this.note);

        return value;
    }

}
