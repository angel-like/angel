<template>
  <div class="date-picker">
    <span class="demonstration">默认</span>
    <el-date-picker
      v-model="currentValue"
      type="datetimerange"
      align="right"
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      value-format="yyyy-MM-dd HH:mm:ss"
      unlink-panels
      @change="pickerDate"
      :editable='false'
      :default-time="['00:00:00', '23:59:59']"
      :picker-options="pickerOptions">
    </el-date-picker>
  </div>
</template>
<script>
import moment from 'moment'
export default {
  computed: {
  },
  data () {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            console.log(picker)
            const end = new Date()
            const start = new Date()
            start.setTime(new Date(new Date().setHours(0, 0, 0, 0)))
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(new Date().setHours(0, 0, 0, 0) - 3600 * 1000 * 24)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '这周',
          onClick (picker) {
            const end = new Date()
            const start = new Date(new Date().setHours(0, 0, 0, 0))
            const countDay = start.getDay() % 7
            start.setTime(start.getTime() - 3600 * 1000 * 24 * (countDay - 1))
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '上周',
          onClick (picker) {
            const end = new Date(new Date().setHours(23, 59, 59, 59))
            const start = new Date(new Date().setHours(0, 0, 0, 0))
            const countDay = start.getDay() % 7
            start.setTime(start.getTime() - 3600 * 1000 * 24 * (countDay + 6))
            end.setTime(end.getTime() - 3600 * 1000 * 24 * countDay)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '这个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date(new Date().setHours(0, 0, 0, 0))
            const countDay = start.getDate()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * (countDay - 1))
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '上个月',
          onClick (picker) {
            const end = new Date(new Date().setHours(23, 59, 59, 59))
            const start = new Date(new Date().setHours(0, 0, 0, 0))
            const countDay = start.getDate()
            var startyear = start.getFullYear()
            var startmonth = end.getMonth()
            var startd = (new Date(startyear, startmonth, 0)).getDate()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * (countDay + startd - 1))
            end.setTime(end.getTime() - 3600 * 1000 * 24 * countDay)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      currentValue: [
        moment((new Date()).setDate(1)).format('YYYY-MM-DD') + '00:00:00',
        moment().format('YYYY-MM-DD h:mm:ss')]
    }
  },
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    value: [String, Number, Array]
  },
  watch: {
    value (newValue) {
      this.value2 = newValue
    }

  },

  methods: {
    pickerDate (newValue) {
      this.$emit('change', this.currentValue)
    },
    pickerLimit () {
    },
    myMonth (year, month) {
      var d = new Date(year, month, 0)
      return d.getDate()
    }
  },
  mounted () {
    this.$emit('change', this.currentValue)
  },
  created () {

  }
}
</script>
<style lang="scss" scoped>

</style>
