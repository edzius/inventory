package storage;

import java.util.ArrayList;

class ItemTags {

    private static final String TAG_SEPARATOR = ",";

    private ArrayList<String> tags;

    public ItemTags(String line) {
        String[] tarr = line.split(TAG_SEPARATOR);
        tags = new ArrayList<String>();

        for (int i = 0; i < tarr.length; i++)
            tags.add(tarr[i]);
    }

    public ItemTags() {
        tags = new ArrayList<String>();
    }

    public int find(String tag) {
        return tags.indexOf(tag);
    }

    public void add(String tag) {
        if (find(tag) == -1)
            tags.add(tag);
    }

    public void remove(String tag) {
        if (find(tag) != -1)
            tags.remove(tag);
    }

    public void clear() {
        tags.clear();
    }

    public String toString() {
        String line = "";
        for (int i = 0; i < tags.size(); i++) {
            line += tags.get(i);
            if (i < tags.size() - 1)
                line += TAG_SEPARATOR;
        }

        return line;
    }

    public String[] toArray() {
        return tags.toArray(new String[1]);
    }
}
