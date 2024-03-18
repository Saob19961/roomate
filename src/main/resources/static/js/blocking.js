function doBlocking(block, callback) {
    $.ajax({
        type: "POST",
        url: '/api/block',
        data: block,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function updateBlocking(block, callback) {
    $.ajax({
        type: "PUT",
        url: '/api/block',
        data: block,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function deleteBooking(block, callback) {
    $.ajax({
        type: "DELETE",
        url: '/api/block/' + block.id,
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

    function formatDate(date) {
        if(date)
            return date.substring(0, date.indexOf('T'));
        return '';
    }

    function formatTime(time) {
        if(time)
            return time.substring(0, time.lastIndexOf(':'));
        return '';
    }

    function editBlock(block) {
        $('#block_arbeitplatz_id').val(block.id);
        $('#block_arbeitplatz').val(block.arbeitplatz.room.name + " / " + block.arbeitplatz.name);
        $('#block_date').val(formatDate(block.data));
        $('#block_from').val(block.datumFrom);
        $('#block_to').val(block.datumTo);

        $('#blockEditModal').modal('show');
    }

    function deleteBlock(block) {
        if(!confirm("Would you like to unblock this space ?"))
            return;

        deleteBlocking(book, () => {
            alert('Unblocking successfully');

            let targetRow = '#blk_' + block.id;
            $(targetRow).remove();
        });
    }

    function block() {
        let arbeitplatz_id = $('#book_arbeitplatz').val() || "";
        let date = $('#book_date').val();
        let time_from = window.fixApiTime($('#book_from').val());
        let time_to = window.fixApiTime($('#book_to').val());

        doBlocking(
            JSON.stringify({ user_id, arbeitplatz_id, date, time_from, time_to }),
            () => {
                alert('Space has blocked successfully');

                $('#blockModal').modal('hide');
                $('#block_form').trigger("reset");

                reload();
            }
        )
    }

    function reload() {
        $.ajax({
            url: '/api/users/' + user_id + '/blocks',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let list = data;
                $('#block_table tbody').empty();

                $.each(list, function(idx, r) {
                    $('#block_table tbody')
                      .append(
                        $('<tr id=\'blk_' + r.id + '\' scope=row>')
                            .append(
                                $('<td>')
                                    .append($('<p>').addClass('fw-bold mb-1').text(r.user.name))
                                    .append($('<p>').addClass('text-muted mb-0').text(r.user.githubadresse))
                            )
                            .append(
                                $('<td>')
                                    .append($('<p>').addClass('fw-normal mb-1').text(r.arbeitplatz.room.name))
                                    .append($('<p>').addClass('text-muted mb-0').text(r.arbeitplatz.name))
                            )
                            .append($('<td>').text(formatDate(r.data)))
                            .append($('<td>').text(formatTime(r.datumFrom)))
                            .append($('<td>').text(formatTime(r.datumTo)))
                            .append(
                                $('<td>')
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold')
                                            .text('Edit')
                                            .click(function() {
                                                editBlock(r);
                                            })
                                    )
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold text-danger')
                                            .text('Delete')
                                            .click(function() {
                                                deleteBlock(r);
                                            })
                                    )
                            )
                      );
                });
            }
        });
    }

    $('#book_rooms').change(function () {
        let selectedRoom = this.value;
        $.ajax({
            url: '/api/data/rooms/' + selectedRoom + '/arbeitplatz',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let rooms = data;
                $('#book_arbeitplatz').empty();
                $.each(rooms, function(idx, arbeitplatz) {
                    $('#book_arbeitplatz')
                      .append(
                        $('<option>')
                          .text(arbeitplatz.name)
                          .attr('value', arbeitplatz.id)
                      );
                });
            }
         });
    });

    $('#blockModal').on('show.bs.modal', function (e) {
         $.ajax({
            url: '/api/data/rooms',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let rooms = data;
                $('#book_rooms').empty();
                $.each(rooms, function(idx, room) {
                    $('#book_rooms')
                      .append(
                        $('<option>')
                          .text(room.name)
                          .attr('value', room.id)
                      );
                });
                $("#book_rooms").change();
            }
         });
    });

    $('#block_form').submit(function(event) {
        event.preventDefault();
        block();
    });

    $('#edit_block_form').submit(function(event) {
        event.preventDefault();

        let book_id = $('#block_arbeitplatz_id').val();
        let date = $('#block_date').val();
        let time_from = window.fixApiTime($('#block_from').val());
        let time_to = window.fixApiTime($('#block_to').val());

        updateBlocking(
            JSON.stringify({ book_id, date, time_from, time_to }),
            () => {
                alert('Block has edited successfully');

                /*let targetRow = '#blk_' + book_id;
                $(targetRow).find('td').eq(2).text(date);
                $(targetRow).find('td').eq(3).text(formatTime(time_from));
                $(targetRow).find('td').eq(4).text(formatTime(time_to));*/
                reload();

                $('#blockEditModal').modal('hide');
                $('#edit_block_form').trigger("reset");
            }
        )
    });

    reload();
});