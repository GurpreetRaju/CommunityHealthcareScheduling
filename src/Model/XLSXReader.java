package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
 	private LinkedList<BundleNode> bundles = new LinkedList<BundleNode>();
 	private Double sum = 0.0;
 	
 	public XLSXReader() throws IOException{
 		inputStream = new FileInputStream(new File(excelFilePath));
 	 	workbook = new XSSFWorkbook(inputStream);
 	 	firstSheet = (XSSFSheet) workbook.getSheetAt(0);
 	 	secondSheet = (XSSFSheet) workbook.getSheetAt(1);
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
							//System.out.println(cell.getNumericCellValue());
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
				if(nextRow.getRowNum()==0 || nextRow.getRowNum()==1 ){
					continue; //just skip the rows if row number is 0 or 1
		 		}
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				int nurse = 0;
				int i = 0;
				BundleNode node = new BundleNode();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					
					if(i==0){
//						System.out.println("nothing to read");
					}
					else if((i%2)!=0){
						nurse++;
						if(cell.getCellType()!= Cell.CELL_TYPE_BLANK){
							node.setNurse(nurse);
							//System.out.print(node.getNurse() + " ");
							temp = cell.getStringCellValue().split(",");
							String[] temp2 = new String[temp.length];
							for(int i1=0 ; i1< temp.length ; i1++){
								temp2[i1] = temp[i1].replaceAll("\\s+","");
							}
							node.setRequests(temp2);
							//System.out.print(node.getRequests()[0] +" ");
						}
					}
					else if(cell.getCellType()!= Cell.CELL_TYPE_BLANK){
						node.setPrice(cell.getNumericCellValue());
						//System.out.println(node.getPrice() + " ");
						this.bundles.add(new BundleNode(node.getRequests(),node.getNurse(),node.getPrice()));
//						System.out.println("adding price");
					}
					i++;
				}
			}
	}
	public void display(){
		System.out.println("Result");
		for(BundleNode b : this.bundles){
			System.out.println(b.getNurse() + " " + b.getPrice() + " " + b.getRequests()[0]);
		}
	}
	
	public LinkedList<Double> getRequestPrices(){
		return this.requestPrices;
	}
 	public LinkedList<BundleNode> getBundles(){
 		return this.bundles;
 	}
 	public Double getSum(){
 		return this.sum;
 	}
	
	public static void main(String[] arg) throws IOException{
		XLSXReader xlsfile = new XLSXReader();
		//xlsfile.getFirstsheetData();
		xlsfile.getSecondsheetData();
		xlsfile.display();
	}
}
