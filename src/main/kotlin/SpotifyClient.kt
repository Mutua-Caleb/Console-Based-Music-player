import com.google.gson.Gson
import java.net.URL
import javax.net.ssl.HttpsURLConnection

data class SpotifySong(val name: String, val artists: List<SpotifyArtist>, val uri: String)
data class SpotifyArtist(val name: String)

class SpotifyClient(private val accessToken: String) {
    private val gson = Gson()

    fun search(query: String): List<SpotifySong> {
        val connection = URL("https://api.spotify.com/v1/search?type=track&q=$query").openConnection() as HttpsURLConnection
        connection.setRequestProperty("Authorization", "Bearer $accessToken")
        val inputStream = connection.inputStream
        val response = inputStream.bufferedReader().use { it.readText() }
        val result = gson.fromJson(response, SpotifySearchResult::class.java)
        return result.tracks.items.map { song -> SpotifySong(song.name, song.artists.map { artist -> SpotifyArtist(artist.name) }, song.uri) }
    }

    fun getPlaylistTracks(playlistId: String): List<SpotifySong> {
        val connection = URL("https://api.spotify.com/v1/playlists/$playlistId/tracks").openConnection() as HttpsURLConnection
        connection.setRequestProperty("Authorization", "Bearer $accessToken")
        val inputStream = connection.inputStream
        val response = inputStream.bufferedReader().use { it.readText() }
        val result = gson.fromJson(response, SpotifyPlaylistResult::class.java)
        return result.items.map { item -> SpotifySong(item.track.name, item.track.artists.map { artist -> SpotifyArtist(artist.name) }, item.track.uri) }
    }

    private data class SpotifySearchResult(val tracks: SpotifyTrackResult)
    private data class SpotifyTrackResult(val items: List<SpotifyTrack>)
    private data class SpotifyTrack(val name: String, val artists: List<SpotifyArtist>, val uri: String)
    private data class SpotifyPlaylistResult(val items: List<SpotifyPlaylistItem>)
    private data class SpotifyPlaylistItem(val track: SpotifyTrack)
}
