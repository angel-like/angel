<template>
  <el-dialog
    :title="'人工充值'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="金额" prop="amount">
      <el-input type= "number" v-model="dataForm.amount" placeholder="金额"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remake">
      <el-input v-model="dataForm.remake" placeholder="备注"></el-input>
    </el-form-item>
		<el-form-item label="目标对象" prop="targetObject">
			<el-radio-group v-model="dataForm.targetObject">
				<el-radio :label="1">指定会员账号</el-radio>
				<el-radio :label="2">指定层次</el-radio>
			</el-radio-group>
		</el-form-item>
    <el-form-item v-if="dataForm.targetObject==1" label="会员账号" prop="account">
      <el-input type="textarea" :rows="2" v-model="dataForm.account" placeholder="多个会员账号使用英文,相隔"></el-input>
    </el-form-item>
    <el-form-item v-if="dataForm.targetObject==2" label="指定层次" prop="hierarchy">
      <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
      <div style="margin: 15px 0;"></div>
      <el-checkbox-group v-model="dataForm.hierarchy" @change="handleCheckedHierarchyChange">
      	<el-checkbox v-for="hierarchy in hierarchyOptions" :label="hierarchy.id" :key="hierarchy.name">{{hierarchy.name}}</el-checkbox>
      </el-checkbox-group>
    </el-form-item>
		<el-form-item label="是否发送站内信" prop="sendMessage">
			<el-radio-group v-model="dataForm.sendMessage">
				<el-radio :label="true">是</el-radio>
				<el-radio :label="false">否</el-radio>
			</el-radio-group>
		</el-form-item>
		<el-form-item v-if="dataForm.sendMessage" label="站内信标题" prop="messageTitle">
			<el-input v-model="dataForm.messageTitle" placeholder="站内信标题"></el-input>
		</el-form-item>
		<el-form-item v-if="dataForm.sendMessage" label="站内信内容" prop="messageContent">
			<el-input type="textarea" :rows="2" v-model="dataForm.messageContent" placeholder="站内信内容"></el-input>
		</el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button v-if="showButton" type="primary" @click="dataFormSubmit()">确定充值</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
			var checkAmount = (rule, value, callback) => {
					if (value) {
						var retgex=/^\+?[1-9][0-9]*$/;
						if (!retgex.test(value)) {
								callback(new Error('请输入非零的正整数'));
							}else{
								callback();
							}
					} else {
						callback();
					}
				
			};
			var checkHierarchy = (rule, value, callback) => {
					if (this.dataForm.targetObject==2 && !value.length) {
						callback(new Error('目标对象指定层级不能为空'));
					} else {
						callback();
					}
				
			};
			var checkUser = (rule, value, callback) => {
				
					if (this.dataForm.targetObject==1 && !value) {
						callback(new Error('目标对象指定会员不能为空'));
					} else {
						callback();
					}
				
			};
			var checkContent = (rule, value, callback) => {
					if (this.dataForm.sendMessage && !value) {
						callback(new Error('发送站内信的内容不能为空'));
					} else {
						callback();
					}
				
			};
			var checkTitle = (rule, value, callback) => {
					if (this.dataForm.sendMessage && !value) {
						callback(new Error('发送站内信的标题不能为空'));
					} else {
						callback();
					}
				
			};
      return {
        visible: false,
        dataForm: {
          id: 0,
          amount: '',
          remake: '',
          account: '',
					targetObject:1,
					sendMessage:false,
					messageTitle:'',
					messageContent:'',
          hierarchyId: '',
					hierarchy:[],
					hierarchyName:[]
        },
				checkAll: false,
				checkOptions : [],
				checkAllOptions : [],
				isIndeterminate: true,
				showButton: true,
				hierarchyOptions:[],
        dataRule: {
          amount: [
            { required: true, message: '金额不能为空', trigger: 'blur' },
						{ validator: checkAmount, trigger: 'blur' }
          ],
          account: [
            { validator: checkUser, trigger: 'blur' }
						
          ],
          hierarchy: [
            { validator: checkHierarchy, trigger: 'blur' }
          ],
          messageTitle: [
            { validator: checkTitle, trigger: 'blur' }
          ],
          messageContent: [
            { validator: checkContent, trigger: 'blur' }
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
			getHierarchyName(){
				this.dataForm.hierarchyName=[];
				if(this.checkAll){
					for(var i=0;i<this.hierarchyOptions.length;i++){
						this.dataForm.hierarchyName.push(this.hierarchyOptions[i].name);
					}
				}else{
					for(var j=0;j<this.dataForm.hierarchy.length;j++){
							for(var i=0;i<this.hierarchyOptions.length;i++){
								if(this.dataForm.hierarchy[j]==this.hierarchyOptions[i].id){
									this.dataForm.hierarchyName.push(this.hierarchyOptions[i].name);
									break;
								}
							}
					}
				}
			},
      // 表单提交
      dataFormSubmit () {
				//this.showButton=false;
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
						this.getHierarchyName();
						var confimMessage="";
						var remark="";
						if(this.dataForm.targetObject==1){
							confimMessage=`确定对会员账号【${this.dataForm.account}]进行充值操作?`;
							remark=`为会员账号【${this.dataForm.account}】充值【${this.dataForm.amount}】`;
						}else{
							confimMessage=`确定对层级[${this.dataForm.hierarchyName.join(',')}]的会员进行充值操作?`;
							remark=`为层级【${this.dataForm.hierarchyName.join(',')}】下会员账号充值【${this.dataForm.amount}】`;
						}
						var userOperater={};
						userOperater.remark=remark;
						this.$confirm(confimMessage, '提示', {
							confirmButtonText: '确定',
							cancelButtonText: '取消',
							type: 'warning'
						}).then(() => {
								this.$http({
									url: this.$http.adornUrl(`/orderadministratorrecharge/orderadministratorrecharge/create`),
									method: 'post',
									data: this.$http.adornData({
										'id': this.dataForm.id || undefined,
										'amount': this.dataForm.amount ,
										'remake': this.dataForm.remake ,
										'account': this.dataForm.account ,
										'hierarchyId': this.dataForm.targetObject==2? this.dataForm.hierarchy.join(','):"",
										'account': this.dataForm.targetObject==1?this.dataForm.account:"" ,
										'targetObject': this.dataForm.targetObject ,
										'operationType': 0 ,
										'designated': false ,
										'sendMessage': this.dataForm.sendMessage ,
										'messageTitle': this.dataForm.messageTitle ,
										'messageContent': this.dataForm.messageContent,
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
									this.showButton=true;
								})
						})
          }else{
						this.showButton=true;
					}
					
        })
      }
    }
  }
</script>
