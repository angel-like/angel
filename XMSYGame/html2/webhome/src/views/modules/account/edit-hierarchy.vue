<template>
  <el-dialog
    :title=" '修改层级'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="层级" prop="hierarchyId">
        <el-select v-model="dataForm.hierarchyId" placeholder="请选择层级">
					<el-option
						v-for="item in options"
						:key="item.id"
						:label="item.name"
						:value="item.id">
					</el-option>
				</el-select>
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
				options:[],
        dataForm: {
          id: 0,
          hierarchyId: 0,
					userAccount:"",
					oldHierarchyId: 0
        },
        dataRule: {
          hierarchyId: [
            { required: true, message: '层级不能为空', trigger: 'blur' }
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
       	this.$http({
       		url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
       		method: 'get',
       		params: this.$http.adornParams({"hierarchyType":0})
       	}).then(({data}) => {
       		if (data && data.code === 200) {
       			this.options = data.hierarchyList
					}
       	});
       	if (this.dataForm.id) {
       		this.$http({
       			url: this.$http.adornUrl(`/user/user/info/${this.dataForm.id}`),
       			method: 'get',
       			params: this.$http.adornParams()
       		}).then(({data}) => {
       			if (data && data.code === 200) {
       				this.dataForm.hierarchyId = data.user.hierarchyId
							this.dataForm.oldHierarchyId = data.user.hierarchyId
							this.dataForm.userAccount = data.user.account
							
       			}
       		})
       	}
       })
      },
      // 表单提交
      dataFormSubmit () {
				var modifyContent="";
				var oldHierarchyName="";//旧层级名称
				var hierarchyName="";//层级名称
				var cengjiList=this.options;
				for (var i = 0; i < cengjiList.length; i++) {  
						if(this.dataForm.oldHierarchyId==cengjiList[i].id){
							oldHierarchyName=cengjiList[i].name;
						}
						if(this.dataForm.hierarchyId==cengjiList[i].id){
							hierarchyName=cengjiList[i].name;
						}
				} 
				modifyContent="用户层级由【"+oldHierarchyName+"】移动到【"+hierarchyName+"】";
				var userOperater={};
				userOperater.memberId=this.dataForm.id;
				userOperater.memberAccount=this.dataForm.userAccount ;
				userOperater.remark=modifyContent;
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/user/operation/editHierarchy`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id ,
                'hierarchyId': this.dataForm.hierarchyId,
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
