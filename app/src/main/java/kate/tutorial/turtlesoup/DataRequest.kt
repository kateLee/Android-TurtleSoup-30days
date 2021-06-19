package kate.tutorial.turtlesoup

import kotlinx.coroutines.*

/**
 * Created by Kate on 2021/6/18
 */
fun <T> CoroutineScope.request(
    onError: (error: Throwable) -> Unit = {},
    execute: suspend CoroutineScope.() -> T
) {
    launch(errorHandler { onError.invoke(it) }) {
        withContext(Dispatchers.IO) {
            execute()
        }
    }
}

private fun errorHandler(onError: (error: Throwable) -> Unit): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        onError.invoke(throwable)
    }
}
