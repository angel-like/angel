<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-tooltip class="item" effect="dark" content="投放广告的渠道名称" placement="top-start">
				<el-form-item label="渠道名称" prop="channelName">
				  <el-input v-model="dataForm.channelName" placeholder="渠道名称"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="广告渠道代码" placement="top-start">
				<el-form-item label="渠道代码" prop="channelCode">
				  <el-input v-model="dataForm.channelCode" placeholder="渠道代码" @change="goFind()"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="广告渠道的地址" placement="top-start">
				<el-form-item label="渠道地址" prop="channelAddress">
				  <el-input v-model="dataForm.channelAddress" placeholder="渠道地址"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="备注留言方便了解" placement="top-start">
				<el-form-item label="备注" prop="remake">
				  <el-input v-model="dataForm.remake" placeholder="备注"></el-input>
				</el-form-item>
			</el-tooltip>
    </el-form>
		<div><font color="red">打包时间很长，请耐心等待2分钟左右</font></div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
				loadPicture:false,
        visible: false,
        dataForm: {
          id: 0,
          channelName: '',
          channelCode: '',
					channelAddress:'',
          remake: '',
        },
        dataRule: {
					/*
          channelName: [
            { required: true, message: '渠道名称不能为空', trigger: 'blur' }
          ],
          channelCode: [
            { required: true, message: '渠道代码不能为空', trigger: 'blur' }
          ]*/
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
				this.loadPicture=false
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/adchannelconfig/adchannelconfig/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                this.dataForm.channelName = data.adchannelconfig.channelName
                this.dataForm.channelCode = data.adchannelconfig.channelCode
								this.dataForm.channelAddress=data.adchannelconfig.channelAddress
                this.dataForm.remake = data.adchannelconfig.remake
              }
            })
          }else{
						this.goFind();
					}
        })
      },
			goFind(){
				//console.log(!this.dataForm.id)
				if (!this.dataForm.id){
					this.loadPicture=true
					 this.$http({
					  url: this.$http.adornUrl(`/adchannelconfig/adchannelconfig/goFindChannelCode`),
					  method: 'get',
					  params: this.$http.adornParams({
							'channelCode':this.dataForm.channelCode
						})
					}).then(({data}) => {
					  if (data && data.code === 200) {
							if(data.flag){
								this.loadPicture=true
								this.$message({
									message: '渠道码已存在，请换一个',
									type: 'warning',
									duration: 1500,
								})
							}else{
								this.loadPicture=false
							}
					  }
					})
				}
			},
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
					this.loadPicture=true;//确定按钮转圈
					//定时器，超过30秒 直接把this.visible = 设为false  关闭弹窗
					var timeoutID;
					timeoutID=setTimeout(()=>{
						this.visible = false//关闭弹窗
						this.$emit('refreshSetInterval')//触发主窗口refreshSetInterval事件
					},30000);
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/adchannelconfig/adchannelconfig/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
          'channelName': this.dataForm.channelName ,

          'channelCode': this.dataForm.channelCode ,
					'channelAddress':this.dataForm.channelAddress,
          'remake': this.dataForm.remake ,

              })
            }).then(({data}) => {
              if (data && data.code === 200) {
								if(data.appdabao!=null){
									this.$message({
										message: data.appdabao,
										type: 'warning',
										duration: 1500,
									})
								}else{
									this.$message({
									  message: '操作成功',
									  type: 'success',
									  duration: 1500,
									  onClose: () => {
									    this.visible = false
											this.loadPicture=false;
									    this.$emit('refreshDataList')
									  }
									})
								}
              } else {
								this.loadPicture=false;
                this.$message.error(data.msg)
              }
							this.loadPicture=false;
            })
          }
        })
      }
    }
  }
</script>
