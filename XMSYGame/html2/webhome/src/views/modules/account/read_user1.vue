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
        <span style="padding-right: 15px; color: red;">余额宝：{{countForm.yuEBaoMoneyCount/100}}</span>
        <span style="padding-right: 15px; color: red;">总取款：{{countForm.takeAmountCount}}</span>
        <span style="padding-right: 15px; color: red;">总充值：{{countForm.rechargeAmountCount}}</span>
        <span style="padding-right: 15px; color: red;">总优惠：{{countForm.discountAmountCount}}</span>
        <span style="padding-right: 115px; color: red;">总返佣：{{countForm.commissionCount}}</span>
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
              <el-dropdown>
                <a style="cursor:pointer ">总存款:{{scope.row.rechargeAmount}}</a>
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
            <div>当前优惠:{{scope.row.lastDiscountAmount}}</div>
            <div>总优惠:{{scope.row.discountAmount}}</div>
            <span>总累计存款:</span>
            <span v-if="scope.row.rechargeAmount+ scope.row.discountAmount>0">{{(scope.row.rechargeAmount + scope.row.discountAmount)}}</span>
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
            <div>当前打码量:{{scope.row.userValidBet}}</div>
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


      <!-- <el-table :data="dataList" border v-loading="dataListLoading" @cell-click="count"
                style="width: 100%;">

        <el-table-column prop="id" fixed="left" header-align="center" align="center" label="会员id">
        </el-table-column>
        <table-tree-column prop="account" header-align="center"  treeKey="id" align="center"
                           label="会员账号">
        </table-tree-column>
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
            <el-button size="mini" type="text"  @click="deleteHandle(scope.row.id)"  >查看详情</el-button>
          </template>
        </el-table-column>
        余额功能隐藏
              <el-table-column prop="money" header-align="center" align="center" label="余额">
                  <template slot-scope="scope">
                      <div v-if="isAuth('usertransactionrecord:usertransactionrecord:list')">
                          <a  @click="transactionRecord(scope.row.account)" title="查看交易记录" style="cursor:pointer ">{{scope.row.money}}</a>
                      </div>
                      <div v-if="!isAuth('usertransactionrecord:usertransactionrecord:list')">
                          {{scope.row.money}}
                      </div>
                  </template>
              </el-table-column>
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
          	<editable-cell slot-scope="{row}"
              :can-edit="editModeEnabled"
              v-model="row.name">
       <span slot="content">{{row.name}}</span>
      </editable-cell>
          <template slot-scope="scope">
                      <el-input size="small" v-if="scope.row.showEdit" v-model="scope.row.remark" placeholder="请输入备注" @change="handleChange(scope.$index, scope.row)"></el-input>
                      <span v-else>{{scope.row.remark}}</span>
                      <el-button size="mini" v-if="!scope.row.showBtn" title="编辑" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                  </template>
        </el-table-column>

      </el-table> -->


      <!--<index-subordinate v-if="subordinateVisible" ref="subordinateRef"></index-subordinate>-->
    </div>
  </el-dialog>
</template>

<script>

  import TableTreeColumn from '@/components/table-tree-column'
  import {treeDataTranslate} from '@/utils'

  export default {
    components: {TableTreeColumn},
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
        this.$http({
          url: this.$http.adornUrl('/user/user/treeList'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'account': this.dataForm.account,
            'id': this.dataForm.id,
            'superiorsAccount': this.dataForm.superiorsAccount,
            'status': this.dataForm.status,
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

            this.dataList = treeDataTranslate(data.list, 'id')

            this.dataListLoading = false
          } else {
            this.dataList = []
          }
          this.dataListLoading = false
        })
      },
      getCount (id, account) {
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
            if(data.count.yuEBaoMoneyCount==null){
              this.countForm.yuEBaoMoneyCount=0
            }
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
      count (row, column) {
        var property = column.property
        if(property=='account'){
         this.getCount(row.id,row.account);
        }
      },
      init(id, account) {

        this.visible = true
        if (id != null) {
          this.dataForm.superiorsId = id;
          this.dataForm.superMan = account;
          this.dataForm.account = '';
          this.getDataList();
          this.getCount(id, account);
        }

      },


      //下一级
      nextLevelHandle(id, account) {
        //this.$refs['dataForm'].resetFields();
        this.dataForm.superiorsId = id;
        this.dataForm.superMan = account;
        this.dataForm.account = '';
        this.getDataList();
        this.getCount(id, account);
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
            this.getCount(this.dataForm.superiorsId, this.dataForm.superMan)
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
