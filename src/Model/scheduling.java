package Model;

import java.io.IOException;
import Model.BundleNode;
import java.util.ArrayList;
import java.util.LinkedList;

public class scheduling{
		
	private Double optimalSum;
	private ArrayList<BundleNode> possibleSol = null;
	//private BundleNode[] oSol = null;

	public void schedule(LinkedList<nurse> nurses, LinkedList<Double> reqPrices, Double sum){
		optimalSum = sum;
		
		System.out.println(nurses.size());
		for(nurse nurs : nurses){
		//for(int x=0; x<1; x++){
			//nurse nurs = nurses.get(x);
			for(BundleNode bundle : nurs.getBundles()){
				Double osum = 0.0;
				ArrayList<BundleNode> tempSol = new ArrayList<BundleNode>();
				ArrayList<nurse> nurse = new ArrayList<nurse>();
				ArrayList<String> requests = new ArrayList<String>();
				tempSol.add(bundle);
				osum += bundle.getPrice();
				requests = addRequestsToList(bundle.getRequests(), requests);
				nurse.add(nurs);
				System.out.println("Solution nurse "+nurs.getID()+" Request "+bundle.returnReqs()+" Cost "+bundle.getPrice());
				for(nurse nurc : nurses){
					if(!nurse.contains(nurc)){
						BundleNode tempBundle1 = nurc.getBestCompatibleBundle(requests);
						if(tempBundle1!=null){
							requests = addRequestsToList(tempBundle1.getRequests(), requests);
							System.out.println("nurse "+nurc.getID()+" Request "+tempBundle1.returnReqs()+" Cost "+tempBundle1.getPrice());
							tempSol.add(tempBundle1);
							osum += tempBundle1.getPrice();
							
							nurse.add(nurc);
						}
					}
				}
				for(int i=1; i<=reqPrices.size(); i++){
					if(!requests.contains(Integer.toString(i))){
						requests.add(Integer.toString(i));
						String[] request = {Integer.toString(i)};
						BundleNode newBundle = new BundleNode(request, reqPrices.get(i-1));
						System.out.println("nurse "+newBundle.getNurse()+" Request "+newBundle.returnReqs()+" Cost "+newBundle.getPrice());
						tempSol.add(newBundle);
						osum += newBundle.getPrice();
					}
				}
				System.out.println(osum);
				if(osum<this.optimalSum){
					this.possibleSol = tempSol;
					this.optimalSum = osum;
				}
			}
		}
		
	}
	
	private ArrayList<String> addRequestsToList(String[] bundleReq, ArrayList<String> ReqList){
		for(String req : bundleReq){
			ReqList.add(req);
		}
		return ReqList;
	}
	
	public Double getOptimalSum(){
		return this.optimalSum;
	}
	public BundleNode[] getSolution(){
		return this.possibleSol.toArray(new BundleNode[this.possibleSol.size()]);
	}
	public static void main(String[] arg) throws IOException{
		
		XLSXReader reader = new XLSXReader();
		
		scheduling sch = new scheduling();
		sch.schedule(reader.getData(), reader.getRequestPrices(), reader.getSum());
		
		BundleNode[] optimalSol = sch.getSolution();
		
		for(BundleNode b : optimalSol){
			System.out.print( "Nurse " + b.getNurse() + " Price " +b.getPrice() + " Requests ");
			b.displayReqs();
		}
		System.out.print(sch.optimalSum);
	}
}
