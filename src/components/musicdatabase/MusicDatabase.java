package components.musicdatabase;

import java.util.ArrayList;

/**
 * {@code MusicDatabaseKernel} enhanced with secondary methods.
 */
public interface MusicDatabase extends MusicDatabaseKernel {

    /**
     * Adds to a database by reading from a tab-delimited .txt file.
     *
     * @param inputPath
     *            The path to the .txt file
     * @requires {@code inputPath} not null or "" AND the file it leads to is a
     *           tab delimited .txt file AND it is formatted such that the first
     *           row contains "Title", "Artist", "Album", "Length", and every
     *           row onwards is such that the first column contains the title,
     *           the second contains the artist, the third contains the album
     *           (optionally), and the fourth contains the length.
     * @ensures this contains all song data in the file {@code inputPath} points
     *          to
     * @updates this
     */
    void readFromFile(String inputPath);

    /**
     * Writes to a tab delimited .txt file from the database. If no path is
     * given the data will be printed to .\output\Music_Database.txt by default.
     * If no file exists in either case one will be created, and if a file does
     * exist, it will be overwritten.
     *
     * @param outputPath
     *            Path to create the .txt in. If "", the .txt file will be
     *            printed to .\output\Music_Database.txt by default.
     * @requires {@code outputPath} is a valid file path and can be printed to,
     *           or it is ""
     * @ensures All songs in this are printed to the output file such that the
     *          first row contains "Title", "Artist", "Album", "Length", and
     *          every row onwards is such that the first column contains the
     *          title, the second contains the artist, the third contains the
     *          album, and the fourth contains the length.
     */
    void writeToFile(String outputPath);

    /**
     * Prints out the details of the given {@code song} with nice formatting.
     *
     * @param song
     *            The {@code Song} object to print.
     * @requires song != null
     * @ensures The title, artist, length, and album (if present) of the given
     *          song are printed to the console.
     * @updates console
     */
    void printSong(Song song);

    /**
     * Prints out the details of all the {@code song} objects in the given
     * {@code ArrayList} with nice formatting.
     *
     * @param songs
     *            The {@code ArrayList} of {@code Song} objects to print.
     * @requires songs != null
     * @ensures The title, artist, length, and album (if present) of each song
     *          in the given {@code ArrayList} are printed to the console.
     * @updates console
     */
    void printSongs(ArrayList<Song> songs);

    /**
     * Makes a new database with all songs in this that have a matching value in
     * either their "title", "artist", "length", "album" (chosen via the field
     * enum) field, and removes those songs from this. If no matches are found,
     * returns an empty {@code MusicDatabase}.
     *
     * @param field
     *            An enum that can be either "title", "artist", "length", or
     *            "album"
     * @param value
     *            The value to split by in the chosen field
     * @return A new {@code MusicDatabase} containing the songs meeting the
     *         given criteria
     * @ensures split = All songs meeting the given criteria AND this = #this /
     *          all songs in split
     * @updates this
     */
    MusicDatabase split(SearchField field, String value);

    /**
     * Adds to this the songs in db that are not already in this.
     *
     * @param db
     *            The {@code MusicDatabase} to append to this
     * @requires db != null
     * @ensures this.entries = #this.entries * [the db.entries that were not
     *          already in this]
     * @updates this
     */
    void append(MusicDatabase db);

    /**
     * Adds the songs in the given {@code ArrayList} to this if they are not
     * already in this.
     *
     * @param songs
     *            The songs to be added to this
     * @ensures this.entries = #this.entries * [the songs.entries that were not
     *          already in this]
     * @updates this
     */
    void addEntries(ArrayList<Song> songs);

    /**
     * Checks this and db for equality by comparing their string representations
     * lexicographically.
     *
     * @param db
     * @return true if this and db are equal
     * @ensures equals = true if this = db
     */
    Boolean equals(MusicDatabase db);
}
