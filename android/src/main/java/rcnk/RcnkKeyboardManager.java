package rcnk;

import android.util.AttributeSet;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

/**
 * Created by reweber on 27/11/2016
 */
public class RcnkKeyboardManager extends SimpleViewManager<RcnkKeyboardView> {

    @Override
    public String getName() {
        return null;
    }

    @Override
    protected RcnkKeyboardView createViewInstance(ThemedReactContext reactContext) {
        return new RcnkKeyboardView(reactContext, null);
    }

    @ReactProp(name = "keys")
    public void setKeys(RcnkKeyboardView view, String[][] keys) {
        view.setKeyboard(new RcnkKeyboard(view.getContext(), keys));
    }
}
