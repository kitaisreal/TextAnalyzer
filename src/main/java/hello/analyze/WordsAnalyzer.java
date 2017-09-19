package hello.analyze;


import hello.models.Word;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.IntStream;

public class WordsAnalyzer {
    private final String pathToDictionary ="dictionary/textAnalyzer.txt";

    public List<Word> mostWords(String text){
        //Clear text and split
        String[] textWords  = text.replaceAll("[,!:.;?()}<>123456{7890@#$%^&*-=+~`'\r\n]", " ").split(" +");
        List<String> result = new ArrayList<>();
        //Add to list
        Collections.addAll(result,textWords);
        //Create Map for word counting
        Map<String, Integer> sl = new HashMap<>();
        //put our word statistic in hash map
        result.forEach(word -> {
            if (!sl.containsKey(word)) {
                //if no word put with 1 count
                sl.put(word, 1);
            } else {
                //if word is put with count+1
                sl.put(word, sl.get(word) + 1);
            }
        });
        //Create Dictionary of wrong words
        List<String> dictionaryWords = getDictionary();
        //Clear our Map
        if (dictionaryWords!=null){
            dictionaryWords.forEach(word->sl.remove(word));
        }
        //Convert Map Into List of Entryes <Word, Count>
        ArrayList<Map.Entry<String, Integer>> l = new ArrayList<>();
        sl.entrySet().forEach(entry -> l.add(entry));
        //Sort our Entry list
        l.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        //Create list with pojo of Word for responce entity
        List<Word> words = new ArrayList<>();
        //Pick 10 on top
        IntStream.range(0, 10).forEach(a -> words.add(new Word(l.get(a).getKey(),l.get(a).getValue())));
        return words;
    }
    private List<String> getDictionary() {
        //get Dictionary from resourses split and return
        Resource resource = new ClassPathResource(pathToDictionary);
        try {
            InputStream resourceInputStream = resource.getInputStream();
            String dictionary = StreamUtils.copyToString(resourceInputStream, Charset.defaultCharset());
            String[] wordsInDictionary= dictionary.split("\r\n");
            System.out.println(wordsInDictionary.length);
            List<String> list = new ArrayList<>();
            Collections.addAll(list,wordsInDictionary);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
