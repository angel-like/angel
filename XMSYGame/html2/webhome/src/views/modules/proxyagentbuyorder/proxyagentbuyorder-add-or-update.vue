<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="代理账户" prop="proxyAccount">
      <el-input v-model="dataForm.proxyAccount" placeholder="代理账户"></el-input>
      <el-button @click="getProxyAccount()">查找</el-button>
    </el-form-item>
    <el-form-item label="代理名称" >
    	<div>{{dataForm.proxyName}}</div>
    </el-form-item>
    <el-form-item label="用户id" >
    	<div>{{dataForm.userId}}</div>
    </el-form-item>
      <!-- <el-form-item label="代理名称" prop="proxyName">
      <el-input v-model="dataForm.proxyName" placeholder="代理名称"></el-input>
    </el-form-item> -->
    <el-form-item label="购买金币" prop="buyCoins">
      <!-- <el-input v-model="dataForm.buyCoins" placeholder="购买金币"></el-input> -->
      <el-input type= "number"  style="width: 150px" v-model="dataForm.buyCoins"  placeholder="购买金币"></el-input>
    </el-form-item>
    <el-form-item label="折扣" prop="discount">
      <el-input v-model="dataForm.discount" placeholder="折扣" @change="getDisGoldCoins()"></el-input>
    </el-form-item>
    <el-form-item label="优惠金币" prop="disGoldCoins">
      <!-- <el-input v-model="dataForm.disGoldCoins" placeholder="优惠金币"></el-input> -->
      <el-input type= "number"  style="width: 150px" v-model="dataForm.disGoldCoins" readonly placeholder="优惠金币"></el-input>
    </el-form-item>
    <el-form-item label="总金币" prop="getTatolCoins">
      <!-- <el-input v-model="dataForm.getTatolCoins" placeholder="总金币" readonly></el-input> -->
      <el-input type= "number"  style="width: 150px" v-model="dataForm.getTatolCoins" readonly placeholder="总金币"></el-input>
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
      var checkDiscount = (rule, value, callback) => {
      	if (value) {
      		//var retgex=/^(1|1\.[0]*|0?\.(?!0+$)[\d]+)$/im;//限制0到1之间
      		//var retgex = /^(\d(\.\d{1,2})?|10)$/; //限制0到10之间.最多4位小数
      		// var retgex = /^0\.[1-9]{0,2}$/;请输入最多包含2位小数且0-1之间的数
          var retgex = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^\d\.\d{1,2}$)/;
      		if (!retgex.test(value)) {
      			callback(new Error('请输入最多包含两位小数的正数'));
      		} else {
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
          proxyAccount: '',
          proxyName: '无',
          buyCoins: '',
          discount: '',
          disGoldCoins: '',
          getTatolCoins: '',
          userId: '',
        },
        dataRule: {
          proxyAccount: [
            { required: true, message: '代理账户不能为空', trigger: 'blur' }
          ],
          proxyName: [
            { required: true, message: '代理名称不能为空', trigger: 'blur' }
          ],
          buyCoins: [
            { required: true, message: '购买金币不能为空', trigger: 'blur' }
          ],
          discount: [
            { required: true, message: '折扣不能为空', trigger: 'blur' },
            {
							validator: checkDiscount,
							trigger: 'blur'
						}

          ],
          disGoldCoins: [
            { required: true, message: '优惠金币不能为空', trigger: 'blur' }
          ],
          getTatolCoins: [
            { required: true, message: '获得的总金币不能为空', trigger: 'blur' }
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
          this.dataForm.proxyName = '无';
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/proxyagentbuyorder/proxyagentbuyorder/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.proxyAccount = data.proxyagentbuyorder.proxyAccount
                this.dataForm.proxyName = data.proxyagentbuyorder.proxyName
                this.dataForm.buyCoins = data.proxyagentbuyorder.buyCoins/100
                this.dataForm.discount = data.proxyagentbuyorder.discount
                this.dataForm.disGoldCoins = data.proxyagentbuyorder.disGoldCoins/100
                this.dataForm.getTatolCoins = data.proxyagentbuyorder.getTatolCoins/100
              }
            })
          }
        })
      },

      //查找代理账户
      getProxyAccount(){
      	if(this.dataForm.proxyAccount==''){
      		this.$message.error("请先输入代理账户")
      		return;
      	}
      	this.$http({
      		url: this.$http.adornUrl(`proxyagentbuyorder/proxyagentbuyorder/getProxyName`),
      		method: 'get',
      		params: this.$http.adornParams({"account":this.dataForm.proxyAccount})
      	}).then(({data}) => {
      		if (data && data.code === 200) {
      		if(data.data.proxyName){
      				this.dataForm.proxyName = data.data.proxyName;
              this.dataForm.userId = data.data.userId;
      			}else{
      				this.dataForm.proxyName="无";
      			}
      		}else{
            this.$message.error(data.msg)
          }
      	});
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/proxyagentbuyorder/proxyagentbuyorder/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'proxyAccount': this.dataForm.proxyAccount ,

          'proxyName': this.dataForm.proxyName ,
          'userId': this.dataForm.userId ,

          'buyCoins': this.dataForm.buyCoins*100 ,

          'discount': this.dataForm.discount ,

          'disGoldCoins': this.dataForm.disGoldCoins*100 ,

          'getTatolCoins': this.dataForm.getTatolCoins*100 ,

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
      },
      //折扣数值改变时计算优惠金币
      getDisGoldCoins(){
      	if(this.dataForm.proxyAccount=='' || this.dataForm.buyCoins==''|| this.dataForm.discount==''){
      		return;
      	}
      	this.dataForm.disGoldCoins=this.dataForm.buyCoins*this.dataForm.discount;
        this.dataForm.getTatolCoins=Number(this.dataForm.buyCoins)+Number(this.dataForm.disGoldCoins);

      },
    }
  }
</script>
