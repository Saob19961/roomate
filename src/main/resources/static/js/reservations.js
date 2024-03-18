function doBooking(book, callback) {
    $.ajax({
        type: "POST",
        url: '/api/buchung',
        data: book,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function updateBooking(book, callback) {
    $.ajax({
        type: "PUT",
        url: '/api/buchung',
        data: book,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
            callback();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert(xhr.responseJSON.cause);
        }
    });
}

function deleteBooking(book, callback) {
    $.ajax({
        type: "DELETE",
        url: '/api/buchung/' + book.id,
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

    function editBook(book) {
        $('#res_arbeitplatz_id').val(book.id);
        $('#res_arbeitplatz').val(book.arbeitplatz.room.name + " / " + book.arbeitplatz.name);
        $('#res_date').val(formatDate(book.data));
        $('#res_from').val(book.buchFrom);
        $('#res_to').val(book.buchTo);

        $('#reservationEditModal').modal('show');
    }

    function deleteBook(book) {
        if(!confirm("Would you like to delete this booking ?"))
            return;

        deleteBooking(book, () => {
            alert('Booking has deleted successfully');

            let targetRow = '#rsrv_' + book.id;
            $(targetRow).remove();
        });
    }

    function book() {
        let arbeitplatz_id = $('#book_arbeitplatz').val() || "";
        let date = $('#book_date').val();
        let time_from = window.fixApiTime($('#book_from').val());
        let time_to = window.fixApiTime($('#book_to').val());

        doBooking(
            JSON.stringify({ user_id, arbeitplatz_id, date, time_from, time_to }),
            () => {
                alert('Space has booked successfully');

                $('#bookModal').modal('hide');
                $('#book_form').trigger("reset");

                reload();
            }
        )
    }

    function reload() {
        $.ajax({
            url: '/api/users/' + user_id + '/reservations',
            type: "GET",
            dataType: "json",
            success: function (data) {
                let list = data;
                $('#reservations_table tbody').empty();

                $.each(list, function(idx, r) {
                    $('#reservations_table tbody')
                      .append(
                        $('<tr id=\'rsrv_' + r.id + '\' scope=row>')
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
                            .append($('<td>').text(formatTime(r.buchFrom)))
                            .append($('<td>').text(formatTime(r.buchTo)))
                            .append(
                                $('<td>')
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold')
                                            .text('Edit')
                                            .click(function() {
                                                editBook(r);
                                            })
                                    )
                                    .append(
                                        $('<button>')
                                            .addClass('btn btn-link btn-rounded btn-sm fw-bold text-danger')
                                            .text('Delete')
                                            .click(function() {
                                                deleteBook(r);
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

    $('#bookModal').on('show.bs.modal', function (e) {
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

    $('#book_form').submit(function(event) {
        event.preventDefault();
        book();
    });

    $('#res_form').submit(function(event) {
        event.preventDefault();

        let book_id = $('#res_arbeitplatz_id').val();
        let date = $('#res_date').val();
        let time_from = window.fixApiTime($('#res_from').val());
        let time_to = window.fixApiTime($('#res_to').val());

        updateBooking(
            JSON.stringify({ book_id, date, time_from, time_to }),
            () => {
                alert('Booking has edited successfully');

                /*let targetRow = '#rsrv_' + book_id;
                $(targetRow).find('td').eq(2).text(date);
                $(targetRow).find('td').eq(3).text(formatTime(time_from));
                $(targetRow).find('td').eq(4).text(formatTime(time_to));*/
                reload();

                $('#reservationEditModal').modal('hide');
                $('#res_form').trigger("reset");
            }
        )
    });

    reload();
});