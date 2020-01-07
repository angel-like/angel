<template>
  <div class="date-picker">
    <span class="demonstration">默认</span>
    <el-date-picker
      v-model="currentValue"
      type="month"
      align="right"
      @change="pickerDate"
      format="yyyy-MM"
      value-format="yyyy-MM"
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
        disabledDate (time) {
          let curDate = (new Date()).getTime()
          let month = (new Date()).getMonth()
          let year = (new Date()).getFullYear()
          var myMonth = new Date(year, month, 0)
          let three = ((new Date()).getDate() + myMonth.getDate()) * 24 * 3600 * 1000
          let threeMonths = curDate - three
          return time.getTime() > Date.now() || time.getTime() < threeMonths
        }
      },
      currentValue: moment().format('YYYY-MM')
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
      // console.log(this.pickerOptions.disabledDate)
    },
    myMonth (year, month) {
      var d = new Date(year, month, 0)
      return d.getDate()
    }
  },
  mounted () {
    this.$emit('change', this.currentValue)
  }
}
</script>
<style lang="scss" scoped>

</style>
