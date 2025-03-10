package com.prisonersdilemma.domain.strategies;

import com.prisonersdilemma.entities.Choice;
import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;

import java.util.List;

public class Suspect1Strategy implements InterrogationStrategy {
    private final Choice choice = new Choice();
    private ChoiceEnum getFirstChoice(){
        return choice.randomChoice();
    }
    private ChoiceEnum getSecondChoice(){
        return choice.randomChoice();
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
