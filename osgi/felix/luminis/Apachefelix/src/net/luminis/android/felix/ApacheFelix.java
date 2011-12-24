package net.luminis.android.felix;

import net.luminis.android.felix.view.ActivityService;
import net.luminis.android.felix.view.ViewFactory;

import org.apache.felix.framework.Felix;
import org.apache.felix.framework.Logger;
import org.apache.felix.framework.util.FelixConstants;
import org.apache.felix.framework.util.StringMap;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class ApacheFelix extends Activity implements ActivityService {
  public static final String FELIX_CACHE_DIR = "/data/data/net.luminis.android.felix/cache";

    private static final String ANDROID_FRAMEWORK_PACKAGES = ("org.osgi.framework; version=1.3.0," +
                "org.osgi.service.packageadmin; version=1.2.0," +
                "org.osgi.service.startlevel; version=1.0.0," +
                "org.osgi.service.url; version=1.0.0," +
                "org.osgi.util.tracker," +
                "android; " + 
                "android.app;" + 
                "android.content;" + 
                "android.database;" + 
                "android.database.sqlite;" + 
                "android.graphics; " + 
                "android.graphics.drawable; " + 
                "android.graphics.glutils; " + 
                "android.hardware; " + 
                "android.location; " + 
                "android.media; " + 
                "android.net; " + 
                "android.net.wifi; " + 
                "android.opengl; " + 
                "android.os; " + 
                "android.provider; " + 
                "android.sax; " + 
                "android.speech.recognition; " + 
                "android.telephony; " + 
                "android.telephony.gsm; " + 
                "android.text; " + 
                "android.text.method; " + 
                "android.text.style; " + 
                "android.text.util; " + 
                "android.util; " + 
                "android.view; " + 
                "android.view.animation; " + 
                "android.webkit; " + 
                "android.widget; " + 
                "com.google.android.maps; " + 
                "com.google.android.xmppService; " + 
                "javax.crypto; " + 
                "javax.crypto.interfaces; " + 
                "javax.crypto.spec; " + 
                "javax.microedition.khronos.opengles; " + 
                "javax.net; " + 
                "javax.net.ssl; " + 
                "javax.security.auth; " + 
                "javax.security.auth.callback; " + 
                "javax.security.auth.login; " + 
                "javax.security.auth.x500; " + 
                "javax.security.cert; " + 
                "javax.sound.midi; " + 
                "javax.sound.midi.spi; " + 
                "javax.sound.sampled; " + 
                "javax.sound.sampled.spi; " + 
                "javax.sql; " + 
                "javax.xml.parsers; " + 
                "junit.extensions; " + 
                "junit.framework; " + 
                "org.apache.commons.codec; " + 
                "org.apache.commons.codec.binary; " + 
                "org.apache.commons.codec.language; " + 
                "org.apache.commons.codec.net; " + 
                "org.apache.commons.httpclient; " + 
                "org.apache.commons.httpclient.auth; " + 
                "org.apache.commons.httpclient.cookie; " + 
                "org.apache.commons.httpclient.methods; " + 
                "org.apache.commons.httpclient.methods.multipart; " + 
                "org.apache.commons.httpclient.params; " + 
                "org.apache.commons.httpclient.protocol; " + 
                "org.apache.commons.httpclient.util; " + 
                "org.bluez; " + 
                "org.json; " + 
                "org.w3c.dom; " + 
                "org.xml.sax; " + 
                "org.xml.sax.ext; " + 
                "org.xml.sax.helpers; " + 
                "version=1.0.0.r1," +
                "net.luminis.android.felix.view").intern();
    
    private File m_cache;
    private Felix m_felix;
    private StringMap m_configMap;
    private ServiceTracker m_tracker;

    private PrintStream m_out;

    protected String TAG = ApacheFelix.class.getSimpleName(); 
    
    /** Called when the activity is first created. 
     * @throws IOException */
    @Override
    public synchronized void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        Log.d(TAG, "onCreate().");
        m_out = new PrintStream(new OutputStream(){
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            @Override
            public void write(int oneByte) throws IOException {
                output.write(oneByte);
                if (oneByte == '\n') {
                    Log.v(TAG , new String(output.toByteArray()));
                    output = new ByteArrayOutputStream();
                }
            }});
        System.setErr(m_out);
        System.setOut(m_out);
        
        // set this property to disable the "spec" behaviour of deployment admin that will always
        // stop all bundles in a deployment package on every update (even if those bundles are not
        // even updated)
        System.setProperty("org.apache.felix.deploymentadmin.stopunaffectedbundle", "false");
        System.setProperty("gateway.discovery", "http://planetmarrs.xs4all.nl:8989/");
        System.setProperty("gateway.identification", "gPhone");
        
        m_configMap = new StringMap(false);
        m_configMap.put(FelixConstants.SERVICE_URLHANDLERS_PROP, "false");
        
        m_configMap.put(FelixConstants.LOG_LEVEL_PROP, String.valueOf(Logger.LOG_WARNING));
        
        // Add core OSGi packages to be exported from the class path
        // via the system bundle.
        m_configMap.put(Constants.FRAMEWORK_SYSTEMPACKAGES, ANDROID_FRAMEWORK_PACKAGES);
        
        // Explicitly specify the directory to use for caching bundles.
        m_cache = new File(FELIX_CACHE_DIR);
        if (!m_cache.exists()) {
            if (!m_cache.mkdirs()) {
                throw new IllegalStateException("Unable to create cache dir");
            }
        }

        m_configMap.put(Constants.FRAMEWORK_STORAGE, m_cache.getAbsolutePath());
    }
    
    @Override
    public synchronized void onStart() {
        super.onStart();
        setContentView(new View(this));
        try {
            List<BundleActivator> activators = new ArrayList<BundleActivator>();
//            activators.add(new net.luminis.liq.ma.webstart.Activator());
//            activators.add(new BundleActivator() {
//
//                public void start(BundleContext context) throws Exception {
//                    context.registerService(org.osgi.service.log.LogService.class.getName(), new org.osgi.service.log.LogService() {
//                public void log(int level, String message) {
//                    log(level, message, null);
//                }
//
//                public void log(int level, String arg1, Throwable arg2) {
//                    log(null, level, arg1, arg2);
//                }
//
//                public void log(ServiceReference ref, int level, String message) {
//                    log(ref, level, message, null);
//                }
//
//                public void log(ServiceReference ref, int level, String message, Throwable throwable) {
//                    m_out.println("FELIX: ServiceReference: " + ref + "\tlevel: " + level + 
//                            "\tmessage: " + message + "\tThrowable: " + throwable);
//                }}, null);
//                }
//
//                public void stop(BundleContext context) throws Exception {
//                }});
            
            m_configMap.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, activators);
            m_felix = new Felix(m_configMap);
            m_felix.start();
            
            m_felix.getBundleContext().registerService(ActivityService.class.getName(), this, null);
        } catch (BundleException ex) {
            throw new IllegalStateException(ex);
        }
        try {
         
            m_tracker = new ServiceTracker(m_felix.getBundleContext(), 
                    m_felix.getBundleContext().createFilter("(" + Constants.OBJECTCLASS + "=" + ViewFactory.class.getName() + ")"), 
                        new ServiceTrackerCustomizer() {

                        public Object addingService(ServiceReference ref) {
                            Log.d(TAG, "addingService(). Servicereference: "  + ref);
                            final ViewFactory fac = (ViewFactory) m_felix.getBundleContext().getService(ref);
                            if (fac != null) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        setContentView(fac.create(ApacheFelix.this));
                                    }
                                });
                            }   
                            return fac;
                        }

                        public void modifiedService(ServiceReference ref,
                                Object service) {
                            Log.d(TAG, "modifiedService(). ServiceReference: " + ref);
                            removedService(ref, service);
                            addingService(ref);
                        }

                        public void removedService(ServiceReference ref,
                                Object service) {
                            Log.d(TAG, "removedService(). ServiceReference: " + ref);
                            m_felix.getBundleContext().ungetService(ref);
                            // TODO Auto-generated method stub
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    setContentView(new View(ApacheFelix.this));
                                }
                            });
                        }});
            m_tracker.open();
            
            BundleContext bContext = m_felix.getBundleContext();
            String location = "";
            location += "/data/data/net.luminis.android.felix/";
            location += "bundles/net.luminis.android.servicebased.host.jar";
            
            location = "file:/sdcard/luminis/net.luminis.android.servicebased.host.jar";
            FileInputStream fin = new FileInputStream(new File("/sdcard/luminis/net.luminis.android.servicebased.host.jar"));
//            bContext.installBundle(location, fin);
            
//            FileInputStream bFile = openFileInput("bundles/net.luminis.android.servicebased.host.jar");
            
            bContext.installBundle(location, fin);
            
            
            location = "file:/sdcard/luminis/net.luminis.android.servicebased.circle.jar";
            fin = new FileInputStream(new File("/sdcard/luminis/net.luminis.android.servicebased.circle.jar"));
            bContext.installBundle(location, fin);
            
            org.osgi.framework.Bundle[] bundles = bContext.getBundles();
            for (org.osgi.framework.Bundle b : bundles) {
                Log.d(TAG, "bunndle: " +b);
                b.start();
            }
        } catch (InvalidSyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BundleException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
//            System.out.println("!!!!! Updating configuration !!!!!!");
//            BundleContext bundleContext = m_felix.getBundleContext();
//            ServiceTracker caTracker = new ServiceTracker(bundleContext, bundleContext.createFilter("(&(" + Constants.OBJECTCLASS + "=" + ManagedService.class.getName() + ")(" + Constants.SERVICE_PID + "=net.luminis.liq.scheduler))"), null);
//            caTracker.open();
//            ManagedService ca = (ManagedService) caTracker.waitForService(5000);
////            ConfigurationAdmin ca = (ConfigurationAdmin) caTracker.getService();
//            if (ca != null) {
//                ca.updated(new Properties() {{
//                    put("auditlog", "15000"); 
//                    put("net.luminis.liq.deployment.task.DeploymentUpdateTask", "15000");
//                    put("net.luminis.liq.deployment.task.DeploymentCheckTask", "600000");
//                }});
////                ca.updated(new Properties());
//                System.out.println("!!!!! Updated configuration !!!!!!");
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public synchronized void onStop() {
        super.onStop();
        m_tracker.close();
        m_tracker = null;
        try {
            m_felix.stop();
            m_felix.waitForStop(5000);
        }
        catch (BundleException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        m_felix = null;
    }
    
    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
//        delete(m_cache);
        m_configMap = null;
        m_cache = null;
    }
    
    private void delete(File target) {
        if (target.isDirectory()) {
            for (File file : target.listFiles()) {
                delete(file);
            }
        }
        target.delete();
    }

    public Activity getActivity() {
        return this;
    }
    
    public Object lookupSystemService(String name) {
        return getSystemService(name);
    }
}