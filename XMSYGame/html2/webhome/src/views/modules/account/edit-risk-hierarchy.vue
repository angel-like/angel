<template>
  <el-dialog
    :title=" '修改会员风控层级'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="风控层级" prop="riskHierarchyId">
        <el-select v-model="dataForm.riskHierarchyId" placeholder="请选择风控层级">
					<el-option
						v-for="item in options"
						:key="item.id"
						:label="item.name"
						:value="item.id">
					</el-option>
				</el-select>
      </el-form-item>
			<el-form-item label="检索风控" prop="noScan" > 
				<el-radio-group v-model="dataForm.noScan" >
					<el-radio :label="false">是</el-radio>
					<el-radio :label="true">否</el-radio>
				</el-radio-group>
			</el-form-item>
    </el-form>
		<span style="padding-right: 25px; color: red;">
		检索风控 根据菜单-用户风控配置表 每分钟扫描</br>
		是 ，开启自动扫描</br>
		否，手动控制
		</span>
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
          riskHierarchyId: 0,
					userAccount:"",
					oldRiskHierarchyId: 0,
					noScan:false,
        },
        dataRule: {
           riskHierarchyId: [
            { required: true, message: '风控层级不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
				var riskList=[{id:0,name:"无"}];
        this.dataForm.id = id || 0
        this.visible = true
       this.$nextTick(() => {
       this.$refs['dataForm'].resetFields()
       	this.$http({
       		url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
       		method: 'get',
       		params: this.$http.adornParams({"hierarchyType":1})
       	}).then(({data}) => {
       		if (data && data.code === 200) {
       			this.options = riskList.concat(data.hierarchyList);
					}else{
						this.options = riskList;
					}
       	});
       	if (this.dataForm.id) {
       		this.$http({
       			url: this.$http.adornUrl(`/user/user/info/${this.dataForm.id}`),
       			method: 'get',
       			params: this.$http.adornParams()
       		}).then(({data}) => {
       			if (data && data.code === 200) {
       				this.dataForm.riskHierarchyId = data.user.riskHierarchyId,
							this.dataForm.oldRiskHierarchyId = data.user.riskHierarchyId,
							this.dataForm.userAccount = data.user.account,
							this.dataForm.noScan = data.user.noScan
							console.log(data)
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
						if(this.dataForm.oldRiskHierarchyId==cengjiList[i].id){
							oldHierarchyName=cengjiList[i].name;
						}
						if(this.dataForm.riskHierarchyId==cengjiList[i].id){
							hierarchyName=cengjiList[i].name;
						}
				} 
				modifyContent="用户风控层级由【"+oldHierarchyName+"】移动到【"+hierarchyName+"】";
				var userOperater={};
				userOperater.memberId=this.dataForm.id;
				userOperater.memberAccount=this.dataForm.userAccount ;
				userOperater.remark=modifyContent;
				var abnormal=true;
				if(this.dataForm.riskHierarchyId==0){
					abnormal=false;
				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/user/operation/editRiskHierarchy`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id ,
                'riskHierarchyId': this.dataForm.riskHierarchyId,
								'noScan': this.dataForm.noScan,
								'account': this.dataForm.userAccount,
								'abnormalEnable': abnormal,
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
