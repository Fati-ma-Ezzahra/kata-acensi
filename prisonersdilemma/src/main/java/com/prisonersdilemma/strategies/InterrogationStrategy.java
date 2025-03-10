package com.prisonersdilemma.strategies;

import com.prisonersdilemma.entities.ChoiceHistory;
import com.prisonersdilemma.enums.ChoiceEnum;

public interface InterrogationStrategy {
    ChoiceEnum getNextChoice(int currentSession, ChoiceHistory choiceHistory);
}
