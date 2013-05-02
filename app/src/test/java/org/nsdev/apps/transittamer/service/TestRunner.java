package org.nsdev.apps.transittamer.service;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.internal.ActionBarSherlockCompat;
import com.actionbarsherlock.internal.ActionBarSherlockNative;
import org.junit.runners.model.InitializationError;
import org.nsdev.apps.transittamer.service.test.ActionBarSherlockRobolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by neal 13-03-27 7:58 PM
 */
public class TestRunner extends RobolectricTestRunner
{
    static
    {
        ActionBarSherlock.registerImplementation(ActionBarSherlockRobolectric.class);
        ActionBarSherlock.unregisterImplementation(ActionBarSherlockNative.class);
        ActionBarSherlock.unregisterImplementation(ActionBarSherlockCompat.class);
    }

    public TestRunner(Class<?> testClass) throws InitializationError
    {
        super(testClass);
    }
}
