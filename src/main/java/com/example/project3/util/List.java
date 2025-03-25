package com.example.project3.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple generic List implementation using an array.
 * Does not use any Java Collections classes.
 * 
 * @author 
 *   Karthik Penumetch, 
 *   Anirudh Deveram
 */
@SuppressWarnings("unchecked")
public class List<E> implements Iterable<E>
{
    private E[] objects;
    private int size;

    public List()
    {
        objects = (E[]) new Object[4];
        size = 0;
    }

    private int find(E e)
    {
        for (int i = 0; i < size; i++)
        {
            if (objects[i].equals(e))
            {
                return i;
            }
        }
        return -1;
    }

    private void grow()
    {
        E[] newArr = (E[]) new Object[objects.length + 4];
        for (int i = 0; i < objects.length; i++)
        {
            newArr[i] = objects[i];
        }
        objects = newArr;
    }

    public boolean contains(E e)
    {
        return find(e) != -1;
    }

    public void add(E e)
    {
        if (size == objects.length)
        {
            grow();
        }
        objects[size++] = e;
    }

    public void remove(E e)
    {
        int index = indexOf(e);
        if (index != -1)
        {
            for (int i = index; i < size - 1; i++)
            {
                objects[i] = objects[i + 1];
            }
            objects[size - 1] = null;
            size--;
        }
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    public Iterator<E> iterator()
    {
        return new ListIterator();
    }

    public E get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        return objects[index];
    }

    public void set(int index, E e)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException();
        }
        objects[index] = e;
    }

    public int indexOf(E e)
    {
        return find(e);
    }

    private class ListIterator implements Iterator<E>
    {
        int current = 0;

        public boolean hasNext()
        {
            return current < size;
        }

        public E next()
        {
            if (!hasNext())
            {
                throw new NoSuchElementException();
            }
            return objects[current++];
        }
    }
}
