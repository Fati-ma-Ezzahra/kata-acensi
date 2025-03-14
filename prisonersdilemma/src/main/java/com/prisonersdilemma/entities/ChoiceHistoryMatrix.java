package com.prisonersdilemma.entities;

import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;

import java.util.ArrayList;
import java.util.List;
//diviser en trois interface differente : historique , resultat, choix
public class ChoiceHistoryMatrix {
    private final List<List<ChoiceEnum>> suspectChoiceMatrix = new ArrayList<>();

    public static ChoiceHistoryMatrix initWithSuspects(int suspectCount) {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        for (int i = 0; i < suspectCount; i++) {
            choiceHistoryMatrix.addSuspect();
        }
        return choiceHistoryMatrix;
    }

    public void addSuspect(){
        suspectChoiceMatrix.add(new ArrayList<>());
    }
    public void saveChoice(int suspect, ChoiceEnum choice){
        suspectChoiceMatrix.get(suspect).add(choice);
    }

    public List<List<ChoiceEnum>> getSuspectChoiceMatrix() {
        return suspectChoiceMatrix;
    }
    public void validateMatrix() throws InvalidChoiceHistory {
        if (suspectChoiceMatrix.size() != 2)
            throw InvalidChoiceHistory.invalidNumberOfSuspectsInMatrix();
        if (suspectChoiceMatrix.getFirst().size() != suspectChoiceMatrix.getLast().size())
            throw InvalidChoiceHistory.suspectHistorySizeMissMatch();
    }
}
