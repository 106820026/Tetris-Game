package Config;

public class LayerConfig {
    private String className;
    private int x, y, w, h;

    public String getClassName() {
        return className;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public LayerConfig(String className, int x, int y, int w, int h) {
        this.className = className;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
