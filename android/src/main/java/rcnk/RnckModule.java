package rcnk;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class RnckModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RnckModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "Rnck";
  }
}
