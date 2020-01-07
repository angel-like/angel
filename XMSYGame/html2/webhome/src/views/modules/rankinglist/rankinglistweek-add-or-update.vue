<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="用户的会员账号" placement="top-start">
				<el-form-item label="用户账号" prop="userAccount">
				  <el-input v-model="dataForm.userAccount" placeholder="用户账号"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="想统计哪一周的,可以具体选择" placement="top-start">
				<el-form-item label="统计周" prop="firstOfWeek" >
						<el-date-picker
							v-model="dataForm.firstOfWeek"
							type="week"
							format="yyyyWW"
							placeholder="选择周">
							</el-date-picker>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="选择什么类型的排行榜单,例如充值榜单" placement="top-start">
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
      <el-tooltip class="item" effect="dark" content="在排行榜单上的累计值" placement="top-start">
				<el-form-item label="排行值" prop="amount">
				  <el-input type="number" v-model="dataForm.amount" placeholder="排行值"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="用户的排名" placement="top-start">
				<el-form-item label="名次" prop="position">
						 <div>{{dataForm.position}}</div>
					<!-- <el-input-number v-model="dataForm.position" controls-position="right" :min="1" :max="1000" label="名次"></el-input-number> -->
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
					if (Number(value)<0 ) {
						callback(new Error('排行值要大于零'));
					} else{
						callback();
					}

			};
      return {
        visible: false,
				ranklist:[],
        dataForm: {
          id: 0,
          userId: '',
          userAccount: '',
          rankingListId: '',
          amount: '',
          position: 0,
          firstOfWeek: '',
        },
        dataRule: {
          userAccount: [
            { required: true, message: '用户账号不能为空', trigger: 'blur' }
          ],
          rankingListId: [
            { required: true, message: '排行榜id不能为空', trigger: 'blur' }
          ],
          amount: [
            { required: true, message: '排行值不能为空', trigger: 'blur' },
						{ validator: checkBetRate, trigger: 'blur' }
          ],
          firstOfWeek: [
            { required: true, message: '统计周不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
			weekOfYearhandle(){
				console.log(this.dataForm.weekOfYear);
			},
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.getSelectList();
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/rankinglistweek/rankinglistweek/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
								console.log(data.rankinglistweek);
                this.dataForm.userId = data.rankinglistweek.userId
                this.dataForm.userAccount = data.rankinglistweek.userAccount
                this.dataForm.rankingListId = data.rankinglistweek.rankingListId
                this.dataForm.amount = data.rankinglistweek.amount
                this.dataForm.position = data.rankinglistweek.position
                this.dataForm.firstOfWeek = new Date(data.rankinglistweek.firstOfWeek)
              }
            })
          }
        })
      },
			getSelectList(){
				this.$http({
					url: this.$http.adornUrl(`/rankinglistweek/rankinglistweek/getRankingList`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.ranklist = data.ranklist
					}
				})
			},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/rankinglistweek/rankinglistweek/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'userAccount': this.dataForm.userAccount ,
								'rankingListId': this.dataForm.rankingListId ,
								'amount': this.dataForm.amount ,
								'position': this.dataForm.position ,
								'firstOfWeek': this.dataForm.firstOfWeek
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
