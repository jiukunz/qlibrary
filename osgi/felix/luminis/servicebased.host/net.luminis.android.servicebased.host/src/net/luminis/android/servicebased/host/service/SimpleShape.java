package net.luminis.android.servicebased.host.service;

import android.graphics.Canvas;

/**
 * This interface defines the <tt>SimpleShape</tt> service. This service
 * is used to draw shapes. It has two service properties:
 * <ul>
 *   <li>simple.shape.name - A <tt>String</tt> name for the shape.
 *   </li>
 * </ul>
**/
public interface SimpleShape {
    /**
     * A service property for the name of the shape.
    **/
    public static final String NAME_PROPERTY = "simple.shape.name";
	public static final String ICON_PROPERTY = "simple.shape.icon";

    /**
     * Method to draw the shape of the service.
    **/
    public void draw(Canvas canvas, int x, int y);
}
