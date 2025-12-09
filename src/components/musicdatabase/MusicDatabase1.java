package components.musicdatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * {@code MusicDatabase} represented as an {@link java.util.ArrayList
 * java.util.ArrayList} with implementations of primary methods.
 *
 * @convention No songs in this are null. There are no duplicate {@code Song}
 *             objects in this. This only contains {@code Song} objects.
 * @correspondence this is represented as an ArrayList of {@code Song} objects
 */
public class MusicDatabase1 extends MusicDatabaseSecondary {

    /*
     * Private members.
     */

    /**
     * The default initial size of the underlying {@code ArrayList} if the
     * no-argument constructor is used.
     */
    private static final int DEFAULT_SIZE = 20;

    /**
     * Representation of {@code this}.
     */
    private ArrayList<Song> db;

    /*
     * Constructors.
     */

    /**
     * No-argument constructor.
     */
    public MusicDatabase1() {
        this.createNewRep(DEFAULT_SIZE);
    }

    /**
     * Constructor that allows user to set initial size of the underlying
     * {@code ArrayList}.
     *
     * @param size
     *            The initial size of the underlying {@code ArrayList}
     */
    public MusicDatabase1(int size) {
        this.createNewRep(size);
    }

    /**
     * Creator of initial representation.
     *
     * @param initSize
     *            Determines how large the underlying {@code ArrayList} is
     *            initially
     */
    private void createNewRep(int initSize) {
        this.db = new ArrayList<Song>(initSize);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Iterator<Song> iterator() {
        Iterator<Song> it = new Iterator<Song>() {
            private int pos = -1;
            private Boolean nextCalled = false;

            @Override
            public boolean hasNext() {
                Boolean hasNextValue = false;
                if ((this.pos + 1) < MusicDatabase1.this.db.size()) {
                    hasNextValue = true;
                }
                return hasNextValue;
            }

            @Override
            public Song next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException(
                            "ERROR: No more values in this");
                }
                this.pos++;
                Song value = MusicDatabase1.this.db.get(this.pos);
                this.nextCalled = true;
                return value;
            }

            @Override
            public void remove() {
                assert this.nextCalled : ""
                        + "Violation of: next() has been called at least "
                        + "once before this method.";

                MusicDatabase1.this.db.remove(this.pos);
                this.pos--;

                this.nextCalled = false;
            }
        };
        return it;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void clear() {
        this.createNewRep(DEFAULT_SIZE);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public MusicDatabase newInstance() {
        return new MusicDatabase1();
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void transferFrom(MusicDatabase source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof MusicDatabase1 : ""
                + "Violation of: source is of dynamic type MusicDatabase1";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        MusicDatabase1 temp = (MusicDatabase1) source;
        this.db = temp.db;
        temp.createNewRep(DEFAULT_SIZE);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void ensureCapacity(int cap) {
        assert cap > 0 : "Violation of: cap > 0";

        this.db.ensureCapacity(cap);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void addEntry(Song song) {
        assert song != null : "Violation of: song != null";
        assert !this.db.contains(song) : "Violation of: song is not in this";

        this.db.add(song);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Song getEntryByOrder(int n) {
        /*
         * ArrayList's get method will already return an error if n is out of
         * bounds, so so this method only needs to throw an exception if an
         * element doesn't exist.
         */
        Song entry = this.db.get(n);

        if (entry == null) {
            throw new NoSuchElementException(
                    "ERROR: No element exists at the given position");
        }

        return entry;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public ArrayList<Song> getEntries(MusicDatabaseKernel.SearchField field,
            String value) {
        assert value != null : "Violation of: value != null";

        ArrayList<Song> list = new ArrayList<Song>();

        for (Song song : this.db) {
            switch (field) {
                case TITLE: {
                    if (song.title().equals(value)) {
                        list.add(song);
                    }
                    break;
                }
                case ARTIST: {
                    if (song.artist().equals(value)) {
                        list.add(song);
                    }
                    break;
                }
                case ALBUM: {
                    if (song.album().equals(value)) {
                        list.add(song);
                    }
                    break;
                }
                case LENGTH: {
                    if (song.length().equals(value)) {
                        list.add(song);
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
        return list;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public ArrayList<Song> removeEntries(MusicDatabaseKernel.SearchField field,
            String value) {
        assert value != null : "Violation of: value != null";

        ArrayList<Song> list = new ArrayList<Song>();

        ListIterator<Song> dbIt = this.db.listIterator();

        while (dbIt.hasNext()) {
            Song song = dbIt.next();

            switch (field) {
                case TITLE: {
                    if (song.title().equals(value)) {
                        list.add(song);
                        dbIt.remove();
                    }
                    break;
                }
                case ARTIST: {
                    if (song.artist().equals(value)) {
                        list.add(song);
                        dbIt.remove();
                    }
                    break;
                }
                case ALBUM: {
                    if (song.album().equals(value)) {
                        list.add(song);
                        dbIt.remove();
                    }
                    break;
                }
                case LENGTH: {
                    if (song.length().equals(value)) {
                        list.add(song);
                        dbIt.remove();
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }

        return list;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Song removeEntry(Song song) {
        assert this.db.contains(song) : "Violation of: song is in this";

        this.db.remove(song);
        return song;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Song removeEntryByOrder(int n) {
        /*
         * ArrayList's get method will already return an error if n is out of
         * bounds, so so this method only needs to throw an exception if an
         * element doesn't exist.
         */
        Song entry = this.db.get(n);

        if (entry == null) {
            throw new NoSuchElementException(
                    "ERROR: No element exists at the given position");
        }

        return this.db.remove(n);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Boolean contains(Song song) {
        return this.db.contains(song);
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public int size() {
        return this.db.size();
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void sort(Comparator<Song> order) {
        this.db.sort(order);
    }
}
