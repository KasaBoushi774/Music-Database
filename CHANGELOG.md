# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Calendar Versioning](https://calver.org/) of
the following form: YYYY.0M.0D.

## 2025.12.08

### Added

- Added tests for no-arg constructor, arg constructor, iterator hasNext, iterator next, iterator remove, and clear.

- Added ensureCapacity() method to kernel

### Changed

- Changed equals() method to correctly return a boolean rather than an int

- Modified file structure to be better organized

## 2025.11.23

### Added

- Added asserts to Song class constructor to ensure correct formatting

## 2025.11.22

### Added

- Added comparator classes to MusicDatabaseSecondary for comparing the title, artist, and album of a song by their natural order (lexicographically), and a comparator class for comparing the length of a song by amount of seconds.

- Implemented sort() method

- Implemented removeEntries()

- Added remove() method to iterator

## 2025.11.20

### Added

- Added removeEntryByOrder() contract to kernel and method implementation to MusicDatabase1

- Implemented getEntryByOrder(), getEntries(), and iterator with methods hasNext() and next()

### Changed

- Changed name of getEntryInOrder() to getEntryByOrder() to better reflect functionality and align with removeEntryByOrder()

- Modified some contracts (and corresponding implementations) for better error checking and improved clarity in descriptions.

## 2025.11.19

### Added

- Made constructors, createNewRep(), clear(), newInstance(), transferFrom(), addEntry(), removeEntry(), contains(), size()

- Made MusicDatabase1.java

## 2025.11.10

### Changed

- Moved sort method to kernel methods rather than secondary methods to improve ease of implementation

### Added

- Finished adding error checking to readFromFile() and writeToFile()

## 2025.11.08

### Changed

- Split isValidTDFile() into isValidHeader() and isValidDataRow()

- Modified readFromFile() to better check for errors and integrate contract checking methods (isTxt(), isValidHeader(), isValidDataRow())

## 2025.11.07

### Added

- Implemented toString(), equals()

### Changed

- Changed name of isValidTSVFile() to isValidTDFile()

## 2025.11.06

### Added

- Made utility method isTxt(), started utility method isValidTSVFile()

- Added addEntries() contract in MusicDatabase

- Implemented split(), append(), readToFile(), writeToFile(), addEntries()

### Changed

- Changed readFromFile() and writeToFile() to input/output tab-delimited .txt files instead of .csvs

- Changed name of merge() to append() and changed contract to better reflect actual behavior.

- Changed the removeEntries() contract to no longer ask user if they want to remove each match.

## 2025.11.05

### Added

- Implemented printSong(), printSongs()

- Made a MusicDatabaseQuickTester class

### Changed

- Changed contracts of readFromFile() and writeToFile() to reflect better .csv file formatting

- Changed names of displaySong() and displaySongs() to printSong() and printSongs()

- Switched to using a record class for Song

## 2025.11.04

### Added

- Made a MusicDatabaseSecondary class

### Removed

- MusicDatabase no longer extends Comparable

## 2025.10.26

### Added

- Added sort(), readFromFile(), writeToFile(), displaySong(), displaySongs(), split(), merge() methods to MusicDatabase.java

- Created MusicDatabase.java

- Added size(), contains(), getEntryInOrder(), an internal interface for Song to MusicDatabaseKernel.java

### Changed

- The internal class Song no longer has setter methods

- The addEntry methods that could take Strings for the fields of Song, create a Song, then put it into the database were removed in favor of only having the addEntry method that takes a Song.

## 2025.10.24

### Changed

- Switched to using the SearchField enum in getEntries() and removeEntries()

### Added

- Added addEntry(), getEntries(), removeEntries(), removeEntry() methods to MusicDatabaseKernel.java

- Created MusicDatabaseKernel.java

## 2025.10.09

### Added

- MusicDatabase1.java, which includes internal class Song, a comparator for comparing the title of songs alphabetically, a constructor for musicDatabase1, versions of addEntry, and getEntry. It also includes a main method used to demonstrate the methods.

## 2025.09.21

### Added

- Designed a Calculator component

## 2025.09.19

### Added

- Designed a MusicPlaylist component
- Designed a MusicDatabase component