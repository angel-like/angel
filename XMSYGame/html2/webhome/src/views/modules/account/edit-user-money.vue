<template>
  <el-dialog
    :title=" '修改用户余额'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="用户账号" >
				<div>
					{{dataForm.account}}
				</div>
      </el-form-item>
			<el-form-item label="余额" prop="money">
				<el-input  v-model="dataForm.money" placeholder="余额"></el-input>
			</el-form-item>
      <el-form-item label="金币" prop="coin">
        <el-input  v-model="dataForm.coin" placeholder="金币"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
			var checkMoney = (rule, value, callback) => {
				var res =	/^\d+(\.\d+)?$/;
				if(!res.test(value)){
					callback(new Error('请输入正确的数值'));
				}else if(Number(value)>10000000){
					callback(new Error('最大值是10000000'));
				}else{
					callback();
				}
			};
			var checkCoin = (rule, value, callback) => {
				var res =	/^\d+$/;
				if(!res.test(value)){
					callback(new Error('请输入正确的数值'));
				}else if(Number(value)>1000000000){
					callback(new Error('最大值是1000000000'));
				}else{
					callback();
				}
			};
      return {
        visible: false,
				oldUser:{},
        dataForm: {
          id: 0,
          account: '',
					money:0,
          coin: 0
        },
        dataRule: {
          money: [
						{ required: true, message: '余额不能为空', trigger: 'blur' },
            { validator: checkMoney, trigger: 'blur' }
          ],
          coin: [
						{ required: true, message: '金币不能为空', trigger: 'blur' },
            { validator: checkCoin, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id 
        this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/user/user/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({data}) => {
							if (data && data.code === 200) {
								this.oldUser=data.user;
								this.dataForm.account = data.user.account
								this.dataForm.coin = data.user.coin/100
								this.dataForm.money = data.user.money
							}
						})
					}
				})
       
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
						if(this.oldUser.coin/100==this.dataForm.coin &&
								this.oldUser.money==this.dataForm.money ){
							this.$message({
								message: '内容没有变更',
								type: 'success',
								duration: 1500,
								onClose: () => {
									this.visible = false
									this.$emit('refreshDataList')
								}
							})
							return;
						}
						var userOperater={};
						userOperater.memberId=this.dataForm.id;
						userOperater.memberAccount=this.dataForm.account ;
						userOperater.remark="修改用户余额：";
						if(this.oldUser.money !=this.dataForm.money ){
								userOperater.remark +=`余额由【${this.oldUser.money}】变更【${this.dataForm.money}】;`;
						}
						if(this.oldUser.coin != this.dataForm.coin ){
								userOperater.remark +=`金币由【${this.oldUser.coin/100}】变更【${this.dataForm.coin}】;`;
						}
						
            this.$http({
              url: this.$http.adornUrl(`/user/operation/editUserMoney`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id ,
                'money': this.dataForm.money,
								'coin': this.dataForm.coin*100,
								'userOperater': userOperater
								
              })
            }).then(({data}) => {
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
          }
        })
      }
    }
  }
</script>
