<template>
	<el-dialog
		:title="'操作记录'"
		:close-on-click-modal="false"
		:visible.sync="visible">
		<div class="mod-config">
			<el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
				<el-form-item>
					<el-select v-model="dataForm.stockId" placeholder="风控对象 " clearable>
						<el-option
							v-for="item in options"
							:key="item.id"
							:label="item.roomName"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
				  <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels
				                  range-separator="至"
				                  start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2" :default-time="['00:00:00', '23:59:59']">
				  </el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-button @click="getDataList()">查询</el-button>
					<!-- <el-button v-if="isAuth('gamestockoperationrecord:gamestockoperationrecord:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
					<el-button v-if="isAuth('gamestockoperationrecord:gamestockoperationrecord:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
				</el-form-item>
			</el-form>
			<el-table
				:data="dataList"
				border
				v-loading="dataListLoading"
				@selection-change="selectionChangeHandle"
				style="width: 100%;">
				<el-table-column prop="roomName" header-align="center" align="left" min-width="150" label="操作前信息">
				  <template slot-scope="scope">
						<div>库存id:{{scope.row.stockId}}</div>
				    <div>风控对象:{{scope.row.roomName}}</div>
				    <div>当前风控库存值:{{scope.row.stock/100}}</div>
				    <div>风控库存阀值:{{scope.row.stockLimit/100}}</div>
				    <div>利润收益比例:{{scope.row.taxRate*100}}%</div>
				    <div>运营净利润值:{{scope.row.tax/100}}</div>
						<div>是否启动:
							<el-tag v-if="scope.row.enable" size="small" >是</el-tag>
							<el-tag v-else size="small" type="danger">否</el-tag>
						</div>
				  </template>
				</el-table-column>
				<el-table-column prop="roomName" header-align="center" align="left" min-width="150" label="操作者信息">
				  <template slot-scope="scope">
				    <div>操作者名称:{{scope.row.sysUserName}}</div>
				    <div>操作时间:{{scope.row.createTime}}</div>
				  </template>
				</el-table-column>
				<el-table-column
					prop="operationContent"
					header-align="center"
					align="center"
					label="操作内容"
					min-width="200">
				</el-table-column>
			<!-- 	<el-table-column
					prop="sysUserName"
					header-align="center"
					align="center"
					label="操作者名称">
				</el-table-column>
				<el-table-column
					prop="updateTime"
					header-align="center"
					align="center"
					label="操作时间">
				</el-table-column> -->
				<!-- <el-table-column
					prop="roomName"
					header-align="center"
					align="center"
					label="风控对象">
				</el-table-column>
				<el-table-column
					prop="stock"
					header-align="center"
					align="center"
					label="实际有效库存">
				</el-table-column>
				<el-table-column
					prop="stockLimit"
					header-align="center"
					align="center"
					label="库存上限">
				</el-table-column>
				<el-table-column
					prop="tax"
					header-align="center"
					align="center"
					label="抽税">
				</el-table-column>
				<el-table-column
					prop="taxRate"
					header-align="center"
					align="center"
					label="抽税比例">
				</el-table-column>
				<el-table-column
					prop="enable"
					header-align="center"
					align="center"
					label="是否启动">
					<template slot-scope="scope">
						<el-tag v-if="scope.row.enable" size="small" >是</el-tag>
						<el-tag v-else size="small" type="danger">否</el-tag>
					</template>
				</el-table-column> -->
				<!-- <el-table-column
					prop="sysUserId"
					header-align="center"
					align="center"
					label="操作者id">
				</el-table-column> -->
				<!-- <el-table-column
					fixed="right"
					header-align="center"
					align="center"
					width="150"
					label="操作">
					<template slot-scope="scope">
						<el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
						<el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
					</template>
				</el-table-column> -->
			</el-table>
			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				:page-sizes="[10, 20, 50, 100]"
				:page-size="pageSize"
				:total="totalPage"
				layout="total, sizes, prev, pager, next, jumper">
			</el-pagination>
			<!-- 弹窗, 新增 / 修改 -->
			<!-- <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update> -->
		</div>
	</el-dialog>
</template>

<script>
  // import AddOrUpdate from './gamestockoperationrecord-add-or-update'
	import moment from 'moment';
	import dateutil from '@/utils/datechonse'
  export default {
    data () {
      return {
				pickerOptions2: {
				  shortcuts: [{
				    text: '最近一周',
				    onClick(picker) {
				      const end = new Date();
				      const start = new Date();
				      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
				      picker.$emit('pick', [start, end]);
				    }
				  }, {
				    text: '最近一个月',
				    onClick(picker) {
				      const end = new Date();
				      const start = new Date();
				      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
				      picker.$emit('pick', [start, end]);
				    }
				  }, {
				    text: '最近三个月',
				    onClick(picker) {
				      const end = new Date();
				      const start = new Date();
				      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
				      picker.$emit('pick', [start, end]);
				    }
				  }]
				},
        dataForm: {
          stockId: '',
					queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime]
        },
        dataList: [],
				options: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
				visible: false,
      }
    },
    components: {
      // AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
				//为下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/gamestock/gamestock/getInfo`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.getInfo
						console.log(data)
					}
				});
        this.dataListLoading = true
				var startTime = "";
				var endTime = "";
				var timeArr = this.dataForm.queryTime;
				if (timeArr != null && timeArr.length > 0) {
				  startTime = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
				  if (timeArr.length > 1) {
				    endTime = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
				  }
				}
        this.$http({
          url: this.$http.adornUrl('/gamestockoperationrecord/gamestockoperationrecord/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'stockId': this.dataForm.stockId,
						'startTime': startTime,
						'endTime': endTime
          })
        }).then(({data}) => {
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
			init(){
				this.visible = true
				this.getDataList();
			},
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/gamestockoperationrecord/gamestockoperationrecord/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
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
