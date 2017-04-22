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
}

