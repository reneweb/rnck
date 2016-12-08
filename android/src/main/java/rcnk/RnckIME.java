package rcnk;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;

/**
 * Created by reweber on 04/12/2016
 */
public class RnckIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private final DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter;
    private final int viewTag;

    public RnckIME(ThemedReactContext reactContext, int viewTag) {
        this.eventEmitter = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
        this.viewTag = viewTag;
    }

    @Override
    public void onPress(final int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onKey(final int primaryCode, int[] keyCodes) {
    }

    @Override
    public void onText(CharSequence text) {
        eventEmitter.emit("onKeyPress", serializeEventData(viewTag, text.toString()));
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    private WritableMap serializeEventData(int viewTag, String text) {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("target", viewTag);
        eventData.putString("key", text);
        return eventData;
    }
}
