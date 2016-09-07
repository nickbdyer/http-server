package uk.nickbdyer.httpserver.filemanager;

public class RangeFinder {

    private final int fileSize;
    private final String lowerBound;
    private final String upperBound;

    public RangeFinder(String rangeAndUnit, int fileSize) {
        String range = getRange(rangeAndUnit);
        this.lowerBound = parseLowerBound(range);
        this.upperBound = parseUpperBound(range);
        this.fileSize = fileSize;
    }

    public int getUpperBound() {
        return calculateUpperBound(upperBound);
    }

    public int getLowerBound() {
        return calculateLowerBound(lowerBound);
    }

    private int calculateLowerBound(String value) {
        if (isDownRange()) {
            return fileSize - Integer.valueOf(upperBound);
        }
        return Integer.valueOf(value);
    }

    private int calculateUpperBound(String value) {
        if (isDownRange() || isUpRange()) {
            return fileSize - 1;
        }
        return Integer.valueOf(value);
    }

    private boolean isDownRange() {
        return "".equals(lowerBound);
    }

    private boolean isUpRange() {
        return "".equals(upperBound);
    }

    private String getRange(String range) {
        return range.substring(range.indexOf("=") + 1);
    }

    private String parseLowerBound(String range) {
        return range.substring(0, range.indexOf('-'));
    }

    private String parseUpperBound(String range) {
        return range.substring(range.indexOf('-') + 1);
    }

}
