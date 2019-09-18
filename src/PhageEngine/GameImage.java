package PhageEngine;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class GameImage {

    private Image image;
    private double rot;

    public GameImage(Image image){
        rot = 0;
        this.image = image;
    }

    public Image getImage(double rot) {
        ImageView iv = new ImageView(image);
        iv.setRotate(rot);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = iv.snapshot(params, null);
        return rotatedImage;
    }

    public Image getImage() {
        ImageView iv = new ImageView(image);
        iv.setRotate(rot);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = iv.snapshot(params, null);
        return rotatedImage;
    }
}
