package pl.edu.uj.project.view.Util;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class STUHeadTest {
    private STUHead head;
    private Long element1 = 1L;
    private Long element2 = 2L;
    private Long element3 = 3L;
    private Long element4 = 4L;
    private Long element5 = 5L;

    @Before
    public void before() {
        head = new STUHead(element1, element2, element3, element4, element5);
    }

    @Test
    public void getElement1ReturnStringType() {
        assertThat(head.getElement1()).isEqualTo(element1.toString());
        assertThat(head.getElement1().getClass()).isEqualTo(String.class);
    }

    @Test
    public void getElement2ReturnStringType() {
        assertThat(head.getElement2()).isEqualTo(element2.toString());
        assertThat(head.getElement2().getClass()).isEqualTo(String.class);
    }

    @Test
    public void getElement3ReturnStringType() {
        assertThat(head.getElement3()).isEqualTo(element3.toString());
        assertThat(head.getElement3().getClass()).isEqualTo(String.class);
    }

    @Test
    public void getElement4ReturnStringType() {
        assertThat(head.getElement4()).isEqualTo(element4.toString());
        assertThat(head.getElement4().getClass()).isEqualTo(String.class);
    }

    @Test
    public void getElement5ReturnStringType() {
        assertThat(head.getElement5()).isEqualTo(element5.toString());
        assertThat(head.getElement5().getClass()).isEqualTo(String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithNullElementThenThrowIAE() {
        new STUHead(null, element2, element3, element4, element5);
        new STUHead(element1, null, element3, element4, element5);
        new STUHead(element1, element2, null, element4, element5);
        new STUHead(element1, element2, element3, null, element5);
        new STUHead(element1, element2, element3, element4, null);
    }
    @Test(expected = IllegalArgumentException.class)
    public void whenCreateWithNegativeElementThenThrowIAE() {
        new STUHead(-1L, element2, element3, element4, element5);
        new STUHead(element1, -1L, element3, element4, element5);
        new STUHead(element1, element2, -1L, element4, element5);
        new STUHead(element1, element2, element3, -1L, element5);
        new STUHead(element1, element2, element3, element4, -1L);
    }

    @Test
    public void getFiveElementsReturnStringArray() {
        assertThat(head.getFiveElementsVarName().getClass()).isEqualTo(String[].class);

    }

    @Test
    public void getFiveElementsReturnStringArrayWithLengthFive() {
        assertThat(head.getFiveElementsVarName().length).isEqualTo(5);
    }
}