package business_objects;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;

public class StanfordNLP {

	/*constructor*/
	public StanfordNLP() {
		
	}
	
	public String[] getTypes(String[] triple) {
		String stag = "";
		String sner = "";
		String vtag = "";
		String otag = "";
		String oner = "";
		Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    // Annotate an example document.
	    Annotation doc = new Annotation(triple[0]+" "+triple[1]+" "+triple[2]);
	    pipeline.annotate(doc);

	    List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class);
	    for (CoreMap sentence : sentences) {
	        for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
	            String word = token.get(CoreAnnotations.TextAnnotation.class);
	            // this is the POS tag of the token
	            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
	            String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	            System.out.println(word + " - POS: " + pos + " - NER: " + ner);
	            
	            //update tag and ner variables
	        }
	    }		
		String[] tags = {stag, sner, vtag, otag, oner};
		return tags;
	}
	
	public static void main(String[] args) {
	      StanfordNLP snlp = new StanfordNLP();
	      String[] triple = {"user", "retrieve money from", "ATM"};
	      snlp.getTypes(triple);
	}
}
