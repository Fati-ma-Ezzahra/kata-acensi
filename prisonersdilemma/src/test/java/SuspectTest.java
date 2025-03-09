import com.prisonersdilemma.entities.Suspect;
import com.prisonersdilemma.enums.ChoiceEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuspectTest {
    @Mock
    Random fakeRandom;
    Suspect suspect = new Suspect();
    @Test
    public void givenSuspect_whenDenounce_thenDenounce() {
        assertEquals(ChoiceEnum.DENOUNCE, suspect.denounce());
    }
    @Test
    public void givenSuspect_whenSilence_thenSilence() {
        assertEquals(ChoiceEnum.SILENCE, suspect.silence());
    }
    @Test
    public void givenSuspect_whenRandomEqualsZero_thenDenounce() {
        ReflectionTestUtils.setField(suspect, "random", fakeRandom);
        when(fakeRandom.nextInt(2)).thenReturn(0);
        assertEquals(ChoiceEnum.DENOUNCE, suspect.randomChoice());
    }
    @Test
    public void givenSuspect_whenRandomEqualsOne_thenSilence() {
        ReflectionTestUtils.setField(suspect, "random", fakeRandom);
        when(fakeRandom.nextInt(2)).thenReturn(1);
        assertEquals(ChoiceEnum.SILENCE, suspect.randomChoice());
    }
}
