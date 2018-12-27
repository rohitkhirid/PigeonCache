import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PigeonCacheTest {

    private PigeonCache mPigeonCache;

    @Test
    public void insertionAndRetrievalTest() {
        Demo insertedDemo = Demo.getDummy();
        String key = mPigeonCache.put(insertedDemo);
        Demo receivedDemo = mPigeonCache.get(Demo.class, key);
        Assert.assertEquals(insertedDemo, receivedDemo);
    }

    @Before
    public void init() {
        mPigeonCache = PigeonCache.getInstance();
    }

    @BeforeEach
    void setUp() {
        // no-op
    }

    @AfterEach
    void tearDown() {
        // no-op
    }

    static class Demo {
        private String user = "user123";
        private int age = 9;
        private String email = "demo@noemail.com";

        public static Demo getDummy() {
            Demo demo = new Demo();
            return demo;
        }

        @Override
        public String toString() {
            return user + age + email;
        }
    }
}