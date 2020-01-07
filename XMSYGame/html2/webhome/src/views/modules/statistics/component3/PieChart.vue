<template>
  <div :class="className" :style="{height:height,width:width,}"/>
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import { debounce } from '@/utils'

export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '80%'
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      chart: null
    }
  },
  mounted () {
    this.initChart()
    this.__resizeHandler = debounce(() => {
      if (this.chart) {
        this.chart.resize()
      }
    }, 100)
    window.addEventListener('resize', this.__resizeHandler)
  },
  beforeDestroy () {
    if (!this.chart) {
      return
    }
    window.removeEventListener('resize', this.__resizeHandler)
    this.chart.dispose()
    this.chart = null
  },
  watch: {
    chartData: {
      deep: true,
      handler (val) {
        this.setOptions(val)
        console.log('_______')
        console.log(val)
      }
    }
  },
  methods: {
    setOptions ({ title, expectedData, actualData } = {}) {
      this.chart.setOption({
        title: {
          text: title
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          left: 'center',
          bottom: 40,
          data: expectedData
        },
        calculable: true,
        series: [
          {
            name: 'WEEKLY WRITE ARTICLES',
            type: 'pie',
            coordinateSystem: 'calendar',
            selectedMode: 'single',
            radius: [0, '80%'],
            label: {
              normal: {
                formatter: '{b}:{c}',
                position: 'top',
                fontSize: 20
              }
            },
            // roseType: 'radius',
            // radius: [15, 95],
            center: ['50%', '45%'],
            data: actualData,
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }
        ]
      })
    },
    // initChart () {
    //   this.chart = echarts.init(this.$el, 'macarons')
    //   this.setOptions(this.chartData)
    // },
    initChart () {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    }
  }
}
</script>
