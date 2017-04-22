package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import Model.BundleNode;
import Model.XLSXReader;
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
				// TODO Auto-generated method stub
					BundleNode[] optimalSol = algo.schedule(readfile.getBundles(), readfile.getRequestPrices(), readfile.getSum());
					for(BundleNode b : optimalSol){
						System.out.println("Checkpoint 1");
						myview.appendToOptimal("Nurse "+b.getNurse() + " Requests " + b.returnReqs() + " Cost " + b.getPrice());
					}
					myview.updateOptimalPanel();
				
			}
		};
		this.myview.runAlgoAction(runAlgoListener);
		readDataListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String file = myview.getFileAddress();
				System.out.println("Reading Data from "+file);
				readfile = new XLSXReader(file);
				LinkedList<BundleNode> sheet2 = readfile.getBundles();
				LinkedList<Double> sheet1 = readfile.getRequestPrices();
				for(BundleNode b: sheet2){
					myview.appendToBundles("Nurse "+b.getNurse() + " Requests " + b.returnReqs() + " Cost " + b.getPrice());
				}
				myview.updateBundlePanel();
				for(int i=0; i< sheet1.size();i++){
					myview.appendToRequests("Request"+ i +" Cost "+ Double.toString(sheet1.get(i)));
				}
				myview.updateRequestPanel();
			}			
		};
		this.myview.readDataAction(readDataListener);
		this.myview.setVisible(true);
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
