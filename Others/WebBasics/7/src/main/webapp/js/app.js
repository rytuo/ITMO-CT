window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

/*window.ajax = function (action, data, func) {
    data.action = action;

    $.ajax({
        type: "POST",
        dataType: "json",
        data: data,
        success: function(response) {
            func(response);
            if (response["redirect"]) {
                location.href = response["redirect"];
            }
        }
    });

    delete data.action;
}*/

window.ajax = function(extensionRequest, error) {
    var basicRequest = {
        type: "POST",
        url: "",
        dataType: "json",

        success: function(response) {
            if (response["error"]) {
                error.text(response["error"])
            } else {
                location.href = response["redirect"]
            }
        }
    }

    $.extend(basicRequest, extensionRequest)
    $.ajax(basicRequest)

    return false;
}