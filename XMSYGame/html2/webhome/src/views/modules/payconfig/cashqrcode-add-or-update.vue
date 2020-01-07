<template>
  <el-dialog :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-tooltip class="item" effect="dark" content="二维码的名称" placement="top-start">
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="dataForm.nickName" placeholder="昵称"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="二维码的账号" placement="top-start">
        <el-form-item label="账号" prop="account">
          <el-input v-model="dataForm.account" placeholder="账号"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="需要备注的内容" placement="top-start">
        <el-form-item label="备注" prop="remark">
          <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="是否启用(如果禁用则失效)" placement="top-start">
        <el-form-item label="状态" prop="enable">
          <el-radio-group v-model="dataForm.enable">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="上传图片的url(不能填写,图片上传完成时会自动生成路径)" placement="top-start">
        <el-form-item label="图片url" prop="url">
          <el-input v-model="dataForm.url" readonly placeholder="图片url"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="上传图片的md5(不能填写,图片上传完成时会自动生成路径)" placement="top-start">
        <el-form-item label="图片md5" prop="md5">
          <el-input v-model="dataForm.md5" readonly placeholder="图片md5"></el-input>
        </el-form-item>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="哪一种支付方式的二维码(微信,支付宝等)" placement="top-start">
        <el-form-item label="类型" prop="type">
          <el-select v-model="dataForm.type" clearable placeholder="类型" @change="selectGetType">
            <el-option v-for="item in incomeTypes" :key="item.code" :label="item.name" :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
      </el-tooltip>
      <!-- <el-form-item label="所属层级ID" prop="hierarchyId">
                <el-select v-model="dataForm.hierarchyId" multiple clearable placeholder="请选择层级">
                    <el-option v-for="item in options" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item> -->
    </el-form>
    <el-tooltip class="item" effect="dark" content="点击这片区域来上传图片(支付方式对应的图标)" placement="top-start">
      <el-upload drag :action="UploadUrl()" :before-upload="beforeUploadHandle" :on-success="successHandle" multiple
                 :file-list="fileList" :data="form" style="text-align: center;">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传图片一</em></div>
        <div class="el-upload__tip" slot="tip">只支持jpg、png、gif格式的图片！</div>
      </el-upload>
    </el-tooltip>
    <span slot="footer" class="dialog-footer">
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="dataFormSubmit()">确定</el-button>
		</span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        visible: false,
        options: [],
        incomeTypes: [],
        fileList: [],
        num: 0,
        show: false,
        form: null,
        successNum: 0,
        dataForm: {
          id: 0,
          nickName: '',
          account: '',
          remark: '',
          md5: '',
          enable: 1,
          url: '',
          type: '',
          hierarchyId: '',
          hierarchyIdStr: '',
        },
        dataRule: {
          nickName: [{
            required: true,
            message: '昵称不能为空',
            trigger: 'blur'
          }],
          account: [{
            required: true,
            message: '账号不能为空',
            trigger: 'blur'
          }],
          remark: [{
            required: true,
            message: '备注不能为空',
            trigger: 'blur'
          }],
          enable: [{
            required: true,
            message: '状态（0：禁用  1：启用）不能为空',
            trigger: 'blur'
          }],
          url: [{
            required: true,
            message: '图片url不能为空',
            trigger: 'blur'
          }],
          type: [{
            required: true,
            message: '类型不能为空',
            trigger: 'blur'
          }],
          hierarchyId: [{
            required: true,
            message: '层级id不能为空',
            trigger: 'blur'
          }]
        }
      }
    },
    methods: {
      init(id) {
        this.show = false
        this.visible = true
        this.fileList = []
        this.$http({
          url: this.$http.adornUrl(`/userhierarchy/userhierarchy/select`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.options = data.list
          }
        })
        this.$http({
          url: this.$http.adornUrl(`/sysdictionary/sysdictionary/select/qrcodeType`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({
                   data
                 }) => {
          if (data && data.code === 200) {
            this.incomeTypes = data.data
          }
        })
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/cashqrcode/cashqrcode/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({
                       data
                     }) => {
              if (data && data.code === 200) {
                this.dataForm.nickName = data.cashqrcode.nickName
                this.dataForm.account = data.cashqrcode.account
                this.dataForm.remark = data.cashqrcode.remark
                this.dataForm.enable = data.cashqrcode.enable == true ? 1 : 0
                this.dataForm.url = data.cashqrcode.url
                this.dataForm.md5 = data.cashqrcode.md5
                this.dataForm.type = data.cashqrcode.type.toString();
                var hierarchyIds = data.cashqrcode.hierarchyId.toString().split(",");
                var tempOptions = [];
                for (var i = 0; i < hierarchyIds.length; i++) {
                  tempOptions.push(parseInt(hierarchyIds[i]));
                }
                this.dataForm.hierarchyId = tempOptions
              }
            })
          }
        })
      },

      //下拉框选中事件
      selectGetType(vId) { //这个vId也就是value值
        let obj = {};
        obj = this.incomeTypes.find((item) => { //这里的userList就是上面遍历的数据源
          return item.code === vId; //筛选出匹配数据
        });
        this.dataForm.typeStr = obj.name
      },


      UploadUrl: function () {
        this.url = this.$http.adornUrl(`/webhomeenclosure/webhomeenclosure/uploadFile?token=${this.$cookie.get('token')}`)
        return this.url;
      },
      // 照片上传之前
      beforeUploadHandle(file) {
        this.num++
      },
      // 照片上传成功
      successHandle(response, file, fileList, type) {
        this.type = type
        this.fileList = fileList
        this.successNum++
        if (response && response.code === 200) {
          if (this.num === this.successNum) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 1500,
            })
          }
          console.log(response);
          console.log("==========response.url========" + response.url);
          this.dataForm.url = response.url
          this.dataForm.md5 = response.Md5Val
          console.log("==========this.dataForm.url========" + this.dataForm.url);
          this.show = true
        } else {
          this.$message.error(response.msg)
        }
      },
      // 表单提交
      dataFormSubmit() {
        //层级集合
        var hierarchyOptions = this.options;
        //选中的层级
        var hierarchyIds = this.dataForm.hierarchyId.toString().split(",");
        console.log("==========hierarchyIds========" + hierarchyIds);
        //选中的层级名称集合
        var hierarchyIdStr = [];
        for (var i = 0; i < hierarchyOptions.length; i++) {
          for (var j = 0; j < hierarchyIds.length; j++) {
            console.log("==========hierarchyOptions[i]========" + hierarchyOptions[i]);
            if (hierarchyIds[j] == hierarchyOptions[i].id) {
              hierarchyIdStr.push(hierarchyOptions[i].name)
            }
          }
        }
        console.log("==========this.dataForm.hierarchyId========" + this.dataForm.hierarchyId);
        console.log("==========hierarchyIdStr========" + hierarchyIdStr);
        //收款类型
        var incomeTypes = this.incomeTypes;
        //选中的收款类型
        var typeStr;
        for (var i = 0; i < incomeTypes.length; i++) {
          if (incomeTypes[i].code == this.dataForm.type) {
            typeStr = incomeTypes[i].name
          }
        }
        console.log("==========this.dataForm.type========" + this.dataForm.type);
        console.log("==========typeStr========" + typeStr);
        console.log("==========url========" + this.dataForm.url);
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/cashqrcode/cashqrcode/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'nickName': this.dataForm.nickName,
                'account': this.dataForm.account,
                'remark': this.dataForm.remark,
                'enable': this.dataForm.enable,
                'url': this.dataForm.url,
                'md5': this.dataForm.md5,
                'type': this.dataForm.type,
                'hierarchyId': this.dataForm.hierarchyId.toString(),
                'hierarchyIdStr': hierarchyIdStr.join(","),
                'typeStr': typeStr,
              })
            }).then(({
                       data
                     }) => {
              if (data && data.code === 200) {
                this.$message({
                  message: '操作成功',
                  // '<div style="margin: 10px 0 0 30px;text-align:center;font-size:12px;">' + '操作成功' +
                  // '</div>',
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
