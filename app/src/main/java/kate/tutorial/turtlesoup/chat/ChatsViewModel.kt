package kate.tutorial.turtlesoup.chat

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kate.tutorial.turtlesoup.request
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * Created by Kate on 2021/7/3
 */
private const val BASE_URL = "ws://192.168.48.3:8080"
class ChatsViewModel : ViewModel() {
    var chats: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var requestError: MutableLiveData<String> = MutableLiveData()
    var noChats: MutableLiveData<Boolean> = MutableLiveData()
    private val wsClient = WsClient(
        OkHttpClient.Builder()
            .pingInterval(60, TimeUnit.SECONDS)
            .build(), ::notifyMessage
    )

    init {
        chats.value = ArrayList()
    }

    fun connect() = viewModelScope.request(
        onError = {
            isLoading.postValue(false)
            requestError.postValue(
                when (it) {
                    is ClosedReceiveChannelException -> "Disconnected. ${it.message}."
                    is NetworkErrorException -> "Network error. ${it.message}."
                    else -> it.message
                }
            )
        },
        execute = {
            isLoading.postValue(true)

            wsClient.connect()

            isLoading.postValue(false)
        }
    )

    fun sendMessage(message: String) = viewModelScope.request(
        onError = {
            requestError.postValue(it.message)
        },
        execute = {
            //todo save local message
            wsClient.send(message)
        })

    private fun notifyMessage(message: String) {
        chats.value?.add(message)
        chats.postValue(chats.value)
    }

    class WsClient(
        private val client: OkHttpClient,
        private val onReceive: (input: String) -> Unit
    ) {
        var session: WebSocket? = null

        fun connect() {
            val request = Request.Builder()
                .url("$BASE_URL/chat")
                .build()
            client.newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    session = webSocket
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    onReceive(text)
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                }

                override fun onClosing(
                    webSocket: WebSocket,
                    code: Int,
                    reason: String
                ) {
                    webSocket.close(1000, null)
                }

                override fun onClosed(
                    webSocket: WebSocket,
                    code: Int,
                    reason: String
                ) {
                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?
                ) {
                }
            })
        }

        fun send(message: String) {
            session?.send(message)
        }
    }
}
