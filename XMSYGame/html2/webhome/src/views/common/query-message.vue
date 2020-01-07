<template>
  <el-dialog
    :title="'查看站内信'"
    :close-on-click-modal="false" :show-close="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="80px">
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
    <el-form-item label="内容" prop="content">
      <el-input  type="textarea" :rows="3" v-model="dataForm.content" placeholder="请输入内容"></el-input>
    </el-form-item>
		<el-form-item label="生效时间" prop="effectTime">
			<el-input v-model="dataForm.effectTime" ></el-input>
		</el-form-item>
		<el-form-item label="失效时间" prop="failureTime">
			<el-input v-model="dataForm.failureTime" ></el-input>
		</el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dataFormSubmit()">关闭</el-button>
    </span>
  </el-dialog>
</template>

<script>

	
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          statuId: 0,
					title: '', 
					roles: [], 
          users: '', content: ''  ,        contentType: ''  ,       
					 enable: true,    targetObject: '' , 
						effectTime:'',
						failureTime:''
				},
				checkAll: false,
				checkOptions : [],
				checkAllOptions : [],
				isIndeterminate: true,
				roleOptions:[],
        dataRule: {
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
      init (id,statuId) {
        this.dataForm.id = id || 0
        this.dataForm.statuId = statuId
        this.visible = true
        this.$nextTick(() => {
         this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/message/message/info/${this.dataForm.id}/${this.dataForm.statuId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.title = data.messageManagement.title
                this.dataForm.content = data.messageManagement.content
                this.dataForm.effectTime = data.messageManagement.effectTime
								this.dataForm.failureTime = data.messageManagement.failureTime
              }
            })
          }
        })
			
      },
      // 表单提交
      dataFormSubmit () {
				this.visible = false
				this.$emit('refreshDataList')
      }
    }
  }
</script>
