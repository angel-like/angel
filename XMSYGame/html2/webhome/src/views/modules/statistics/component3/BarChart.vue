<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import { debounce } from '@/utils'

const animationDuration = 2000

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
      default: (window.innerHeight)+'px'
    }
  },
  data () {
    return {
      chart: null,
			listName:[],
			listNum:[]
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
  activated () {
  	this.getInsertUserNumber()
  },
  methods: {
    initChart (listName,listNum) {
      this.chart = echarts.init(this.$el, 'macarons')

      this.chart.setOption({
        title: {
          text: '七天新增会员数量'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        grid: {
          top: 60,
          left: '2%',
          right: '2%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          data: listName,
          axisTick: {
            alignWithLabel: true
          }
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          }
        }],
        series: [
          {
            name: '人数',
            type: 'bar',
            stack: '总量',
            label: {
              normal: {
                show: true,
                formatter: '{c}',
                position: 'inside'
              }
            },
            data: listNum
          }]
      })
    },
			// 获取数据列表
			getInsertUserNumber () {
				this.$http({
					url: this.$http.adornUrl('/userstatistics/userstatistics/getInsertUserNumber'),
					method: 'get',
					params: this.$http.adornParams({
					})
				}).then(({data}) => {
					if (data && data.code === 200) {
						this.listName = []
						this.listNum = []
						data.list.forEach(element=>{
							this.listName.push(element.time)
							this.listNum.push(String(element.numbers))
						})
						this.initChart(this.listName,this.listNum)
					} 
				})
			}
		
  }
}
</script>
