package rpengine.core.gles32core;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.MotionEvent;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import rpengine.core.gles32core.view.Camera;
import rpengine.core.gles32core.view.Light;
import rpengine.core.gles32core.view.Projection;
import rpengine.core.gles32core.view.m3d.Cube;

public class Surface extends GLSurfaceView {
  public Context context;
  private Renderer renderer;
  private float prevX = -1, prevY = -1;
  private float prevDx = -1, prevDy = -1;
  private float dx = 0, dy = 0;

  public Surface(Context context) {
    super(context);
    this.context = context;
    setEGLContextClientVersion(3);
    renderer = new Renderer(context);
    setRenderer(renderer);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX(), y = event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        prevX = x;
        prevY = y;
        break;
      case MotionEvent.ACTION_MOVE:
        dx = (x - prevX) % 360 + prevDx;
        dy = (y - prevY) % 360 + prevDy;
        renderer.setListener(
            () -> {
              float len = (float) Math.sqrt((dx * dx + dy * dy));
              Matrix.rotateM(renderer.cube().modelMatrix(), 0, len, dy, dx, 0);
            });
        break;
      case MotionEvent.ACTION_UP:
        prevDx = dx%360;
        prevDy = dy%360;
        break;
    }
    return true;
  }

  public class Renderer implements GLSurfaceView.Renderer {

    private Context context;
    private Projection projection;
    private Camera camera;
    private Light light;
    private Cube cube;
    private Listener listener;

    public Renderer(Context context) {
      this.context = context;
      camera = new Camera();
      projection = new Projection();
      cube = new Cube(context);
    }

    public void setListener(Listener listener) {
      this.listener = listener;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig eglConfig) {
      Color bg = Color.valueOf(Color.GRAY);
      GLES32.glClearColor(bg.red(), bg.green(), bg.blue(), bg.alpha());
      GLES32.glClearDepthf(1);
      GLES32.glEnable(GLES32.GL_DEPTH_TEST);

      cube.init(light);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int w, int h) {
      GLES32.glViewport(0, 0, w, h);
      projection.setup(w, h, 1.0f, 5f);
    }

    public Camera camera() {
      return camera;
    }

    public Projection projection() {
      return projection;
    }

    public Cube cube() {
      return cube;
    }

    @Override
    public void onDrawFrame(GL10 unused) {
      GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT | GLES32.GL_DEPTH_BUFFER_BIT);
      camera.setup(0, 0, 3, 0, 0, 0, 0, 1, 0);
      cube.setIdentity();
      if (listener != null) {
        listener.listen();
      }
      cube.draw(projection, camera);
    }

    @FunctionalInterface
    public interface Listener {
      void listen();
    }
  }
}
