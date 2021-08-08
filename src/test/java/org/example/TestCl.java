package org.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class TestCl {
    @Test
    public void a() {
        byte[] arr = new byte[100000];
        ThreadLocalRandom.current().nextBytes(arr);
        for (int i = 0; i < arr.length; i++) {
            if (ThreadLocalRandom.current().nextInt(0,100) < 25) {
                arr[i] = 0;
            }
        }
    }

}
