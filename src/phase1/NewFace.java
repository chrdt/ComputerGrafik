package phase1;

import java.util.ArrayList;
import java.util.List;

public class NewFace {
    private Edge edge;
    private float[] normal;

    public NewFace(Edge edge) {
        this.edge = edge;
    }

    public float[] getNormal() {
        return normal;
    }

    public void setNormal(float[] normal) {
        this.normal = normal;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Edge getEdge() {
        return this.edge;
    }

    public List<Vertex> getVertexes() {
        List<Vertex> vertexes = new ArrayList<>();
        Edge currentEdge = edge;

        while(!currentEdge.getNext().getStart().equals(edge.getStart())) {
            vertexes.add(currentEdge.getStart());
            currentEdge = currentEdge.getNext();
        }

        return vertexes;
    }
}
