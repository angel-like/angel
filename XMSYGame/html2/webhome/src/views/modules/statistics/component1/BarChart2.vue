<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
  import echarts from 'echarts'

  require('echarts/theme/vintage') // echarts theme
  import {debounce} from '@/utils'

  const animationDuration = 2000

  export default {
    props: {
      className: {
        type: String,
        default: 'chart'
      },
      width: {
        type: String,
        default: '80%'
      },
      height: {
        type: String,
        default: '600px'
      },
      chartData: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        chart: null,
        seriesData: [],
        legendData: [],
        xAxisData: [],
        maxNumber: 10000

      }
    },
    watch: {
      chartData: {
        deep: true,
        handler(val) {
          this.setOptions(val)
        }
      }
    },
    mounted() {
      this.initChart()
      this.__resizeHandler = debounce(() => {
        if (this.chart) {
          this.chart.resize()
        }
      }, 100)
      window.addEventListener('resize', this.__resizeHandler)
    },
    beforeDestroy() {
      if (!this.chart) {
        return
      }
      window.removeEventListener('resize', this.__resizeHandler)
      this.chart.dispose()
      this.chart = null
    },
    methods: {
      setOptions({maxNumber, xAxisData, legendData,seriesData} = {}) {
        this.chart.setOption({

          legend: {
            itemWidth: 50,
            itemHeight: 30,
            data: legendData,
            textStyle: {
              fontSize: 25
            }
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: { // 坐标轴指示器，坐标轴触发有效
              type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          grid: {
            top: '6%',
            left: '2%',
            right: '2%',
            bottom: '5%',
            containLabel: true
          },
          xAxis: [{
            type: 'category',
            data: xAxisData,
            axisTick: {
              alignWithLabel: true
            },
            axisLabel: {
              show: true,
              textStyle: {
                fontSize: '22',//字体大小
              }
            },
            axisLine: {
              lineStyle: {
                color: 'black'
              }
            }
          }],
          yAxis: [{
            type: 'value',
            min: 0,
            max: maxNumber,
            axisTick: {
              show: false
            },
            axisLabel: {
              show: true,
              textStyle: {
                fontSize: '22',//字体大小
              }
            },
            axisLine: {
              lineStyle: {
                color: 'black'
              }
            }
          }],
          series: seriesData
        })
      },
      initChart() {
        this.chart = echarts.init(this.$el, 'macarons')
        this.setOptions(this.chartData)
      }
    }
  }
</script>
