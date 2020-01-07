<template>
  <div style="width: 100%;">
		<div style="width: 49%;float: left;">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span>初始化奖金设定</span>
				</div>
				<div  class="text item">
						<el-form :model="dataForm"  ref="dataForm" >
							<el-form-item label="代理奖金组"  prop="paramKey">
								<div>{{dataForm.paramKey}}</div>
							</el-form-item>
							<el-form-item label="会员奖金组" prop="paramValue">
								<div>{{dataForm.paramKey}}</div>
							</el-form-item>
							<el-form-item label="平台最低返点" prop="remark">
								<div>{{dataForm.paramKey}}</div>
							</el-form-item>
							<el-form-item label="是否默认注册未代理" prop="remark">
								<div>{{dataForm.paramKey}}</div>
							</el-form-item>
						</el-form>
				</div>
			</el-card>
		</div>
		<div style="width: 49%;float: right;">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span>用户注册选项</span>
				</div>
				<div  class="text item">
						<el-table
							:data="dataList"
							border
							v-loading="dataListLoading"
							style="width: 100%;">
							<el-table-column
								prop="filedName"
								header-align="center"
								align="center"
								label="显示名称">
							</el-table-column>
							<el-table-column
								prop="isShow"
								header-align="center"
								align="center"
								label="是否展示">
								<template slot-scope="scope">
								<el-switch
								v-model="scope.row.isShow"
								active-color="#13ce66"
								inactive-color="#ff4949">
								</el-switch>
								</template>
							</el-table-column>
							<el-table-column
								prop="isMust"
								header-align="center"
								align="center"
								label="是否必填">
								<template slot-scope="scope">
								<el-switch
								v-model="scope.row.isMust"
								active-color="#13ce66"
								inactive-color="#ff4949">
								</el-switch>
								</template>
							</el-table-column>
							<el-table-column
								prop="message"
								header-align="center"
								align="center"
								label="提示语">
							</el-table-column> 
							<el-table-column
								prop="order"
								header-align="center"
								align="center"
								label="排序(拖到图标)">
							</el-table-column>
						</el-table>
				</div>
			</el-card>
		</div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dataForm: {
          paramKey: ''
        },
        dataList: [
					{"filedName":"手机","isShow":true,"isMust":true,"message":"出入款需要验证手机号","order":1},
					{"filedName":"QQ","isShow":false,"isMust":false,"message":"出入款需要验证QQ","order":1},
					{"filedName":"邮箱","isShow":false,"isMust":false,"message":"出入款需要验证邮箱","order":1},
					{"filedName":"身份证","isShow":true,"isMust":true,"message":"出入款需要验证身份证","order":1},
					{"filedName":"邀请码","isShow":true,"isMust":true,"message":"出入款需要验证邀请码","order":1}
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
    },
    activated () {
      //this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/sys/config/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'paramKey': this.dataForm.paramKey
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
            url: this.$http.adornUrl('/sys/config/delete'),
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
        }).catch(() => {})
      }
    }
  }
</script>
