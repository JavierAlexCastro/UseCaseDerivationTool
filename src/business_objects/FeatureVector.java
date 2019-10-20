package business_objects;
import java.util.ArrayList;

/**
 * <p>Title: Machine Learning Use Case Derivation</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class FeatureVector {
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
   private ArrayList<Integer> label;
   
   /*Constructor*/
   public FeatureVector() {
   }
   
   public String toString() {
      return ""+id+", "+sentence+", "+subject+", "+stag+", "+
         sner+", "+stype+", "+verb+", "+vtag+", "+
         vcat+", "+vprocess+", "+object+", "+otag+", "+
         oner+", "+otype+", "+label;
   }
   
   public void addRow(String[] triples, String[] tags, String[] types) {
	   //iterate through triples, tags and types and .add() to appropriate attributes.
   }
   
   public void insertLabel(int label) {
	   /* insert label provided by user into label ArrayList. 
	   Probably using `attribute.set(index, value)` but how to
	   determine index? */
   }
   
   public ArrayList<Integer> getId(){ return id; }
   public ArrayList<String> getSentence(){ return sentence; }
   public ArrayList<String> getSubject() { return subject; }
   public ArrayList<String> getStag(){ return stag; }
   public ArrayList<String> getSner(){ return sner; }
   public ArrayList<String> getStype(){ return stype; }
   public ArrayList<String> getVerb(){ return verb; }
   public ArrayList<String> getVtag(){ return vtag; }
   public ArrayList<String> getVcat(){ return vcat; }
   public ArrayList<String> getVprocess(){ return vprocess; }
   public ArrayList<String> getObject(){ return object; }
   public ArrayList<String> getOtag(){ return otag; }
   public ArrayList<String> getOner(){ return oner; }
   public ArrayList<String> getOtype(){ return otype; }
   public ArrayList<Integer> getLabel(){ return label; }
   
   //we might not need setters because we add one row at a time with attribute.add()
   //for instance we can get the subject for sentence #5 with fv.getSubject().get(5)
   
   //alternatively we can change the implementation to have a List of feature vectors
   //instead of having one feature vector with a List for each attribute
   /*
   public void setId(int id){ this.id=id; }
   public void setSentence(String sentence){ this.sentence=sentence; }
   public void setSubject(String subject){ this.subject=subject; }
   public void setStag(String stag){ this.stag=stag; }
   public void setSner(String sner){ this.sner=sner; }
   public void setStype(String stype){ this.stype=stype; }
   public void setVerb(String verb){ this.verb=verb; }
   public void setVtag(String vtag){ this.vtag=vtag; }
   public void setVcat(String vcat){ this.vcat=vcat; }
   public void setVprocess(String vprocess){ this.vprocess=vprocess; }
   public void setObject(String object){ this.object=object; }
   public void setOtag(String otag){ this.otag=otag; }
   public void setOner(String oner){ this.oner=oner; }
   public void setOtype(String otype){ this.otype=otype; }
   public void setLabel(int label){ this.label=label; }
   */

   public static void main(String[] args) {
      //FeatureVector fv = new FeatureVector();
      //fv.addRow();
   }
}