package com.prisonersdilemma.domain.strategies;

import com.prisonersdilemma.entities.Choice;
import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Suspect2Strategy implements InterrogationStrategy {
    private final Choice choice = new Choice();
    private ChoiceEnum getFirstChoice(){
        return choice.silence();
    }
    private ChoiceEnum getSecondChoice(){
        return choice.randomChoice();
    }
    private ChoiceEnum getOtherChoices(int currentSession, ChoiceHistoryMatrix choiceHistoryMatrix){
        Map<ChoiceEnum, Integer> choiceFrequency = getChoiceFrequency(
                currentSession,
                choiceHistoryMatrix.getSuspectChoiceMatrix().getFirst()
        );
        int silenceFrequency = choiceFrequency.get(ChoiceEnum.SILENCE);
        int denounceFrequency = choiceFrequency.get(ChoiceEnum.DENOUNCE);
        if ( silenceFrequency>=denounceFrequency ){ return ChoiceEnum.SILENCE; }
        else { return ChoiceEnum.DENOUNCE; }

    }
    private Map<ChoiceEnum, Integer> getChoiceFrequency(int currentSession, List<ChoiceEnum> list){
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List is empty or null");
        }

        Map<ChoiceEnum, Integer> frequencyMap = new HashMap<>(){{
            this.put(ChoiceEnum.SILENCE, 0);
            this.put(ChoiceEnum.DENOUNCE,0);
        }};

        for (int i=0; i<currentSession; i++) {
            frequencyMap.merge(list.get(i), 1, Integer::sum);
        }

        return frequencyMap;
    }
    @Override
    public ChoiceEnum getNextChoice(int currentSession, ChoiceHistoryMatrix choiceHistoryMatrix) {
        if(currentSession == 0) return getFirstChoice();
        else if(currentSession == 1) return getSecondChoice();
        else return getOtherChoices(currentSession, choiceHistoryMatrix);
    }
}
