<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-select v-model="dataForm.channelCode" clearable placeholder="渠道码">
					<el-option v-for="item in channelConfigOptions" :key="item.channelCode" :label="item.channelCode" :value="item.channelCode">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-select v-model="dataForm.iosName" clearable placeholder="苹果签名">
					<el-option v-for="item in iosNameOptions" :key="item.certificateName" :label="item.certificateName" :value="item.certificateName">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('webhomefilepackage:webhomefilepackage:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('webhomefilepackage:webhomefilepackage:delete')" type="danger" @click="deleteHandle()"
				 :disabled="dataListSelections.length <= 0">批量删除</el-button>
				<el-button v-if="isAuth('webhomefilepackage:webhomefilepackage:update')" type="primary" @click="updateHandle()"
				 :disabled="dataListSelections.length <= 0" style="text-align:right;">批量修改更新地址</el-button>
			</el-form-item>
		</el-form>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<!--<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="proxyNo" header-align="center" align="center" label="代理编号">
			</el-table-column>
			<el-table-column prop="proxyAlias" header-align="center" align="center" label="代理别名">
			</el-table-column>-->
			<el-table-column prop="channelName" header-align="center" align="center" label="渠道名称">
			</el-table-column>
			<el-table-column prop="channelCode" header-align="center" align="center" label="渠道码">
			</el-table-column>
			<el-table-column prop="iosName" header-align="center" align="center" label="苹果签名">
			</el-table-column>
			<el-table-column prop="expireTime" header-align="center" align="center" label="过期时间">
			</el-table-column>
			<el-table-column prop="updateUrl" header-align="center" align="center" label="更新地址">
			</el-table-column>
			<!--
			<el-table-column prop="iosFileUrl" header-align="center" align="center" label="苹果文件打包地址">
			</el-table-column>
			<el-table-column prop="androidFileUrl" header-align="center" align="center" label="安卓文件打包地址">
			</el-table-column>-->
			<el-table-column prop="remark" header-align="center" align="center" label="备注">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="下载">
				<template slot-scope="scope">
					<div v-if="scope.row.iosFileUrl!=null && scope.row.iosFileUrl!=''"> 
						<a :href="scope.row.iosFileUrl">苹果下载</a>
					</div>
					<div v-if="scope.row.androidFileUrl!=null&&scope.row.androidFileUrl!=''">
					 	<a :href="scope.row.androidFileUrl">安卓下载</a>
					 </div>
					<!--<el-button type="text" size="small" :href="scope.row.iosFileUrl" :disabled="scope.row.iosFileUrl==null||scope.row.iosFileUrl==''">苹果下载</el-button><br/>
					<el-button type="text" size="small" :href="scope.row.androidFileUrl" :disabled="scope.row.androidFileUrl==null||scope.row.androidFileUrl==''">安卓下载</el-button>-->
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="重置">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="goFilePackage('ios','build',scope.row.id,scope.row.iosName,scope.row.channelCode,scope.row.updateUrl)">重打苹果</el-button><br/>
					<el-button type="text" size="small" @click="goFilePackage('android','build',scope.row.id,scope.row.iosName,scope.row.channelCode,scope.row.updateUrl)">重打安卓</el-button>
				</template>
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
	</div>
</template>

<script>
	import AddOrUpdate from './webhomefilepackage-add-or-update'
	export default {
		data() {
			return {
				dataForm: {
					channelCode: '',
					iosName:''
				},
				iosNameOptions: [],
				channelConfigOptions:[],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false
			}
		},
		components: {
			AddOrUpdate
		},
		activated() {
			this.getDataList()
		},
		created(){
		 this.keyupSubmit()
		},
		methods: {
			// 获取数据
			getDataList() {
				//获取苹果签证下拉选择
				this.$http({
					url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/selectIosName`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.iosNameOptions = data.iosNameList
					}
				});
				//获取渠道配置下拉选择
				this.$http({
					url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/selectChannelConfig`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.channelConfigOptions = data.channelConfigList
					}
				});
				//获取数据列表
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/webhomefilepackage/webhomefilepackage/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'channelCode': this.dataForm.channelCode,
						'iosName': this.dataForm.iosName
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataList = data.page.list
						this.totalPage = data.page.totalCount
					} else {
						this.dataList = []
						this.totalPage = 0
					}
					this.dataListLoading = false
				})
			},
			//绑定回车事件
			keyupSubmit(){
				document.onkeydown=e=>{
					let _key=window.event.keyCode;
					if(_key===13){
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery(){
				this.pageIndex=1;
				this.getDataList();
			},
			// 每页数
			sizeChangeHandle(val) {
				this.pageSize = val
				this.pageIndex = 1
				this.getDataList()
			},
			// 当前页
			currentChangeHandle(val) {
				this.pageIndex = val
				this.getDataList()
			},
			// 多选
			selectionChangeHandle(val) {
				this.dataListSelections = val
			},
			// 新增 / 修改
			addOrUpdateHandle(id) {
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id)
				})
			},
			//调用第三方接口 获取文件打包地址
			goFilePackage(platParam, type,id,iosName,channelCode,updateUrl) {
				//console.log(platParam +"---------------"+ type+"----"+id+"----"+iosName+"----"+channelCode+"-----"+updateUrl)
				this.dataListLoading = true
				//定时器，超过30秒 直接把this.dataListLoading = 设为false
				var timeoutID;
				timeoutID=setTimeout(()=>{
					this.dataListLoading = false
				},30000);
				//定时器
				var timerID;
				timerID=setInterval(()=>{
					//获取下拉选择
					this.$http({
						url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/getStatus`),
						method: 'post',
						data: this.$http.adornData({
							'id':id,//通过id才能对获取的文件地址 添加进当前这行 ----修改
							'platform': platParam, //安卓或者 苹果 平台
							'buildtype': type, //第一次打包，还是重新打包
							'iosName':iosName, //要通过苹果签名去查询 苹果企业证书 表的数据去打包
							'channelCode':channelCode,//渠道码
							'updateUrl':updateUrl,//更新地址
						})
					}).then(({
						data
					}) => {
						if(data.data==0){
							this.$message({
								message: '正在打包中，请稍等',
								type: 'warning',
								duration: 1500,
							})
						}else if(data.data==1){
							clearTimeout(timeoutID);
							clearInterval(timerID);
							this.$message({
								message: '打包完成',
								type: 'success',
								duration: 1500,
							})
							
						}else if(data.data==2){
							clearTimeout(timeoutID);
							this.$message({
								message: '15分钟超时',
								type: 'warning',
								duration: 1500,
							})
							clearInterval(timerID);
						}else{
							clearTimeout(timeoutID);
							this.$message.error("打包错误")
							clearInterval(timerID);
						}
					});
				},10000);
				//执行打包
				this.$http({
					url: this.$http.adornUrl(`/webhomefilepackage/webhomefilepackage/goFilePackage`),
					method: 'post',
					data: this.$http.adornData({
						'id':id,//通过id才能对获取的文件地址 添加进当前这行 ----修改
						'platform': platParam, //安卓或者 苹果 平台
						'buildtype': type, //第一次打包，还是重新打包
						'iosName':iosName, //要通过苹果签名去查询 苹果企业证书 表的数据去打包
						'channelCode':channelCode,//渠道码
						'updateUrl':updateUrl,//更新地址
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						if(data.appdabao!=null){
							this.$message({
								message: data.appdabao,
								type: 'warning',
								duration: 1500,
							})
						}else{
							this.$message({
								message: '操作成功',
								type: 'success',
								duration: 1500,
							})
						}
							this.getDataList();
					} else {
						this.$message.error(data.msg)
					}
					this.dataListLoading = false
					clearInterval(timerID);
				})
			},
			// 批量修改更新地址
			updateHandle(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '更新' : '批量更新'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.dataListLoading = true
					this.$http({
						url: this.$http.adornUrl('/webhomefilepackage/webhomefilepackage/updateurl'),
						method: 'post',
						data: this.$http.adornData(ids, false)
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.$message({
								message: '操作成功',
								type: 'success',
								duration: 1500,
								onClose: () => {
									this.getDataList()
								}
							})
						} else {
							this.$message.error(data.msg)
						}
						this.dataListLoading = false
					})
				})
			},
			// 删除
			deleteHandle(id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/webhomefilepackage/webhomefilepackage/delete'),
						method: 'post',
						data: this.$http.adornData(ids, false)
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.$message({
								message: '操作成功',
								type: 'success',
								duration: 1500,
								onClose: () => {
									this.getDataList()
								}
							})
						} else {
							this.$message.error(data.msg)
						}
					})
				})
			}
		}
	}
</script>
