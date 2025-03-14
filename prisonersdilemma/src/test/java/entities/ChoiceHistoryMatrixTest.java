package entities;

import com.prisonersdilemma.domain.ScoreCalculator;
import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Test
    void givenZeroSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        assertThrows(
                InvalidChoiceHistory.class,
                () -> ScoreCalculator.getFinalScore(choiceHistoryMatrix));
    }
    @Test
    void givenOneSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        assertThrows(InvalidChoiceHistory.class,
                choiceHistoryMatrix::validateMatrix);
    }
    @Test
    void givenTwoSuspects_whenGetFinalScore_thenValidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        assertDoesNotThrow(choiceHistoryMatrix::validateMatrix);
    }
    @Test
    void givenMoreThanTwoSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistoryMatrix choiceHistoryMatrix = new ChoiceHistoryMatrix();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        choiceHistoryMatrix.addSuspect();
        assertThrows(InvalidChoiceHistory.class,
                choiceHistoryMatrix::validateMatrix);
    }
}
