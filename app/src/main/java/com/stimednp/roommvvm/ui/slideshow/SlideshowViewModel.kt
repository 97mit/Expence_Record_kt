package com.stimednp.roommvvm.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stimednp.roommvvm.data.db.entity.NewResponse
import com.stimednp.roommvvm.di.NetworkRepository
import com.stimednp.roommvvm.utils.Constants.API_KEY
import com.stimednp.roommvvm.utils.Constants.COUNTRY_CODE
import com.stimednp.roommvvm.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

/*class SlideshowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text
}*/
@HiltViewModel
class SlideshowViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {

    private val _topHeadlines = MutableLiveData<DataHandler<NewResponse>>()
    val topHeadlines: LiveData<DataHandler<NewResponse>> = _topHeadlines

    fun getTopHeadlines() {
        _topHeadlines.postValue(DataHandler.LOADING())
        viewModelScope.launch {
            val response = networkRepository.getTopHeadlines(COUNTRY_CODE, API_KEY)
            _topHeadlines.postValue(handleResponse(response))
        }
    }

    private fun handleResponse(response: Response<NewResponse>): DataHandler<NewResponse> {
        if (response.isSuccessful) {
            response.body()?.let { it ->
                return DataHandler.SUCCESS(it)
            }
        }
        return DataHandler.ERROR(message = response.errorBody().toString())
    }
}