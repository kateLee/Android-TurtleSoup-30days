package kate.tutorial.turtlesoup.puzzle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kate.tutorial.turtlesoup.Repository
import kate.tutorial.turtlesoup.request

/**
 * Created by Kate on 2021/6/22
 */
class PuzzlePostViewModel : ViewModel() {
    var puzzle: MutableLiveData<PuzzleDetail> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var requestError: MutableLiveData<String> = MutableLiveData()

    fun postPuzzle(title: String, description: String, tags: String) = viewModelScope.request (
        onError = {
            isLoading.postValue(false)
            requestError.postValue(it.message)
        },
        execute = {
            isLoading.postValue( true)
            puzzle.postValue(Repository.postPuzzleDetail(title, description, tags))
            isLoading.postValue(false)
        }
    )
}
