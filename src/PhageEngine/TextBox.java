package PhageEngine;

import RMath.Pair;
import RMath.Point;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class TextBox extends Entity{

    private boolean accessible = false;
    private boolean showing = true;
    private boolean entered = false;
    public boolean wrappable = false;
    public double maxwidth;
    private Font font = Font.getDefault();
    public String text = "";
    public Color color = Color.BLACK;

    public TextBox() {
        attachToWorld(this);
    }

    public void setWrap(boolean wrappable, double maxwidth){
        this.wrappable = wrappable;
        this.maxwidth = maxwidth;
    }

    public void setFont(Font f) {
        font = f;
        Text t = new Text(text);
        t.setFont(font);
        t.setLineSpacing(0);
        t.setBoundsType(TextBoundsType.VISUAL);
        setWidth(t.getLayoutBounds().getWidth());
        setHeight(t.getLayoutBounds().getHeight());
    }

    @Override
    protected void onUpdate(GraphicsContext gc) {
        if(showing){
            draw(gc);
        }
    }

    private void draw(GraphicsContext gc){

        double w = getWidth();
        double h = getHeight();

        gc.setFont(Font.font("Comic Sans MS", font.getSize()));
        gc.setFill(color);

        setFont(font);

        if(wrappable){
            gc.fillText(text, x, y + h, maxwidth);
        } else{
            gc.fillText(text, x, y + h);
        }

        gc.setStroke(Color.RED);
        gc.strokeRect(x, y, w, h);
    }
}
