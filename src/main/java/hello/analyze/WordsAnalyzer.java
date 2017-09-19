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
        String[] textWords  = text.replaceAll("[,!:.;?()}<>123456{7890@#$%^&*-=+~`'\r\n]", " ").split(" +");
        List<String> result = new ArrayList<>();
        Collections.addAll(result,textWords);

        Map<String, Integer> sl = new HashMap<>();
        List<String> dictionaryWords = getDictionary();

        result.forEach(word -> {
            if (!sl.containsKey(word)) {
                sl.put(word, 1);
            } else {
                sl.put(word, sl.get(word) + 1);
            }
        });
        if (dictionaryWords!=null){
            dictionaryWords.forEach(word->sl.remove(word));
        }
        ArrayList<Map.Entry<String, Integer>> l = new ArrayList<>();
        sl.entrySet().forEach(entry -> l.add(entry));
        l.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        List<Word> words = new ArrayList<>();
        IntStream.range(0, 10).forEach(a -> words.add(new Word(l.get(a).getKey(),l.get(a).getValue())));
        return words;
    }
    private List<String> getDictionary() {
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
