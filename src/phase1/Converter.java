package phase1;

import java.util.ArrayList;
import java.util.Hashtable;

public class Converter {

    private Hashtable<NewFace, Edge> newFaces = new Hashtable();
    private ArrayList<Edge> edges = new ArrayList();
    private ArrayList<Edge> noTwinYet = new ArrayList();
    private NewFace currentNewFace = null;

    public Hashtable<NewFace, Edge> getNewFaces() {
        return newFaces;
    }

    public ArrayList<Edge> parseEdges(ArrayList<OldFace> oldFaces) {

        for(int i = 0; i < oldFaces.size(); ++i) {
            OldFace currentOldFace = oldFaces.get(i);
            int length = currentOldFace.getVertexes().size();

            for(int j = 0; j < length; ++j) {
                Edge edge = new Edge(currentOldFace.getVertexes().get(j));
                edge.setNext(currentOldFace.getVertexes().get(nextEdge(j, length)));
                edge.setPrev(currentOldFace.getVertexes().get(previousEdge(j, length)));

                if(j == 0) {
                    currentNewFace = new NewFace(edge);
                    newFaces.put(currentNewFace, edge);
                }

                edge.setNewFace(currentNewFace);
                noTwinYet.add(edge);
                checkForTwin(edge, noTwinYet);
                edges.add(edge);
            }
        }

        return edges;
    }

    private void checkForTwin(Edge edge, ArrayList<Edge> notwin) {
        try {
            notwin.stream().forEach( x -> {
                if (edge.getStart().equals(x.getNext()) && edge.getNext().equals(x.getStart())) {
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