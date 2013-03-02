package storage;

public class StockItem extends ItemParser {

    private int index;
    private String type;
    private String brand;
    private String model;
    private String title;
    private int haveAmount;
    private float buyPrice;
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
        this.buyPrice = nextFloat();
        this.tags = new ItemTags(nextString());
        this.note = nextString();
    }

    public StockItem(String type, String brand, String model, String title) {
        super("");

        this.index = -1;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.title = title;
        this.haveAmount = -1;
        this.buyPrice = -1;
        this.tags = new ItemTags();
        this.note = "";
    }

    public boolean match(String value) {
        if (this.type.indexOf(value) != -1)
            return true;
        if (this.brand.indexOf(value) != -1)
            return true;
        if (this.model.indexOf(value) != -1)
            return true;
        if (this.tags.find(value) != -1)
            return true;

        return false;
    }

    public void updateIndex(int index) {
        if (this.index != -1)
            return;

        this.index = index;
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

    public void setBuyPrice(float value) {
        this.buyPrice = value;
    }

    public int getIndex() {
        return this.index;
    }

    public String getType() {
        return this.type;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getModel() {
        return this.model;
    }

    public String getTitle() {
        return this.title;
    }

    public int getHaveAmount() {
        return this.haveAmount;
    }

    public float getBuyPrice() {
        return this.buyPrice;
    }

    public String getTagsString() {
        return this.tags.toString();
    }

    public String getNote() {
        return this.note;
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
                this.buyPrice < 0 ? "" : this.buyPrice, 
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
                this.buyPrice < 0 ? "" : this.buyPrice, 
                this.tags.toString(), 
                this.note);

        return value;
    }

}
