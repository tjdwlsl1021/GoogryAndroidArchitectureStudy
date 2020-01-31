package com.jay.architecturestudy.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jay.architecturestudy.data.model.Movie
import com.jay.architecturestudy.data.repository.NaverSearchRepository
import com.jay.architecturestudy.ui.BaseViewModel
import com.jay.architecturestudy.ui.ViewType
import com.jay.architecturestudy.util.addTo
import com.jay.architecturestudy.util.singleIoMainThread

class MovieViewModel(
    override val repository: NaverSearchRepository
): BaseViewModel<Movie>(repository) {
    override val _data: MutableLiveData<List<Movie>> = MutableLiveData()
    val data: LiveData<List<Movie>>
        get() = _data

    val viewType = Transformations.map(data) { list ->
        if (list.isNotEmpty()) {
            ViewType.VIEW_SEARCH_RESULT
        } else {
            ViewType.VIEW_SEARCH_NO_RESULT
        }
    }

    override fun init() {
        repository.
            getLatestMovieResult()
            .compose(singleIoMainThread())
            .subscribe({
                keyword.value = it.keyword
                _data.value = it.movies
            }, { e ->
                val message = e.message ?: return@subscribe
                errorMsg.value = message
                _data.value = null
            })
            .addTo(compositeDisposable)
    }

    override fun search(keyword: String) {
        repository.getMovie(
            keyword = keyword
        )
            .compose(singleIoMainThread())
            .subscribe({ movieRepo ->
                _data.value = movieRepo.movies
            }, { e ->
                val message = e.message ?: return@subscribe
                errorMsg.value = message
            })
            .addTo(compositeDisposable)
    }
}