package org.wordpress.android.ui;

import java.lang.reflect.Field;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.actionbarsherlock.internal.widget.IcsSpinner;

import org.wordpress.android.ui.media.MediaGridFragment.Filter;

public class CustomSpinner extends IcsSpinner {
    OnItemSelectedListener listener;

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position) {
        //only ignore if the old selection is custom date since we may want to click on it again
        if (position == Filter.CUSTOM_DATE.ordinal())
            ignoreOldSelectionByReflection();
        super.setSelection(position);
    }

    public void setOnItemSelectedEvenIfUnchangedListener(
            OnItemSelectedListener listener) {
        this.listener = listener;
    }
    
    private void ignoreOldSelectionByReflection() {
        try {
            Class<?> c = this.getClass().getSuperclass().getSuperclass().getSuperclass();
            Field reqField = c.getDeclaredField("mOldSelectedPosition");
            reqField.setAccessible(true);
            reqField.setInt(this, -1);
        } catch (Exception e) {
            Log.d("Exception Private", "ex", e);
        }
    }
}
