<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="100px">
    <el-tooltip class="item" effect="dark" content="新增加一种排行榜单,例如用户在线时间最长" placement="top-start">
				<el-form-item label="排行榜名称" prop="rankListName">
				  <el-input v-model="dataForm.rankListName" placeholder="排行榜名称"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="派发奖池奖金时,所选中榜单前多少名进行抽奖" placement="top-start">
				<el-form-item label="参与抽奖最高名次" prop="topNum">
					<el-input-number v-model="dataForm.topNum" controls-position="right" :min="1" :max="1000" label="榜单前几名参与抽奖"></el-input-number>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="如果禁用了,用户看不到,相当于没有设置" placement="top-start">
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
      return {
        visible: false,
        dataForm: {
          id: 0,
          rankListName: '',
          enable: true,
					topNum:20
        },
        dataRule: {
          rankListName: [
            { required: true, message: '排行榜名称不能为空', trigger: 'blur' }
          ],
          enable: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
					topNum:[
            { required: true, message: '参与抽奖最高名次不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/rankinglist/rankinglist/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.rankListName = data.rankinglist.rankListName
                this.dataForm.enable = data.rankinglist.enable
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
              url: this.$http.adornUrl(`/rankinglist/rankinglist/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
								'rankListName': this.dataForm.rankListName ,
								'enable': this.dataForm.enable ,
								'topNum': this.dataForm.topNum
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
