<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

    <el-tooltip class="item" effect="dark" content="选择派奖任务的类型" placement="top-start">
				<el-form-item label="派奖任务" prop="taskId">
					<el-select  v-model="dataForm.taskId"  placeholder="请选择派奖任务">
					<el-option
						v-for="item in taskList"
						:key="item.id"
						:label="item.title"
						:value="item.id">
					</el-option>
					</el-select>
				</el-form-item>
        </el-tooltip>
      <el-tooltip class="item" effect="dark" content="给这个奖项起一个名称" placement="top-start">
      		<el-form-item label="奖项名称" prop="title">
      		  <el-input v-model="dataForm.title" placeholder="奖项名称"></el-input>
      </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="设置能够获得这个奖项的人数" placement="top-start">
          <el-form-item label="人数" prop="num">
            <el-input type="number" v-model="dataForm.num" placeholder="人数"></el-input>
      </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="派奖总额*比例=派奖的值,用派奖的值在分给获奖的人数" placement="top-start">
          <el-form-item label="比例" prop="rate">
            <el-input type="number"  v-model="dataForm.rate" placeholder="比例值范围0~1"></el-input>
      </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="派发奖项的人的id" placement="top-start">
          <el-form-item label="指定派奖人id" prop="userids">
            <el-input v-model="dataForm.userids" placeholder="指定派奖人id"></el-input>
          </el-form-item>
      </el-tooltip>
   <!-- <el-form-item label="层级id集合" prop="hierarchyIds">
      <el-input v-model="dataForm.hierarchyIds" placeholder="层级id集合"></el-input>
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
					if (Number(value)<=0) {
						callback(new Error('人数必须大于零'));
					} else{
						callback();
					}

			};
			var checkRate = (rule, value, callback) => {
					if (Number(value)<=0) {
						callback(new Error('人数必须大于零'));
					}else if (Number(value)>=1) {
						callback(new Error('人数必须小于1'));
					}  else{
						callback();
					}

			};
      return {
        visible: false,
        taskList: [],
        dataForm: {
          id: 0,
          title: '',
          num: 0,
          rate: 0.00,
          taskId: '',
          userids: '',
          hierarchyIds: ''
        },
        dataRule: {
          title: [
            { required: true, message: '奖项名称不能为空', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '人数不能为空', trigger: 'blur' },
						{ validator: checkNum, trigger: 'blur' }
          ],
          rate: [
            { required: true, message: '比例不能为空', trigger: 'blur' },
						{ validator: checkRate, trigger: 'blur' }
          ],
          taskId: [
            { required: true, message: '派奖任务不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/pooldispatchtaskdetail/pooldispatchtaskdetail/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.title = data.pooldispatchtaskdetail.title
                this.dataForm.num = data.pooldispatchtaskdetail.num
                this.dataForm.rate = data.pooldispatchtaskdetail.rate
                this.dataForm.taskId = data.pooldispatchtaskdetail.taskId
                this.dataForm.userids = data.pooldispatchtaskdetail.userids
                this.dataForm.hierarchyIds = data.pooldispatchtaskdetail.hierarchyIds
              }
            })
          }
        })
      },
			getSelectList(){
				this.$http({
					url: this.$http.adornUrl(`/pooldispatchtaskdetail/pooldispatchtaskdetail/taskList/0`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.taskList = data.taskList
					}
				})
// 				this.$http({
// 					url: this.$http.adornUrl(`/pooldispatchtaskdetail/pooldispatchtaskdetail/getHierarchy`),
// 					method: 'get',
// 					params: this.$http.adornParams()
// 				}).then(({data}) => {
// 					if (data && data.code === 200) {
// 						this.taskList = data.hierarchyList
// 						this.taskList = data.ids
// 					}
// 				})
			},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/pooldispatchtaskdetail/pooldispatchtaskdetail/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'title': this.dataForm.title ,
								'num': this.dataForm.num ,
								'rate': this.dataForm.rate ,
								'taskId': this.dataForm.taskId ,
								'userids': this.dataForm.userids ,
								'hierarchyIds': this.dataForm.hierarchyIds

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
