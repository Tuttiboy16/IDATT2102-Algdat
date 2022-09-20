abstract class HashFunction {}

class Linear extends HashFunction {
    private int[] hashtable;

    public Linear(int m) {
        this.hashtable = new int[m];
    }

    public int hash(int k) {
        return Math.abs(k % this.hashtable.length);
    }

    public int probe(int h, int i) {
        return (h + i) % this.hashtable.length;
    }

    public int insert(int number, int index) {
        int collisions = 0;
        if (this.hashtable[index] != 0) {
            collisions++;
            int posHash = hash(number);

            int i = 1;
            int move;
            do {
                move = probe(posHash, i);
                if (this.hashtable[move] == 0) {
                    this.hashtable[move] = number;
                    break;
                }
                i++;
                collisions++;
            } while(this.hashtable[move] != 0);
        } else {
            this.hashtable[index] = number;
        }
        return collisions;
    }
}

class Square extends HashFunction {
    private int[] hashtable;

    public Square(int m) {
        this.hashtable = new int[m];
    }

    public int hash(int k) {
        return Math.abs(k % this.hashtable.length);
    }

    public int probe(int h, int i) {
        return Math.abs((h + i + (i * i)) % this.hashtable.length);
    }

    public int insert(int number, int index) {
        int collisions = 0;
        if (this.hashtable[index] != 0) {
            collisions++;
            int posHash = hash(number);

            int i = 1;
            int move;
            do {
                move = probe(posHash, i);
                if (this.hashtable[move] == 0) {
                    this.hashtable[move] = number;
                    break;
                }
                i++;
                collisions++;
            } while(this.hashtable[move] != 0);
        } else {
            this.hashtable[index] = number;
        }
        return collisions;
    }
}

class Dubble extends HashFunction {
    private int[] hashtable;

    public Dubble(int m) {
        this.hashtable = new int[m];
    }

    public int hash1(int k) {
        return Math.abs(k % this.hashtable.length);
    }

    public int hash2(int k) {
        return Math.abs(1 + (k % (this.hashtable.length - 1)));
    }

    public int probe(int pos, int h2, int i) {
        return Math.abs((pos + (i * h2)) % this.hashtable.length);
    }

    public int insert(int number, int index) {
        int collision = 0;
        if (this.hashtable[index] != 0) {
            collision++;
            int posHash2 = hash2(number);

            int i = 1;
            int move;
            do {
                move = probe(index, posHash2, i);
                if (this.hashtable[move] == 0) {
                    this.hashtable[move] = number;
                    break;
                }
                i++;
                collision++;
            } while(this.hashtable[move] != 0);
        } else {
            this.hashtable[index] = number;
        }
        return collision;
    }
}