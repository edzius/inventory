
package storage;

public class CombinedItem implements ItemInterface {

    private StockItem item;
    private SellingItem sale;
    private SoldItem[] sold;
    private String[] attributes;

    // XXX: Accepts nulls
    public CombinedItem(StockItem item, SellingItem sale, SoldItem[] sold) {
        this.item = item;
        this.sale = sale;
        this.sold = sold;
        this.attributes = null;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    private boolean isSelling() {
        return this.sale != null;
    }

    private boolean isSold() {
        return this.sold != null;
    }

    private int getStockAmount() {
        int soldAmount = getSoldAmount();
        if (soldAmount <= 0)
            return item.getAmount();

        return item.getAmount() - soldAmount;
    }

    private int getSoldAmount() {
        if (!isSelling() && !isSold())
            return -1;

        if (!isSold())
            return 0;

        int count = 0;
        for (int i = 0; i < this.sold.length; i++)
            count += this.sold[i].getSoldAmount();

        return count;
    }

    private float getBuyCost() {
        float price = item.getBuyPrice();
        int amount = item.getAmount();

        if (price < 0 || amount < 0)
            return 0;

        return price * amount;
    }

    private float getSoldIncome() {
        if (!isSelling() && !isSold())
            return -1;

        if (!isSold())
            return 0;

        float price = 0;
        for (int i = 0; i < this.sold.length; i++)
            price += this.sold[i].getSoldAmount() * this.sold[i].getSoldPrice();

        return price;
    }

    private float getItemEarn() {
        float income = getSoldIncome();
        if (income < 0)
            return -1;

        return income - getBuyCost();
    }

    private float getSoldPrice() {
        int amount = getSoldAmount();
        float earn = getSoldIncome();

        // XXX: if one of them comply -- data consistentcy error.
        // Need to do addiotional handling
        if (amount < 0 || earn < 0)
            return -1;

        return earn / amount;
    }

    public String header() {
        String line = "|";
        for (int i = 0; i < attributes.length; i++) {
            String attr = attributes[i];
            if (attr.equals("id")) {
                line += String.format(" %4s ", "ID");
            } else if (attr.equals("type")) {
                line += String.format(" %-15s ", "Type");
            } else if (attr.equals("brand")) {
                line += String.format(" %-15s ", "Brand");
            } else if (attr.equals("model")) {
                line += String.format(" %-20s ", "Model");
            } else if (attr.equals("title")) {
                line += String.format(" %-30s ", "Title");
            } else if (attr.equals("tags")) {
                line += String.format(" %-30s ", "Tags");
            } else if (attr.equals("note")) {
                line += String.format(" %-30s ", "Node");
            } else if (attr.equals("selling")) {
                line += String.format(" %c ", 'S');
            } else if (attr.equals("had")) {
                line += String.format(" %-4s ", "Had");
            } else if (attr.equals("have")) {
                line += String.format(" %-4s ", "Have");
            } else if (attr.equals("sold")) {
                line += String.format(" %-4s ", "Sold");
            } else if (attr.equals("$bought")) {
                line += String.format(" %-7s ", "$Bought");
            } else if (attr.equals("$ask")) {
                line += String.format(" %-7s ", "$Asking");
            } else if (attr.equals("$market")) {
                line += String.format(" %-7s ", "$Market");
            } else if (attr.equals("$sold")) {
                line += String.format(" %-7s ", "$Sold");

            } else if (attr.equals("$cost")) {
                line += String.format(" %-7s ", "$Cost");
            } else if (attr.equals("$income")) {
                line += String.format(" %-7s ", "$Income");
            } else if (attr.equals("$earn")) {
                line += String.format(" %-7s ", "$Earn");

            } else {
                continue;
            }
            line += "|";
        }
        return line;
    }

    public String fileString() {
        return "";
    }

    public String toString() {
        String line = "|";
        for (int i = 0; i < attributes.length; i++) {
            String attr = attributes[i];
            if (attr.equals("id")) {
                line += String.format(" %4s ", item.getIndex());
            } else if (attr.equals("type")) {
                line += String.format(" %-15s ", item.getType());
            } else if (attr.equals("brand")) {
                line += String.format(" %-15s ", item.getBrand());
            } else if (attr.equals("model")) {
                line += String.format(" %-20s ", item.getModel());
            } else if (attr.equals("title")) {
                line += String.format(" %-30s ", item.getTitle());
            } else if (attr.equals("tags")) {
                line += String.format(" %-30s ", item.getTagsString());
            } else if (attr.equals("note")) {
                line += String.format(" %-30s ", item.getNote());
            } else if (attr.equals("selling")) {
                char selling = isSelling() ? 'X' : ' ';
                line += String.format(" %c ", selling);
            } else if (attr.equals("had")) {
                String hadAmount = item.getAmount() < 0 ? "-" : Integer.toString(item.getAmount());
                line += String.format(" %-4s ", hadAmount);
            } else if (attr.equals("have")) {
                String haveAmount = getStockAmount() < 0 ? "-" : Integer.toString(getStockAmount());
                line += String.format(" %-4s ", haveAmount);
            } else if (attr.equals("sold")) {
                String soldAmount = getSoldAmount() < 0 ? "-" : Integer.toString(getSoldAmount());
                line += String.format(" %-4s ", soldAmount);
            } else if (attr.equals("$bought")) {
                String buyPrice = item.getBuyPrice() < 0 ? "-" : Float.toString(item.getBuyPrice());
                line += String.format(" %-7s ", buyPrice);
            } else if (attr.equals("$ask")) {
                String askPrice = "-";
                if (isSelling() && sale.getSellPrice() >= 0)
                    askPrice = Float.toString(sale.getSellPrice());
                line += String.format(" %-7s ", askPrice);
            } else if (attr.equals("$market")) {
                String marketPrice = "-";
                if (isSelling() && sale.getMarketPrice() >= 0)
                    marketPrice = Float.toString(sale.getMarketPrice());
                line += String.format(" %-7s ", marketPrice);
            } else if (attr.equals("$sold")) {
                String soldPrice = getSoldPrice() < 0 ? "-" : Float.toString(getSoldPrice());
                line += String.format(" %-7s ", soldPrice);

            } else if (attr.equals("$cost")) {
                String buyCost = getBuyCost() < 0 ? "-" : Float.toString(getBuyCost());
                line += String.format(" %-7s ", buyCost);
            } else if (attr.equals("$income")) {
                String soldIncome = getSoldIncome() < 0 ? "-" : Float.toString(getSoldIncome());
                line += String.format(" %-7s ", soldIncome);
            } else if (attr.equals("$earn")) {
                String itemEarn = getItemEarn() < 0 ? "-" : Float.toString(getItemEarn());
                line += String.format(" %-7s ", itemEarn);
            } else {
                continue;
            }
            line += "|";
        }
        return line;
    }
}
