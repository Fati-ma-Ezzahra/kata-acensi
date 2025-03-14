package entities;

import com.prisonersdilemma.entities.ChoiceGenerator;
import com.prisonersdilemma.enums.ChoiceEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChoiceGeneratorTest {
    @Mock
    Random fakeRandom;
    ChoiceGenerator choiceGenerator = ChoiceGenerator.getInstance();
    @Test
    public void givenSuspect_whenDenounce_thenDenounce() {
        assertEquals(ChoiceEnum.DENOUNCE, choiceGenerator.denounce());
    }
    @Test
    public void givenSuspect_whenSilence_thenSilence() {
        assertEquals(ChoiceEnum.SILENCE, choiceGenerator.silence());
    }
    @Test
    public void givenSuspect_whenRandomEqualsZero_thenDenounce() {
        ReflectionTestUtils.setField(choiceGenerator, "random", fakeRandom);
        when(fakeRandom.nextInt(2)).thenReturn(0);
        assertEquals(ChoiceEnum.DENOUNCE, choiceGenerator.random());
    }
    @Test
    public void givenSuspect_whenRandomEqualsOne_thenSilence() {
        ReflectionTestUtils.setField(choiceGenerator, "random", fakeRandom);
        when(fakeRandom.nextInt(2)).thenReturn(1);
        assertEquals(ChoiceEnum.SILENCE, choiceGenerator.random());
    }
}
