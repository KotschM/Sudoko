package mocomabe.services;

/**
 * BackgroundNotification
 */
public class BackgroundNotification {

    public final int x;
    public final int y;
    public final int value;
    public final boolean finished;

    public BackgroundNotification(int x, int y, int value, boolean finished) {
        super();
        this.x = x;
        this.y = y;
        this.value = value;
        this.finished = finished;
    }
}