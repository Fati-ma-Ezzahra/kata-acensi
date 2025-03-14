package domain;

import com.prisonersdilemma.domain.ScoreCalculator;
import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreCalculatorTest {
    @ParameterizedTest
    @CsvSource(value = {
            "DENOUNCE,DENOUNCE,-5,-5",
            "DENOUNCE,SILENCE,0,-10",
            "SILENCE,DENOUNCE,-10,0",
            "SILENCE,SILENCE,-1,-1"
    })
    void givenSingleSessionChoice_whenGetFinalScore_thenGetValidScore(String rawChoice1, String rawChoice2, int score1, int score2) throws InvalidChoiceHistory {
        ChoiceEnum choice1 = ChoiceEnum.valueOf(rawChoice1);
        ChoiceEnum choice2 = ChoiceEnum.valueOf(rawChoice2);

        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.saveChoice(0, choice1);
        choiceHistoryMatrix.saveChoice(1, choice2);

        List<Integer> score = ScoreCalculator.getFinalScore(choiceHistoryMatrix);

        assertEquals(score1, score.get(0));
        assertEquals(score2, score.get(1));
    }
}
