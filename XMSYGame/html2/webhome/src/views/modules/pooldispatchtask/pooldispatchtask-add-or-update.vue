<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
    <el-tooltip class="item" effect="dark" content="选择一款游戏抽取的金额" placement="top-start">
				<el-form-item label="奖池" prop="poolGameId">
				 <el-select  v-model="dataForm.poolGameId" @change="poolSelectChange()" placeholder="请选择游戏">
				 	<el-option
				 		v-for="item in poolList"
				 		:key="item.id"
				 		:label="item.name"
				 		:value="item.id">
				 	</el-option>
				 </el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="这款游戏抽的总额" placement="top-start">
				<el-form-item label="奖金池总额" prop="goldPoolCoins">
					<el-input v-model="dataForm.goldPoolCoins" readonly="readonly"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="要派发的原因" placement="top-start">
				<el-form-item label="标题" prop="title">
					<el-input v-model="dataForm.title" placeholder="标题"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="派发奖励的金额" placement="top-start">
				<el-form-item label="派发金额" prop="amount">
					<el-input type="number" v-model="dataForm.amount" placeholder="派发金额"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="可以充值,或者分享等排名派奖,按前20名抽取" placement="top-start">
				<el-form-item label="排行榜单" prop="rankingListId">
					<el-select  v-model="dataForm.rankingListId" placeholder="请选择排行榜单">
					<el-option
						v-for="item in ranklist"
						:key="item.id"
						:label="item.rankListName"
						:value="item.id">
					</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="按一天排行还是一周排行" placement="top-start">
				<el-form-item label="类型" size="mini" prop="type">
					<el-radio-group v-model="dataForm.type">
						<el-radio :label="1">日排行</el-radio>
						<el-radio :label="2">周排行</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-tooltip>
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
			var checkAmount = (rule, value, callback) => {
					if (Number(value)<=0) {
						callback(new Error('派发金额必须大于零'));
					} else if (Number(value)>Number(this.dataForm.goldPoolCoins)) {
						callback(new Error('派发金额必须小于奖池总金额'));
					}else{
						callback();
					}

			};
      return {
        visible: false,
				poolList:[],
				ranklist:[],
        dataForm: {
          id: 0,
          title: '',
          amount: 0,
          rankingListId: '',
          poolGameId: '',
          status: 0,
					goldPoolCoins:0,
					type: 1
        },
        dataRule: {
					poolGameId: [
						{ required: true, message: '请选择奖池', trigger: 'blur' }
					],
					rankingListId: [
						{ required: true, message: '请选择榜单', trigger: 'blur' }
					],
          title: [
            { required: true, message: '标题不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '派发金额不能为空', trigger: 'blur' },
						{ validator: checkAmount, trigger: 'blur' }
          ],
          rankingListId: [
            { required: true, message: '排行榜单id不能为空', trigger: 'blur' }
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
					this.getSelectList();
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/pooldispatchtask/pooldispatchtask/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.title = data.pooldispatchtask.title
                this.dataForm.amount = data.pooldispatchtask.amount
                this.dataForm.poolGameId = data.pooldispatchtask.poolGameId
                this.dataForm.rankingListId = data.pooldispatchtask.rankingListId
                this.dataForm.status = data.pooldispatchtask.status
								this.poolSelectChange();
              }
            })
          }
        })
      },
			getSelectList(){
				this.$http({
					url: this.$http.adornUrl(`/pooldispatchtask/pooldispatchtask/getRankingList`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.ranklist = data.ranklist
					}
				})
				this.$http({
					url: this.$http.adornUrl(`/pooldispatchtask/pooldispatchtask/getPool`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.poolList = data.poolList
					}
				})
			},
			poolSelectChange(){
				this.$http({
					url: this.$http.adornUrl(`/pooldispatchtask/pooldispatchtask/pool/${this.dataForm.poolGameId}`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.dataForm.goldPoolCoins = data.pool
					}
				})
			},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/pooldispatchtask/pooldispatchtask/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'title': this.dataForm.title ,
								'amount': this.dataForm.amount ,
								'poolGameId': this.dataForm.poolGameId ,
								'rankingListId': this.dataForm.rankingListId ,
								'status': this.dataForm.status ,

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
