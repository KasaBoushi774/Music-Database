/**
 * A record class meant to hold song data. Parameters are self explanatory.
 *
 * @param title
 * @param artist
 * @param length
 * @param album
 * @return
 */
public record Song(String title, String artist, String album, String length) {

    /**
     * Song constructor. Putting empty quotes ("") in for the album field will
     * set it to the default value. Empty quotes are not allowed for any other
     * field though. Also, the length field must be in the format hh:mm:ss or
     * mm:ss.
     *
     * @param title
     * @param artist
     * @param album
     * @param length
     */
    public Song(String title, String artist, String album, String length) {
        final int four = 4;

        assert title != "" : "Violation of: title != \"\"";
        assert artist != "" : "Violation of: artist != \"\"";
        assert length != "" : "Violation of: length != \"\"";
        String[] test = length.split(":");
        assert test.length < four : ""
                + "Violation of: Songs can have a length of at most 99:99:99";
        for (String str : test) {
            assert str.length() == 2 : ""
                    + "Violation of: length string must be in format hh:mm:ss or mm:ss";
        }

        this.title = title;
        this.artist = artist;
        this.album = album;
        this.length = length;
    }
}
