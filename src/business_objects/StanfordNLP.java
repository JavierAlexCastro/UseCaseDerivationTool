package business_objects;

import java.util.Arrays;
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
		List<String> subject = Arrays.asList(triple[0].split(" "));
		List<String> verb = Arrays.asList(triple[1].split(" "));
		List<String> object = Arrays.asList(triple[2].split(" "));
		
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    // Annotate an example document.
	    Annotation doc = new Annotation(triple[0]+" "+triple[1]+" "+triple[2]);
	    pipeline.annotate(doc);

	    List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class);
	    for (CoreMap sentence : sentences) {
	        for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
	            String word = token.get(CoreAnnotations.TextAnnotation.class);
	            if(subject.contains(word)){
	            	stag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
		            sner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	            }else if(verb.contains(word)){
	            	vtag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
	            }else if(object.contains(word)){
	            	otag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
		            oner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	            }else{
	            	System.out.println("Not subject, not ver, not object?");
	            }
	            // this is the POS tag of the token
	            //String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
	            //String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	            //System.out.println(word + " - POS: " + pos + " - NER: " + ner);
	            
	            //update tag and ner variables
	        }
	    }	
		String[] tags = {stag, sner, vtag, otag, oner};
		//System.out.println(Arrays.toString(tags));
		return tags;
	}
	
	public static void main(String[] args) {
	      StanfordNLP snlp = new StanfordNLP();
	      String[] triple = {"user", "retrieve money from", "ATM"};
	      snlp.getTypes(triple);
	}
}
