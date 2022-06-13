package com.example.final_108820044;

import static com.example.final_108820044.MainActivity.charInAnswer;
import static com.example.final_108820044.MainActivity.charInCorrectSpot;
import static com.example.final_108820044.MainActivity.getColorWithInput;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void charInAnswerTest() {
        assertTrue(charInAnswer('t', "test"));
    }

    @Test
    public void charInAnswerTestFalseUpperCase() {
        assertFalse(charInAnswer('T', "test"));
    }

    @Test
    public void charInAnswerTestFalseNotIn() {
        assertFalse(charInAnswer('a', "test"));
    }

    @Test
    public void charInCorrectSpotTest() {
        assertTrue(charInCorrectSpot("test", "test", 0));
    }

    @Test
    public void charInCorrectSpotTestFalse() {
        assertFalse(charInCorrectSpot("temp", "test", 2));
    }

    @Test
    public void getColorWithInputTest() {
        int[] a = {2, 2, 2, 2, 2}, b = {-1, -1, -1, -1, 1}, c = {2, 1, 1, 2, -1};

        assertArrayEquals(a, getColorWithInput("TCASE", "TCASE"));
        assertArrayEquals(b, getColorWithInput("ASKED", "DONOR"));
        assertArrayEquals(c, getColorWithInput("DROOP", "DONOR"));
        assertArrayEquals(a, getColorWithInput("DONOR", "DONOR"));
    }
}