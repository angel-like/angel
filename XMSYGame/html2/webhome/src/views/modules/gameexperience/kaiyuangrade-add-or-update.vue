<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="游戏"  label-width="100px" prop="gameId">
        <el-select  v-model="dataForm.gameId" clearable placeholder="请选择游戏">
          <el-option
            v-for="item in gameList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="场次"  label-width="100px" prop="gradeId">
        <el-select  v-model="dataForm.gradeId" clearable  placeholder="请选择游戏场次">
          <el-option
            v-for="item in gradeList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    <el-form-item label="开元房间号"  label-width="100px" prop="serviceId">
      <el-input v-model="dataForm.serviceId" placeholder="开元房间号"></el-input>
    </el-form-item>
    <el-form-item label="场次名称" label-width="100px" prop="name">
      <el-input v-model="dataForm.name" placeholder="场次名称"></el-input>
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
      //验证正整数
      var validateRate = (rule, value, callback) => {
        var res= /^[0-9]*[1-9][0-9]*$/;
        if (value === '') {
          callback(new Error('不可为空'));
        } else {
          if(!res.test(value)){
            callback(new Error('格式有误'));
          }
          callback();
        }
      };
      return {
        visible: false,
        dataForm: {
          gameId: '',
          serviceId: '',
          name: '',
          gradeId: ''
        },
        gameList:[],
        gradeList:[],
        dataRule: {
          gameId: [
            { required: true, message: '游戏不能为空', trigger: 'blur' }
          ],
          serviceId: [
            { required: true, message: '开元房间号不能为空', trigger: 'blur' },
            {
              validator: validateRate,
              trigger: 'blur'
            }
          ],
          name: [
            { required: true, message: '场次名称不能为空', trigger: 'blur' }
          ],
          gradeId: [
            { required: true, message: '场次等级id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      // 获取下拉数据源
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$http({
            url: this.$http.adornUrl('/user/gemerecord/selectList'),
            method: 'get',
            params: this.$http.adornParams({})
          }).then(({
                     data
                   }) => {
            if (data && data.code === 200) {
              this.gameList = data.gameList
              this.gradeList = data.gradeList
            } else {
              this.gameList = []
              this.gradeList = []
            }
          })
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/gameexperience/kaiyuangrade/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.gameId = data.kaiyuanGrade.gameId
                this.dataForm.serviceId = data.kaiyuanGrade.serviceId
                this.dataForm.name = data.kaiyuanGrade.name
                this.dataForm.gradeId = data.kaiyuanGrade.gradeId
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
              url: this.$http.adornUrl(`/gameexperience/kaiyuangrade/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'gameId': this.dataForm.gameId,
                'serviceId': this.dataForm.serviceId,
                'name': this.dataForm.name,
                'gradeId': this.dataForm.gradeId
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
