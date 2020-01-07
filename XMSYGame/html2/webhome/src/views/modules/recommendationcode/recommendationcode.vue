<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.userAccount" placeholder="会员账号" clearable></el-input>
      </el-form-item>
			<el-form-item>
				<el-input v-model="dataForm.recommendationCode" placeholder="邀请码" clearable></el-input>
			</el-form-item>
			<el-form-item >
			<el-select v-model="dataForm.sortFiled"  placeholder="请选择排序方式">
				<el-option
					v-for="item in orderList"
					:key="item.key"
					:label="item.label"
					:value="item.key">
				</el-option>
			</el-select>
			</el-form-item>
			<el-select v-model="dataForm.direction" placeholder="升/降" style="width: 80px;">
				<el-option v-for="item in directionOptions" :key="item.direction" :label="item.label" :value="item.direction">
				</el-option>
			</el-select>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
				<el-button @click="exportCSV()">下载Excel</el-button>
        <!--<el-button v-if="isAuth('recommendationcode:recommendationcode:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
         <el-button v-if="isAuth('recommendationcode:recommendationcode:delete2')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
      	type="index"
      	width="120"
      	header-align="center"
      	align="center"
      	label="序号">
      </el-table-column>
      <!-- <el-table-column
        prop="userId"
        header-align="center"
        align="center"
        label="会员ID">
      </el-table-column> -->
      <el-table-column
        prop="userAccount"
        header-align="center"
        align="center"
        label="会员账号">
      </el-table-column>
      <el-table-column
        prop="recommendationCode"
        header-align="center"
        align="center"
        label="邀请码">
      </el-table-column>
      <el-table-column
        prop="num"
        header-align="center"
        align="center"
        label="邀请人数">
      </el-table-column>
      <el-table-column
        prop="amount"
        header-align="center"
        align="center"
        label="返佣金额">
      </el-table-column>
			<el-table-column
			  prop="agentEnable"
			  header-align="center"
			  align="center"
			  label="状态">
				<template slot-scope="scope">
					<div v-if="scope.row.agentEnable">开</div>
					<div v-if="!scope.row.agentEnable">关</div>
				</template>
			</el-table-column>
			 <el-table-column
			  prop="createTime"
			  header-align="center"
			  align="center"
			  label="创建时间">
			</el-table-column>
			 <el-table-column
			  prop="updateTime"
			  header-align="center"
			  align="center"
			  label="修改时间">
			</el-table-column>
			<!--
			<el-table-column
				prop="coin"
				header-align="center"
				align="center"
				label="返佣金币">
				<template slot-scope="scope">
						<div>
								{{scope.row.coin/100}}
						</div>
				</template>
			</el-table-column>-->
			<el-table-column
			  prop="agentEnable"
			  header-align="center"
			  align="center"
			  label="是否启用">
				<template slot-scope="scope">
					<el-switch v-model="scope.row.agentEnable" active-color="#13ce66" inactive-color="#ff4949" @change="updateEnable(scope.row.id,scope.row.userId,scope.row.agentEnable)">
					</el-switch>
				</template>
			</el-table-column>
      <!-- <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id,scope.row.userId,scope.row.userAccount,scope.row.recommendationCode)">删除</el-button>
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
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './recommendationcode-add-or-update'
	import json2csv from 'json2csv'
  export default {
    data () {
      return {
        dataForm: {
					sortFiled:'num',
          userAccount: '',
          recommendationCode: '',
					agentEnable:'',
					direction:'desc'
        },
        dataList: [],
        orderList: [
					{"key":"num","label":"邀请人数"},
					{"key":"amount","label":"返利"},
					{"key":"create_time","label":"创建时间"},
					{"key":"user_account","label":"用户账号"}
				],
				directionOptions: [{
						direction: 'asc',
						label: '升序'
					},
					{
						direction: 'desc',
						label: '倒序'
					}
				],
				
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate,
			json2csv
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/recommendationcode/recommendationcode/list'),
          method: 'get',
          params: this.$http.adornParams({
            'sort': this.dataForm.sortFiled,
						'direction': this.dataForm.direction,
						'page': this.pageIndex,
            'limit': this.pageSize,
            'userAccount': this.dataForm.userAccount,
            'recommendationCode': this.dataForm.recommendationCode
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
			exportCSV() {
				this.downLoadMix("邀请码管理.csv");
			},
			downLoadMix(title) {
				this.$http({
					url: this.$http.adornUrl('/recommendationcode/recommendationcode/exportCSVData'),
					method: 'get',
					responseType: 'arraybuffer',
					params: this.$http.adornParams({
						'sort': this.dataForm.sortFiled,
						'direction': this.dataForm.direction,
						'userAccount': this.dataForm.userAccount,
						'recommendationCode': this.dataForm.recommendationCode
					})
				}).then(({
					data
				}) => {
					var fields_ = [{
							label: "会员ID",
							value: "userId"
						}, {
							label: "会员账号",
							value: "userAccount"
						}, {
							label: "邀请码",
							value: "recommendationCode"
						}, {
							label: "邀请人数",
							value: "num"
						}, {
							label: "返佣金额",
							value: "amount"
						},
						{
							label: "创建时间",
							value: "createTime"
						},
						{
							label: "修改时间",
							value: "updateTime"
						}
					];
					var opt = {
						fields: fields_,
						excelStrings: true
					}
					if (data && data.code === 200) {
						const result = json2csv.parse(data.dataList, opt);
						const csvContent = 'data:text/csv;charset=utf-8,\uFEFF' + result
						const link = document.createElement('a')
						link.href = encodeURI(csvContent)
						link.download = title;
						document.body.appendChild(link) // Required for FF
						link.click()
						document.body.removeChild(link) // Required for FF
					}
				})
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
			//修改状态滑块触发事件
			updateEnable(id,userId,agentEnable) {
				var userOperater = {};
				userOperater.memberId = this.dataForm.userId;
				userOperater.memberAccount = this.dataForm.userAccount;
				if(this.dataForm.agentEnable){
					userOperater.remark = "邀请码状态变更为开启";
				}else{
					userOperater.remark = "邀请码状态变更为关闭";
				}
				this.$http({
					url: this.$http.adornUrl(`/recommendationcode/recommendationcode/AgencyAuthority`),
					method: 'post',
					data: this.$http.adornData({
						'id': id || undefined,
						'userId': userId,
						'agentEnable': agentEnable,
						'userOperater': userOperater
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.$message({
							message: '操作成功',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.visible = false
								this.$emit('refreshDataList')
							}
						})
					} else {
						this.$message.error(data.msg)
					}
				})
			},
      // 删除
      deleteHandle (id,userId,userAccount,code) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
				var userOperater={};
				userOperater.memberId=userId;
				userOperater.memberAccount=userAccount ;
				userOperater.remark="删除邀请码为"+code;
				
        this.$confirm(`确定对会员账号[${userAccount}]进行删除邀请码操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/recommendationcode/recommendationcode/deleteByEntity'),
            method: 'post',
           data: this.$http.adornData({
           	'id': id,
           	'userOperater': userOperater,
           })
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
