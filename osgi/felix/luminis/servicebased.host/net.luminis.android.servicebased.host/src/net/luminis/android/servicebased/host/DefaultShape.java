package net.luminis.android.servicebased.host;

import net.luminis.android.servicebased.host.service.SimpleShape;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * This class is used as a proxy to defer object creation from shape
 * provider bundles and also as a placeholder shape when previously
 * used shapes are no longer available. These two purposes are
 * actually orthogonal, but were combined into a single class to
 * reduce the number of classes in the application. The proxy-related
 * functionality is introduced as a way to lazily create shape
 * objects in an effort to improve performance; this level of
 * indirection could be removed if eager creation of objects is not
 * a concern. Since this application uses the service-based extension
 * appraoch, lazy shape creation will only come into effect if
 * service providers register service factories instead of directly
 * registering <tt>SimpleShape</tt> or if they use a technology like
 * Declarative Services or iPOJO to register services. Since the
 * example providers register services instances directly there is
 * no laziness in the example, but the proxy approach is still used
 * to demonstrate how to make laziness possible and to keep it
 * similar to the extender-based approach.
**/
class DefaultShape implements SimpleShape
{
    private SimpleShape m_shape;
    private BundleContext m_context;
    private ServiceReference m_ref;
    private final String m_name;
    
    /**
     * This constructs a proxy shape that lazily gets the shape service.
    **/
    public DefaultShape(BundleContext context, ServiceReference ref, String name)
    {
        m_context = context;
        m_ref = ref;
        m_name = name;
    }

    /**
     * This method tells the proxy to dispose of its service object; this
     * is called when the underlying service goes away.
    **/
    public void dispose()
    {
        if (m_shape != null)
        {
            m_context.ungetService(m_ref);
//            m_context = null;
            m_ref = null;
            m_shape = null;
        }
    }

    /**
     * Implements the <tt>SimpleShape</tt> interface method. When acting as
     * a proxy, this method gets the shape service and then uses it to draw
     * the shape. When acting as a placeholder shape, this method draws the
     * default icon.
    **/
    public void draw(Canvas canvas, int x, int y)
    {
        // If this is a proxy shape, instantiate the shape class
        // and use it to draw the shape.
        if (m_context != null)
        {
            try
            {
                if (m_shape == null)
                {
                    if (m_ref == null) {
                        ServiceReference[] refs = m_context.getServiceReferences(SimpleShape.class.getName(), "(" + SimpleShape.NAME_PROPERTY + "=" + m_name + ")");
                        if (refs != null && refs.length > 0) {
                            m_ref = refs[0];
                        }
                    }
                    if (m_ref != null) {
                        // Get the shape service.
                        m_shape = (SimpleShape) m_context.getService(m_ref);
                    }
                }
                if (m_shape != null) {
                    // Draw the shape.
                    m_shape.draw(canvas, x, y);
                    // If everything was successful, then simply return.
                    return;
                }
            }
            catch (Exception ex)
            {
                // This generally should not happen, but if it does then
                // we can just fall through and paint the default icon.
            }
        }

        // If the proxied shape could not be drawn for any reason or if
        // this shape is simply a placeholder, then draw the default icon.
        Paint p = new Paint();
        p.setARGB(255, 255, 0, 0);
        p.setAntiAlias(true);
        p.setTextSize(40);
        canvas.drawText("?", x, y, p);
    }
}