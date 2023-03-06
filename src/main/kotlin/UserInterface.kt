import kotlin.system.exitProcess

class UserInterface(private val musicPlayer: MusicPlayer, private val playlist: Playlist ) {
    fun displayPlayList() {
        playlist.getSongs().forEachIndexed { index, song ->
            println("$index. ${song.title} - ${song.artist}")
        }
    }

    fun displaySongInfo(song: Song) {
        println("Playing: ${song.title} - ${song.artist}")
    }

    fun displayControls() {
        println("Controls: [P]lay/[P]ause [S]top [F]orward [B]ackward [Q]uit")
    }

    fun getUserInput(): String {
        print(">> ")
        return readLine() ?: ""
    }

    fun handleUserInput(input: String) {
        when(input) {
            "P" -> musicPlayer.togglePlayPause()
            "S" -> musicPlayer.stopSong()
            "F" -> musicPlayer.skipSong()
            "B" ->musicPlayer.rewindSong()
            "Q"-> exitProcess(0)
            else -> {
                try {
                    val index = input.toInt()
                    if (index in playlist.getSongs().indices) {
                        musicPlayer.playSong(index)
                    }

                } catch (e: NumberFormatException) {
                    println("Invalid input. Try again")
                }
            }
        }
    }
}