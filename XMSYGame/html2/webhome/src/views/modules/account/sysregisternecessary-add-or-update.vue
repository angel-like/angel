<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-tooltip class="item" effect="dark" content="可以修改用户的名称" placement="top-start">
				<el-form-item label="显示名称" prop="name">
					<el-input v-model="dataForm.name" placeholder="显示名称"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="是否想让用户看到" placement="top-start">
				<el-form-item label="是否展示" prop="show">
					<el-switch v-model="dataForm.show" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否"></el-switch>
				</el-form-item>
			</el-tooltip>
				<el-form-item label="是否必填" prop="necessary">
					<el-switch v-model="dataForm.necessary" active-color="#13ce66" inactive-color="#ff4949" active-text="是" inactive-text="否">
					</el-switch>
				</el-form-item>
      <el-tooltip class="item" effect="dark" content="用户进行对应的操作给出的提示" placement="top-start">
				<el-form-item label="提示语" prop="hints">
					<el-input v-model="dataForm.hints" placeholder="提示语"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="数据库中的列名" placement="top-start">
				<el-form-item label="字段名" prop="remark">
					<el-input v-model="dataForm.remark" placeholder="字段名"></el-input>
				</el-form-item>
			</el-tooltip>
				<el-form-item label="序号" prop="orderNumber">
					<el-input v-model="dataForm.orderNumber" placeholder="序号(正整数)"></el-input>
				</el-form-item>
      <el-tooltip class="item" effect="dark" content="验证用户身份的方式,例如可以是数字加字母" placement="top-start">
				<el-form-item label="验证规则" prop="validationRule">
					<el-input v-model="dataForm.validationRule" placeholder="验证规则(正则)"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="例如身份验证失败给出的提示" placement="top-start">
				<el-form-item label="错误提示" prop="validationDescribe">
					<el-input v-model="dataForm.validationDescribe" placeholder="错误提示"></el-input>
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
		data() {
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
					id: 0,
					name: '',
					show: '',
					necessary: '',
					hints: '',
					orderNumber: '1',
					remark: '',
					validationRule: '',
					validationDescribe: ''
				},
				dataRule: {
					name: [{
						required: true,
						message: '显示名称不能为空',
						trigger: 'blur'
					}],
					show: [{
						required: true,
						message: '是否展示不能为空',
						trigger: 'blur'
					}],
					necessary: [{
						required: true,
						message: '是否必填不能为空',
						trigger: 'blur'
					}],
					hints: [{
						required: true,
						message: '提示语不能为空',
						trigger: 'blur'
					}],
					orderNumber: [{
						validator: validateRate,
						trigger: 'blur'
					}],
					remark: [{
						required: true,
						message: '字段名不能为空',
						trigger: 'blur'
					}]

				}
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
							url: this.$http.adornUrl(`/sysregisternecessary/sysregisternecessary/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.name = data.registerNecessary.name
								this.dataForm.show = data.registerNecessary.show
								this.dataForm.necessary = data.registerNecessary.necessary
								this.dataForm.hints = data.registerNecessary.hints
								this.dataForm.orderNumber = data.registerNecessary.orderNumber
								this.dataForm.remark = data.registerNecessary.remark
								this.dataForm.validationRule = data.registerNecessary.validationRule
								this.dataForm.validationDescribe = data.registerNecessary.validationDescribe
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				var res= /^[0-9]*[1-9][0-9]*$/;
				if(!res.test(this.dataForm.orderNumber)){
					this.$message.error("序号格式有误，请输入正整数");
				}

				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/sysregisternecessary/sysregisternecessary/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'name': this.dataForm.name,
								'show': this.dataForm.show,
								'necessary': this.dataForm.necessary,
								'hints': this.dataForm.hints,
								'orderNumber': this.dataForm.orderNumber,
								'remark': this.dataForm.remark,
								'validationRule': this.dataForm.validationRule,
								'validationDescribe': this.dataForm.validationDescribe
							})
						}).then(({
							data
						}) => {
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
