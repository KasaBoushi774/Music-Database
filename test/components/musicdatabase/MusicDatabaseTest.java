package components.musicdatabase;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import components.musicdatabase.MusicDatabaseKernel.SearchField;

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
     * File 2 for testing, containing many lines data.
     */
    private static final String FILE2 = "data\\input\\FILE2.txt";

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

    /**
     * Test of addEntry.
     */
    @Test
    public void addEntryTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE1);
        Song song = new Song("Title", "Artist", "Album", "00:00");

        db1.addEntry(song);

        assertEquals(song, db1.getEntryByOrder(db1.size() - 1));
    }

    /**
     * Test of addEntry on empty db.
     */
    @Test
    public void addEntryWhenEmptyTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        Song song = new Song("Title", "Artist", "Album", "00:00");

        db1.addEntry(song);

        assertEquals(song, db1.getEntryByOrder(0));
    }

    /**
     * Test of getEntryByOrder.
     */
    @Test
    public void getEntryByOrderTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        Song song1 = new Song("Title", "Artist", "Album", "00:00");
        db1.addEntry(song1);
        Song song2 = new Song("Title1", "Artist5", "Album3", "10:10");
        db1.addEntry(song2);

        assertEquals(song1, db1.getEntryByOrder(0));
        assertEquals(song2, db1.getEntryByOrder(1));
    }

    /**
     * Test of getEntries using the ARTIST field.
     */
    @Test
    public void getEntriesArtistTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        ArrayList<Song> retrieved = db1.getEntries(SearchField.ARTIST,
                "Hoshimachi Suisei");

        /*
         * When searching in the ARTIST field with Hoshimachi Suisei, the two
         * songs put into the ArrayList below should be in the returned
         * ArrayList as well.
         */
        ArrayList<Song> expected = new ArrayList<Song>(2);
        expected.add(new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14"));
        expected.add(
                new Song("Bye Bye Rainy", "Hoshimachi Suisei", "", "03:20"));

        assertEquals(expected, retrieved);
    }

    /**
     * Test of getEntries using the ALBUM field.
     */
    @Test
    public void getEntriesAlbumTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        ArrayList<Song> retrieved = db1.getEntries(SearchField.ALBUM,
                "I'll put you in misery");

        /*
         * When searching in the ALBUM field with "I'll put you in misery", the
         * two songs put into the ArrayList below should be in the returned
         * ArrayList as well.
         */
        ArrayList<Song> expected = new ArrayList<Song>(2);
        expected.add(new Song("To bask in the rain", "TUYU",
                "I'll put you in misery", "03:19"));
        expected.add(new Song("Loser Girl", "TUYU", "I'll put you in misery",
                "03:18"));

        assertEquals(expected, retrieved);
    }

    /**
     * Test of removeEntries using the ALBUM field.
     */
    @Test
    public void removeEntriesAlbumTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        ArrayList<Song> retrieved = db1.removeEntries(SearchField.ALBUM,
                "I'll put you in misery");

        /*
         * When calling removeEntries using the ALBUM field with
         * "I'll put you in misery", the two songs put into the ArrayList below
         * should be in the returned ArrayList and not in db1 after the call.
         */
        ArrayList<Song> expected = new ArrayList<Song>(2);
        Song song1 = new Song("To bask in the rain", "TUYU",
                "I'll put you in misery", "03:19");
        expected.add(song1);
        Song song2 = new Song("Loser Girl", "TUYU", "I'll put you in misery",
                "03:18");
        expected.add(song2);

        assertEquals(expected, retrieved);
        assertEquals(false, db1.contains(song1));
        assertEquals(false, db1.contains(song2));
    }

    /**
     * Test of removeEntries when the given parameters don't match anything in
     * the db, and therefore nothing is removed.
     */
    @Test
    public void removeEntriesNothingToRemoveTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        MusicDatabase db2 = db1.newInstance();
        db2.append(db1);

        ArrayList<Song> retrieved = db1.removeEntries(SearchField.ALBUM,
                "Some random album");

        assertEquals(0, retrieved.size());
        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of removeEntry.
     */
    @Test
    public void removeEntryTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        Song song = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14");

        Song retrieved = db1.removeEntry(song);

        assertEquals(song, retrieved);
        assertEquals(false, db1.contains(song));
    }

    /**
     * Test of removeEntryByOrder.
     */
    @Test
    public void removeEntryByOrderTest() {
        final int three = 3;
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        Song song = new Song("UNDEAD", "YOASOBI", "", "03:03");

        Song retrieved = db1.removeEntryByOrder(three);

        assertEquals(song, retrieved);
        assertEquals(false, db1.contains(song));
    }

    /**
     * Test of contains.
     */
    @Test
    public void containsTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        Song song = new Song("UNDEAD", "YOASOBI", "", "03:03");

        assertEquals(true, db1.contains(song));
    }

    /**
     * Test of contains with the same object that was added.
     */
    @Test
    public void containsAfterAddingTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        Song song = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14");
        db1.addEntry(song);

        assertEquals(true, db1.contains(song));
    }

    /**
     * Test of contains when object is not in the db.
     */
    @Test
    public void containsWhenNotInTest() {
        MusicDatabase db1 = this.createFromArgsTest();

        Song song = new Song("UNDEAD", "YOASOBI", "", "03:03");

        assertEquals(false, db1.contains(song));
    }

    /**
     * Test of size when it is one.
     */
    @Test
    public void sizeWhenOneTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        Song song = new Song("UNDEAD", "YOASOBI", "", "03:03");
        db1.addEntry(song);

        assertEquals(1, db1.size());
    }

    /**
     * Test of size when it is zero.
     */
    @Test
    public void sizeWhenZeroTest() {
        MusicDatabase db1 = this.createFromArgsTest();

        assertEquals(0, db1.size());
    }

    /**
     * Test of size when it is many.
     */
    @Test
    public void sizeTest() {
        final int eight = 8;
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        assertEquals(eight, db1.size());
    }

    /**
     * Test of sort with TitleComparator.
     */
    @Test
    public void sortWithTitleCompTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);
        db1.sort(new MusicDatabaseSecondary.TitleComparator());

        /*
         * If sorting by title lexicographically, these two songs should be
         * first and last, respectively.
         */
        Song first = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14");
        Song last = new Song("UNDEAD", "YOASOBI", "", "03:03");

        assertEquals(first, db1.getEntryByOrder(0));
        assertEquals(last, db1.getEntryByOrder(db1.size() - 1));
    }

    /**
     * Test of sort with ArtistComparator.
     */
    @Test
    public void sortWithArtistCompTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);
        db1.sort(new MusicDatabaseSecondary.ArtistComparator());

        /*
         * If sorting by artist lexicographically, these two songs should be
         * first and last, respectively.
         */
        Song first = new Song("Kairikou (2025 Arrange ver.)", "Aitsuki Nakuru",
                "Shinsou", "04:42");
        Song last = new Song("UNDEAD", "YOASOBI", "", "03:03");

        assertEquals(first, db1.getEntryByOrder(0));
        assertEquals(last, db1.getEntryByOrder(db1.size() - 1));
    }

    /**
     * Test of sort with AlbumComparator.
     */
    @Test
    public void sortWithAlbumCompTest() {
        final int four = 4;
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);
        db1.sort(new MusicDatabaseSecondary.AlbumComparator());

        /*
         * If sorting by album lexicographically, these two songs should be
         * first and last, respectively.
         */
        Song first = new Song("Loser Girl", "TUYU", "I'll put you in misery",
                "03:18");
        Song last = new Song("Kairikou (2025 Arrange ver.)", "Aitsuki Nakuru",
                "Shinsou", "04:42");

        assertEquals(first, db1.getEntryByOrder(four));
        assertEquals(last, db1.getEntryByOrder(db1.size() - 1));
    }

    /**
     * Test of sort with LengthComparator.
     */
    @Test
    public void sortWithLengthCompTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);
        db1.sort(new MusicDatabaseSecondary.LengthComparator());

        /*
         * If sorting numerically by length in seconds, these two songs should
         * be first and last, respectively.
         */
        Song first = new Song("UNDEAD", "YOASOBI", "", "03:03");
        Song last = new Song("Kairikou (2025 Arrange ver.)", "Aitsuki Nakuru",
                "Shinsou", "04:42");

        assertEquals(first, db1.getEntryByOrder(0));
        assertEquals(last, db1.getEntryByOrder(db1.size() - 1));
    }

    /**
     * Test of readFromFile.
     */
    @Test
    public void readFromFileTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE1);

        /* When reading from FILE1, db1 should have all of these Song objects */
        Song first = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14");
        Song second = new Song("Bye Bye Rainy", "Hoshimachi Suisei", "",
                "03:20");
        Song third = new Song("KINGWORLD", "Shirakami Fubuki", "", "03:30");

        assertEquals(first, db1.getEntryByOrder(0));
        assertEquals(second, db1.getEntryByOrder(1));
        assertEquals(third, db1.getEntryByOrder(2));
    }

    /**
     * Test of writeToFile.
     */
    @Test
    public void writeToFileTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        MusicDatabase db2 = db1.newInstance();
        db1.readFromFile(FILE1);

        /*
         * If you write db1 to a file, then read that file into db2, db1 and db2
         * should be equal
         */
        db1.writeToFile("data\\output\\writeToFileTestOutput.txt");
        db2.readFromFile("data\\output\\writeToFileTestOutput.txt");

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of split when no matches are found.
     */
    @Test
    public void splitNoMatchesTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE1);
        MusicDatabase db2 = db1.newInstance();
        db2.append(db1);

        MusicDatabase db3 = db1.split(SearchField.TITLE, "E");

        assertEquals(true, db1.equals(db2));
        assertEquals(0, db3.size());
    }

    /**
     * Test of split.
     */
    @Test
    public void splitTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        MusicDatabase db2 = db1.split(SearchField.ARTIST, "Hoshimachi Suisei");

        Song song1 = new Song("Bye Bye Rainy", "Hoshimachi Suisei", "",
                "03:20");
        Song song2 = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14");

        assertEquals(true, db2.contains(song1));
        assertEquals(true, db2.contains(song2));
        assertEquals(false, db1.contains(song1));
        assertEquals(false, db1.contains(song2));
        assertEquals(2, db2.size());
    }

    /**
     * Test of equality after appending.
     */
    @Test
    public void appendEqualityTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        MusicDatabase db2 = db1.newInstance();
        db2.append(db1);

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of equality after appending one db to another with overlap in the
     * data they contain.
     */
    @Test
    public void appendEqualityOverlapTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE2);

        /*
         * Due to the nature of append, even if db2 already contains some songs
         * in db1, appending db1 to db2 should not add duplicates.
         */
        MusicDatabase db2 = db1.newInstance();
        db2.readFromFile(FILE1);
        db2.append(db1);

        assertEquals(true, db1.equals(db2));
    }

    /**
     * Test of equality after addEntries.
     */
    @Test
    public void addEntriesEqualityTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        Song song1 = new Song("Bye Bye Rainy", "Hoshimachi Suisei", "",
                "03:20");
        Song song2 = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14");
        db1.addEntry(song1);
        db1.addEntry(song2);

        ArrayList<Song> entries = new ArrayList<Song>();
        entries.add(song1);
        entries.add(song2);

        MusicDatabase db2 = db1.newInstance();
        db2.addEntries(entries);

        assertEquals(true, db1.equals(db2));
        assertEquals(2, db2.size());
    }

    /**
     * Test of toString.
     */
    @Test
    public void toStringTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        Song song1 = new Song("Bye Bye Rainy", "Hoshimachi Suisei", "",
                "03:20");
        Song song2 = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:14");
        db1.addEntry(song1);
        db1.addEntry(song2);

        String expected = "Bye Bye Rainy    Hoshimachi Suisei        03:20\n"
                + "AWAKE    Hoshimachi Suisei    Shinsei Mokuroku    03:14\n";

        String str = db1.toString();

        assertEquals(expected, str);
    }

    /**
     * Test of equals.
     */
    @Test
    public void equalsTest() {
        MusicDatabase db1 = this.createFromArgsTest();
        db1.readFromFile(FILE1);

        MusicDatabase db2 = this.createFromArgsTest();
        db2.readFromFile(FILE1);

        assertEquals(true, db1 instanceof MusicDatabase1);
        assertEquals(true, db2 instanceof MusicDatabase1);

        for (int i = 0; i < db1.size(); i++) {
            assertEquals(true,
                    db1.getEntryByOrder(i).equals(db2.getEntryByOrder(i)));
        }

        assertEquals(true, db1.equals(db2));
    }
}
