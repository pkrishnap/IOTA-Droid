package com.example.android.sunshine.app.test;
        import android.test.suitebuilder.TestSuiteBuilder;

        import junit.framework.Test;
        import junit.framework.TestSuite;
/**
 * Created by kpeeramsetty on 10/21/2014.
 */


public class FullTestSuite extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(FullTestSuite.class)
                .includeAllPackagesUnderHere().build();
    }

    public FullTestSuite() {
        super();
    }
}