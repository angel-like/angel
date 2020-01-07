/**
 * 生产环境
 */
;(function () {
  window.SITE_CONFIG = {};

  // api接口请求地址
	let uurl = window.location.host
	let htp = location.protocol
	const urlHerf = htp + '//' + uurl
	window.SITE_CONFIG['baseUrl'] = urlHerf+'/inweb';
	// window.SITE_CONFIG['baseUrl'] = 'http://192.168.0.111:8090';
	

  // cdn地址 = 域名 + 版本号
  window.SITE_CONFIG['domain']  = './'; // 域名
  window.SITE_CONFIG['version'] = '';   // 版本号(年月日时分)
  window.SITE_CONFIG['cdnUrl']  = window.SITE_CONFIG.domain + window.SITE_CONFIG.version;
})();
