<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
			<el-form-item label="持有道具" prop="holdProp">
				<el-select v-model="dataForm.holdProp" placeholder="请选择持有道具" clearable>
					<el-option v-for="item in holdPropOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="目标道具" prop="targetProp">
				<el-select v-model="dataForm.targetProp" placeholder="请选择目标道具" clearable>
					<el-option v-for="item in targetPropOptions" :key="item.id" :label="item.name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-tooltip class="item" effect="dark" content="兑换一个房卡所需的金币值" placement="top-start">
				<el-form-item label="兑换比例" prop="proportion">
					<el-input v-model="dataForm.proportion" placeholder="兑换比例"></el-input>
				</el-form-item>
			</el-tooltip>
		  <el-tooltip class="item" effect="dark" content="什么时间可以使用" placement="top-start">
			<el-form-item label="生效时间" prop="effectDate">
					<el-date-picker v-model="dataForm.effectDate" type="date" placeholder="生效时间" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
				</el-form-item>
		</el-tooltip>
		<el-form-item label="状态" prop="status">
			<el-radio-group v-model="dataForm.status">
				<el-radio :label="true">启用</el-radio>
				<el-radio :label="false">禁用</el-radio>
			</el-radio-group>
		</el-form-item>
		</el-form>
		<div style="color:red">兑换比例:兑换一个房卡所需的金币值</div>
		<span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
		</span>
	</el-dialog>
</template>

<script>
	export default {
		data() {
			var checkAmount = (rule, value, callback) => {
				if (value) {
					//var retgex=/^(1|1\.[0]*|0?\.(?!0+$)[\d]+)$/im;//限制0到1之间
					//var retgex = /^(\d(\.\d{1,2})?|10)$/; //限制0到10之间.最多4位小数
					var retgex = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^\d\.\d{1,2}$)/;
					if (!retgex.test(value)) {
						callback(new Error('请输入最多包含两位小数的正数'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			return {
				visible: false,
				dataForm: {
					id: 0,
					proportion: '',
					effectDate: '',
					status: '',
					targetProp: '',
					holdProp: '',
				},
				holdPropOptions: [{
					id: 1,
					name: '金币'
				}],
				targetPropOptions: [],
				dataRule: {
					proportion: [{
						required: true,
						message: '比例不能为空',
						trigger: 'blur'
					}, {
						validator: checkAmount,
						trigger: 'blur'
					}],
					effectDate: [{
						required: true,
						message: '生效时间不能为空',
						trigger: 'blur'
					}],
					targetProp: [{
						required: true,
						message: '目标道具不能为空',
						trigger: 'blur'
					}],
					holdProp: [{
						required: true,
						message: '持有道具不能为空',
						trigger: 'blur'
					}],
					status: [{
						required: true,
						message: '状态不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			init(id) {
				//道具选择
				this.$http({
					url: this.$http.adornUrl(`/sysprop/sysprop/getProp`),
					method: 'get',
					params: this.$http.adornParams()
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.targetPropOptions = data.propList
					}
				});
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/shoproomcardproportion/shoproomcardproportion/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.proportion = data.shoproomcardproportion.proportion
								this.dataForm.effectDate = data.shoproomcardproportion.effectDate
								this.dataForm.targetProp = data.shoproomcardproportion.targetProp
								this.dataForm.holdProp = data.shoproomcardproportion.holdProp
								this.dataForm.status = data.shoproomcardproportion.status
								
							}
						})
					}
				})
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(
								`/shoproomcardproportion/shoproomcardproportion/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'proportion': this.dataForm.proportion,
								'effectDate': this.dataForm.effectDate,
								'holdProp': this.dataForm.holdProp,
								'targetProp': this.dataForm.targetProp,
								'status': this.dataForm.status,

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
