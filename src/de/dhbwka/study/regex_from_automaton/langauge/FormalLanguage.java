package de.dhbwka.study.regex_from_automaton.langauge;

import java.util.regex.*;

public abstract class FormalLanguage {
    public static final String EPSILON = "ɛ";
    
    public FormalLanguage concatenation(FormalLanguage other) {
        return new FormalLanguageConcatenated(this, other);
    }
    
    public FormalLanguage finishedConcatenation() {
        return new FormalLanguageFinishedConcatenation(this);
    }
    
    public abstract boolean containsValues();
    
    public abstract FormalLanguage simplify();
    
    public FormalLanguage union(FormalLanguage other) {
        return new FormalLanguageUnion(this, other);
    }
    
    public abstract String toString();
    
    public abstract String toRegexString();
    
    public Pattern toRegexPattern() {
        return Pattern.compile(toRegexString());
    }
}
