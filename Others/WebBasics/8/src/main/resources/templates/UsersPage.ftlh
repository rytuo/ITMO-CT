<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Enable/Disable</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as unit>
                        <tr>
                            <td class="id">${unit.id}</td>
                            <td class="login">${unit.login}</td>
                            <td class="creationTime">${unit.creationTime}</td>
                            <td class="switch">
                                <#if !(user?? && unit == user)>
                                    <#if unit.disabled == true>
                                        <#assign text = "Enable"/>
                                    <#else>
                                        <#assign text = "Disable"/>
                                    </#if>
                                    <form method="post" action="">
                                        <label>
                                            <input name="userId" value="${unit.id}"  hidden/>
                                        </label>
                                        <label>
                                            <input name="disabled" value="${text}" hidden/>
                                        </label>
                                        <div class="button-field">
                                            <input type="submit" value="${text}">
                                        </div>
                                    </form>
                                </#if>
                            </td>
                        </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
