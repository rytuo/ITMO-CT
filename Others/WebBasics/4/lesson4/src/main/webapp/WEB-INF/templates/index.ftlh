<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as post>
        <@c.displayPost post/>
    </#list>
</@c.page>