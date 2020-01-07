<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" ref="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels
                        range-separator="至"
                        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions2"
                        value-format="yyyy-MM-dd HH:mm:ss" :default-time="['00:00:00', '23:59:59']">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.id"  placeholder="会员ID" clearable
                  @change="clearTime(dataForm.id)"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.account" placeholder="会员账号" clearable
                  @change="clearTime(dataForm.account)"></el-input>
      </el-form-item>
      <el-form-item>
        <el-select v-model="dataForm.status" clearable placeholder="请选择状态" @change="clearTime(dataForm.status)">
          <el-option v-for="item in statusOptions" :key="item.key" :label="item.label" :value="item.key">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.superiorsAccount" placeholder="上级" clearable
                  @change="clearTime(dataForm.superiorsAccount)"></el-input>
      </el-form-item>
      <!-- <el-form-item>
                <el-select v-model="dataForm.userType" clearable  placeholder="请选择会员类型">
                    <el-option
                        v-for="item in userTypeList"
                        :key="item.code"
                        :label="item.name"
                        :value="item.code">
                    </el-option>
                </el-select>
            </el-form-item> -->
      <el-form-item>
        <el-select v-model="dataForm.hierarchyId" clearable placeholder="请选择推广层级"
                   @change="clearTime(dataForm.hierarchyId)">
          <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.userName" placeholder="真实姓名" clearable
                  @change="clearTime(dataForm.userName)"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.bankCard" placeholder="银行卡号" clearable
                  @change="clearTime(dataForm.bankCard)"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.registerIp" placeholder="注册ip" clearable
                  @change="clearTime(dataForm.registerIp)"></el-input>
      </el-form-item>

      <el-form-item>
        <el-select v-model="dataForm.deviceType" placeholder="请选择登录设备类型" clearable
                  @change="clearTime(dataForm.deviceType)">
          <el-option v-for="item in deviceTypeOptions" :key="item.key" :label="item.label" :value="item.key">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.deviceCode" placeholder="设备码" clearable
                  @change="clearTime(dataForm.deviceCode)"></el-input>
      </el-form-item>
      <!-- <el-form-item>
                <el-input v-model="dataForm.loginIp" placeholder="当前登录ip" clearable></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="dataForm.deviceCode" placeholder="设备码" clearable></el-input>
            </el-form-item> -->
      </el-form-item>

      <el-tooltip class="item" effect="dark" content="请选择排序方式" placement="top-start">
        <el-form-item>
          <el-select v-model="dataForm.sortOption" clearable placeholder="请选择排序方式"
                     @change="clearTime(dataForm.sortOption)">
            <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button @click="clearButton()">清空</el-button>
        <el-button @click="getDataList()">刷新</el-button>
        <el-button @click="exportCSV()">下载Excel</el-button>
        <el-button v-if="isAuth('user:user:deletePassWordNum')" type="danger" @click="deletePassWordNum()"
                   :disabled="dataListSelections.length <= 0">重置密码错误次数
        </el-button>
        <el-button v-if="isAuth('user:user:delete')" type="danger" @click="deleteHandle()"
                   :disabled="dataListSelections.length <= 0">批量删除
        </el-button>
        <el-button type="danger" @click="batchUserHierarchy()" :disabled="dataListSelections.length <= 0">批量修改用户层级
        </el-button>
        <el-tooltip class="item" effect="dark" content="会员不能有存在存款记录" placement="top-start">
          <el-button type="danger" @click="batchMarkTestUser()">标记测试会员</el-button>
        </el-tooltip>
        <!-- <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button> -->
        <!--        <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
 -->
      </el-form-item>
    </el-form>
    <div style="margin-bottom: 20px;font-size: 16px;">
      <span style="padding-right: 25px; color: red;">注册人数：{{totalNum}}</span>
    </div>
    <div v-if="dataForm.superiorsId">
      <span style="margin-right: 20px;">上级账号：{{dataForm.superMan}}</span>
      <el-button @click="upperLevelHandle()">上一级</el-button>
    </div>
    <!-- 表格 -->
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
              style="width: 100%;">
      <el-table-column type="selection" header-align="center" align="center" width="50">
      </el-table-column>
      <!-- <el-table-column prop="id" fixed="left" header-align="center" align="center" label="会员id">
      </el-table-column> -->
      <el-table-column prop="account" width="250" header-align="center" align="left" label="会员详情">
        <template slot-scope="scope">
          <div>
            会员id：
            {{scope.row.id}}
          </div>
          <div>
              会员号：<a @click="read(scope.row)" title="查看详情" style="cursor:pointer "> {{scope.row.account}}</a>&emsp;
            <span v-clipboard:copy="scope.row.account" v-clipboard:success="onCopy" style="color: blue;cursor:pointer;"
              v-clipboard:error="onError">
              <i class="el-icon-document"></i>复制
            </span>
          </div>
          <div>
            真实姓名：
              <span v-if="scope.row.userName===undefined||scope.row.userName===''||scope.row.userName === null">暂无</span>
              <span v-else>
                {{scope.row.userName}}
              </span>
              <span v-clipboard:copy="scope.row.userName" v-if="scope.row.userName!=''"
               v-clipboard:success="onCopy"
               v-clipboard:error="onError"
               style="color: blue;cursor:pointer;">&emsp;<i class="el-icon-document"></i>复制</span>
          </div>

          <div>
            直接上级：
            <span v-if="scope.row.superiorsAccount===undefined||scope.row.superiorsAccount===''||scope.row.superiorsAccount===null">暂无</span>
            <span v-else>{{scope.row.superiorsAccount}}</span>
          </div>

          <div>
            会员等级：
            <span v-if="scope.row.vipId==0">
            <a @click="readVipRecord(scope.row.id)" title="查看详情" style="cursor:pointer ">VIP0</a>
            </span>
            <span else>
              <a @click="readVipRecord(scope.row.id)" title="查看详情" style="cursor:pointer ">{{scope.row.vipName}}</a>
            </span>
          </div>

          <div>
            推广层级：
            {{scope.row.hierarchyName}}
          </div>
          <div>
            渠道码：
            {{scope.row.channelCode}}
          </div>
          <div>
            账号状态：
            <el-tag v-if="scope.row.forbiddenEnable=='0' && scope.row.nobetEnable=='0' && scope.row.frozenEnable=='0' && scope.row.abnormalEnable=='0' ">
              正常
            </el-tag>
            <el-tag v-if="scope.row.forbiddenEnable=='1'" type="danger">已拉黑</el-tag>
            <el-tag v-if="scope.row.nobetEnable=='1'" type="danger">已停押</el-tag>
            <el-tag v-if="scope.row.frozenEnable=='1'" type="danger">已冻结</el-tag>
            <el-tag v-if="scope.row.abnormalEnable=='1'" type="danger">风控</el-tag>
          </div>

          <div>
            登录状态：
            <el-tag v-if="scope.row.online" type="success">({{scope.row.deviceType}})游戏中</el-tag>
            <el-tag v-else type="info">离线</el-tag>
          </div>


          <!-- <div>
            风控层级：{{scope.row.riskHierarchyName}}
          </div> -->



          <!--
                     <input id="accountId" v-model="dataList" hidden="true"></input>
                    <i class="el-icon-document" ref="copy" data-clipboard-action="copy" data-clipboard-target="#accountId" id="btn" @click="copyDocument(scope.row.account)"></i> -->
        </template>

      </el-table-column>
      <!--


      <el-table-column prop="remark" header-align="center" align="center" min-width="128px" label="登录状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.online" type="success">({{scope.row.deviceType}})游戏中</el-tag>
          <el-tag v-else type="info">离线</el-tag>
        </template>
      </el-table-column> -->
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

      <el-table-column prop="capital" header-align="center" align="center" label="资金" min-width="150px">
        <template slot-scope="scope">
          <div v-if="scope.row.yuEBaoMoney!=null">余额宝：{{scope.row.yuEBaoMoney/100}}</div>
          <!-- <div v-if="isAuth('user:gemerecord:list')"> -->
          <div>
            <a @click="gameRecord(scope.row.account)" title="查看游戏记录" style="cursor:pointer ">金币：{{scope.row.coin}}</a>
          </div>
          <div>房卡数量：{{scope.row.roomCard}}</div>
					<el-tooltip class="item" effect="dark" content="开元游戏上分金额将冻结" placement="bottom">
						<div>冻结金额：{{scope.row.freezeCoin/100}}</div>
					</el-tooltip>
        </template>
      </el-table-column>
      <!-- <el-table-column prop="yuEBaoMoney" header-align="center" align="center" label="余额宝">
        <template slot-scope="scope">
          <div v-if="scope.row.yuEBaoMoney!=null">{{scope.row.yuEBaoMoney/100}}</div>
          <div v-else>{{this.yuEBaoMoney}}</div>
        </template>
      </el-table-column>
      <el-table-column prop="coin" header-align="center" align="center" label="金币">
        <template slot-scope="scope">
          <div v-if="isAuth('user:gemerecord:list')">
            <a @click="gameRecord(scope.row.account)" title="查看游戏记录" style="cursor:pointer ">{{scope.row.coin}}</a>
          </div>
          <div v-if="!isAuth('user:gemerecord:list')">
            {{scope.row.coin}}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="roomCard" header-align="center" align="center" label="房卡数量">
      </el-table-column> -->
      <el-table-column prop="rechargeAmount" header-align="center" align="left" min-width="180" label="存取款">
        <template slot-scope="scope">
          <div>当前存款:{{scope.row.lastRechargeAmount}}</div>

          <div v-if="isAuth('orderrecharge:orderrecharge:list')">
            <a style="cursor:pointer " @click="bankRechargeMoney(scope.row.account)">总存款:{{scope.row.rechargeAmount}}</a>
            <!-- <el-dropdown>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="thirdRechargeMoney(scope.row.account)">第三方支付</el-dropdown-item>
                <el-dropdown-item @click.native="bankRechargeMoney(scope.row.account)">银行卡存款</el-dropdown-item>
                <el-dropdown-item @click.native="adminRechargeMoney(scope.row.account)">人工充值</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown> -->
          </div>
          <div v-if="!isAuth('orderrecharge:orderrecharge:list')">
            存款:{{scope.row.rechargeAmount}}
          </div>
					<div>当前优惠:{{scope.row.lastDiscountAmount}}</div>
          <div>总优惠:{{scope.row.discountAmount}}</div>
					<span>总累计存款:</span>
					<span v-if="scope.row.rechargeAmount+ scope.row.discountAmount>0">{{(scope.row.rechargeAmount + scope.row.discountAmount).toFixed(2)}}</span>
          <div>
            上次取款:{{scope.row.lastTakeAmount}}
          </div>
          <div v-if="isAuth('ordertakemoney:ordertakemoney:list')">
            <a @click="takeMoney(scope.row.account)" style="cursor:pointer  ">总取款:{{scope.row.takeAmount}}</a>
          </div>
          <div v-if="!isAuth('ordertakemoney:ordertakemoney:list')">
            总取款:{{scope.row.takeAmount}}
          </div>
          <div>次数:取({{scope.row.takeNum}})存({{scope.row.rechargeNum}})</div>
        </template>
      </el-table-column>
      <el-table-column prop="userNeedBet" header-align="center" align="left" min-width="180" label="打码量">
        <template slot-scope="scope">
          <div>当前需要打码量:{{scope.row.userNeedBet}}</div>
          <div>当前打码量:{{(scope.row.userValidBet/100).toFixed(2)}}</div>
					<div>
						<!--<span>当前需要打码倍数:</span>
						<span v-if="scope.row.lastRechargeAmount>0">{{scope.row.userNeedBet/scope.row.lastRechargeAmount| rounding}}</span>
						<span v-else>0</span>-->
					</div>
					<div style="color: red;">
						<span>差值:</span>
						<span v-if="scope.row.userNeedBet>scope.row.userValidBet">{{(scope.row.userNeedBet-scope.row.userValidBet).toFixed(2)}}</span>
						<span v-else>0</span>
					</div>
					<div>总累计打码量:{{scope.row.totalUserValidBet>0?scope.row.totalUserValidBet/100:0}}</div>
					<span>累计打码倍数:</span>
					<span v-if="(scope.row.rechargeAmount+ scope.row.discountAmount)>0&&scope.row.totalUserValidBet!=null">
						{{scope.row.totalUserValidBet/(scope.row.rechargeAmount + scope.row.discountAmount)/100| rounding}}
					</span>
					<span v-else>0</span>
          <div v-if="scope.row.userValidBet-scope.row.userNeedBet>=0">(已满足)</div>
          <div v-else>(未满足)</div>
        </template>
      </el-table-column>
      <el-table-column prop="userType" header-align="center" align="center" label="会员类型">
        <template slot-scope="scope">
          <div v-if="scope.row.userType=='TRIAL'">试玩账号</div>
          <div v-else-if="scope.row.userType=='USER'">普通会员</div>
          <div v-else-if="scope.row.userType=='TOURIST'">游客</div>
          <div v-else-if="scope.row.userType=='VIP'">VIP</div>
          <div v-else-if="scope.row.userType=='TEST'">测试账号</div>
        </template>
      </el-table-column>
      <el-table-column prop="remark" header-align="center" align="center" label="下级">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" title="查看" @click="nextLevelHandle(scope.row.id,scope.row.account)">查看({{scope.row.sonPeople}})
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
      <el-table-column fixed="right" header-align="center" align="center" width="160" label="操作">
        <template slot-scope="scope">
          <el-button-group>
            <div style="position: relative; top: 3px;">
              <el-dropdown>
                <el-button size="mini" type="primary" title="修改" icon="el-icon-menu"></el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="editLoginPassword(scope.row.id,scope.row.account)"
                                    v-if="isAuth('user:user:editpasswd')">修改登录密码
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="editBankPassword(scope.row.id,scope.row.account)"
                                    v-if="isAuth('user:user:editpasswd')">修改取款密码
                  </el-dropdown-item>
                  <el-dropdown-item @click.native="editHierarchy(scope.row.id)"
                                    v-if="isAuth('user:user:editHierarchy')">修改用户层级
                  </el-dropdown-item>
                 <!-- <el-dropdown-item @click.native="editRiskHierarchy(scope.row.id)"
                                    v-if="isAuth('user:user:editRiskHierarchy')">修改用户风控层级
                  </el-dropdown-item> -->
                  <!-- <el-dropdown-item @click.native="editUserMoney()" v-if="isAuth('user:user:editUserMoney')">修改用户余额</el-dropdown-item> -->
                </el-dropdown-menu>
              </el-dropdown>
              <el-button style="margin-right: 3px;" size="mini" type="primary" v-if="isAuth('user:user:save')"
                         title="编辑基本信息"
                         @click="addOrUpdateHandle(scope.row.id)" icon="el-icon-edit"></el-button>
            </div>
            <!-- <el-button size="mini" type="success" v-if="isAuth('user:user:editpasswd')" title="修改会员登录密码" @click="editLoginPassword(scope.row.id,scope.row.account)"  icon="el-icon-edit" ></el-button> -->
            <!-- <el-button size="mini" type="success" v-if="isAuth('user:user:editpasswd')" title="修改会员取款密码" @click="editBankPassword(scope.row.id,scope.row.account)"  icon="el-icon-check" ></el-button> -->
            <!-- <el-button size="mini" type="primary" v-if="isAuth('user:user:editHierarchy')" title="修改层级" @click="editHierarchy(scope.row.id)"  icon="el-icon-message" ></el-button> -->
            <el-button style="margin-right: 3px;" size="mini" type="success" v-if="isAuth('user:user:info')"
                       title="查看会员详情"
                       @click="queryUserInfoHandle(scope.row.id)" icon="el-icon-search"></el-button>
            <el-dropdown v-if="isAuth('user:user:more')">
              <el-button size="mini" type="primary" title="更多" icon="el-icon-menu"></el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="forbidden(scope.row.id,scope.row.account)"
                                  v-if="!scope.row.forbiddenEnable">拉黑
                </el-dropdown-item>
                <el-dropdown-item @click.native="relieveForbidden(scope.row.id,scope.row.account)" v-else>取消拉黑
                </el-dropdown-item>
                <el-dropdown-item @click.native="frozen(scope.row.id,scope.row.account)" v-if="!scope.row.frozenEnable">
                  冻结
                </el-dropdown-item>
                <el-dropdown-item @click.native="relieveFrozen(scope.row.id,scope.row.account)" v-else>解除冻结
                </el-dropdown-item>
                <el-dropdown-item @click.native="nobet(scope.row.id,scope.row.account)" v-if="!scope.row.nobetEnable">
                  停压
                </el-dropdown-item>
                <el-dropdown-item @click.native="relieveNobet(scope.row.id,scope.row.account)" v-else>解除停压
                </el-dropdown-item>
                <!-- 	<el-dropdown-item @click.native="abnormal(scope.row.id,scope.row.account)" v-if="!scope.row.abnormalEnable">异常</el-dropdown-item>
                                  <el-dropdown-item @click.native="normal(scope.row.id,scope.row.account)" v-else>正常</el-dropdown-item> -->
                <el-dropdown-item @click.native="LoginOutMember(scope.row.id,scope.row.account)"
                                  v-if="scope.row.online">登出
                </el-dropdown-item>
                <el-dropdown-item @click.native="deleteMember(scope.row.id,scope.row.account)">删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>

          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <vip-record v-if="vipRecordVisible" ref="vipRecord" @refreshDataList="getDataList"></vip-record>
    <edit-user-details v-if="editUserDetailsVisible" ref="editUserDetails" @refreshDataList="getDataList"
                       @recordingFlag="recordingFlag"></edit-user-details>
    <edit-login-passwd v-if="editLoginPasswdVisible" ref="editLoginPasswd"
                       @refreshDataList="getDataList"></edit-login-passwd>
    <edit-bank-passwd v-if="editBankPasswdVisible" ref="editBankPasswd"
                      @refreshDataList="getDataList"></edit-bank-passwd>
    <edit-hierarchy v-if="editHierarchyVisible" ref="editHierarchy" @refreshDataList="getDataList"></edit-hierarchy>
    <batch-edit-hierarchy v-if="batchEditHierarchyVisible" ref="batchEditHierarchy"
                          @refreshDataList="getDataList"></batch-edit-hierarchy>
    <edit-riskHierarchy v-if="editRiskHierarchyVisible" ref="editRiskHierarchy"
                        @refreshDataList="getDataList"></edit-riskHierarchy>
    <query-userInfo v-if="queryUserInfoVisible" ref="queryUserInfo" @refreshDataList="getDataList"></query-userInfo>
    <user v-if="readUser" ref="user" @refreshDataList="getDataList"></user>
    <!-- 	<edit-userMoney v-if="editUserMoneyVisible" ref="editUserMoney" @refreshDataList="getDataList"></edit-userMoney> -->
    <!-- 获取url图片的弹框 -->
    <el-dialog title="会员详情" :close-on-click-modal="false" :visible.sync="visible">

    </el-dialog>
    <index-subordinate v-if="subordinateVisible" ref="subordinateRef"></index-subordinate>
  </div>
</template>

<script>
  import user from './read_user1'
  import AddOrUpdate from './usr-add-or-update'
  import vipRecord from './userviprecord'
  import editUserDetails from './edit-user-details'
  import editLoginPasswd from './edit-login-password'
  import editBankPasswd from './edit-bank-password'
  import editHierarchy from './edit-hierarchy'
  import batchEditHierarchy from './batch-edit-hierarchy'
  import editRiskHierarchy from './edit-risk-hierarchy'
  // import editUserMoney from './edit-user-money'
  import queryUserInfo from './query-userInfo'
  import json2csv from 'json2csv'
  import Clipboard from 'clipboard'
  import IndexSubordinate from './agent-subordinate'// 弹出框
  import dateutil from '@/utils/datechonse'
  import moment from 'moment'

import {
    isAuth
  } from '@/utils'

  export default {
    data () {
      return {

        copyUrl: '',
        subordinateVisible: true,
        pickerOptions2: {
          shortcuts: [{
            text: '今天',
            onClick (picker) {
              const end = dateutil.getToday().endtime
              const start = dateutil.getToday().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '昨天',
            onClick (picker) {
              const end = dateutil.getYesterday().endtime
              const start = dateutil.getYesterday().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '本周',
            onClick (picker) {
              const end = dateutil.getCurrWeekDays().endtime
              const start = dateutil.getCurrWeekDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '上周',
            onClick (picker) {
              const end = dateutil.getLastWeekDays().endtime
              const start = dateutil.getLastWeekDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '本月',
            onClick (picker) {
              const end = dateutil.getCurrMonthDays().endtime
              const start = dateutil.getCurrMonthDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '上月',
            onClick (picker) {
              const end = dateutil.getLastMonthDays().endtime
              const start = dateutil.getLastMonthDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去7天',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去30天',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去二月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 60)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去三月',
            onClick (picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }]
        },
        dataForm: {
         /* queryTime: [new Date(new Date(new Date().toLocaleDateString()).getTime()), new Date()], */
          // queryTime: [],
          queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime],
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
        options: [],
        showEdit: [], // 显示编辑框
        showBtn: [],
        totalNum: 0,
        pcNum: 0,
        androidNum: 0,
        iphoneNum: 0,
        otherNum: 0,
        visible: false,
        userTypeList: [],
        statusOptions: [
          {
            key: 1,
            label: '正常'
          },
          {
            key: 2,
            label: '拉黑'
          },
          {
            key: 3,
            label: '停压'
          },
          {
            key: 4,
            label: '冻结'
          },
          {
            key: 5,
            label: '风控'
          }
        ],
        deviceTypeOptions: [
          {
            key: 'pc',
            label: 'WEB'
          },
          {
            key: 'android',
            label: '安卓'
          },
          {
            key: 'IOS',
            label: 'IOS'
          }
        ],
        sortOptions: [{
          key: '',
          label: '按游戏中倒序'
        },
        {
          key: 'id',
          label: '按最近注册倒序'
        },
        {
          key: 'recharge_date',
          label: '按最近充值倒序'
        },
        {
          key: 'recharge_num',
          label: '按充值总额倒序'
        },
        {
          key: 'takemoney_date',
          label: '按最近取款倒序'
        },
        {
          key: 'takemoney_num',
          label: '按取款总额倒序'
        }

        ],
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        editUserDetailsVisible: false,
        vipRecordVisible: false,
        editLoginPasswdVisible: false,
        editBankPasswdVisible: false,
        editHierarchyVisible: false,
        batchEditHierarchyVisible: false,
        editRiskHierarchyVisible: false,
        readUser: false,
        // editUserMoneyVisible: false,
        queryUserInfoVisible: false
      }
    },
    components: {
      user,
      AddOrUpdate,
      editUserDetails,
      vipRecord,
      editLoginPasswd,
      editBankPasswd,
      editHierarchy,
      batchEditHierarchy,
      editRiskHierarchy,
      queryUserInfo,
      // editUserMoney,
      IndexSubordinate,
      json2csv,
      Clipboard
    },
  filters: {
		 rounding (value) {
		 return value.toFixed(2)
		 }
},
    activated () {
      if (isAuth('user:user:list')) {
        /* this.dataForm.queryTime = [dateutil.getToday().starttime, dateutil.getToday().endtime]; */
        // this.dataForm.queryTime = [];
        this.getDataList()
        this.getHierarchy()
        this.getUseType()
      } else {
        this.$router.push({
          path: '/home'
        })
      }
    },
    // 绑定回车事件
    created () {
      this.keyupSubmit()
    },
    methods: {
      onCopy (e) {
        if (this.dataForm.userAccount == '') {
          this.$message({
            message: '复制内容为空！',
            type: 'warning',
            duration: 1000
          })
        } else {
          this.$message({
            message: '复制成功！',
            type: 'success',
            duration: 1000
          })
        }
      },
      onError (e) {
        this.$message.error('复制失败')
      },
      recordingFlag (id) {
        // this.subordinateVisible =flag
        this.$refs.subordinateRef.init(id)
      },
      // 获取层级列表
      getHierarchy () {
        this.$http({
          url: this.$http.adornUrl(`/userhierarchy/userhierarchy/getHierarchy`),
          method: 'get',
          params: this.$http.adornParams({'hierarchyType': 0})
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.options = data.hierarchyList
          }
        })
      },
      // 获取会员类型列表
      getUseType () {
        this.$http({
          url: this.$http.adornUrl(`/user/user/getUseType`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.userTypeList = data.userTypeList
          }
        })
      },
      handleChange (index, row) {
        if (row.oldRemark != row.remark) {
          var remark = ''
          var userOperater = {}
          var remark = '备注'
          if (row.oldRemark) {
            remark += '由【' + row.oldRemark + '】修改为【'
          } else {
            remark += '由【空】修改为【'
          }
          if (row.remark) {
            remark += row.remark + '】'
          } else {
            remark += '空】'
          }
          userOperater.memberId = row.id
          userOperater.memberAccount = row.account
          userOperater.remark = remark
          var parm = {}
          parm.id = row.id
          parm.remark = row.remark
          parm.userOperater = userOperater
          this.updateRemark(parm)
        }
        row.showBtn = false
        row.showEdit = false
      },
      thirdMoneyHandle (id) {

      },
      // 点击编辑
      handleEdit (index, row) {
        row.showBtn = true
        row.showEdit = true
        this.$set(row, true)
      },
      read (user) {
        this.editUserDetailsVisible = true
        this.$nextTick(() => {
          this.$refs.editUserDetails.initEdit(user)
        })
      },
      readVipRecord (id) {
        this.vipRecordVisible = true
        this.$nextTick(() => {
          this.$refs.vipRecord.init(id)
        })
      },
      clearTime (time) {
        if (time != null) {
          this.dataForm.queryTime = []
        }
      },
      clearButton () {
        this.dataForm.queryTime = []
        this.dataForm.account = ''
        this.dataForm.id = ''
        this.dataForm.superiorsAccount = ''
        this.dataForm.superMan = ''
        this.dataForm.superiorsId = 0
        this.dataForm.status = ''
        this.dataForm.userName = ''
        this.dataForm.vipName = ''
        this.dataForm.yuEBaoMoney = 0
        this.dataForm.roomCard = 0
        this.dataForm.bankCard = ''
        this.dataForm.hierarchyId = null
        this.dataForm.registerIp = ''
        this.dataForm.loginIp = ''
        this.dataForm.deviceCode = ''
        this.dataForm.deviceType = ''
        this.dataForm.userType = ''
        this.dataForm.startTime = ''
        this.dataForm.endTime = ''
        this.dataForm.forbiddenEnable = ''
        this.dataForm.nobetEnable = ''
        this.dataForm.abnormalEnable = ''
        this.dataForm.sortOption = ''
        this.dataForm.orientation = 'desc'
        this.dataForm.frozenEnable = ''
        this.getDataList()
      },
      takeMoney (account) {
        this.$router.push({
          path: '/orderrecharge-enter',
          query: {
            account: account,
            name: 'OrderTakeMoney'
          }
        })
      },
      // 人工订单跳转跳转
      adminRechargeMoney (account) {
        this.$router.push({
          path: '/orderrecharge-admin',
          query: {
            account: account,
            name: 'adminhistory',
            operationType: 0
          }
        })
      },
      // 银行卡订单跳转
      bankRechargeMoney (account) {
        this.$router.push({
          path: '/orderrecharge-enter',
          query: {
            account: account,
            name: 'orderRecharge'
          }
        })
      },
      // 第三方订单跳转
      thirdRechargeMoney (account) {
        this.$router.push({
          path: '/orderrecharge-enter',
          query: {
            account: account,
            name: 'orderthirdRecharge'
          }
        })
      },
      // 交易记录跳转gameRecord
      transactionRecord (account) {
        this.$router.push({
          path: '/usertransactionrecord-usertransactionrecord',
          query: {
            account: account
          }
        })
      },
      // 游戏记录跳转
      gameRecord (account) {
        this.$router.push({
          path: '/account-usergamerecord',
          query: {
            account: account
          }
        })
      },
      exportCSV () {
        this.downLoadMix('会员名单.csv')
      },
      downLoadMix (title) {
        var startDate = ''
        var endDate = ''
        var timeArr = this.dataForm.queryTime
        if (timeArr != null && timeArr.length > 0) {
          startDate = timeArr[0]
          if (timeArr.length > 1) {
            endDate = timeArr[1]
          }
        }
        var forbiddenEnable = null
        var nobetEnable = null
        var frozenEnable = null
        var abnormalEnable = null
        if (this.dataForm.status == 1) {
          forbiddenEnable = false
          nobetEnable = false
          frozenEnable = false
          abnormalEnable = false
        } else if (this.dataForm.status == 2) {
          forbiddenEnable = true
        } else if (this.dataForm.status == 3) {
          nobetEnable = true
        } else if (this.dataForm.status == 4) {
          frozenEnable = true
        } else if (this.dataForm.status == 5) {
          abnormalEnable = true
        }
        this.$http({
          url: this.$http.adornUrl('/user/user/exportCSV'),
          method: 'post',
          responseType: 'arraybuffer',
          params: this.$http.adornParams({
            'account': this.dataForm.account,
            'id': this.dataForm.id,
            'superiorsAccount': this.dataForm.superiorsAccount,
            'status': this.dataForm.status,
            'startTime': startDate,
            'endTime': endDate,
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
          let blob = new Blob([data],
            {
              type: 'text/csv,charset=UTF-8'
            })
          let link = document.createElement('a')
          link.href = window.URL.createObjectURL(blob)
          link.download = title
          link.click()
          URL.revokeObjectURL(blob)
        })
      },
      downLoadMix2 (title) {
        var startDate = ''
        var endDate = ''
        var timeArr = this.dataForm.queryTime
        if (timeArr != null && timeArr.length > 0) {
          startDate = timeArr[0]
          if (timeArr.length > 1) {
            endDate = timeArr[1]
          }
        }
        var forbiddenEnable = null
        var nobetEnable = null
        var frozenEnable = null
        var abnormalEnable = null
        if (this.dataForm.status == 1) {
          forbiddenEnable = false
          nobetEnable = false
          frozenEnable = false
          abnormalEnable = false
        } else if (this.dataForm.status == 2) {
          forbiddenEnable = true
        } else if (this.dataForm.status == 3) {
          nobetEnable = true
        } else if (this.dataForm.status == 4) {
          frozenEnable = true
        } else if (this.dataForm.status == 5) {
          abnormalEnable = true
        }
        this.$http({
          timeout: 1000 * 300,
          url: this.$http.adornUrl('/user/user/exportCSVData'),
          method: 'get',
          params: this.$http.adornParams({
            'account': this.dataForm.account,
            'id': this.dataForm.id,
            'superiorsAccount': this.dataForm.superiorsAccount,
            'status': this.dataForm.status,
            'startTime': startDate,
            'endTime': endDate,
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
          var fields_ = [{
            label: 'ID',
            value: 'id'
          }, {
            label: '会员账号',
            value: 'account'
          }, {
            label: '真实姓名',
            value: 'userName'
          }, {
            label: '手机号码',
            value: 'phone'
          }, {
            label: '上级',
            value: 'superiorsAccount'
          }, {
            label: '会员等级',
            value: 'vipName'
          },
          {
            label: '推广层级',
            value: 'hierarchyName'
//             }, {
//               label: "风控层级",
//               value: "riskHierarchyName"
          }, {
            label: '用户类型',
            value: 'userType'
          }, {
            label: '会员状态',
            value: 'enable'
          }, {
            label: '注册IP',
            value: 'registerIp'
          }, {
            label: '登录IP',
            value: 'LoginIp'
          },
          {
            label: '设备码',
            value: 'deviceCode'
          }, {
            label: '余额宝',
            value: 'yuEBaoMoney'
          }, {
            label: '金币',
            value: 'coin'
          }, {
            label: '房卡数量',
            value: 'roomCard'
          }, {
            label: '备注',
            value: 'remake'
          }
          ]
          var opt = {
            fields: fields_,
            excelStrings: true
          }
          if (data && data.code === 200) {
            const result = json2csv.parse(data.dataList, opt)
            const csvContent = 'data:text/csv;charset=utf-8,\uFEFF' + result
            const link = document.createElement('a')
            link.href = encodeURI(csvContent)
            link.download = title
            document.body.appendChild(link) // Required for FF
            link.click()
            document.body.removeChild(link) // Required for FF
          }
        })
      },
      getDataList () {
        this.dataListLoading = true
        var startDate = null
        var endDate = null
        var timeArr = this.dataForm.queryTime
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format('YYYY-MM-DD HH:mm:ss')
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format('YYYY-MM-DD HH:mm:ss')
          }
        }
        var forbiddenEnable = null
        var nobetEnable = null
        var frozenEnable = null
        var abnormalEnable = null
        if (this.dataForm.status == 1) {
          forbiddenEnable = false
          nobetEnable = false
          frozenEnable = false
          abnormalEnable = false
        } else if (this.dataForm.status == 2) {
          forbiddenEnable = true
        } else if (this.dataForm.status == 3) {
          nobetEnable = true
        } else if (this.dataForm.status == 4) {
          frozenEnable = true
        } else if (this.dataForm.status == 5) {
          abnormalEnable = true
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
            'startTime': startDate,
            'endTime': endDate,
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
            'deviceCode': this.dataForm.deviceCode,
            'deviceType':this.dataForm.deviceType,
            'trial': '0'
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.totalNum = data.onlineData.totalNum
            this.dataList = data.page.list
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
      // 绑定回车键
      keyupSubmit () {
        document.onkeydown = e => {
          let _key = window.event.keyCode
          if (_key === 13) {
            this.getDataListQuery()
          }
        }
      },
      // 查询按钮事件
      getDataListQuery () {
        this.pageIndex = 1
        this.getDataList()
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
      //  查看会员信息
      queryUserInfoHandle (id) {
        this.queryUserInfoVisible = true
        this.$nextTick(() => {
          this.$refs.queryUserInfo.init(id)
        })
      },
      // 修改会员余额
      editUserMoney () {
// 				this.editUserMoneyVisible = true
// 				this.$nextTick(() => {
// 					this.$refs.editUserMoney.init(id)
// 				})
        this.visible = false
        this.$router.push({name: 'orderadministrator-orderadministrator'})
      }, // 修改登录密码
      editLoginPassword (id, userAccount) {
        this.editLoginPasswdVisible = true
        this.$nextTick(() => {
          this.$refs.editLoginPasswd.init(id, userAccount)
        })
      },
      //  修改取款密码
      editBankPassword (id, userAccount) {
        this.editBankPasswdVisible = true
        this.$nextTick(() => {
          this.$refs.editBankPasswd.init(id, userAccount)
        })
      },
      //  修改层级
      editHierarchy (id) {
        this.editHierarchyVisible = true
        this.$nextTick(() => {
          this.$refs.editHierarchy.init(id)
        })
      },
      //  修改风控层级
      editRiskHierarchy (id) {
        this.editRiskHierarchyVisible = true
        this.$nextTick(() => {
          this.$refs.editRiskHierarchy.init(id)
        })
      },

      // 修改备注
      updateRemark (param) {
        this.$http({
          url: this.$http.adornUrl(`/user/user/update`),
          method: 'post',
          data: this.$http.adornData(param)
        }).then(({
                   data
                 }) => {
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
      },
      // 下一级
      nextLevelHandle (id, account) {
        this.readUser = true
        this.$nextTick(() => {
          this.$refs.user.init(id, account)
        })
      }, // 上一级
      upperLevelHandle () {
        this.$refs['dataForm'].resetFields()
        this.$http({
          url: this.$http.adornUrl(`/user/user/surperinfo/${this.dataForm.superiorsId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.dataForm.superiorsId = data.user.superiorsId
            this.dataForm.account = data.dataForm.superMan
            this.dataForm.superMan = data.user.superiorsAccount
            this.getDataList()
          }
        })
      },
      // 批量修改会员推广层级
      batchUserHierarchy (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        var accounts = this.dataListSelections.map(item => {
          return item.account
        })
        this.batchEditHierarchyVisible = true
        this.$nextTick(() => {
          this.$refs.batchEditHierarchy.init(ids, accounts)
        })
      },
      // 批量标记测试会员
      batchMarkTestUser (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        if (ids.length == 0) {
          this.$alert('必须选择一个会员')
          return
        }
        if (ids.length != 1) {
          this.$alert('只能选择一个会员')
          return
        }
        var user = this.dataListSelections.map(item => {
          return item
        })
        console.log(user[0])
        if (user[0].userType == 'TEST') {
          this.$alert('已经是测试会员')
          return
        }
        this.$confirm(`确定将[id=${ids.join(',')}]的会员类型[${id ? '转成测试账号' : '批量转成测试账号'}]?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var userOperater = {}
          userOperater.memberId = user[0].id
          userOperater.memberAccount = user[0].account
          userOperater.remark = `将会员[${user[0].account}]的会员类型[转成测试账号]`
          this.$http({
            url: this.$http.adornUrl('/user/user/batchMarkTestUser'),
            method: 'post',
            data: this.$http.adornData({
              'ids': ids,
              'userOperater': userOperater
            })
// 							 this.$http.adornData(
// 							"ids": ids,
// 							'userOperater': userOperater
// 						)
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
      forbidden (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为停用'

        this.$confirm(`确定对[id=${id}]进行[停用]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editForbidden`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'forbiddenEnable': '1',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      relieveForbidden (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为解除停用'
        this.$confirm(`确定对[id=${id}]进行[解除停用]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editForbidden`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'forbiddenEnable': '0',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      nobet (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为停押'
        this.$confirm(`确定对[id=${id}]进行[停押]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editNobet`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'nobetEnable': '1',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      relieveNobet (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为解除停押'
        this.$confirm(`确定对[id=${id}]进行[解除停押]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editNobet`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'nobetEnable': '0',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      // 异常状态
      abnormal (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为异常状态吗'
        this.$confirm(`确定对[id=${id}]进行[异常]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editAbnormal`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'abnormalEnable': '1',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      // 正常状态
      normal (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为正常状态'
        this.$confirm(`确定对[id=${id}]进行[解除异常]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editAbnormal`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'abnormalEnable': '0',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      frozen (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为冻结'
        this.$confirm(`确定对[id=${id}]进行[冻结]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editFrozen`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'frozenEnable': '1',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      relieveFrozen (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '会员状态设置为解除冻结'
        this.$confirm(`确定对[id=${id}]进行[解除冻结]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/editFrozen`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'frozenEnable': '0',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      LoginOutMember (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '强制会员登出'
        this.$confirm(`确定对[id=${id}]进行[强制会员登出]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/LoginOutMember`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'frozenEnable': '0',
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      deleteMember (id, userAccount) {
        var userOperater = {}
        userOperater.memberId = id
        userOperater.memberAccount = userAccount
        userOperater.remark = '删除会员'
        this.$confirm(`确定对[id=${id}]进行[删除会员]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl(`/user/operation/deleteUser`),
            method: 'post',
            data: this.$http.adornData({
              'id': id,
              'userOperater': userOperater
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
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }).catch(() => {
        })
      },
      // 重置密码错误次数
      deletePassWordNum (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '重置密码' : '批量重置密码'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/user/user/deletePassWordNum'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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

      // 批量删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/user/user/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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
