<template>
    <div class="recommend-container">
        <!-- 推荐内容列表 -->
        <div class="recommend-list">
            <div v-for="(item, index) in recommendItems" :key="index" class="recommend-item">
                <div class="item-header">
                    <img :src="item.avatar" alt="用户头像" class="user-avatar">
                    <span class="username">{{ item.username }}</span>
                </div>
                <div class="item-content">
                    <h3>{{ item.title }}</h3>
                    <p>{{ item.content }}</p>
                </div>
                <div class="item-footer">
                    <span><i class="el-icon-thumb"></i> {{ item.likes }}</span>
                    <span><i class="el-icon-chat-dot-round"></i> {{ item.comments }}</span>
                    <span><i class="el-icon-star-off"></i> {{ item.favorites }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'RecommendContent',
    data() {
        return {
            recommendItems: [
                {
                    username: '用户1',
                    avatar: 'https://via.placeholder.com/40',
                    title: '推荐内容标题1',
                    content: '这是推荐内容的详细描述...',
                    likes: 123,
                    comments: 45,
                    favorites: 67
                },
                // 可以添加更多测试数据
            ]
        }
    }
}
</script>

<style scoped>
.recommend-container {
    padding: 20px;
}

.recommend-list {
    max-width: 800px;
    margin: 0 auto;
}

.recommend-item {
    background: #fff;
    border-radius: 8px;
    padding: 15px;
    margin-bottom: 20px;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.item-header {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

.user-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
}

.username {
    font-weight: bold;
    color: #333;
}

.item-content {
    margin: 10px 0;
}

.item-content h3 {
    margin-bottom: 8px;
    font-size: 18px;
}

.item-content p {
    color: #666;
    line-height: 1.5;
}

.item-footer {
    display: flex;
    gap: 20px;
    color: #8590a6;
    margin-top: 10px;
}

.item-footer span {
    cursor: pointer;
}

.item-footer span:hover {
    color: #406eff;
}
</style> 