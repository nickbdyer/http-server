package uk.nickbdyer.httpserver.filemanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RangeFinderTest {

    @Test
    public void aRangeFinderCanCalculateAnUpperAndLowerBoundFromDefinedRange() {
        RangeFinder rangeFinder = new RangeFinder("bytes=0-4", 10);

        assertEquals(0, rangeFinder.getLowerBound());
        assertEquals(4, rangeFinder.getUpperBound());
    }

    @Test
    public void aRangeFinderCanCalculateAnotherUpperAndLowerBoundFromDefinedRange() {
        RangeFinder rangeFinder = new RangeFinder("bytes=33-89", 100);

        assertEquals(33, rangeFinder.getLowerBound());
        assertEquals(89, rangeFinder.getUpperBound());
    }
}
