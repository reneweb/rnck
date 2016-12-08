package rcnk;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * Created by reweber on 27/11/2016
 */
public class RnckKeyboardView extends KeyboardView {
    public RnckKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setPreviewEnabled(false);
    }
}
