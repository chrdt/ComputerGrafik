package phase1;

import java.util.ArrayList;

public class OldFace {
    private ArrayList<Vertex> vertexes;

    public OldFace(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public ArrayList<Vertex> getVertexes() {
        return this.vertexes;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Vertex vertex : this.getVertexes()) {
            builder.append(vertex.toString());
        }
        return builder.toString();
    }
}