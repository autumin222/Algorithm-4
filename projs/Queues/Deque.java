import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    int size;
    Node<Item> head, tail;

    private class Node<Item> {
        Item value;
        Node<Item> next;
        Node<Item> prev;

        public Node(Item item) {
            value = item;
            next = null;
            prev = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> cur = head;

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            Item v = cur.value;
            cur = cur.next;
            return v;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // construct an empty deque
    public Deque() {
        size = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add a null item in the deque.");
        Node<Item> newFirst = new Node<>(item);
        if (isEmpty()) {
            head = newFirst;
            tail = newFirst;
            size++;
            return;
        }
        newFirst.next = head;
        head.prev = newFirst;
        size++;
        head = newFirst;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("can not add a null item in the deque.");
        Node<Item> newLast = new Node<>(item);
        if (isEmpty()) {
            head = newLast;
            tail = newLast;
        }
        tail.next = newLast;
        newLast.prev = tail;
        tail = newLast;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("the deque is already empty");
        Item v = head.value;
        head.next.prev = null;
        head = head.next;
        return v;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) throw new java.util.NoSuchElementException("the deque is already empty");
        Item v = tail.value;
        tail.prev.next = null;
        tail = tail.prev;
        return v;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);

        deque.addLast(1);
        deque.addLast(2);
        for (int item: deque) {
            System.out.println(item);
        }
        System.out.println();
        deque.removeFirst();
        deque.removeLast();
        for (int item: deque) {
            System.out.println(item);
        }

    }
}
