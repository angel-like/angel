<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="内容" prop="content">
      <el-input  type="textarea" :rows="2" v-model="dataForm.content" placeholder="请输入内容"></el-input>
    </el-form-item>
		<!--
    <el-form-item label="类型(1：站内信 2:公告）" prop="contentType">
      <el-input v-model="dataForm.contentType" placeholder="类型(1：站内信 2:公告）"></el-input>
    </el-form-item>
		-->
    <el-form-item label="状态" prop="enable">
			<el-radio-group v-model="dataForm.enable">
				<el-radio :label="true">启用</el-radio>
				<el-radio :label="false">禁用</el-radio>
			</el-radio-group>
    </el-form-item>
		<el-form-item label="生效时间" prop="effectTime">
			<el-date-picker v-model="dataForm.effectTime" type="datetime"  :editable="false"  format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="生效时间" default-time="00:00:00">
			         </el-date-picker>
		</el-form-item>
		<el-form-item label="失效时间" prop="failureTime">
			<el-date-picker v-model="dataForm.failureTime" type="datetime"  :editable="false"  format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="失效时间" default-time="23:59:59">
			         </el-date-picker>
		</el-form-item>
    <el-form-item label="目标对象" prop="targetObject">
			<el-radio-group v-model="dataForm.targetObject">
				<el-radio :label="1">指定用户</el-radio>
				<el-radio :label="2">指定角色</el-radio>
				<el-radio :label="3">所有用户</el-radio>
			</el-radio-group>
    </el-form-item>

		<el-form-item v-if="dataForm.targetObject==2" label="指定角色" prop="roles">
				<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
				<div style="margin: 15px 0;"></div>
				<el-checkbox-group v-model="dataForm.roles" @change="handleCheckedRoleChange">
					<el-checkbox v-for="role in roleOptions" :label="role.roleId" :key="role.roleName">{{role.roleName}}</el-checkbox>
				</el-checkbox-group>
		</el-form-item>
		<el-form-item v-if="dataForm.targetObject==1" label="指定用户" prop="users">
			<el-input  type="textarea" :rows="2" v-model="dataForm.users" placeholder="请输入指定用户账号,多个用英文的逗号相隔"></el-input>
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
			var checkUser = (rule, value, callback) => {

					if (this.dataForm.targetObject==1 && !value) {
						callback(new Error('目标对象指定用户不能为空'));
					} else {
						callback();
					}

			};
			var checkRole = (rule, value, callback) => {
					if (this.dataForm.targetObject==2 && !value.length) {
						callback(new Error('目标对象指定角色不能为空'));
					} else {
						callback();
					}

			};
			var checkFailureTime = (rule, value, callback) => {
					if (value && this.dataForm.effectTime) {
						if(value<=this.dataForm.effectTime){
							callback(new Error('失效时间不能早于生效时间'));
						}else {
							callback();
						}

					} else {
						callback();
					}

			};
      return {
        visible: false,
        dataForm: {
          id: 0,
					title: '',
					roles: [],
          users: '', content: ''  ,        contentType: ''  ,
					 enable: true,    targetObject: '' ,
						effectTime:this.getTimes(),
						failureTime:''
				},
				checkAll: false,
				checkOptions : [],
				checkAllOptions : [],
				isIndeterminate: true,
				roleOptions:[],
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          targetObject: [
            { required: true, message: '目标对象不能为空', trigger: 'blur' }
          ],
					users: [
						{ validator: checkUser, trigger: 'blur' }
					],
					roles: [
						{ validator: checkRole, trigger: 'blur' }

					],
          effectTime: [
            { required: true, message: '生效时间不能为空', trigger: 'blur' }
          ],
          failureTime: [
            { required: true, message: '失效时间不能为空', trigger: 'blur' },
            { validator: checkFailureTime, trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
			getTimes(){
				let date = new Date()
				      let y = date.getFullYear()
				      let m = date.getMonth() + 1
				      let d = date.getDate()
				      if (m < 10) {
				        m = '0' + m
				      }
				      if (d < 10) {
				        d = '0' + d
				      }

				      let time = y + '-' + m + '-' + d
				return time+" 00:00:00";
			},
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
         this.$refs['dataForm'].resetFields()
					this.$http({
						url: this.$http.adornUrl(`/messagemanagement/adminmessage/getRole`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.checkOptions = data.roleList
							this.roleOptions= data.roleList
							this.checkAllOptions = data.ids
						}
					});
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/messagemanagement/adminmessage/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.title = data.messageManagement.title
                this.dataForm.content = data.messageManagement.content
                this.dataForm.contentType = data.messageManagement.contentType
                this.dataForm.enable = data.messageManagement.enable
                this.dataForm.targetObject = data.messageManagement.targetObject
                this.dataForm.effectTime = data.messageManagement.effectTime
								this.dataForm.failureTime = data.messageManagement.failureTime
								if(this.dataForm.targetObject==1){
									this.dataForm.users = data.messageManagement.userAccount
								}else if(this.dataForm.targetObject==2){
									this.dataForm.roles = JSON.parse("["+data.messageManagement.hierarchyIds+"]");
								}
              }
            })
          }
        })

      },
			 handleCheckAllChange(val) {
        this.dataForm.roles = val ? this.checkAllOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedRoleChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.roleOptions.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.roleOptions.length;
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/messagemanagement/adminmessage/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'title': this.dataForm.title ,
								'content': this.dataForm.content ,
								'contentType': this.dataForm.contentType ,
								'enable': this.dataForm.enable ,
								'targetObject': this.dataForm.targetObject ,
								'hierarchyIds': this.dataForm.targetObject==2? this.dataForm.roles.join(','):"",
								'userAccount': this.dataForm.targetObject==1?this.dataForm.users:"" ,
								'effectTime': this.dataForm.effectTime,
								'failureTime': this.dataForm.failureTime
              })
            }).then(({data}) => {
              if (data && data.code === 200) {
								if (data.isOk) {
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
									this.$message.error(data.errmsg)
								}
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
