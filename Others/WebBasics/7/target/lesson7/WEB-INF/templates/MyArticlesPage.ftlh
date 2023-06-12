<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        <div class="userArticles datatable">
            <div class="caption">Articles</div>
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Hide/Show</th>
                </tr>
                </thead>
                <tbody>
                <#list articles as article>
                    <tr class="article">
                        <td class="id">${article.id}</td>
                        <td class="title">${article.title}</td>
                        <td class="hidden">
                            <#if article.hidden == true>
                                <#assign val="Show">
                            <#else>
                                <#assign val="Hide">
                            </#if>
                            <button article_id="${article.id}">${val}</button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>

        <script>
            $(document).ready(function () {
                $(".userArticles .article button").click(function () {
                    const $button = $(this);
                    const action = $button.text();
                    let newHidden = action === "Hide";
                    ajax("setArticleHidden", {
                        article_id: $button.attr("article_id"),
                        hidden: newHidden
                    }, function (response) {
                        if (response["newHidden"] != null) {
                            if (response["newHidden"] === true) {
                                $button.text("Show");
                            } else {
                                $button.text("Hide");
                            }
                        }
                    })
                })
            })
        </script>
    <#else>
        <h1>Please log in to edit articles.</h1>
    </#if>
</@c.page>