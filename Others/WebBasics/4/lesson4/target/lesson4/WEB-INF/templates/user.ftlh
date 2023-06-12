<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle?has_content>
        <#assign u = c.findBy(users, "handle", handle)!/>
    <#else>
        <#if user_id?has_content && user_id?is_number>
            <#assign u = c.findBy(users, "id", user_id)!/>
        </#if>
    </#if>

    <#if u?has_content>
        <div class="userPage">
            <div class="userBlock">
                <img class="userPic" src="img/no-title.jpg" alt="userPic">
                <div class="userName">${u.name}</div>
                <ul>
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                </ul>
            </div>

            <#assign ind = c.getInd(users, "handle", u.handle)>
            <#if ind == 0>
                <div class="userArrow fadedArrow">←</div>
            <#else>
                <a href="/user?handle=${users[ind - 1].handle}" class="userArrow activeArrow">←</a>
            </#if>
            <#if ind + 1 == users?size>
                <div class="userArrow fadedArrow">→</div>
            <#else>
                <a href="/user?handle=${users[ind + 1].handle}" class="userArrow activeArrow">→</a>
            </#if>
        </div>
    <#else>
        <h1>No such user</h1>
    </#if>
</@c.page>
