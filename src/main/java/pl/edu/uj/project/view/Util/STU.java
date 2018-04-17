package pl.edu.uj.project.view.Util;

import pl.edu.uj.project.core.Util;

public class STU {
    private String element;
    private Long count;

    public STU(String element, Long count) {
        Util.throwIAEWhenNull(element,this,"Constructor");
        Util.throwIAEWhenNull(count,this,"Constructor");
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
