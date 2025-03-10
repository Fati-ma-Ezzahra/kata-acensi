package domain;
import com.prisonersdilemma.domain.Interrogation;
import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.entities.Suspect;
import com.prisonersdilemma.enums.ChoiceEnum;
import com.prisonersdilemma.exceptions.InvalidChoiceHistory;
import com.prisonersdilemma.domain.strategies.Suspect1Strategy;
import com.prisonersdilemma.domain.strategies.Suspect2Strategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InterrogationTest {

    @Mock
    private ChoiceHistoryMatrix choiceHistoryMatrix;

    @Mock
    private Suspect suspect1;

    @Mock
    private Suspect suspect2;

    @InjectMocks
    private Interrogation interrogation;

    @Test
    public void testInitialSessionState() {
        // Test if the initial session state is correct (session should be 0)
        assertEquals(0, interrogation.getCurrentSession());
    }

    @Test
    public void testStartSimulation() throws InvalidChoiceHistory {
        ReflectionTestUtils.setField(interrogation, "suspect1", suspect1);
        ReflectionTestUtils.setField(interrogation, "suspect2", suspect2);
        when(suspect1.getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class)))
                .thenReturn(ChoiceEnum.SILENCE);
        when(suspect2.getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class)))
                .thenReturn(ChoiceEnum.DENOUNCE);

        List<Integer> mockFinalScores = Arrays.asList(-100, 0);
        List<Integer> finalScores = interrogation.startSimulation();

        assertEquals(mockFinalScores, finalScores);
        verify(suspect1, times(10)).getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class)); // Ensure suspect1 makes 10 choices
        verify(suspect2, times(10)).getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class)); // Ensure suspect2 makes 10 choices
    }

    @Test
    public void testMultipleSessions() throws InvalidChoiceHistory {
        // Mock the suspects' choices for each session
        ReflectionTestUtils.setField(interrogation, "suspect1", suspect1);
        ReflectionTestUtils.setField(interrogation, "suspect2", suspect2);
        when(suspect1.getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class)))
                .thenReturn(ChoiceEnum.SILENCE);
        when(suspect2.getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class)))
                .thenReturn(ChoiceEnum.SILENCE);

        List<Integer> mockFinalScores = Arrays.asList(-10, -10);
        for (int i = 0; i < 10; i++) {
            var finalScores = interrogation.startSimulation();
            assertEquals(mockFinalScores, finalScores); // Ensure the session gets reset everytime
        }

        verify(suspect1, times(100)).getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class));
        verify(suspect2, times(100)).getNextChoice(anyInt(), any(ChoiceHistoryMatrix.class));
    }
}
