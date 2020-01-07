<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="被推荐人id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="被推荐人id"></el-input>
    </el-form-item>
    <el-form-item label="被推荐人账号" prop="userAccount">
      <el-input v-model="dataForm.userAccount" placeholder="被推荐人账号"></el-input>
    </el-form-item>
    <el-form-item label="推荐码" prop="recommendationCode">
      <el-input v-model="dataForm.recommendationCode" placeholder="推荐码"></el-input>
    </el-form-item>
    <el-form-item label="推广人id" prop="promoterId">
      <el-input v-model="dataForm.promoterId" placeholder="推广人id"></el-input>
    </el-form-item>
    <el-form-item label="推广人账号" prop="promoterAccount">
      <el-input v-model="dataForm.promoterAccount" placeholder="推广人账号"></el-input>
    </el-form-item>
    <el-form-item label="推广盈利" prop="promotingProfit">
      <el-input v-model="dataForm.promotingProfit" placeholder="推广盈利"></el-input>
    </el-form-item>
		<el-form-item label="金币" prop="coin">
			<el-input v-model="dataForm.coin" placeholder="金币"></el-input>
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
        dataForm: {
          id: 0,
          userId: '',          userAccount: ''  ,        recommendationCode: ''   ,       promoterId: ''   ,       promoterAccount: ''   ,       promotingProfit: '',coin: ''        },
        dataRule: {
          userId: [
            { required: true, message: '被推荐人id不能为空', trigger: 'blur' }
          ],
          userAccount: [
            { required: true, message: '被推荐人账号不能为空', trigger: 'blur' }
          ],
          recommendationCode: [
            { required: true, message: '推荐码不能为空', trigger: 'blur' }
          ],
          promoterId: [
            { required: true, message: '推广人id不能为空', trigger: 'blur' }
          ],
          promoterAccount: [
            { required: true, message: '推广人账号不能为空', trigger: 'blur' }
          ],
          promotingProfit: [
            { required: true, message: '推广盈利不能为空', trigger: 'blur' }
          ],
					coin: [
						{ required: true, message: '金币不能为空', trigger: 'blur' }
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
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/userrecommendation/userrecommendation/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.userId = data.userRecommendation.userId
                this.dataForm.userAccount = data.userRecommendation.userAccount
                this.dataForm.recommendationCode = data.userRecommendation.recommendationCode
                this.dataForm.promoterId = data.userRecommendation.promoterId
                this.dataForm.promoterAccount = data.userRecommendation.promoterAccount
                this.dataForm.promotingProfit = data.userRecommendation.promotingProfit
								this.dataForm.coin = data.userRecommendation.coin
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
              url: this.$http.adornUrl(`/userrecommendation/userrecommendation/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'userId': this.dataForm.userId ,
          'userAccount': this.dataForm.userAccount ,
          'recommendationCode': this.dataForm.recommendationCode ,
          'promoterId': this.dataForm.promoterId ,
          'promoterAccount': this.dataForm.promoterAccount, 
          'promotingProfit': this.dataForm.promotingProfit ,
					'coin': this.dataForm.coin 
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
