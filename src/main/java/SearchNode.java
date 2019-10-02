public class SearchNode {
    long name;
    long sourceName;
    double distance;
    SearchNode parent;

    SearchNode(long curent, SearchNode p, double dist) {
        name = curent;
        parent = p;
        distance = dist;
        if (p != null) {
            sourceName = parent.name;
        }
    }
}
