
package storage;

public class CombinedItem {

    private StockItem item;
    private SellingItem sale;

    public CombinedItem(StockItem item, SellingItem sale) {
        this.item = item;
        this.sale = sale;
    }

    public String toString() {
        String fmt = "| %4s | %-15s | %-15s | %-20s | %-30s | %4s | %7s | %7s | %7s | %-30s | %-30s |";
        String value = String.format(fmt, 
                item.getIndex(),
                item.getType(),
                item.getBrand(),
                item.getModel(),
                item.getTitle(),
                item.getHaveAmount() < 0 ? "-" : item.getHaveAmount(),
                item.getBuyPrice() < 0 ? "-" : item.getBuyPrice(),
                sale.getSellPrice(),
                sale.getMarketPrice() < 0 ? "-" : sale.getMarketPrice(),
                item.getTagsString(),
                item.getNote());

        return value;

    }
}
