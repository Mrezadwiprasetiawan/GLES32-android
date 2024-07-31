package rpengine.core.gles32core.view;

import android.opengl.Matrix;
import rpengine.core.gles32core.math.Mat4;
import rpengine.core.gles32core.math.Vec3;

/**
 * Kelas Camera yang merupakan ekstensi dari Mat4 untuk menangani matriks pandangan (view matrix).
 */
public class Camera extends Mat4 {
  Vec3 eye;
  Vec3 center;
  Vec3 up;
  public Camera(Vec3 eye,Vec3 center,Vec3 up){
		this.eye=eye;
		this.center=center;
		this.up=up;
		setLookAt(eye,center,up);
	}
	
	public void move(Vec3 offset){
		Translate(offset);
	}
	public void moveTo(Vec3 eye){
		setLookAt(eye,center,up);
	}
	
	
}