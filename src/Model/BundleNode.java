package Model;

public class BundleNode{
	private String[] requests;
	private int nurse = 0;
	private Double price = 0.0;
	
	public BundleNode(String[] req, int nurse){
		this.requests = req;
		this.nurse = nurse;
	}
	
	public BundleNode(String[] newreq, int newnurse, Double newPrice){
		this.requests = newreq;
		this.nurse = newnurse;
		this.price = newPrice;
	}
	
	public BundleNode(String[] newreq, Double newPrice){
		this.requests = newreq;
		this.price = newPrice;
	}
		
	public BundleNode(){
		
	}
	
	public String[] getRequests(){
		return this.requests;
	}
	
	public int getNurse(){
		return this.nurse;
	}
	
	public void setRequests(String[] requests){
		this.requests = requests;
	}
	
	public void setNurse(int n){
		this.nurse = n;
	}
	
	public void setPrice(Double newPrice){
		this.price = newPrice;
	}
	
	public Double getPrice(){
		return this.price;
	}
	
	public void display(){
		System.out.print( "Nurse " + this.getNurse() + " Price " + this.getPrice() + " Requests ");
		this.displayReqs();
	}
	
	public void displayReqs(){
		for(String req : this.requests){
			System.out.print(req + " ");
		}
		System.out.println("");
	}
	
	public String returnReqs(){
		String req1 = new String();
		int i=0;
		for(String req : this.requests){
			if(i!=0){
				req1+=", ";
			}
			req1+=req;
			i++;
		}
		return req1;
	}
	
	public boolean equals(Object o){
		if(o instanceof BundleNode){
			BundleNode b = (BundleNode) o;
			if(this.nurse == b.nurse && this.price == b.price && this.equals(b.requests)){
				return true;
			}
		}
		return false;
	}
	
	
}
