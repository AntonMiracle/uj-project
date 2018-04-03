package pl.edu.uj.project.controler;

public class StatisticUnit {
    private String element;
    private Long count;

    public StatisticUnit(String element, Long count) {
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
