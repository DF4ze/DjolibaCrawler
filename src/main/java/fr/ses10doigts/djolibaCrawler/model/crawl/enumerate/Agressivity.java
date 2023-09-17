package fr.ses10doigts.djolibaCrawler.model.crawl.enumerate;

public enum Agressivity {
    // @formatter:off
    HARD(0,1),
    MEDIUM(5,10),
    SOFT(5, 20),
    REALLY_SOFT(10,30);
    // @formatter:on

    // Wait in second
    private int	min;
    private int	max;

    private Agressivity(int min, int max) {
	this.min = min;
	this.max = max;
    }

    public int getMin() {
	return min;
    }


    public int getMax() {
	return max;
    }
}
