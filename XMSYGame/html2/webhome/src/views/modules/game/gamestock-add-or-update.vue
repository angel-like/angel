<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="150px">
      <el-form-item label="风控对象" prop="gameIds">
        <template>
          <el-select v-model="dataForm.roomId" multiple placeholder="请选择">
            <el-option v-for="item in roomList" :key="item.id" :label="item.name" :value="item.id">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="风控库存值" prop="stock">
        <el-input-number v-model="dataForm.stock" :step="100"  disabled
                         placeholder="风控库存值"></el-input-number>
					 <!-- <span style="height: 36px;line-height: 36px;">{{dataForm.stock}}</span> -->
					 <span style="height: 36px;line-height: 36px;">风控库存标准值:{{dataForm.stockStandard}}</span>
      </el-form-item> 
      <el-form-item label="风控阀门调节值" prop="stockLimit" >
        <el-input-number v-model="dataForm.stockLimit" :step="100" @change="changeStockLimit"  placeholder="风控阀门调节值"></el-input-number>
				<span style="height: 36px;line-height: 36px;">风控阀门标准值:{{dataForm.stockLimitStandard}}</span>
      </el-form-item>
			
			<el-form-item >
					<span style="color: red;height: 36px;line-height: 36px;">玩家胜率:{{dataForm.userRate}}%</span>
					<span style="color: red;height: 36px;line-height: 36px;margin-left: 12%;">玩家标准胜率:{{dataForm.userRateStandard}}%</span>
			</el-form-item>
      <el-form-item label="利润收益比例" prop="taxRate">
        <el-input-number v-model="dataForm.taxRate" :precision="2" :step="0.1" :min="0" :max="5">
        </el-input-number>
				<span style="color: red;">设置值范围0~5%</span>
      </el-form-item>
      <el-form-item label="启用" prop="enable">
        <el-switch v-model="dataForm.enable" active-color="#13ce66" inactive-color="#ff4949" active-text="是"
                   inactive-text="否"></el-switch>
      </el-form-item>
    </el-form>
		<div style="color: red;height: 36px;line-height: 36px;">风控库存值:游戏预存库存,如果大于标准值，平台已经盈利，否则反之。</div>
		<div style="color: red;height: 36px;line-height: 36px;">风控库存阀值:风控库存上限，往上调整，玩家胜率降低；往下调整，玩家胜率升高。</div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      var validateRate = (rule, value, callback) => {
        var res = /^(-?\d+)+(.[0-9]{0,2})?$/;
        if (Number(value) === 0) {
          callback(new Error('不能等于0'));
        } else {
          callback();
        }
      };
      return {
        roomList: [],
        visible: false,
        stock: '',
        dataForm: {
          id: 0,
          roomId: '',
          stock: '',
          stockLimit: '',
          stockStandard: '',
          stockLimitStandard: '',
          userRate: 0,
          userRateStandard: 0,
          enable: 1,
          taxRate: '',
        },
        dataRule: {
          roomId: [
            {required: true, message: '房间id不能为空', trigger: 'blur'}
          ],
          stock: [
            {required: true, message: '实际有效库存不能为空', trigger: 'blur'}
          ],
          stockLimit: [
            {required: true, validator: validateRate, message: '库存上限不能为空', trigger: 'blur'}
          ],
          tax: [
            {required: true, message: '抽税不能为空', trigger: 'blur'}
          ],
          taxRate: [
            {required: true, message: '抽税比例不能为空', trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      changeStock(currentValue, oldValue) {
        this.dataForm.stockLimit = currentValue * 2;
      },
			changeStockLimit(currentValue, oldValue) {
				this.dataForm.userRate = this.toShowNum(this.dataForm.stock / currentValue*100,2);
      },
      init(id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.$http({
            url: this.$http.adornUrl(`/gamestock/gamestock/RoomList`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({
                     data
                   }) => {
            if (data && data.code === 200) {
              this.roomList = data.roomList;
              this.roomList.sort(function (a, b) {
                  return a.id - b.id
                }
              );
              if (this.dataForm.id) {
                this.$http({
                  url: this.$http.adornUrl(`/gamestock/gamestock/info/${this.dataForm.id}`),
                  method: 'get',
                  params: this.$http.adornParams()
                }).then(({data}) => {
                  if (data && data.code === 200) {
                    this.dataForm.roomId = data.gamestock.roomId
                    this.dataForm.stock = this.toShowNum(data.gamestock.stock / 100,2);
                    this.dataForm.stockLimit = this.toShowNum(data.gamestock.stockLimit / 100,2);
                    this.dataForm.taxRate = data.gamestock.taxRate * 100
                    this.dataForm.enable = data.gamestock.enable
                    this.dataForm.stockStandard = this.toShowNum(data.gamestock.stockStandard / 100,2);
                    this.dataForm.stockLimitStandard = this.toShowNum(data.gamestock.stockLimitStandard / 100,2);
                    this.dataForm.userRate = this.toShowNum(data.gamestock.stock / data.gamestock.stockLimit*100,2);
                    this.dataForm.userRateStandard = this.toShowNum(data.gamestock.stockStandard / data.gamestock.stockLimitStandard*100,2);
                    if (data.gamestock.roomId != '') {
                      var roomIds = data.gamestock.roomId.split(',');
                      var roomIdArray = [];
                      for (var i = 0; i < roomIds.length; i++) {
                        roomIdArray.push(Number(roomIds[i]));
                      }
                      this.dataForm.roomId = roomIdArray
                    }
                  }
                })
              }
            }
          })

        })
      },
			toShowNum(num, s) {
					var times = Math.pow(10, s)
					var des = num * times + 0.5
					des = parseInt(des, 10) / times
					return des;
			},
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/gamestock/gamestock/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'roomId': this.dataForm.roomId.join(","),
                'stock': this.dataForm.stock * 100,
                'stockLimit': this.dataForm.stockLimit * 100,
                'enable': this.dataForm.enable,
                'taxRate': this.dataForm.taxRate / 100,
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
