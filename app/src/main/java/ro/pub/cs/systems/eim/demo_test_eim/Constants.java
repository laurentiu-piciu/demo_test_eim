package ro.pub.cs.systems.eim.demo_test_eim;

public interface Constants {

    final public static String TAG                  = "demo_test_eim";
    final public static boolean DEBUG               = true;
    final public static int NUMBER_OF_CLICKS_THRESHOLD = 5;
    final public static int SERVICE_STOPPED = 0;
    final public static int SERVICE_STARTED = 1;

    final public static String[] actionTypes = {
            "ro.pub.cs.systems.eim.demo_test_eim.arithmeticmean",
            "ro.pub.cs.systems.eim.demo_test_eim.geometricmean",
            "ro.pub.cs.systems.eim.demo_test_eim.anothermean"
    };
}