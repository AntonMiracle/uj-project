package pl.edu.uj.project.view.Util;

import pl.edu.uj.project.core.Util;

public class STUHead {

    private Long element1;
    private Long element2;
    private Long element3;
    private Long element4;
    private Long element5;

    public STUHead(Long element1, Long element2, Long element3, Long element4, Long element5) {
        checkElements(element1, element2, element3, element4, element5);
        this.element1 = element1;
        this.element2 = element2;
        this.element3 = element3;
        this.element4 = element4;
        this.element5 = element5;
    }

    private void checkElements(Long element1, Long element2, Long element3, Long element4, Long element5) {
        Util.throwIAE(element1 == null || element2 == null || element3 == null || element4 == null || element5 == null
                , this, "Constructor", "Some element is Null");
        Util.throwIAE(element1 < 0 || element2 < 0 || element3 < 0 || element4 < 0 || element5 < 0
                , this, "Constructor", "Some element is negative");

    }

    public String getElement1() {
        return element1.toString();
    }

    public String getElement2() {
        return element2.toString();
    }

    public String getElement3() {
        return element3.toString();
    }

    public String getElement4() {
        return element4.toString();
    }

    public String getElement5() {
        return element5.toString();
    }

    public String[] getFiveElementsVarName() {
        String[] result = new String[]{
                "element1",
                "element2",
                "element3",
                "element4",
                "element5",
        };
        return result;
    }
}
