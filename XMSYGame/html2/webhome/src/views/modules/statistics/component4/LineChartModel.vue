<template>
  <div :class="className" :style="{height:height,width:width}"/>
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import { debounce } from '@/utils'
const colorArr=["#3888fa","#FFCC66","#8A2BE2","#A52A2A","#660099","#993399"]
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
      default: '697px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      chart: null,
      sidebarElm: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler (val) {
        this.setOptions(val)
      }
    }
  },
  mounted () {
    this.initChart()
    if (this.autoResize) {
      this.__resizeHandler = debounce(() => {
        if (this.chart) {
          this.chart.resize()
        }
      }, 100)
      window.addEventListener('resize', this.__resizeHandler)
    }

    // 监听侧边栏的变化
    this.sidebarElm = document.getElementsByClassName('sidebar-container')[0]
    this.sidebarElm && this.sidebarElm.addEventListener('transitionend', this.sidebarResizeHandler)
  },
  beforeDestroy () {
    if (!this.chart) {
      return
    }
    if (this.autoResize) {
      window.removeEventListener('resize', this.__resizeHandler)
    }

    this.sidebarElm && this.sidebarElm.removeEventListener('transitionend', this.sidebarResizeHandler)

    this.chart.dispose()
    this.chart = null
  },
  methods: {
    sidebarResizeHandler (e) {
      if (e.propertyName === 'width') {
        this.__resizeHandler()
      }
    },
		getBaseSeriesData(name,data,color){
			var seriesData= {
				name: name,
				data: data,
				smooth: true,
				type: 'line',
				label: {
					normal: {
						formatter: '{c}',
						show: true,
						position: 'top'
						// fontSize: 10
					}
				},
				itemStyle: {
					normal: {
						color: color,
						lineStyle: {
							color: color,
							width: 2
						},
						areaStyle: {
							color: "#f3f8ff"
						}
					}
				},
				animationDuration: 2800,
				animationEasing: 'quadraticOut'
			};
			return seriesData;
		},
    setOptions ({ xaxisData, yaxislData,legendData,title} = {}) {
			
			var seriesList=[];
			for(var i=0;i<yaxislData.length;i++){
				seriesList.push(this.getBaseSeriesData(yaxislData[i].name,yaxislData[i].list,colorArr[i]));
			}
			
			
      this.chart.setOption({
        xAxis: {
          type: 'category',
          data: xaxisData,
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        yAxis: [
          {
            type: 'value'
          }
        ],
        legend: {
          data:legendData
        },
        series:seriesList
      })
    },
    initChart () {
      this.chart = echarts.init(this.$el, 'macarons')
      this.setOptions(this.chartData)
    }
  }
}
</script>
