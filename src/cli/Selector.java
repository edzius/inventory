
package cli;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Selector {

    private ArrayList<String> data;   
    private String fileName;
    private boolean readOnly;

    public Selector(String[] values) {
        this.readOnly = true;
        clean();

        for (int i = 0; i < values.length; i++)
            data.add(values[i]);
    }

    public Selector(String fileName) throws IOException {
        this.readOnly = false;
        this.fileName = fileName;
        read(fileName);
    }

    private void clean() {
        this.data = new ArrayList<String>();
    }

    private void read(String fileName) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        clean();
        this.readOnly = false;

        while ((line = br.readLine()) != null) {
            data.add(line);
        }

        br.close();
    }

    private void write(String fileName) throws IOException {
        int i;
        String line;
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        for (i = 0; i < data.size(); i++) {
            line = data.get(i);
            bw.write(line, 0, line.length());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public void add(String value) {
        if (data.indexOf(value) != -1)
            return;

        data.add(value);

        if (readOnly)
            return;

        try {
            write(this.fileName);
        } catch (IOException e) {
            // XXX: log sometring.. Need to find out something about logging
        }
    }

    public int size() {
        return data.size();
    }

    public String get(int index) {
        return data.get(index);
    }

}
