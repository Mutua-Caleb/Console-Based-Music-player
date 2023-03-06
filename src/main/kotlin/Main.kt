import java.io.File

fun main() {
    val musicDir = File("path/to/music/directory")
    val musicLibrary = MusicLibrary(musicDir)
    val songs = musicLibrary.loadSongs()
    val playlist = Playlist("My PlayList")
    playlist.addSongs(songs)
    val musicPlayer = MusicPlayer(playlist)
    val userInterface = UserInterface(musicPlayer, playlist)

    while(true) {
        userInterface.displayPlayList()
        val currentSongIndex = musicPlayer.getCurrentSongIndex()
        if (currentSongIndex >= 0) {
            val currentSong = playlist.getSongs()[currentSongIndex]
            userInterface.displaySongInfo(currentSong)
        }
        userInterface.displayControls()
        val input = userInterface.getUserInput()
        userInterface.handleUserInput(input)
    }
}