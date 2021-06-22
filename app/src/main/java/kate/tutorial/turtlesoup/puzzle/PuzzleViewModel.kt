package kate.tutorial.turtlesoup.puzzle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kate.tutorial.turtlesoup.Repository
import kate.tutorial.turtlesoup.request

/**
 * Created by Kate on 2021/6/22
 */
class PuzzleViewModel : ViewModel() {
    private val repository = Repository()
    var puzzle: MutableLiveData<PuzzleDetail> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var requestError: MutableLiveData<String> = MutableLiveData()

    fun fetchPuzzle(id: String) = viewModelScope.request (
        onError = {
            isLoading.postValue(false)
            requestError.postValue(it.message)
        },
        execute = {
            isLoading.postValue( true)
            puzzle.postValue(repository.getPuzzleDetail(id))
            isLoading.postValue(false)
        }
    )
}
