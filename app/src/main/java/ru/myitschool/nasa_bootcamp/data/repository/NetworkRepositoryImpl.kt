package ru.myitschool.nasa_bootcamp.data.repository

import androidx.lifecycle.MutableLiveData
import ru.myitschool.nasa_bootcamp.data.model.*
import ru.myitschool.nasa_bootcamp.utils.Resource
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    newsRepository: NewsRepository
) : NetworkRepository {
    override suspend fun getNews(): Resource<List<ContentWithLikesAndComments<ArticleModel>>> {

        return Resource.success(
            listOf(
                ContentWithLikesAndComments(
                    likes = listOf(),
                    id = 123,
                    comments =
                    listOf(
                        Comment(
                            _postClass = ArticleModel::class.java,
                            text = "ds/lkfsdfksdfsd",
                            subComments = listOf(),
                            id = 4,
                            author = UserModel(
                                id = "4",
                                avatarUrl = "https://lh3.googleusercontent.com/0xn6EsKc4lfdgFBt_1rA8uN6FgUUCrNO7cmTQny30x6wQhFrlTuZomwENpYsyMW00lytSuv6hLSHOs1voqpUautXcQ",
                                name = "Zach"
                            ),
                            likes = listOf(),
                            date = 1628008932
                        )

                    ),
                    content = ArticleModel(
                        id = 123,
                        title = "Hello World",
                        summary = "Hmm d;lfksd ;fkhsa ldkhf;s aldkhfl sdjfaskdhalsdl asdak  sdhlas khdlask dlkasdas ldkhas/ ldkha /sldkh a/skd a/skda skhdas",
                        imageUrl = "https://i.ytimg.com/vi/0QaX4KMjeVQ/hq720.jpg?sqp=-oaymwEcCNAFEJQDSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLDEYHd9BOxp4Qnw0gdFKQe2fNf1xg",
                        publishedAt = "2021-08-03T20:40:02.000Z"
                    )
                )
            )
        )
    }

    override suspend fun getBlogPosts(): Resource<List<ContentWithLikesAndComments<PostModel>>> {
        return Resource.error("TO DO", null)
    }

    override suspend fun pressedLikeOnItem(
        item: ContentWithLikesAndComments<out Any>
    ): Resource<Nothing> {
        if (item.content::class.java == ArticleModel::class.java) {
            firebaseRepository.pushLike("ArticleModel", item.id.toInt())!!
        } else {
            firebaseRepository.pushLike("UserPost", item.id.toInt())!!
        }
        return Resource.success(null)
    }

    override suspend fun pressedLikeOnComment(comment: Comment): Resource<Nothing> {

        return Resource.error("TO DO", null)
    }

    override suspend fun sendComment(
        message: String,
        id: Long,
        _class: Class<*>,
        parentComment: Comment?
    ): Resource<Nothing> {
        if (_class == ArticleModel::class.java) {
            firebaseRepository.pushComment("ArticleModel", id.toInt(), message)
        } else {
            firebaseRepository.pushComment("UserPost", id.toInt(), message)
        }
        return Resource.success(null)
    }

    override suspend fun getCurrentUser(): UserModel = firebaseRepository.getCurrentUser()!!
}

