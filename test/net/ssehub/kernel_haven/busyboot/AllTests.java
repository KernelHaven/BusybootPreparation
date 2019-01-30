package net.ssehub.kernel_haven.busyboot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests.
 * 
 * @author Adam
 */
@RunWith(Suite.class)
@SuiteClasses({
    AbstractBusybootPreparationTest.class,
    FloridaPreparationTest.class,
    PrepareBusyboxTest.class,
    PrepareCorebootTest.class,
    })
public class AllTests {

}
