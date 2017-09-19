package hello.analyze;

import hello.models.Bracket;

import java.util.*;

public class BracketAnalyzer {
    public Bracket testBrackets(String s){
        //Clear test
        String test = s.replaceAll("[^(){}]", "");
        //Get brackets
        List<String> as = Arrays.asList(test.split(""));
        //Create Stack
        Stack<String> stack = new Stack<>();
        //Create Pojo for responce entity
        Bracket bracket = new Bracket();
        //Put test
        bracket.setTest(test);
        try{
            for (String p : as) {
                //push open bracket
                if (Objects.equals(p, "(") || Objects.equals(p,"{")) {
                    stack.push(p);
                }
                //compare break bracket with bracket on top of stack if not equal return incorrect
                if (Objects.equals(p, ")")) {
                    if (!stack.pop().equals("(")) {
                        bracket.setResult("INCORRECT");
                        return bracket;
                    }
                }
                if (Objects.equals(p, "}")) {
                    if (!stack.pop().equals("{")) {
                        bracket.setResult("INCORRECT");
                        return bracket;
                    }
                }
            }}
        catch (EmptyStackException e){
            //if first element is already wrong return incorrect
            bracket.setResult("INCORRECT");
            return bracket;
        }
        //if for in does not find errors return correct
        bracket.setResult("CORRECT");
        return bracket;
    }
}
