package Model;

import java.io.IOException;
import Model.BundleNode;
import java.util.ArrayList;
import java.util.LinkedList;

public class scheduling {
		
	public BundleNode[] schedule(LinkedList<BundleNode> allBundles, LinkedList<Double> reqPrices, Double sum){
		
		ArrayList<BundleNode[]> possibleSol = new ArrayList<BundleNode[]>();
		for(BundleNode bundle : allBundles){
			ArrayList<BundleNode> tempSol = new ArrayList<BundleNode>();
			ArrayList<Integer> nurse = new ArrayList<Integer>();
			ArrayList<String> requests = new ArrayList<String>();
			//System.out.println("add node to tempSol");
			//bundle.display();
			tempSol.add(bundle);
			requests = addRequestsToList(bundle.getRequests(), requests);
//			for(String s : requests){
//				System.out.println(s);
//			}
			nurse.add(bundle.getNurse());
			for(BundleNode tempBundle : allBundles){
				if(!(tempSol.contains(tempBundle)) && !(nurse.contains(tempBundle.getNurse())) && checkReqExistInList(tempBundle.getRequests(),requests)){
					tempSol.add(tempBundle);
					requests = addRequestsToList(tempBundle.getRequests(), requests);
					nurse.add(tempBundle.getNurse());
					//System.out.println(" add node to tempSol");
					//tempBundle.display();
				}
//				else{
//					if(nurse.contains(tempBundle.getNurse())){
//						for(int i : nurse){
//							System.out.print(" "+i);
//						}
//						System.out.println("Bundle already traversed for nurse " + tempBundle.getNurse());
//					}
//					else if(!checkReqExistInList(tempBundle.getRequests(),requests)){
//						System.out.println("Request already exist for ");
//						tempBundle.display();
//					}
//				}
			}
			for(int i=1; i<=reqPrices.size(); i++){
				if(!requests.contains(Integer.toString(i))){
					requests.add(Integer.toString(i));
					String[] request = new String[1];
					request[0] = Integer.toString(i);
					BundleNode newBundle = new BundleNode(request, reqPrices.get(i-1));
					//System.out.println("add node to tempSol");
					//newBundle.display();
					tempSol.add(newBundle);
				}
			}
			possibleSol.add(tempSol.toArray(new BundleNode[tempSol.size()]));
		}
		Double oSum = sum;
		BundleNode[] oSol = null;
		for(BundleNode[] tempsol : possibleSol){
			Double tempsum = 0.0;
			for(BundleNode bundle : tempsol){
				tempsum = tempsum + bundle.getPrice();
			}
			if(tempsum<oSum){
				oSum = tempsum;
				oSol = tempsol;
			}
		}
		
		System.out.println("Optimal Solution "+oSum);
		return oSol;
	}
	
	private ArrayList<String> addRequestsToList(String[] bundleReq, ArrayList<String> ReqList){
		for(String req : bundleReq){
			ReqList.add(req);
		}
		return ReqList;
	}
	private boolean checkReqExistInList(String[] newBundleReq, ArrayList<String> ReqList){
		
		for(String req : newBundleReq){
			if(ReqList.contains(req)){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] arg) throws IOException{
		
		XLSXReader reader = new XLSXReader();
		reader.getFirstsheetData();
		reader.getSecondsheetData();
		
		scheduling sch = new scheduling();
		BundleNode[] optimalSol = sch.schedule(reader.getBundles(), reader.getRequestPrices(), reader.getSum());
		
		for(BundleNode b : optimalSol){
			System.out.print( "Nurse " + b.getNurse() + " Price " +b.getPrice() + " Requests ");
			b.displayReqs();
		}
	}
}
