package entities;

import com.prisonersdilemma.entities.Choice;
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
public class ChoiceTest {
    @Mock
    Random fakeRandom;
    Choice choice = new Choice();
    @Test
    public void givenSuspect_whenDenounce_thenDenounce() {
        assertEquals(ChoiceEnum.DENOUNCE, choice.denounce());
    }
    @Test
    public void givenSuspect_whenSilence_thenSilence() {
        assertEquals(ChoiceEnum.SILENCE, choice.silence());
    }
    @Test
    public void givenSuspect_whenRandomEqualsZero_thenDenounce() {
        ReflectionTestUtils.setField(choice, "random", fakeRandom);
        when(fakeRandom.nextInt(2)).thenReturn(0);
        assertEquals(ChoiceEnum.DENOUNCE, choice.randomChoice());
    }
    @Test
    public void givenSuspect_whenRandomEqualsOne_thenSilence() {
        ReflectionTestUtils.setField(choice, "random", fakeRandom);
        when(fakeRandom.nextInt(2)).thenReturn(1);
        assertEquals(ChoiceEnum.SILENCE, choice.randomChoice());
    }
}
