package de.dhbwka.study.regex_from_automaton;

import java.util.*;

public class Starter {
    
    public static void main(String[] args) {
        final List<String> stateNames = Arrays.asList("0", "1", "2");
        final List<String> alphabet = Arrays.asList("a", "b");
        new InputWindow(stateNames, alphabet);
        
        /*
        final Map<Pair<String, String>, String> transitions = new HashMap<>(stateNames.size() * alphabet.size());
        transitions.put(new Pair<>("0", "a"), "0");
        transitions.put(new Pair<>("0", "b"), "1");
        transitions.put(new Pair<>("1", "a"), "1");
        transitions.put(new Pair<>("1", "b"), "1");
    
        
    
        //System.out.println(new FormalLanguageFromLetters("a", "b").finishedConcatenation().toString());
    
    
        final LanguageDescriptor languageDescriptor = new LanguageDescriptor(transitions, stateNames, 0, 1);
    
        System.out.println(languageDescriptor.calculateLanguageRegex(0, 1, 2).simplify());
       
         */
    }
}
