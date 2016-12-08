package rcnk;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import com.facebook.csslayout.CSSMeasureMode;
import com.facebook.csslayout.CSSNodeAPI;
import com.facebook.csslayout.CSSPositionType;
import com.facebook.csslayout.MeasureOutput;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.BaseViewManager;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.slider.ReactSlidingCompleteEvent;
import com.facebook.react.views.view.MeasureUtil;

import java.util.Map;

/**
 * Created by reweber on 27/11/2016
 */
public class RnckKeyboardManager extends BaseViewManager<RnckKeyboardView, RnckKeyboardManager.KeyboardShadowNode> {


    @Override
    public String getName() {
        return "RnckKeyboard";
    }

    @Override
    public KeyboardShadowNode createShadowNodeInstance() {
        KeyboardShadowNode keyboardShadowNode = new KeyboardShadowNode();
        keyboardShadowNode.setPositionType(CSSPositionType.ABSOLUTE);
        keyboardShadowNode.setPositionValues(0, 0);
        keyboardShadowNode.setPositionValues(3, 0);
        return keyboardShadowNode;
    }

    @Override
    public Class<? extends KeyboardShadowNode> getShadowNodeClass() {
        return KeyboardShadowNode.class;
    }

    @Override
    protected RnckKeyboardView createViewInstance(ThemedReactContext reactContext) {
        RnckKeyboardView keyboardView = new RnckKeyboardView(reactContext, null);
        keyboardView.setVisibility(View.GONE);
        return keyboardView;
    }

    @Override
    public void updateExtraData(RnckKeyboardView root, Object extraData) {
    }

    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, RnckKeyboardView view) {
        view.setOnKeyboardActionListener(new RnckIME(reactContext, view.getId()));
    }

    @ReactProp(name = "keys")
    public void setKeys(RnckKeyboardView view, ReadableArray keys) {
        ThemedReactContext context = (ThemedReactContext) view.getContext();
        View rootView = context.getCurrentActivity().getWindow().getDecorView();
        view.setKeyboard(new RnckKeyboard(view.getContext(), rootView.getHeight(), rootView.getWidth(), keys));
    }

    @ReactProp(name = "show")
    public void setShow(RnckKeyboardView view, boolean show) {
        if(show) {
            ThemedReactContext context = (ThemedReactContext) view.getContext();
            View rootView = context.getCurrentActivity().getWindow().getDecorView().getRootView();

            view.setEnabled(true);
            view.setVisibility(View.VISIBLE);
            if(rootView !=null) {
                InputMethodManager systemService = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                systemService.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
            }
        } else {
            view.setEnabled(false);
            view.setVisibility(View.GONE);
        }
    }

    static class KeyboardShadowNode extends LayoutShadowNode implements
            CSSNodeAPI.MeasureFunction {

        private ReadableArray keys;
        private boolean show;

        private KeyboardShadowNode() {
            setMeasureFunction(this);
        }

        @Override
        public long measure(CSSNodeAPI node,
                            float width,
                            CSSMeasureMode widthMode,
                            float height,
                            CSSMeasureMode heightMode) {
            if(show) {
                int calcHeight = keys.size() * (RnckKeyboard.DEFAULT_KEY_HEIGHT + RnckKeyboard.DEFAULT_VERTICAL_GAP);
                return MeasureOutput.make(width, calcHeight);
            } else {
                return MeasureOutput.make(0, 0);
            }
        }

        @Override
        public void markUpdated() {
            super.markUpdated();
            // We mark virtual anchor node as dirty as updated text needs to be re-measured
            if (!isVirtual()) {
                super.dirty();
            }
        }

        @ReactProp(name = "keys")
        public void setKeys(ReadableArray keys) {
            this.keys = keys;
            markUpdated();
        }

        @ReactProp(name = "show")
        public void setShow(boolean show) {
            this.show = show;
            markUpdated();
        }
    }
}
