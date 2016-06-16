package phase1;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class DCEL {
    private Hashtable<NewFace, Edge> faces;
    private ArrayList<Edge> edges;
    private ArrayList<Vertex> vertexes;
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

    public Hashtable<NewFace, Edge> getFaces() {
        return this.faces;
    }

    public void setFaces(Hashtable<NewFace, Edge> faces) {
        this.faces = faces;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public ArrayList<Vertex> getVertexes() {
        return this.vertexes;
    }

    public void setVertexes(ArrayList<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public void setVertexArray(float[] vertexArray) { this.vertexArray = vertexArray; }

    public float[] getVertexArray() { return this.vertexArray; }


    private DCEL setUp(Parser parser, Converter converter) {
        parser.einlesen("/src/resources/" + getChosenFile());

        this.edges = converter.parseEdges(parser.getFaceArray());
        this.faces = converter.getNewFaces();
        this.vertexes = parser.getVertexArray();

        generateVertexArray();
        generateLinkesVertices();

        return this;
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
        for (int i = 0; i < this.edges.size(); i++) {
            indizes[index++] = this.getVertexes().indexOf
                    (this.edges.get(i).getStart());
            indizes[index++] = this.getVertexes().indexOf
                    (this.edges.get(i).getNext());
        }

        this.linkedVertices = indizes;
    }

    private void generateVertexArray() {
        float[] floates = new float[this.getVertexes().size()*3];

        int index = 0;
        for (int i=0; i<this.vertexes.size(); i++) {
            floates[index++] = this.vertexes.get(i).getX();
            floates[index++] = this.vertexes.get(i).getY();
            floates[index++] = this.vertexes.get(i).getZ();
        }

        this.vertexArray = floates;
    }
}
