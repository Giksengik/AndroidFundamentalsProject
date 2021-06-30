package vlasov.ru.androidfundamentalsproject.data.locale.room

import vlasov.ru.androidfundamentalsproject.data.locale.LocalDataSource
import vlasov.ru.androidfundamentalsproject.models.Actor
import vlasov.ru.androidfundamentalsproject.models.Genre
import vlasov.ru.androidfundamentalsproject.models.Movie

class RoomDataSource(private val db : AppRoomDatabase) : LocalDataSource {

    override suspend fun loadMovies(): List<Movie> {
        return db.getMovieDao().getMovies().map {
            Movie(
                id = it.movie.id,
                pgAge = it.movie.pgAge,
                title = it.movie.title,
                genres = it.genres.map { genreDB ->
                    Genre(
                        id = genreDB.id,
                        name = genreDB.name
                    )
                },
                runningTime = it.movie.runningTime,
                reviewCount = it.movie.reviewCount,
                isLiked = it.movie.isLiked,
                rating = it.movie.rating,
                imageUrl = it.movie.imageUrl,
                detailImageUrl = it.movie.detailImageUrl,
                storyLine = it.movie.storyLine,
                actors = it.actors.map { actorDB ->
                    Actor(
                        id = actorDB.id,
                        name = actorDB.name,
                        imageUrl = actorDB.imageUrl)
                }

            )
        }
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        val itemsToInsert : List<Triple<MovieDB, List<ActorDB>, List<GenreDB>>> = movies.map{
            Triple(
                MovieDB(
                id = it.id,
                pgAge = it.pgAge,
                title = it.title,
                runningTime = it.runningTime,
                reviewCount = it.reviewCount,
                isLiked = it.isLiked,
                rating = it.rating,
                imageUrl = it.imageUrl,
                detailImageUrl = it.detailImageUrl,
                storyLine = it.storyLine
                ),
                it.actors.map{ actor ->
                    ActorDB(
                        id = actor.id,
                        name = actor.name,
                        imageUrl = actor.imageUrl,
                        parentId = it.id
                    )
                },
                it.genres.map{ genre ->
                    GenreDB(
                        id = genre.id,
                        name = genre.name,
                        parentId = it.id
                    )
                }
            )
        }
        for (triple in itemsToInsert){
            db.getMovieDao().insertMovie(triple.first)
            for( actor in triple.second){
                db.getMovieDao().insertActor(actor)
            }
            for( genre in triple.third){
                db.getMovieDao().insertGenre(genre)
            }
        }
    }

    override suspend fun loadMovie(id: Long): Movie {
        return db.getMovieDao().getMovie(id).let{
            Movie(
                    id = it.movie.id,
                    pgAge = it.movie.pgAge,
                    title = it.movie.title,
                    genres = it.genres.map { genreDB ->
                        Genre(
                                id = genreDB.id,
                                name = genreDB.name
                        )
                    },
                    runningTime = it.movie.runningTime,
                    reviewCount = it.movie.reviewCount,
                    isLiked = it.movie.isLiked,
                    rating = it.movie.rating,
                    imageUrl = it.movie.imageUrl,
                    detailImageUrl = it.movie.detailImageUrl,
                    storyLine = it.movie.storyLine,
                    actors = it.actors.map { actorDB ->
                        Actor(
                                id = actorDB.id,
                                name = actorDB.name,
                                imageUrl = actorDB.imageUrl)
                    }
            )
        }
    }
}