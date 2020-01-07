<template>
  <el-dialog
     title="修改"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
		<el-form-item label="游戏id" prop="gameId">
		<el-input v-model="dataForm.gameId" placeholder="游戏id" ></el-input>
	</el-form-item>
    <el-form-item label="游戏名字" prop="gameName">
      <el-input v-model="dataForm.gameName" placeholder="游戏名字" ></el-input>
    </el-form-item>
	<el-form-item label="游戏房间" prop="roomId">
		<template>
			<el-select v-model="dataForm.roomId"  placeholder="请选择">
				<el-option v-for="item in roomList" :key="item.id" :label="item.name" :value="item.id">
				</el-option>
			</el-select>
		</template>
	</el-form-item>
	<el-form-item label="游戏场次" prop="gradeId">
		<template>
			<el-select v-model="dataForm.gradeId"  placeholder="请选择">
				<el-option v-for="item in gradeList" :key="item.id" :label="item.name" :value="item.id">
				</el-option>
			</el-select>
		</template>
	</el-form-item>
	<el-form-item label="启用" prop="enable">
		<el-switch v-model="dataForm.enable" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否"></el-switch>
	</el-form-item>
	<el-form-item label="显示" prop="display">
		<el-switch v-model="dataForm.display" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否"></el-switch>
	</el-form-item>
	<el-form-item label="维护中" prop="maintenance">
		<el-switch v-model="dataForm.maintenance" active-color="#13ce66" inactive-color="#ff4949" active-text="否" inactive-text="是"></el-switch>
	</el-form-item>
    </el-form-item>
    <el-form-item label="显示的scene页面" prop="sence">
      <el-input v-model="dataForm.sence" placeholder="显示的scene页面"></el-input>
    </el-form-item>
    <el-form-item label="游戏低分" prop="bscore">
      <el-input v-model="dataForm.bscore" placeholder="游戏低分"></el-input>
    </el-form-item>
    <el-form-item label="入场限制" prop="restrict">
      <el-input v-model="dataForm.restrict" placeholder="入场限制"></el-input>
    </el-form-item>
		<el-form-item label="限高" prop="limitHigh">
			<el-input v-model="dataForm.limitHigh" placeholder="限高"></el-input>
		</el-form-item>
		<el-form-item label="限踢" prop="limitLower">
			<el-input v-model="dataForm.limitLower" placeholder="限踢"></el-input>
		</el-form-item>
    <el-form-item label="最高倍率" prop="maxTimes">
      <el-input v-model="dataForm.maxTimes" placeholder="最高倍率"></el-input>
    </el-form-item>
    <el-form-item label="线数" prop="maxLines">
      <el-input v-model="dataForm.maxLines" placeholder="线数"></el-input>
    </el-form-item>
	<el-form-item label="最小在线人数" prop="onlineMin">
		<el-input v-model="dataForm.onlineMin" placeholder="只能输入数字"></el-input>
	</el-form-item>
	<el-form-item label="最大在线人数" prop="onlineMax">
		<el-input v-model="dataForm.onlineMax" placeholder="只能输入数字"></el-input>
	</el-form-item>
	<el-form-item label="抽水比率" prop="rate">
		<el-input v-model="dataForm.rate" placeholder="只能输入数字"></el-input>
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
				var res= /^[0-9]*$/;
				if (value === '') {
					callback(new Error('不可为空'));
				} else {
					if(!res.test(value)){
						callback(new Error('格式有误'));
					}else{
						if(rule.field=="rate" && value>=100){
							callback(new Error('格式有误'));
						}else{
							callback();
						}
					}
				}
			};
      return {
					visible: false,
					roomList:[],
					gradeList:[],
					dataForm: {
          id: 0,
					gameId:'',
          gameName: '',
					roomId:'',
					gradeId:'',
          enable: true,
          display: true,
          sence: '',
          bscore: '',
          restrict: '',
				  maintenance: false,
          maxTimes: '',
          maxLines: '',
					onlineMin: '',
					onlineMax: '',
         limitHigh: '',
				 limitLower: '',
         rate: 0,
        },
        dataRule: {
			gameId: [
				{ required: true, message: '游戏id不能为空', trigger: 'blur' }
			],
			  gameName: [
				{ required: true, message: '游戏名字不能为空', trigger: 'blur' }
			  ],
			  gradeId: [
				{ required: true, message: '游戏场次id不能为空', trigger: 'blur' }
			  ],
				roomId: [
					{ required: true, message: '房间不能为空', trigger: 'blur' }
				],
			  enable: [
				{ required: true, message: '是否启用不能为空', trigger: 'blur' }
			  ],
			  display: [
				{ required: true, message: '是否显示不能为空', trigger: 'blur' }
			  ],
			  sence: [
				{ required: true, message: '显示的scene页面不能为空', trigger: 'blur' }
			  ],
			  limitHigh: [
				{ required: true,   validator: validateRate, message: '限高不能为空并且只能输入数字', trigger: 'blur' }
			  ],
				limitLower: [
					{ required: true,   validator: validateRate, message: '限踢不能为空并且只能输入数字', trigger: 'blur' }
				],
				onlineMin: [
					{ required: true,  validator: validateRate, message: '最小在线人数不能为空并且只能输入数字', trigger: 'blur' }
				],
				onlineMax: [
					{ required: true,  validator: validateRate, message: '最小在线人数不能为空并且只能输入数字', trigger: 'blur' }
				],
			  restrict: [
				{ required: true, message: '入场限制不能为空', validator: validateRate, trigger: 'blur' }
			  ],
			  maintenance: [
				{ required: true, message: '是否维护中不能为空', trigger: 'blur' }
			  ],
				rate: [
					{ required: true, validator: validateRate, message: '抽水比例不能为空并且只能输入0~100数字', trigger: 'blur' },
				],
         
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
					this.$http({
						url: this.$http.adornUrl(`/room/room/RoomList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.roomList = data.roomList; //获取房间名称		
						}
					})
					this.$http({
						url: this.$http.adornUrl(`/gamegrade/gamegrade/GradeList`),
						method: 'get',
						params: this.$http.adornParams()
					}).then(({
						data
					}) => {
						if (data && data.code === 200) {
							this.gradeList = data.gradeList; //获取房间名称		
						}
					})
            this.$http({
              url: this.$http.adornUrl(`/info/info/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 200) {
					this.dataForm.gameId = data.info.gameId
					this.dataForm.gameName = data.info.gameName
					this.dataForm.gradeId = data.info.gradeId
					this.dataForm.roomId = data.info.roomId
					this.dataForm.enable = data.info.enable
					this.dataForm.display = data.info.display
					this.dataForm.sence = data.info.sence
					this.dataForm.bscore = data.info.bscore
					this.dataForm.restrict = data.info.restrict
					this.dataForm.maxTimes = data.info.maxTimes
					this.dataForm.maxLines = data.info.maxLines
					this.dataForm.maintenance = data.info.maintenance
					this.dataForm.limitLower =data.info.limitLower
					this.dataForm.limitHigh =data.info.limitHigh
					this.dataForm.onlineMin =data.info.onlineMin
					this.dataForm.onlineMax =data.info.onlineMax
					this.dataForm.rate =data.info.rate*100
	
              }
            })
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/info/info/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
							'id': this.dataForm.id || undefined,
							'gameId': this.dataForm.gameId ,
							'gameName': this.dataForm.gameName,
							'gradeId': this.dataForm.gradeId,
							'roomId': this.dataForm.roomId,
							'enable': this.dataForm.enable,
							'display': this.dataForm.display,
							'sence': this.dataForm.sence,
							'bscore': this.dataForm.bscore,
							'restrict': this.dataForm.restrict,
							'maxTimes': this.dataForm.maxTimes,
							'maxLines': this.dataForm.maxLines,
							'limitHigh': this.dataForm.limitHigh,
							'limitLower': this.dataForm.limitLower,
							'onlineMin': this.dataForm.onlineMin,
							'onlineMax': this.dataForm.onlineMax,
							'maintenance': this.dataForm.maintenance,
							'rate': this.dataForm.rate/100,
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
