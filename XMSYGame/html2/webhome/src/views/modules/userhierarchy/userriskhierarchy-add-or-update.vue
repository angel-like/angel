<template>
  <el-dialog
    :title="!dataForm.id ? '新增风控层级' : '修改风控层级'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">

		<el-tooltip class="item" effect="dark" content="会员的层级名称" placement="top-start">
				<el-form-item label="层级名称" prop="name">
				  <el-input v-model="dataForm.name" placeholder="层级名称" clearable></el-input>
				</el-form-item>
			</el-tooltip>

		<el-tooltip class="item" effect="dark" content="游戏的胜率(正数胜率高,负数胜率低)" placement="top-start">
				<el-form-item label="游戏胜率" prop="gameRate">
					<el-input style="width: 150px" v-model="dataForm.gameRate"  type="text" placeholder="请输入正整数,零为不生效" clearable>
							<template slot="append">%</template>
					</el-input>
				</el-form-item>
			</el-tooltip>


	<!-- 	<el-form-item label="权限"  >
					<el-tree
					:data="data2"
					show-checkbox
					node-key="gameId"
					ref="tree"
					:default-checked-keys="dataForm.gameIds"
					:props="defaultProps">
				</el-tree>
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
			//验证正整数
			var validateRate = (rule, value, callback) => {
				// var res= /^([1-9]+\\d*)|[0]/;
				var res= /^\d+$/;
					if(!res.test(value)){
						callback(new Error('格式有误'));
					}else{
						callback();
					}
				// }
			};
			var validateGameRate = (rule, value, callback) => {
				// var res= /^([1-9]+\\d*)|[0]/;
				var res= /^(-?\d+)(\.\d+)?$/;
					if(!res.test(value)){
						callback(new Error('格式有误'));
					}else if(value>100 || value<-100 ){
						callback(new Error('输入的值超出范围，请输入-100~100的值'));
					}else{
						callback();
					}
				// }
			};
			//验证---正数，小数最多两位
			var validateMultiple = (rule, value, callback) => {
				var res= /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
					if(!res.test(value)){
						callback(new Error('格式有误'));
					}else{
						callback();
					}
				// }
			};
      return {
        visible: false,
				data2: [],
				defaultProps: {
          children: 'gameList',
          label: 'name'
        },
				rate:100,
				gameProp:1,
        dataForm: {
          id: 0,
          name: '',
					rechargeMultiple:0,
					discountMultiple:0,
					administrativeRate:0,
					relaxationRate:0,
					betRate:0,
					gameRate:'',
					vipEnable:false,
          type: false,
          hierarchyType: 1,
					// gameIds:[]
        },
        dataRule: {
          name: [
            { required: true, message: '层级名称不能为空', trigger: 'blur' }
          ],
					gameRate: [
						{ required: true, message: '游戏胜率不能为空', trigger: 'blur' },
						{ validator: validateGameRate, trigger: 'blur' }
					]
        }
      }
    },

    methods: {
      init (id) {
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameMenu`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						// this.data2 = data.data
						let that =  this
						setTimeout(()=>{
							that.data2 = data.data
						},100)
					}
				});
				// this.dataForm.gameIds=[];
        this.dataForm.id = id || 0;
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/userhierarchy/userhierarchy/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.name = data.userHierarchy.name
                this.dataForm.type = data.userHierarchy.type
								this.dataForm.rechargeMultiple = data.userHierarchy.rechargeMultiple
								this.dataForm.discountMultiple = data.userHierarchy.discountMultiple
								this.dataForm.administrativeRate = data.userHierarchy.administrativeRate*this.rate
								this.dataForm.relaxationRate = data.userHierarchy.relaxationRate*this.rate
								this.dataForm.betRate = data.userHierarchy.betRate*this.rate
								this.dataForm.gameRate=data.userHierarchy.gameRate*this.rate
								this.dataForm.vipEnable = data.userHierarchy.vipEnable
								// this.dataForm.gameIds=data.userHierarchy.gameIds
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
// 				var array = this.$refs.tree.getCheckedKeys();
// 				for(var i = 0 ;i<array.length;i++){
//              if(array[i] == "" || typeof(array[i]) == "undefined") {
//                       array.splice(i,1);
//                       i= i-1;
//              }
// 				}
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/userhierarchy/userhierarchy/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'name': this.dataForm.name ,
								'rechargeMultiple': this.dataForm.rechargeMultiple ,
								'discountMultiple': this.dataForm.discountMultiple ,
								'administrativeRate': this.dataForm.administrativeRate/this.rate ,
								'relaxationRate': this.dataForm.relaxationRate/this.rate ,
								'betRate': this.dataForm.betRate/this.rate ,
								'gameRate': this.dataForm.gameRate/this.rate ,
								'type': this.dataForm.type ,
								'hierarchyType': this.dataForm.hierarchyType ,
								'vipEnable': this.dataForm.vipEnable ,
								// 'gameIds': array
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
