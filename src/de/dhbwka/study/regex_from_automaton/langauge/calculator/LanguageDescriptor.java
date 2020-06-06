package de.dhbwka.study.regex_from_automaton.langauge.calculator;

import de.dhbwka.study.regex_from_automaton.*;
import de.dhbwka.study.regex_from_automaton.langauge.*;

import java.util.*;

public class LanguageDescriptor {
    
    private final Map<Pair<String, String>, String> transitions;
    private final List<String> stateNames;
    private final int startIndex;
    private final int[] acceptedIndices;
    
    public LanguageDescriptor(Map<Pair<String, String>, String> transitions, List<String> stateNames, int startIndex, int... acceptedIndices) {
        this.transitions = transitions;
        this.stateNames = stateNames;
        this.startIndex = startIndex;
        this.acceptedIndices = acceptedIndices;
    }
    
    
    public FormalLanguage formaleSpracheBerechnen() {
        FormalLanguage f = null;
        for(int acceptedIndex : acceptedIndices) {
            final FormalLanguage formalLanguage = calculateLanguageRegex(startIndex, acceptedIndex, stateNames.size());
            f = f == null ? formalLanguage : f.union(formalLanguage);
        }
        
        return Objects.requireNonNull(f);
    }
    
    public FormalLanguage calculateLanguageRegex(int i, int j, int k) {
        if(k == 0) {
            return calculateK0(i, j);
        }
        
        return calculateLanguageRegex(i, j, k - 1).union(calculateLanguageRegex(i, k - 1, k - 1).concatenation(calculateLanguageRegex(k - 1, k - 1, k - 1).finishedConcatenation()).concatenation(calculateLanguageRegex(k - 1, j, k - 1)));
    }
    
    private FormalLanguage calculateK0(int i, int j) {
        final FormalLanguageFromLetters alphabet = new FormalLanguageFromLetters();
    
        transitions.entrySet().stream()
                .filter(e -> Objects.equals(e.getKey().getA(), stateNames.get(i)))
                .filter(e -> Objects.equals(e.getValue(), stateNames.get(j)))
                .map(e -> e.getKey().getB())
                .forEach(alphabet::addLetter);
    
        if(i == j) {
            alphabet.addLetter(FormalLanguage.EPSILON);
        }
        
        return alphabet;
    }
}
