<template>
  <el-dialog
    :title="'游戏详情' "
    :close-on-click-modal="false"
    :visible.sync="visible">
	<div style="padding-bottom: 20px">游戏局号：{{dataForm.gameRoundNo}}</div>
		<el-table
			:data="dataList"
			border
			v-loading="dataListLoading"
			style="width: 100%;">
		<el-table-column
			prop="createTime"
			header-align="center"
			align="center"
			label="游戏时间">
		</el-table-column>
		  <el-table-column
			prop="userId"
			header-align="center"
			align="center"
			label="用户ID">
		  </el-table-column>
		  <el-table-column
			prop="userAccount"
			header-align="center"
			align="center"
			label="用户账号">
		  </el-table-column>
		  <el-table-column
		    prop="gameName"
		    header-align="center"
		    align="center"
		    label="游戏名称">
		  </el-table-column>
		  <el-table-column
		    prop="gradeName"
		    header-align="center"
		    align="center"
		    label="场次名称">
		  </el-table-column>
		  <el-table-column
		    prop="roomName"
		    header-align="center"
		    align="center"
		    label="房间名称">
		  </el-table-column>
		 <el-table-column
		   prop="baseScore"
		   header-align="center"
		   align="center"
		   label="底分">
		 	<template slot-scope="scope">
				<div>
				{{scope.row.baseScore/100}}
				</div>
		 	</template>
		 </el-table-column>
			<el-table-column
				prop="multiple"
				header-align="center"
				align="center"
				label="倍数">
			</el-table-column>
		  <el-table-column
		    prop="coins"
		    header-align="center"
		    align="center"
		    label="赢输金币">
			<template slot-scope="scope">
				<div>
				{{scope.row.coins/100}}
				</div>
			</template>
		  </el-table-column>
		  <el-table-column
		    prop="landlord"
		    header-align="center"
		    align="center"
		    label="是否地主">
			<template slot-scope="scope">
				<div v-if="scope.row.landlord">
						是
				</div>
				<div v-else>
					否
				</div>
			</template>
		  </el-table-column>
		  <el-table-column
		    prop="robot"
		    header-align="center"
		    align="center"
		    label="是否机器人">
			<template slot-scope="scope">
				<div v-if="scope.row.robot">
						是
				</div>
				<div v-else>
					否
				</div>
			</template>
		  </el-table-column>
		</el-table>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
		dataList: [],
		dataListLoading: false,
        dataForm: {
          gameRoundNo: '',
        }
      }
    },
    methods: {
      init (gameRoundNo) {
        this.dataForm.gameRoundNo = gameRoundNo
        this.visible = true
        this.$nextTick(() => {
		  this.dataListLoading = true
          this.gamerecordList();
        })
      },
		gamerecordList(){
			this.dataListLoading = true
			this.$http({
				url: this.$http.adornUrl('/gamerecordfightlandlords/gamerecordfightlandlords/getGameList'),
				method: 'get',
				params: this.$http.adornParams({
					'gameRoundNo': this.dataForm.gameRoundNo
				})
			}).then(({data}) => {
				if (data && data.code === 200) {
					this.dataList = data.gamerecordList
					this.dataForm.gameRoundNo = data.gameRoundNo
				} else {
					this.dataList = []
				}
				this.dataListLoading = false
			})
		},
    }
  }
</script>
