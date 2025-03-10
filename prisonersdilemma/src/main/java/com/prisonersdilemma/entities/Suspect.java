package com.prisonersdilemma.entities;

import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.strategies.InterrogationStrategy;

public class Suspect {
    private final InterrogationStrategy strategy;
    private final ChoiceHistory choiceHistory;

    public Suspect(InterrogationStrategy strategy, ChoiceHistory choiceHistory) {
        this.strategy = strategy;
        this.choiceHistory = choiceHistory;
    }
    public ChoiceEnum getNextChoice(int currentSession, ChoiceHistory choiceHistory) {
        return strategy.getNextChoice(currentSession, choiceHistory);
    }
}
