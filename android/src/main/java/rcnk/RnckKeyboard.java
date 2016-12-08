package rcnk;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import com.facebook.react.bridge.ReadableArray;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by reweber on 27/11/2016
 */
public class RnckKeyboard extends Keyboard {
    public static int DEFAULT_KEY_HEIGHT = 100;
    public static int DEFAULT_KEY_WIDTH = 100;

    public static int DEFAULT_VERTICAL_GAP = 4;
    public static int DEFAULT_HORIZONTAL_GAP = 4;

    private final Context context;
    private final int screenHeight;
    private final int screenWidth;
    private final ReadableArray keys;

    public RnckKeyboard(Context context, int screenHeight, int screenWidth, ReadableArray keys) {
        super(context, R.xml.keyboard_base, 0, screenWidth, keys.size() * (DEFAULT_KEY_HEIGHT + DEFAULT_VERTICAL_GAP));
        this.context = context;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.keys = keys;

        this.setKeyHeight(DEFAULT_KEY_HEIGHT);
        this.setKeyWidth(DEFAULT_KEY_WIDTH);
        this.setVerticalGap(DEFAULT_VERTICAL_GAP);
        this.setHorizontalGap(DEFAULT_HORIZONTAL_GAP);

        resetKeys();

        this.setPrivateField("mTotalHeight", keys.size() * (DEFAULT_KEY_HEIGHT + DEFAULT_VERTICAL_GAP));
        this.setPrivateField("mTotalWidth", screenWidth);
    }

    private void resetKeys() {
        List<Row> rowsList = new ArrayList<>();
        List<Key> keysList = new ArrayList<>();
        List<Key> modifierKeysLst = new ArrayList<>();

        for(int i = 0; i < keys.size(); i++) {
            ReadableArray row = keys.getArray(i);

            Row r = new Row(this);
            r.defaultHeight = DEFAULT_KEY_HEIGHT;
            r.defaultWidth = screenWidth;
            r.defaultHorizontalGap = DEFAULT_VERTICAL_GAP;
            r.verticalGap = DEFAULT_HORIZONTAL_GAP;
            if(i ==0) {
                r.rowEdgeFlags = EDGE_TOP;
            } else if(i == keys.size() - 1) {
                r.rowEdgeFlags = EDGE_BOTTOM;
            }

            for(int j = 0; j < row.size(); j++) {
                int widthSize = screenWidth / row.size();
                Key k = new Key(r);
                k.label = row.getString(j);
                k.text = row.getString(j);
                k.height = DEFAULT_KEY_HEIGHT;
                k.width = widthSize;
                k.y = i * DEFAULT_KEY_HEIGHT;
                k.x = j * widthSize;
                k.codes = new int[] { row.getString(j).charAt(0) };
                keysList.add(k);
            }

            rowsList.add(r);
        }

        setPrivateField("mKeys", keysList);
        setPrivateField("mModifierKeys", modifierKeysLst);
        setPrivateField("rows", rowsList);
    }

    //Pretty hacky, but works for now
    private <T>void setPrivateField(String fieldName, T value) {
        try {
            Field field = Keyboard.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(this, value);
        } catch (Exception e ) {
            throw new IllegalStateException("Problem generating custom keyboard: Could not set required fields.");
        }
    }

}
