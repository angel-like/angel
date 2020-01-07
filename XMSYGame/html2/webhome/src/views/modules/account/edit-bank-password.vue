<template>
  <el-dialog
    :title=" '修改取款密码'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="用户账号" >
				<div>
					{{dataForm.userAccount}}
				</div>
      </el-form-item>
			<el-form-item label="新取款密码" prop="password">
				<el-input type="password" v-model="dataForm.password" placeholder="新取款密码"></el-input>
			</el-form-item>
      <el-form-item label="确认取款密码" prop="confirmPassword">
        <el-input type="password" v-model="dataForm.confirmPassword" placeholder="确认取款密码"></el-input>
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
			var validatePass = (rule, value, callback) => {
				var res =	/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/;
				if (value === '') {
					callback(new Error('请输入新密码'));
				} else {
					if(!res.test(value)){
						callback(new Error('密码格式有误'));
					}
					callback();
				}
			};
			var validatePass2 = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请再次新输入密码'));
				} else if (value !== this.dataForm.password) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
      return {
				
        visible: false,
        dataForm: {
          id: 0,
          password: '',
					userAccount:'',
          confirmPassword: ''
        },
        dataRule: {
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          confirmPassword: [
           { validator: validatePass2, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id,userAccount) {
        this.dataForm.id = id || 0
				this.dataForm.userAccount = userAccount
				this.$nextTick(() => {
				 this.$refs['dataForm'].resetFields()
				 });
        this.visible = true
       
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
						var userOperater={};
						userOperater.memberId=this.dataForm.id;
						userOperater.memberAccount=this.dataForm.userAccount ;
						userOperater.remark="修改用户的取款密码";
            this.$http({
              url: this.$http.adornUrl(`/user/passwd/editBank`),
              method: 'post',
              data: this.$http.adornData({
                'userId': this.dataForm.id ,
                'password': this.dataForm.password,
								'confirmPassword': this.dataForm.confirmPassword,
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
