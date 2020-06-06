package de.dhbwka.study.regex_from_automaton;

import de.dhbwka.study.regex_from_automaton.langauge.*;
import de.dhbwka.study.regex_from_automaton.langauge.calculator.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class InputWindow extends JFrame {
    
    
    public InputWindow(List<String> stateNames, List<String> alphabet) {
        super("Application");
        final JComboBox<?> startSelector = new JComboBox<>(stateNames.toArray());
        this.add(startSelector, BorderLayout.NORTH);
        this.add(new Middle(stateNames, alphabet, startSelector), BorderLayout.CENTER);
        
        
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        EventQueue.invokeLater(() -> InputWindow.this.setVisible(true));
    }
    
    
    private static final class Middle extends JPanel {
        
        private static final Insets noInset = new Insets(0, 0, 0, 0);
        //Pair<State, Letter>
        final Map<Pair<String, String>, String> transitions;
        private final Set<Integer> acceptedStates;
        private final List<String> stateNames;
        private final List<String> alphabet;
        private final JComboBox<?> startSelector;
        private final JTextField outputTextLang = new JTextField();
        private final JTextField outputTextRegex = new JTextField();
        private int startIndex;
        
        private Middle(List<String> stateNames, List<String> alphabet, JComboBox<?> startSelector) {
            super(new GridBagLayout());
            
            this.stateNames = stateNames;
            this.alphabet = alphabet;
            this.startSelector = startSelector;
            
            this.startSelector.addActionListener(e -> {
                Middle.this.startIndex = Middle.this.startSelector.getSelectedIndex();
                updateOutput();
            });
            
            this.transitions = new HashMap<>();
            this.acceptedStates = new HashSet<>();
            
            this.startIndex = 0;
            this.acceptedStates.add(stateNames.size() - 1);
            
            for(String stateName : this.stateNames) {
                for(String letter : this.alphabet) {
                    transitions.put(new Pair<>(stateName, letter), stateName);
                }
            }
            
            updateOutput();
            this.resetGrid();
        }
        
        private static GridBagConstraints getConstraints(int row, int column) {
            return getConstraints(row, column, 1);
        }
        
        private static GridBagConstraints getConstraints(int row, int column, int width) {
            return new GridBagConstraints(column, row, width, 1, 0, 0, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, noInset, 5, 5);
        }
        
        private void resetGrid() {
            this.removeAll();
            
            for(int column = 0; column < stateNames.size(); column++) {
                String stateName = stateNames.get(column);
                this.add(new JLabel(stateName), getConstraints(0, column * 2 + 1));
                final JCheckBox checkBox = new JCheckBox("", acceptedStates.contains(column));
                final Integer finalColumn = column;
                checkBox.addActionListener(e -> {
                    if(checkBox.isSelected()) {
                        acceptedStates.add(finalColumn);
                    } else {
                        acceptedStates.remove(finalColumn);
                    }
                    
                    updateOutput();
                    
                });
                this.add(checkBox, getConstraints(0, column * 2 + 2));
            }
            
            for(int row = 0; row < alphabet.size(); row++) {
                String letter = alphabet.get(row);
                this.add(new JLabel(letter), getConstraints(row + 1, 0));
                for(int column = 0; column < stateNames.size(); column++) {
                    String stateName = stateNames.get(column);
                    final JComboBox<?> transition = new JComboBox<>(this.stateNames.toArray());
                    final Pair<String, String> stringPair = new Pair<>(stateName, letter);
                    transition.setSelectedItem(transitions.get(stringPair));
                    transition.addActionListener(e -> Middle.this.updateHandles(stringPair, (String) transition.getSelectedItem()));
                    this.add(transition, getConstraints(row + 1, column * 2 + 1, 2));
                }
            }
            
            this.add(outputTextLang, new GridBagConstraints(0, alphabet.size() + 1, stateNames.size() * 2 + 2, 1, 0, 0, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 5, 5));
            this.add(outputTextRegex, new GridBagConstraints(0, alphabet.size() + 2, stateNames.size() * 2 + 2, 1, 0, 0, GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 5, 5));
            
            this.revalidate();
        }
        
        private void updateHandles(Pair<String, String> pair, String newState) {
            this.transitions.put(pair, newState);
            
            updateOutput();
        }
        
        private void updateOutput() {
            final FormalLanguage simplify = new LanguageDescriptor(this.transitions, this.stateNames, startIndex, acceptedStates.stream().mapToInt(Integer::intValue).toArray()).formaleSpracheBerechnen()
                    .simplify()
                    ;
            this.outputTextLang.setText(simplify.toString());
            this.outputTextRegex.setText(simplify.toRegexString());
            this.revalidate();
        }
        
    }
    
    
}
