import com.aparapi.Kernel;

/**
 * Created by the-magical-llamicorn on 5/27/17.
 * This calculator ignores the lengths of vectors. If a vector is too short, it will be looped
 */
public class CyclicCalculator extends Kernel implements Calculator {

    // Aparapi does not like enums
    protected static final int ADD = Operations.ADD.ordinal();
    protected static final int SUBTRACT = Operations.SUBTRACT.ordinal();
    protected static final int MULTIPLY = Operations.MULTIPLY.ordinal();
    protected static final int DIVIDE = Operations.DIVIDE.ordinal();
    protected static final int POWER = Operations.POWER.ordinal();
    protected static final int ROOT = Operations.ROOT.ordinal();

    private int operation;
    private float[] v1, v2, ans;
    private int v1Length, v2Length;

    {
        setExplicit(true);
    }

    public synchronized float[] execute(final float[] v1, final Operations operation, final float[] v2) {
        this.v1 = v1;
        this.v2 = v2;
        v1Length = v1.length;
        v2Length = v2.length;
        final int range = max(v1Length, v2Length);
        ans = new float[range];
        this.operation = operation.ordinal();

        put(v1).put(v2).put(ans);
        execute(range);
        get(ans);

        return ans;
    }

    public float[] execute(final float[] v, final Operations operation, final float scalar) {
        return execute(v, operation, new float[]{scalar});
    }

    public void run() {
        final int gid = getGlobalId();

        int v1i = 0; // Aparapi does not like uninitialized variables
        if (gid < v1Length) v1i = gid; // Aparapi is still broken? https://github.com/Saalma/aparapi/issues/99
        else v1i = gid % v1Length; // Mod is an expensive operation, do not use it unless necessary

        if (v2Length == 1) {
            if (operation == ADD) ans[gid] = v1[v1i] + v2[0];
            else if (operation == SUBTRACT) ans[gid] = v1[v1i] - v2[0];
            else if (operation == MULTIPLY) ans[gid] = v1[v1i] * v2[0];
            else if (operation == DIVIDE) ans[gid] = v1[v1i] / v2[0];
            else if (operation == POWER) ans[gid] = pow(v1[v1i], v2[0]);
            else if (operation == ROOT) ans[gid] = pow(v1[v1i], 1 / v2[0]);
            else ans[gid] = ans[gid]; // Aparapi is still broken? https://github.com/Saalma/aparapi/issues/99
        } else {
            int v2i = 0; // Aparapi does not like uninitialized variables
            if (gid < v2Length) v2i = gid; // Aparapi is still broken? https://github.com/Saalma/aparapi/issues/99
            else v2i = gid % v2Length; // Mod is an expensive operation, do not use it unless necessary

            if (operation == ADD) ans[gid] = v1[v1i] + v2[v2i];
            else if (operation == SUBTRACT) ans[gid] = v1[v1i] - v2[v2i];
            else if (operation == MULTIPLY) ans[gid] = v1[v1i] * v2[v2i];
            else if (operation == DIVIDE) ans[gid] = v1[v1i] / v2[v2i];
            else if (operation == POWER) ans[gid] = pow(v1[v1i], v2[v2i]);
            else if (operation == ROOT) ans[gid] = pow(v1[v1i], 1 / v2[v2i]);
            else ans[gid] = ans[gid]; // Aparapi is still broken? https://github.com/Saalma/aparapi/issues/99
        }
    }
}
