
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class InventoryParser {
    
    private static Options makeOptions() {
        // Inventory tool options
		Option verbose = OptionBuilder.withLongOpt("verbose")
                                      .withDescription("Set verbose output")
                                      .create('v');
		Option datafile = OptionBuilder.withLongOpt("config")
                                       .withDescription("Set application configuration file")
                                       .hasArg()
                                       .create('c');

        // Stock items list control options
		Option stockList = OptionBuilder.withLongOpt("list")
                                        .withDescription("List exisiting stock items")
                                        .hasOptionalArg()
                                        .create('l');
		Option stockFind = OptionBuilder.withLongOpt("find")
                                        .withDescription("Find item in stock")
                                        .hasArg()
                                        .create('f');
		Option stockAdd = OptionBuilder.withLongOpt("add")
                                       .withDescription("Add new item to stock")
                                       .create('a');
		Option stockSelect = OptionBuilder.withLongOpt("select")
                                          .withDescription("Select an item from stock")
                                          .hasArg()
                                          .create('s');
		Option stockRemove = OptionBuilder.withLongOpt("delete")
                                          .withDescription("Delete an item from stock")
                                          .hasArg()
                                          .create('d');

        // Stock item record control options
		Option itemNoteSet = OptionBuilder.withLongOpt("set-note")
                                       .withDescription("Update stock item note")
                                       .hasArg()
                                       .create("setNote");
		Option itemNoteRemove = OptionBuilder.withLongOpt("remove-note")
                                       .withDescription("Remove stock item note")
                                       .create("removeNote");
		Option itemBuyPriceSet = OptionBuilder.withLongOpt("set-buy-price")
                                       .withDescription("Update item bought cost")
                                       .hasArg()
                                       .create("setBuyPrice");
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

        // Selling item control options
		Option saleStart = OptionBuilder.withLongOpt("start-selling")
                                       .withDescription("Start selling selected item")
                                       .hasArg()
                                       .create("startSelling");
		Option saleStop = OptionBuilder.withLongOpt("stop-selling")
                                       .withDescription("Stop selling selectem item")
                                       .create("stopSelling");
		Option itemMarketPriceSet = OptionBuilder.withLongOpt("set-market-price")
                                       .withDescription("Update item market price")
                                       .hasArg()
                                       .create("setMarketPrice");
		Option itemSellPriceSet = OptionBuilder.withLongOpt("set-sell-price")
                                       .withDescription("Update item sell price")
                                       .hasArg()
                                       .create("setSellPrice");

        // Sold item control options
        Option markSold = OptionBuilder.withLongOpt("set-sold")
                                       .withDescription("set selected item as sold")
                                       .hasArgs(2)
                                       .create("sold");


        Options options = new Options();

        options.addOption(verbose);
        options.addOption(datafile);

        options.addOption(stockList);
        options.addOption(stockFind);
        options.addOption(stockAdd);
        options.addOption(stockSelect);
        options.addOption(stockRemove);

        options.addOption(itemNoteSet);
        options.addOption(itemNoteRemove);
        options.addOption(itemBuyPriceSet);
        options.addOption(itemAmountSet);
        options.addOption(itemTagAdd);
        options.addOption(itemTagRemove);
        options.addOption(itemTagsClear);
        options.addOption(itemTitleSet);

        options.addOption(saleStart);
        options.addOption(saleStop);
        options.addOption(itemMarketPriceSet);
        options.addOption(itemSellPriceSet);

        options.addOption(markSold);

        return options;
    }

    public static CommandLine parse(String[] args) {
        Options options = makeOptions();
        CommandLine params = null;
        CommandLineParser parser = new GnuParser();
        try {
            params = parser.parse(options, args);
        } catch (ParseException e) {
            Utils.die("Parsing failed. Reason: " + e.getMessage());
        }

        return params;
    }
}

