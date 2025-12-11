
import java.util.ArrayList;

import components.musicdatabase.MusicDatabase;
import components.musicdatabase.MusicDatabase1;
import components.musicdatabase.MusicDatabaseSecondary;
import components.musicdatabase.Song;

/**
 * A class meant to show some use cases of {@code MusicDatabase}.
 */
public class ProofOfConcept {

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        /* Initializes the database with an allocated size of 5 */
        final int initialSize = 5;
        MusicDatabase db1 = new MusicDatabase1(initialSize);

        /* Makes a new Song object */
        Song song1 = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "03:13");

        /* Adds song to database */
        db1.addEntry(song1);

        /* Prints song data to console */
        db1.printSong(song1);

        /*
         * Reads in song data from a tab delimited .txt file, without adding
         * duplicates
         */
        db1.readFromFile("data\\input\\FILE2.txt");

        /* Writes all song data to a tab delimited .txt file */
        db1.writeToFile("data\\output\\ProofOfConceptOutput");

        MusicDatabase db2 = db1.newInstance();
        /*
         * Appends all songs in db1 to db2, making them identical data-wise (and
         * in terms of .equals())
         */
        db2.append(db1);

        /* Remove songs matching a certain criteria from db2 */
        ArrayList<Song> entries = db2.removeEntries(
                MusicDatabaseSecondary.SearchField.ARTIST, "Hoshimachi Suisei");

        /* Make a new database and add the songs removed from db2 to db3 */
        MusicDatabase db3 = new MusicDatabase1();
        db3.addEntries(entries);

        /* Use db3 to print all songs in the ArrayList entries */
        db3.printSongs(entries);

    }
}
