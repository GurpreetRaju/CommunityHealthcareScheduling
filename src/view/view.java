package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

public class view extends JFrame{
	private JButton readData;
	private JButton runAlgo;
	private JTextField fileAddress;
	private JButton browse;
	private JScrollPane bundles;
	private JPanel bundleInner;
	private JScrollPane requests;
	private JPanel requestInner;
	private JPanel optimalSol;
	private JTable table1;
	private JTable table2;
	
	public view(){
		init();
	}
	
	private void init(){
		
		//Buttons
		readData = new JButton("Read Data");
		runAlgo = new JButton("Run algorithm");
	    browse = new JButton("Browse");
	    
		//TextFields
		fileAddress = new JTextField(15);
		
		//JPanels
		bundleInner = new JPanel();
		bundleInner.setLayout(new BoxLayout(bundleInner, BoxLayout.PAGE_AXIS));
		bundleInner.setAlignmentY(Component.TOP_ALIGNMENT);
		requestInner = new JPanel();
		requestInner.setLayout(new BoxLayout(requestInner, BoxLayout.PAGE_AXIS));
		requestInner.setAlignmentY(Component.TOP_ALIGNMENT);
		optimalSol = new JPanel();
		optimalSol.setLayout(new BoxLayout(optimalSol, BoxLayout.PAGE_AXIS));
		optimalSol.setAlignmentY(Component.TOP_ALIGNMENT);
		
		//JScrollPanes
		bundles = new JScrollPane(bundleInner);
		bundles.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		bundles.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		bundles.setPreferredSize(new Dimension(800,500));
		requests = new JScrollPane(requestInner);
		requests.setPreferredSize(new Dimension(500,300));
		requests.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		requests.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				
		//Layout setting
		SpringLayout layout = new SpringLayout();
		this.setLayout(layout);
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		//horizonatl constarints
		layout.putConstraint(SpringLayout.WEST, fileAddress, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, browse, 5, SpringLayout.EAST, fileAddress);
		layout.putConstraint(SpringLayout.WEST, readData, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, runAlgo, 5, SpringLayout.EAST, readData);
		layout.putConstraint(SpringLayout.WEST, bundles, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, requests, 5, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.WEST, optimalSol, 20, SpringLayout.EAST, bundles);
		
		//vertical consraint
		layout.putConstraint(SpringLayout.NORTH, fileAddress, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, browse, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, readData, 15, SpringLayout.SOUTH, fileAddress);
		layout.putConstraint(SpringLayout.NORTH, runAlgo, 0, SpringLayout.NORTH, readData);
		layout.putConstraint(SpringLayout.NORTH, bundles, 15, SpringLayout.SOUTH, readData);
		layout.putConstraint(SpringLayout.NORTH, requests, 15, SpringLayout.SOUTH, bundles);
		layout.putConstraint(SpringLayout.NORTH, optimalSol, 15, SpringLayout.NORTH, this);
		
		//add components to frame
		this.add(fileAddress);
		this.add(browse);
		this.add(readData);
		this.add(runAlgo);
		this.add(bundles);
		this.add(requests);
		this.add(optimalSol);
		
		
		browse.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		JFileChooser chooser = new JFileChooser();
	    		FileNameExtensionFilter type = new FileNameExtensionFilter("XLSX file", "xlsx");
	    		chooser.setFileFilter(type);
	    		int returnVal = chooser.showOpenDialog(null);
	    		if(returnVal == JFileChooser.APPROVE_OPTION) {
	    			fileAddress.setText(chooser.getSelectedFile().getAbsolutePath());
	    		}
	    	}
		});
		this.validate();
	}
	
	public void runAlgoAction(ActionListener newAction){
		runAlgo.addActionListener(newAction);
	}
	
	public void readDataAction(ActionListener newAction){
		readData.addActionListener(newAction);
	}
	
	public String getFileAddress(){
		return this.fileAddress.getText();
	}
	
	public void appendToBundles(String data){
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tempPanel.add(new JLabel(data));
		bundleInner.add(tempPanel);
	}
	public void updateBundlePanel(){
		bundleInner.revalidate();
		bundleInner.repaint();
		bundleInner.updateUI();
	}
	public void appendToRequests(String data){
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tempPanel.add(new JLabel(data));
		requestInner.add(new JLabel(data));
	}
	public void updateRequestPanel(){
		requestInner.revalidate();
		requestInner.repaint();
		requestInner.updateUI();
	}
	
	public void appendToOptimal(String data){
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tempPanel.add(new JLabel(data));
		optimalSol.add(new JLabel(data));
	}
	public void updateOptimalPanel(){
		optimalSol.revalidate();
		optimalSol.repaint();
		optimalSol.updateUI();
	}

	public void setTable1(String[][] sheetTwo, String[] strings) {
		table1 = new JTable(sheetTwo , strings);
	}
	public void setTable2(String[][] sheetTwo) {
		String[] title = {"R. ID", "Cost"};
		table2 = new JTable(sheetTwo,title);		
	}
	
}
