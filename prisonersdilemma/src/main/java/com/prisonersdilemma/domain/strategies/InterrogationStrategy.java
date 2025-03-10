package com.prisonersdilemma.domain.strategies;

import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;

public interface InterrogationStrategy {
    ChoiceEnum getNextChoice(int currentSession, ChoiceHistoryMatrix choiceHistoryMatrix);
}
