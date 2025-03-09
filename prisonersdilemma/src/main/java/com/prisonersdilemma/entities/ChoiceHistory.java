package com.prisonersdilemma.entities;

import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;

import java.util.ArrayList;
import java.util.List;

public class ChoiceHistory {
    private final List<List<ChoiceEnum>> suspectChoiceMatrix = new ArrayList<>();

    public void addSuspect(){
        suspectChoiceMatrix.add(new ArrayList<>());
    }
    public void saveChoice(int suspect, ChoiceEnum choice){
        suspectChoiceMatrix.get(suspect).add(choice);
    }

    public List<List<ChoiceEnum>> getSuspectChoiceMatrix() {
        return suspectChoiceMatrix;
    }
    public List<Integer> getFinalScore() throws InvalidChoiceHistory {
        validateMatrix();

        ArrayList<Integer> finalScore = new ArrayList<>(List.of(0, 0));
        for(int i = 0; i < suspectChoiceMatrix.getFirst().size(); i++){
            ChoiceEnum suspect1Choice = suspectChoiceMatrix.getFirst().get(i);
            ChoiceEnum suspect2Choice = suspectChoiceMatrix.getLast().get(i);
            List<Integer> interrogationScore = getInterrogationScore(suspect1Choice, suspect2Choice);

            finalScore.set(0, finalScore.get(0) + interrogationScore.get(0));
            finalScore.set(1, finalScore.get(1) + interrogationScore.get(1));
        }
        return finalScore;
    }
    private void validateMatrix() throws InvalidChoiceHistory {
        if (suspectChoiceMatrix.size() != 2)
            throw InvalidChoiceHistory.invalidNumberOfSuspectsInMatrix();
        if (suspectChoiceMatrix.getFirst().size() != suspectChoiceMatrix.getLast().size())
            throw InvalidChoiceHistory.suspectHistorySizeMissMatch();
    }
    private List<Integer> getInterrogationScore(ChoiceEnum firstSuspectChoice, ChoiceEnum secondSuspectChoice) {
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
