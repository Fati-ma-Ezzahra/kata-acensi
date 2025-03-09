package com.prisonersdilemma.entities;

import com.prisonersdilemma.enums.ChoiceEnum;

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
}
