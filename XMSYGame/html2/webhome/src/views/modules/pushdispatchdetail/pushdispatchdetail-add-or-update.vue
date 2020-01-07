<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="90px">
		<el-tooltip class="item" effect="dark" content="发送范围(指定用户发送)" placement="top-start">
		<el-form-item label="范围" prop="scope">
			<el-select  v-model="dataForm.scope"  placeholder="请选择任务范围">
			<el-option
				v-for="item in scopeOption"
				:key="item.id"
				:label="item.name"
				:value="item.id">
			</el-option>
			</el-select>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="发送类型" placement="top-start">
		<el-form-item label="类型" prop="type">
			<el-select  v-model="dataForm.type"  placeholder="请选择任务类型">
			<el-option
				v-for="item in typeOption"
				:key="item.id"
				:label="item.name"
				:value="item.id">
			</el-option>
			</el-select>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="选择发送时间方式" placement="top-start">
    <el-form-item v-show="dataForm.type!=1" label="执行时间" prop="executeTime">
			<el-date-picker
				v-model="dataForm.executeTime"
				type="datetime"
				placeholder="选择执行时间">
			</el-date-picker>
    </el-form-item>
	</el-tooltip>
    <el-form-item label="发送内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="发送内容"></el-input>
    </el-form-item>
	<el-tooltip class="item" effect="dark" content="多ip用逗号分割" placement="top-start">
		<el-form-item v-show="dataForm.scope==2" label="接收者层级" prop="hierarchyId">
			<el-select v-model="dataForm.hierarchyIds" multiple placeholder="请选择层级">
				<el-option
					v-for="item in options"
					:key="item.id"
					:label="item.name"
					:value="item.id">
				</el-option>
			</el-select>
		</el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="输入接收者账号" placement="top-start">
		<el-form-item v-show="dataForm.scope==3" label="接收者账号" prop="recipient">
			<el-input v-model="dataForm.recipient" placeholder="接收者账号"></el-input>
			<span style="padding-right: 25px; color: red;">
			接收者账号多个用逗号隔开
			</span>
		</el-form-item>
		</el-tooltip>
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
      return {
        visible: false,
				options:[],
        dataForm: {
          id: 0,
          operator: '',
          recipient: '',
          executeTime: '',
          scope: 1,
          type: 1,
          hierarchyIds: ''
        },
				scopeOption: [
					{
						id: 1,
						name: "发送所有用户"
					},
					{
						id: 2,
						name: "指定层级发送"
					},
					{
						id: 3,
						name: "指定用户发送"
					}
				],
				typeOption: [
					{
						id: 1,
						name: "立即发送"
					},
					{
						id: 2,
						name: "定时发送"
					}
// 					,
// 					{
// 						id: 3,
// 						name: "循环定时发送"
// 					}
				],
        dataRule: {
// 					scope: [
// 						{ required: true, message: '定时器范围吧不能为空', trigger: 'blur' }
// 					],
// 					type: [
// 						{ required: true, message: '定时器类型不能为空', trigger: 'blur' }
// 					],
//           executeTime: [
//             { required: true, message: '不能为空', trigger: 'blur' }
//           ],
          content: [
            { required: true, message: '发送内容不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      init (id) {
				this.$http({
					url: this.$http.adornUrl(`/userhierarchy/userhierarchy/select`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options=data.list
					}
				})
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.options=[]
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/pushdispatchdetail/pushdispatchdetail/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.operator = data.pushdispatchdetail.operator
                this.dataForm.recipient = data.pushdispatchdetail.recipient
                this.dataForm.executeTime = data.pushdispatchdetail.executeTime
                this.dataForm.status = data.pushdispatchdetail.status
                this.dataForm.scope = data.pushdispatchdetail.scope
                this.dataForm.type = data.pushdispatchdetail.type
                this.dataForm.content = data.pushdispatchdetail.content
                this.dataForm.failReason = data.pushdispatchdetail.failReason
								if(null!=data.pushdispatchdetail.hierarchyIds&&data.pushdispatchdetail.hierarchyIds!=''){
									var hierarchyIds=data.pushdispatchdetail.hierarchyIds.split(",");
									var hierarchyArray=[];
									for(var i=0; i<hierarchyIds.length; i++){
										hierarchyArray.push(parseInt(hierarchyIds[i]));
									}
									this.dataForm.hierarchyIds=hierarchyArray
								}
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
				if(2==this.dataForm.scope && this.dataForm.hierarchyIds=='') {
					this.$message.error("指定层级发送需要选层级")
					return
				}
				if(3==this.dataForm.scope && this.dataForm.recipient==''){
					this.$message.error("指定发送需要填接收人账号")
					return
				}
				if(2==this.dataForm.type && this.dataForm.executeTime=='') {
					this.$message.error("定时发送需要填执行时间")
					return
				}
				if(3==this.dataForm.type && this.dataForm.executeTime=='') {
					this.$message.error("定时发送需要填执行时间")
					return
				}
				
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/pushdispatchdetail/pushdispatchdetail/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'operator': this.dataForm.operator ,
              'recipient': this.dataForm.recipient ,
              'executeTime': this.dataForm.executeTime ,
              'scope': this.dataForm.scope ,
              'type': this.dataForm.type ,
              'hierarchyIds': this.dataForm.hierarchyIds.join(","),
					    'content': this.dataForm.content ,

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
