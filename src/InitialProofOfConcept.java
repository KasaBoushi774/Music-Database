import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A class with some implementations of musicPlaylist methods meant to show
 * viability of the design.
 */
public class InitialProofOfConcept {

    /**
     * An internal class containing song data, with methods to set and get
     * parameters (title, artist, album, length).
     */
    public static class Song {

        /*
         * Private members--------------------------------
         */

        /**
         * Song title.
         */
        private String title;

        /**
         * Song artist.
         */
        private String artist;

        /**
         * The album the song is from.
         */
        private String album;

        /**
         * Song length.
         */
        private String length;

        /*
         * Methods------------------------------------------
         */

        /**
         * Returns {@code this.title}.
         *
         * @return The title of the song
         */
        public String title() {
            return this.title;
        }

        /**
         * Returns {@code this.artist}.
         *
         * @return The artist of the song
         */
        public String artist() {
            return this.artist;
        }

        /**
         * Returns {@code this.album}.
         *
         * @return The album of the song
         */
        public String album() {
            return this.album;
        }

        /**
         * Returns {@code this.length}.
         *
         * @return The length of the song
         */
        public String length() {
            return this.length;
        }

        /**
         * Sets {@code this.title} to the input parameter {@code title}.
         *
         * @param title
         *            The title to set this.title to.
         * @requires title != null && title != ""
         */
        public void setTitle(String title) {
            assert title != null : "Violation of : title != null";
            assert title != "" : "Violation of : title != \"\"";

            this.title = title;
        }

        /**
         * Sets {@code this.artist} to the input parameter {@code artist}.
         *
         * @param artist
         *            The value to set this.artist to.
         * @requires artist != null
         */
        public void setArtist(String artist) {
            assert artist != null : "Violation of : artist != null";

            this.artist = artist;
        }

        /**
         * Sets {@code this.album} to the input parameter {@code album}.
         *
         * @param album
         *            The value to set this.album to.
         * @requires album != null
         */
        public void setAlbum(String album) {
            assert album != null : "Violation of : album != null";

            this.album = album;
        }

        /**
         * Sets {@code this.length} to the input parameter {@code length}.
         *
         * @param length
         *            The value to set this.length to.
         * @requires length != null
         */
        public void setLength(String length) {
            assert length != null : "Violation of : length != null";

            this.length = length;
        }

        /**
         * Constructor that takes in all required song information.
         *
         * @param title
         *            Title of the song
         * @param artist
         *            Artist of the song
         * @param length
         *            Length of the song
         * @param album
         *            Album of the song
         * @requires title, artist, album, and length are neither null nor ""
         */
        public Song(String title, String artist, String length, String album) {
            assert title != null : "Violation of : title != null";
            assert title != "" : "Violation of : title != \"\"";
            assert artist != null : "Violation of : title != null";
            assert artist != "" : "Violation of : title != \"\"";
            assert album != null : "Violation of : title != null";
            assert length != null : "Violation of : title != null";
            assert length != "" : "Violation of : title != \"\"";

            this.title = title;
            this.artist = artist;
            this.album = album;
            this.length = length;
        }

        /**
         * Constructor that takes in all required song information except album.
         *
         * @param title
         *            Title of the song
         * @param artist
         *            Artist of the song
         * @param length
         *            Length of the song
         * @requires title, artist, and length are neither null nor ""
         */
        public Song(String title, String artist, String length) {
            assert title != null : "Violation of : title != null";
            assert title != "" : "Violation of : title != \"\"";
            assert artist != null : "Violation of : title != null";
            assert artist != "" : "Violation of : title != \"\"";
            assert length != null : "Violation of : title != null";
            assert length != "" : "Violation of : title != \"\"";

            this.title = title;
            this.artist = artist;
            this.length = length;
        }
    }

    /**
     * ArrayList containing {@code Song} objects used to represent the playlist.
     */
    private ArrayList<Song> songs;

    /**
     * No argument constructor.
     */
    public InitialProofOfConcept() {
        this.songs = new ArrayList<Song>();
    }

    /**
     * A custom comparator to compare properties of the song class.
     */
    class SongComparator implements Comparator<Song> {
        @Override
        public int compare(Song s1, Song s2) {
            return s2.title().compareToIgnoreCase(s1.title());
        }
    }

    /*
     * Kernel methods---------------------------------------------------------
     */

    /**
     * Adds an entry to the database.
     *
     * @param title
     *            The title of the song
     * @param artist
     *            The artist of the song
     * @param length
     *            The length of the song
     * @param album
     *            The album the song is from
     * @requires title != null AND title != ""
     */
    public void addEntry(String title, String artist, String length,
            String album) {
        assert title != null : "Violation of: title != null";
        assert title != "" : "Violation of: title != \"\"";

        Song song = new Song(title, artist, length, album);

        this.songs.add(song);
    }

    /**
     * Adds an entry to the database. No album version.
     *
     * @param title
     *            The title of the song
     * @param artist
     *            The artist of the song
     * @param length
     *            The length of the song
     * @requires title, artist, and length are neither null nor ""
     */
    public void addEntry(String title, String artist, String length) {
        assert title != null : "Violation of : title != null";
        assert title != "" : "Violation of : title != \"\"";
        assert artist != null : "Violation of : title != null";
        assert artist != "" : "Violation of : title != \"\"";
        assert length != null : "Violation of : title != null";
        assert length != "" : "Violation of : title != \"\"";

        Song song = new Song(title, artist, length);

        this.songs.add(song);
    }

    /**
     * Adds an entry to the database. Song version.
     *
     * @param song
     *            An object of type {@code Song} to add to the database.
     * @requires song != null
     */
    public void addEntry(Song song) {
        assert song != null : "Violation of: song != null";

        this.songs.add(song);
    }

    /**
     * A method that takes a parameter ("title", "artist", "album", "length"),
     * to search by and a value to use to search.
     *
     * @param para
     * @param value
     * @return An array of all Songs with a parameter matching the given value.
     * @requires para == "title" || para == "artist" || para == "album" || para
     *           == "length" && value != null
     */
    public ArrayList<Song> getEntries(String para, String value) {
        assert para == "title" || para == "artist" || para == "album"
                || para == "length" : "Violation of : para == "
                        + "\"title\" OR \"artist\" OR \"album\" OR \"length\"";
        final int five = 5;

        ArrayList<Song> foundSongs = new ArrayList<Song>(five);

        if (para == "title") {
            for (Song song : this.songs) {
                if (song.title() == value) {
                    foundSongs.add(song);
                }
            }

        } else if (para == "artist") {
            for (Song song : this.songs) {
                if (song.artist() == value) {
                    foundSongs.add(song);
                }
            }

        } else if (para == "album") {
            for (Song song : this.songs) {
                if (song.album() == value) {
                    foundSongs.add(song);
                }
            }

        } else if (para == "length") {
            for (Song song : this.songs) {
                if (song.length() == value) {
                    foundSongs.add(song);
                }
            }
        }

        return foundSongs;
    }

    //public Song remove(String title, String artist, String length,
    //        String album) {
    //
    //}

    /*
     * Secondary
     * methods-----------------------------------------------------------------
     */

    /**
     * Sorts the database based on alphabetic order of each song's title.
     */
    public void sort() {
        Collections.sort(this.songs, new SongComparator());
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
         * The Song constructor and addEntry methods have been changed
         * (positions of length and album are swapped), so this code must be
         * modified slightly to work properly. Right now it would produce
         * incorrect output.
         */
        InitialProofOfConcept db = new InitialProofOfConcept();

        db.addEntry("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku", "3:18");

        Song suiSong1 = new Song("comet", "Hoshimachi Suisei", "4:27");
        db.addEntry(suiSong1);

        db.addEntry("BIBIDIBA", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "3:19");

        db.addEntry("Stellar Stellar", "Hoshimachi Suisei",
                "Still Still Stellar", "3:19");

        db.addEntry("NEXT COLOR PLANET", "Hoshimachi Suisei",
                "Still Still Stellar", "4:34");

        db.addEntry("Infinity Karat", "Nanami Urara", "3:25");

        System.out.println("Prints all songs by Hoshimachi Suisei.");

        ArrayList<Song> suiseiSongs = db.getEntries("artist",
                "Hoshimachi Suisei");
        for (Song song : suiseiSongs) {
            System.out.println(song.title());
        }

        System.out.println(
                "-----------------------------------------------------");
        System.out.println("Prints all songs in the album Shinsei Mokuroku.");

        ArrayList<Song> shinseiMokurokuSongs = db.getEntries("album",
                "Shinsei Mokuroku");
        for (Song song : shinseiMokurokuSongs) {
            System.out.println(song.title());
        }

        System.out.println(
                "-----------------------------------------------------");
        System.out
                .println("Prints all songs in the album Still Still Stellar.");

        ArrayList<Song> stillStillStellarSongs = db.getEntries("album",
                "Still Still Stellar");
        for (Song song : stillStillStellarSongs) {
            System.out.println(song.title());

        }

        System.out.println(
                "-----------------------------------------------------");
        System.out.println("Prints out all songs with the same length.");

        ArrayList<Song> similarLengthSongs = db.getEntries("length", "3:19");

        for (Song song : similarLengthSongs) {
            System.out.println(song.title());

        }

        System.out.println(
                "-----------------------------------------------------");
        System.out.println("Prints out all songs by Nanami Urara.");

        ArrayList<Song> nanamiUraraSongs = db.getEntries("artist",
                "Nanami Urara");

        for (Song song : nanamiUraraSongs) {
            System.out.println(song.title());

        }

        db.sort();
    }

    /*
     * Some notes: the sort method will be more useful once I implement the
     * method that prints to a .csv file. I unfortunately did not have enough
     * time to implement a removeEntry() method because I spent too much time
     * figuring out which data structure I should use, but, I'm thinking of
     * implementing two types: one that takes only one parameter and returns an
     * ArrayList of all relevant songs found, then goes through each
     * sequentially, displaying the song's information and asking if the user
     * wants remove it (there will alternatively be a "yes to all" option). This
     * first option will use the second option under the hood to remove any
     * songs the user confirms. The second version will be a specific remove
     * version that takes title, artist, album, and length or title, artist, and
     * length, finds a song, confirms with the user, then deletes it.
     */
}
