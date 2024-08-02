package rpengine.core.gles32core.view;

import android.graphics.Color;

public class Light {
  private float[] position = new float[3];
  private int color;

  public Light(float[] position) {
    this(position, Color.WHITE);
  }

  public Light(float[] position, int color) {
    this.position = position;
    this.color = color;
  }

  public void move(float[] offset) {
    move(offset[0],offset[1],offset[2]);
  }
  public void move(float x,float y, float z){
		position[0]+=x;
		position[1]+=y;
		position[2]+=z;
	}
	
	public void moveTo(float x, float y, float z){
		position[0]=x;
		position[1]=y;
		position[2]=z;
	}

  public void setColor(int color) {
    this.color = color;
  }

  public void setColor(float r, float g, float b) {
    setColor(Color.rgb((int) (r * 255), (int) (g * 255), (int) (b * 255)));
  }

  public void setColor(float a, float r, float g, float b) {
    // Mengatur warna dengan alpha dan RGB
    setColor(Color.argb((int) (a * 255), (int) (r * 255), (int) (g * 255), (int) (b * 255)));
  }

  public float[] getPosition() {
    return position;
  }

  public int getColor() {
    return color;
  }
}
