package de.dhbwka.study.regex_from_automaton.langauge;

import java.util.*;
import java.util.stream.*;

public class FormalLanguageConcatenated extends FormalLanguage {
    
    private final List<FormalLanguage> languages;
    
    public FormalLanguageConcatenated(FormalLanguage... languages) {
        this.languages = new ArrayList<>(Arrays.asList(languages));
    }
    
    public FormalLanguageConcatenated(List<FormalLanguage> languages) {
        this.languages = languages;
    }
    
    @Override
    public FormalLanguage concatenation(FormalLanguage other) {
        final ArrayList<FormalLanguage> formalLanguages = new ArrayList<>(languages);
        formalLanguages.add(other);
        return new FormalLanguageConcatenated(formalLanguages);
    }
    
    @Override
    public boolean containsValues() {
        return languages.stream().allMatch(FormalLanguage::containsValues);
    }
    
    @Override
    public FormalLanguage simplify() {
        if(!containsValues())
            return FormalLanguageEmpty.INSTANCE;
    
        final List<FormalLanguage> collect = languages.stream().map(FormalLanguage::simplify).collect(Collectors.toList());
        return new FormalLanguageConcatenated(collect);
    }
    
    @Override
    public String toString() {
        return languages.stream().map(FormalLanguage::toString).collect(Collectors.joining("", "(", ")"));
    }
    
    @Override
    public String toRegexString() {
        return languages.stream().map(FormalLanguage::toRegexString).collect(Collectors.joining(""));
    
    }
}
