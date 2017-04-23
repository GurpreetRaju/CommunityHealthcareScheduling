package Model;

import java.util.ArrayList;

public class nurse {
	private int id = 0;
	private ArrayList<BundleNode> bundles = new ArrayList<BundleNode>();
	
	public nurse(int newId){
		this.id = newId;
	}
		
	public void addBundle(BundleNode newbundle){
		this.bundles.add(newbundle);
	}
	
	public int getID(){
		return this.id;
	}
	
	public BundleNode[] getBundles(){
		return this.bundles.toArray(new BundleNode[bundles.size()]);
	}
	
	public BundleNode getCheapestBundle(){
		BundleNode cb = null;
		int i = 0;
		for(BundleNode b: bundles){
			if(i==0){
				cb = b;
				i++;
				continue;
			}
			if(b.getPrice()<cb.getPrice()){
				cb=b;
			}
		}
		return cb;
	}
	
	public BundleNode getBestCompatibleBundle(ArrayList<String> newReqList){
		BundleNode cb = null;
		for(BundleNode b: bundles){
			if(checkReqExistInList(b.getRequests(), newReqList)){
				if(cb==null){
					cb = b;
				}
				else if(b.getRequests().length>cb.getRequests().length){
					cb = b;
				}
				else if(b.getRequests().length==cb.getRequests().length && b.getPrice()<cb.getPrice()){
					cb = b;
				}
			}
		}
		return cb;
	}	
	
	private boolean checkReqExistInList(String[] newBundleReq, ArrayList<String> ReqList){
		
		for(String req : newBundleReq){
			if(ReqList.contains(req)){
				return false;
			}
		}
		return true;
	}
	
	public boolean equals(Object o){
		if(o instanceof nurse){
			nurse n = (nurse) o;
			//System.out.println("Checkpoint");
			if(this.getID() == n.getID()){
				return true;
			}
		}
		return false;
	}
//	
//	public static void main(String[] arg){
//		nurse n = new nurse(1);
//		String[] req1 = {"5","3","2"};
//		n.addBundle(new BundleNode(req1,50.0));
//		String[] req2 = {"1","3","2","6"};
//		n.addBundle(new BundleNode(req2,58.0));
//		String[] req3 = {"1","3","4"};
//		n.addBundle(new BundleNode(req3,40.0));
//		String[] req4 = {"2","4"};
//		n.addBundle(new BundleNode(req4,30.0));
//		ArrayList<String> newReqList = new ArrayList<String>();
//		newReqList.add("4");
//		BundleNode b = n.getBestCompatibleBundle(newReqList);
//		b.displayReqs();
//	}
}

