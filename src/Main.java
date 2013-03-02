
import org.ini4j.Ini;
import org.apache.commons.cli.CommandLine;
 
import java.io.File;
import java.io.IOException;

import storage.StockList;
import storage.StockItem;
import cli.Selector;
import cli.CliTools;


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

    public StockItem[] listStockItems() {
        if (items.getCount() == 0)
            return null;

        return items.toArray();
    }

    public StockItem[] findStockItems(String value) {
        StockItem item;
        int count = 0;
        for (int i = 0; i < items.getCount(); i++) {
            item = items.getElement(i);
            if (!item.match(value))
                continue;

            count += 1;
        }

        if (count == 0)
            return null;

        StockItem[] filtered = new StockItem[count];
        int current = 0;
        for (int i = 0; i < items.getCount(); i++) {
            item = items.getElement(i);
            if (!item.match(value))
                continue;

            filtered[current] = item;
            current += 1;
        }
        return filtered;
    }

    public StockItem getStockItem(int index) {
        return items.getItem(index);
    }

    public StockItem addStockItem(String type, String model, String manufacturer, String title) {
        StockItem item = new StockItem(type, manufacturer, model, title);
        items.addItem(item);
        return item;
    }

    public StockItem deleteStockItem(int index) throws AttributeException {
        if (!hasStockItem(index))
            throw new AttributeException(String.format("Stock item %d not found", index));

        StockItem item = getStockItem(index);
        items.deleteItem(index);
        return item;
    }

    public boolean hasStockItem(int index) {
        return items.hasItem(index);
    }

    public static void perror(String message) {
        System.err.println(message);
    }

    public static Ini initConfig(String fileName) {
        File configFile = new File(fileName);

        if (!configFile.exists() || !configFile.isFile())
            Utils.die(String.format("Config file '%s' does not exist", fileName));

        try {
            return new Ini(configFile);
        } catch (IOException e) {
            Utils.die(String.format("Failed to read config file '%s'", fileName));
        }
        return null;
    }

    public static void printStockItems(StockItem[] items) {
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i].toString());
        }
    }

    public static void process(Main ctrl, CommandLine params) throws IOException {
        if (params.hasOption('l')) {
            StockItem[] items = ctrl.listStockItems();
            if (items == null) {
                perror("No items to display");
                return;
            }

            printStockItems(items);
            return;
        }

        if (params.hasOption('f')) {                    // Find item
            String value = params.getOptionValue('f');

            StockItem[] items = ctrl.findStockItems(value);
            if (items == null) {
                perror(String.format("Can't find items matching '%s'", value));
                return;
            }
            
            printStockItems(items);
            return;
        }

        if (params.hasOption('a')) {                    // Add item
            String type = CliTools.listCLI("Product type", ctrl.getTypeSelector(), true);
            String brand = CliTools.listCLI("Product brand", ctrl.getBrandSelector(), true);
            String model = CliTools.readCLI("Product model", true);
            String title = CliTools.readCLI("Product title", false);
            StockItem item = ctrl.addStockItem(type, model, brand, title);
            perror(String.format("Added item -- %s", item.summaryString()));
            return;
        }

        if (params.hasOption('d')) {                    // Delete item
            int index = Integer.parseInt(params.getOptionValue('d'));
            StockItem item = null;
            try {
                item = ctrl.deleteStockItem(index);
            } catch (AttributeException e) {
                Utils.die(e.getMessage());
            }
            perror(String.format("Deleted item -- %s", item.summaryString()));
            return;
        }

        if (params.hasOption('s')) {                    // Select item
            int index = Integer.parseInt(params.getOptionValue('s'));
            StockItem item;

            if (!ctrl.hasStockItem(index))
                Utils.die(String.format("Stock item %d not found", index));

            item = ctrl.getStockItem(index);

            if (params.hasOption("removeNote")) {
                item.setNote("");
                perror(String.format("Removed item note"));
            }

            if (params.hasOption("setNote")) {
                item.setNote(params.getOptionValue("setNote"));
                perror(String.format("Updated item note"));
            }

//            if (params.hasOption("selling")) {
//                item.toggleSelling();
//                perror(String.format("Updated item selling state"));
//            }
//
//            if (params.hasOption("setMarket")) {
//                float value = Float.parseFloat(params.getOptionValue("setMarket"));
//                item.setMarketPrice(value);
//                perror(String.format("Updated item market price"));
//            }
//
//            if (params.hasOption("setPrice")) {
//                float value = Float.parseFloat(params.getOptionValue("setPrice"));
//                item.setMinePrice(value);
//                perror(String.format("Updated item selling price"));
//            }

            if (params.hasOption("setAmount")) {
                int value = Integer.parseInt(params.getOptionValue("setAmount"));
                item.setAmount(value);
                perror(String.format("Updated item stock amount"));
            }

            if (params.hasOption("addTag")) {
                String value = params.getOptionValue("addTag");
                if (value == null)
                    value = CliTools.listCLI("Add item tag", ctrl.getTagSelector(), true);
                item.addTag(value);
                perror(String.format("Added tag for item"));
            }

            if (params.hasOption("removeTag")) {
                String value = params.getOptionValue("removeTag");
                if (value == null)
                    value = CliTools.listCLI("Remove item tag", new Selector(item.getTags()), false);
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
        CommandLine params = InventoryParser.parse(args);

        String configFile = "default.cfg";
        if (params.hasOption('c'))
            configFile = params.getOptionValue('c');

        Ini config = initConfig(configFile);
        Main ctrl = null;

        try {
            ctrl = new Main(params, config);
        } catch (Exception e) {                 // IOException, AttributeException
            Utils.die(e.getMessage());
        }

        try {
            process(ctrl, params);
        } catch (Exception e) {
            Utils.die(e.getMessage());
        } finally {
            try {
                ctrl.writeStorages();
            } catch (IOException ex) {
                Utils.die(ex.getMessage());
            }
        }
	}
};

