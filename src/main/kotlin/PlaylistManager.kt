class PlaylistManager {
    private val playlists = mutableListOf<Playlist>()

    fun createPlaylist(name: String): Playlist {
        val playlist = Playlist(name)
        playlists.add(playlist)
        return playlist
    }

    fun deletePlaylist(playlist: Playlist) {
        playlists.remove(playlist)
    }

    fun getPlaylists(): List<Playlist> {
        return playlists.toList()
    }

}