package kate.tutorial.turtlesoup

import kate.tutorial.turtlesoup.puzzle.PuzzleRequest
import kate.tutorial.turtlesoup.puzzle.PuzzleService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Kate on 2021/6/18
 */
private const val BASE_URL = "http://192.168.48.3:8080"
class Repository {
    private val retrofit: Retrofit

    init {
        val builder = OkHttpClient.Builder()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build()
    }
    private val puzzleService = retrofit.create(PuzzleService::class.java)
    suspend fun getPuzzles() = puzzleService.getPuzzles()
    suspend fun getPuzzleDetail(id: String) = puzzleService.getPuzzleDetail(id)
    suspend fun postPuzzleDetail(title: String, description: String, tags: String) = puzzleService.postPuzzle(
        PuzzleRequest(title, description, tags)
    )
    suspend fun deletePuzzle(id: String) = puzzleService.deletePuzzle(id)
}
