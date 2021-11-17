package example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyStreamReaderTest {
    @Test
    void shouldGetTop2() {
        String words = "dog cat bird cat dog donkey horse dog";
        WordFrequencyStreamReader ssr = new WordFrequencyStreamReader(words, 2);

        String[] observed = ssr.getTopKFrequentWordsUnordered();

        String[] expectedTop2 = {"cat", "dog"};
        assertTrue(expectedTop2.length == observed.length);
        assertTrue(Arrays.asList(expectedTop2).containsAll(Arrays.asList(observed)));
    }

    @Test
    void shouldGetTop3() {
        String words = "dog cat bird cat dog donkey horse dog monkey horse dog cat";
        WordFrequencyStreamReader ssr = new WordFrequencyStreamReader(words, 3);

        String[] observedMostFrequent3 = ssr.getTopKFrequentWordsUnordered();

        String[] expectedTop3 = {"horse", "cat", "dog"};
        assertTrue(expectedTop3.length == observedMostFrequent3.length);
        assertTrue(Arrays.asList(expectedTop3).containsAll(Arrays.asList(observedMostFrequent3)));
    }

    @Test
    void shouldGetAsManyWordsAsPossible() {
        String words = "dog cat bird cat dog donkey horse dog";
        WordFrequencyStreamReader ssr = new WordFrequencyStreamReader(words, 12);

        String[] observed = ssr.getTopKFrequentWordsUnordered();

        String[] expectedTop2 = {"cat", "dog", "bird", "donkey", "horse"};
        assertTrue(expectedTop2.length == observed.length);
        assertTrue(Arrays.asList(expectedTop2).containsAll(Arrays.asList(observed)));
    }

    @Test
    //@Description("Would have to check with the product owner on this one! Making the assumption case insensitivity should apply throughout to get more interesting results!")
    void shouldIgnoreCaseGettingMostFrequentWords() {
        String givenWords = "cat dog Cat Dog";
        WordFrequencyStreamReader ssr = new WordFrequencyStreamReader(givenWords, 2);

        String[] observed = ssr.getTopKFrequentWordsUnordered();

        String[] expectedTopWords = {"cat", "dog"};
        assertTrue(expectedTopWords.length == observed.length);
        assertTrue(Arrays.asList(expectedTopWords).containsAll(Arrays.asList(observed)));
    }

    @Test
    //@Description("3) Exclude a set of common words (case insensitive) (the,of,to,and,a,in,is,it,you,that,he,was,for,on,are,with,as,I,his,they,be,at,one,have,this,from,or,had,by,not,word,but,what,some,we,can,out,other,were,all,there,when,up,use,your,how,said,an,each,she)")
    void shouldDisqualifyWordsBasedOnPredefinedExclusionsList() {
        String excludedWords = "the of to and a in is it you that he was for on are with as I his they be at one have this from or had by not word but what some we can out other were all there when up use your how said an each she";
        String includedWords = "cat dog ";
        String givenWords = includedWords.concat(excludedWords);
        WordFrequencyStreamReader ssr = new WordFrequencyStreamReader(givenWords, 2000);

        String[] observed = ssr.getTopKFrequentWordsUnordered();

        String[] expectedTopWords = includedWords.split(" ");
        assertTrue(expectedTopWords.length == observed.length);
        assertTrue(Arrays.asList(expectedTopWords).containsAll(Arrays.asList(observed)));
    }

    @Test
    void shouldIgnoreCaseGivenExcludedWords() {
        String excludedWords = "The Of To and a in is it you that he was for on are with as I his they be at one have this from or had by not word but what some we can out other were all there when up use your how said an each she";
        String includedWords = "cat dog ";
        String givenWords = includedWords.concat(excludedWords);
        WordFrequencyStreamReader ssr = new WordFrequencyStreamReader(givenWords, 2000);

        String[] observed = ssr.getTopKFrequentWordsUnordered();

        String[] expectedTopWords = includedWords.split(" ");
        assertTrue(expectedTopWords.length == observed.length);
        assertTrue(Arrays.asList(expectedTopWords).containsAll(Arrays.asList(observed)));
    }

}