function doRoom(room, callback) {
    $.ajax({
        type: "POST",
        url: '/api/data/rooms',
        data: room,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function updateRoom(room, callback) {
    $.ajax({
        type: "PUT",
        url: '/api/data/rooms',
        data: room,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function _deleteRoom(room, callback) {
    $.ajax({
        type: "DELETE",
        url: '/api/data/rooms/' + room.id,
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

$(function() {
    var user_id = window.getCookie('rome_mate_user');

    function editRoom(room) {
        $('#eroom_id').val(room.id);
        $('#eroom_name').val(room.name);
        $('#eroom_des').val(room.adresse);
        $('#eroom_cap').val(room.kapatzität);

        $('#roomEditModal').modal('show');
    }

    function deleteRoom(room) {
        if(!confirm("Would you like to delete this room with its spaces ?"))
            return;

        _deleteRoom(room, () => {
            alert('Deleted successfully');

            let targetRow = '#room_' + room.id;
            $(targetRow).remove();
        });
    }

    function room() {
        let name = $('#room_name').val();
        let adresse = $('#room_des').val();
        let kapatzität = $('#room_cap').val();

        doRoom(
            JSON.stringify({ name, adresse, kapatzität }),
            () => {
                alert('Room has created successfully');

                $('#roomModal').modal('hide');
                $('#room_form').trigger("reset");

                reload();
            }
        )
    }

    function reload() {
        $.ajax({
            url: '/api/data/rooms',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let list = data;
                $('#room_table tbody').empty();

                $.each(list, function(idx, r) {
                    $('#room_table tbody')
                      .append(
                        $('<tr id=\'room_' + r.id + '\' scope=row>')
                            .append($('<td>').text(r.name))
                            .append($('<td>').text(r.adresse))
                            .append($('<td>').text(r.kapatzität))
                            .append(
                                $('<td>')
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold')
                                            .text('Edit')
                                            .click(function() {
                                                editRoom(r);
                                            })
                                    )
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold text-danger')
                                            .text('Delete')
                                            .click(function() {
                                                deleteRoom(r);
                                            })
                                    )
                            )
                      );
                });
            }
        });
    }

    $('#room_form').submit(function(event) {
        event.preventDefault();
        room();
    });

    $('#edit_room_form').submit(function(event) {
        event.preventDefault();

        let id = $('#eroom_id').val();
        let name = $('#eroom_name').val();
        let adresse = $('#eroom_des').val();
        let kapatzität = $('#eroom_cap').val();

        updateRoom(
            JSON.stringify({ id, name, adresse, kapatzität }),
            () => {
                alert('Room has edited successfully');

                /*let targetRow = '#room_' + id;
                $(targetRow).find('td').eq(0).text(name);
                $(targetRow).find('td').eq(1).text(adresse);
                $(targetRow).find('td').eq(2).text(kapatzität);*/
                reload();

                $('#roomEditModal').modal('hide');
                $('#edit_room_form').trigger("reset");
            }
        )
    });

    reload();
});