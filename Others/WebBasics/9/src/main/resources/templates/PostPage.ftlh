<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post/>

    <div style="font-size: 1.5rem">Comments section:</div>

    <#if user??>
        <div class="comment-form">
            <div class="header">Write comment</div>
            <div class="body">
                <form method="post" action="">
                    <input type="hidden" name="action" value="writeComment"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Text:</label>
                        </div>
                        <div class="value">
                            <input autofocus id="text" name="text" value="${comment.text!}"/>
                        </div>
                        <@c.error "comment.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
    </#if>

    <#list post.comments as comm>
        <@c.comment comm/>
    </#list>
</@c.page>