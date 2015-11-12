# aedict

A clone of Aedict 2.  This is a fork of the original project on [Google Code](https://code.google.com/p/aedict/).  That source code base uses Mercurial, and here it has been converted to Git.  The original author stopped development in late 2011, but the app is functioning well years later.

# Download

Get an APK from the `releases` directory.  I recommend `Aedict.2.9.4.apk`.

The `Aedict.2.9.4.apk` release is newer, but the only change is nagware.  The author added an obnoxious pop-up request for money to the 2.9.4 release, and I recommend you never use it.  The file is included here for posterity's sake.

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

From the source directory, the following command will try to build everything.  If you're missing any dependencies, look at the error messages to see what is going wrong.

```
$ mvn
```

The first time you run this, maven will download a lot of dependencies.  There are many files and it could take several minutes.  One problem is that a strange old version of the Android jar is sought.  It can be downloaded [here](http://baka.sk/maven2/android/android/1.1-r1/).  Then do something like the following (assuming you downloaded the file to your home directory.

```
$ mvn install:install-file -DgroupId=android -DartifactId=android -Dversion=1.1_r1 -Dpackaging=jar -Dfile=~/android-1.1-r1.jar
```
