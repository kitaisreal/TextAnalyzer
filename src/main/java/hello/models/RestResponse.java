package hello.models;

import java.util.List;

public class RestResponse {
    public List<Word> topMatchedWords;
    public Bracket bracket;
    public RestResponse(List<Word> topMatchedWords, Bracket bracket){
        this.topMatchedWords=topMatchedWords;
        this.bracket=bracket;
    }
}
