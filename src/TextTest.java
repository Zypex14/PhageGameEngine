import PhageEngine.GameApp;
import PhageEngine.GameSettings;
import PhageEngine.TextBox;
import PhageEngine.Timer;
import RMath.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class TextTest extends GameApp {

    private TextBox text;
    private int i;
    private Timer t;

    @Override
    public void initSettings(GameSettings gs) {
        gs.setTitle("Text Test");
    }

    @Override
    public void initGame() {
        text = new TextBox();
        text.setFont(Font.font("Consolas", 100));
        i = 0;
    }

    @Override
    public void onUpdate(GraphicsContext gc) {
        i++;
        text.text = i + "";
        text.setCenterX(getW() / 2);
        text.setCenterY(getH() / 2);
    }
}
