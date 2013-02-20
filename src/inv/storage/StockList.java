package inv.storage;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class StockList {

    private ArrayList<StockItem> data;
    
    public StockList() {
        data = new ArrayList<StockItem>();
    }

    public void read(String filename) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filename));

        while ((line = br.readLine()) != null) {
            data.add(new StockItem(line));
        }

        br.close();
    }

    public int getCount() {
        return data.size();
    }

    public StockItem getItem(int index) {
        return data.get(index);
    }

    public void addItem(String type, String manufacturer, String model, String title) {
        int index = getFreeIndex();
        StockItem item = new StockItem(index, type, manufacturer, model, title);
        data.add(item);
    }

    private int getFreeIndex() {
        int i;
        int max = 0;
        StockItem item;

        for (i = 0; i < getCount(); i++) {
            item = getItem(i);
            if (item.getIndex() > max)
                max = item.getIndex();
        }
        return max + 1;
    }
}
