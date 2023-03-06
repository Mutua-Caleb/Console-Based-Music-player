// This class represents a playlist in the player.It can contain a list of 'song' objects and functions to add, remove, and reorder songs in the playlist

class Playlist(val name: String) {
    private val songs = mutableListOf<Song>()

    fun addSongs(newSongs: List<Song>) {
        songs.addAll(newSongs)
    }

    fun addSong(song: Song) {
        songs.add(song)
    }

    fun removeSong(index: Int) {
        songs.removeAt(index)
    }

    fun reorderSong(fromIndex: Int, toIndex: Int) {
        val song = songs.removeAt(fromIndex)
        songs.add(toIndex, song)
    }

    fun getSongs(): List<Song> {
        return songs.toList()
    }
}