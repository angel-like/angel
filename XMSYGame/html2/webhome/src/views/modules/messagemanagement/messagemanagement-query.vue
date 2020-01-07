<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '查看'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="标题" prop="title">
			{{dataForm.title}}
    </el-form-item>
    <el-form-item label="内容" prop="content">
			{{dataForm.content}}
    </el-form-item>
   <!-- <el-form-item label="状态" prop="enable" >
			<div v-if="dataForm.enable">启用</div>
			<div v-else>禁用</div>
    </el-form-item> -->
		<el-form-item label="生效时间" prop="effectTime">
			<el-date-picker v-model="dataForm.effectTime" readonly type="datetime"  :editable="false"  format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="生效时间" default-time="00:00:00">
			         </el-date-picker>
		</el-form-item>
		<el-form-item label="失效时间" prop="failureTime">
			<el-date-picker v-model="dataForm.failureTime"  readonly type="datetime"  :editable="false"  format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"  placeholder="失效时间" default-time="00:00:00">
			         </el-date-picker>
		</el-form-item>
    <el-form-item label="目标对象" prop="targetObject">
				<div v-if="dataForm.targetObject==1">指定用户</div>
				<div v-else-if="dataForm.targetObject==2">指定层次</div>
				<div v-else-if="dataForm.targetObject==3">所有用户</div>
    </el-form-item>
		<el-form-item v-if="dataForm.targetObject==2" label="指定层次" prop="hierarchy">
				<el-checkbox :indeterminate="isIndeterminate" disabled  v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
				<div style="margin: 15px 0;"></div>
				<el-checkbox-group v-model="dataForm.hierarchy" @change="handleCheckedHierarchyChange" disabled>
					<el-checkbox v-for="hierarchy in hierarchyOptions" :label="hierarchy.id" :key="hierarchy.name">{{hierarchy.name}}</el-checkbox>
				</el-checkbox-group>
		</el-form-item>
		<el-form-item v-if="dataForm.targetObject==1" label="指定用户" prop="users">
			<el-input  type="textarea" :rows="2" v-model="dataForm.users" placeholder="请输入指定用户账号,多个用英文的逗号相隔"></el-input>
		</el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="visible = false">关闭</el-button>
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
					title: '', 
					hierarchy: [], 
          users: '', content: ''  ,        contentType: ''  ,       
					 enable: true,    targetObject: '' , 
						effectTime:this.getTimes(),
						rightNow:false,
						failureTime:''
				},
				checkAll: false,
				checkOptions : [],
				checkAllOptions : [],
				isIndeterminate: true,
				hierarchyOptions:[],
        dataRule: {
        }
      }
    },
    methods: {
			changeTarget(){
				if(this.dataForm.targetObject!=1){
					this.dataForm.rightNow=false;
				}
			},
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
						url: this.$http.adornUrl(`/messagemanagement/messagemanagement/getHierarchy`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.checkOptions = data.hierarchyList
							this.hierarchyOptions= data.hierarchyList
							this.checkAllOptions = data.ids
						}
					});
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/messagemanagement/messagemanagement/info/${this.dataForm.id}`),
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
									this.dataForm.hierarchy = JSON.parse("["+data.messageManagement.hierarchyIds+"]");
								}
              }
            })
          }
        })
			
      },
			 handleCheckAllChange(val) {
        this.dataForm.hierarchy = val ? this.checkAllOptions : [];
        this.isIndeterminate = false;
      },
      handleCheckedHierarchyChange(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.hierarchyOptions.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyOptions.length;
      }
      
    }
  }
</script>
