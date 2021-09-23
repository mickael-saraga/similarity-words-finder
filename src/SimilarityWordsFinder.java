import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimilarityWordsFinder {
  
  private String wordInput;
  public String getWordInput() {
    return wordInput;
  }
  public void setWordInput(String wordInput) {
    this.wordInput = wordInput;
  }
  
  public SimilarityWordsFinder(String wordInput) {
    this.wordInput = wordInput;
  }
  
  
  public List<String> getWordSuggestions(List<String> suggestedWords) {
    List<String> similarWords = new ArrayList<>(Collections.emptyList());
     suggestedWords.forEach(suggestedWord -> {
       if (!isShorterThanInput(suggestedWord)) {
         if (computeSimilarity(getLettersIndexesIn(suggestedWord.toLowerCase())) > -1) {
           similarWords.add(suggestedWord);
         }
       }
     });
    return similarWords;
  }
  
  public List<String> getSuggestions(List<String> words, int suggestionsCount) {
//    List<Pair<Integer, String>> similarWords = new ArrayList<>();
//    words.forEach(word -> {
//       if (!isShorterThanInput(word)) {
//         Integer similarityDistanceScore = computeSimilarity(getLettersIndexesIn(word.toLowerCase()));
//         similarWords.add(new Pair<>(similarityDistanceScore, word));
//       }
//     });
    List<String> nearerWords = words.stream()
                                    .filter(((Predicate<? super String>) this::isShorterThanInput).negate())
                                    .map(this::createWordSimilarityPair)
                                    .sorted(similaritiesComparator())
                                    .map(Pair::getValue)
                                    .collect(Collectors.toList());
  
    return suggestionsCount <= nearerWords.size() ? nearerWords.subList(0, suggestionsCount)
                                                  : nearerWords;
  }
  
  private Pair<Integer, String> createWordSimilarityPair(String word) {
    return new Pair<>(computeSimilarity(getLettersIndexesIn(word.toLowerCase())), word);
  }
  
  private boolean isShorterThanInput(String word) {
    return word.length() < wordInput.length();
  }
  
  private List<Integer> getLettersIndexesIn(String word) {
    return Arrays.stream(wordInput.toLowerCase().split(""))
                 .map(word::indexOf)
                 .collect(Collectors.toList());
  }
  
  private Integer computeSimilarity(List<Integer> indexes) {
    AtomicInteger lastIndex = new AtomicInteger(indexes.get(0));
    indexes.forEach(index -> {
      if (index != -1 && !index.equals(indexes.get(indexes.size() - 1))) {
        lastIndex.set(index);
      }
    });
    return ((indexes.get(indexes.size() - 1) - lastIndex.get()) - 1);
  }
  
  private Comparator<Pair<Integer, String>> similaritiesComparator() {
    return (p1, p2) -> Objects.equals(p1.getKey(), p2.getKey()) ? p1.getValue().length() - p2.getValue().length()
                                                                : p1.getKey() - p2.getKey();
  }
  
}
