package com.github.hymanme.example;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Data data = new Data();
        data.a = 2;
        data.b = 3;
        data.c = 1;
        Data.calculate(data);
        System.out.println(data.c);
        assertEquals(4, 2 + 2);
    }

    public static class Data {
        public int a;
        public int b;
        public int c;

        public static void calculate(Data data) {
            data = new Data();
            data.c = data.a + data.b;
        }
    }
}