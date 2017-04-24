package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import Model.BundleNode;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXReader{
	private String excelFilePath = "src/DataSample.xlsx";
	private FileInputStream inputStream = null;
 	private XSSFWorkbook workbook = null;
 	private XSSFSheet firstSheet = null;
 	private XSSFSheet secondSheet = null;
 	private LinkedList<Double> requestPrices = new LinkedList<Double>();
 	private LinkedList<nurse> nurses = new LinkedList<nurse>();
 	private Double sum = 0.0;
 	
 	public XLSXReader(){
 		init();
 	}
 	
 	public XLSXReader(String newfile){
 		this.excelFilePath = newfile;
 		init();
 	}
 	
 	private void init(){
 		try {
			inputStream = new FileInputStream(new File(excelFilePath));
			workbook = new XSSFWorkbook(inputStream);
 		} catch (IOException e) {
			e.printStackTrace();
		} 
 	 	firstSheet = (XSSFSheet) workbook.getSheetAt(0);
 	 	secondSheet = (XSSFSheet) workbook.getSheetAt(1);
 	 	this.getFirstsheetData();
		this.getSecondsheetData();
 	}
 	
	public void getFirstsheetData(){
		 	Iterator<Row> iterator = firstSheet.iterator();
		 	Row nextRow = iterator.next();
				while (iterator.hasNext()) {
					nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					int i=0;
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && i==1){
							requestPrices.add(cell.getNumericCellValue());
							i=0;
							break;
						}
						i++;
					}
				}
			calculateTotalPrice();
	}
	
	private void calculateTotalPrice() {
		for(Double reqPrice : requestPrices){
			sum += reqPrice;
		}
	}

	public void getSecondsheetData(){
	 	Iterator<Row> iterator = secondSheet.iterator();
	 	String[] temp;
	 		while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				if(nextRow.getRowNum()==0){
					continue; //just skip the rows if row number is 0 or 1
		 		}
				if(nextRow.getRowNum()==1 ){
					int n = nextRow.getLastCellNum()/2;
					for(int x=1;x<=n;x++){	
						nurses.add(new nurse(x));
					}
					continue;
				}
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				int nurse = 0;
				int i = 0;
				BundleNode node = new BundleNode();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if(i==0){
						
					}
					else if((i%2)!=0){
						nurse++;
						if(cell.getCellType()== Cell.CELL_TYPE_BLANK){
							
						}
						else{
							node.setNurse(nurse);
							temp = cell.getStringCellValue().split(",");
							
							String[] temp2 = new String[temp.length];
							for(int i1=0 ; i1< temp.length ; i1++){
								temp2[i1] = temp[i1].replaceAll("\\s+","");
							}
							node.setRequests(temp2);
						}
					}
					else if(cell.getCellType()!= Cell.CELL_TYPE_BLANK){
						node.setPrice(cell.getNumericCellValue());
						nurse nurs = this.nurses.get(nurse-1);
						nurs.addBundle(new BundleNode(node.getRequests(),node.getNurse(),node.getPrice()));
					}
					i++;
				}
			}
	}
	public void display(){
		System.out.println("Result");
		for(nurse n : this.nurses){
			for(BundleNode b: n.getBundles()){
				System.out.println("Nurse "+ n.getID() + " Price " + b.getPrice() + " Requests " + b.returnReqs());
			}
		}
	}
	
	private void displayRequests() {
		for(Double d: requestPrices){
			System.out.println(d);
		}
	}
	
	public LinkedList<Double> getRequestPrices(){
		return this.requestPrices;
	}
 	public LinkedList<nurse> getData(){
 		return this.nurses;
 	}
 	public Double getSum(){
 		return this.sum;
 	}

}
