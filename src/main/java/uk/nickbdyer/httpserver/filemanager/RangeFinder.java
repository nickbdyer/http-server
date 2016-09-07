package uk.nickbdyer.httpserver.filemanager;

public class RangeFinder {

    private final int fileSize;
    private final int lowerBound;
    private final int upperBound;

    public RangeFinder(String rangeAndUnit, int fileSize) {
        String range = getRange(rangeAndUnit);
        this.lowerBound = calculateLowerBound(range);
        this.upperBound = calculateUpperBound(range);
        this.fileSize = fileSize;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    private int calculateLowerBound(String range) {
        String value = range.substring(0, range.indexOf('-'));
        return Integer.valueOf(value);
    }

    private int calculateUpperBound(String range) {
        String value = range.substring(range.indexOf('-') + 1);
        return Integer.valueOf(value);
    }

    private String getRange(String range) {
        return range.substring(range.indexOf("=") + 1);
    }

}
