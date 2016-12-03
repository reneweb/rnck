package rcnk;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import com.facebook.react.bridge.ReadableArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by reweber on 27/11/2016
 */
public class RnckKeyboard extends Keyboard {
    private final Context context;
    private final ReadableArray keys;

    public RnckKeyboard(Context context, ReadableArray keys) {
        super(context, R.xml.keyboard_base);
        this.context = context;
        this.keys = keys;
        resetKeys();
    }

    private void resetKeys() {
        List<Row> rowsList = new ArrayList<>();
        List<Key> keysList = new ArrayList<>();
        List<Key> modifierKeysLst = new ArrayList<>();
        for(int i = 0; i < keys.size(); i++) {
            ReadableArray row = keys.getArray(i);
            Row r = new Row(this);
            for(int j = 0; j < row.size(); j++) {
                Key k = new Key(r);
                k.label = row.getString(j);
                keysList.add(k);
            }
            rowsList.add(r);
        }

        setPrivateField("mKeys", keysList);
        setPrivateField("mModifierKeys", modifierKeysLst);
        setPrivateField("rows", rowsList);
    }

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
