import com.prisonersdilemma.entities.ChoiceHistory;
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
public class ChoiceHistoryTest {
    @Test
    void whenAddSuspect_thenAddSuspectToMatrix() {
        ChoiceHistory choiceHistory = new ChoiceHistory();
        choiceHistory.addSuspect();
        choiceHistory.addSuspect();
        assertEquals(2, choiceHistory.getSuspectChoiceMatrix().size());
        assertEquals(0, choiceHistory.getSuspectChoiceMatrix().getFirst().size());
        assertEquals(0, choiceHistory.getSuspectChoiceMatrix().getLast().size());
    }
    @Test
    void whenSuspectMakeChoice_thenAddSuspectChoiceToMatrix() {
        ChoiceHistory choiceHistory = new ChoiceHistory();
        choiceHistory.addSuspect();
        choiceHistory.saveChoice(0, ChoiceEnum.DENOUNCE);
        assertEquals(1, choiceHistory.getSuspectChoiceMatrix().size());
        assertEquals(1, choiceHistory.getSuspectChoiceMatrix().getFirst().size());
        assertEquals(ChoiceEnum.DENOUNCE, choiceHistory.getSuspectChoiceMatrix().getFirst().getFirst());
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

        ChoiceHistory choiceHistory = new ChoiceHistory();
        choiceHistory.addSuspect();
        choiceHistory.addSuspect();
        choiceHistory.saveChoice(0, choice1);
        choiceHistory.saveChoice(1, choice2);

        List<Integer> score = choiceHistory.getFinalScore();

        assertEquals(score1, score.get(0));
        assertEquals(score2, score.get(1));
    }
    @Test
    void givenZeroSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistory choiceHistory = new ChoiceHistory();
        assertThrows(InvalidChoiceHistory.class, choiceHistory::getFinalScore);
    }
    @Test
    void givenOneSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistory choiceHistory = new ChoiceHistory();
        choiceHistory.addSuspect();
        assertThrows(InvalidChoiceHistory.class, choiceHistory::getFinalScore);
    }
    @Test
    void givenTwoSuspects_whenGetFinalScore_thenValidChoiceHistory() {
        ChoiceHistory choiceHistory = new ChoiceHistory();
        choiceHistory.addSuspect();
        choiceHistory.addSuspect();
        assertDoesNotThrow(choiceHistory::getFinalScore);
    }
    @Test
    void givenMoreThanTwoSuspects_whenGetFinalScore_thenThrowInvalidChoiceHistory() {
        ChoiceHistory choiceHistory = new ChoiceHistory();
        choiceHistory.addSuspect();
        choiceHistory.addSuspect();
        choiceHistory.addSuspect();
        assertThrows(InvalidChoiceHistory.class, choiceHistory::getFinalScore);
    }
}
