package storage;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SellingList {

    private ArrayList<SellingItem> data;

    public SellingList(SellingItem[] values) {
        data = new ArrayList<SellingItem>();

        if (values == null)
            return;

        for (int i = 0; i < values.length; i++)
            data.add(values[i]);
    }

    public SellingList(String fileName) throws IOException {
        data = new ArrayList<SellingItem>();
        read(fileName);
    }

    public void read(String fileName) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while ((line = br.readLine()) != null) {
            if (line.trim().length() == 0)
                continue;

            data.add(new SellingItem(line));
        }

        br.close();
    }

    public void write(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        for (int i = 0; i < getCount(); i++) {
            SellingItem item = getElement(i);
            String line = item.fileString();

            bw.write(line, 0, line.length());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public int getCount() {
        return data.size();
    }

    public SellingItem getElement(int index) {
        return data.get(index);
    }

    private int getPosition(int index) {
        for (int i = 0; i < getCount(); i++)
            if (getElement(i).getIndex() == index)
                return i;

        return -1;
    }

    public boolean hasItem(int index) {
        return getPosition(index) != -1;
    }
    
    public SellingItem getItem(int index) {
        int position = getPositionById(index);
        return getElement(position);
    }

    public void addItem(SellingItem item) {
        int index = getFreeIndex();
        item.updateIndex(index);
        data.add(item);
    }

    public void deleteItem(int index) {
        int position = getPositionById(index);
        data.remove(position);
    }   

    public int getPositionById(int id) {
        for (int i = 0; i < getCount(); i++)
            if (getElement(i).getItemId() == id)
                return i;

        return -1;
    }

    public boolean hasItemWithId(int id) {
        return getPositionById(id) != -1;
    }

    private int getFreeIndex() {
        int max = 0;
        SellingItem item;

        for (int i = 0; i < getCount(); i++) {
            item = getElement(i);
            if (item.getIndex() > max)
                max = item.getIndex();
        }
        return max + 1;
    }

    public SellingItem[] toArray() {
        return data.toArray(new SellingItem[1]);
    }
}
