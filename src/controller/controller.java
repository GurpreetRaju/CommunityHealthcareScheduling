package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import Model.BundleNode;
import Model.XLSXReader;
import Model.nurse;
import Model.scheduling;
import view.view;

public class controller {
	private view myview;
	private ActionListener runAlgoListener;
	private ActionListener readDataListener;
	private XLSXReader readfile = null;
	private scheduling algo = new scheduling();
	
	public controller(){
		myview = new view();
		
		runAlgoListener= new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myview.clearOptimalPanel();
				algo.schedule(readfile.getData(), readfile.getRequestPrices(), readfile.getSum());
				BundleNode[] optimalSol = algo.getSolution();
				for(BundleNode b : optimalSol){
					myview.appendToOptimal("Nurse "+b.getNurse() + " Requests " + b.returnReqs() + " Cost " + b.getPrice());
				}
				myview.appendToOptimal(Double.toString(algo.getOptimalSum()));
				myview.updateOptimalPanel();
			}
		};
		this.myview.runAlgoAction(runAlgoListener);
		readDataListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String file = myview.getFileAddress();
				//System.out.println("Reading Data from "+file);
				readfile = new XLSXReader(file);
				LinkedList<nurse> sheet2 = readfile.getData();
				LinkedList<Double> sheet1 = readfile.getRequestPrices();
				ArrayList<String[]> temp = new ArrayList<String[]>();
				myview.clearBundlePanel();
				for(nurse n: sheet2){
					for(BundleNode b : n.getBundles()){
						String[] tempBundle = {Integer.toString(n.getID()),b.returnReqs(),Double.toString(b.getPrice())};
						temp.add(tempBundle);
					}
				}
				myview.setTable1(toMultiDArray(temp));
				temp.clear();
				for(int i=0; i< sheet1.size();i++){
					String[] tempBundle = {Integer.toString(i), Double.toString(sheet1.get(i))};
					temp.add(tempBundle);
				}
				myview.setTable2(toMultiDArray(temp));
				temp.clear();
			}			
		};
		this.myview.readDataAction(readDataListener);
		this.myview.setVisible(true);
	}
	
	private String[][] toMultiDArray(ArrayList<String[]> newList){
		int x = newList.size();
		String[][] finalstring = new String[x][];
		for(int i=0; i<x; i++){
			finalstring[i] = newList.get(i);
		}
		return finalstring;
	}
	
//	public Object[][] convertToTable(){
//		Object[][] obj = null;
//		
//		String[][] sh2 = sheet2.toArray(new String[sheet2.size()][]);
//		String[][] sh1 = sheet1.toArray(new String[sheet1.size()][]);
//		ArrayList<String> tmp = new ArrayList<String>();
//		int j=1;
//		for(int i=0; i<sh2[0].length; i++){
//			if(i==0){
//				tmp.add("No.");
//			}
//			else if((i%2)!=0){
//				tmp.add("Nurse "+j+" bundle");
//			}
//			else{
//				tmp.add("Nurse "+j+" cost");
//				j++;
//			}
//		}
//		
//		return obj;
//	}
	
	public static void main(String[] arg){
		controller mycontrol = new controller();
	}
}
