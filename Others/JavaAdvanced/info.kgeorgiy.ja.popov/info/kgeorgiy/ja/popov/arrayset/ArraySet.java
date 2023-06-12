package info.kgeorgiy.ja.popov.arrayset;

import java.util.*;

public class ArraySet<T> extends AbstractSet<T> implements SortedSet<T> {

    private final List<T> elements;
    private final Comparator<? super T> comp;

    public ArraySet() {
        this(null, null);
    }

    public ArraySet(Collection<T> elements) {
        this(elements, null);
    }

    public ArraySet(Comparator<? super T> comp) {
        this(null, comp);
    }

    public ArraySet(Collection<T> elements, Comparator<? super T> comp) {
        this.elements = new ArrayList<>();
        this.comp = comp;

        if (elements != null && !elements.isEmpty()) {
            TreeSet<T> temp = new TreeSet<>(comp);
            temp.addAll(elements);
            this.elements.addAll(temp);
        }
    }

    public ArraySet(List<T> elements, Comparator<? super T> comp) {
        this.elements = Objects.requireNonNullElseGet(elements, ArrayList::new);
        this.comp = comp;
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public Comparator<? super T> comparator() {
        return comp;
    }

    private SortedSet<T> getSubList(int i, int j) {
        return new ArraySet<>(elements.subList(i, j), comparator());
    }

    @Override
    public SortedSet<T> subSet(T t, T e1) {
        if (comparator() != null && comparator().compare(t, e1) > 0) {
            throw new IllegalArgumentException();
        }
        int i = lowerBound(t), j = lowerBound(e1);
        return getSubList(i, j);
    }

    @Override
    public SortedSet<T> headSet(T t) {
        return getSubList(0, lowerBound(t));
    }

    @Override
    public SortedSet<T> tailSet(T t) {
        return getSubList(lowerBound(t), size());
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements.get(0);
    }

    @Override
    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return elements.get(elements.size() - 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return binarySearch((T) o) >= 0;
    }

    private int binarySearch(T t) {
        return Collections.binarySearch(elements, t, comparator());
    }

    private int lowerBound(T t) {
        int i = binarySearch(t);
        return i >= 0 ? i : -i - 1;
    }
}