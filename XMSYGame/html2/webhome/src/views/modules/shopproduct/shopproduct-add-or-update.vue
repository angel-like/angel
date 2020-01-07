<template>
	<el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
		<el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">

     <el-tooltip class="item" effect="dark" content="道具的名称(如金币,房卡等)" placement="top-start">
				<el-form-item label="道具名称" prop="sysPropId">
					<el-select v-model="dataForm.sysPropId" placeholder="道具名称" clearable>
						<!--sysPropIdOptions 是道具实体-->
						<el-option v-for="item in sysPropIdOptions" :key="item.id" :label="item.name" :value="item.id">
						</el-option>
					</el-select>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="道具的数量" placement="top-start">
				<el-form-item label="产品数量" prop="productNumber">
					<el-input type="number" v-model="dataForm.productNumber" placeholder="产品数量"></el-input>
				</el-form-item>
			</el-tooltip>
      <el-tooltip class="item" effect="dark" content="道具的单价" placement="top-start">
      	<el-form-item label="产品单价" prop="productOnePrice">
      		<el-input type="number" v-model="dataForm.productOnePrice" placeholder="产品单价" :change="getTotalPrice()" ></el-input>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="道具的折扣(限制0到1之间)" placement="top-start">
      	<el-form-item label="折扣" prop="discount" >
      		<el-input v-model="dataForm.discount" placeholder="折扣" :change="getDiscountPrice()"></el-input>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="产品的总价" placement="top-start">
      	<el-form-item label="产品总价" prop="productTotalPrice">
      		<el-input type="number" v-model="dataForm.productTotalPrice" placeholder="产品总价"  readOnly></el-input>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="产品的优惠价格" placement="top-start">
      	<el-form-item label="产品优惠价" prop="productPrice">
      		<el-input type="number" v-model="dataForm.productPrice" placeholder="产品优惠价" :formatter="formatterRate" readOnly></el-input>
      	</el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="是否把这个产品上架" placement="top-start">
      	<el-form-item label="是否上架" prop="sell">
      		<el-radio-group v-model="dataForm.sell">
      			<el-radio :label="true">是</el-radio>
      			<el-radio :label="false">否</el-radio>
      		</el-radio-group>
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
			var checkAmount = (rule, value, callback) => {
				if (value) {
					var retgex = /^\+?[1-9][0-9]*$/;
					if (!retgex.test(value)) {
						callback(new Error('请输入非零的正整数'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			var checkDiscount = (rule, value, callback) => {
				if (value) {
					var retgex=/^(0(\.\d{1,2})?|1(\.0{1,2})?)$/;
					//var retgex=/^(1|1\.[0]*|0?\.(?!0+$)[\d]+)$/im;//限制0到1之间
					//var retgex = /^(\d(\.\d{1,4})?|10)$/; //限制0到10之间.最多4位小数
					if (!retgex.test(value)) {
						callback(new Error('请输入0到1之间的数,最多两位小数'));
					} else {
						callback();
					}
				} else {
					callback();
				}
			};
			return {
				visible: false,
				sysPropIdOptions: [],
				multiple: 100,
				dataForm: {
					id: 0,
					sysPropId: '',
					productNumber: '',
					productPrice: '',
					productTotalPrice: '',
					discount: '',
					productOnePrice: '',
					sell: true,
				},
				dataRule: {
					sysPropId: [{
						required: true,
						message: '道具名称id不能为空',
						trigger: 'blur'
					}],
					productNumber: [{
							required: true,
							message: '产品数量不能为空',
							trigger: 'blur'
						},
						{
							validator: checkAmount,
							trigger: 'blur'
						}
					],
					productOnePrice: [{
							required: true,
							message: '产品单价不能为空',
							trigger: 'blur'
						},
						{
							validator: checkAmount,
							trigger: 'blur'
						}
					],
					discount: [{
							required: true,
							message: '折扣不能为空',
							trigger: 'blur'
						},
						{
							validator: checkDiscount,
							trigger: 'blur'
						}
					],
					sell: [{
						required: true,
						message: '是否上架不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		methods: {
			formatterRate(row, column, cellValue, index){
				return Number(cellValue).toFixed(2);
			},
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
						this.sysPropIdOptions = data.propList
					}
				});
				this.dataForm.id = id || 0
				this.visible = true
				this.$nextTick(() => {
					this.$refs['dataForm'].resetFields()
					if (this.dataForm.id) {
						this.$http({
							url: this.$http.adornUrl(`/shopproduct/shopproduct/info/${this.dataForm.id}`),
							method: 'get',
							params: this.$http.adornParams()
						}).then(({
							data
						}) => {
							if (data && data.code === 200) {
								this.dataForm.sysPropId = data.shopproduct.sysPropId
								this.dataForm.productNumber = data.shopproduct.productNumber
								//如果产品id 为房卡时，价格要除以100
								if (data.shopproduct.sysPropId === 2) {
									this.dataForm.productOnePrice = data.shopproduct.productOnePrice / this.multiple
									this.dataForm.productTotalPrice = data.shopproduct.productTotalPrice / this.multiple
									this.dataForm.productPrice = data.shopproduct.productPrice / this.multiple
								} else {
									this.dataForm.productOnePrice = data.shopproduct.productOnePrice
									this.dataForm.productTotalPrice = data.shopproduct.productTotalPrice
									this.dataForm.productPrice = data.shopproduct.productPrice
								}
								this.dataForm.discount = data.shopproduct.discount
								this.dataForm.sell = data.shopproduct.sell
							}
						})
					}
				})
			},
			//获取总价格
			getTotalPrice(){
				this.dataForm.productTotalPrice=this.dataForm.productNumber*this.dataForm.productOnePrice;
				if(this.dataForm.discount!=null){//如果折扣已经存在，直接算出优惠价
						this.dataForm.productPrice=this.dataForm.productTotalPrice*this.dataForm.discount;
				}
			},
			//获取优惠价
			getDiscountPrice(){
					this.dataForm.productPrice=this.dataForm.productTotalPrice*this.dataForm.discount;
			},
			//获取道具下拉列表
			getSysPropId() {
				if (this.dataForm.sysPropId == '') {
					return;
				}
				this.$http({
					url: this.$http.adornUrl(`/shopproduct/shopproduct/getSysPropId`),
					method: 'get',
					params: this.$http.adornParams({
						"sysPropId": this.dataForm.sysPropId
					})
				}).then(({
					data
				}) => {
					if (data && data.code === 200) {
						this.dataForm.sysPropId = data.data.sysPropId;
					} else {
						this.dataForm.sysPropId = 0;
						this.$message.error(data.msg)
					}
				});
			},
			// 表单提交
			dataFormSubmit() {
				this.$refs['dataForm'].validate((valid) => {
					//如果产品id 为房卡时，价格要乘以100
					var youhuijia = this.dataForm.productPrice;
					var zongjia = this.dataForm.productTotalPrice;
					var danjia = this.dataForm.productOnePrice;
					if (this.dataForm.sysPropId == 2) {
						danjia = danjia * this.multiple;
						zongjia = zongjia * this.multiple;
						youhuijia = youhuijia * this.multiple;
					}
					if (valid) {
						this.$http({
							url: this.$http.adornUrl(`/shopproduct/shopproduct/${!this.dataForm.id ? 'save' : 'update'}`),
							method: 'post',
							data: this.$http.adornData({
								'id': this.dataForm.id || undefined,
								'sysPropId': this.dataForm.sysPropId,
								'productNumber': this.dataForm.productNumber,
								'productOnePrice': danjia, //单价
								'productTotalPrice': zongjia, //总价
								'discount': this.dataForm.discount, //折扣
								'productPrice': youhuijia, //优惠价
								'sell': this.dataForm.sell,
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
