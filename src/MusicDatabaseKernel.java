import java.util.ArrayList;
import java.util.Comparator;

import components.standard.Standard;

/**
 * Component meant to model a database of music with primary methods. (Note: by
 * package-wide convention, all references are non-null.)
 */
public interface MusicDatabaseKernel
        extends Iterable<Song>, Standard<MusicDatabase> {

    /**
     * An enum for methods only intended to accept the four fields of the
     * {@code Song} class as one of their parameters (i.e. methods searching by
     * field). Possible values: TITLE, ARTIST, ALBUM, LENGTH
     */
    enum SearchField {
        /**
         * See above, this comment is just to remove a checkstyle issue.
         */
        TITLE, ARTIST, LENGTH, ALBUM
    }

    /**
     * Adds a {@code Song} object to the database if a duplicate does not
     * already exist.
     *
     * @param song
     *            An object of type {@code Song} to add to the database.
     * @requires song != null AND an identical Song object is not already in the
     *           database
     * @ensures this = #this * song
     * @updates this
     */
    void addEntry(Song song);

    /**
     * Returns the {@code Song} at position {@code n} in the database. Throws a
     * {@code NoSuchElementException} if there is no Song object at the
     * position.
     *
     * @param n
     *            An int representing the index at which the user wants to fetch
     *            a value from.
     * @return A {@code Song} if one exists at {@code n}.
     * @requires A {@code Song} object exists at index n of this
     * @ensures getEntryByOrder = The {@code Song} at position {@code n} if it
     *          exists
     */
    Song getEntryByOrder(int n);

    /**
     * A method that takes an enum (which can be TITLE, ARTIST, ALBUM, or
     * LENGTH), and a value to use to find matches within the {@code Song}
     * objects of the database. It then returns an {@code ArrayList} of all
     * matches found.
     *
     * @param field
     *            An enum (possible values: TITLE, ARTIST, ALBUM, LENGTH)
     *            representing the {@code Song} field to search through
     * @param value
     *            A string value to search by
     * @return An {@code ArrayList} of all {@code Songs} with a field matching
     *         the given value.
     * @requires value != null
     * @ensures getEntries = All Songs that have a value in {@code field}
     *          matching the {@code value} parameter
     */
    ArrayList<Song> getEntries(SearchField field, String value);

    /**
     * A method that takes an enum (which can be "title", "artist", "album", or
     * "length"), and a value to use to find matches within the {@code Song}
     * objects of the database. For each match it finds, it will remove it from
     * this and add it to the returned {@code ArrayList}.
     *
     * @param field
     *            An enum representing the {@code Song} field to search through
     * @param value
     *            A string value to search by
     * @return An {@code ArrayList} of all {@code Songs} with a field matching
     *         the given value.
     * @requires value != null
     * @ensures getEntries = All Songs that have a value in {@code field}
     *          matching the {@code value} parameter
     * @updates this
     */
    ArrayList<Song> removeEntries(SearchField field, String value);

    /**
     * Takes in a {@code Song} and removes it from the database.
     *
     * @param song
     *            A {@code Song} object to be searched for and removed, then
     *            returned.
     * @return The {@code Song} removed.
     * @requires {@code song} is in this
     * @ensures this = #this / song
     * @updates this
     */
    Song removeEntry(Song song);

    /**
     * Takes in an index and removes the {@code Song} there from the database.
     * Returns an error if no entry exists at n.
     *
     * @param n
     *            The index of a song to be removed
     * @return The {@code Song} removed.
     * @requires An entry exists at n.
     * @ensures this = #this / [song at n]
     * @updates this
     */
    Song removeEntryByOrder(int n);

    /**
     * A method that takes in a Song object and checks for a match in the
     * database.
     *
     * @param song
     *            A {@code Song} object to be searched for
     * @return A boolean
     * @ensures contains = true if {@code song} is in this
     */
    Boolean contains(Song song);

    /**
     * Returns the number of songs in the database.
     *
     * @return the number of songs in the database.
     * @ensures size = this.size
     */
    int size();

    /**
     * Sorts the database based on the provided {@code Comparator}.
     *
     * @param order
     *            A {@code Comparator<MusicDatabase>} used to sort the database.
     * @ensures The database is sorted according to {@code order}
     * @updates this
     */
    void sort(Comparator<Song> order);
}
