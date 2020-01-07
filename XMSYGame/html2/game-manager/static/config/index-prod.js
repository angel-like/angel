/**
 * 生产环境
 */
;(function () {
  window.SITE_CONFIG = {};

  // api接口请求地址
	let uurl = window.location.host
  let htp = location.protocol
  const urlHerf = htp + '//' + uurl
  window.SITE_CONFIG['baseUrl'] = urlHerf+'/ingame';

	//window.SITE_CONFIG['baseUrl'] = 'https://www.abqpht.com';
	

  // cdn地址 = 域名 + 版本号
  window.SITE_CONFIG['domain']  = './'; // 域名
  window.SITE_CONFIG['version'] = '';   // 版本号(年月日时分)
  window.SITE_CONFIG['cdnUrl']  = window.SITE_CONFIG.domain + window.SITE_CONFIG.version;
})();
