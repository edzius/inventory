
package storage;

public class CombinedItem {

    private StockItem item;
    private SellingItem sale;

    public CombinedItem(StockItem item) {
        this.item = item;
        this.sale = null;
    }

    public CombinedItem(StockItem item, SellingItem sale) {
        this.item = item;
        this.sale = sale;
    }

    private boolean isSellable() {
        return this.sale != null;
    }

    public String toString() {
        String fmt = "| %4s | %-15s | %-15s | %-20s | %-30s | %4s | %7s | %7s | %7s | %c | %-30s | %-30s |";
        String value = String.format(fmt, 
                item.getIndex(),
                item.getType(),
                item.getBrand(),
                item.getModel(),
                item.getTitle(),
                item.getAmount(),
                item.getBuyPrice() < 0 ? "-" : item.getBuyPrice(),
                !isSellable() ? "-" : sale.getSellPrice() ,
                !isSellable() || sale.getMarketPrice() < 0 ? "-" : sale.getMarketPrice(),
                isSellable() ? 'X' : ' ',
                item.getTagsString(),
                item.getNote());

        return value;

    }
}
