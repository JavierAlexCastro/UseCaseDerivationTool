package business_objects;

public class MachineLearningModel {
	 private double accuracy;
	 
	 /*constructor*/
	 public MachineLearningModel() {
		 
	 }
	 
	 /* determines if a sentence/requirement is a valid use case */
	 public int provideRequirement(String sentence) {
		 int isValid = 0;
		 
		 /* check if sentence is a valid requirement and if able to produce
		  * a valid use case from it. Change isValid to 1 if so.
		  */
		 
		 return isValid;
	 }
	 
	 public void setAccuracy(double accuracy) { 
		 this.accuracy=accuracy; 
	 }
	 
	 public double getAccuracy() {
		 return accuracy;
	 }

}
