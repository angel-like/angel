#!/bin/bash

# 获取Java进程ID
JAVA_PID=$(ps -ef | grep 'java' | grep 'your-springboot-app-name' | awk '{print $2}')
if [ -z "$JAVA_PID" ]; then
    echo "没有找到Java进程，请确保应用正在运行。"
    #exit 1 通常表示“脚本执行出现了错误”，它是最常见的非零退出码，适用于参数校验、文件检查、业务逻辑失败等场景
    exit 1
fi

# 发送优雅停机信号
echo "准备优雅地关闭Spring Boot应用..."
kill -15 $JAVA_PID

# 设置最大等待时间（单位：秒）
MAX_WAIT_TIME=30
elapsed_time=0

# 等待Java进程结束 > /dev/null: 这部分将标准输出重定向到 /dev/null，即丢弃标准输出的内容，不显示在终端上。
while kill -0 $JAVA_PID > /dev/null 2>&1; do
    echo "等待Java进程结束... 已等待 $elapsed_time 秒"
    sleep 1
    elapsed_time=$((elapsed_time + 1))

    # 如果超过最大等待时间，则强制终止进程
    if [ $elapsed_time -ge $MAX_WAIT_TIME ]; then
        echo "等待超时，强制终止Java进程..."
        kill -9 $JAVA_PID
        break
    fi
done

echo "Spring Boot应用已成功关闭。"