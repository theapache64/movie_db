# movie_db
A simple API for IMDB (no-api-key, no fee, fully free and unlimited access for lifetime).
##### Straight to the example

##### Syntax

http://theapache64.xyz:8080/movie_db/search?keyword=[YOUR-KEYWORD]

##### Example

**GET**

[http://theapache64.xyz:8080/movie_db/search?keyword=Titanic](http://theapache64.xyz:8080/movie_db/search?keyword=Titanic)

Response:
```json
{
  "data": {
    "plot": "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.",
    "name": "Titanic",
    "rating": "7.7",
    "genre": "Drama,Romance",
    "poster_url": "https://images-na.ssl-images-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_UX182_CR0,0,182,268_AL_.jpg"
  },
  "error_code": 0,
  "error": false,
  "message": "Movie found"
}
```

Please note that the `keyword` can be anything like character name, part of the movie name etc. 
For example if we search `Tony Stark` (Ironman's character name), We'll get the `Ironman` movie.

GET
```
http://theapache64.xyz:8080/movie_db/search?keyword=Tony+Stark
```
Response:
```
{
  "data": {
    "plot": "After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil.",
    "name": "Iron Man",
    "rating": "7.9",
    "genre": "Action,Adventure,Sci-Fi",
    "poster_url": "https://images-na.ssl-images-amazon.com/images/M/MV5BMTczNTI2ODUwOF5BMl5BanBnXkFtZTcwMTU0NTIzMw@@._V1_UX182_CR0,0,182,268_AL_.jpg"
  },
  "error_code": 0,
  "error": false,
  "message": "Movie found"
}
```

As of now, the API only provides basic details like name, plot, rating, genre and posterUrl. If you need more details, shoot me a mail to `theapache64@gmail.com` and i'll implement it. :)

#### Found an issue?

Open an issue (y)
