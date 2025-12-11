package components.musicdatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Layered implementation of secondary methods for {@code MusicDatabase}.
 */
public abstract class MusicDatabaseSecondary implements MusicDatabase {

    /*
     * ----------------------------Utility methods------------------------------
     */

    /**
     * Simplified version of System.out.println for convenience.
     *
     * @param output
     *            The string to be printed to the console
     */
    private static void out(String output) {
        System.out.println(output);
    }

    /**
     * Throws an IllegalArgumentException and prints an error message if the
     * given line is not a valid header. Used in readFromFile().
     *
     * @param line
     *            A line of text from a tab delimited file
     * @ensures isValidHeader throws an exception if line is a valid header in
     *          the format "Title\tArtist\tAlbum\tLength".
     */
    public static void isValidHeader(String line) {
        if (!line.equals("Title\tArtist\tAlbum\tLength")) {
            throw new IllegalArgumentException(
                    "ERROR: Header is incorrectly formatted. "
                            + "Header should be in the form"
                            + " \"Title\\tArtist\\tAlbum\\tLength\", "
                            + "where \\t is a tab");
        }
    }

    /**
     * Throws an exception if the given line of data is not properly formatted.
     * See the ensures tag for details. Also prints the number of the row
     * currently being checked. Used in readFromFile().
     *
     * @param line
     *            A line of text
     * @param rowNum
     *            The row currently being checked
     * @ensures An exception with a descriptive message is thrown for each
     *          individual case: 1. If the number of values separated by tabs is
     *          not four 2. If the first value in the line (title) is null or an
     *          empty string 3. If the second value in the line (artist) is null
     *          or an empty string 4. If the fourth value in the line (length)
     *          is null or an empty string
     */
    public static void isValidDataRow(String line, int rowNum) {
        /*
         * Splits line along any tabs, including empty strings.
         */
        String[] fields = line.split("\t", -1);
        /*
         * Checks the number of tabs in the input to make sure there are four
         * values, even if album is blank.
         */
        final int four = 4;
        if (fields.length != four) {
            throw new IllegalArgumentException("ERROR on row " + rowNum
                    + ": the given line is invalid. There must be four "
                    + "separate values separated by three total tabs "
                    + "in each row of data.");
        }
        /*
         * Checks to make sure title (index 0), artist (index 1), and length
         * (index 3) are not null.
         */
        if (fields[0] == null || fields[0] == "") {
            throw new IllegalArgumentException("ERROR on row " + rowNum
                    + ": Title is blank, a title is required for every data row.");
        }
        if (fields[1] == null || fields[1] == "") {
            throw new IllegalArgumentException("ERROR on row " + rowNum
                    + ": Artist is blank, an artist is required"
                    + " for every data row.");
        }
        final int three = 3;
        if (fields[three] == null || fields[three] == "") {
            throw new IllegalArgumentException("ERROR on row " + rowNum
                    + ": Length is blank, a length is required for every data row.");
        }
    }

    /**
     * Throws an IllegalArgumentException and prints an error message if the
     * file at the given file path is not a .txt file. Used in readFromFile().
     *
     * @param filePath
     *            The path to a file.
     * @requires filePath != null
     * @ensures isTxt = Whether the file at the given path is a text/plain file
     */
    public static void isTxt(String filePath) {
        assert filePath != null : "Violation of: filePath != null";

        try {
            Path pathToFile = Paths.get(filePath);

            if (!Files.probeContentType(pathToFile).equals("text/plain")) {
                throw new IllegalArgumentException(
                        "ERROR: file must be of type \".txt\", "
                                + "or more specifically, \"text/plain\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * ------------------------- Secondary methods -----------------------------
     */

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void readFromFile(String inputPath) {
        assert inputPath != null : "Violation of: inputPath != null";
        assert inputPath != "" : "Violation of: inputPath != \"\"";

        try (BufferedReader rdr = new BufferedReader(
                new FileReader(inputPath));) {
            isTxt(inputPath);

            String line = rdr.readLine();
            /*
             * The previous call to readLine() got the headers of the file,
             * which we don't care about the value of, so we need to call
             * readLine() again to get the first row of actual data. But before
             * that, we still need to make sure the header in the first line is
             * valid.
             */
            isValidHeader(line);

            line = rdr.readLine();
            int lineCount = 2;
            while (line != null) {
                isValidDataRow(line, lineCount);

                String[] fields = line.split("\t");
                final int three = 3;
                Song song = new Song(fields[0], fields[1], fields[2],
                        fields[three]);

                if (!this.contains(song)) {
                    this.addEntry(song);
                }

                line = rdr.readLine();
                lineCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void writeToFile(String outputPath) {
        assert outputPath != null : "Violation of: outputPath != null";

        BufferedWriter wrtr = null;
        try {
            String path = outputPath;
            if (outputPath == "") {
                path = ".\\output\\Music_Database";
            }

            File outputFile = new File(path);
            /*
             * Makes the parent folders of the given path if they don't already
             * exist.
             */
            File outputDirectory = outputFile.getParentFile();
            if (outputDirectory != null) {
                outputDirectory.mkdirs();
            }
            wrtr = new BufferedWriter(new FileWriter(outputFile));

            wrtr.write("Title\tArtist\tAlbum\tLength");
            wrtr.newLine();

            for (Song song : this) {
                wrtr.write(String.format("%s\t%s\t%s\t%s", song.title(),
                        song.artist(), song.album(), song.length()));
                wrtr.newLine();
            }

            wrtr.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (wrtr != null) {
                    wrtr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void printSong(Song song) {
        assert song != null : "Violation of: song != null";

        out("* Title: " + song.title());
        out("* Artist: " + song.artist());
        /*
         * Not all songs are part of an album, so this line will only print if a
         * song is part of an album
         */
        if (!song.album().equals("")) {
            out("* Album: " + song.album());
        }
        out("* Length: " + song.length());
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void printSongs(ArrayList<Song> songs) {
        assert songs != null : "Violation of: songs != null";

        out("Songs to be printed: " + songs.size());
        out("******************************");

        for (Song song : songs) {
            out("* Title: " + song.title());
            out("* Artist: " + song.artist());
            /*
             * Not all songs are part of an album, so this line will only print
             * if a song is part of an album
             */
            if (!song.album().equals("")) {
                out("* Album: " + song.album());
            }
            out("* Length: " + song.length());
            out("******************************");
        }
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public MusicDatabase split(MusicDatabaseKernel.SearchField field,
            String value) {
        assert value != null : "Violation of: value != null";

        MusicDatabase newDB = this.newInstance();
        ArrayList<Song> splitSongs = this.removeEntries(field, value);

        for (Song song : splitSongs) {
            newDB.addEntry(song);
        }

        return newDB;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void append(MusicDatabase db) {
        assert db != null : "Violation of: db != null";

        for (Song song : db) {
            if (!this.contains(song)) {
                this.addEntry(song);
            }
        }
    }

    /*
     * For the methods above and below, their functionality is identical, but
     * they take different parameters. Are they fine as is or should I combine
     * them into one overloaded method?
     */

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public void addEntries(ArrayList<Song> songs) {
        assert songs != null : "Violation of: songs != null";

        for (Song song : songs) {
            if (!this.contains(song)) {
                this.addEntry(song);
            }
        }
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public String toString() {
        String result = "";
        if (this.size() > 0) {
            StringBuilder strDB = new StringBuilder(this.size());
            for (Song song : this) {
                strDB.append(
                        String.format("%s    %s    %s    %s\n", song.title(),
                                song.artist(), song.album(), song.length()));
            }
            result = strDB.toString();
        }

        return result;
    }

    // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
    @Override
    public Boolean equals(MusicDatabase db) {
        return (this.toString().equals(db.toString()));
    }

    /*
     * ------------------------- Comparator Classes ----------------------------
     */

    /**
     * A comparator subclass that compares song objects lexicographically based
     * on their title fields.
     */
    public static class TitleComparator implements Comparator<Song> {

        // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public int compare(Song song1, Song song2) {
            return song1.title().compareToIgnoreCase(song2.title());
        }
    }

    /**
     * A comparator subclass that compares song objects lexicographically based
     * on their artist fields.
     */
    public static class ArtistComparator implements Comparator<Song> {

        // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public int compare(Song song1, Song song2) {
            return song1.artist().compareToIgnoreCase(song2.artist());
        }
    }

    /**
     * A comparator subclass that compares song objects lexicographically based
     * on their album fields. If the album of one song is blank, the other is
     * considered "greater". If both are blank or have the same album then their
     * titles are compared instead, and if both are non-blank they are compared
     * normally.
     */
    public static class AlbumComparator implements Comparator<Song> {

        // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public int compare(Song song1, Song song2) {
            if (song2.album() == "") {
                return 1;
            } else if (song1.album() == "") {
                return -1;
            } else if (song1.album() == "" && song2.album() == ""
                    || song1.album().equals(song2.album())) {
                return song1.title().compareToIgnoreCase(song2.title());
            } else {
                return song1.album().compareToIgnoreCase(song2.album());
            }
        }
    }

    /**
     * A comparator subclass that compares song objects based on their length in
     * seconds. The shortest songs will come first.
     */
    public static class LengthComparator implements Comparator<Song> {

        // CHECKSTYLE: ALLOW THIS METHOD TO BE OVERRIDDEN
        @Override
        public int compare(Song song1, Song song2) {
            final double sixty = 60;

            /*
             * Splits the length of the song into different parts by the colons
             * in it (2 parts if the length is minutes and seconds, 3 parts if
             * the song is hours, minutes, and seconds). Then adds the seconds,
             * minutes*60, and hours*60*60 to the total in a loop to get the
             * total seconds.
             */
            String[] song1Split = song1.length().split(":");
            int total1 = 0;
            for (int i = 0; i < song1Split.length; i++) {
                total1 += Integer
                        .parseInt(song1Split[song1Split.length - 1 - i])
                        * Math.pow(sixty, (double) i);
            }

            String[] song2Split = song2.length().split(":");
            int total2 = 0;
            for (int i = 0; i < song2Split.length; i++) {
                total2 += Integer
                        .parseInt(song2Split[song2Split.length - 1 - i])
                        * Math.pow(sixty, (double) i);
            }

            return Integer.compare(total1, total2);
        }
    }
}
