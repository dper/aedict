# aedict

A clone of Aedict 2.  This is a fork of the original project on [Google Code](https://code.google.com/p/aedict/).  That source code base uses Mercurial, and here it has been converted to Git.  Aedict 2 was and is open source, although the original author dropped it in 2011 to work on the closed-source Aedict 3 (not included here).

# Download

Get an APK from the `releases` directory.  I recommend `Aedict.2.9.4.apk`.

The `Aedict.2.9.4.apk` release is newer, but I don't think it has any extra features.  The author added an obnoxious pop-up request for money to the last release before switching to closed source.  I have no respect for nagware, and I recommend against using it.  The APK is there for posterity's sake.

The dictionary files this program tries to get are on an old server that is still running as of 2015 but will not be replaced when the hardware finally fails.  The relevant data files can be found on another server.

* [edict.gz](ftp://ftp.edrdg.org/pub/Nihongo/edict.gz)
* [enamdict.gz](ftp://ftp.edrdg.org/pub/Nihongo/enamdict.gz)
* [compdic.gz](ftp://ftp.edrdg.org/pub/Nihongo/compdic.gz)

# Description

A free, open-source English-Japanese dictionary for Android which uses Jim Breen's edict data. Does not require japanese keyboard. Works offline. The dictionary data is downloaded automatically. 

# Features

* Simple user interface, tested on HTC Magic. No settings, just type a word and do a search.
* Automatic download of the indexed EDict/Kanjidic/Tanaka dictionaries (Warning: the dictionaries files are quite large, e.g. English EDict zip file is 8mb long, which may take some time to download. Perform this download over a Wi-Fi or with a quick internet access if possible).
* Allows automatic download of a German/French EDict, the names dictionary and the computer terminology dictionary.
* Once the dictionary is downloaded it works completely off-line.
* Kanjipad for drawing and searching kanji characters.
* Search kanji characters by radicals and stroke numbers.
* Shows a kanji analysis in a word.
* [SKIP](http://www.basic-japanese.com/Hilfsdateien/skipCode.html) search (a search based on the kanji visual look).
* Custom dictionaries.
* Optionally shows romaji instead of hiragana/katakana - good for beginner learners. Also provides a hiragana/katakana learning table.
* Search in Tanaka Example sentences.
* Optional verb deinflection (e.g. searching for あえない will find あう).
* Kanji drawing order for more than 1500 kanjis.
* A basic sentence translation.
* Integration with Simeji.
* Verb conjugations with example sentences.
* Integrates with Android Search (Android 1.6 or higher only).

# Compatibility

The code is quite old, but it works with newer versions of Android.  It has been successfully tested on Android 4.4.4 (November, 2014) and Android 5.0.1 (March, 2015), as well as many other older other versions.

# Building from Source

Get an up-to-date copy of the [source](https://github.com/dper/aedict).  Make sure that `maven` and `aapt` are installed.  On Debian, they can be installed as follows.

```
# apt-get install maven aapt
```
