
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import org.ini4j.Ini;

import com.obixlabs.commons.io.InteractiveCommandLineReader;
import com.obixlabs.commons.io.FreeTextConsoleInputHandler;
 
import java.io.File;
import java.io.IOException;

import inv.storage.StockList;
import inv.storage.StockItem;
import inv.storage.Selector;

/*
 * TODO:
 * * Add option to move item to sold list
 * * Separate StockItems and SellingItems lists
 */

class AttributeException extends Exception {

    public AttributeException() {
        super();
    }

    public AttributeException(String message) {
        super(message);
    }

}

public class Main {

    private boolean verbose = false;

    private CommandLine params;
    private Ini config;

    private StockList items;
    private Selector types;
    private Selector brands;
    private Selector tags;

    public Main(CommandLine params, Ini config) throws IOException, AttributeException {
        this.config = config;
        this.params = params;
        if (params.hasOption('v'))
            verbose = true;

        readStorages();
    }

    public void readStorages() throws IOException, AttributeException {
        Ini.Section storage = this.config.get("storages");
        String itemsFile = storage.get("items-file");
        if (itemsFile == null)
            throw new AttributeException("Items file path not defined");
        String typesDb = storage.get("types-db");
        if (typesDb == null)
            throw new AttributeException("Types DB file path not defined");
        String brandsDb = storage.get("brands-db");
        if (brandsDb == null)
            throw new AttributeException("Brands DB file path not defined");
        String tagsDb = storage.get("tags-db");
        if (tagsDb == null)
            throw new AttributeException("Tags DB file path not defined");

        this.items = new StockList(itemsFile);
        this.types = new Selector(typesDb);
        this.brands = new Selector(brandsDb);
        this.tags = new Selector(tagsDb);
    }

    public void writeStorages() throws IOException {
        Ini.Section storage = this.config.get("storages");
        String itemsFile = storage.get("items-file");

        this.items.write(itemsFile);
    }

    public Selector getTypeSelector() {
        return this.types;
    }

    public Selector getBrandSelector() {
        return this.brands;
    }

    public Selector getTagSelector() {
        return this.tags;
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

    public static Ini initConfig(String fileName) {
        File configFile = new File(fileName);

        if (!configFile.exists() || !configFile.isFile())
            die(String.format("Config file '%s' does not exist", fileName));

        try {
            return new Ini(configFile);
        } catch (IOException e) {
            die(String.format("Failed to read config file '%s'", fileName));
        }
        return null;
    }

    public static String readCLI(String caption, boolean required) {
        FreeTextConsoleInputHandler hndl = new FreeTextConsoleInputHandler(caption + ": ", "Invalid " + caption + ", repeat: ", !required);
        try {
            InteractiveCommandLineReader.prompt(hndl);
        } catch (IOException e) {
            return null;
        }
        return hndl.getInputValue();
    }

    public static String listCLI(String caption, Selector selector, boolean allowCustom) {
        int i;
        String text;
        FreeTextConsoleInputHandler hndl = new FreeTextConsoleInputHandler(caption + ": ", "Invalid " + caption + ", repeat: ", false);

        while (true) {

            for (i = 0; i < selector.size(); i++) {
                System.out.println(String.format("%d: %s", i+1, selector.get(i)));
            }
            System.out.println(String.format("*: Custom entry"));

            try {
                InteractiveCommandLineReader.prompt(hndl);
            } catch (IOException e) {
                return null;
            }
            text = hndl.getInputValue();

            try {
                int value = Integer.parseInt(text);
                if (selector.size() <= value) {
                    perror(String.format("Invalid index selected %d", value));
                    continue;
                }
                return selector.get(value-1);
            } catch (Exception e) {}

            if (!allowCustom)
                continue;

            selector.add(text);
            return text;
        }
    }

    public static void process(Main ctrl, CommandLine params) throws IOException {
        if (params.hasOption('l')) {
            ctrl.listStockItems();
            perror("Stock items listed succesfully");
        }

        if (params.hasOption('a')) {
            String type = listCLI("Product type", ctrl.getTypeSelector(), true);
            String brand = listCLI("Product brand", ctrl.getBrandSelector(), true);
            String model = readCLI("Product model", true);
            String title = readCLI("Product title", false);
            ctrl.addStockItem(type, model, brand, title);
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

            if (params.hasOption("removeNote")) {
                item.setNote("");
                perror(String.format("Removed item note"));
            }

            if (params.hasOption("setNote")) {
                item.setNote(params.getOptionValue("setNote"));
                perror(String.format("Updated item note"));
            }

            if (params.hasOption("selling")) {
                item.toggleSelling();
                perror(String.format("Updated item selling state"));
            }

            if (params.hasOption("setMarket")) {
                float value = Float.parseFloat(params.getOptionValue("setMarket"));
                item.setMarketPrice(value);
                perror(String.format("Updated item market price"));
            }

            if (params.hasOption("setPrice")) {
                float value = Float.parseFloat(params.getOptionValue("setPrice"));
                item.setMinePrice(value);
                perror(String.format("Updated item selling price"));
            }

            if (params.hasOption("setAmount")) {
                int value = Integer.parseInt(params.getOptionValue("setAmount"));
                item.setAmount(value);
                perror(String.format("Updated item stock amount"));
            }

            if (params.hasOption("addTag")) {
                String value = params.getOptionValue("addTag");
                if (value == null)
                    value = listCLI("Add item tag", ctrl.getTagSelector(), true);
                item.addTag(value);
                perror(String.format("Added tag for item"));
            }

            if (params.hasOption("removeTag")) {
                String value = params.getOptionValue("removeTag");
                if (value == null)
                    value = listCLI("Remove item tag", new Selector(item.getTags()), false);
                item.removeTag(value);
                perror(String.format("Removed tag from item"));
            }

            if (params.hasOption("clearTags")) {
                item.clearTags();
                perror(String.format("Cleared all item tags"));
            }

            if (params.hasOption("setTitle")) {
                String value = params.getOptionValue("setTitle");
                item.setTitle(value);
                perror(String.format("New title for item set"));
            }


            System.out.println(item.toString());
        }
    }

	public static void main(String[] args) {

		Option verbose = OptionBuilder.withLongOpt("verbose")
                                      .withDescription("Set verbose output")
                                      .create('v');
		Option datafile = OptionBuilder.withLongOpt("config")
                                       .withDescription("Set application configuration file")
                                       .hasArg()
                                       .create('c');
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
		Option itemNoteSet = OptionBuilder.withLongOpt("set-note")
                                       .withDescription("Update stock item note")
                                       .hasArg()
                                       .create("setNote");
		Option itemNoteRemove = OptionBuilder.withLongOpt("remove-note")
                                       .withDescription("Remove stock item note")
                                       .create("removeNote");
		Option itemSelling = OptionBuilder.withLongOpt("selling")
                                       .withDescription("Toggle item selling or not")
                                       .create("selling");
		Option itemPriceMine = OptionBuilder.withLongOpt("set-price")
                                       .withDescription("Update item market price")
                                       .hasArg()
                                       .create("setPrice");
		Option itemPriceMarket = OptionBuilder.withLongOpt("set-market")
                                       .withDescription("Update item selling price")
                                       .hasArg()
                                       .create("setMarket");
		Option itemAmountSet = OptionBuilder.withLongOpt("set-amount")
                                       .withDescription("Update item stock amount")
                                       .hasArg()
                                       .create("setAmount");
        Option itemTagAdd = OptionBuilder.withLongOpt("add-tag")
                                       .withDescription("Add new tag for item")
                                       .hasOptionalArg()
                                       .create("addTag");
        Option itemTagRemove = OptionBuilder.withLongOpt("remove-tag")
                                       .withDescription("Remove item tag")
                                       .hasOptionalArg()
                                       .create("removeTag");
        Option itemTagsClear = OptionBuilder.withLongOpt("clear-tags")
                                       .withDescription("Remove all item tags")
                                       .create("clearTags");
        Option itemTitleSet = OptionBuilder.withLongOpt("set-title")
                                       .withDescription("Update item title")
                                       .hasArg()
                                       .create("setTitle");

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
        options.addOption(itemTagAdd);
        options.addOption(itemTagRemove);
        options.addOption(itemTagsClear);
        options.addOption(itemTitleSet);

        CommandLine params = null;
        CommandLineParser parser = new GnuParser();
        try {
            params = parser.parse(options, args);
        } catch (ParseException exp) {
            die("Parsing failed. Reason: " + exp.getMessage());
        }

        String configFile = "default.cfg";
        if (params.hasOption('c'))
            configFile = params.getOptionValue('c');

        Ini config = initConfig(configFile);
        Main ctrl = null;

        try {
            ctrl = new Main(params, config);
        } catch (Exception e) {                 // IOException, AttributeException
            die(e.getMessage());
        }

        try {
            process(ctrl, params);
        } catch (Exception e) {
            die(e.getMessage());
        } finally {
            try {
                ctrl.writeStorages();
            } catch (IOException ex) {
                die(ex.getMessage());
            }
        }
	}
};

