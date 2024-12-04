package object;

import object.behaviour.Drawable;
import scene.Scene;

public abstract class GUIObject extends Object implements Drawable {
    public GUIObject(double x, double y, Scene scene) {
        super(x, y, scene);
    }


}
