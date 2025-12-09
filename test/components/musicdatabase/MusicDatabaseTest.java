package components.musicdatabase;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Test;

/**
 * JUnit test fixture for {@code MusicDatabase}'s constructor and kernel
 * methods.
 *
 * @author Jacksen Cooper
 *
 */
public abstract class MusicDatabaseTest {

    /**
     * File 1 for testing, containing a few lines of data.
     */
    private static final String FILE1 = "data\\input\\file1.txt";

    /**
     * Invokes the appropriate {@code MusicDatabase} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new database
     * @ensures constructorTest = {}
     */
    protected abstract MusicDatabase constructorTest();

    /**
     *
     * Creates and returns a {@code MusicDatabase} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the initial size of the map, if given
     * @return the constructed database
     * @requires <pre>
     * [args.length < 2]
     * </pre>
     * @ensures createFromArgsTest = a {@code MusicDatabase} with either the
     *          given initial size or the default initial size.
     */
    private MusicDatabase createFromArgsTest(int... args) {
        assert args.length < 2 : "Violation of: args.length < 2";

        MusicDatabase db = this.constructorTest();

        if (args.length > 0) {
            db.ensureCapacity(args[0]);
        }

        return db;
    }

    /**
     * Test of whether the constructor returns the same initial object.
     */
    @Test
    public void constructorEqualityTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        MusicDatabase db2 = this.createFromArgsTest();

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of whether the constructor returns the same initial object, given an
     * initial size parameter.
     */
    @Test
    public void constructorParamEqualityTest() {
        final int fifty = 50;
        MusicDatabase db1 = new MusicDatabase1(fifty);
        MusicDatabase db2 = new MusicDatabase1(fifty);

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of iterator's hasNext method with all three song objects in FILE1.
     */
    @Test
    public void itHasNextTest() {
        MusicDatabase db1 = this.createFromArgsTest();

        db1.readFromFile(FILE1);

        Iterator<Song> it = db1.iterator();

        assertEquals(true, it.hasNext());
        it.next();
        assertEquals(true, it.hasNext());
        it.next();
        assertEquals(true, it.hasNext());
    }

    /**
     * Test of iterator's hasNext method when db1 is empty.
     */
    @Test
    public void itHasNextWhenEmptyTest() {
        MusicDatabase db1 = this.createFromArgsTest();

        Iterator<Song> it = db1.iterator();

        assertEquals(false, it.hasNext());
    }

    /**
     * Test of iterator's next method with one song.
     */
    @Test
    public void itNextTest() {
        MusicDatabase db1 = this.createFromArgsTest();

        db1.addEntry(new Song("Title", "Artist", "Album", "00:00"));

        Iterator<Song> it = db1.iterator();

        assertEquals("Artist", it.next().artist());
        assertEquals(false, it.hasNext());
    }

    /**
     * Test of iterator's next method with all three song objects in FILE1.
     */
    @Test
    public void itNextMultiSongTest() {
        MusicDatabase db1 = this.createFromArgsTest();

        db1.readFromFile(FILE1);

        Iterator<Song> it = db1.iterator();

        /* The titles of the three songs in FILE1 */
        String songTitle1 = "AWAKE";
        String songTitle2 = "Bye Bye Rainy";
        String songTitle3 = "KINGWORLD";

        /*
         * The title of the song returned from the iterator should be equal to
         * songTitle1/2/3 at each step
         */
        assertEquals(songTitle1, it.next().title());

        assertEquals(songTitle2, it.next().title());

        assertEquals(songTitle3, it.next().title());
    }

    /**
     * Test of iterator's remove method.
     */
    @Test
    public void itRemoveTest() {
        final int three = 3;
        MusicDatabase db1 = this.createFromArgsTest(three);

        db1.readFromFile(FILE1);
        MusicDatabase db2 = db1.newInstance();
        db2.append(db1);
        Iterator<Song> it = db1.iterator();

        Song nextReturned = it.next();
        Song nextExpected = db2.getEntryByOrder(0);

        assertEquals(nextExpected, nextReturned);

        it.remove();
        db2.removeEntryByOrder(0);

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of clear when not empty.
     */
    @Test
    public void clearNotEmptyTest() {
        final int three = 3;
        MusicDatabase db1 = this.createFromArgsTest(three);
        MusicDatabase db2 = this.createFromArgsTest(three);

        db1.readFromFile(FILE1);
        db1.clear();

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of clear when empty.
     */
    @Test
    public void clearEmptyTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        MusicDatabase db2 = this.createFromArgsTest();

        db1.clear();

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of newInstance.
     */
    @Test
    public void newInstanceTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        MusicDatabase db2 = db1.newInstance();

        assertEquals(true, db2 instanceof MusicDatabase1);
    }

    /**
     * Test of transferFrom.
     */
    @Test
    public void transferFromTest() {
        /* Initialize db1 and load data from FILE1 into it */
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE1);

        /* Make a copy of it in db2, then test for equality between them */
        MusicDatabase db2 = db1.newInstance();
        db2.append(db1);
        assertEquals(true, db1.equals(db2));

        /*
         * Make a new object, db3, then transfer db2's data into it and compare
         * it to db1 for equality. If db1 and db2 are equal, db1 and db3 should
         * be equal after transferFrom
         */
        MusicDatabase db3 = db1.newInstance();
        db3.transferFrom(db2);
        assertEquals(true, db1.equals(db3));
    }

}
