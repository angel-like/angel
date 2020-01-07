<template>
  <el-dialog
    :title="'详情'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <div class="mod-config">
      <div style="margin-bottom: 20px;font-size: 14px;">
        <span style="padding-right: 15px; color: red;">会员账号：{{countForm.account}}</span>
        <span style="padding-right: 15px; color: red;">直接下级总数：{{countForm.sonPeopleCount}}</span>
        <span style="padding-right: 15px; color: red;">房卡总数：{{countForm.roomCardCount}}</span>
        <span style="padding-right: 15px; color: red;">金币总额：{{countForm.coinCount}}</span>
        <span style="padding-right: 15px; color: red;">余额宝：{{countForm.yuEBaoMoneyCount}}</span>
        <span style="padding-right: 15px; color: red;">总取款：{{countForm.takeAmountCount}}</span>
        <span style="padding-right: 15px; color: red;">总充值：{{countForm.rechargeAmountCount}}</span>
        <span style="padding-right: 15px; color: red;">总优惠：{{countForm.discountAmountCount}}</span>
        <span style="padding-right: 115px; color: red;">总返佣：{{countForm.commissionCount}}</span>
      </div>
      <div v-if="dataForm.superiorsId">
        <span style="margin-right: 20px;">上级账号：{{dataForm.superMan}}</span>
        <el-button @click="upperLevelHandle()">上一级</el-button>
      </div>
      <el-table :data="dataList" border v-loading="dataListLoading"
                style="width: 100%;">

        <el-table-column prop="id" fixed="left" header-align="center" align="center" label="会员id">
        </el-table-column>
        <el-table-column prop="account" header-align="center"   treeKey="id" align="center" label="会员账号">
        </el-table-column>
        <el-table-column prop="userName" header-align="center" align="center" label="真实姓名">
        </el-table-column>
        <el-table-column prop="superiorsAccount" header-align="center" align="center" label="直接上级">
        </el-table-column>
        <el-table-column prop="vipName" header-align="center" align="center" label="会员等级">
          <template slot-scope="scope">
            <div v-if="scope.row.vipId==0">
              VIP0
            </div>
            <div else>
              {{scope.row.vipName}}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="hierarchyName" header-align="center" align="center" label="推广层级">
        </el-table-column>
        <el-table-column prop="riskHierarchyName" header-align="center" align="center" label="风控层级">
        </el-table-column>

        <el-table-column prop="enable" header-align="center" align="center" label="账号状态">
          <template slot-scope="scope">
            <el-tag
              v-if="scope.row.forbiddenEnable=='0' && scope.row.nobetEnable=='0' && scope.row.frozenEnable=='0' && scope.row.abnormalEnable=='0' ">
              正常
            </el-tag>
            <el-tag v-if="scope.row.forbiddenEnable=='1'" type="danger">已停号</el-tag>
            <el-tag v-if="scope.row.nobetEnable=='1'" type="danger">已停押</el-tag>
            <el-tag v-if="scope.row.frozenEnable=='1'" type="danger">已冻结</el-tag>
            <el-tag v-if="scope.row.abnormalEnable=='1'" type="danger">风控</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" header-align="center" align="center" min-width="128px" label="登录状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.online" type="success">({{scope.row.deviceType}})游戏中</el-tag>
            <el-tag v-else type="info">离线</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" header-align="center" align="left" min-width="250" label="详情">
          <template slot-scope="scope">
            <div>最后登录ip:{{scope.row.LoginIp}}({{scope.row.loginAddress}})</div>
            <div>上次登录ip:{{scope.row.lastLoginIp}}({{scope.row.lastLoginAddress}})</div>
            <div>上次登录时间:{{scope.row.lastLoginTime}}</div>
            <div>注册ip:<i class="el-icon-document" v-clipboard:copy="scope.row.registerIp"
                         v-clipboard:success="onCopy"
                         v-clipboard:error="onError"
                         style="color: blue;cursor:pointer;"
                         v-if="scope.row.registerIp!=''">
              {{scope.row.registerIp}}
            </i>({{scope.row.registerIpAddress}})
            </div>
            <div>注册时间:{{scope.row.createTime}}</div>
            <div>设备码:{{scope.row.deviceCode}}</div>
            <!-- <el-button size="mini" type="text"  @click="deleteHandle(scope.row.id)"  >查看详情</el-button> -->
          </template>
        </el-table-column>
        <!--余额功能隐藏
              <el-table-column prop="money" header-align="center" align="center" label="余额">
                  <template slot-scope="scope">
                      <div v-if="isAuth('usertransactionrecord:usertransactionrecord:list')">
                          <a  @click="transactionRecord(scope.row.account)" title="查看交易记录" style="cursor:pointer ">{{scope.row.money}}</a>
                      </div>
                      <div v-if="!isAuth('usertransactionrecord:usertransactionrecord:list')">
                          {{scope.row.money}}
                      </div>
                  </template>
              </el-table-column>-->
        <el-table-column prop="yuEBaoMoney" header-align="center" align="center" label="余额宝">
          <template slot-scope="scope">
            <div v-if="scope.row.yuEBaoMoney!=null">{{scope.row.yuEBaoMoney/100}}</div>
            <div v-else>{{this.yuEBaoMoney}}</div>
          </template>
        </el-table-column>
        <el-table-column prop="coin" header-align="center" align="center" label="金币">
          <template slot-scope="scope">
            <div v-if="isAuth('user:gemerecord:list')">
              {{scope.row.coin}}
            </div>
            <div v-if="!isAuth('user:gemerecord:list')">
              {{scope.row.coin}}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="roomCard" header-align="center" align="center" label="房卡数量">
        </el-table-column>
        <el-table-column prop="rechargeAmount" header-align="center" align="left" min-width="180" label="存取款">
          <template slot-scope="scope">
            <div>上次存款:{{scope.row.lastRechargeAmount}}</div>

            <div v-if="isAuth('orderrecharge:orderrecharge:list')">
              <el-dropdown>
                存款:{{scope.row.rechargeAmount}}
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="thirdRechargeMoney(scope.row.account)">第三方支付</el-dropdown-item>
                  <el-dropdown-item @click.native="bankRechargeMoney(scope.row.account)">银行卡存款</el-dropdown-item>
                  <el-dropdown-item @click.native="adminRechargeMoney(scope.row.account)">人工充值</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <div v-if="!isAuth('orderrecharge:orderrecharge:list')">
              存款:{{scope.row.rechargeAmount}}
            </div>
            <div>优惠:{{scope.row.discountAmount}}</div>
            <div>
              上次取款:{{scope.row.lastTakeAmount}}
            </div>
            <div v-if="isAuth('ordertakemoney:ordertakemoney:list')">
              取款:{{scope.row.takeAmount}}
            </div>
            <div v-if="!isAuth('ordertakemoney:ordertakemoney:list')">
              取款:{{scope.row.takeAmount}}
            </div>
            <div>次数:取({{scope.row.takeNum}})存({{scope.row.rechargeNum}})</div>
          </template>
        </el-table-column>
        <el-table-column prop="userNeedBet" header-align="center" align="left" min-width="180" label="打码量">
          <template slot-scope="scope">
            <div>需要打码量:{{scope.row.userNeedBet}}</div>
            <div>当前打码量:{{scope.row.userValidBet}}</div>
            <div v-if="scope.row.userValidBet-scope.row.userNeedBet>=0">(已满足)</div>
            <div v-else>(未满足)</div>
          </template>
        </el-table-column>
        <el-table-column prop="userType" header-align="center" align="center" label="会员类型">
          <template slot-scope="scope">
            <div v-if="scope.row.userType=='TRIAL'">试玩账号</div>
            <div v-else-if="scope.row.userType=='USER'">普通会员</div>
            <div v-else-if="scope.row.userType=='VIP'">VIP</div>
            <div v-else-if="scope.row.userType=='TEST'">测试账号</div>
          </template>
        </el-table-column>
        <el-table-column prop="remark" header-align="center" align="center" label="下级">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" title="查看" @click="nextLevelHandle(scope.row.id,scope.row.account)">
              查看({{scope.row.sonPeople}})
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="remark" header-align="center" align="center" label="备注">
          <!-- 	<editable-cell slot-scope="{row}"
              :can-edit="editModeEnabled"
              v-model="row.name">
       <span slot="content">{{row.name}}</span>
      </editable-cell> -->
          <!-- <template slot-scope="scope">
                      <el-input size="small" v-if="scope.row.showEdit" v-model="scope.row.remark" placeholder="请输入备注" @change="handleChange(scope.$index, scope.row)"></el-input>
                      <span v-else>{{scope.row.remark}}</span>
                      <el-button size="mini" v-if="!scope.row.showBtn" title="编辑" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                  </template> -->
        </el-table-column>

      </el-table>

      <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                     :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                     layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>


      <!--<index-subordinate v-if="subordinateVisible" ref="subordinateRef"></index-subordinate>-->
    </div>
  </el-dialog>
</template>

<script>
  import moment from 'moment'
  import {
    isAuth
  } from '@/utils'

  export default {
    data() {
      return {
        /*  subordinateVisible: true,*/
        countForm: {
          account: 0,
          discountAmountCount: 0,
          rechargeAmountCount: 0,
          commissionCount: 0,
          sonPeopleCount: 0,
          roomCardCount: 0,
          yuEBaoMoneyCount: 0,
          coinCount: 0,
          takeAmountCount: 0
        },
        dataForm: {
          id: '',
          account: '',
          superiorsAccount: '',
          superMan: '',
          superiorsId: 0,
          status: '',
          userName: '',
          vipName: '',
          yuEBaoMoney: 0,
          roomCard: 0,
          bankCard: '',
          hierarchyId: null,
          registerIp: '',
          loginIp: '',
          deviceCode: '',
          deviceType: '',
          sonPeople: 0,
          userType: '',
          startTime: '',
          endTime: '',
          forbiddenEnable: '',
          nobetEnable: '',
          abnormalEnable: '',
          sortOption: '',
          orientation: 'desc',
          frozenEnable: '',
          phone: ''
        },
        dataRule: {
          id: [
            {required: true, message: '试玩个数不能为空', trigger: 'blur'}
          ]
        },
        visible: false,
        userTypeList: [],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: []
      }
    },
    methods: {
      onCopy(e) {
        if (this.dataForm.userAccount == '') {
          this.$message({
            message: '复制内容为空！',
            type: 'warning',
            duration: 1000
          });
        } else {
          this.$message({
            message: '复制成功！',
            type: 'success',
            duration: 1000
          });
        }
      },
      onError(e) {
        this.$message.error("复制失败")
      },
      getDataList() {
        this.dataListLoading = true
        var startDate = null;
        var endDate = null;
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
          }
        }
        var forbiddenEnable = null;
        var nobetEnable = null;
        var frozenEnable = null;
        var abnormalEnable = null;
        if (this.dataForm.status == 1) {
          forbiddenEnable = false;
          nobetEnable = false;
          frozenEnable = false;
          abnormalEnable = false;
        } else if (this.dataForm.status == 2) {
          forbiddenEnable = true;
        } else if (this.dataForm.status == 3) {
          nobetEnable = true;
        } else if (this.dataForm.status == 4) {
          frozenEnable = true;
        } else if (this.dataForm.status == 5) {
          abnormalEnable = true;
        }
        this.$http({
          url: this.$http.adornUrl('/user/user/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'account': this.dataForm.account,
            'id': this.dataForm.id,
            'superiorsAccount': this.dataForm.superiorsAccount,
            'status': this.dataForm.status,
            // 'startTime': startDate,
            // 'endTime': endDate,
            'abnormalEnable': abnormalEnable,
            'frozenEnable': frozenEnable,
            'nobetEnable': nobetEnable,
            'forbiddenEnable': forbiddenEnable,
            'userName': this.dataForm.userName,
            'vipName': this.dataForm.vipName,
            'roomCard': this.dataForm.roomCard,
            'bankCard': this.dataForm.bankCard,
            'hierarchyId': this.dataForm.hierarchyId,
            'registerIp': this.dataForm.registerIp,
            'loginIp': this.dataForm.loginIp,
            'deviceCode': this.dataForm.deviceCode,
            'superiorsId': this.dataForm.superiorsId,
            'deviceType': this.dataForm.deviceType,
            'userType': this.dataForm.userType,
            'sortOption': this.dataForm.sortOption,
            'orientation': this.dataForm.orientation,
            'trial': '0'
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {

            this.pcNum = data.onlineData.pcNum;
            this.androidNum = data.onlineData.androidNum;
            this.iphoneNum = data.onlineData.iphoneNum;
            this.otherNum = data.onlineData.otherNum;
            this.totalNum = data.onlineData.totalNum;
            this.dataList = data.page.list;
            // 						for(var i = 0; i < this.dataList.length; i++) {
            // 							this.showEdit[i] = false;
            // 							this.showBtn[i] = false;
            // // 							this.$set(this.showEdit[i], false);
            // // 							this.$set(this.showBtn[i], false);
            // 					}
           this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },

      getCount(id,account) {
        this.$http({
          url: this.$http.adornUrl('/user/user/statistics'),
          method: 'get',
          params: this.$http.adornParams({
            'id': id
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.countForm = data.count
            this.countForm.account = account
          } else {
            this.countForm = {
              account: 0,
              discountAmountCount: 0,
              rechargeAmountCount: 0,
              commissionCount: 0,
              sonPeopleCount: 0,
              roomCardCount: 0,
              yuEBaoMoneyCount: 0,
              coinCount: 0,
              takeAmountCount: 0

            }
          }
        })
      },
      init(id, account) {

        this.visible = true
        if (id != null) {
          this.dataForm.superiorsId = id;
          this.dataForm.superMan = account;
          this.dataForm.account = '';
          this.getDataList();
          this.getCount(id,account);
        }

      },
      // 每页数
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.getDataList()
      },

      //下一级
      nextLevelHandle(id, account) {
        //this.$refs['dataForm'].resetFields();
        this.dataForm.superiorsId = id;
        this.dataForm.superMan = account;
        this.dataForm.account = '';
        this.getDataList();
        this.getCount(id,account);
      }, //上一级
      upperLevelHandle() {
        //this.$refs['dataForm'].resetFields()
        this.$http({
          url: this.$http.adornUrl(`/user/user/surperinfo/${this.dataForm.superiorsId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.dataForm.superiorsId = data.user.superiorsId;
            this.dataForm.account = this.dataForm.superMan;
            this.dataForm.superMan = data.user.superiorsAccount;
            this.getDataList();
            this.getCount(this.dataForm.superiorsId,this.dataForm.superMan)
          }
        })

      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      }
    }
  }
</script>
