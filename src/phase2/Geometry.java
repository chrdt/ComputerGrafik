package phase2;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import com.jogamp.opengl.GL4;
import com.jogamp.common.nio.Buffers;
import phase1.*;


/**
 * Klasse zum Halten der Geometrieinformation
 *
 * Hier werden alle Puffer verwaltet, die ausschließlich für die Geometriebehandlung notwendig sind
 */
public class Geometry {

    private DCEL dcel = new DCEL(new Parser(), new Converter());

    // Vertexpositionen
    private FloatBuffer vertices;

    // Vertexindizes
    private IntBuffer indices;

    // Farben
    private FloatBuffer colors;

    // Position der Vertex Buffer Objects (VBO)
    int[] triangleVertexPositionBuffer = new int[3];

    // Vertex Array Object
    private static int vao[];

    /**
     * Konstruktur, Erzeugt die Geometrie
     */

    Geometry()
    {
        // Positionen der Eckpunkte eines Würfels
        vertices = FloatBuffer.wrap(dcel.getVertexArray());
        // vertices.flip();

        // Indizes, die Kanten verbinden, die als Linien gezeichnet werden
        indices = IntBuffer.wrap(dcel.getLinkedVertices());

        // Farben als RGB-Attribute an den Eckpunkten
        colors = FloatBuffer.wrap(
                makeColor(dcel.getVertexArray().length)
        );
    }

    private float[] makeColor(int length) {
        Random rand = new Random();
        float[] colors = new float[length];

        for (int i = 0; i < length; i++) {
            colors[i] = rand.nextFloat() * (1.0f - 0.0f) + 0.0f;

        }
        return colors;
    }


    /**
     * Legt die notwendigen Puffer auf der Grafikkarte an und füllt diese
     * @pre initBuffers darf zuvor nicht ausgeführt worden sein
     * @post die Puffer sind angelegt und initialisiert
     * @param gl
     */
    public void initBuffers( GL4 gl )
    {
        gl.glGenBuffers(3, triangleVertexPositionBuffer, 0 );

        gl.glBindBuffer( GL4.GL_ARRAY_BUFFER, triangleVertexPositionBuffer[0] );
        gl.glBufferData( GL4.GL_ARRAY_BUFFER, vertices.capacity() * Buffers.SIZEOF_FLOAT, vertices, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer( GL4.GL_ELEMENT_ARRAY_BUFFER, triangleVertexPositionBuffer[1] );
        gl.glBufferData( GL4.GL_ELEMENT_ARRAY_BUFFER, indices.capacity() * Buffers.SIZEOF_INT, indices, GL4.GL_STATIC_DRAW);

        gl.glBindBuffer( GL4.GL_ARRAY_BUFFER, triangleVertexPositionBuffer[2] );
        gl.glBufferData( GL4.GL_ARRAY_BUFFER, colors.capacity() * Buffers.SIZEOF_FLOAT, colors, GL4.GL_STATIC_DRAW);

        vao = new int[1];
        gl.glGenVertexArrays( 1, vao, 0);

        gl.glBindVertexArray( vao[0] );
        gl.glBindBuffer( GL4.GL_ARRAY_BUFFER, triangleVertexPositionBuffer[0] );
        // Hinzufuegen eines neuen Vertexattributes mit Groesse 3 (z.B. vec3) zur location 0 (hier vertexPositionAttribute)
        gl.glVertexAttribPointer( 0, 3, GL4.GL_FLOAT, false, 0, 0 );
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer( GL4.GL_ARRAY_BUFFER, triangleVertexPositionBuffer[2] );
        // Hinzufuegen eines neuen Vertexattributes mit Groesse 3 (z.B. vec3) zur location 1 (hier vertexColorAttribute)
        gl.glVertexAttribPointer( 1, 3, GL4.GL_FLOAT, false, 0, 0 );
        gl.glEnableVertexAttribArray(1);
    }

    /**
     * Zeichnet das Objekt
     * @pre initBuffers muss im gleichen Kontext vorher ausgeführt worden sein
     * @param gl
     */
    public void display( GL4 gl )
    {
        SimpleScene.checkGLError( gl, "Geometry start of of display" );
        // setup OpenGL state

        // Anti-Aliasing (Kantenglättung) für Linien einschalten
        gl.glEnable( GL4.GL_LINE_SMOOTH );
        gl.glLineWidth( 1.0f );

        SimpleScene.checkGLError( gl, "Geometry 1" );

        // activate vertex array objects including all buffers
        gl.glBindVertexArray( vao[0] );

        SimpleScene.checkGLError( gl, "Geometry 2" );

        // verwende 24 Punkte um Linien zwischen jeweils zwei Punkten zu zeichnen.
        // starte bei Offset 0 in GL_ELEMENT_ARRAY_BUFFER
        gl.glBindBuffer( GL4.GL_ELEMENT_ARRAY_BUFFER, triangleVertexPositionBuffer[1] );
        //gl.glDrawElements( GL4.GL_LINES, /*size*/ 24, GL4.GL_UNSIGNED_INT, /* offset */ 0 );
        gl.glDrawElements( GL4.GL_LINES, /*size*/ dcel.getEdges().size(), GL4.GL_UNSIGNED_INT, /* offset */ 0 );

        // Alternative: zeichne die ersten 6 Punkte aus der Vertexliste, ignoriert die Indexliste
        //gl.glDrawArrays( GL4.GL_LINES, /* start index */ 0, /* size */ 6);

        gl.glBindVertexArray(0);
        SimpleScene.checkGLError( gl, "Geometry end of of display" );
    }
}

