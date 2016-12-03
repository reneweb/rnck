package rcnk;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by reweber on 27/11/2016
 */
public class RnckKeyboardManager extends SimpleViewManager<RnckKeyboardView> {

    @Override
    public String getName() {
        return "RnckKeyboard";
    }

    @Override
    protected RnckKeyboardView createViewInstance(ThemedReactContext reactContext) {
        return new RnckKeyboardView(reactContext, null);
    }

    @ReactProp(name = "keys")
    public void setKeys(RnckKeyboardView view, ReadableArray keys) {
        view.setKeyboard(new RnckKeyboard(view.getContext(), keys));
    }
}
