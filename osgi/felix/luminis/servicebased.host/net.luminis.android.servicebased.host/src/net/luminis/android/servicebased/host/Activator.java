package net.luminis.android.servicebased.host;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.luminis.android.felix.view.ViewFactory;
import net.luminis.android.servicebased.host.service.SimpleShape;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
//import android.view.UIThreadUtilities;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class Activator implements BundleActivator, ViewFactory, ServiceTrackerCustomizer {
    private LinearLayout m_main;
    private LinearLayout m_buttonBar;
    private Bitmap m_bitmap;
    private Canvas m_canvas;
    private Paint m_paint;
    private Button m_clear;
    private View m_canvasView;
    private BundleContext m_context;
    private ServiceTracker m_tracker;
    private Map<ServiceReference, Button> m_buttonMap = new HashMap<ServiceReference, Button>();
    private List<ShapeComponent> m_shapes = new ArrayList<ShapeComponent>();
    private Context m_androidContext;
    protected volatile SimpleShape m_selectedShape;

	public void start(BundleContext context) throws Exception {
	    m_context = context;
	    m_tracker = new ServiceTracker(m_context, SimpleShape.class.getName(), this);
	    m_context.registerService(ViewFactory.class.getName(), this, null);
	    
	    System.out.println("start host.");
	}

	public void stop(BundleContext context) throws Exception {
	    m_tracker.close();
	}

	public View create(Context context) {
        m_androidContext = context;
        LinearLayout.LayoutParams tlp = 
            new LinearLayout.LayoutParams( LayoutParams.FILL_PARENT, 
                                    LayoutParams.WRAP_CONTENT );
        LinearLayout.LayoutParams tlp2 = 
            new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT, 
                                    LayoutParams.WRAP_CONTENT );
        
        
        m_main = new LinearLayout(context);
        m_main.setOrientation(LinearLayout.VERTICAL);
        m_buttonBar = new LinearLayout(context);
        m_buttonBar.setOrientation(LinearLayout.HORIZONTAL);
//        m_buttonBar.setPreferredWidth(LayoutParams.FILL_PARENT);
//        m_buttonBar.setPreferredHeight(LayoutParams.FILL_PARENT);
        m_buttonBar.setBackgroundColor(Color.WHITE);

        m_clear = new Button(context);
        m_clear.setText("Clear");
        m_clear.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                synchronized (m_shapes) {
                    m_shapes.clear();
                    redraw();
                }
            }});
        m_buttonBar.addView(m_clear, tlp2);

        m_canvasView = new View(context) {
        	boolean done = false;
            @Override
            protected void onDraw(Canvas canvas) {
            	if (!done) {
            		done = true;
            		m_canvas.drawRect(0, 0, m_canvasView.getRight(), m_canvasView.getBottom(), m_paint);
            	}
                if (m_bitmap != null) {
                    canvas.drawBitmap(m_bitmap, 0, 0, null);
                }
            }
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    SimpleShape shape = m_selectedShape;
                    ShapeComponent sc = new ShapeComponent(shape, x, y);
                    synchronized (m_shapes) {
                        m_shapes.add(sc);
                    }
                    if (shape != null) {
                        shape.draw(m_canvas, x, y);
                    }
                    m_canvasView.invalidate();
                }
                return true;
            }
            @Override
            protected void onAttachedToWindow() {
                m_tracker.open();
            }
        };
        m_main.addView(m_buttonBar, tlp);
        m_main.addView(m_canvasView, tlp);
        
        int width = m_canvasView.getWidth();
        int height = m_canvasView.getHeight();
        if (width < 1) {
            width = 800;
        }
        if (height < 1) {
            height = 800;
        }
        m_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        m_canvas = new Canvas(m_bitmap);
        m_paint = new Paint();
        m_paint.setAntiAlias(true);
        m_paint.setARGB(255, 255, 255, 255);
	    return m_main;
	}

    public Object addingService(final ServiceReference ref)
    {
        final SimpleShape shape = new DefaultShape(m_context, ref, (String) ref.getProperty(SimpleShape.NAME_PROPERTY));
        
        m_main.post(new Runnable() {
            public void run() {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                Button b = new Button(m_androidContext);
                b.setText((CharSequence) ref.getProperty(SimpleShape.NAME_PROPERTY));
//                b.setBackground(new BitmapDrawable((Bitmap) ref.getProperty(SimpleShape.ICON_PROPERTY)));
                b.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        m_selectedShape = shape;
                    }});
                
                m_buttonBar.addView(b, params);
                m_buttonMap.put(ref, b);
                redraw();
            }});
        return shape;
    }

    public void modifiedService(ServiceReference ref, Object svc)
    {
        // do nothing for now
    }

    public void removedService(final ServiceReference ref, Object svc)
    {
        ((DefaultShape) svc).dispose();
        try {
            m_main.post(new Runnable() {
                public void run() {
                    Button b = m_buttonMap.remove(ref);
                    m_buttonBar.removeView(b);
                    redraw();
                }});
        }
        catch (IllegalArgumentException e) {
            // ignore this (might occur during cleanup)
        }
    }
    
    private void redraw() {
        m_bitmap.eraseColor(Color.WHITE);
        synchronized (m_shapes) {
            for (ShapeComponent sc : m_shapes) {
                SimpleShape shape = sc.getShape();
                if (shape != null) {
                    shape.draw(m_canvas, sc.getX(), sc.getY());
                }
            }
        }
        m_canvasView.invalidate();
    }
}
