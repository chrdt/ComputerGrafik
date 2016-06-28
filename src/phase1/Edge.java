package phase1;

public class Edge {
    private Vertex start;
    private Edge prev;
    private Edge next;
    private Edge twin;
    private NewFace newFace;

    public Edge(Vertex start) {
        this.start = start;
    }

    public Vertex getStart() {
        return this.start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public Edge getPrev() {
        return this.prev;
    }

    public void setPrev(Edge prev) {
        this.prev = prev;
    }

    public Edge getNext() {
        return this.next;
    }

    public void setNext(Edge next) {
        this.next = next;
    }

    public Edge getTwin() {
        return this.twin;
    }

    public void setTwin(Edge twin) {
        this.twin = twin;
    }

    public NewFace getNewFace() {
        return this.newFace;
    }

    public void setNewFace(NewFace newFace) {
        this.newFace = newFace;
    }

    public Boolean hasTwin() {
        return this.twin != null;
    }

    public String toString() {
        String hastwin;
        if(this.hasTwin()) {
            hastwin = "Twin:" + this.twin.getStart().toString() + "\t " + this.twin.getNext().toString();
        } else {
            hastwin = "Twin: nicht vorhanden";
        }
        return "Startknoten:" + this.start.toString() + "Previous:" + this.prev.toString() + "Next:" + this.next.toString() + hastwin + "\n-----\n";
    }
}
