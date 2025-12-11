package components.musicdatabase;

/**
 * Customized JUnit test fixture for {@code MusicDatabase1} using default
 * constructor.
 */
public class MusicDatabase1Test extends MusicDatabaseTest {
    @Override
    protected final MusicDatabase constructorTest() {
        return new MusicDatabase1();
    }
}
