package pl.edu.uj.project.core;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UtilTest {

    @Test
    public void getUtilClass() {
        assertThat(new Util()).isNotNull();
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwIAEWhenTrue() {
        Util.throwIAE(true, this, "throwIAEWhenTrue", "MSG");
    }

    @Test
    public void throwIAEWithMsg() {
        try {
            Util.throwIAE(true, this, "throwIAEWithMsg", "MSG");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("UtilTest#throwIAEWithMsg : MSG");
        }
    }

    @Test
    public void whenThrowIAEWithMsgNullThenMsgIsNoMsg() {
        try {
            Util.throwIAE(true, this, "throwIAEWithMsg", null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("UtilTest#throwIAEWithMsg : NO MSG");
        }
    }

    @Test
    public void whenThrowIAEWithMethodNameNullThenInMsgMethodNameNull() {
        try {
            Util.throwIAE(true, this, null, "MSG");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("UtilTest#:METHOD NAME NULL: : MSG");
        }
    }

    @Test
    public void whenThrowIAEWithClassWhereIAENullThenInMsgClassNull() {
        try {
            Util.throwIAE(true, null, "throwIAEWithMsg", "MSG");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(":CLASS NULL:#throwIAEWithMsg : MSG");
        }
    }

    @Test
    public void throwIAEWhenNull() {
        try {
            Util.throwIAEWhenNull(null, this, "methodName");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("UtilTest#methodName : Argument is NULL");
        }
    }

    @Test
    public void whenThrowIAEWhenNullWithMethodNameNullThenInMsgMethodNameNull() {
        try {
            Util.throwIAEWhenNull(null, this, null);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("UtilTest#:METHOD NAME NULL: : Argument is NULL");
        }
    }

    @Test
    public void whenThrowIAEWhenNullWithClassWhereIAENullThenInMsgClassNull() {
        try {
            Util.throwIAEWhenNull(null, null, "methodName");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo(":CLASS NULL:#methodName : Argument is NULL");
        }
    }
}