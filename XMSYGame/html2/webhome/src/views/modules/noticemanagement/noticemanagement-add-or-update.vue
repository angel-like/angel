<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
		
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-tooltip class="item" effect="dark" content="公告名称" placement="top-start">
    <el-form-item label="标题" prop="title">
      <el-input v-model="dataForm.title" placeholder="标题"></el-input>
    </el-form-item>
		</el-tooltip>
		<el-tooltip class="item" effect="dark" content="公告内容" placement="top-start">
    <el-form-item label="内容" prop="content">
      <el-input type="textarea" :rows="2" v-model="dataForm.content" placeholder="内容"></el-input>
    </el-form-item>
		</el-tooltip>
    <el-form-item label="状态" prop="enable">
     <el-radio-group v-model="dataForm.enable">
     	<el-radio :label="true">启用</el-radio>
     	<el-radio :label="false">禁用</el-radio>
     </el-radio-group>
    </el-form-item>
		<el-tooltip class="item" effect="dark" content="选择推送目标" placement="top-start">
		<el-form-item label="推送目标" prop="noticeType">
			<el-select v-model="dataForm.noticeType" clearable placeholder="请选择存款方式">
				<el-option v-for="item in noticeTypeOptions" :key="item.id" :label="item.label" :value="item.id">
				</el-option>
			</el-select>
		</el-form-item>
		</el-tooltip>
    <el-form-item label="生效时间" prop="effectTime">
     <el-date-picker v-model="dataForm.effectTime" type="datetime"  :editable="false" 
			format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"  
			placeholder="生效时间" default-time="00:00:00">
              </el-date-picker>
    </el-form-item>
		<el-form-item label="失效时间" prop="failureTime">
		<el-date-picker v-model="dataForm.failureTime" type="datetime"  :editable="false" 
			format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"  
			placeholder="失效时间" default-time="00:00:00">
		         </el-date-picker>
		</el-form-item>
		<!-- <el-form-item label="目标对象" prop="targetObject">
			<el-radio-group v-model="dataForm.targetObject">
				<el-radio :label="2">指定层次</el-radio>
				<el-radio :label="3">所有用户</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item v-if="dataForm.targetObject==2" label="指定层次" prop="hierarchy">
				<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
				<div style="margin: 15px 0;"></div>
				<el-checkbox-group v-model="dataForm.hierarchy" @change="handleCheckedHierarchyChange">
					<el-checkbox v-for="hierarchy in hierarchyOptions" :label="hierarchy.id" :key="hierarchy.name">{{hierarchy.name}}</el-checkbox>
				</el-checkbox-group>
		</el-form-item> -->
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
// 			var checkHierarchy = (rule, value, callback) => {
// 					if (this.dataForm.targetObject==2 && !value.length) {
// 						callback(new Error('目标对象指定层级不能为空'));
// 					} else {
// 						callback();
// 					}
// 				
// 			};
			var checkFailureTime = (rule, value, callback) => {
					if (value && this.dataForm.effectTime) {
						if(value<=this.dataForm.effectTime){
							callback(new Error('失效时间不能早于生效时间'));
						}	else {
							callback();
						}
						
					} else {
						callback();
					}
				
			};
      return {
        visible: false,
				checkAll: false,
				checkOptions : [],
				checkAllOptions : [],
				isIndeterminate: true,
				noticeTypeOptions: [{
						id: 0,
						label: '--全部--'
					},
					{
						id: 1,
						label: '主大厅'
					},
					{
						id: 2,
						label: '房卡大厅'
					}
				],
				// hierarchyOptions:[],
        dataForm: {
          id: 0,
					noticeType: 0,
					hierarchy: [], 
					targetObject:3,
                    title: '',      
					content: '',         
					enable: true,      
					effectTime: this.defaultEffectTime() ,
					failureTime:this.defaultFailureTime() ,
					},
        dataRule: {
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态', trigger: 'blur' }
          ],
          effectTime: [
          	{ required: true, message: '生效时间不能为空', trigger: 'blur' }
          ],
          failureTime: [
            { required: true, message: '失效时间不能为空', trigger: 'blur' },
						{ validator: checkFailureTime, trigger: 'blur' }
          ],
					 targetObject: [
						{ required: true, message: '目标对象不能为空', trigger: 'blur' }
					],
// 					hierarchy: [
// 						{ validator: checkHierarchy, trigger: 'blur' }
// 
// 					],
        }
      }
    },
    methods: {
			 defaultEffectTime(){
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
			defaultFailureTime(){
				let date = new Date()
							let y = date.getFullYear()
							let m = date.getMonth() + 2
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
// 					this.$http({
// 						url: this.$http.adornUrl(`/noticemanagement/noticemanagement/getHierarchy`),
// 						method: 'get',
// 						params: this.$http.adornParams()
// 					}).then(({data}) => {
// 						if (data && data.code === 200) {
// 							this.checkOptions = data.hierarchyList
// 							this.hierarchyOptions= data.hierarchyList
// 							this.checkAllOptions = data.ids
// 						}
// 					});
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/noticemanagement/noticemanagement/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.title = data.noticeManagement.title
                this.dataForm.content = data.noticeManagement.content
                this.dataForm.enable = data.noticeManagement.enable
                this.dataForm.effectTime = data.noticeManagement.effectTime
                this.dataForm.failureTime = data.noticeManagement.failureTime
                this.dataForm.targetObject = data.noticeManagement.targetObject
				this.dataForm.noticeType = data.noticeManagement.noticeType
// 								this.dataForm.hierarchy = JSON.parse("["+data.noticeManagement.hierarchyIds+"]");
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
			},
      // 表单提交
      dataFormSubmit () {
				console.log("dataFormSubmit")
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/noticemanagement/noticemanagement/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'title': this.dataForm.title ,
								'content': this.dataForm.content ,
								'enable': this.dataForm.enable ,
// 								'targetObject': this.dataForm.targetObject ,
// 								'hierarchyIds': this.dataForm.targetObject==2? this.dataForm.hierarchy.join(','):"",
								'effectTime': this.dataForm.effectTime ,
								'failureTime': this.dataForm.failureTime,
								'noticeType': this.dataForm.noticeType
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
