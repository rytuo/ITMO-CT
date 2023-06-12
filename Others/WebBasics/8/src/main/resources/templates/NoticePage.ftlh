<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice form-box">
        <div class="header">Add Notice</div>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="content">Text</label>
                    </div>
                    <div class="value">
                        <input id="content" name="content"/>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>
