package de.dhbwka.study.regex_from_automaton.langauge;

public final class FormalLanguageEmpty extends FormalLanguage {
    
    public static FormalLanguageEmpty INSTANCE = new FormalLanguageEmpty();
    
    private FormalLanguageEmpty() {
    }
    
    @Override
    public boolean containsValues() {
        return false;
    }
    
    @Override
    public FormalLanguage simplify() {
        return this;
    }
    
    @Override
    public String toString() {
        return "{}";
    }
    
    @Override
    public String toRegexString() {
        return "∅";
    }
    
    @Override
    public FormalLanguage concatenation(FormalLanguage other) {
        return this;
    }
    
    @Override
    public FormalLanguage union(FormalLanguage other) {
        return other;
    }
}
