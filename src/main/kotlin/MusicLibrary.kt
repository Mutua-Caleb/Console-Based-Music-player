import java.io.File

class MusicLibrary(private val musicDir: File) {
    private val songs = mutableListOf<Song>()

    fun loadSongs(): List<Song> {
        songs.clear()
        musicDir.listFiles()?.forEach { file ->
            if (file.isFile && file.extension == "mp3") {
                val song = readSongMetaData(file)
                songs.add(song)
            }
        }
        return songs.toList()
    }


    private fun readSongMetaData(file: File): Song {
        val title = ""
        val artist = ""

        // Read the song metadata using third-party library, such as Apache Tika or JAudioTagger
        return Song(title, artist, file.path)
    }
}

