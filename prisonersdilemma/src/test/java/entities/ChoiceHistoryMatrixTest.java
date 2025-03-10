package entities;

import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChoiceHistoryMatrixTest {
    @Test
    void whenAddSuspect_thenAddSuspectToMatrix() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        assertEquals(2, choiceHistoryMatrix.getSuspectChoiceMatrix().size());
        assertEquals(0, choiceHistoryMatrix.getSuspectChoiceMatrix().getFirst().size());
        assertEquals(0, choiceHistoryMatrix.getSuspectChoiceMatrix().getLast().size());
    }
    @Test
    void whenSuspectMakeChoice_thenAddSuspectChoiceToMatrix() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.saveChoice(0, ChoiceEnum.DENOUNCE);
        assertEquals(1, choiceHistoryMatrix.getSuspectChoiceMatrix().size());
        assertEquals(1, choiceHistoryMatrix.getSuspectChoiceMatrix().getFirst().size());
        assertEquals(ChoiceEnum.DENOUNCE, choiceHistoryMatrix.getSuspectChoiceMatrix().getFirst().getFirst());
    }
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

        List<Integer> score = choiceHistoryMatrix.getFinalScore();

        assertEquals(score1, score.get(0));
        assertEquals(score2, score.get(1));
    }
    @Test
    void givenZeroSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        assertThrows(InvalidChoiceHistory.class, choiceHistoryMatrix::getFinalScore);
    }
    @Test
    void givenOneSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        assertThrows(InvalidChoiceHistory.class, choiceHistoryMatrix::getFinalScore);
    }
    @Test
    void givenTwoSuspects_whenGetFinalScore_thenValidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        assertDoesNotThrow(choiceHistoryMatrix::getFinalScore);
    }
    @Test
    void givenMoreThanTwoSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        assertThrows(InvalidChoiceHistory.class, choiceHistoryMatrix::getFinalScore);
    }
}
