<template>
	<el-dialog
		:title=" '用户相关信息'"
		width="1000px"
		:close-on-click-modal="false"
		:visible.sync="visible" 
		>
  <div style="width: 100%;">
		<div style="width: 20%;float:left;">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span><el-tag>用户详情</el-tag></span>
				</div>
				<div  class="text item">
						<el-form :model="dataForm"  ref="dataForm" >
							<el-form-item label="用户id" style="margin-bottom: 0; border-bottom:1px solid #ccc;" >
								<div>{{dataForm.id}}</div>
							</el-form-item>
							<el-form-item label="用户名" style="margin-bottom: 0;border-bottom:1px solid #ccc;" >
								<div>{{dataForm.account}}</div>
							</el-form-item>
							<el-form-item label="代理商id" style="margin-bottom: 0;border-bottom:1px solid #ccc;" >
								<div>{{dataForm.superiorsId}}</div>
							</el-form-item>
							<el-form-item label="真实姓名" style="margin-bottom: 0;border-bottom:1px solid #ccc;" >
								<div>{{dataForm.userName}}</div>
							</el-form-item>
							<el-form-item label="注册ip" style="margin-bottom: 0;border-bottom:1px solid #ccc;" >
								<div>{{dataForm.registeIp}}</div>
							</el-form-item>
							<el-form-item label="设备码" style=",margin-bottom: 0;border-bottom:1px solid #ccc;" >
								<div>{{dataForm.registerDeviceCode}}</div>
							</el-form-item>
						</el-form>
				</div>
			</el-card>
		</div>
		<div style="width: 80%;float: right;">
			<el-card class="box-card">
				<div slot="header" class="clearfix">
					<span><el-tag>账户详情</el-tag></span>
				</div>
				<div  class="text item">
						<el-table
							:data="bankDataList"
							border
							v-loading="dataListLoading"
							style="width: 100%;">
							<!-- <el-table-column
								prop="alipayAccount"
								header-align="center"
								align="center"
								label="阿里账号">
							</el-table-column> -->
							<el-table-column
								prop="bankCard"
								header-align="center"
								align="center"
								label="用户银行卡号">
							</el-table-column>
							<el-table-column
								prop="bankName"
								header-align="center"
								align="center"
								label="银行名称">
							</el-table-column>
							<el-table-column
								prop="bankAccountName"
								header-align="center"
								align="center"
								label="银行开户名">
							</el-table-column>
							<el-table-column
								prop="bankAddress"
								header-align="center"
								align="center"
								label="开户支行">
							</el-table-column> 
							<el-table-column
								fixed="right"
								header-align="center"
								align="center"
								width="150px"
								label="操作">
								<template slot-scope="scope">
									<el-button size="mini" type="primary"   @click="deleteHandle()">删除</el-button>
									<el-button size="mini" type="primary"   @click="addOrUpdateHandle()">编辑</el-button>
								</template>
							</el-table-column>
						</el-table>
				</div>
			</el-card>
		</div>
		<div  style="clear:both"></div>
  </div>
			<panl-editBankinfo v-if="addOrUpdateVisible" ref="PanlEditBankinfo" @refreshDataList="getData"></panl-editBankinfo>
	</el-dialog>
</template>

<script>
	import PanlEditBankinfo from './edit-bankinfo'

  export default {
    data () {
      return {
				visible:false,
        dataForm: {
          id: 0,
					account:'',
					superiorsId:'',
					userName:'',
					registeIp:'',
					registerDeviceCode:''
        },
        bankDataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
				innerVisible:false
      }
    },
    components: {
			PanlEditBankinfo
    },
    activated () {
     // this.getData()
    },
    methods: {
			init (id) {
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					this.getData();
				})
			},
			getData(){
				this.bankDataList=[];
				if (this.dataForm.id) {
					this.$http({
						url: this.$http.adornUrl(`/user/user/allinfo/${this.dataForm.id}`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.dataForm.account = data.user.account
							this.dataForm.superiorsId = data.user.superiorsId
							this.dataForm.userName = data.user.userName
							this.dataForm.registeIp = data.user.registeIp
							this.dataForm.registerDeviceCode = data.user.registerDeviceCode
							var bank={};
							bank.bankName=data.userinfo.bankName
							bank.bankCard=data.userinfo.bankCard
							// bank.alipayAccount=data.userinfo.alipayAccount
							bank.bankAddress=data.userinfo.bankAddress
							bank.bankAccountName=data.userinfo.bankAccountName
							this.bankDataList.push(bank);
							
						}
					})
				}
			},
      // 新增 / 修改
      addOrUpdateHandle () {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.PanlEditBankinfo.initEdit(this.dataForm.id,this.dataForm.userName)
        })
      },
      // 删除
      deleteHandle () {
        this.$confirm(`确定删除用户银行卡信息操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
					var modifyContent="用户银行信息由【"+"银行姓名："+
								this.bankDataList[0].bankName+"，银行支行："+this.bankDataList[0].bankAddress
							 +"，银行开户行："+this.bankDataList[0].bankAccountName+"，银行卡号："+this.bankDataList[0].bankCard+
								"】修改为【空】";
					var userOperater={};
					userOperater.memberId=this.dataForm.id;
					userOperater.memberAccount=this.dataForm.account ;
					userOperater.remark=modifyContent;
					this.$http({
							url: this.$http.adornUrl(`/user/operation/deleteBankInfo`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id ,
								'userOperater': userOperater
							})
						}).then(({data}) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getData()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
					
        }).catch(() => {
					this.$message({
            type: 'info',
            message: '已取消删除'
          });      
				})
      }
    }
  }
</script>
