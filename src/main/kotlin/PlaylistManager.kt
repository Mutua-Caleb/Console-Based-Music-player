class PlaylistManager(private val musicPlayer: MusicPlayer, private val spotifyClient: SpotifyClient) {
    private val playlists = mutableListOf<Playlist>()

    fun createPlaylist(name: String, songs: List<Song>): Playlist {
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

    fun importPlaylist(spotifyClient: SpotifyClient) {
        print("Enter playlist ID: ")
        val playlistId = readLine() ?: ""
        val songs = this.spotifyClient.getPlaylistTracks(playlistId).map { Song(it.name, it.artists.joinToString(", "), it.uri) }
        this.createPlaylist("Imported from Spotify", songs)
        println("Playlist imported from Spotify")
    }

}