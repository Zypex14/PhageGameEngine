import PhageEngine.GameApp;
import PhageEngine.GameSettings;
import PhageEngine.PolyBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import RMath.*;

public class Main extends GameApp {

    PolyBox polygon;

    double zoom = 1;

    @Override
    public void initSettings(GameSettings gs) {
        gs.setTitle("Polygon test");
    }

    @Override
    public void initGame() {
        polygon = new PolyBox(getW() / 2, getH() / 2, 0, 1,
                new Point(-10, 10),
                new Point(10, 10),
                new Point(10, -10),
                new Point(-10, -10)
        );
    }

    @Override
    public void onUpdate(GraphicsContext gc) {

        gc.setStroke(Color.PINK);
        gc.setLineWidth(5);

        if (isKeyHeld(KeyCode.Z)) {
            zoom *= 1.01;
        }

        if (isKeyHeld(KeyCode.X)) {
            zoom *= 0.99;
        }

        if (isKeyHeld(KeyCode.UP)) {
            polygon.y--;
        }

        if (isKeyHeld(KeyCode.DOWN)) {
            polygon.y++;
        }

        if (isKeyHeld(KeyCode.RIGHT)) {
            polygon.x++;
        }

        if (isKeyHeld(KeyCode.LEFT)) {
            polygon.x--;
        }

        polygon.size = zoom;

        gc.setStroke(Color.BLACK);

        if (polygon.isIntersecting(getMouse())) {
            gc.setStroke(Color.RED);
        }

        for (int i = 0; i < polygon.getActualPoints().size() - 1; i++) {
            Point p1 = polygon.getActualPoints().get(i);
            Point p2 = polygon.getActualPoints().get(i + 1);
            gc.strokeLine(p1.x, p1.y, p2.x, p2.y);

        }

        gc.setStroke(Color.GOLD);
        gc.setFill(Color.GOLD);

    }
}
