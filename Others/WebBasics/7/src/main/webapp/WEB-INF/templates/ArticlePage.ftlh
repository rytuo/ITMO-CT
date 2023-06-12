<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        <div class="article form-box">
            <div class="header">Create article</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="createArticle"/>
                    <div class="field">
                        <div class="name">
                            <label for="title">Title</label>
                        </div>
                        <div class="value">
                            <input id="title" name="title" value="${title!}"/>
                        </div>
                    </div>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text</label>
                        </div>
                        <div class="value">
                            <input id="text" name="text" type="text" value="${text!}"/>
                        </div>
                    </div>
                    <div class="error">
                        ${error!}
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Enter">
                    </div>
                </form>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $(".article form").submit(function () {
                    const title = $(this).find("input[name='title']").val();
                    const text = $(this).find("input[name='text']").val();
                    const $error = $(this).find(".error");

                    ajax("createArticle", {
                            title: title,
                            text: text
                        }, function (response) {
                            if (response["error"]) {
                                $error.text(response["error"]);
                            }
                        });

                    return false;
                });
            })
        </script>
    <#else>
        <h1>Please log in to create articles.</h1>
    </#if>
</@c.page>