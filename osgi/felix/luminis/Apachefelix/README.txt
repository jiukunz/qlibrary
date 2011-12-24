This project does some tricky stuff to create the felix-ma.jar which is 
the bundle that is put on the classpath for Android. The jar is created
from the supplied build.xml and must be run by hand when updating
either of the "donor" jars (felix.jar or n.l.l.ma.webstart.jar). The
issue is that these jars contain some similar files. OSGi can handle
that, Android can't and it complains.

