<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <#list languages as item>
                <a href="#"><img alt="${item.text}" title="${item.text}"
                                src="${item.src}"></a>
            </#list>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <#list menu_links as item>
                    <li>
                        <#if uri == item.link>
                            <a class="underline" href="${item.link}">${item.text}</a>
                        <#else>
                            <a href="${item.link}">${item.text}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list aside_texts as item>
            <section>
                <div class="header">${item.header}</div>
                <div class="body">${item.body}</div>
                <div class="footer">
                    <a href="#">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user>
    <a href="/user?handle=${user.handle}">${user.name}</a>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function getInd items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item?index>
        </#if>
    </#list>
</#function>

<#macro usersTable>
    <div class="usersTable">
        <div class="caption">Users</div>
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Handle</th>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
                <#list users as u>
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.handle}</td>
                        <td><@c.userlink u/></td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</#macro>

<#macro postText text>
    <#if text?length &gt; 250>
        <#assign modText = text[0..250] + "...">
    <#else>
        <#assign modText = text>
    </#if>
    ${modText}
</#macro>

<#macro displayPost post>
    <article class="post">
        <#assign u = findBy(users, "id", post.userId)!>
        <div class="title">${post.title}</div>
        <div class="information">By <@userlink u/>, TextId: ${post.id}</div>
        <div class="body"><@postText post.text/></div>
        <div class="footer">
            <div class="left">
                <img src="/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">${post.id}</span>
                <img src="/img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="/img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">0</a>
            </div>
        </div>
    </article>
</#macro>