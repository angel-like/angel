<template>
  <el-row :gutter="40" class="panel-group">

 <el-input v-model="inputNumberId"  v-if="false"></el-input>
    <el-col :xs="24" :sm="24" :lg="4" class="card-panel-col" v-for="(list,index) in 24" :key="list.id">
      <div class="card-panel">
        <div class="div-flex">
          <div class="card-panel-icon-wrapper icon-people">
            <div class="card-panel-text">{{index}}点</div>
          </div>
        </div>
        <div class="card-panel-description">
          <el-input :disabled="index <= parseInt(hourActive)" v-model="inputNumber[index]" 
					 placeholder="请输入内容"></el-input>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import CountTo from 'vue-count-to'
import moment from 'moment'
export default {
  components: {
    CountTo
  },
	props:['inputNumber','inputNumberId','hourActive','time'],
  data () {
    return {
      value1: '',
      value2: '',
      activeClass: null,
			id:'',
    }
  },
  created () {
		
  },

  methods: {
		checkVal(rule, value, callback){
			 console.log("val",val);
		},
		checkRate(){
			var dataList = this.inputNumber;
			console.log("dataList",dataList);
			var msg="";
			//（正浮点数 + 0）：^\d+(\.\d+)?$ 
			var reg=/^[0-9]+(.[0-9]{0,2})?$/
			for(var i=0;i<24;i++){
				console.log(i,dataList[i]);
				if(!reg.test(dataList[i])){
					msg+=i+"点：输入数值有误;";
				}
			}
			return msg;
		},
    handleSetLineChartData () {
			var emsg=this.checkRate();
			if(emsg!=''){
				this.$message.error(emsg)
				return;
			}
			 this.$http({
					 url: this.$http.adornUrl(`/exchangeraterules/exchangeraterules/${!this.inputNumberId ? 'save' : 'update'}`),
					 method: 'post',
					 data: this.$http.adornData({
						 'id': this.inputNumberId ,
						 'rateTime': this.time ,
						 'rate':  JSON.stringify(this.inputNumber)
					 })
				 }).then(({data}) => {
					 if (data && data.code === 200) {
						 this.$message({
							 message: '操作成功',
							 type: 'success',
							 duration: 1500,
							 onClose: () => {
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

<style rel="stylesheet/scss" lang="scss" scoped>
.panel-group {
  .card-panel-col{
    margin-bottom: 20px;
    text-align: center;
  }

  .card-panel {
    font-size: 18px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);
    .icon-people {
      color: #40c9c6;
    }
    .icon-message {
      color: #36a3f7;
    }
    .icon-money {
      color: #f4516c;
    }
    .icon-shopping {
      color: #34bfa3
    }
    .div-flex{
      display: flex;
    }
    .card-panel-icon-wrapper {
      // width: 78px;
      // height: 78px;
      margin: auto;
      margin-top: 14px;
      margin-bottom: 5px;
      padding:0 8px;
      transition: all 0.38s ease-out;
      // border-radius: 6px;
      i{
        font-size: 34px;
      }
    }
    .card-panel-icon {
      float: left;
      font-size: 48px;
    }
    .card-panel-description {
      font-weight: bold;
      margin: 7px 26px;
      display: flex;
      justify-content:center;
      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }
      .card-panel-num {
        font-size: 20px;
      }
    }
  }
  .card-panel-active {
    .card-panel-icon-wrapper {
      color: #fff;
    }
    .icon-people {
        background: #40c9c6;
    }
    .icon-message {
      background: #36a3f7;
    }
    .icon-money {
      background: #f4516c;
    }
    .icon-shopping {
      background: #34bfa3
    }
    .icon-people {
        background: #40c9c6;
    }
    .icon-message {
      background: #36a3f7;
    }
    .icon-money {
      background: #f4516c;
    }
    .icon-shopping {
      background: #34bfa3
    }
  }
}
</style>
