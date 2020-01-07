<template>
  <el-dialog
    :title="'层级选择'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
		<el-form-item label="层级" prop="hierarchyId">
		  <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
		  <div style="margin: 15px 0;"></div>
		  <el-checkbox-group v-model="dataForm.hierarchyId" @change="handleCheckedhierarchyTypesListChange">
		  	<el-checkbox v-for="hierarchyType in hierarchyTypeList" :label="hierarchyType.id" :key="hierarchyType.id">{{hierarchyType.name}}</el-checkbox>
		  </el-checkbox-group>
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
      return {
				visible: false,
				isIndeterminate: true,
				checkAll: false,
				hierarchyTypeList: [],
				checkAllOptions : [],
				checkOptions : [],
				hierarchyId:'',
        dataForm: {
						id:0,
						hierarchyId: [],
						hierarchyTypesName:[],
        },
        dataRule: {
					 hierarchyId: [
					  { required: true, message: '用户层级不能为空', trigger: 'blur' }
					],
        }
      }
    },
	
    methods: {
      init (id) {
        this.dataForm.id = id || 0;
        this.visible = true
        this.$nextTick(() => {
				this.$refs['dataForm'].resetFields()
				this.checkAll=false;
				this.$http({
				url: this.$http.adornUrl(`/userhierarchy/userhierarchy/selectHierarchy`),
				method: 'get',
				params: this.$http.adornParams()
			}).then(({data}) => {
				if (data && data.code === 200) {
					this.checkOptions = data.dataList,
					this.hierarchyTypeList= data.dataList,
					this.checkAllOptions = data.ids
				}
			});
				if (this.dataForm.id) {
					this.$http({
						url: this.$http.adornUrl(`/hierarchypaychannelconfigrelationship/hierarchypaychannelconfigrelationship/info/${this.dataForm.id}`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
								this.dataForm.hierarchyId = data.hierarchypaychannelconfigrelationship
								let checkedCount = this.dataForm.hierarchyId.length;
								this.checkAll = checkedCount === this.hierarchyTypeList.length;
								this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyTypeList.length;
						}else{
								this.dataForm.hierarchyId =[];
						}
					})
				}
			})
    },
	  handleCheckAllChange(val) {
	  	this.dataForm.hierarchyId = val ? this.checkAllOptions : [];
	  	this.isIndeterminate = false;
	  },
	  handleCheckedhierarchyTypesListChange(value) {
	  	let checkedCount = value.length;
	  	this.checkAll = checkedCount === this.hierarchyTypeList.length;
	  	this.isIndeterminate = checkedCount > 0 && checkedCount < this.hierarchyTypeList.length;
	  },
	  gethierarchyTypesListName(){
	  	this.dataForm.hierarchyTypesName=[];
	  	if(this.checkAll){
	  		for(var i=0;i<this.hierarchyTypeList.length;i++){
	  			this.dataForm.hierarchyTypesName.push(this.hierarchyTypeList[i].name);
	  		}
	  	}else{
	  		for(var j=0;j<this.dataForm.hierarchyId.length;j++){
	  				for(var i=0;i<this.hierarchyTypeList.length;i++){
	  					if(this.dataForm.hierarchyId[j]==this.hierarchyTypeList[i].id){
	  						this.dataForm.hierarchyTypesName.push(this.hierarchyTypeList[i].name);
	  						break;
	  					}
	  				}
	  		}
	  	}
	  },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
			this.gethierarchyTypesListName();
            this.$http({
              url: this.$http.adornUrl(`/hierarchypaychannelconfigrelationship/hierarchypaychannelconfigrelationship/save`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'hierarchyId': this.dataForm.hierarchyId ,
							'paychannelconfigId': this.dataForm.id,
				
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
