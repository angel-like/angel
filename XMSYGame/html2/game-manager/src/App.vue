<template>
	<div>
  <transition name="fade">
    <router-view ></router-view>
  </transition>
	</div>
</template>

<script>
  export default {
		data(){
			return{
				pageIndex: 1,
				pageSize: 10,
				onlinedepositnum:null,
				takemoneyrecordnum:null,
			}
		},
// 		watch:{
// 			onlinedepositnum(val,old){
// 				if(old!=null){
// 					if(val>old){
// 						this.autoPlay()
// 						this.openonlinedeposit()
// 					}
// 				}
// 				
// 			},
// 			takemoneyrecordnum(val,old){
// 				if(old!=null){
// 					if(val>old){
// 						this.autoPlay()
// 						this.opentakemoneyrecord()
// 					}
// 				}
// 				
// 			}
// 		},
		methods:{
// 			autoPlay(){
// 				var myAuto = document.getElementById('myaudio');
// 				myAuto.play()
// 			},
// 			openonlinedeposit() {
// 					let that =this
// 					this.$notify({
// 						title: '充值提醒',
// 						message: '有一条新的充值提示',
// 						position: 'bottom-right',
// 						duration:6000,
// 						onClick: function(){
// 							that.$router.push({
// 								path: '/websiteonlinedeposit-websiteonlinedeposit', 
//            
//         })
// 						}
// 					});
// 			},
// 			opentakemoneyrecord() {
// 					let that =this
// 					this.$notify({
// 						title: '取现提醒',
// 						message: '有一条新的取现提示',
// 						position: 'bottom-right',
// 						duration:6000,
// 						onClick: function(){
// 							that.$router.push({
// 								path: '/websitetakemoneyrecord-websitetakemoneyrecord', 
// 		
// 					
// 				})
// 						}
// 					});
// 			}
// 		},
		
// 		created(){
// 			let that = this
// 			setInterval(()=>{
// 						//充值
// 						that.$http({
// 							url: that.$http.adornUrl('/websiteonlinedeposit/websiteonlinedeposit/list'),
// 							method: 'get',
// 							params: that.$http.adornParams({
// 								'page': that.pageIndex,
// 								'limit': that.pageSize,
// 							})
// 						}).then(({data}) => {
// 							if (data && data.code === 200) {
// 								that.onlinedepositnum=data.page.totalCount
// 							} else {
// 								that.totalPage = 0
// 							}
// 						})
// 						//取现
// 						that.$http({
// 							url: that.$http.adornUrl('/websitetakemoneyrecord/websitetakemoneyrecord/list'),
// 							method: 'get',
// 							params: that.$http.adornParams({
// 								'page': that.pageIndex,
// 								'limit': that.pageSize,
// 							})
// 						}).then(({data}) => {
// 							if (data && data.code === 200) {
// 								that.takemoneyrecordnum=data.page.totalCount
// 							} else {
// 								that.totalPage = 0
// 							}
// 						})
// 				
// 			},3000)
 		}

  }
</script>
