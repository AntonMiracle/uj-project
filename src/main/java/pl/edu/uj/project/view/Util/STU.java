package pl.edu.uj.project.view.Util;

public class STU {
    private String element;
    private Long count;

    public STU(String element, Long count) {
        this.element = element;
        this.count = count;
    }

    public String getElement() {
        return element;
    }

    public String getCount() {
        return count.toString();
    }
}
