package rpengine.core.gles32core;
import android.content.Context;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Surface extends GLSurfaceView{
	public Context context;
	public Surface(Context context, Renderer renderer){
		super(context);
		this.context=context;
		setRenderer(renderer);
	}
	
	public class Renderer implements GLSurfaceView.Renderer{
	  
	  @Override
	  public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
	  	// TODO: Implement this method
	  }
	  
	  @Override
	  public void onSurfaceChanged(GL10 unused, int w, int h) {
	  	// TODO: Implement this method
	  }
	  
		@Override
		public void onDrawFrame(GL10 unused) {
			// TODO: Implement this method
		}
		
	}
}
