#!/bin/bash

# 文件和目录变量
LOCAL_FILE="file.txt"
HDFS_DIR="/test_dir"
HDFS_FILE_PATH="$HDFS_DIR/$LOCAL_FILE"

# 创建本地文件并写入当前时间
echo "$(date)" > $LOCAL_FILE

# 确保HDFS目录存在
hdfs dfs -mkdir -p $HDFS_DIR

# 上传文件到HDFS
echo "正在上传文件到HDFS..."
hdfs dfs -copyFromLocal -f $LOCAL_FILE $HDFS_FILE_PATH

# SSH到DataNode1并下载文件，打印内容
echo "SSH到DataNode1并下载文件..."
ssh DataNode1 "
    rm $LOCAL_FILE
    hdfs dfs -get $HDFS_FILE_PATH $LOCAL_FILE
    echo 'DataNode1上该文件的内容:'
    cat $LOCAL_FILE
    exit
"

# 更新文件内容
echo "添加'hello world'到文件..."
echo "hello world" >> $LOCAL_FILE
hdfs dfs -appendToFile $LOCAL_FILE $HDFS_FILE_PATH

# 本地验证文件更新
echo "本地验证文件更新..."
hdfs dfs -cat $HDFS_FILE_PATH

# SSH到DataNode2验证文件更新
echo "SSH到DataNode2和验证文件更新..."
ssh DataNode2 "
    hdfs dfs -cat $HDFS_FILE_PATH
    exit
"
