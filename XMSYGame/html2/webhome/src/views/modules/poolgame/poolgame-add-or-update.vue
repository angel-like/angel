<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
    <el-tooltip class="item" effect="dark" content="添加一款要派发奖励的游戏" placement="top-start">
				<el-form-item label="游戏名称" prop="gameId" >
					<el-select  v-model="dataForm.gameId" placeholder="请选择游戏">
						<el-option
							v-for="item in gameList"
							:key="item.id"
							:label="item.name"
							:value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="根据用户下注比例抽取金币,放入到奖池" placement="top-start">
      		<el-form-item label="奖金池金额" prop="pool" >
      		  <el-input type="number" v-model="dataForm.pool" readonly placeholder="奖金池金额"></el-input>
      		</el-form-item>
      	</el-tooltip>
        <el-tooltip class="item" effect="dark" content="抽取这款游戏的下注比例,可以自己随便设置" placement="top-start">
        		<el-form-item label="下注比例" prop="betRate">
        			<el-input type="number" max="1" min="0" v-model="dataForm.betRate" placeholder="下注比例"></el-input>
        		</el-form-item>
        	</el-tooltip>
          <el-tooltip class="item" effect="dark" content="是否启用" placement="top-start">
          		<el-form-item label="状态" prop="enable">
          		 <el-radio-group v-model="dataForm.enable">
          		 <el-radio :label="true">启用</el-radio>
          		 <el-radio :label="false">禁用</el-radio>
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
			var checkBetRate = (rule, value, callback) => {
					if (Number(value)<0 || Number(value)>1) {
						callback(new Error('下注比例必须大于零小于等于一'));
					} else{
						callback();
					}

			};
      return {
        visible: false,
				gameList:[],
        dataForm: {
          id: 0,
          pool: 0.00,
          enable: true,
          gameId: '',
          betRate: '',
        },
        dataRule: {
          gameId: [
            { required: true, message: '游戏不能为空', trigger: 'blur' }
          ],
          betRate: [
            { required: true, message: '下注比例不能为空', trigger: 'blur' },
						{ validator: checkBetRate, trigger: 'blur' }
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
						url: this.$http.adornUrl(`/poolgame/poolgame/gameList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.gameList = data.gameList
						}
					})
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/poolgame/poolgame/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.pool = data.poolgame.pool
                this.dataForm.enable = data.poolgame.enable
                this.dataForm.gameId = data.poolgame.gameId
                this.dataForm.betRate = data.poolgame.betRate
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/poolgame/poolgame/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'pool': this.dataForm.pool ,
								'enable': this.dataForm.enable ,
								'gameId': this.dataForm.gameId ,
								'betRate': this.dataForm.betRate
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
