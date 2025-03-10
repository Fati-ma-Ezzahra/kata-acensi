package com.prisonersdilemma.entities;

import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.domain.strategies.InterrogationStrategy;

public class Suspect {
    private final InterrogationStrategy strategy;
    private final ChoiceHistoryMatrix choiceHistoryMatrix;

    public Suspect(InterrogationStrategy strategy, ChoiceHistoryMatrix choiceHistoryMatrix) {
        this.strategy = strategy;
        this.choiceHistoryMatrix = choiceHistoryMatrix;
    }
    public ChoiceEnum getNextChoice(int currentSession, ChoiceHistoryMatrix choiceHistoryMatrix) {
        return strategy.getNextChoice(currentSession, choiceHistoryMatrix);
    }
}
