import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JavaSyntaxLearningTest {

    @Test
    public void containsTest() {
        String containingDot = "abc.d";
        String notContainingDot = "abcd";

        assertThat(containingDot.contains(".")).isTrue();
        assertThat(notContainingDot.contains(".")).isFalse();
    }
}
