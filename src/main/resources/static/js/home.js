window.getCookie = function(name) {
    let match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
    if (match)
        return match[2];
    return '';
}

window.fixApiTime = function(time) {
    if(!time)
       return '';
   return (time.match(/:/g) || []).length == 2 ? time : (time + ':00');
}

$(function() {
    $(".nav-item .nav-link").on("click", function(){
       $(".nav-item").find(".active").removeClass("active");
       $(this).addClass("active");
    });
});