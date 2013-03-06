
package storage;

public class CombinedItem {

    private StockItem item;
    private SellingItem sale;
    private SoldItem[] sold;

    // XXX: Accepts nulls
    public CombinedItem(StockItem item, SellingItem sale, SoldItem[] sold) {
        this.item = item;
        this.sale = sale;
        this.sold = sold;
    }

    private boolean isSellable() {
        return this.sale != null;
    }

    private boolean isSold() {
        return this.sold != null;
    }

    private int getStockAmount() {
        int soldAmount = getSoldAmount();
        if (soldAmount <= 0)
            return item.getAmount();

        return item.getAmount() - soldAmount;
    }

    private int getSoldAmount() {
        if (!isSellable() && !isSold())
            return -1;

        if (!isSold())
            return 0;

        int count = 0;
        for (int i = 0; i < this.sold.length; i++)
            count += this.sold[i].getSoldAmount();

        return count;
    }

    private float getSoldPrice() {
        if (!isSellable() && !isSold())
            return -1;

        if (!isSold())
            return 0;

        float price = 0;
        for (int i = 0; i < this.sold.length; i++)
            price += this.sold[i].getSoldAmount() * this.sold[i].getSoldPrice();

        return price;
    }

    public String header() {
        String fmt = "| %4s | %-15s | %-15s | %-20s | %-30s | %-30s | %-30s | %c | %4s | %4s | %7s | %7s | %7s |";
        String value = String.format(fmt, 
                "ID",
                "Type",
                "Brand",
                "Model",
                "Title",
                "Tags",
                "Note",
                "S",
                "Have",
                "Sold",
                "$Bought",
                "$Asking",
                "$Sold");

        return value;

    }

    public String toString() {
        String fmt = "| %4s | %-15s | %-15s | %-20s | %-30s | %-30s | %-30s | %c | %4s | %4s | %7s | %7s | %7s |";
        String value = String.format(fmt, 
                item.getIndex(),
                item.getType(),
                item.getBrand(),
                item.getModel(),
                item.getTitle(),
                item.getTagsString(),
                item.getNote(),
                isSellable() ? 'X' : ' ',
                getStockAmount(),
                getSoldAmount() < 0 ? "-" : getSoldAmount(),
                item.getBuyPrice() < 0 ? "-" : item.getBuyPrice(),
                !isSellable() ? "-" : sale.getSellPrice(),
                getSoldPrice() < 0 ? "-" : getSoldPrice());

        return value;
    }
}
