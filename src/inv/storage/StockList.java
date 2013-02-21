package inv.storage;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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

    public void write(String filename) throws IOException {
        int i;
        String line;
        StockItem item;
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

        for (i = 0; i < getCount(); i++) {
            item = getElement(i);
            line = item.fileString();

            bw.write(line, 0, line.length());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public int getCount() {
        return data.size();
    }

    public StockItem getElement(int index) {
        return data.get(index);
    }

    private int getPosition(int index) {
        int i;
        for (i = 0; i < getCount(); i++)
            if (getElement(i).getIndex() == index)
                return i;

        return -1;
    }


    public boolean hasItem(int index) {
        return getPosition(index) != -1;
    }
    
    public StockItem getItem(int index) {
        int position = getPosition(index);
        return getElement(position);
    }

    public void addItem(String type, String manufacturer, String model, String title) {
        int index = getFreeIndex();
        StockItem item = new StockItem(index, type, manufacturer, model, title);
        data.add(item);
    }

    public void deleteItem(int index) {
        int position = getPosition(index);
        System.out.println(index);
        data.remove(position);
    }   

    private int getFreeIndex() {
        int i;
        int max = 0;
        StockItem item;

        for (i = 0; i < getCount(); i++) {
            item = getElement(i);
            if (item.getIndex() > max)
                max = item.getIndex();
        }
        return max + 1;
    }
}
