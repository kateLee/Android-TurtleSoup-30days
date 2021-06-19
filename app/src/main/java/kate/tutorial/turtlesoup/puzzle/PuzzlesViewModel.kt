package kate.tutorial.turtlesoup.puzzle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kate.tutorial.turtlesoup.Repository
import kate.tutorial.turtlesoup.request

/**
 * Created by Kate on 2021/6/12
 */
class PuzzlesViewModel : ViewModel() {
    private val repository = Repository()
    var puzzles: MutableLiveData<ArrayList<Puzzle>> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var isRefreshing: MutableLiveData<Boolean> = MutableLiveData()
    var requestError: MutableLiveData<String> = MutableLiveData()
    var noPuzzle: MutableLiveData<Boolean> = MutableLiveData()

    fun fetchPuzzles() = viewModelScope.request (
        onError = {
            isLoading.postValue(false)
            requestError.postValue(it.message)
        },
        execute = {
            isLoading.postValue( true)
            puzzles.postValue(repository.getPuzzles())
            isLoading.postValue(false)
            noPuzzle.postValue(puzzles.value?.isEmpty()?:true)
        }
    )

    fun refreshPuzzles() = viewModelScope.request (
        onError = {
            isRefreshing.postValue( false)
            requestError.postValue(it.message)
        },
        execute = {
            isRefreshing.postValue( true)
            puzzles.postValue(repository.getPuzzles())
            isRefreshing.postValue( false)
            noPuzzle.postValue(puzzles.value?.isEmpty()?:true)
        }
    )
}
