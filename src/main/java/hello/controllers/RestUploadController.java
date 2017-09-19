package hello.controllers;

import hello.analyze.BracketAnalyzer;
import hello.analyze.WordsAnalyzer;
import hello.models.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
public class RestUploadController {
    @PostMapping(value = "/testUpload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            //check file extension
            if (!Objects.equals(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')), ".txt")){
                return new ResponseEntity("Input .txt file", HttpStatus.BAD_REQUEST);
            }
            //get text
            String content = new String(file.getBytes(), "UTF-8");
            //create analyze object
            WordsAnalyzer wordsAnalyzer = new WordsAnalyzer();
            BracketAnalyzer bracketAnalyzer = new BracketAnalyzer();
            //generate response
            RestResponse restResponse = new RestResponse(wordsAnalyzer.mostWords(content), bracketAnalyzer.testBrackets(content));
            return new ResponseEntity(restResponse,HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity("Input .txt file", HttpStatus.BAD_REQUEST);
    }

}