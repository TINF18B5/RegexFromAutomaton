package de.dhbwka.study.regex_from_automaton.langauge;

import java.util.*;
import java.util.stream.*;

public class FormalLanguageUnion extends FormalLanguage {
    
    private final List<FormalLanguage> languages;
    
    public FormalLanguageUnion(FormalLanguage... languages) {
        this.languages = Arrays.asList(languages);
    }
    
    public FormalLanguageUnion(List<FormalLanguage> languages) {
        this.languages = languages;
    }
    
    @Override
    public boolean containsValues() {
        return languages.stream().anyMatch(FormalLanguage::containsValues);
    }
    
    @Override
    public FormalLanguage simplify() {
        if(!containsValues()) {
            return FormalLanguageEmpty.INSTANCE;
        }
    
        final List<FormalLanguage> collect = languages.stream().filter(FormalLanguage::containsValues).map(FormalLanguage::simplify).collect(Collectors.toList());
        if(collect.size() == 0) {
            return FormalLanguageEmpty.INSTANCE;
        }
        if (collect.size() == 1) {
            return collect.get(0);
        }
    
        return new FormalLanguageUnion(collect);
    }
    
    @Override
    public String toRegexString() {
        return languages.stream().map(FormalLanguage::toRegexString).collect(Collectors.joining(" | ", "(", ")"));
    }
    
    @Override
    public FormalLanguage union(FormalLanguage other) {
        final ArrayList<FormalLanguage> formalLanguages = new ArrayList<>(languages);
        formalLanguages.add(other);
        return new FormalLanguageUnion(formalLanguages);
    }
    
    @Override
    public String toString() {
        return languages.stream().map(FormalLanguage::toString).collect(Collectors.joining(" ⋃ ", "(", ")"));
    }
}
