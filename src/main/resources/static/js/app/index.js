var main = {
    init: function () {
        var _this = this;
        $('#btn_save').on('click', function () {
            _this.save();
        });
    },

    save: function () {
        var data = {
            originUrl: $('#originUrl').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/urls',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (response) {
            var code = response.code;
            if(code == 400) {
                alert(response.message);
                return;
            }
            if(code == 200) {
                $('#shortUrl').text(response.data.url);
                $('#requestCount').text(response.data.count);
                return;
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();