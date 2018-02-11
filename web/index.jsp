<%@ page import="com.theah64.movie_db.database.tables.Requests" %>
<%@ page import="com.theah64.movie_db.utils.StringUtils" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>movie_db</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
    <link rel="icon" href="favicon.ico" type="image/x-icon">


    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <style>
        span#count {
            font-size: 13em;
        }

        .hint {
            color: #ccc;
            font-style: italic;
        }
    </style>

    <script>
        setInterval(function () {
            $.post("get_total_hits", function (data) {
                $("span#count").text(data.data.count);
            });
        }, 1000);
    </script>

</head>
<body>
<div class="container">

    <div class="row">
        <div style="margin-top: 12em" class="col-md-12 text-center">

            <span id="count"><%=StringUtils.toIndianNumber(Requests.getInstance().getTotalHits())%></span>
            <br>
            <a class="hint" href="">requests served</a>

        </div>
    </div>


</div>
</body>
</html>
