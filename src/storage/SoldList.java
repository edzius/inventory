package storage;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SoldList {

    private ArrayList<SoldItem> data;

    public SoldList(SoldItem[] values) {
        data = new ArrayList<SoldItem>();

        if (values == null)
            return;

        for (int i = 0; i < values.length; i++)
            data.add(values[i]);
    }

    public SoldList(String fileName) throws IOException {
        data = new ArrayList<SoldItem>();
        read(fileName);
    }

    public void read(String fileName) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while ((line = br.readLine()) != null) {
            if (line.trim().length() == 0)
                continue;

            data.add(new SoldItem(line));
        }

        br.close();
    }

    public void write(String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        for (int i = 0; i < getCount(); i++) {
            SoldItem item = getElement(i);
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

    public SoldItem getElement(int index) {
        return data.get(index);
    }

    // XXX: don't use getItemId() becouse it is 1-N connection.. One ItemId may have many sold items
    private int getPosition(int index) {
        for (int i = 0; i < getCount(); i++)
            if (getElement(i).getIndex() == index)
                return i;

        return -1;
    }

    public boolean hasItem(int index) {
        return getPosition(index) != -1;
    }
    
    public SoldItem getItem(int index) {
        int position = getPosition(index);
        return getElement(position);
    }

    public void addItem(SoldItem item) {
        int index = getFreeIndex();
        item.updateIndex(index);
        data.add(item);
    }

    public void deleteItem(int index) {
        int position = getPosition(index);
        System.out.println(index);
        data.remove(position);
    }

    public SoldItem[] getItemsById(int itemId) {
        int count = 0;
        for (int i = 0; i < getCount(); i++) {
            if (getElement(i).getItemId() != itemId)
                continue;

            count += 1;
        }

        if (count == 0)
            return null;

        SoldItem[] items = new SoldItem[count];
        int current = 0;
        for (int i = 0; i < getCount(); i++) {
            if (getElement(i).getItemId() != itemId)
                continue;

            items[current] = getElement(i);
            current += 1;
        }

        return items;
    }

    public boolean hasItemsWithId(int itemId) {
        for (int i = 0; i < getCount(); i++) {
            if (getElement(i).getItemId() != itemId)
                continue;

            return true;
        }
        return false;
    }

    private int getFreeIndex() {
        int max = 0;
        SoldItem item;

        for (int i = 0; i < getCount(); i++) {
            item = getElement(i);
            if (item.getIndex() > max)
                max = item.getIndex();
        }
        return max + 1;
    }

    public SoldItem[] toArray() {
        return data.toArray(new SoldItem[1]);
    }
}
