/**
 * Class for quick tests.
 */
public final class MusicDatabaseQuickTester {

    /**
     * Simplified version of System.out.println for convenience.
     *
     * @param output
     */
    public static void out(String output) {
        System.out.println(output);
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private MusicDatabaseQuickTester() {
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        MusicDatabase db = new MusicDatabase1();

        db.readFromFile("data/input/file1.txt");

        Song song1 = new Song("AWAKE", "Hoshimachi Suisei", "Shinsei Mokuroku",
                "2:03:14");
    }
}
