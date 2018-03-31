package pl.edu.uj.project.controler;

public class ControlerTest {
    /*
    private Controller controler;

    @Before
    public void before() {
        controler = new ControllerImp();
    }

    @Test
    public void whenFileTreeViewNullThenIsReadyReturnFalse() {
        controler.set((FileTreeView) null);
        assertThat(controler.isReady()).isFalse();
    }

    @Test
    public void whenToggleGroupNullThenIsReadyReturnFalse() {
        controler.set((ToggleGroup) null);
        assertThat(controler.isReady()).isFalse();
    }

    @Test
    public void whenTextFieldNullThenIsReadyReturnFalse() {
        controler.set((TextField) null);
        assertThat(controler.isReady()).isFalse();
    }

    @Test
    public void whenStatisticLabelNullThenIsReadyReturnFalse() {
        controler.set((Label) null);
        assertThat(controler.isReady()).isFalse();
    }

    @Test
    public void whenSpinnerDepthNullThenIsReadyReturnFalse() {
        controler.set((Spinner<Integer>) null);
        assertThat(controler.isReady()).isFalse();
    }

    @Test
    public void whenGetButtonNullThenIsReadyReturnFalse() {
        controler.set((Button) null);
        assertThat(controler.isReady()).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInitAndControllerNotReadyThenThrowIAE() {
        controler.init();
    }

    @Test
    public void convertStatisticToStringRepresentation() {
        Map<String, Integer> statistic = new HashMap<>();
        for (int i = 0; i < 5; ++i) {
            Integer value = i * 1000;
            String key = "String" + value.toString();
            statistic.put(key, value);
        }
        String expected = "        WORD : COUNT               WORD : COUNT               WORD : COUNT       " + System.lineSeparator() +
                "     String0 : 0             String3000 : 3000          String2000 : 2000        " + System.lineSeparator() +
                "  String4000 : 4000          String1000 : 1000        ";
        assertThat(controler.buildTable(statistic, 3, FileObserver.Element.WORDS)).isEqualTo(expected);
    }
*/
}