<template>
  <div class="date-picker">
    <span class="demonstration">默认</span>
    <el-date-picker
      v-model="currentValue"
      type="daterange"
      align="right"
      unlink-panels
      range-separator="至"
      start-placeholder="开始日期"
      end-placeholder="结束日期"
      value-format="yyyy-MM-dd"
      @change="pickerDate"
      :editable='false'
      :picker-options="pickerOptions">
    </el-date-picker>
  </div>
</template>
<script>
import moment from 'moment'
export default {
  computed: {
    prevMonth () {
      // let curDate = (new Date()).getTime()
      // let month = (new Date()).getMonth()
      // let year = (new Date()).getFullYear()
      // let three = ((new Date()).getDate() + this.myMonth(year, month)) * 24 * 3600 * 1000
    }
  },
  data () {
    return {
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime())
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '这周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            const countDay = start.getDay() % 7
            start.setTime(start.getTime() - 3600 * 1000 * 24 * (countDay - 1))
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '上周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            const countDay = start.getDay() % 7
            start.setTime(start.getTime() - 3600 * 1000 * 24 * (countDay + 6))
            end.setTime(end.getTime() - 3600 * 1000 * 24 * countDay)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '这个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            const countDay = start.getDate()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * (countDay - 1))
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '上个月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
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
      currentValue: [moment((new Date()).setDate(1)).format('YYYY-MM-DD'), moment().format('YYYY-MM-DD')]
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
      // this.$emit('change', this.currentValue)
      this.$emit('change', this.currentValue)
    }

  },
  mounted () {
    this.$emit('change', this.currentValue)
  }
}
</script>
<style lang="scss" scoped>

</style>
