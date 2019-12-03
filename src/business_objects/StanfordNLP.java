package business_objects;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.*;

public class StanfordNLP {

	/*constructor*/
	public StanfordNLP() {
		
	}
	
	//Chain of Responsibility to handle exception somewhere else
	public String[] getTypes(String[] triple) throws Exception{
		String stag = "";
		String sner = "";
		String vtag = "";
		String otag = "";
		String oner = "";
		Properties props = new Properties();
		//composite pattern
		List<String> subject = Arrays.asList(triple[0].split(" ")); //list of words in subject
		List<String> verb = Arrays.asList(triple[1].split(" ")); //list of words in verb
		List<String> object = Arrays.asList(triple[2].split(" ")); //list of words in object
		
		//set the properties for getting the POS and NER
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    //create the document based on the triple
	    Annotation doc = new Annotation(triple[0]+" "+triple[1]+" "+triple[2]);
	    pipeline.annotate(doc);

	    //composite pattern
	    List<CoreMap> sentences = doc.get(CoreAnnotations.SentencesAnnotation.class); //get sentences from the document - only 1 in this context
	    Iterator<CoreMap> sent_iterator = sentences.iterator(); //iterator pattern
	    while(sent_iterator.hasNext()) {
	    	CoreMap sentence = sent_iterator.next();
	    	Iterator<CoreLabel> token_iterator = sentence.get(CoreAnnotations.TokensAnnotation.class).iterator(); //iterator pattern
	    	while(token_iterator.hasNext()) {
	    		CoreLabel token = token_iterator.next();
	    		String word = token.get(CoreAnnotations.TextAnnotation.class); //extract the word from the token
	            if(subject.contains(word)){ //if it matches any word in the subject
	            	stag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
		            sner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	            }else if(verb.contains(word)){ //if it matches any word in the verb
	            	vtag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
	            }else if(object.contains(word)){ //if it matches any word in the object
	            	otag = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
		            oner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
	            }else{
	            	System.out.println("Not subject, not ver, not object? Word: " + word);
	            }
	    	}
	    }
		String[] tags = {stag, sner, vtag, otag, oner};
		return tags;
	}
	
	public static void main(String[] args) {
	      /*StanfordNLP snlp = new StanfordNLP();
	      String[] triple = {"user", "retrieve money from", "ATM"};
	      snlp.getTypes(triple);*/
	}
}
