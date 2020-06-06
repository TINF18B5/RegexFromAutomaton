package de.dhbwka.study.regex_from_automaton.langauge;

import java.util.*;
import java.util.stream.*;

public class FormalLanguageFromLetters extends FormalLanguage {
    private final List<String> letters;
    
    public FormalLanguageFromLetters() {
        this.letters = new ArrayList<>();
    }
    
    public FormalLanguageFromLetters(String... letters) {
        this.letters = new ArrayList<>(Arrays.asList(letters));
    }
    
    public void addLetter(String letter) {
        this.letters.add(letter);
    }
    
    public FormalLanguageFromLetters(List<String> letters) {
        this.letters = letters;
    }
    
    @Override
    public boolean containsValues() {
        return !letters.isEmpty();
    }
    
    @Override
    public FormalLanguage simplify() {
        return containsValues() ? this : FormalLanguageEmpty.INSTANCE;
    }
    
    @Override
    public String toString() {
        if(letters.isEmpty()) {
            return FormalLanguageEmpty.INSTANCE.toString();
        }
    
        if(letters.size() == 1)
            return letters.get(0);
        return letters.stream().collect(Collectors.joining(", ", "{", "}"));
    }
    
    @Override
    public String toRegexString() {
        if(letters.isEmpty()) {
            return FormalLanguageEmpty.INSTANCE.toRegexString();
        }
        
        if(letters.size() == 1)
            return letters.get(0);
        
        return letters.stream().map(l -> EPSILON.equals(l) ? "∅⁎": l).collect(Collectors.joining("|", "(", ")"));
    }
    
    @Override
    public FormalLanguage union(FormalLanguage other) {
        if(other instanceof FormalLanguageFromLetters) {
            final FormalLanguageFromLetters result = new FormalLanguageFromLetters(this.letters);
            ((FormalLanguageFromLetters) other).letters.forEach(result::addLetter);
            return result;
        }
        
        return super.union(other);
    }
}
