package mlui;

import java.io.IOException;



public class InputVectorLearning {

	private svm_train myTrain;
	private svm_predict myPredict;
	
	public InputVectorLearning() {
	    myTrain = new svm_train();
	    myPredict = new svm_predict();
	}
	    
    @SuppressWarnings("static-access")
	public void train() throws IOException {
		String[] trainingData = { 
		    	"-s" ,"4", "-t","0", "-d", "2", "-c" ,"1","-g","1",
		    	"./buffer/historicalData.txt",
			    "./buffer/model.txt",
		}; 
		myTrain.main(trainingData);		
    }
    
    
    @SuppressWarnings("static-access")
	public void predict() throws IOException {
     
   		String[] verifyData = {
	    		 "./buffer/realtimeData.txt",
   			     "./buffer/model.txt",
	    		 "./result/result.txt"
	    };
	    myPredict.main(verifyData);
    }    
 
	public static void main(String[] args) {
    	InputVectorLearning myLearning = new InputVectorLearning();
        
    	try {
        	myLearning.train();
        	myLearning.predict();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
}
