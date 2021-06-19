package kate.tutorial.turtlesoup.puzzle

import retrofit2.http.GET

/**
 * Created by Kate on 2021/6/18
 */
interface PuzzleService {
    @GET("/api/puzzles")
    suspend fun getPuzzles(): ArrayList<Puzzle>
}
