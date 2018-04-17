package pl.edu.uj.project.view.Util;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class STUTest {
    private STU stu;
    private String element = "element";
    private Long count = 1000L;

    @Before
    public void before() {
        stu = new STU(element, count);
    }

    @Test
    public void getCountReturnStringType() {
        assertThat(stu.getCount()).isEqualTo(count.toString());
        assertThat(stu.getCount().getClass()).isEqualTo(String.class);
    }

    @Test
    public void getElenebtReturnStringType() {
        assertThat(stu.getElement()).isEqualTo(element);
        assertThat(stu.getElement().getClass()).isEqualTo(String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateSTUWithElementNullThenThrowIAE() {
        new STU(null, count);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateSTUWithCountNullThenThrowIAE() {
        new STU(null, count);
    }
}
