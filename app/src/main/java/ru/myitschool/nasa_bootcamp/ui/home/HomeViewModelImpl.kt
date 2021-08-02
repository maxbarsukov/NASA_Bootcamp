package ru.myitschool.nasa_bootcamp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.myitschool.nasa_bootcamp.data.model.ArticleModel
import ru.myitschool.nasa_bootcamp.data.model.ImageOfTheDayModel
import ru.myitschool.nasa_bootcamp.data.repository.NasaRepository
import ru.myitschool.nasa_bootcamp.data.repository.NewsRepository
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(
    private val nasaRepository: NasaRepository,
    private val newsRepository: NewsRepository
) : ViewModel(), HomeViewModel {
    private val imageOfTheDay = MutableLiveData<Resource<ImageOfTheDayModel>>()
    private val articles = MutableLiveData<Resource<List<ArticleModel>>>()
    override fun getViewModelScope() = viewModelScope

    override fun getImageOfTheDayModel(): LiveData<Resource<ImageOfTheDayModel>> {
        return imageOfTheDay
    }

    override fun getArticles(): LiveData<Resource<List<ArticleModel>>> {
        return articles
    }

    override suspend fun loadImageOfTheDay() {
        imageOfTheDay.postValue(Resource.loading(null))
        try {
            val response = nasaRepository.getImageOfTheDay()
            if (response.body() != null)
                imageOfTheDay.postValue(Resource.success(response.body()!!.createApodModel()))
            else imageOfTheDay.postValue(Resource.error("Empty response body", null))
        } catch (e: Exception) {
            e.printStackTrace()
            imageOfTheDay.postValue(Resource.error(e.message.toString(), null))
        }
    }

    override suspend fun loadArticles() {
        articles.postValue(Resource.loading(null))
        try {
            val response = newsRepository.getNews()
            if (response.body() != null)
                articles.postValue(
                    Resource.success(
                        response.body()!!.map { dto -> dto.createArticleModel() }.take(10)
                    )
                )
            else articles.postValue(Resource.error("Empty response body", null))
        } catch (e: Exception) {
            e.printStackTrace()
            articles.postValue(Resource.error(e.message.toString(), null))
        }
    }
}