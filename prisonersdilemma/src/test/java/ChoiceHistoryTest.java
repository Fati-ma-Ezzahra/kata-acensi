import com.prisonersdilemma.entities.ChoiceHistory;
import com.prisonersdilemma.entities.Suspect;
import com.prisonersdilemma.enums.ChoiceEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
