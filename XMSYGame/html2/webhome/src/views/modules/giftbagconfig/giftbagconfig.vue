<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @submit.native.prevent @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.exchangeCode" placeholder="兑换码" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.exchangeAccount" placeholder="指定兑换账号" clearable></el-input>
      </el-form-item>
      <el-date-picker v-model="dataForm.queryTime" type="datetimerange" align="right" unlink-panels range-separator="至"
                      start-placeholder="生效时间" end-placeholder="结束时间" :picker-options="pickerOptions2"
                      value-format="yyyy-MM-dd HH:mm:ss"  :default-time="['00:00:00', '23:59:59']">
      </el-date-picker>
      <el-form-item>
        <el-button @click="getDataListQuery()">查询</el-button>
        <el-button v-if="isAuth('giftbagconfig:giftbagconfig:save')" type="primary" @click="addOrUpdateHandle()">新增
        </el-button>
        <!-- <el-button v-if="isAuth('giftbagconfig:giftbagconfig:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button> -->
      </el-form-item>
      <!--<el-upload
        class="upload-demo"

      :limit="1"
      action="submitUpload()"
      multiple
      accept="xls/xlsx"
      :file-list="fileList"
      :before-upload="beforeUpload">
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
      <!--<a href="./static/moban.xlsx" rel="external nofollow" download="模板">-->
      <!--<el-button size="small" type="success">下载模板</el-button>-->
      <!--</a>-->
      <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button> -->
     <el-form-item>
       <el-button  size="small" type="primary" @click="exportMould()">下载Excel模板</el-button>
     </el-form-item>
      <el-form-item>
        <el-tooltip class="item" effect="dark" content="上传文件" placement="top">
          <el-upload class="upload-demo" :action="UploadUrl()" :before-upload="beforeUploadHandle"
                     :on-success="successHandle"
                     multiple
                     :file-list="fileList" :show-file-list="false" :data="form" style="text-align: center;">
            <el-button slot="trigger" size="small" type="primary">Excel导入</el-button>
            <div slot="tip" class="el-upload__tip" style="float: right">只能上传excel文件，且不超过5MB</div>
            <div slot="tip" class="el-upload-list__item-name" style="float: right">{{fileName}}</div>
          </el-upload>
        </el-tooltip>
      </el-form-item>
    </el-form>
    <!-- <span slot="footer" class="dialog-footer">
       <el-button @click="visible = false">取消</el-button>
       <el-button type="primary" @click="submitUpload()">确定</el-button>
       </span>-->
    <el-table :data="dataList" border v-loading="dataListLoading" @selection-change="selectionChangeHandle"
              style="width: 100%;">
      <el-table-column prop="id" header-align="center" align="center" label="id">
      </el-table-column>
      <el-table-column prop="exchangeCode" header-align="center" align="center" label="兑换码">
      </el-table-column>
      <el-table-column prop="password" header-align="center" align="center" label="兑换密码">
      </el-table-column>
      <el-table-column prop="acountMoney" header-align="center" align="center" label="兑换额度">
        <template slot-scope="scope">
          <div>{{scope.row.acountMoney/100}}</div>
        </template>
      </el-table-column>
      <el-table-column prop="exchange" header-align="center" align="center" label="兑换码状态">
        <template slot-scope="scope">
          <div v-if="scope.row.exchange">使用中</div>
          <div v-else>已关闭</div>
        </template>
      </el-table-column>
      <el-table-column prop="exchangeTotalNum" header-align="center" align="center" label="总兑换次数">
      </el-table-column>
      <el-table-column prop="exchangeNum" header-align="center" align="center" label="剩余兑换次数">
        <template slot-scope="scope">
          <el-button style="margin-right: 3px;" size="mini" type="text" title="查看存取记录"
                     @click="readgiftconfigrecord(scope.row.exchangeCode)">{{scope.row.exchangeNum}}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="exchangeAccount" header-align="center" align="center" label="指定兑换账号">
      </el-table-column>
      <el-table-column prop="limitTimesUser" header-align="center" align="center" label="单个用户限制次数">
        <template slot-scope="scope">
          <div v-if="scope.row.limitTimesUser">多次</div>
          <div v-else>单次</div>
        </template>
      </el-table-column>
      <el-table-column prop="period" header-align="center" align="center" label="周期(天)">
        <template slot-scope="scope">
          <div v-if="scope.row.limitTimesUser">{{scope.row.period}}</div>
          <div v-else></div>
        </template>
      </el-table-column>
      <el-table-column prop="receiveTimes" header-align="center" align="center" label="可以领取次数">
        <template slot-scope="scope">
          <div v-if="scope.row.limitTimesUser">{{scope.row.receiveTimes}}</div>
          <div v-else></div>
        </template>
      </el-table-column>
      <el-table-column prop="startTime" header-align="center" align="center" label="生效时间">
      </el-table-column>
      <el-table-column prop="endTime" header-align="center" align="center" label="结束时间">
        <template slot-scope="scope">
          <div v-if="scope.row.endTime==null">永久有效</div>
          <div v-else>{{scope.row.endTime}}</div>
        </template>
      </el-table-column>
      <el-table-column fixed="right" header-align="center" align="center" width="150" label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)" :disabled="!scope.row.exchange">
            修改
            <!--已经关闭不可修改-->
          </el-button>
          <!--<el-button type="text" size="small" @click="deleteHandle(scope.row.id)" :disabled="!scope.row.exchange">删除</el-button>-->
          <el-button type="text" size="small" @click="updateExchange(scope.row.id)" :disabled="!scope.row.exchange">关闭
            <!--已经关闭不可再修改-->
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination @size-change="sizeChangeHandle" @current-change="currentChangeHandle" :current-page="pageIndex"
                   :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" :total="totalPage"
                   layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
    <gift-config-record v-if="readGiftConfigRecordVisbel" ref="giftConfigRecord"
                        @refreshDataList="getDataList"></gift-config-record>
  </div>
</template>

<script>
  import AddOrUpdate from './giftbagconfig-add-or-update'
  import giftConfigRecord from './read_giftbagexchangerecord'
  import dateutil from '@/utils/datechonse'
  import moment from 'moment'

  export default {
    data() {
      return {
        fileList: [],
        fileName: '',
        num: 0,
        successNum: 0,
        form: null,
        pickerOptions2: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              const end = dateutil.getToday().endtime
              const start = dateutil.getToday().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const end = dateutil.getYesterday().endtime
              const start = dateutil.getYesterday().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '本周',
            onClick(picker) {
              const end = dateutil.getCurrWeekDays().endtime
              const start = dateutil.getCurrWeekDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '上周',
            onClick(picker) {
              const end = dateutil.getLastWeekDays().endtime
              const start = dateutil.getLastWeekDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '本月',
            onClick(picker) {
              const end = dateutil.getCurrMonthDays().endtime
              const start = dateutil.getCurrMonthDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '上月',
            onClick(picker) {
              const end = dateutil.getLastMonthDays().endtime
              const start = dateutil.getLastMonthDays().starttime
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去7天',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去30天',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去二月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 60)
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '过去三月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }]
        },
        dataForm: {
          queryTime: [dateutil.getToday().starttime, dateutil.getToday().endtime],
          exchangeCode: '',
          exchangeAccount: ''
        },
        readGiftConfigRecordVisbel: false,
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate,
      giftConfigRecord
    },
    activated() {
      this.getDataList()
    },
    created() {
      this.keyupSubmit()
    },
    methods: {
      // 获取数据列表
      getDataList() {
        var startDate = null;
        var endDate = null;
        var timeArr = this.dataForm.queryTime;
        if (timeArr != null && timeArr.length > 0) {
          startDate = moment(timeArr[0]).format("YYYY-MM-DD HH:mm:ss");
          if (timeArr.length > 1) {
            endDate = moment(timeArr[1]).format("YYYY-MM-DD HH:mm:ss");
          }
        }
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/giftbagconfig/giftbagconfig/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'exchangeCode': this.dataForm.exchangeCode,
            'exchangeAccount': this.dataForm.exchangeAccount,
            'sTime': startDate,
            'eTime': endDate
          })
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 下载模板start
      exportMould () {
        this.downLoadMix('新增兑换码模板.xlsx')
      },
      downLoadMix (title) {
        this.$http({
          url: this.$http.adornUrl('/excel/excel/ExportMould'),
          method: 'get',
          responseType: 'arraybuffer',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          let blob = new Blob([data],
            {
              type: 'text/xlsx,charset=UTF-8、'
            })
          let link = document.createElement('a')
          link.href = window.URL.createObjectURL(blob)
          link.download = title
          link.click()
          URL.revokeObjectURL(blob)
        })
      },
      // 下载模板end

      // 弹窗出礼包兑换记录
      readgiftconfigrecord(exchangeCode) {
        this.readGiftConfigRecordVisbel = true
        this.$nextTick(() => {
          this.$refs.giftConfigRecord.init(exchangeCode)
        })
      },
      //绑定回车事件
      keyupSubmit() {
        document.onkeydown = e => {
          let _key = window.event.keyCode;
          if (_key === 13) {
            this.getDataListQuery()
          }
        }
      },
      //查询
      getDataListQuery() {
        this.pageIndex = 1;
        this.getDataList();
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
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      UploadUrl: function () {
        this.url = this.$http.adornUrl(`/excel/excel/doImport?token=${this.$cookie.get('token')}`)
        return this.url
      },
      // 照片上传之前
      beforeUploadHandle(file) {

        this.num++
        this.files = file;
        const extension = file.name.split('.')[1] === 'xls'
        const extension2 = file.name.split('.')[1] === 'xlsx'
        const isLt2M = file.size / 1024 / 1024 < 5
        if (!extension && !extension2) {
          this.$message.warning('上传模板只能是 xls、xlsx格式!')
          return
        }
        if (!isLt2M) {
          this.$message.warning('上传模板大小不能超过 5MB!')
          return
        }
        this.fileName = file.name;
        return true // 返回false不会自动上传
      },
      // 文件上传成功
      successHandle(response, file, fileList, type) {
        //this.type = type;

        this.fileList = fileList;
        this.successNum++;
        if (response && response.data.code === 200) {
          if (this.num === this.successNum) {
            this.$message({
              message: response.data.msg,
              type: 'success',
              duration: 5000
            })
            this.getDataList()
          }
        } else {
          this.$alert('<html><text style="color: red">' + response.data.msg + '</text></html>', '错误提示', {
            dangerouslyUseHTMLString: true
          })
        }
      },
      //关闭操作
      updateExchange(id) {
        this.$confirm(`确定对[id=${id}]进行关闭操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/giftbagconfig/giftbagconfig/updateExchange'),
            method: 'post',
            data: this.$http.adornData(id, false)
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
        })
      },
      // 删除
      deleteHandle(id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/giftbagconfig/giftbagconfig/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
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
        })
      }
    }
  }
</script>
