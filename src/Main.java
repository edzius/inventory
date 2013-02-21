
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
import inv.storage.StockItem;

/*
 * TODO:
 * * Add item type classificator. Make it selectable via CLI and possibility to add new if required.
 * * Add item manufactorer classificator. Make it selectable via CLI and possibility to add new if required.
 * * Add tags appending and removing
 * * Add option to move item to sold list
 * * Separate StockItems and SellingItems lists
 */

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
            System.out.println(items.getElement(i));
    }

    public StockItem getStockItem(int index) {
        return items.getItem(index);
    }

    public void addStockItem(String type, String model, String manufacturer, String title) {
        items.addItem(type, manufacturer, model, title);
    }

    public void deleteStockItem(int index) {
        items.deleteItem(index);
    }

    public boolean hasStockItem(int index) {
        return items.hasItem(index);
    }

    public static void die(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static void ok(String message) {
        System.err.println(message);
        System.exit(0);
    }

    public static void perror(String message) {
        System.err.println(message);
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
            perror("Stock items listed succesfully");
        }

        if (params.hasOption('a')) {
            String type = readCLI("product type");
            String model = readCLI("product model");
            String manufacturer = readCLI("product manufacturer");
            String title = readCLI("product title");
            ctrl.addStockItem(type, model, manufacturer, title);
            perror("Stock item added succesfully");
        }

        if (params.hasOption('s')) {
            int index = Integer.parseInt(params.getOptionValue('s'));
            StockItem item;

            if (!ctrl.hasStockItem(index))
                die(String.format("Stock item %d not found", index));

            item = ctrl.getStockItem(index);
            if (params.hasOption('d')) {
                ctrl.deleteStockItem(index);
                perror(String.format("Deleted item %s", item.summaryString()));
                return;
            }

            if (params.hasOption("noteRemove")) {
                item.setNote("");
                perror(String.format("Removed item note"));
            }

            if (params.hasOption("noteSet")) {
                item.setNote(params.getOptionValue("noteSet"));
                perror(String.format("Updated item note"));
            }

            if (params.hasOption("selling")) {
                item.toggleSelling();
                perror(String.format("Updated item selling state"));
            }

            if (params.hasOption("priceMarket")) {
                float value = Float.parseFloat(params.getOptionValue("priceMarket"));
                item.setMarketPrice(value);
                perror(String.format("Updated item market price"));
            }

            if (params.hasOption("priceMine")) {
                float value = Float.parseFloat(params.getOptionValue("priceMine"));
                item.setMinePrice(value);
                perror(String.format("Updated item selling price"));
            }

            if (params.hasOption("amountSet")) {
                int value = Integer.parseInt(params.getOptionValue("amountSet"));
                item.setAmount(value);
                perror(String.format("Updated item stock amount"));
            }

            System.out.println(item.toString());
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
		Option stockSelect = OptionBuilder.withLongOpt("select")
                                       .withDescription("Select an item from stock")
                                       .hasArg()
                                       .create('s');
		Option stockRemove = OptionBuilder.withLongOpt("delete")
                                       .withDescription("Delete an item from stock")
                                       .create('d');
		Option itemNoteSet = OptionBuilder.withLongOpt("note-set")
                                       .withDescription("Update stock item note")
                                       .hasArg()
                                       .create("noteSet");
		Option itemNoteRemove = OptionBuilder.withLongOpt("note-remove")
                                       .withDescription("Remove stock item note")
                                       .create("noteRemove");
		Option itemSelling = OptionBuilder.withLongOpt("selling")
                                       .withDescription("Toggle item selling or not")
                                       .create("selling");
		Option itemPriceMine = OptionBuilder.withLongOpt("price-mine")
                                       .withDescription("Update item market price")
                                       .hasArg()
                                       .create("priceMine");
		Option itemPriceMarket = OptionBuilder.withLongOpt("price-market")
                                       .withDescription("Update item selling price")
                                       .hasArg()
                                       .create("priceMarket");
		Option itemAmountSet = OptionBuilder.withLongOpt("amount-set")
                                       .withDescription("Update item stock amount")
                                       .hasArg()
                                       .create("amountSet");

        Options options = new Options();
        options.addOption(verbose);
        options.addOption(datafile);
        options.addOption(stockList);
        options.addOption(stockAdd);
        options.addOption(stockSelect);
        options.addOption(stockRemove);
        options.addOption(itemNoteSet);
        options.addOption(itemNoteRemove);
        options.addOption(itemSelling);
        options.addOption(itemPriceMine);
        options.addOption(itemPriceMarket);
        options.addOption(itemAmountSet);

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

