// this class manages the playback of Music. It can contain functions to play, pause, stop, skip and rewind songs
class MusicPlayer(private val playList: Playlist){
    private var currentSongIndex = -1
    private var mediaPlayer: Process? = null
    private var isPlaying = false


    fun getCurrentSongIndex(): Int {
        return currentSongIndex
    }

    fun playSong(index: Int) {
        currentSongIndex = index
        stopSong()
        val song = playList.getSongs()[currentSongIndex]
        val command = "cmd /c start wmplayer.exe \"${song.filePath}\""
        mediaPlayer = ProcessBuilder(command).start()
        isPlaying = true
    }

    fun pauseSong() {
        mediaPlayer?.let {
            it.destroy()
            mediaPlayer = null
        }
        isPlaying = false

    }


    fun stopSong() {
        pauseSong()
        currentSongIndex = -1

    }

    fun skipSong() {
        val nextIndex = currentSongIndex + 1
        if (nextIndex < playList.getSongs().size) {
            playSong(nextIndex)
        }
    }

    fun rewindSong() {
        val prevIndex = currentSongIndex - 1
        if (prevIndex >=0) {
            playSong(prevIndex)
        }
    }

    fun togglePlayPause() {
        if(isPlaying) {
            pauseSong()
        } else {
            mediaPlayer?.let {
                it.destroy()
                mediaPlayer = null
            }
            val song = playList.getSongs()[currentSongIndex]
            val command = "cmd /c start wmplayer.exe /play /close \"${song.filePath}\""
            mediaPlayer = ProcessBuilder(command).start()
            isPlaying = true
        }
    }
}