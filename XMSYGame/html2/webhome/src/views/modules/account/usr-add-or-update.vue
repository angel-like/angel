<template>
  <el-dialog
    :title="!dataForm.id ? '新增会员' : '修改会员基本资料'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="会员账号" prop="account">
        <el-input v-model="dataForm.account" readonly placeholder="会员账号"></el-input>
      </el-form-item>
      <el-form-item label="会员类型" prop="userType">
				<el-select v-model="dataForm.userType" disabled   placeholder="请选择会员类型">
					<el-option
						v-for="item in userTypeList"
						:key="item.code"
						:label="item.name"
						:value="item.code">
					</el-option>
				</el-select>
      </el-form-item>
			<el-form-item label="真实姓名" prop="userName">
				<el-input  v-model="dataForm.userName" disabled="true" clearable placeholder="真实姓名"></el-input>
			</el-form-item>
			<el-form-item label="手机" prop="userPhone">
				<el-input  v-model="dataForm.userPhone" clearable placeholder="手机"></el-input>
			</el-form-item>
			<el-form-item label="QQ" prop="userQq">
				<el-input  v-model="dataForm.userQq" clearable placeholder="QQ"></el-input>
			</el-form-item>
			<el-form-item label="邮箱" prop="userEmail">
				<el-input v-model="dataForm.userEmail" clearable placeholder="邮箱"></el-input>
			</el-form-item>
			<el-form-item label="证件号" prop="userIdentity">
				<el-input v-model="dataForm.userIdentity" clearable placeholder="证件号"></el-input>
			</el-form-item>
			<el-form-item label="备注" prop="remark" style="position: relative;">
				<el-input
				  type="textarea"
				  placeholder="请输入备注"
				  v-model="dataForm.remark"
				  maxlength="30"
				  @input="descInput"
				  show-word-limit>
				</el-input>
				<span class="text" style="color: #909399;margin-right: 55px;position: absolute;bottom: 0px;right:-5px;">已输入{{remnant}}字/30</span>
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
			var checkPhone = (rule, value, callback) => {

				if (value) {
					var retgex=this.regObject.phone;
					if (!retgex.test(value)) {
							callback(new Error('请输入正确手机号码'));
						}else{
							callback();
						}
				} else {
					callback();
				}
			};
			var checkQQ = (rule, value, callback) => {
				if (value ) {
					var retgex=this.regObject.qq;
					if (!retgex.test(value)) {
							callback(new Error('请输入正确QQ号码'));
						}else{
							callback();
						}

				} else {
					callback();
				}
			};
			var checkEmail = (rule, value, callback) => {
				if (value) {
					var retgex=this.regObject.email;
					if (!retgex.test(value)) {
							callback(new Error('请输入正确邮箱'));
						}else{
							callback();
						}
				} else {
					callback();
				}
			};
			var checkUserName = (rule, value, callback) => {
				if (value) {
					var retgex=this.regObject.userName;
					if (!retgex.test(value)) {
							callback(new Error('真实姓名：字母和汉字组合，2-20个字符'));
						}else{
							callback();
						}
				} else {
					callback();
				}
			};
			var checkIdentity = (rule, value, callback) => {
				if (value) {
					var retgex=this.regObject.identity;
					if (!retgex.test(value)) {
							callback(new Error('请输入正确身份证号码'));
						}else{
							callback();
						}
				} else {
					callback();
				}
			};
			var checkRemark = (rule, value, callback) => {
				if (value) {
					var retgex=this.regObject.remark;
					if (!retgex.test(value)) {
							callback(new Error('输入的字数设定30以内'));
						}else{
							callback();
						}
				} else {
					callback();
				}
			};
      return {
		remnant:0,
        visible: false,
		oldUser:{},
		userTypeList:[],
        dataForm: {
			id: 0,
			account: '',
			userType: '',
			userName: '',
			userPhone: '',
			userQq: '',
			userEmail: '',
			userIdentity: '',
			remark: ''
        },
		regObject:{
			qq:/^[0-9]*$/,
			phone:/^\d{11,}$/,
			email:/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
			identity:/^\d{15}|\d{}18$/,
			userName:/^[A-Za-z\u4e00-\u9fa5]+$/,
			remark:/^[\s\S]{0,30}$/,
		},
        dataRule: {
          account: [
            { required: true, message: '账号不能为空', trigger: 'blur' }
          ],
          userType: [
            { required: true, message: '会员类型不能为空', trigger: 'blur' }
          ],
					userName: [
						{ required: true, message: '真实姓名不能为空', trigger: 'blur' },
            { validator: checkUserName, trigger: 'blur' }
          ],
			userPhone : [
				{ validator: checkPhone, trigger: 'blur' }
			],
			userQq : [
				{ validator: checkQQ, trigger: 'blur' }
			],
			userEmail : [
				{ validator: checkEmail, trigger: 'blur' }
			],
			userIdentity : [
				{ validator: checkIdentity, trigger: 'blur' }
			],
			remark : [
				{ validator: checkRemark, trigger: 'blur' }
			]
        }
      }
    },
    methods: {

      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.getUseType();
          if (this.dataForm.id) {
            this.$http({
            	url: this.$http.adornUrl(`/user/user/allinfo/${this.dataForm.id}`),
            	method: 'get',
            	params: this.$http.adornParams()
            }).then(({data}) => {
            	if (data && data.code === 200) {
            		this.oldUser = data.userinfo
            		this.dataForm.account=data.user.account
            		this.dataForm.userName=data.user.userName
            		this.oldUser.userName=data.user.userName
            		this.dataForm.userType=data.user.userType
            		this.dataForm.userPhone=data.user.phone
            		this.dataForm.userQq=data.userinfo.userQq
					this.dataForm.userEmail=data.userinfo.userEmail
					this.dataForm.userIdentity=data.userinfo.userIdentity
					this.dataForm.remark=data.user.remark
					this.remnant = data.user.remark.length
            	}
            })
          }
        })
      },
	    //计算输入数
	  descInput(){
	  		var txtVal = this.dataForm.remark.length;
	  		this.remnant = txtVal;
	  },
			// 获取会员类型列表
			getUseType(){
				this.$http({
					url: this.$http.adornUrl(`/user/user/getUseType`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.userTypeList = data.userTypeList
					}
				});
			},
			getModifyContent(option,oldVal,newVal){
				var result="";
				if(oldVal!=newVal){
					result=option;
					if(oldVal){
						result+="由【"+oldVal+"】修改为【";
					}else{
						result+="由【空】修改为【";
					}
					if(newVal){
						result+=newVal+"】;";
					}else{
						result+="空】;";
					}
				}
				return result;
			},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
						if(this.oldUser.userPhone==this.dataForm.userPhone &&
								this.oldUser.userQq==this.dataForm.userQq &&
								this.oldUser.userName==this.dataForm.userName &&
								this.oldUser.userEmail==this.dataForm.userEmail &&
								this.oldUser.userIdentity==this.dataForm.userIdentity &&
								this.oldUser.remark==this.dataForm.remark ){
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
						var userName=null;
						if(this.dataForm.userName!=this.oldUser.userName){
							userName=this.dataForm.userName;
						}


						var remark="";
						var userOperater={};
						userOperater.memberId=this.dataForm.id;
						userOperater.memberAccount=this.dataForm.account ;
						remark+=this.getModifyContent("真实姓名",this.oldUser.userName,this.dataForm.userName);
						remark+=this.getModifyContent("手机号码",this.oldUser.userPhone,this.dataForm.userPhone);
						remark+=this.getModifyContent("QQ",this.oldUser.userQq,this.dataForm.userQq);
						remark+=this.getModifyContent("邮箱",this.oldUser.userEmail,this.dataForm.userEmail);
						remark+=this.getModifyContent("证件号",this.oldUser.userIdentity,this.dataForm.userIdentity);
						remark+=this.getModifyContent("备注",this.oldUser.remark,this.dataForm.remark);
						userOperater.remark=remark;

            this.$http({
              url: this.$http.adornUrl(`/user/operation/updateBaseInfo`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.oldUser.id ,
                'userPhone': this.dataForm.userPhone,
				'userId': this.dataForm.id,
				'userName': userName,
                'userQq': this.dataForm.userQq,
                'userEmail': this.dataForm.userEmail,
                'userIdentity': this.dataForm.userIdentity,
                'remark':this.dataForm.remark,
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
