package com.prisonersdilemma.domain.strategies;

import com.prisonersdilemma.entities.ChoiceGenerator;
import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;

import java.util.List;

public class Suspect1Strategy implements InterrogationStrategy {
    private final ChoiceGenerator choiceGenerator = ChoiceGenerator.getInstance();
    private ChoiceEnum getFirstChoice(){
        return choiceGenerator.random();
    }
    private ChoiceEnum getSecondChoice(){
        return choiceGenerator.random();
    }
    private ChoiceEnum getOtherChoices(int currentSession, ChoiceHistoryMatrix choiceHistoryMatrix){
        List<ChoiceEnum> suspect2ChoiceList = choiceHistoryMatrix
                .getSuspectChoiceMatrix()
                .getLast();

        return suspect2ChoiceList.get(currentSession-1);
    }
    @Override
    public ChoiceEnum getNextChoice(int currentSession, ChoiceHistoryMatrix choiceHistoryMatrix) {
        if(currentSession == 0) return getFirstChoice();
        else if(currentSession == 1) return getSecondChoice();
        else return getOtherChoices(currentSession, choiceHistoryMatrix);
    }
}
