package hello.analyze;

import hello.models.Bracket;

import java.util.*;

public class MathAnalyzer {
    public Bracket testBrackets(String s){
        String test = s.replaceAll("[^(){}]", "");
        List<String> as = Arrays.asList(test.split(""));
        Stack<String> stack = new Stack<>();
        Bracket bracket = new Bracket();
        bracket.setTest(test);
        try{
            for (String p : as) {
                if (Objects.equals(p, "(") || Objects.equals(p,"{")) {
                    stack.push(p);
                }
                if (Objects.equals(p, ")")) {
                    if (!stack.pop().equals("(")) {
                        System.out.println("INCORRECT");
                        bracket.setResult("INCORRECT");
                        return bracket;
                    }
                }
                if (Objects.equals(p, "}")) {
                    if (!stack.pop().equals("{")) {
                        System.out.println("INCORRECT");
                        bracket.setResult("INCORRECT");
                        return bracket;
                    }
                }
            }}
        catch (EmptyStackException e){
            System.out.println("INCORRECT");
            bracket.setResult("INCORRECT");
            return bracket;
        }
        System.out.println("CORRECT");
        bracket.setResult("CORRECT");
        return bracket;
    }
}
