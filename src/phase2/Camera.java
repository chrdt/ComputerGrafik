package phase2;

import java.nio.FloatBuffer;

/**
 * Klasse zum Halten der Kamerainformation
 */
public class Camera {

    FloatBuffer mypmatrix;
    FloatBuffer mymvmatrix;

    Camera()
    {
        // Anlegen der aktuellen Matrizen fuer die Anzeige
        mypmatrix = FloatBuffer.wrap(
                new float[]{
                        0.5f, 0.0f, 0.0f, 0.0f,
                        0.0f, 0.5f, 0.0f, 0.0f,
                        0.0f, 0.03f, 0.2f, 0.2f,
                        0.0f, 0.0f, 1.0f, 1.0f //Zeile für Kameraposition
                } );



        mymvmatrix = FloatBuffer.wrap(
                new float[]{1.0f, 0.0f, 0.0f, 0.0f,
                        0.0f, 1.0f, 0.0f, 0.0f,
                        0.0f, 0.0f, -1.0f, 0.0f,
                        0.0f, -1.0f, 0.0f, 1.2f //Zeile für Kameraposition
                } );
    }

    /**
     * Gibt die aktuelle Model View Matrix zurück
     * @return die aktuelle Model View Matrix
     */
    public FloatBuffer getModelViewMatrix()
    {
        return mymvmatrix;
    }

    /**
     * Gibt die aktuelle Projektionsmatrix zurück
     * @return die aktuelle Projektionsmatrix
     */
    public FloatBuffer getProjectionMatrix()
    {
        return mypmatrix;
    }
}

