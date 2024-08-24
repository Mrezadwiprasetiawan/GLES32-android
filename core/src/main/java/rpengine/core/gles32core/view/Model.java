package rpengine.core.gles32core.view;
import android.graphics.Color;


public interface Model {
	public void init(Light Light);
	public void setIdentity();
	public void setColor(Color color);
	public void rotate(float dx,float dy,float sensitivity);
	public float[] MVP();
	public void draw(Projection projection,Camera camera);
}
