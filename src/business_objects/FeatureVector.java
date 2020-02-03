package business_objects;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>Title: Machine Learning Use Case Derivation</p>
 *
 * <p>Description: Generates a Feature vector based on a list of requirements (.txt)
 *                 Generates Use Cases from a list of requirements (.txt)
 *                     Requires training data 'labelledFile_true_false_version.arff'</p>
 *
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <p>Company: UTA - CSE5322 - Fall 2019 - Team 4</p>
 *
 * @author not attributable
 * @version 1.0
 */

public class FeatureVector {
   //composite pattern
   private ArrayList<Integer> id;
   private ArrayList<String> sentence;
   private ArrayList<String> subject;
   private ArrayList<String> stag;
   private ArrayList<String> sner;
   private ArrayList<String> stype;
   private ArrayList<String> verb;
   private ArrayList<String> vtag;
   private ArrayList<String> vcat;
   private ArrayList<String> vprocess;
   private ArrayList<String> object;
   private ArrayList<String> otag;
   private ArrayList<String> oner;
   private ArrayList<String> otype;
   private ArrayList<Boolean> label;
   
   /*Constructor*/
   public FeatureVector() {
	   id = new ArrayList<Integer>();
	   sentence = new ArrayList<String>();
	   subject = new ArrayList<String>();
	   stag = new ArrayList<String>();
	   sner = new ArrayList<String>();
	   stype = new ArrayList<String>();
	   verb = new ArrayList<String>();
	   vtag = new ArrayList<String>();
	   vcat = new ArrayList<String>();
	   vprocess = new ArrayList<String>();
	   object = new ArrayList<String>();
	   otag = new ArrayList<String>();
	   oner = new ArrayList<String>();
	   otype = new ArrayList<String>();
	   label = new ArrayList<Boolean>();
   }
   
   public String toString() {
      return ""+id+", "+sentence+", "+subject+", "+stag+", "+
         sner+", "+stype+", "+verb+", "+vtag+", "+
         vcat+", "+vprocess+", "+object+", "+otag+", "+
         oner+", "+otype+", "+"label";
   }
   
   //creates a feature vector object and returns it - clearly..
   public FeatureVector create(){
	   FeatureVector fv = new FeatureVector();
	   return fv;
   }
   
   //populate a feature vector with an individual row
   public void addRow(int req_no, String requirement, String[] triple, String[] tags, String[] types) {
	   vcat.add(types[2]);
	   vprocess.add(types[3]);
	   id.add(req_no);
	   sentence.add(requirement);
	   subject.add(triple[0]);
	   stag.add(tags[0]);
	   sner.add(tags[1]);
	   stype.add(types[0]);
	   verb.add(triple[1]);
	   vtag.add(tags[2]);
	   object.add(triple[2]);
	   otag.add(tags[3]);
	   oner.add(tags[4]);
	   otype.add(types[1]);
	   label.add(false); //default to false so WEKA knows it's a string attribute   
   }
   
   //write Feature Vector to a csv file
   public void writeFV(String filename) {
	   try{
		   String fname = filename.split("\\.")[0]; //remove file extension
		   FileWriter csvWriter = new FileWriter("src/outputs/FV_" + fname + ".csv");
		   csvWriter.write("subject,s-tag,s-NER,s-type,verb,v-tag,v-cat,v-process,object,o-tag,o-NER,o-type,label\n");
		   for(int i=0;i<id.size();i++){
			   csvWriter.append(subject.get(i) + ",");
			   csvWriter.append(stag.get(i) + ",");
			   csvWriter.append(sner.get(i) + ",");
			   csvWriter.append(stype.get(i) + ",");
			   csvWriter.append(verb.get(i) + ",");
			   csvWriter.append(vtag.get(i) + ",");
			   csvWriter.append(vcat.get(i).toString() + ",");
			   csvWriter.append(vprocess.get(i).toString() + ",");
			   csvWriter.append(object.get(i) + ",");
			   csvWriter.append(otag.get(i) + ",");
			   csvWriter.append(oner.get(i) + ",");
			   csvWriter.append(otype.get(i) + ",");
			   csvWriter.append(label.get(i) + "\n");
		   }
		   csvWriter.flush();
		   csvWriter.close();
		   
	   }catch(IOException ioex){
		   System.out.println("Error creating CSV file");
		   ioex.printStackTrace();
	   }catch(Exception ex){
		   System.out.println("Error generating Feature Vector file");
		   ex.printStackTrace();
	   }
	   
	   
   }
   
   public void insertLabel(int label) {
	   
   }

   public static void main(String[] args) {
      //FeatureVector fv = new FeatureVector();
      //fv.addRow();
   }
}