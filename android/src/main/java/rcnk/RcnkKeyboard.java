package rcnk;

import android.content.Context;
import android.inputmethodservice.Keyboard;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by reweber on 27/11/2016
 */
public class RcnkKeyboard extends Keyboard {
    private final Context context;
    private final String[][] keys;

    public RcnkKeyboard(Context context, String[][] keys) {
        super(context, R.xml.keyboard_base);
        this.context = context;
        this.keys = keys;
        resetKeys();
    }

    private void resetKeys() {
        List<Row> rowsList = new ArrayList<>();
        List<Key> keysList = new ArrayList<>();
        List<Key> modifierKeysLst = new ArrayList<>();
        for (String[] row : keys) {
            Row r = new Row(this);
            for (String key : row) {
                Key k = new Key(r);
                k.label = key;
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
