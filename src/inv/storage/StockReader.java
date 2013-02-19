package inv.storage;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class StockReader {

    private ArrayList<StockItem> data;
    
    public StockReader() {
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
}
