<template>
	<div class="mod-config">
		<el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
			<el-form-item>
				<el-input v-model="dataForm.channelName" placeholder="渠道名称" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataListQuery()">查询</el-button>
				<el-button v-if="isAuth('adchannelconfig:adchannelconfig:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('adchannelconfig:adchannelconfig:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
			</el-form-item>
		</el-form>
		<div><font color="red">必须先设置一个空的渠道码</font></div>
		<el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle" style="width: 100%;">
			<el-table-column type="selection" header-align="center" align="center" width="50">
			</el-table-column>
			<el-table-column prop="id" header-align="center" align="center" label="id">
			</el-table-column>
			<el-table-column prop="channelName" header-align="center" align="center" label="渠道名称">
			</el-table-column>
			<el-table-column prop="channelCode" header-align="center" align="center" label="渠道码">
			</el-table-column>
			<el-table-column prop="channelAddress" header-align="center" align="center" label="渠道地址">
			</el-table-column>
			<el-table-column prop="iosDownLoadUrl" header-align="center" align="center" label="ios下载地址">
			</el-table-column>
			<el-table-column prop="androidDownLoadUrl" header-align="center" align="center" label="安卓下载地址">
			</el-table-column>
			<el-table-column prop="publishUrl" header-align="center" align="center" label="发布地址">
			</el-table-column>
			<!-- <el-table-column prop="user" header-align="center" align="center" label="用户名">
			</el-table-column>
			<el-table-column prop="pwd" header-align="center" align="center" label="密码">
			</el-table-column>
			<el-table-column prop="loginUrl" header-align="center" align="center" label="登录地址">
			</el-table-column> -->
			<el-table-column prop="remake" header-align="center" align="center" label="备注">
			</el-table-column>
			<el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
				<template slot-scope="scope">
					<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
					<el-button type="text" size="small" @click="shareWeixinHandle(scope.row.id,scope.row.publishUrl,scope.row.iosDownLoadUrl,scope.row.androidDownLoadUrl)">微信分享下载</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
		 :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage" layout="total, sizes, prev, pager, next, jumper">
		</el-pagination>
		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList" @refreshSetInterval="inToInterval"></add-or-update>
		<share-weixin v-if="shareWeixinVisible" ref="shareWeixin" @refreshDataList="getDataList"></share-weixin>
	</div>
</template>

<script>
	import AddOrUpdate from './adchannelconfig-add-or-update'
	import shareWeixin from './share-weixin'
	export default {
		data() {
			return {
				dataForm: {
					channelName: ''
				},
				updateOrSaveId:0,
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				addOrUpdateVisible: false,
				shareWeixinVisible:false
			}
		},
		components: {
			AddOrUpdate,
			shareWeixin
		},
		activated() {
			this.getDataList()
		},
		created() {
			this.keyupSubmit()
		},
		methods: {
			// 获取数据列表
			getDataList() {
				this.dataListLoading = true
				this.$http({
					url: this.$http.adornUrl('/adchannelconfig/adchannelconfig/list'),
					method: 'get',
					params: this.$http.adornParams({
						'page': this.pageIndex,
						'limit': this.pageSize,
						'channelName': this.dataForm.channelName
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
			//设置10秒一次的定时器  发送查询打包是否完成
			inToInterval(){
				//this.getDataList();
				this.dataListLoading = true
				//定时器
				var timerID;
				timerID=setInterval(()=>{
					this.dataListLoading = true
					//获取下拉选择
					this.$http({
						url: this.$http.adornUrl(`/adchannelconfig/adchannelconfig/getStatus`),
						method: 'post',
						data: this.$http.adornData({
							'id':this.updateOrSaveId,//通过id才能对获取的文件地址 添加进当前这行 ----修改
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
							clearInterval(timerID);
							this.$message({
								message: '成功',
								type: 'success',
								duration: 1500,
							})
							this.dataListLoading = false
							this.getDataList();//打包完成需要重新刷新页面
						}else if(data.data==2){
							this.$message({
								message: '15分钟超时',
								type: 'warning',
								duration: 1500,
							})
							this.dataListLoading = false
							clearInterval(timerID);
						}else if(data.data==4){
							this.$message({
								message: '数据库已存在该条记录',
								type: 'warning',
								duration: 1500,
							})
							this.dataListLoading = false
							clearInterval(timerID);
						}else{
							this.$message.error("调用第三方接口错误")
							this.dataListLoading = false
							clearInterval(timerID);
						}
					});
				},10000);
			},
			//绑定回车事件
			keyupSubmit() {
				document.onkeydown = e => {
					let _key = window.event.keyCode;
					if (_key === 13) {
						this.getDataListQuery()
					}
				}
			},
			//查询
			getDataListQuery() {
				this.pageIndex = 1;
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
				this.updateOrSaveId=id
				this.addOrUpdateVisible = true
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id)
				})
			},
			//分享微信弹窗
			shareWeixinHandle(id,publishUrl,iosDownLoadUrl,androidDownLoadUrl) {
				this.shareWeixinVisible = true
				this.$nextTick(() => {
					this.$refs.shareWeixin.init(id,publishUrl,iosDownLoadUrl,androidDownLoadUrl)
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
						url: this.$http.adornUrl('/adchannelconfig/adchannelconfig/delete'),
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
