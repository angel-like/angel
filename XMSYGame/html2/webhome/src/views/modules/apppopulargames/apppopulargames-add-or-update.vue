<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-tooltip class="item" effect="dark" content="游戏名称" placement="top-start">
      <el-form-item label="游戏" >
        <el-select v-model="dataForm.gameId" clearable  placeholder="请选择游戏" >
          <el-option
            v-for="item in gameOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    </el-tooltip>
      <el-tooltip class="item" effect="dark" content="游戏类型" placement="top-start">
        <el-form-item label="类型" >
          <el-select v-model="dataForm.type" clearable  placeholder="请选择类型" >
            <el-option
              v-for="item in typeOptions"
              :key="item.name"
              :label="item.label"
              :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
		<el-form-item label="排序" prop="sort">
			<el-input-number v-model="dataForm.sort" controls-position="right" :min="0" label="排序号"></el-input-number>
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
				gameOptions:[],
        dataForm: {
          id: 0,
          gameId: '',
					sort: '',
          type:''
        },
          typeOptions: [{

              name: 0,
              label: '热门游戏'
          }, {
              name: 1,
              label: '最新游戏'
          }, {
            name: 2,
            label: '即将上线'
          }],
        dataRule: {
          gameId: [
            { required: true, message: '游戏ID不能为空', trigger: 'blur' }
          ],
					 sort: [
					  { required: true, message: '排序不能为空', trigger: 'blur' },
					]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
				//为游戏下拉获取数据
				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelect`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({data}) => {
					if (data && data.code === 200) {
						console.log(data.data);
						this.gameOptions = data.data
					}
				});
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/apppopulargames/apppopulargames/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.gameId = data.apppopulargames.gameId
								this.dataForm.sort=data.apppopulargames.sort
                this.dataForm.type=data.apppopulargames.type
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
              url: this.$http.adornUrl(`/apppopulargames/apppopulargames/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'gameId': this.dataForm.gameId ,
								'sort':this.dataForm.sort,
                'type':this.dataForm.type

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
