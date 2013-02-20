
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
    private StockList items;

    public Main(CommandLine params) {
        items = new StockList();

        this.params = params;
        if (params.hasOption('v'))
            verbose = true;
    }

    public void readItemsFile(String filename) throws IOException {
        items.read(filename);
    }

    public void writeItemsFile(String filename) {
        ;
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
            ok("Stock item added succesfully");
        }
	}
};

