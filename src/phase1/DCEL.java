package phase1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DCEL {
    private List<NewFace> newFaces = new ArrayList<>();
    private List<Edge> edges;
    private List<Vertex> vertexes;

    private float[] vertexArray;
    private int[] linkedVertices;

    public DCEL(Parser parser, Converter converter) {
        this.setUp(parser, converter);
    }

    public int[] getLinkedVertices() {
        return linkedVertices;
    }

    public void setLinkedVertices(int[] linkedVertices) {
        this.linkedVertices = linkedVertices;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return this.vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public void setVertexArray(float[] vertexArray) { this.vertexArray = vertexArray; }

    public float[] getVertexArray() { return this.vertexArray; }

    public List<NewFace> getNewFaces() {
        return newFaces;
    }

    public void setNewFaces(List<NewFace> newFaces) {
        this.newFaces = newFaces;
    }

    private DCEL setUp(Parser parser, Converter converter) {
        parser.einlesen("src\\resources\\" + getChosenFile());

        this.edges = converter.parseEdges(parser.getFaceArray());
        this.newFaces = converter.getNewFaces();
        this.vertexes = parser.getVertexArray();

        generateVertexArray();
        generateLinkesVertices();
        setFaceNormals();
        setVertexNormals();

        for(Vertex vertex : vertexes) {
            System.out.println(vertex.getNormal()[0] + ";" + vertex.getNormal()[1] + ";" + vertex.getNormal()[2]);
        }

        return this;
    }

    private void setVertexNormals() {
        List<NewFace> adjacentFaces = new ArrayList<>();
        for(Vertex vertex : vertexes) {
            adjacentFaces.clear();
            findAdjacentFaces(adjacentFaces, vertex);
            calculateNormal(adjacentFaces, vertex);
        }
    }

    private void calculateNormal(List<NewFace> adjacentFaces, Vertex vertex) {
        float x = 0;
        float y = 0;
        float z = 0;
        for (NewFace adjacentFace : adjacentFaces) {
            x = x + adjacentFace.getNormal()[0];
            y = y + adjacentFace.getNormal()[1];
            z = z + adjacentFace.getNormal()[2];

        }
        vertex.setNormal(new float[]{x/adjacentFaces.size(), y/adjacentFaces.size(), z/adjacentFaces.size()});
    }

    private void findAdjacentFaces(List<NewFace> adjacentFaces, Vertex vertex) {
        for(NewFace face : newFaces) {
            compareVertexes(vertex, adjacentFaces, face);
        }
    }

    private void compareVertexes(Vertex vertex, List<NewFace> adjacentFaces, NewFace face) {
        for(Vertex compare : face.getVertexes()) {
            if(vertex.equals(compare)) {
                adjacentFaces.add(face);
                return;
            }
        }
    }

    private void setFaceNormals() {
        for(NewFace face : newFaces) {
            face.setNormal(generateNormal(
                face.getEdge().getStart(),
                face.getEdge().getPrev().getStart(),
                face.getEdge().getNext().getStart())
            );
        }
    }

    private float[] generateNormal(Vertex start, Vertex prev, Vertex next) {
        float[] vec1 = new float[3];
        float[] vec2 = new float[3];
        float[] normal = new float[3];

        setVector(start, next, vec1);
        setVector(start, prev, vec2);
        calculateNormal(vec1, vec2, normal);

        return normal;
    }

    private void calculateNormal(float[] vec1, float[] vec2, float[] normal) {
        normal[0] = (vec2[1]*vec1[2])-(vec2[2]*vec1[1]);
        normal[1] = (vec2[2]*vec1[0])-(vec2[0]*vec1[2]);
        normal[2] = (vec2[0]*vec1[1])-(vec2[1]*vec1[0]);
    }

    private void setVector(Vertex start, Vertex prev, float[] vector) {
        vector[0] = prev.getX() - start.getX();
        vector[1] = prev.getY() - start.getY();
        vector[2] = prev.getZ() - start.getZ();
    }


    private String getChosenFile() {
        try {
            System.out.println("Available files:");
            Files.walk(Paths.get("src\\resources")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    System.out.println("\t" + filePath.getFileName());
                }
            });
        } catch (IOException e) {
            System.out.println("No Files found.");
            System.exit(0);
        }

        Scanner userInputScanner = new Scanner(System.in);
        System.out.print("Choose a file: ");
        return userInputScanner.nextLine();
    }

    private void generateLinkesVertices() {
        int[] indizes = new int[this.edges.size()*2];

        int index = 0;
        for (Edge edge : this.edges) {
            indizes[index++] = this.getVertexes().indexOf
                    (edge.getStart());
            indizes[index++] = this.getVertexes().indexOf
                    (edge.getNext().getStart());
        }

        this.linkedVertices = indizes;
    }

    private void generateVertexArray() {
        float[] floates = new float[this.getVertexes().size()*3];

        int index = 0;
        for (Vertex vertexe : this.vertexes) {
            floates[index++] = vertexe.getX();
            floates[index++] = vertexe.getY();
            floates[index++] = vertexe.getZ();
        }

        this.vertexArray = floates;
    }
}
