
package storage;

import java.util.ArrayList;

public class CombinedFactory {

    public static CombinedItem[] combine(StockItem[] itemsArray, SellingItem[] salesArray, SoldItem[] soldsArray) {
        ArrayList<CombinedItem> data = new ArrayList<CombinedItem>();
        StockList items = new StockList(itemsArray);
        SellingList sales = new SellingList(salesArray);
        SoldList solds = new SoldList(soldsArray);

        for (int i = 0; i < items.getCount(); i++) {
            StockItem item = items.getElement(i);
            SellingItem sale = null;
            SoldItem[] sold = null;

            if (sales.hasItem(item.getIndex()))
                sale = sales.getItem(item.getIndex());

            if (solds.hasItemsWithId(item.getIndex()))
                sold = solds.getItemsById(item.getIndex());
               
            data.add(new CombinedItem(item, sale, sold));
        }

        if (data.size() == 0)
            return null;

        return data.toArray(new CombinedItem[1]);
    }
}

