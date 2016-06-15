/**
 * 
 */
$(document).ready(function(){
	$.validator.setDefaults({
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).data('title', '').tooltip('destroy')
				.closest('.form-group').removeClass('has-error');
		},
		errorElement : 'span',
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			$(element).closest('.form-group').append(error);

//			var intputType = element.attr('type')
//			if(intputType == 'checkbox' || intputType == 'radio'){
//				element.parent().data({
//					toggle: 'tooltip',
//					placement: 'top',
//					title: error.text()
//				}).tooltip();
//			}else{
//				element.data({
//					toggle: 'tooltip',
//					placement: 'top',
//					title: error.text()
//				}).tooltip();
//			}
		}
	});
	
	$('#loginForm').validate({
		rules: {
			userid: {
				required: true
			},
			password: {
				required: true
			}
		},
		submitHandler: function(form){
			form.submit();
		}
	});
});