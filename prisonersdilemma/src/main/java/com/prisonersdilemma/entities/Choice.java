package com.prisonersdilemma.entities;

import com.prisonersdilemma.enums.ChoiceEnum;

import java.util.Random;

// Singleton
public class Choice {
    private final Random random = new Random();

    public ChoiceEnum denounce(){
        return ChoiceEnum.DENOUNCE;
    }
    public ChoiceEnum silence(){
        return ChoiceEnum.SILENCE;
    }
    public ChoiceEnum randomChoice(){
        return ChoiceEnum.values()[random.nextInt(ChoiceEnum.values().length)];
    }
}
