package com.prisonersdilemma.domain;

import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;

import java.util.ArrayList;
import java.util.List;

public class ScoreCalculator {
    public static List<Integer> getFinalScore(ChoiceHistoryMatrix choiceHistoryMatrix) throws InvalidChoiceHistory {
        choiceHistoryMatrix.validateMatrix();
        List<List<ChoiceEnum>> suspectChoiceMatrix = choiceHistoryMatrix.getSuspectChoiceMatrix();

        ArrayList<Integer> finalScore = new ArrayList<>(List.of(0, 0));
        for(int i = 0; i < suspectChoiceMatrix.getFirst().size(); i++){
            ChoiceEnum suspect1Choice = suspectChoiceMatrix.getFirst().get(i);
            ChoiceEnum suspect2Choice = suspectChoiceMatrix.getLast().get(i);
            List<Integer> sessionScore = getSessionScore(suspect1Choice, suspect2Choice);

            finalScore.set(0, finalScore.get(0) + sessionScore.get(0));
            finalScore.set(1, finalScore.get(1) + sessionScore.get(1));
        }
        return finalScore;
    }
    private static List<Integer> getSessionScore(ChoiceEnum firstSuspectChoice, ChoiceEnum secondSuspectChoice) {
        if(firstSuspectChoice == ChoiceEnum.DENOUNCE){
            if(secondSuspectChoice == ChoiceEnum.DENOUNCE){ return List.of(-5,-5);}
            else if (secondSuspectChoice == ChoiceEnum.SILENCE){ return List.of(0,-10);}
        }
        else {
            if(secondSuspectChoice == ChoiceEnum.DENOUNCE){ return List.of(-10,0);}
            else return List.of(-1,-1);
        }
        return List.of(0, 0);
    }

}
