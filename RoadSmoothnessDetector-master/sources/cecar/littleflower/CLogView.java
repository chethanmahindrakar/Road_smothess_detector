package cecar.littleflower;

import android.content.Context;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

public class CLogView extends TextView {
    public CLogView(Context context) {
        this(context, null);
    }

    public CLogView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);
    }

    public CLogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected MovementMethod getDefaultMovementMethod() {
        return ScrollingMovementMethod.getInstance();
    }

    public void addtext(CharSequence text) {
        append("\n" + text);
    }
}
