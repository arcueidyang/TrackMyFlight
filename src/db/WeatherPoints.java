package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * 
 * @author Richard Yang
 *
 */

public class WeatherPoints {
    
    public static final int START_LINE_NUMBER = 0;
    public static final int END_LINE_NUMBER = 3;
    public static final int FULL_LINE_NUMBER = 4;
    public static final String BUNDLE_PATH = "resources.weatherPointSize";
    
    private Scanner myScanner;
    private List<Integer[]> myData;
    private ResourceBundle myVectorSize;  
    private int arraySize;
    private int lineSize;
    
    
    public WeatherPoints(String fileName){

        myData = new ArrayList<Integer[]>();
        myVectorSize = ResourceBundle.getBundle(BUNDLE_PATH);
        lineSize = Integer.parseInt(myVectorSize.getString(fileName));
        arraySize = lineSize * (END_LINE_NUMBER - START_LINE_NUMBER + 1);
        try {
            myScanner = new Scanner(new File("./data/"+fileName));
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        initialize();
        
    }
    public WeatherPoints(){
        myData = new ArrayList<Integer[]>();
    }
    
    public void initialize(){
        storeInArray(loadData());
        myScanner.close();
    }
    
    public List<String> loadData(){
       List<String> allVectors = new ArrayList<String>();
       String line = myScanner.nextLine();
       allVectors.add(line);
       
       while(myScanner.hasNextLine()){
           line = myScanner.nextLine();
           if(line.indexOf("*") == -1) allVectors.add(line);
       }
       return allVectors; 
        
    }
    public void storeInArray(List<String> allVectors){
        int size = allVectors.size();
        Integer[] data = new Integer[arraySize];
        stringToIntArray(allVectors.get(0), 0,data);
        for(int i =1 ; i<size ; i++){
            int currentLineIndexMod4 = i % FULL_LINE_NUMBER;
        	if(currentLineIndexMod4 != 0){
            	if(currentLineIndexMod4 >= START_LINE_NUMBER && currentLineIndexMod4 <= END_LINE_NUMBER) {
            		stringToIntArray(allVectors.get(i), lineSize*(currentLineIndexMod4 - START_LINE_NUMBER),data);	
            	}
            }else{
                myData.add(data);
                data = new Integer[arraySize];
                stringToIntArray(allVectors.get(i), 0,data);
            }
        }
        myData.add(data);
    }
    public void stringToIntArray(String str, int seq,Integer[] arr){
        int length = str.length();
        for(int i =0; i<length ; i++){
            arr[seq+i] = Integer.parseInt(str.charAt(i)+"");
        }
    }
   
    public List<Integer[]> getMyData () {
        return myData;
    }
    
    public void addInputVector(Integer[] newData){
        myData.add(newData);
    }
    
    public Integer[] remove(int seq){
        return myData.remove(seq);
    }
    
    public boolean remove(Integer[] removeData){
       return  myData.remove(removeData);
    }
    
    public int size(){
        return myData.size();
    }
    
    public Integer[] get(int seq){
        return myData.get(seq);
    }
    
    public int arraySize() {
    	return arraySize;
    }
    
    
    public int computeDistance(Integer[] a, Integer[] b) {
    	if(a.length != b.length) return -1;
    	int length = a.length;
    	int distance = 0;
    	for(int i = 0; i < length; i++) {
    		distance += Math.abs(a[i] - b[i]);
    	}
    	//System.out.println(distance);
    	return distance;
    } 
    
    public long computeAverageDistance() {
    	int size = myData.size();
    	long totalDistance = 0;
    	for(int i = 0; i < size - 1; i++) {
    		for(int j = i + 1; j < size; j++) {
    			totalDistance += computeDistance(myData.get(i), myData.get(j));
    		}
    	}
    	int totalNumber = size * (size -1) / 2; 
        return totalDistance / totalNumber;
    }
    
    public int countSameVectorNumber(WeatherPoints vectors) {
    	int count = 0;
    	for(Integer[] i : myData) {
    		for(Integer[] j : vectors.myData) {
    			if(isSameArray(i,j)) {
    				count ++;
    				break;
    			}
    		}
    	}
    	return count;
    }
    
    public static boolean isSameArray(Integer[] a1, Integer[] a2) {
    	if(a1.length != a2.length) return false;
    	int length = a1.length;
    	for(int i = 0; i < length; i++) {
    		if(a1[i] != a2[i]) return false;
    	}
    	return true;
    }
    
}