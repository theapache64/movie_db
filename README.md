# movie_db
A simple API for IMDB

##### Straight to the example

GET
```
http://theapache64.xyz:8080/movie_db/search?keyword=Titanic
```
Response:
```
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

