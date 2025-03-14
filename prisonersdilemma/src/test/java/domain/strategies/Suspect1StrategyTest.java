package domain.strategies;

import com.prisonersdilemma.domain.strategies.Suspect1Strategy;
import com.prisonersdilemma.entities.ChoiceGenerator;
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
class Suspect1StrategyTest {

    @InjectMocks
    private Suspect1Strategy suspect1Strategy;

    @Mock
    private ChoiceGenerator choiceGenerator;

    @Mock
    private ChoiceHistoryMatrix choiceHistoryMatrix;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(suspect1Strategy, "choiceGenerator", choiceGenerator);
    }

    @Test
    void givenFirstSession_whenGetNextChoice_ThenReturnRandomChoice() {
        // Arrange
        ChoiceEnum mockChoice = ChoiceEnum.SILENCE; // Assuming SILENCE is part of ChoiceEnum
        when(choiceGenerator.random()).thenReturn(mockChoice);

        // Act
        ChoiceEnum result = suspect1Strategy.getNextChoice(0, choiceHistoryMatrix);

        // Assert
        assertEquals(mockChoice, result);
        verify(choiceGenerator, times(1)).random();  // Verifies that random was called once
    }
    // il faut connaitre le resultat aussi == Mok du random

    @Test
    void givenSecondSession_whenGetNextChoice_ThenReturnRandomChoice() {
        ChoiceEnum mockChoice = ChoiceEnum.DENOUNCE;
        when(choiceGenerator.random()).thenReturn(mockChoice);

        ChoiceEnum result = suspect1Strategy.getNextChoice(1, choiceHistoryMatrix);

        assertEquals(mockChoice, result);
        verify(choiceGenerator, times(1)).random();  // Verifies that random was called once
    }

    @Test
    void givenThirdSession_whenGetNextChoice_ThenReturnSuspect2LastChoice() {
        int currentSession = 2; // session starts at 0
        List<List<ChoiceEnum>> choiceMatrix = List.of(
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.SILENCE, ChoiceEnum.SILENCE),
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE)
        );
        when(choiceHistoryMatrix.getSuspectChoiceMatrix()).thenReturn(choiceMatrix);

        ChoiceEnum result = suspect1Strategy.getNextChoice(currentSession, choiceHistoryMatrix);

        assertEquals(ChoiceEnum.DENOUNCE, result);  // Should return the $currentSession-1 = 1 choiceGenerator
        verify(choiceHistoryMatrix, times(1)).getSuspectChoiceMatrix();
    }

    @Test
    void givenOtherSessionAndMissMatchedChoiceList_whenGetNextChoice_ThenThrow() {
        // Arrange
        int currentSession = 8;
        List<List<ChoiceEnum>> choiceMatrix = List.of(
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.SILENCE, ChoiceEnum.SILENCE),
                Arrays.asList(ChoiceEnum.SILENCE, ChoiceEnum.DENOUNCE, ChoiceEnum.SILENCE)
        );
        when(choiceHistoryMatrix.getSuspectChoiceMatrix()).thenReturn(choiceMatrix);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> {
            suspect1Strategy.getNextChoice(currentSession, choiceHistoryMatrix);  // Accessing index 3 (4th element) when there are only 3
        });
    }
}

