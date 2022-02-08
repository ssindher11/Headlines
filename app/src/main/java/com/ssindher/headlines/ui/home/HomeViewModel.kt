package com.ssindher.headlines.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssindher.headlines.data.model.NewsArticle
import com.ssindher.headlines.data.model.NewsDTO
import com.ssindher.headlines.data.repository.NewsRepository
import com.ssindher.headlines.util.getErrorMessage
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    /**
     * If device is connected to internet,
     * fetch data from API and update it in DB.
     * Else, load the data from DB
     */

    private val _newsList = MutableLiveData<List<NewsArticle>>()
    val newsList: LiveData<List<NewsArticle>> = _newsList
    private lateinit var newsListDisposable: DisposableSingleObserver<NewsDTO>
    private val _newsLoader = MutableLiveData<Boolean>()
    val newsLoader: LiveData<Boolean> = _newsLoader
    private val _newsError = MutableLiveData<String>()
    val newsError: LiveData<String> = _newsError

    fun fetchNewsFromApi() {
        newsListDisposable = object : DisposableSingleObserver<NewsDTO>() {
            override fun onSuccess(t: NewsDTO) {
                _newsList.postValue(t.articles ?: listOf())
                _newsLoader.postValue(false)
                viewModelScope.launch { newsRepository.updateData(t.articles ?: listOf()) }
            }

            override fun onError(e: Throwable) {
                _newsLoader.postValue(false)
                _newsError.postValue(getErrorMessage(e))
            }
        }
        _newsLoader.postValue(true)
        newsRepository.getAllNewsFromApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(newsListDisposable)
        compositeDisposable.add(newsListDisposable)
    }


    private lateinit var cachedNewsListDisposable: DisposableSingleObserver<List<NewsArticle>?>

    fun fetchNewsFromDB() {
        cachedNewsListDisposable = object : DisposableSingleObserver<List<NewsArticle>?>() {
            override fun onSuccess(t: List<NewsArticle>?) {
                _newsList.postValue(t ?: listOf())
                _newsLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                _newsLoader.postValue(false)
                _newsList.postValue(listOf())
            }
        }
        _newsLoader.postValue(true)
        newsRepository.getAllNewsFromDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(cachedNewsListDisposable)
        compositeDisposable.add(cachedNewsListDisposable)
    }


    private val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}