/**
 * 
 */

i18nUtil = {
	options: {
		name: 'message'
		,path: 'message/'
		,mode: 'map'
		,language: ''
		,checkAvailableLanguages: true
		,callback: function(){
			console.log('i18n Complate.', this, $.i18n.map);
//			sessionStorage.setItem(this.language, JSON.stringify($.i18n.map));
		}
	}
	,load: function(options){
		if(!$.i18n){
			console.warn('Not Found $.i18n');
		}
			
		var options = $.extend(this.options, options);
		console.log(options);
		
//		var sMap = sessionStorage.getItem(options.language);
//		if(sMap){
//			$.i18n.map = JSON.parse(sMap);
//			return;
//		}
		
		$.i18n.properties(options);
	}
};

