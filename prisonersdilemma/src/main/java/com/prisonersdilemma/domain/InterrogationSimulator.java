package com.prisonersdilemma.domain;

import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.entities.Suspect;
import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;
import com.prisonersdilemma.domain.strategies.Suspect1Strategy;
import com.prisonersdilemma.domain.strategies.Suspect2Strategy;

import java.util.List;

public class InterrogationSimulator {
    private ChoiceHistoryMatrix choiceHistoryMatrix;
    private final Suspect suspect1;
    private final Suspect suspect2;
    private int currentSession;
    public InterrogationSimulator() {
        this.choiceHistoryMatrix = ChoiceHistoryMatrix.initWithSuspects(2);
        this.suspect1= new Suspect(new Suspect1Strategy(), choiceHistoryMatrix);
        this.suspect2= new Suspect(new Suspect2Strategy(), choiceHistoryMatrix);
        this.currentSession = 0;
    }
    public List<Integer> start() throws InvalidChoiceHistory {
        reset();
        for (int i=0;i<10;i++){
            nextSession();
        }
        System.out.println("⚖\uFE0F InterrogationSimulator choices: ");
        System.out.println(choiceHistoryMatrix.getSuspectChoiceMatrix());
        return ScoreCalculator.getFinalScore(choiceHistoryMatrix);
    }
    private void nextSession() {
        ChoiceEnum choice1 = suspect1.getNextChoice(currentSession);
        ChoiceEnum choice2 = suspect2.getNextChoice(currentSession);

        choiceHistoryMatrix.saveChoice(0,choice1);
        choiceHistoryMatrix.saveChoice(1,choice2);
        incrementCurrentSession();
    }
    private void reset() {
        this.choiceHistoryMatrix = ChoiceHistoryMatrix.initWithSuspects(2);
        this.currentSession = 0;
    }
    private void incrementCurrentSession() {
        currentSession++;
    }
    public int getCurrentSession() {
        return currentSession;
    }
}
