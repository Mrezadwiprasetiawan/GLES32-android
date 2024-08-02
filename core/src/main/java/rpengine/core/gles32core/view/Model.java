package rpengine.core.gles32core.view;


public interface Model {
	public void init(Light Light);
	public float[] modelMatrix();
	public void subInit();
	public void draw(Projection projection, Camera camera);
}
