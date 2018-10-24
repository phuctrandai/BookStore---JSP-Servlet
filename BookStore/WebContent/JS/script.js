$(document).ready(function(){
	
	// Check to see if the window is top if not then display
	$(window).scroll(function(){
		if($(this).scrollTop() > 100)
			$('#scrollToTopBtn').fadeIn();
		else
			$('#scrollToTopBtn').fadeOut();
		if($(this).scrollTop() >= ($(document).height() - $(window).height()))
			$('#scrollToBotBtn').fadeOut();
		else
			$('#scrollToBotBtn').fadeIn();
	});
	
	// Click event to scroll to top
	$('#scrollToTopBtn').click(function(){
		$('html, body').animate({scrollTop : 0}, 800);
		return false;
	});
	
	// Click event to scroll to bottom
	$('#scrollToBotBtn').click(function(){
		$('html, body').animate({scrollTop: $(document).height() - $(window).height()}, 800);
		return false;
	});
	
	// Cập nhật số lượng sách trong giỏ hàng
	$('.updateBtn').click(function() {
        var bookId = $(this).attr("id");
        var itemQuality = $('#itemQuality'+bookId).val();
        $.post('cart',
        {
            updateBtn : "updateBtn",
            command : "modify",
            bookId : bookId,
            itemQuality : itemQuality
        }, function(data, status) {
            
            var str = data.split(";");
            $('#totalPrice').html(str[0]);
        });
    });
    
	// Xóa sách khỏi giỏ hàng
    $('.removeBtn').click(function() {
        var bookId = $(this).attr("id");
        $.post('cart',
        {   removeBtn : "removeBtn",
            command : "modify",
            bookId : bookId
        }, function(data, status) {
            var str = data.split(";");
            $('#totalPrice').html(str[0]);
            $('tr[id='+bookId+']').remove();
            $('#totalItem').html(str[1]);

            if(str[1] == 0) {
                $('#emptyCart').css("visibility","visible");
                $('#hasItem').remove();
                
            } else {
                $('#emptyCart').css("visibility","collapse");
                $('#hasItem').css("visibility","visible");
            }
        });
    });
});