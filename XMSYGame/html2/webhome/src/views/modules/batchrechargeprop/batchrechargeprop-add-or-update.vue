<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
	<el-form-item label="会员账号" prop="account">
		<el-input style="width: 150px" clearable v-model="dataForm.account" placeholder="会员账号"></el-input>
		<el-button @click="getUser()">查找</el-button>
	</el-form-item>
	<el-form-item label="待操作会员账号" prop="userAccount" >
		<div v-clipboard:copy="dataForm.userAccount"  
		 v-clipboard:success="onCopy" 
		 v-clipboard:error="onError" 
		 style="color: blue;cursor:pointer;">
		{{dataForm.userAccount}}
		<i class="el-icon-document"    ></i>
		</div>
	</el-form-item>
	<el-form-item label="真实姓名" >
		<div>{{dataForm.userName}}</div>
	</el-form-item>
	<el-form-item label="房卡数量" >
		<span style="margin-right: 10px;">{{dataForm.roomCard}}</span>
	</el-form-item>
    <el-form-item label="道具名称" prop="propId">
    	<el-select v-model="dataForm.propId" placeholder="道具名称" clearable>
    		<el-option
    			v-for="item in options"
    			:key="item.id"
    			:label="item.name"
    			:value="item.id">
    		</el-option>
    	</el-select>
    </el-form-item>
    <el-form-item label="道具数量" prop="propNum">
      <el-input v-model="dataForm.propNum" placeholder="道具数量"></el-input>
    </el-form-item>
    <!-- <el-form-item label="目标对象" prop="targetObject">
    	<el-radio-group v-model="dataForm.targetObject">
    		<el-radio :label="1">特定用户群</el-radio>
    		<el-radio :label="2">指定会员账号</el-radio>
    	</el-radio-group>
    </el-form-item> -->
   <!-- <div v-if="dataForm.targetObject==1" style="width: 380px;height: 160px;border: 1px solid lightgray;margin-left: 80px;padding: 30px;margin-bottom: 20px;">
    	<el-form-item label="用户层级" prop="hierarchy">
    		<el-select v-model="dataForm.hierarchyId" placeholder="用户层级 " clearable>
    			<el-option
    				v-for="item in optionsHierarchy"
    				:key="item.id"
    				:label="item.name"
    				:value="item.id">
    			</el-option>
    		</el-select>
    	</el-form-item>
    	<el-form-item  label="VIP等级" prop="hierarchy">
    		<el-select v-model="dataForm.vipId" placeholder="VIP等级 " clearable>
    			<el-option
    				v-for="item in optionsVip"
    				:key="item.id"
    				:label="item.name"
    				:value="item.id">
    			</el-option>
    		</el-select>
       </el-form-item>
    </div> -->
    <!-- <el-form-item v-if="dataForm.targetObject==2" label="会员账号" prop="account">
      <el-input type="textarea" :rows="2" v-model="dataForm.account" placeholder="多个会员账号使用英文,相隔"></el-input>
    </el-form-item>
    <el-form-item label="是否发送站内信" prop="sendMessage">
    	<el-radio-group v-model="dataForm.sendMessage">
    		<el-radio :label="false">否</el-radio>
    		<el-radio :label="true">是</el-radio>
    	</el-radio-group>
    </el-form-item>
    <el-form-item v-if="dataForm.sendMessage" label="站内信标题" prop="messageTitle">
    	<el-input v-model="dataForm.messageTitle" placeholder="站内信标题"></el-input>
    </el-form-item>
    <el-form-item v-if="dataForm.sendMessage" label="站内信内容" prop="messageContent">
    	<el-input type="textarea" :rows="2" v-model="dataForm.messageContent" placeholder="站内信内容"></el-input>
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
			var checkNum = (rule, value, callback) => {
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
		optionsHierarchy:[],
		optionsVip:[],
		options: [],
        visible: false,
        dataForm: {
          id: 0,
          orderNo: '',
		  userId: '',
		  roomCard: '',
		  userName: '无',
		  userAccount: '',
          sysUserAccount: '',
		  targetObject: 1,
          sysUserId: '',
          propId: 2,
          propNum: '',
          account: '',
          hierarchyId: '',
          vipId: '',
          designated: false,
          sendMessage:false,
          messageTitle: '',
          messageContent: '',
          effectiveDate: '',
        },
        dataRule: {
          orderNo: [
            { required: true, message: '订单号不能为空', trigger: 'blur' }
          ],
		  sysUserAccount: [
		    { required: true, message: '操作人用户名不能为空', trigger: 'blur' }
		  ],
          sysUserId: [
            { required: true, message: '操作人id不能为空', trigger: 'blur' }
          ],
          propId: [
            { required: true, message: '道具id不能为空', trigger: 'blur' }
          ],
          propNum: [
            { required: true, message: '道具数量不能为空', trigger: 'blur' },
						{ validator: checkNum, trigger: 'blur' }
          ],
          account: [
            { required: true, message: '会员账号不能为空', trigger: 'blur' }
          ],
          hierarchyId: [
            { required: true, message: '层级id不能为空', trigger: 'blur' }
          ],
          vipId: [
            { required: true, message: 'vip_id不能为空', trigger: 'blur' }
          ],
          designated: [
            { required: true, message: '是否指定不能为空', trigger: 'blur' }
          ],
          sendMessage: [
            { required: true, message: '是否发送站内信不能为空', trigger: 'blur' }
          ],
          messageTitle: [
            { required: true, message: '站内信标题不能为空', trigger: 'blur' },
						{ validator: checkTitle, trigger: 'blur' }
          ],
          messageContent: [
            { required: true, message: '站内信内容不能为空', trigger: 'blur' },
						{ validator: checkContent, trigger: 'blur' }
          ],
          effectiveDate: [
            { required: true, message: '站内信有效期限不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
		 init (id) {
			 //道具选择
			 this.$http({
					url: this.$http.adornUrl(`/sysprop/sysprop/getPropNotCoin`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.options = data.propList
					}
			});
// 			this.$http({
// 				url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
// 				method: 'get',
// 				params: this.$http.adornParams({"hierarchyType":0})
// 			}).then(({data}) => {
// 				if (data && data.code === 200) {
// 					this.optionsHierarchy = data.hierarchyList
// 				}
// 			});
// 			this.$http({
// 				url: this.$http.adornUrl(`/uservip/uservip/getVIPGrade`),
// 				method: 'get',
// 				params: this.$http.adornParams()
// 			}).then(({data}) => {
// 				if (data && data.code === 200) {
// 					this.optionsVip = data.userVipEntity
// 				}
// 			});
		  this.dataForm.id = id || 0
		  this.visible = true
		  this.$nextTick(() => {
		  this.$refs['dataForm'].resetFields()
		  this.dataForm.userName = '无';
		  this.dataForm.roomCard = 0;
		  this.dataForm.account="";
		    if (this.dataForm.id) {
		      this.$http({
		        url: this.$http.adornUrl(`/batchrechargeprop/batchrechargeprop/info/${this.dataForm.id}`),
		        method: 'get',
		        params: this.$http.adornParams()
		      }).then(({data}) => {
		        if (data && data.code === 200) {
		          this.dataForm.orderNo = data.batchrechargeprop.orderNo
		          this.dataForm.sysUserAccount = data.batchrechargeprop.sysUserAccount
		          this.dataForm.sysUserId = data.batchrechargeprop.sysUserId
		          this.dataForm.propId = data.batchrechargeprop.propId
		          this.dataForm.propNum = data.batchrechargeprop.propNum
		          this.dataForm.account = data.batchrechargeprop.account
		          this.dataForm.hierarchyId = data.batchrechargeprop.hierarchyId
		          this.dataForm.vipId = data.batchrechargeprop.vipId
		          this.dataForm.designated = data.batchrechargeprop.designated
		          this.dataForm.sendMessage = data.batchrechargeprop.sendMessage
		          this.dataForm.messageTitle = data.batchrechargeprop.messageTitle
		          this.dataForm.messageContent = data.batchrechargeprop.messageContent
		          this.dataForm.effectiveDate = data.batchrechargeprop.effectiveDate
		        }
		      })
		    }
		  })
		},
		onCopy(e) {
			if(this.dataForm.userAccount==''){
				this.$message({
					message: '复制内容为空！',
					type: 'warning',
					duration: 1000
				});
			}else{
				this.$message({
					message: '复制成功！',
					type: 'success',
					duration: 1000
				});
			}
		},
		onError(e) {
			this.$message.error("复制失败")
		},
		getUser(){
			if(this.dataForm.account==''){
				this.$message.error("请先输入会员账号")
				return;
			}
			this.dataForm.roomCard = 0;
		
			this.$http({
				url: this.$http.adornUrl(`batchrechargeprop/batchrechargeprop/getUser`),
				method: 'get',
				params: this.$http.adornParams({"account":this.dataForm.account})
			}).then(({data}) => {
				console.log(data)
				if (data && data.code === 200) {
					this.dataForm.userId = data.data.userId;
					if(data.data.userName){
						this.dataForm.userName = data.data.userName;
					}else{
						this.dataForm.userName="无";
					}
					this.dataForm.roomCard = data.data.roomCard;
					this.dataForm.userAccount=this.dataForm.account;
				}else{
					this.dataForm.userId="";
					this.dataForm.userAccount="";
					this.dataForm.account="";
					this.dataForm.userName="无";
					this.dataForm.roomCard = 0;
					this.$message.error(data.msg)
				}
			});
		},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/batchrechargeprop/batchrechargeprop/${!this.dataForm.id ? 'create' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
							'orderNo': this.dataForm.orderNo ,
							'sysUserAccount': this.dataForm.sysUserAccount ,
							'sysUserId': this.dataForm.sysUserId ,
							'propId': this.dataForm.propId ,
							'propNum': this.dataForm.propNum ,
							'account': this.dataForm.account ,
							'hierarchyId': this.dataForm.hierarchyId ,
							'vipId': this.dataForm.vipId ,
							'designated': true,
							'sendMessage': this.dataForm.sendMessage ,
							'messageTitle': this.dataForm.messageTitle ,
							'messageContent': this.dataForm.messageContent ,
							'effectiveDate': this.dataForm.effectiveDate ,
					
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
