package domain.strategies;

import com.prisonersdilemma.domain.strategies.Suspect2Strategy;
import com.prisonersdilemma.entities.Choice;
import com.prisonersdilemma.entities.ChoiceHistoryMatrix;
import com.prisonersdilemma.enums.ChoiceEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Suspect2StrategyTest {

    @InjectMocks
    private Suspect2Strategy suspect2Strategy;

    @Mock
    private Choice choice;

    @Mock
    private ChoiceHistoryMatrix choiceHistoryMatrix;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(suspect2Strategy, "choice", choice);
    }

    @Test
    void givenFirstSession_whenGetNextChoice_ThenReturnSilence() {
        ChoiceEnum mockChoice = ChoiceEnum.SILENCE; // getFirstChoice() should return SILENCE
        when(choice.silence()).thenReturn(mockChoice);

        ChoiceEnum result = suspect2Strategy.getNextChoice(0, choiceHistoryMatrix);

        assertEquals(mockChoice, result);
        verify(choice, times(1)).silence();  // Verifies that silence() was called once
    }

    @Test
    void givenSecondSession_whenGetNextChoice_ThenReturnRandomChoice() {
        ChoiceEnum mockChoice = ChoiceEnum.SILENCE;// random choice is the mocked one
        when(choice.randomChoice()).thenReturn(mockChoice);

        ChoiceEnum result = suspect2Strategy.getNextChoice(1, choiceHistoryMatrix);

        assertEquals(mockChoice, result); // result
        verify(choice, times(1)).randomChoice();
    }

    @Test
    void givenThirdSession_whenGetNextChoice_ThenSuspect1MostFrequentChoice() {
        int currentSession = 3;
        List<List<ChoiceEnum>> choiceMatrix = List.of(
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE),
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE)
        );
        when(choiceHistoryMatrix.getSuspectChoiceMatrix()).thenReturn(choiceMatrix);

        ChoiceEnum result = suspect2Strategy.getNextChoice(currentSession, choiceHistoryMatrix);

        assertEquals(ChoiceEnum.SILENCE, result); // Should return SILENCE because it appears more often than DENOUNCE
        verify(choiceHistoryMatrix, times(1)).getSuspectChoiceMatrix();
    }

    @Test
    void givenThirdSessionWithDenounce_whenGetNextChoice_ThenSuspect1MostFrequentChoice() {
        int currentSession = 3;
        List<List<ChoiceEnum>> choiceMatrix = List.of(
                Arrays.asList(ChoiceEnum.DENOUNCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE,ChoiceEnum.SILENCE),
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE)
        );
        when(choiceHistoryMatrix.getSuspectChoiceMatrix()).thenReturn(choiceMatrix);

        ChoiceEnum result = suspect2Strategy.getNextChoice(currentSession, choiceHistoryMatrix);

        assertEquals(ChoiceEnum.DENOUNCE, result); // Should return DENOUNCE because it appears more often than SILENCE
        verify(choiceHistoryMatrix, times(1)).getSuspectChoiceMatrix();
    }

    @Test
    void givenLargeSessionWithEqualFrequencies_whenGetNextChoice_ThenSilence() {
        int currentSession = 4; // Session number exceeds history size

        List<List<ChoiceEnum>> choiceMatrix = List.of(
                Arrays.asList(ChoiceEnum.DENOUNCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE,ChoiceEnum.SILENCE,ChoiceEnum.DENOUNCE),
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE,ChoiceEnum.SILENCE)
        );
        when(choiceHistoryMatrix.getSuspectChoiceMatrix()).thenReturn(choiceMatrix);

        ChoiceEnum result = suspect2Strategy.getNextChoice(currentSession, choiceHistoryMatrix);

        assertEquals(ChoiceEnum.SILENCE, result);
    }
    @Test
    void givenOtherSessionAndMissMatchedChoiceList_whenGetNextChoice_ThenThrow() {
        int currentSession = 5; // Session number exceeds history size

        List<List<ChoiceEnum>> choiceMatrix = List.of(
                Arrays.asList(ChoiceEnum.DENOUNCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE),
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE)
        );
        when(choiceHistoryMatrix.getSuspectChoiceMatrix()).thenReturn(choiceMatrix);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> {
            suspect2Strategy.getNextChoice(currentSession, choiceHistoryMatrix); // This will access an out-of-bounds index
        });
    }

    @Test
    void givenEmptyList_whenGetChoiceFrequency_thenThrow() {
        ChoiceHistoryMatrix choiceHistoryMatrix = ChoiceHistoryMatrix.initWithSuspects(2);

        assertThrows(IllegalArgumentException.class, () -> suspect2Strategy.getNextChoice(3, choiceHistoryMatrix));
    }

    @Test
    void givenNullList_whenGetChoiceFrequency_thenThrow() {
        assertThrows(NullPointerException.class,
                () -> suspect2Strategy.getNextChoice(3, null)
        );
    }
}

