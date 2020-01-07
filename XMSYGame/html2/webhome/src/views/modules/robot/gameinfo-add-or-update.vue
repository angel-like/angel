<template>
  <el-dialog
	 width="1024px"
    :close-on-click-modal="false"
    :visible.sync="visible">
		<div style="overflow: hidden;">
			<el-form style="float:left"   v-for="config in configFrom" label-width="100px">
			<el-card class="box-card">
			  <div slot="header" class="clearfix">
			   <div style="text-align: center;"><el-tag>{{config.name}}</el-tag></div>
			  </div>
			  <el-form-item v-if="list.type !== 'hide'&&list.alias=='机器人的数量'" :label="list.alias" prop="coin" v-for="list in config.data" :key="list.id">
			  	<!--<el-input  v-model="list.value"  width="30%" style="font-size:13px"></el-input>-->
					 <el-input-number v-model="list.value"  :min="1" :max="50"></el-input-number>
					<!-- :disabled="list.name != 'num'" -->
			  </el-form-item>
			</el-card>
			</el-form>
		</div>
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
				putUrl:'',
				configFrom:null,
        dataForm: {
          
        },
        dataRule: {
         
        }
      }
    },
    methods: {
      init (gameId) {
        this.visible = true
				this.configFrom=null;
				this.$http({
					url: this.$http.adornUrl('/gameinfo/gameinfo/robotGameConfig'),
					method: 'get',
					params: this.$http.adornParams({
						'gameId':gameId
					})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.configFrom = data.list
					} else {
						this.$message.error(data.msg)
					}
				})
      },
      // 表单提交
      dataFormSubmit () {
            this.$http({
              url: this.$http.adornUrl('/gameinfo/gameinfo/saveConfig'),
              method: 'post',
              data: this.$http.adornData({
						'arry': this.configFrom 
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
    }
  }
</script>
