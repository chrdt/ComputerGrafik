package phase1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private ArrayList<String> rawDataFromFile = new ArrayList();
    private ArrayList<Vertex> vertexArray = new ArrayList();
    private ArrayList<OldFace> faceArray = new ArrayList();

    public ArrayList<OldFace> getFaceArray() {
        return faceArray;
    }

    public ArrayList<Vertex> getVertexArray(){
        return vertexArray;
    }

    public void einlesen(String filePath) {
        this.einlesenInArray(filePath);
        this.createVertexArray();
        this.createFaceArray();
    }

    private void createFaceArray() {
        int startOfFaces = startOfData() + getNumOfVertices();
        ArrayList<String> oneFace = new ArrayList<String>();

        for(int i = startOfFaces; i < rawDataFromFile.size(); ++i) {
            oneFace = indecesForFace(rawDataFromFile.get(i));
            faceArray.add(new OldFace(indexToVertices(oneFace)));
        }
    }

    private ArrayList<Vertex> indexToVertices(ArrayList<String> oneFace) {
        ArrayList <Vertex> vertexList = new ArrayList<Vertex>();
        int index;
        for(int i = 0; i < oneFace.size(); i++) {
            index = Integer.parseInt(oneFace.get(i));
            vertexList.add(vertexArray.get(index));
        }
        return vertexList;
    }

    private ArrayList<String> indecesForFace(String s) {
        ArrayList faceIndeces = new ArrayList();
        String[] split = s.split("\\s+");

        for(int i = 1; i < split.length; ++i) {
            faceIndeces.add(split[i]);
        }

        return faceIndeces;
    }

    private void einlesenInArray(String filePath) {
        ArrayList list = new ArrayList();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }

        try {
            String line;
            while((line = br.readLine()) != null) {
                list.add(line);
            }

            this.rawDataFromFile = list;
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    private void createVertexArray() {
        int numOfVertices = getNumOfVertices();
        int startOfData = startOfData();

        for(int i = startOfData; i < numOfVertices + startOfData; ++i) {
            ArrayList myDoubles = numsFromEachLine(i);
            float x = ((Float)myDoubles.get(0)).floatValue();
            float y = ((Float)myDoubles.get(1)).floatValue();
            float z = ((Float)myDoubles.get(2)).floatValue();
            vertexArray.add(new Vertex(x, y, z));
        }

    }

    private ArrayList<Float> numsFromEachLine(int i) {
        String input = rawDataFromFile.get(i);
        ArrayList myFloats = new ArrayList();
        Matcher matcher = Pattern.compile("[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?").matcher(input);

        while(matcher.find()) {
            float element = Float.parseFloat(matcher.group());
            myFloats.add(Float.valueOf(element));
        }

        return myFloats;
    }

    private int getNumOfVertices() {
        String resultString;
        String lookingFor = "element vertex";
        String vertexNum = null;

        for(int i = 0; i < rawDataFromFile.size(); ++i) {
            resultString = rawDataFromFile.get(i);
            if(resultString.startsWith(lookingFor)) {
                vertexNum = (rawDataFromFile.get(i)).replaceAll("\\D+", "");
                vertexNum = vertexNum.trim();
                break;
            }
        }
        return Integer.parseInt(vertexNum);
    }

    private int startOfData() {
        for(int i = 0; i < rawDataFromFile.size(); ++i) {
            if((rawDataFromFile.get(i)).equalsIgnoreCase("end_header")) {
                return i + 1;
            }
        }
        return 0;
    }
}
