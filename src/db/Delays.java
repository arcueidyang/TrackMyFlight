package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Richard Yang
 *
 */

public class Delays {
   private Scanner myScanner;
   private List<Double> myDelays = new ArrayList<Double>(); 
   
   public Delays(String fileName){
       myDelays = new ArrayList<Double>();
       
       try {
           myScanner = new Scanner(new File("./data/"+fileName));
       }
       catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       initialize();
       //normalize();
   }
   
   public Delays(){
       myDelays = new ArrayList<Double>();
   }
   
   public void initialize(){
       String line = myScanner.nextLine();
       while(myScanner.hasNextLine()){
           myDelays.add(Double.parseDouble(line));
           line = myScanner.nextLine();
       }
       myDelays.add(Double.parseDouble(line));
       myScanner.close();
   }
   
   public void normalize() {
	   double maxDelay = findMaxDelay();
	   double minDelay = findMinDelay();
	   List<Double> normalized = new ArrayList<Double>();
	   for(int i = 0; i < myDelays.size(); i++) {
		   double origin = myDelays.get(i);
		   normalized.add((origin - minDelay)/(maxDelay - minDelay));
	   }
	   myDelays = normalized;
   }

   public double findMinDelay(){
       double min =myDelays.get(0);
       for(int i =0 ;i < myDelays.size() ; i++){
           if(myDelays.get(i)< min){
               min = myDelays.get(i);
           } 
       }
       return min;
   }
   public double findMaxDelay(){
       double max =myDelays.get(0);
       for(int i =0 ;i < myDelays.size() ; i++){
           if(myDelays.get(i)> max){
               max = myDelays.get(i);
           } 
       }
       return max;
   }
   
   public List<Double> getMyDelays () {
       return myDelays;
   }
   
   public void addDelay(double newDelay){
       myDelays.add(newDelay);
   }
   
   public double remove(int seq){
       return myDelays.remove(seq);
   }
   
   public boolean remove(Double removeDelay){
       return myDelays.remove(removeDelay);
   }
   
   public int size(){
       return myDelays.size();
   }
   
   public Double get(int seq){
       return myDelays.get(seq);
   }
}
