LOCAL_FILE="bigfile.txt"
HDFS_DIR="/test_dir"
HDFS_FILE_PATH="$HDFS_DIR/$LOCAL_FILE"

# 上传文件到HDFS
echo "正在上传大文件到HDFS..."
hdfs dfs -copyFromLocal -f $LOCAL_FILE $HDFS_FILE_PATH

# 展示文件分块和副本信息
echo "展示文件分块和副本信息..."
hdfs fsck $HDFS_FILE_PATH -files -blocks