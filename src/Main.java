
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import com.obixlabs.commons.io.InteractiveCommandLineReader;
import com.obixlabs.commons.io.FreeTextConsoleInputHandler;

import java.io.IOException;

import inv.storage.StockList;

public class Main {

    private CommandLine params;
    private boolean verbose = false;
    private StockList items;
    private String sourceFile;

    public Main(CommandLine params) {
        items = new StockList();

        this.params = params;
        if (params.hasOption('v'))
            verbose = true;
    }

    public void readItemsFile(String filename) throws IOException {
        this.sourceFile = filename;
        items.read(filename);
    }

    public void writeItemsFile(String filename) throws IOException {
        items.write(filename);
    }

    public void saveItemsFile() throws IOException {
        writeItemsFile(this.sourceFile);
    }

    public void listStockItems() {
        int i;
        int count = items.getCount();

        for (i = 0; i < count; i++)
            System.out.println(items.getItem(i));
    }

    public void addStockItem(String type, String model, String manufacturer, String title) {
        items.addItem(type, manufacturer, model, title);
    }

    public static void die(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void ok(String message) {
        System.err.println(message);
        System.exit(0);
    }

    public static String readCLI(String caption) {
        FreeTextConsoleInputHandler hndl = new FreeTextConsoleInputHandler(caption + ": ", "Invalid " + caption + ", repeat: ", true);
        try {
            InteractiveCommandLineReader.prompt(hndl);
        } catch (IOException e) {
            return null;
        }
        return hndl.getInputValue();
    }

    public static void process(Main ctrl, CommandLine params) throws IOException {
        if (params.hasOption('l')) {
            ctrl.listStockItems();
            ok("Stock items listed succesfully");
        }

        if (params.hasOption('a')) {
            String type = readCLI("product type");
            String model = readCLI("product model");
            String manufacturer = readCLI("product manufacturer");
            String title = readCLI("product title");
            ctrl.addStockItem(type, model, manufacturer, title);
            ctrl.saveItemsFile();
            ok("Stock item added succesfully");
        }
    }

	public static void main(String[] args) {

		Option verbose = OptionBuilder.withLongOpt("verbose")
                                      .withDescription("Set verbose output")
                                      .create('v');
		Option datafile = OptionBuilder.withLongOpt("datafile")
                                       .withDescription("Set data file to read data from")
                                       .hasArg()
                                       .create('f');
		Option stockList = OptionBuilder.withLongOpt("list")
                                        .withDescription("List exisiting stock items")
                                        .create('l');
		Option stockAdd = OptionBuilder.withLongOpt("add")
                                       .withDescription("Add new item to stock")
                                       .create('a');

        Options options = new Options();
        options.addOption(verbose);
        options.addOption(datafile);
        options.addOption(stockList);
        options.addOption(stockAdd);

        CommandLine params = null;
        CommandLineParser parser = new GnuParser();
        try {
            params = parser.parse(options, args);
        } catch (ParseException exp) {
            die("Parsing failed. Reason: " + exp.getMessage());
        }

        Main ctrl = new Main(params);

        String sourceFile = "stock.db";
        if (params.hasOption('f'))
            sourceFile = params.getOptionValue('f');

        try {
            ctrl.readItemsFile(sourceFile);
        } catch (IOException e) {
            die(e.getMessage());
        }

        try {
            process(ctrl, params);
        } catch (Exception e) {
            die(e.getMessage());
        } finally {
            try {
                ctrl.saveItemsFile();
            } catch (IOException ex) {
                die(ex.getMessage());
            }
        }
	}
};

