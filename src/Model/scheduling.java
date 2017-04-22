package Model;

import java.io.IOException;
import Model.BundleNode;
import java.util.ArrayList;
import java.util.LinkedList;

public class scheduling{
		
	private Double optimalSum;

	public BundleNode[] schedule(LinkedList<nurse> nurses, LinkedList<Double> reqPrices, Double sum){
		
		ArrayList<BundleNode[]> possibleSol = new ArrayList<BundleNode[]>();
		System.out.println(nurses.size());
		for(nurse nurs : nurses){
			for(BundleNode bundle : nurs.getBundles()){
				ArrayList<BundleNode> tempSol = new ArrayList<BundleNode>();
				ArrayList<nurse> nurse = new ArrayList<nurse>();
				ArrayList<String> requests = new ArrayList<String>();
				tempSol.add(bundle);
				requests = addRequestsToList(bundle.getRequests(), requests);
				nurse.add(nurs);
				System.out.println("nurse added to nurseArray "+nurs.getID());
				for(nurse nurc : nurses){
					if(!nurse.contains(nurc)){
						BundleNode tempBundle1 = new BundleNode();
						int i=0;
						for(BundleNode tempBundle : nurc.getBundles()){
							if(!(tempSol.contains(tempBundle)) && checkReqExistInList(tempBundle.getRequests(),requests)){
								if(i==0){
									tempBundle1 = tempBundle;
									i++;
									continue;
								}
								else if(tempBundle1.getPrice()>tempBundle.getPrice()){	
									tempBundle1 = tempBundle;
								}
							}
						}
						if(i!=0){
							requests = addRequestsToList(tempBundle1.getRequests(), requests);
							System.out.println("nurse added to nurseArray"+nurc.getID());
							tempSol.add(tempBundle1);
							nurse.add(nurc);
						}
					}
				}
				for(int i=1; i<=reqPrices.size(); i++){
					if(!requests.contains(Integer.toString(i))){
						requests.add(Integer.toString(i));
						String[] request = new String[1];
						request[0] = Integer.toString(i);
						BundleNode newBundle = new BundleNode(request, reqPrices.get(i-1));
						tempSol.add(newBundle);
					}
				}
				possibleSol.add(tempSol.toArray(new BundleNode[tempSol.size()]));
			}
		}
				
		Double oSum = sum;
		BundleNode[] oSol = null;
		for(BundleNode[] tempsol : possibleSol){
			Double tempsum = 0.0;
			for(BundleNode bundle : tempsol){
				//System.out.print("Bundle Nurse: "+ bundle.getNurse() +" Price: " + String.valueOf(bundle.getPrice()) + " Requests: ");
				bundle.displayReqs();
				tempsum = tempsum + bundle.getPrice();
			}
			//System.out.print(tempsum);
			if(tempsum<oSum){
				oSum = tempsum;
				oSol = tempsol;
			}
			//System.out.println("");
		}
		
		//System.out.println("Optimal Solution "+oSum);
		this.optimalSum = oSum;
		
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
	
	public Double getOptimalSum(){
		return this.optimalSum;
	}
	
	public static void main(String[] arg) throws IOException{
		
		XLSXReader reader = new XLSXReader();
		
		scheduling sch = new scheduling();
		BundleNode[] optimalSol = sch.schedule(reader.getData(), reader.getRequestPrices(), reader.getSum());
		
		for(BundleNode b : optimalSol){
			System.out.print( "Nurse " + b.getNurse() + " Price " +b.getPrice() + " Requests ");
			b.displayReqs();
		}
	}
}
