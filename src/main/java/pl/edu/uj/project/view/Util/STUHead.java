package pl.edu.uj.project.view.Util;

public class STUHead {

    private Long element1;
    private Long element2;
    private Long element3;
    private Long element4;
    private Long element5;

    public STUHead(Long element1, Long element2, Long element3, Long element4, Long element5) {
        this.element1 = element1;
        this.element2 = element2;
        this.element3 = element3;
        this.element4 = element4;
        this.element5 = element5;
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
