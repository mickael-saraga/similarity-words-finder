import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimilarityWordsFinderTest {
  
  SimilarityWordsFinder similaritiesFinder;
  String wordToTest = "gros";
  
  List<String> goodShorterWordsList = Arrays.asList("go", "ros", "go");
  List<String> badShorterWordsList = Arrays.asList("go", "grosse", "ros", "go");
  List<String> badEqualSizeAndLongerWordsList = Arrays.asList("gro", "gosse", "gos", "gors");
  
//  List<String> wordsToCompareList = Arrays.asList("gros", "gras", "graisse", "aggressif", "go");
  List<String> wordsToCompareList = Arrays.asList("gros", "aggressif", "gras", "graisse", "go");
//  int expectedWordsToCompareSuggestionsSize = 4;
//  List<String> expectedSuggestions = Arrays.asList("gros", "gras", "aggressif", "graisse");
  int expectedWordsToCompareSuggestionsSize = 2;
  List<String> expectedSuggestions = Arrays.asList("gros", "gras");
  List<String> anotherWordsToCompareList = Arrays.asList("lamelle", "gros", "gras", "graisse", "aggressif", "go");
  int suggestionsNumberAsked = 2;
  
  
  @BeforeEach
  void setUp() {
    similaritiesFinder = new SimilarityWordsFinder(wordToTest);
  }
  
  @Test
  void isWordToTestCorrectlySet() {
    assertEquals(wordToTest, similaritiesFinder.getWordInput());
  }
  
  @Test
  void isCheckerWithEmptyWordsListToCompareReturnsEmptySuggestions() {
    assertEquals(0, similaritiesFinder.getWordSuggestions(Collections.emptyList()).size());
  }
  
  @Test
  void isCheckerWithShorterWordsReturnsEmptySuggestions() {
    assertNotEquals(0, similaritiesFinder.getWordSuggestions(badShorterWordsList).size());
    assertEquals(0, similaritiesFinder.getWordSuggestions(goodShorterWordsList).size());
  }
  
  @Test
  void checkThatShorterWordsAreFiltered() {
    assertTrue(similaritiesFinder.getWordSuggestions(badEqualSizeAndLongerWordsList).size() < badEqualSizeAndLongerWordsList.size());
  }
  
  @Test
  void checkWordsSimilarities() {
    similaritiesFinder.getSuggestions(wordsToCompareList, suggestionsNumberAsked);
    assertEquals(expectedWordsToCompareSuggestionsSize, similaritiesFinder.getSuggestions(wordsToCompareList, suggestionsNumberAsked).size());
    assertEquals(expectedSuggestions, similaritiesFinder.getSuggestions(wordsToCompareList, suggestionsNumberAsked));
  }
  
}