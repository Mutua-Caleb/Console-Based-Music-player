import kotlin.system.exitProcess

class UserInterface(private val musicPlayer: MusicPlayer, private val playlist: Playlist, private val playlistManager: PlaylistManager,  private val userPreferences: UserPreferences, private val userManager: UserManager, private val spotifyClient: SpotifyClient) {
    fun displayPlayList() {
        playlist.getSongs().forEachIndexed { index, song ->
            println("$index. ${song.title} - ${song.artist}")
        }
    }

    fun displaySongInfo(song: Song) {
        println("Playing: ${song.title} - ${song.artist}")
    }

    fun displayControls() {
        println("Controls: [P]lay/[P]ause [S]top [F]orward [B]ackward [Sh]uffle [R]epeat [Se]arch [C]reate playlist [D]elete playlist [L]ist playlists [V+]olume up [V-]olume down [Sp]ecify volume [Sh]uffle mode [R]epeat mode [Q]uit")
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

    fun login(): Boolean {
        var attempts = 3
        while(attempts > 0) {
            print("Enter username: ")
            val username = readLine() ?: ""
            println("Enter password: ")
            val password = readLine() ?: ""

            if (userManager.authenticate(username, password)) {
                println("welcome, $username")
                return true
            } else {
                attempts--
                println("Invalid username or password. $attempts attempts left.")
            }
        }
        println("Too many failed attempts. Goodbye")
        return false
    }

    fun start() {
        if (login()) {
            displayControls()
            while (true) {
                val input = readLine() ?: ""
                handleUserInput(input)
            }
        }
    }



    fun handleUserInput(input: String) {
        when(input) {
            "V+" -> {
                musicPlayer.setVolume(minOf(userPreferences.volume + 10, 100))
                println("volume increased to ${userPreferences.volume}%")
            }
            "V-" -> {

                musicPlayer.setVolume(maxOf(userPreferences.volume - 10, 0 ))
                println("Volume decreased to ${userPreferences.volume}%")
            }
            "Sp" -> {
                print("Enter volume level (0-100): ")
                val volume = readLine()?.toIntOrNull() ?: userPreferences.volume

                musicPlayer.setVolume(maxOf(minOf(volume, 100), 0))
                println("Volume set to ${userPreferences.volume}%")
            }

            "C" -> {
                print("Enter playlist name: ")
                val name = readLine() ?: ""
                val playlist = playlistManager.createPlaylist(name, songs)
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
            "Sh" -> {
                userPreferences.shuffle = !userPreferences.shuffle
                if (userPreferences.shuffle) {
                    musicPlayer.shuffleSongs()
                    println("Shuffle mode turned on")
                } else {
                    println("Shuffle mode turned off")
                }
            }
            "R" -> {
                userPreferences.repeat = !userPreferences.repeat
                if(userPreferences.repeat) {
                    println("Repeat mode turned on")
                } else {
                    println("Repeat mode turned off")
                }
            }
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
            "Im" -> {
                playlistManager.importPlaylist(spotifyClient)
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