package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class IntQueueTest {

    private IntQueue mQueue;

    @Before
    public void setUp() {
        mQueue = new ArrayIntQueue();
    }

    @Test
    public void testEmptyInitially() {
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testEnqueuePeekDequeue() {
        assertTrue(mQueue.enqueue(10));
        assertFalse(mQueue.isEmpty());
        assertEquals(Integer.valueOf(10), mQueue.peek());
        assertEquals(Integer.valueOf(10), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testFIFOOrder() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.enqueue(3);

        assertEquals(Integer.valueOf(1), mQueue.dequeue());
        assertEquals(Integer.valueOf(2), mQueue.dequeue());
        assertEquals(Integer.valueOf(3), mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testClear() {
        mQueue.enqueue(1);
        mQueue.enqueue(2);
        mQueue.clear();

        assertTrue(mQueue.isEmpty());
        assertNull(mQueue.peek());
        assertNull(mQueue.dequeue());
    }

    @Test
    public void testResize() {
        for (int i = 0; i < 30; i++) {
            mQueue.enqueue(i);
        }
        for (int i = 0; i < 30; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testWrapAroundAndResize() {
        for (int i = 0; i < 8; i++) {
            mQueue.enqueue(i);
        }
        for (int i = 0; i < 5; i++) {
            mQueue.dequeue();
        }
        for (int i = 8; i < 25; i++) {
            mQueue.enqueue(i);
        }
        for (int i = 5; i < 25; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testContentFromFile() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> expected = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                expected.add(value);
                mQueue.enqueue(value);
            }

            for (Integer value : expected) {
                assertEquals(value, mQueue.dequeue());
            }
            assertTrue(mQueue.isEmpty());
        }
    }
}