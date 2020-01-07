<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
			<el-form-item label="机器人账号">
				<el-input v-model="dataForm.name" placeholder="机器人账号" clearable></el-input>
			</el-form-item>
			<el-form-item label="游戏" >
					<el-select v-model="dataForm.game"  placeholder="请选择游戏" clearable>
						<el-option
						v-for="item in gameOptions"
						:key="item.id"
						:label="item.gameName"
						:value="item.id">
						<span style="float: left">{{ item.gameName }}</span>
						<span style="float: right; color: #8492a6; font-size: 13px">{{ item.gradeName }}</span>
						</el-option>
					</el-select>
					<el-input v-model="dataForm.gradeName" placeholder="场次名称" style="width: 100px;" disabled ></el-input>
					<el-input v-model="dataForm.gameId" placeholder="游戏ID" style="width: 80px;" disabled v-if="false"></el-input>
					<el-input v-model="dataForm.gradeId" placeholder="场次ID" style="width: 80px;" disabled v-if="false"></el-input>
			</el-form-item>
			<el-form-item label="机器人状态">
				<el-select v-model="dataForm.status" placeholder="请选择状态" clearable>
					<el-option
					v-for="item in options"
					:key="item.type"
					:label="item.label"
					:value="item.type">
					</el-option>
				</el-select>
			</el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('robot:robot:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
				<el-button v-if="isAuth('robot:robot:taskRecyclingRobot')" type="danger" @click="taskRecyclingRobot()" :disabled="dataListSelections.length <= 0">回收机器人</el-button>
      </el-form-item>
    </el-form>
    <div style="color: red;">
    总盈利(元):{{countProfit/1000000}}万
    </div>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        label="id">
      </el-table-column>
      <el-table-column
      	prop="name"
      	header-align="center"
      	align="center"
      	label="机器人账号">
      </el-table-column>
			<el-table-column
				prop="sex"
				header-align="center"
				align="center"
				label="性别">
				<template slot-scope="scope">
					<div v-if="scope.row.sex=='1'" >
						男
					</div>
					<div v-if="scope.row.sex=='0'">
						女
					</div>
				</template>
			</el-table-column>
			<el-table-column
				prop="portrait"
				header-align="center"
				align="center"
				label="头像">
			</el-table-column>
			<el-table-column
        prop="coin"
        header-align="center"
        align="center"
        label="初始金币">
				<template slot-scope="scope">
					{{scope.row.coin/100}}
				</template>
      </el-table-column>
      <!-- <el-table-column
        prop="profitCoin"
        header-align="center"
        align="center"
        label="盈利额(元)">
				<template slot-scope="scope">
					{{scope.row.profitCoin/10000}}
				</template>
      </el-table-column> -->
      <el-table-column
        prop="enable"
        header-align="center"
        align="center"
        label="是否可用">
				<template slot-scope="scope">
					<div v-if="scope.row.enable=='1'">
						可用
					</div>
					<div v-if="scope.row.enable=='0'" style="color: red;">
						结算中
					</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="状态">
				<template slot-scope="scope">
					<div v-if="scope.row.status=='1'" style="color: red;">
						游戏中
					</div>
					<div v-if="scope.row.status=='0'">
						空闲
					</div>
				</template>
      </el-table-column>
      <el-table-column
        prop="gameName"
        header-align="center"
        align="center"
        label="游戏名称">
      </el-table-column>
			<el-table-column
				prop="updateTime"
				header-align="center"
				align="center"
				label="最后操作时间" width="100px">
			</el-table-column>
      <el-table-column
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
					<el-button type="text" size="small" v-if="scope.row.enable=='0'" @click="enable(scope.row.id)">启用</el-button>
					<el-button type="text" size="small" v-if="scope.row.enable=='1'" @click="disable(scope.row.id)">结算</el-button>
        </template>
      </el-table-column>
    </el-table>
		
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dataForm: {
          game: '',
					gameId:'',
					gradeId:'',
					name:'',
					status:'',
					
        },
				countProfit:0,
				options:[{
					"type":true,
					"label":"游戏中"
				},
				{
					"type":false,
					"label":"空闲中"
				}],
        dataList: [],
				gameOptions:[],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
    },
    activated () {
      this.getDataList()
    },
		watch:{
			//监听
			dataForm:{
				handler(val,oldval){
					//获取选择值
					this.gameOptions.forEach((game,i)=>{
						if(game.id==val.game){
							this.dataForm.gameName=game.gameName
							this.dataForm.gameId=game.gameId
							this.dataForm.gradeId=game.gradeId
							this.dataForm.gradeName=game.gradeName
							this.getDataList ();
						}
						if(val.game==null||val.game==''){
							this.dataForm.gameName=''
							this.dataForm.gameId=''
							this.dataForm.gradeId=''
							this.dataForm.gradeName=''
							this.getDataList ();
						}
					})
				},
				deep:true
			}
		},
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
				
				//为游戏下拉获取数据
 				this.$http({
					url: this.$http.adornUrl(`/gameinfo/gameinfo/gameSelectForRobot`),
 					method: 'get',
 					params: this.$http.adornParams()
 				}).then(({data}) => {
					if (data && data.code === 200) {
 						this.gameOptions = data.data
					}
 				});
				
				
        this.$http({
          url: this.$http.adornUrl('/robot/robot/list'),
					method: 'post',
					data: this.$http.adornData({
					'page': this.pageIndex,
					'limit': this.pageSize,
					'gradeId': this.dataForm.gradeId,
					'gameId': this.dataForm.gameId,
					'name': this.dataForm.name,
					'status':this.dataForm.status
					})
        }).then(({data}) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
						this.countProfit = data.countProfit
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 回收机器人
      taskRecyclingRobot (id) {
				if(this.dataForm.gameId==null||this.dataForm.gameId==''){
					this.$message.error("请先选择游戏及场次");
					return;
				}
				if(this.dataForm.gradeId==null||this.dataForm.gradeId==''){
					this.$message.error("请先选择游戏及场次");
					return;
				}
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对当前页所选机器人进行回收操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/robot/robot/taskRecyclingRobot'),
            method: 'post',
            data: this.$http.adornData({
						'ids': ids ,
						'gameId': this.dataForm.gameId ,
						'gradeId': this.dataForm.gradeId 
						})
          }).then(({data}) => {
            if (data && data.code === 200) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      },
			// 启用
			enable (id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定[启用]?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http({
						url: this.$http.adornUrl('/robot/robot/enable/' + id),
						method: 'get',
						params: this.$http.adornParams({
							'page': this.pageIndex,
							'key': this.dataForm.key
						})
					}).then(({data}) => {
						if (data && data.code === 200) {
							this.$message({
								message: '操作成功',
								type: 'success',
								duration: 1500,
								onClose: () => {
								this.getDataList()
							}
						})
						} else {
							this.$message.error(data.msg)
						}
					})
				})
			},
			// 禁用
			disable (id) {
				var ids = id ? [id] : this.dataListSelections.map(item => {
					return item.id
				})
				this.$confirm(`确定[结算]?`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
						this.$http({
							url: this.$http.adornUrl('/robot/robot/disable/' + id),
							method: 'get',
							params: this.$http.adornParams({
								'page': this.pageIndex,
								'key': this.dataForm.key
							})
						}).then(({data}) => {
							if (data && data.code === 200) {
								this.$message({
									message: '操作成功',
									type: 'success',
									duration: 1500,
									onClose: () => {
									this.getDataList()
								}
							})
							} else {
								this.$message.error(data.msg)
							}
						})
					})
			}
    }
  }
</script>
