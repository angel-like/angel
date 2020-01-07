/**
 * 测试环境
 */
;(function () {
  window.SITE_CONFIG = {};

  // api接口请求地址
  window.SITE_CONFIG['baseUrl'] = 'http://10.2.4.24:8088';
	//window.SITE_CONFIG['baseUrl'] = 'https://www.abqpht.com';
	//window.SITE_CONFIG['baseUrl'] = 'https://127.0.0.1:8080';

  // cdn地址 = 域名 + 版本号
  window.SITE_CONFIG['domain']  = './'; // 域名
  window.SITE_CONFIG['version'] = '';   // 版本号(年月日时分)
  window.SITE_CONFIG['cdnUrl']  = window.SITE_CONFIG.domain + window.SITE_CONFIG.version;
})();
