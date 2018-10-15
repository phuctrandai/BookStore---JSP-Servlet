$(document).ready(function(){
	// Check to see if the window is top if not then display
	$(window).scroll(function(){
		if($(this).scrollTop() > 100)
			$('#scrollToTopBtn').fadeIn();
		else
			$('#scrollToTopBtn').fadeOut();
	});
	
	// Click event to scroll to top
	$('#scrollToTopBtn').click(function(){
		$('html, body').animate({scrollTop : 0}, 800);
		return false;
	});
});