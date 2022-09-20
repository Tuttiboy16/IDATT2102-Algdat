
import java.io.*;
import java.util.*;

public class MapData {
    private static final int POSITIVE_INFINITY = 1000000000;
    private int nrOfNodes;
    private int nrOfEdges;
    private Integer[] prev;
    private Node[] nodeList;

    private int[] landmarks = {2151398, 1236417, 3225427}; // {Nordkapp, Lindesnes fyr, Åre}
    List<int[]> fromLandMark = new ArrayList<>();
    List<int[]> toLandMark = new ArrayList<>();

    public MapData() {}

    public int getNrOfNodes() {
        return nrOfNodes;
    }

    public int getNrOfEdges() {
        return nrOfEdges;
    }

    public Node[] getNodeList() {
        return nodeList;
    }

    public boolean alt(int start, int target) {
        int degree = nrOfEdges / nrOfNodes;
        MinDHeap<Integer> priorityQueue = new MinDHeap<>(degree, nrOfNodes);
        priorityQueue.insert(start, 0);
        nodeList[start].cost = 0;


        while (!priorityQueue.isEmpty()) {

            int nodeNr = priorityQueue.peekMinKey();
            if(nodeNr == target){
                return true;
            }
            priorityQueue.remove(nodeNr);


            for (Edge edge: nodeList[nodeNr].edges){

                if (!nodeList[edge.to].visited) {
                    int finalNumber = 0;
                    for (int i = 0; i < landmarks.length; i++) {
                        int distanceToTargetFromNodeFL = fromLandMark.get(i)[target] - fromLandMark.get(i)[edge.to];
                        int distanceToTargetFromNodeTL = toLandMark.get(i)[edge.to] - toLandMark.get(i)[target];

                        if (distanceToTargetFromNodeFL < 0) distanceToTargetFromNodeFL = 0;
                        if (distanceToTargetFromNodeTL < 0) distanceToTargetFromNodeTL = 0;

                        if(distanceToTargetFromNodeFL>finalNumber) finalNumber = distanceToTargetFromNodeFL;
                        if(distanceToTargetFromNodeTL>finalNumber) finalNumber = distanceToTargetFromNodeTL;
                    }

                    if(priorityQueue.contains(edge.to)){
                        if (nodeList[edge.to].sumOfValues < finalNumber + nodeList[nodeNr].cost){
                            continue;
                        }
                        else priorityQueue.remove(edge.to);
                    }

                    nodeList[edge.to].distanceToTarget = finalNumber;
                    nodeList[edge.to].visited = true;
                    nodeList[edge.to].sumOfValues = nodeList[nodeNr].cost + nodeList[nodeNr].distanceToTarget;
                    nodeList[edge.to].parent = nodeNr;
                    priorityQueue.insert(edge.to, nodeList[edge.to].cost);

                }
                else{
                    int newCost = nodeList[nodeNr].cost + edge.weight;

                    if(newCost<nodeList[edge.to].cost) {
                        if(priorityQueue.contains(edge.to)) priorityQueue.remove(edge.to);
                        nodeList[edge.to].cost = newCost;
                        nodeList[edge.to].sumOfValues = nodeList[nodeNr].cost + nodeList[nodeNr].distanceToTarget;
                        nodeList[edge.to].parent = nodeNr;
                        priorityQueue.insert(edge.to, nodeList[edge.to].cost);
                    }

                }
            }

        }
        return false;
    }

    public int[] dijkstra(int start) {

        int degree = nrOfEdges / nrOfNodes;
        MinDHeap<Integer> priorityQueue = new MinDHeap<>(degree, nrOfNodes);
        priorityQueue.insert(start, 0);

        int[] dist = new int[nrOfNodes];
        Arrays.fill(dist, POSITIVE_INFINITY);
        dist[start] = 0;

        boolean[] visited = new boolean[nrOfNodes];
        prev = new Integer[nrOfNodes];

        while (!priorityQueue.isEmpty()) {
            int nodeNr = priorityQueue.peekMinKey();

            visited[nodeNr] = true;
            int minValue = priorityQueue.pollMin();

            if (minValue > dist[nodeNr]) continue;

            for (Edge edge : nodeList[nodeNr].edges) {

                if (visited[edge.to]) continue;

                int newDist = dist[nodeNr] + edge.weight;
                if (newDist < dist[edge.to]) {
                    prev[edge.to] = nodeNr;
                    dist[edge.to] = newDist;
                    nodeList[edge.to].parent = nodeNr;

                    if (!priorityQueue.contains(edge.to)) priorityQueue.insert(edge.to, newDist);
                    else priorityQueue.decrease(edge.to, newDist);
                }
            }
        }

        return dist;
    }

    public List<Node> dijkstraDistStations(int startPlace) {
        List<Node> stationList = new ArrayList<>();
        int[] cost = dijkstra(startPlace);
        List<Node> nodeArr = new ArrayList<>(Arrays.asList(nodeList));
        for (int i = 0; i < nrOfNodes; i++) {
            nodeArr.get(i).cost = cost[i];
        }
        nodeArr.sort(Comparator.comparing(Node::getCost));
        for (Node station : nodeArr) {
            if (station.code == 2 || station.code == 4) {
                stationList.add(station);
            }
            if (stationList.size() == 10) break;
        }
        return stationList;
    }

    public void readPreProcessedFileFromLandmark(InputStream inputStream) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringTokenizer st;
        int[] arr0 = new int[nrOfNodes];
        int[] arr1 = new int[nrOfNodes];
        int[] arr2 = new int[nrOfNodes];
        int index = 0;
        while ((line = br.readLine()) != null) {
            st = new StringTokenizer(line, ",");
            while (st.hasMoreTokens() && st.countTokens() == 3) {
                arr0[index] = Integer.parseInt(st.nextToken());
                arr1[index] = Integer.parseInt(st.nextToken());
                arr2[index] = Integer.parseInt(st.nextToken());
            }
            index++;
        }
        fromLandMark.add(0, arr0);
        fromLandMark.add(1, arr1);
        fromLandMark.add(2, arr2);
    }

    public void readPreProcessedFileToLandmark(InputStream inputStream) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringTokenizer st;
        int[] arr0 = new int[nrOfNodes];
        int[] arr1 = new int[nrOfNodes];
        int[] arr2 = new int[nrOfNodes];
        int index = 0;
        while ((line = br.readLine()) != null) {
            st = new StringTokenizer(line, ",");
            while (st.hasMoreTokens() && st.countTokens() == 3) {
                arr0[index] = Integer.parseInt(st.nextToken());
                arr1[index] = Integer.parseInt(st.nextToken());
                arr2[index] = Integer.parseInt(st.nextToken());
            }//TODO FOERSTE INDEX FORSVINNER
            index++;
        }
        toLandMark.add(0, arr0);
        toLandMark.add(1, arr1);
        toLandMark.add(2, arr2);
    }

    public void writeMapData(int start, int target) throws IOException {
        Node next = nodeList[nodeList[target].parent];

        FileWriter fw = new FileWriter("./test.csv");
        PrintWriter pw = new PrintWriter(fw);

        pw.print("lat;lng;name\n");

        while(next.parent != 0) {
            pw.println(String.format("%s;%s;%s", next.latitude, next.longitude, next.name));
        }

        if(next.equals(nodeList[start])) {
            pw.println(String.format("%s;%s;%s", next.latitude, next.longitude, next.name));
        }

        pw.flush();
        pw.close();
    }

    public void readInterestPoint(InputStream inputStream) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int nrOfIntPoints = Integer.parseInt(st.nextToken());
        for (int i = 0; i < nrOfIntPoints; i++) {
            st = new StringTokenizer(br.readLine());
            int nodeNr = Integer.parseInt(st.nextToken());
            nodeList[nodeNr].setCode(Integer.parseInt(st.nextToken()));
            nodeList[nodeNr].setName(st.nextToken());
        }
    }

    public void nodeInput(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.nrOfNodes = Integer.parseInt(st.nextToken());
        this.nodeList = new Node[nrOfNodes];
        for (int i = 0; i < nrOfNodes; ++i) {
            st = new StringTokenizer(br.readLine());
            int index = Integer.parseInt(st.nextToken());
            double longitude = Double.parseDouble(st.nextToken());
            double latitude = Double.parseDouble(st.nextToken());
            nodeList[index] = new Node(index,latitude,longitude);
        }
    }

    public void edgeInput(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringTokenizer st = new StringTokenizer(br.readLine());
        this.nrOfEdges = Integer.parseInt(st.nextToken());
        for (int i = 0; i < nrOfEdges; ++i) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());
            Edge newEdge = new Edge(from, to, weight);
            nodeList[from].edges.add(newEdge);
        }
    }

    public static class MinDHeap<T extends Comparable<T>> {

        private int size;
        private final int NROFELEMENTS;
        private final int DEGREE;

        private final int[] child, parent;
        public final int[] posMap;
        public final int[] inverseMap;

        public final Object[] val;

        public MinDHeap(int degree, int maxSize) {
            if (maxSize <= 0) throw new IllegalArgumentException("maxSize <= 0");

            DEGREE = Math.max(2, degree);
            NROFELEMENTS = Math.max(DEGREE + 1, maxSize);

            inverseMap = new int[NROFELEMENTS];
            posMap = new int[NROFELEMENTS];
            child = new int[NROFELEMENTS];
            parent = new int[NROFELEMENTS];
            val = new Object[NROFELEMENTS];

            for (int i = 0; i < NROFELEMENTS; i++) {
                parent[i] = (i - 1) / DEGREE;
                child[i] = i * DEGREE + 1;
                posMap[i] = inverseMap[i] = -1;
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean contains(int k) {
            keyInBoundsOrThrow(k);
            return posMap[k] != -1;
        }

        public int peekMinKey() {
            throwIfNotEmpty();
            return inverseMap[0];
        }

        public T peekMin() {
            throwIfNotEmpty();
            return (T) val[inverseMap[0]];
        }

        public T pollMin() {
            T minValue = peekMin();
            remove(peekMinKey());
            return minValue;
        }

        public void insert(int k, T value) {
            if (contains(k)) throw new IllegalArgumentException("index already exists: " + k);
            throwIfValNull(value);
            posMap[k] = size;
            inverseMap[size] = k;
            val[k] = value;
            swim(size++);
        }

        public T remove(int k) {
            throwIfKeyNotExists(k);
            final int i = posMap[k];
            swap(i, --size);
            sink(i);
            swim(i);
            T value = (T) val[k];
            val[k] = null;
            posMap[k] = -1;
            inverseMap[size] = -1;
            return value;
        }

        public void decrease(int k, T value) {
            throwIfKeyValNull(k, value);
            if (lessThan(value, val[k])) {
                val[k] = value;
                swim(posMap[k]);
            }
        }

        private void sink(int i) {
            for (int j = minChild(i); j != -1; ) {
                swap(i, j);
                i = j;
                j = minChild(i);
            }
        }

        private void swim(int i) {
            while (lessThan(i, parent[i])) {
                swap(i, parent[i]);
                i = parent[i];
            }
        }

        private int minChild(int i) {
            int index = -1, from = child[i], to = Math.min(size, from + DEGREE);
            for (int j = from; j < to; j++) if (lessThan(j, i)) index = i = j;
            return index;
        }

        private void swap(int i, int j) {
            posMap[inverseMap[j]] = i;
            posMap[inverseMap[i]] = j;
            int temp = inverseMap[i];
            inverseMap[i] = inverseMap[j];
            inverseMap[j] = temp;
        }

        private boolean lessThan(int i, int j) {
            return ((Comparable<? super T>) val[inverseMap[i]]).compareTo((T) val[inverseMap[j]]) < 0;
        }


        private boolean lessThan(Object obj1, Object obj2) {
            return ((Comparable<? super T>) obj1).compareTo((T) obj2) < 0;
        }

        @Override
        public String toString() {
            List<Integer> lst = new ArrayList<>(size);
            for (int i = 0; i < size; i++) lst.add(inverseMap[i]);
            return lst.toString();
        }

        private void throwIfNotEmpty() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        }

        private void throwIfKeyValNull(int k, Object value) {
            throwIfKeyNotExists(k);
            throwIfValNull(value);
        }

        private void throwIfKeyNotExists(int k) {
            if (!contains(k)) throw new NoSuchElementException("Index does not exist: " + k);
        }

        private void throwIfValNull(Object value) {
            if (value == null) throw new IllegalArgumentException("Value cannot be null");
        }

        private void keyInBoundsOrThrow(int k) {
            if (k < 0 || k >= NROFELEMENTS)
                throw new IllegalArgumentException("Key index out of bounds: " + k);
        }
    }
}