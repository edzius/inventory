
package storage;

import java.util.ArrayList;

public class CombinedFactory {

    public static CombinedItem[] combine(StockItem[] itemsArray, SellingItem[] salesArray) {
        ArrayList<CombinedItem> data = new ArrayList<CombinedItem>();
        StockList items = new StockList(itemsArray);
        SellingList sales = new SellingList(salesArray);

        for (int i = 0; i < items.getCount(); i++) {
            StockItem item = items.getElement(i);
            if (!sales.hasItem(item.getIndex())) {
                data.add(new CombinedItem(item));
                continue;
            }

            SellingItem sale = sales.getItem(item.getIndex());
            data.add(new CombinedItem(item, sale));
        }

        if (data.size() == 0)
            return null;

        return data.toArray(new CombinedItem[1]);
    }
}

