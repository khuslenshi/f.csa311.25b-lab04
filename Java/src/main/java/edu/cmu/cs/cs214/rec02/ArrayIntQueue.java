package edu.cmu.cs.cs214.rec02;

public class ArrayIntQueue implements IntQueue {

    private static final int DEFAULT_CAPACITY = 8;

    private Integer[] elements;
    private int head;
    private int tail;
    private int size;

    public ArrayIntQueue() {
        this.elements = new Integer[DEFAULT_CAPACITY];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    @Override
    public boolean enqueue(Integer value) {
        ensureCapacity(size + 1);
        elements[tail] = value;
        tail = (tail + 1) % elements.length;
        size++;
        return true; // enqueue амжилттай
    }

    @Override
    public Integer dequeue() {
        if (size == 0) {
            return null;
        }
        Integer value = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return value;
    }

    @Override
    public Integer peek() {
        if (size == 0) {
            return null;
        }
        return elements[head];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // ⚠️ Хэрвээ IntQueue интерфэйс дээр clear() байхгүй бол @Override-ийг аваарай.
    // IntQueue дээр clear() байгаа бол @Override хэвээр байж болно.
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[(head + i) % elements.length] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }

    private void ensureCapacity(int capacity) {
        if (elements.length >= capacity) {
            return;
        }

        int newCapacity = elements.length * 2;
        if (newCapacity < capacity) {
            newCapacity = capacity;
        }

        Integer[] newArray = new Integer[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[(head + i) % elements.length];
        }

        elements = newArray;
        head = 0;
        tail = size;
    }
}