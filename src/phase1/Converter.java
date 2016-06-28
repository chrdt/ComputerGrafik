package phase1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Converter {

    private List<NewFace> newFaces = new ArrayList<>();
    private List<Edge> edges = new ArrayList();
    private List<Edge> noTwinYet = new ArrayList();
    private NewFace currentNewFace = null;

    public List<NewFace> getNewFaces() {
        return newFaces;
    }

    public List<Edge> parseEdges(ArrayList<OldFace> oldFaces) {

        for (OldFace currentOldFace : oldFaces) {
            int length = currentOldFace.getVertexes().size();
            Edge[] currentEdges = new Edge[length];

            for (int j = 0; j < length; ++j) {
                Edge edge = new Edge(currentOldFace.getVertexes().get(j));
                //edge.setNext(currentOldFace.getVertexes().get(nextEdge(j, length)));
                //edge.setPrev(currentOldFace.getVertexes().get(previousEdge(j, length)));

                if (j == 0) {
                    currentNewFace = new NewFace(edge);
                    newFaces.add(currentNewFace);
                }

                edge.setNewFace(currentNewFace);

                noTwinYet.add(edge);
                checkForTwin(edge, noTwinYet);

                currentEdges[j] = edge;
                edges.add(edge);
            }

            for (int k = 0; k < currentEdges.length; k++) {
                currentEdges[k].setNext(currentEdges[nextEdge(k, length)]);
                currentEdges[k].setPrev(currentEdges[previousEdge(k, length)]);
            }
        }

        return edges;
    }

    private void checkForTwin(Edge edge, List<Edge> notwin) {
        try {
            notwin.stream().forEach( x -> {
                if (edge.getStart().equals(x.getNext().getStart()) && edge.getNext().getStart().equals(x.getStart())) {
                    edge.setTwin(x);
                    x.setTwin(edge);
                    notwin.remove(edge);
                    notwin.remove(x);
                    return;
                }
            });
        } catch (NullPointerException e) { return; }
    }

    private int previousEdge(int j, int edgecount) {
        return j == 0 ? edgecount -1 : j-1;
    }

    private int nextEdge(int j, int edgecount) {
        return j + 1 == edgecount ? 0 : j+1;
    }
}