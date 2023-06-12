<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin == true>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin == true>
                        <td class="user_admin">
                            <span class="user_admin_state"></span>
                            <span class="user_admin_action" style="cursor: pointer"></span>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(document).ready(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax("findAll", {}, function(response) {
                response["users"].forEach(function(user) {
                    $noData.hide();
                    const $tr = $($(".users template").prop("content")).clone();
                    $tr.find(".user_id").text(user["id"]);
                    $tr.find(".user_login").text(user["login"]);
                    $tr.find("tr").attr("data-userId", user["id"]);
                    if (user["admin"] === true) {
                        $tr.find(".user_admin_state").text("true");
                        $tr.find(".user_admin_action").text("disable");
                    } else {
                        $tr.find(".user_admin_state").text("false");
                        $tr.find(".user_admin_action").text("enable");
                    }
                    $(".users tbody").append($tr);
                });

                $(".users .user_admin_action").click(function () {
                    const $tr = $(this).parent().parent();
                    const id = $tr.attr("data-userId");
                    const action = $tr.find(".user_admin_action").text();
                    let admin = action === "enable";
                    ajax("setUserAdmin", {
                        userId: id,
                        admin: admin
                    }, function (response) {
                        if (response["newAdminState"] != null) {
                            if (response["newAdminState"] === true) {
                                $tr.find(".user_admin_state").text("true");
                                $tr.find(".user_admin_action").text("disable");
                            } else {
                                $tr.find(".user_admin_state").text("false");
                                $tr.find(".user_admin_action").text("enable");
                            }
                        }
                    })
                });

                $(".users tr .user_login").click(function() {
                    ajax("findUser", {
                            userId: $(this).parent().attr("data-userId")
                        },
                        function (response) {
                            notify(response["user"].creationTime);
                        });
                })
            });
        })
    </script>
</@c.page>
