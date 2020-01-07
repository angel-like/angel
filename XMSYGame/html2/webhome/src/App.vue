<template>
	<div >
  <transition name="fade">
    <router-view ></router-view>
  </transition>
		<div style="display: none;">
			<audio id="takeMoneyAudio" src="https://cplusyoga.com/FllrfJltyt6eBmdaxDv5O9_jWick" hidden="true" ></audio>
			<audio id="rechargeAudio" src="https://cplusyoga.com/Fh33qmGE4AN3YrNftlyawb1IJ3mc" hidden="true" ></audio>
			<el-button
				plain
				@click="openonlinedeposit">
			</el-button>
			<el-button
				plain
				@click="opentakemoneyrecord">
			</el-button>
		</div>
	</div>
</template>

<script>
	import { isAuth } from '@/utils'
  export default {
		data(){
			return{
				onlinedepositnum:null,
				takemoneyrecordnum:null,
				takemoneycommissionrecordnum:null
			}
		},
// 		watch:{
// 			onlinedepositnum(val,old){
// 					if(val>0){
// 						this.autoPlay()
// 						this.openonlinedeposit()
// 					}
// 				
// 			},
// 			takemoneyrecordnum(val,old){
// 					if(val>0){
// 						this.autoPlay()
// 						this.opentakemoneyrecord()
// 					}
// 				
// 			},
// 			takemoneycommissionrecordnum(val,old){
// 					if(val>0){
// 						this.autoPlay()
// 						this.opentakemoneycommissionrecord()
// 					}
// 				
// 			}
// 		},
		methods:{
			autoRechargeNotifyPlay(){
				var myAuto = document.getElementById('rechargeAudio');
				myAuto.play();
			},
			autoTakeMoneyNotifyPlay(){
				var myAuto1 = document.getElementById('takeMoneyAudio');
				myAuto1.play();
			},
			openonlinedeposit() {
					let that =this
					this.$notify({
						title: '充值提醒',
						message: '有一条新的【线下充值】提示',
						position: 'bottom-right',
						duration:4000,
						onClick: function(){
							that.$router.push({
								path: '/orderrecharge-enter', 
           
        })
						}
					});
			},
			opentakemoneyrecord() {
					let that =this
					this.$notify({
						title: '取现提醒',
						message: '有一条新的【余额取现】提示',
						position: 'bottom-right',
						duration:4000,
						onClick: function(){
							that.$router.push({
								path: '/orderrecharge-out', 
				})
						}
					});
			},
			opentakemoneycommissionrecord() {
						let that =this
						this.$notify({
							title: '取现提醒',
							message: '有一条新的【佣金取现】提示',
							position: 'bottom-right',
							duration:4000,
							onClick: function(){
								that.$router.push({
									path: '/orderrecharge-ordertakecommission', 
					})
							}
						});
				}
			
			
		},
		
		created(){
			let that = this
			setInterval(()=>{
				if(isAuth('orderrecharge:orderrecharge:totalNumber')){
					//充值
					that.$http({
						url: that.$http.adornUrl('/orderrecharge/orderrecharge/totalNumber'),
						method: 'get',
						params: that.$http.adornParams({
						})
					}).then(({data}) => {
						if (data && data.code === 200) {
							if(data.num>0){
								this.autoRechargeNotifyPlay()
								this.openonlinedeposit()
							}
							// that.onlinedepositnum=data.num
						} 
					})
				}
				
				if(isAuth('ordertakemoney:ordertakemoney:totalNumber')){
					//余额取现
					that.$http({
						url: that.$http.adornUrl('/ordertakemoney/ordertakemoney/totalNumber'),
						method: 'get',
						params: that.$http.adornParams({
						})
					}).then(({data}) => {
						if (data && data.code === 200) {
							// that.takemoneyrecordnum=data.num
							if(data.num>0){
								this.autoTakeMoneyNotifyPlay()
								this.opentakemoneyrecord()
							}
						} 
					})
				}
				if(isAuth('ordertakemoney:ordertakemoney:commissionTotalNumber')){
					//佣金取现
					that.$http({
						url: that.$http.adornUrl('/ordertakemoney/ordertakemoney/commissionTotalNumber'),
						method: 'get',
						params: that.$http.adornParams({
						})
					}).then(({data}) => {
						if (data && data.code === 200) {
							if(data.num>0){
								this.autoTakeMoneyNotifyPlay()
								this.opentakemoneycommissionrecord()
							}
							// that.takemoneycommissionrecordnum=data.num
						} 
					})
				}
			},20000)
		}

  }
</script>
<style>
	
</style>