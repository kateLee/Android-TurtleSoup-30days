package kate.tutorial.turtlesoup.puzzle

import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Kate on 2021/6/18
 */
interface PuzzleService {
    @GET("/api/puzzles")
    suspend fun getPuzzles(): ArrayList<Puzzle>

    @GET("/api/puzzles/{id}")
    suspend fun getPuzzleDetail(@Path("id") id: String): PuzzleDetail

    @POST("/api/puzzles")
    suspend fun postPuzzle(@Body body: PuzzleRequest): PuzzleDetail

    @DELETE("/api/puzzles/{id}")
    suspend fun deletePuzzle(@Path("id") id: String): Response<Unit>
}
