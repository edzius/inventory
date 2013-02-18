
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import org.apache.commons.cli.ParseException;

/*
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
*/

public class Main {
	/*   
	JTable table;
	JButton discoveryButton;
	JButton clearButton;
	JButton resetButton;
	JButton addButton;

	JPopupMenu popupMenu;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu helpMenu;
	JMenuItem exitMenuItem;
	JMenuItem aboutMenuItem;
	JMenuItem noteMenuItem;

	JMenu localeMenu;
	JMenuItem language_en;
	JMenuItem language_br;

	StatusBar statusBar;
	DeviceTableModel model;


	public Main(Properties props) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {}

		createComponents();
		layoutComponents();
	}
      
	public void tableChanged(TableModelEvent e) {
		int cnt = table.getRowCount();
		int row = table.getSelectedRow();

		if (cnt == 0)
			resetButton.setEnabled(false);
		else {
			resetButton.setEnabled(true);
		}
			
		if (row > -1)
			return;

		table.requestFocusInWindow();
		table.clearSelection();
		table.changeSelection(0,0,false,false);
	}

	private void createComponents() {
                model = new DeviceTableModel();
		model.addTableModelListener(this);
//		TableSorter sorter = new TableSorter(model);
//		table = new JTable(sorter);
		table = new JTable(model);
		table.getSelectionModel().addListSelectionListener(this);

		Main.getDiscovery().addListener(model);
		Main.getDiscovery().addListener(this);
		Main.getLegacyDiscovery().addListener(model);
		Main.getLegacyDiscovery().addListener(this);

//		sorter.setTableHeader(table.getTableHeader());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFont(new Font("Monospaced", Font.PLAIN, 12));

		statusBar = new StatusBar();
		setStatus(LanguageFactory.get_text("status-ready"));

		final DiscoveryActionHandler discovery_hndl = new DiscoveryActionHandler(this);
		final ResetActionHandler reset_hndl = new ResetActionHandler(this);
		Main.getDiscovery().addListener(discovery_hndl);
		Main.getDiscovery().addListener(reset_hndl);
		Main.getLegacyDiscovery().addListener(discovery_hndl);
		Main.getLegacyDiscovery().addListener(reset_hndl);

		discoveryButton = new JButton(LanguageFactory.get_text("button-scan"));
		discoveryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread thr = new Thread(discovery_hndl);
				thr.start();
			}
		});

		resetButton = new JButton(LanguageFactory.get_text("button-reset"));
		ActionListener resetAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = 0;				//Stop counter
				Thread thr = new Thread(reset_hndl);
				thr.start();
				setStatus("");
				setHint("");
				setDevice("");
			}
		};
		resetButton.setEnabled(false);
		resetButton.addActionListener(resetAL);

		clearButton = new JButton(LanguageFactory.get_text("button-clear"));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					if (ext_mac != null)
						getDiscovery().send_boot(ext_mac);
					else
						System.out.println("extdev mac not found, cannot speedup boot");
					ext_mac = null;
				}

				index = 0;				//Stop counter
				setStatus("");
				setHint("");
				setDevice("");

				clearDevices();
			}
		});

		addButton = new JButton(LanguageFactory.get_text("button-add"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                                NewDeviceDialog dd = new NewDeviceDialog(self);
				dd.setVisible(true);
			}
		});

		popupMenu = new JPopupMenu();
		JMenuItem item = new JMenuItem(LanguageFactory.get_text("button-launch"));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();				
				if (row == -1)
					return;
				String wiliIp = table.getValueAt(row, 0).toString();
				launchBrowser(wiliIp);
			}
		});

		popupMenu.add(item);

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}

			public void mouseClicked(MouseEvent e) {
				if ( e.getClickCount() == 2 ) {
					int row = table.getSelectedRow();				
					if (row == -1)
						return;
					String wiliIp = table.getValueAt(row, 0).toString();
					launchBrowser(wiliIp);
				}
			}

			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					int row = table.rowAtPoint(e.getPoint());
					table.getSelectionModel().setSelectionInterval(row, row);
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}			
		});
	}

	private void layoutComponents() {
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		menuBar.add(localeMenu);

		fileMenu.add(exitMenuItem);
		helpMenu.add(noteMenuItem);
		helpMenu.add(aboutMenuItem);
		
		setJMenuBar(menuBar);

		//Scan reset menu
		JPanel scanCntrlPanel = new JPanel();
		scanCntrlPanel.setLayout(new BoxLayout(scanCntrlPanel, BoxLayout.LINE_AXIS));

		scanCntrlPanel.add(discoveryButton);
		scanCntrlPanel.add(clearButton);
		scanCntrlPanel.add(Box.createRigidArea(new Dimension(50,0)));
		scanCntrlPanel.add(addButton);
		scanCntrlPanel.add(Box.createHorizontalGlue());
		scanCntrlPanel.add(resetButton);

		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(80);
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(80);
		col = table.getColumnModel().getColumn(2);
		col.setPreferredWidth(500);
		col = table.getColumnModel().getColumn(3);
		col.setPreferredWidth(20);

		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel scanPanel = new JPanel(new BorderLayout());
		scanPanel.add(tablePanel, BorderLayout.CENTER);
		scanPanel.add(scanCntrlPanel, BorderLayout.NORTH);

		//Main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(scanPanel, BorderLayout.CENTER);
		mainPanel.add(statusBar, BorderLayout.SOUTH);

		this.setContentPane(mainPanel);
	}


		Main frame = new Main(props);

		frame.pack();
		frame.setSize(900, 500);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


	*/

    private CommandLine params;
    private boolean verbose = false;
    private ItemsReader items;

    public Main(CommandLine params) throws IOException {
        items = new ItemsReader();

        this.params = params;
        if (params.hasOption('v'))
            verbose = true;

        readItemsFile(this.params.getOptionValue('f'));
    }

    public void readItemsFile(String filename) throws IOException {
        items.read(filename);
    }

    public void writeItemsFile(String filename) {
        ;
    }

    public void listItems() {
        int i;
        int count = items.getCount();

        for (i = 0; i < count; i++)
            System.out.println(items.getItem(i));
    }

    public static void die(String message) {
        System.err.println(message);
        System.exit(1);
    }

	public static void main(String[] args) {

        Main main = null;
        Options options = new Options();

		Option verbose = OptionBuilder.withLongOpt("verbose")
                                      .withDescription("Set verbose output")
                                      .create('v');
		Option datafile = OptionBuilder.withLongOpt("datafile")
                                      .withDescription("Set data file to read data from")
                                      .hasArg()
                                      .isRequired()
                                      .create('f');
		Option list = OptionBuilder.withLongOpt("list")
                                   .withDescription("List exisiting items")
                                   .create('l');

        options.addOption(verbose);
        options.addOption(datafile);
        options.addOption(list);

        CommandLine params = null;
        CommandLineParser parser = new GnuParser();
        try {
            params = parser.parse(options, args);
        } catch (ParseException exp) {
            die("Parsing failed. Reason: " + exp.getMessage());
        }

        Main ctrl = null;
        try {
            ctrl = new Main(params);
        } catch (IOException e) {
            die(e.getMessage());
        }

        if (params.hasOption('l')) {
            ctrl.listItems();
            System.exit(0);
        }
            
	}
};

class ItemParser {

    private static final float DEFAULT_FLOAT = -1.0f;
    private static final int DEFAULT_INT = -1;
    private static final String DEFAULT_STRING= "";

    protected int nextInt(StringTokenizer scanner) {
        String value = nextString(scanner);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    protected float nextFloat(StringTokenizer scanner) {
        String value = nextString(scanner);
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return -1.0f;
        }
    }

    protected boolean nextBoolean(StringTokenizer scanner) {
        String value = nextString(scanner);
        if (value.equals("y"))
            return true;
        return false;
    }


    protected String nextString(StringTokenizer scanner) {
        if (!scanner.hasMoreTokens())
            return DEFAULT_STRING;

        String value = scanner.nextToken().trim();
        return value;
    }   

};

class SoldItem extends ItemParser {

};

class StockItem extends ItemParser {

    private int index;
    private String model;
    private String title;
    private int haveAmount;
    private int soldAmount;
    private float originalPrice;
    private float minePrice;
    private float soldPrice;
    private boolean selling;
    private String tags;
    private String note;

    public StockItem(String line) {
        StringTokenizer scanner = new StringTokenizer(line, "|");

        this.index = nextInt(scanner);
        this.model = nextString(scanner);
        this.title = nextString(scanner);
        this.haveAmount = nextInt(scanner);
        this.soldAmount = nextInt(scanner);
        this.originalPrice = nextFloat(scanner);
        this.minePrice = nextFloat(scanner);
        this.soldPrice = nextFloat(scanner);
        this.selling = nextBoolean(scanner);
        this.tags = nextString(scanner);
        this.note = nextString(scanner);
    }

    public String toString() {
        String fmt = "| %-20s | %-30s | %4s | %4s | %7s | %7s | %7s | %c | %-30s | %-30s |";
        String value = String.format(fmt, 
                this.model, 
                this.title,
                this.haveAmount, 
                this.soldAmount < 0 ? "-" : this.soldAmount,
                this.originalPrice < 0 ? "-" : this.originalPrice, 
                this.minePrice < 0 ? "-" : this.minePrice,
                this.soldPrice < 0 ? "-" : this.soldPrice,
                this.selling ? 'X' : ' ',
                this.tags, 
                this.note);

        return value;
    }

}

class ItemsReader {

    private ArrayList<Item> data;
    
    public ItemsReader() {
        data = new ArrayList<Item>();
    }

    public void read(String filename) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filename));

        while ((line = br.readLine()) != null) {
            data.add(new StockItem(line));
        }

        br.close();
    }

    public int getCount() {
        return data.size();
    }

    public Item getItem(int index) {
        return data.get(index);
    }

}
