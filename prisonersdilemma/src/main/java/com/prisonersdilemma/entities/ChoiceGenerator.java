package com.prisonersdilemma.entities;

import com.prisonersdilemma.enums.ChoiceEnum;
import java.util.Random;

public final class ChoiceGenerator {
    private final Random random = new Random();
    private static final ChoiceGenerator INSTANCE = new ChoiceGenerator();

    private ChoiceGenerator(){}

    public static ChoiceGenerator getInstance() {
        return INSTANCE;
    }

    public ChoiceEnum denounce(){
        return ChoiceEnum.DENOUNCE;
    }
    public ChoiceEnum silence(){
        return ChoiceEnum.SILENCE;
    }
    //no random choice
    public ChoiceEnum random(){
        return ChoiceEnum.values()[random.nextInt(ChoiceEnum.values().length)];
    }
}
