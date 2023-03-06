import kotlin.system.exitProcess

class UserInterface(private val musicPlayer: MusicPlayer, private val playlist: Playlist, private val playlistManager: PlaylistManager ) {
    fun displayPlayList() {
        playlist.getSongs().forEachIndexed { index, song ->
            println("$index. ${song.title} - ${song.artist}")
        }
    }

    fun displaySongInfo(song: Song) {
        println("Playing: ${song.title} - ${song.artist}")
    }

    fun displayControls() {
        println("Controls: [P]lay/[P]ause [S]top [F]orward [B]ackward [Sh]uffle [R]epeat [Se]arch [C]reate playlist [D]elete playlist [L]ist playlists [Q]uit")
    }

    fun getUserInput(): String {
        print(">> ")
        return readLine() ?: ""
    }

    fun decreaseVolume() {
        val newVolume = maxOf(musicPlayer.getVolume() - 10, 0)
        musicPlayer.setVolume(newVolume)
        println("volume decreased to $newVolume%")
    }

    fun increaseVolume() {
        val newVolume = minOf(musicPlayer.getVolume() +10, 100)
        musicPlayer.setVolume(newVolume)
        println("volume increased to $newVolume")
    }



    fun handleUserInput(input: String) {
        when(input) {
            "C" -> {
                print("Enter playlist name: ")
                val name = readLine() ?: ""
                val playlist = playlistManager.createPlaylist(name)
                println("Playlist created: ${playlist.name}")
            }

            "D" -> {
                print("Enter playlist name: ")
                val name = readLine() ?: ""
                val playlist = playlistManager.getPlaylists().find{it.name == name }
                if(playlist != null) {
                    playlistManager.deletePlaylist(playlist)
                    println("PlayList deleted: ${playlist.name}")
                } else {
                    println("Playlist not found.")
                }
            }
            "L" -> {
                val playlists = playlistManager.getPlaylists()
                if (playlists.isNotEmpty()) {
                    println("Playlists:")
                    for ((index, playlist) in playlists.withIndex()) {
                        println("$index. ${playlist.name}")
                    }
                } else {
                    println("No playlist found.")
                }
            }
            "P" -> musicPlayer.togglePlayPause()
            "S" -> musicPlayer.stopSong()
            "F" -> musicPlayer.skipSong()
            "B" ->musicPlayer.rewindSong()
            "Sh" -> playlist.shuffle()
            "R" -> musicPlayer.toggleRepeat()
            "Se" -> {
                println("Enter search query: ")
                val query = readLine() ?: ""
                val results = playlist.search(query)
                if (results.isNotEmpty()) {
                    println("Search results:")
                    for ((index, song) in results.withIndex()) {
                        println("$index. ${song.title} - ${song.artist}")
                    }
                } else {
                    println("No results found")
                }
            }
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