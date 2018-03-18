package pl.edu.project.core.model;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UtilTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwIAEWhenTrue() {
        Util.throwIAE(true, "MSG");
    }

    @Test
    public void throwIAEWithMsg() {
        try {
            Util.throwIAE(true, "MSG");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("MSG");
        }
    }

    @Test
    public void whenThrowIAEWithMsgNullThenMsgIsEmptyString() {
        try {
            Util.throwIAE(true, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIAEWhenNull() {
        Util.throwIAEWhenNull(null);
    }
}