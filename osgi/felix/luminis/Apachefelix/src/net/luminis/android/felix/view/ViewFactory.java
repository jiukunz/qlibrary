package net.luminis.android.felix.view;

import android.content.Context;
import android.view.View;

/**
 * When you register as a view factory, you indicate you have a
 * top level view available for displaying within some other
 * activity's context.
 */
public interface ViewFactory {
    /**
     * Create a new view with the given context.
     */
	public View create(Context context);
}
