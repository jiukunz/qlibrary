package net.luminis.android.servicebased.circle;

import java.net.URL;
import java.util.Properties;

import net.luminis.android.servicebased.host.service.SimpleShape;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Activator implements BundleActivator, SimpleShape{

	private Bitmap m_shape = null;
	
	public void start(BundleContext context) throws Exception {
	    Properties props = new Properties();
	    props.put(SimpleShape.NAME_PROPERTY, "Circle");
	    try {
	    	URL url = context.getBundle().getResource("circle.png");
	    	System.out.println(url);
	    	props.put(SimpleShape.ICON_PROPERTY, m_shape = BitmapFactory.decodeStream(url.openStream()));
	    } catch (Throwable t) {
	    	t.printStackTrace();
	    	throw (RuntimeException) t;
	    }
	    context.registerService(SimpleShape.class.getName(), this, props);
	}

	public void stop(BundleContext context) throws Exception {
	}

	public void draw(Canvas canvas, int x, int y) {
	    Paint paint = new Paint();
	    paint.setStyle(Style.FILL_AND_STROKE);
	    canvas.drawBitmap(m_shape, x, y, paint);
	}

}
