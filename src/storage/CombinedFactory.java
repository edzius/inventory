
package storage;

import java.util.ArrayList;

public class CombinedFactory {

    private String[] attributes;

    public CombinedFactory(String[] attrs) {
        this.attributes = attrs;
    }

    public CombinedFactory(String attr) {
        this.attributes = attr.split(",");
    }

    public CombinedItem[] joinAll(StockItem[] itemsArray, SellingItem[] salesArray, SoldItem[] soldsArray) {
        ArrayList<CombinedItem> data = new ArrayList<CombinedItem>();
        StockList items = new StockList(itemsArray);
        SellingList sales = new SellingList(salesArray);
        SoldList solds = new SoldList(soldsArray);

        for (int i = 0; i < items.getCount(); i++) {
            StockItem item = items.getElement(i);
            CombinedItem joined = join(item, sales, solds);
            data.add(joined);
        }

        if (data.size() == 0)
            return null;

        return data.toArray(new CombinedItem[1]);
    }

    public CombinedItem join(StockItem item, SellingList sales, SoldList solds) {
        SellingItem sale = null;
        SoldItem[] sold = null;

        if (sales.hasItemWithId(item.getIndex()))
            sale = sales.getItem(item.getIndex());

        if (solds.hasItemsWithId(item.getIndex()))
            sold = solds.getItemsById(item.getIndex());

        CombinedItem joined = new CombinedItem(item, sale, sold);
        joined.setAttributes(attributes);
        return joined;
    }
}

