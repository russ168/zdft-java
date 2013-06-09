package common;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Dong ai hua
 * Date: 13-5-30
 * Time: 下午12:10
 * To change this template use File | Settings | File Templates.
 */
public class NameDict {
    public static List<String> ngram(String input, int minGram, int maxGram) throws Exception {
        StringReader reader = new StringReader(input);
        //TokenStream stream = analyzer.tokenStream("f", sr);
        TokenStream stream = new NGramTokenizer(reader, minGram, maxGram);
        stream.reset();
        List<String> list = new ArrayList<String>();
        while (stream.incrementToken()) {
            CharTermAttribute ta = stream.getAttribute(CharTermAttribute.class);
            list.add(ta.toString());
            System.out.println(ta.toString());
        }
        //Joiner joiner = Joiner.on("");
        //System.out.println("Result:" + joiner.join(list));
        //Assert.assertEquals(joiner.join(list), expected[i]);
        return list;

    }

    public static String genSuffix(int len, String characters) {
        StringBuffer buffer = new StringBuffer();
        int charactersLength = characters.length();
        for (int i = 0; i < len; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int)index));
        }
        return  buffer.toString();
    }


}
