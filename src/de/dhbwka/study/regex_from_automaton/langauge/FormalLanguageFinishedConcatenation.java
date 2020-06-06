package de.dhbwka.study.regex_from_automaton.langauge;

public class FormalLanguageFinishedConcatenation extends FormalLanguage {
    
    private final FormalLanguage s;
    
    public FormalLanguageFinishedConcatenation(FormalLanguage s) {
        this.s = s;
    }
    
    @Override
    public boolean containsValues() {
        return s.containsValues();
    }
    
    @Override
    public String toString() {
        return "(" + s.toString() + ")*";
    }
    
    @Override
    public String toRegexString() {
        return "(" + s.toRegexString() + ")⁎";
    }
    
    @Override
    public FormalLanguage simplify() {
        return new FormalLanguageFinishedConcatenation(s.simplify());
    }
}
