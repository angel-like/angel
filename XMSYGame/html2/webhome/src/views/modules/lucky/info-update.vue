<template>
  <el-dialog title="修改" :close-on-click-modal="false"  :visible.sync="visible">
    <div style="overflow: hidden;height: 300px">

      <el-card class="box-card">
        <div>
          <div style="text-align: center;width:13%;float:left;height:40px;">
            <el-tag>属性</el-tag>
          </div>
          <div v-for="(name,i) in nameList" style="text-align: center;width:11%;float:left;height:40px;">
            <el-tag>{{nameList[i]}}</el-tag>
          </div>
        </div>  <br>
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="120px">
          <!--1.属性-->
          <div style="width:12%;float:left; background:white;">
            <!--1.1游戏信息的属性-->
            <div style="text-align: center;height:60px;">奖品类型</div>
            <div style="text-align: center;height:60px;">道具数量</div>
            <div style="text-align: center;height:60px;">是否大奖</div>
            <!--<div style="text-align: center;height:60px;">概率</div>-->
          </div>
          <!--1.配置信息-->
          <div v-for="luckyConfig in configList"
               style="width:10%;float:left; background:white;height:700px;margin-left:13px">
            <div style="text-align: center;height:60px;">
              <el-select v-model="luckyConfig.propId" placeholder="奖品类型" clearable>
                <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </div>
            <div style="text-align: center;height:60px;">
              <el-input v-model="luckyConfig.propNum" type="number" placeholder="数量"></el-input>
            </div>
            <div style="text-align: center;height:60px;">
              <!-- <template slot-scope="scope">-->
              <el-switch v-model="luckyConfig.grand" active-color="#13ce66" inactive-color="#ff4949"
              >
              </el-switch>  <!-- </template>-->

              <!-- <el-radio-group v-model="luckyConfig.grand">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>-->
            </div>
            <!--<div style="text-align: center;height:60px;">-->
            <!--<el-input v-model="luckyConfig.chance" placeholder="概率" ></el-input>-->
            <!--</div>-->
          </div>
          <div style="text-align: center;background:white;height:780px;"></div>
          <!--上面属于div浮动不占用空间，所以这边把上面浮动覆盖掉-->
        </el-form>
      </el-card>


    </div>

    <span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()" :loading="loadPicture">确定</el-button>
		</span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      //验证正整数
      var validateRate = (rule, value, callback) => {
        var res = /^[0-9]*$/;
        if (value === '') {
          callback(new Error('不可为空'));
        } else {
          if (!res.test(value)) {
            callback(new Error('格式有误'));
          } else {
            if (rule.field == "rate" && value >= 100) {
              callback(new Error('格式有误'));
            } else {
              callback();
            }
          }
        }
      };
      return {
        loadPicture: false,
        visible: false,
        configList: [],
        nameList: [],
        options: [{
          value: 1,
          label: '金币'
        }, {
          value: 2,
          label: '房卡'
        }],
        dataForm: {
          id :'',
          luckyId: '',
          propId: '',
          propNum: '',
          propName: '',
          name: ''
        },
        dataRule: {}
      }
    },
    methods: {
      init(id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/luckyconfig/luckyconfig/getconfig/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
                console.log(data.list)
                this.configList = data.list
                this.nameList = data.nameList
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          console.log(this.configList)
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/luckyconfig/luckyconfig/updateConfig`),
              method: 'post',
              data: this.$http.adornData({
                'configs': this.configList
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
