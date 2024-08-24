package rpengine.core.gles32core;

import android.app.Activity;
import android.os.Bundle;
import rpengine.core.gles32core.databinding.ActivityMainBinding;


public class MainActivity extends Activity {
  ActivityMainBinding binding;
  Surface surface;
  @Override
  protected void onCreate(Bundle bundle) {
  	super.onCreate(bundle);
	  binding=ActivityMainBinding.inflate(getLayoutInflater());
	  surface=new Surface(this);
	  setContentView(surface);
	  surface.setSensitivity(1);
	  //setContentView(binding.getRoot());
  }
  
  @Override
  protected void onDestroy() {
  	super.onDestroy();
	  binding=null;
	  surface=null;
  }
  
}