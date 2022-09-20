import java.util.Arrays;

public class CustomQueue {
    private Object[] tab;
    private int start = 0;
    private int end = 0;
    private int quantity = 0;

    public CustomQueue(int size) {
        this.tab = new Object[size];
    }

    public Object[] getTab() {
        return tab;
    }

    public boolean empty() {
        return quantity == 0;
    }

    public boolean full() {
        return quantity == tab.length;
    }

    public void addToQueue(Object obj) {
        if (full()) {
            return;
        }

        tab[end] = obj;
        end = (end + 1) % tab.length;
        quantity++;
    }

    public Object nextInQueue() {
        if (!empty()) {
            Object obj = tab[start];
            start = (start + 1) % tab.length;
            quantity--;
            return obj;
        } else {
            return null;
        }
    }

    public boolean contains(Object obj) {
        if (!empty()) {
            return Arrays.asList(this.tab).contains(obj);
        } else {
            return false;
        }
    }

    public Object checkQueue() {
        if (!empty()) {
            return this.tab[start];
        } else {
            return null;
        }
    }
}
