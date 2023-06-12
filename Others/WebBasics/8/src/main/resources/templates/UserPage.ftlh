<#-- @ftlvariable name="userInfo" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if userInfo??>
        <div><b>Login:</b> ${userInfo.login}</div>
        <div><b>ID:</b> ${userInfo.id}</div>
        <div><b>Creation time:</b> ${userInfo.creationTime}</div>
    <#else>
        <h3>No such user</h3>
    </#if>
</@c.page>
