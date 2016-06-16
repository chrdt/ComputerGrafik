package phase1;

public class Vertex {
    private float x;
    private float y;
    private float z;

    public Vertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String toString() {
        return "x = " + this.x + " y = " + this.y + " z = " + this.z + "\n";
    }

    public Boolean equals(Vertex v) {
        return this.getX() == v.getX() && this.getY() == v.getY() && this.getZ() == v.getZ() ? true : false;
    }
}
