<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <template>
            <article class="article">
                <div class="header">
                    <div class="title"></div>
                    <div class="information"></div>
                </div>
                <div class="body">
                    <div class="text"></div>
                </div>
                <div class="footer">
                    <div class="left">
                        <span class="articleId"></span>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                    </div>
                </div>
            </article>
        </template>
        <article class="noData">
            No articles
        </article>
    </div>

    <script>
        $(document).ready(function () {
            const $noData = $(".articles .noData");
            $noData.text("Loading...");
            ajax("findArticles", {}, function (response) {
                response["articles"].forEach(function (article) {
                    $noData.hide();
                    const $article = $($(".articles template").prop("content")).clone();
                    $article.attr("article_id", article["id"]);
                    $article.find(".title").text(article["title"]);
                    $article.find(".text").text(article["text"]);
                    $article.find(".articleId").text("Text ID: " + article["id"]);
                    $article.find(".information").text(
                        "By " + response["users"][article["userId"]]["login"]);
                    $(".articles").append($article);
                })
            });
        })
    </script>
</@c.page>