<template>
    <div class="profile-page">
        <!-- 个人信息头部 -->
        <div class="profile-header">
            <div class="header-content">
                <div class="profile-info">
                    <img src="https://via.placeholder.com/100" alt="Profile Picture" class="profile-picture"/>
                    <div class="info-text">
                        <h2 class="profile-name">久而久</h2>
                        <p class="profile-bio">个人简介：分享知识，连接世界</p>
                        <div class="profile-stats">
                            <span>关注 123</span>
                            <span>粉丝 456</span>
                            <span>获赞 789</span>
                        </div>
                    </div>
                </div>
                <el-button type="primary" class="edit-profile-button">编辑个人资料</el-button>
            </div>
        </div>

        <!-- 主要内容区域 -->
        <div class="profile-content">
            <div class="content-main">
                <el-tabs v-model="activeTab" @tab-click="handleTabClick">
                    <el-tab-pane label="动态" name="activity">
                        <activity-component v-if="activeTab === 'activity'"/>
                    </el-tab-pane>
                    <el-tab-pane label="回答" name="answers">
                        <answers-component v-if="activeTab === 'answers'"/>
                    </el-tab-pane>
                    <el-tab-pane label="视频" name="videos">
                        <videos-component v-if="activeTab === 'videos'"/>
                    </el-tab-pane>
                    <el-tab-pane label="提问" name="questions">
                        <questions-component v-if="activeTab === 'questions'"/>
                    </el-tab-pane>
                    <el-tab-pane label="文章" name="articles">
                        <articles-component v-if="activeTab === 'articles'"/>
                    </el-tab-pane>
                    <el-tab-pane label="收藏" name="collections">
                        <collections-component v-if="activeTab === 'collections'"/>
                    </el-tab-pane>
                </el-tabs>
            </div>

            <!-- 右侧边栏 -->
            <div class="content-sidebar">
                <el-card class="creation-center">
                    <div slot="header">
                        <span>创作中心</span>
                    </div>
                    <div class="creation-stats">
                        <div class="stat-item">
                            <div class="stat-number">0</div>
                            <div class="stat-label">创作总数</div>
                        </div>
                        <div class="stat-item">
                            <div class="stat-number">0</div>
                            <div class="stat-label">总浏览</div>
                        </div>
                    </div>
                    <el-button type="primary" class="create-button" @click="startCreation">开始创作</el-button>
                </el-card>

                <el-card class="achievement-card">
                    <div slot="header">
                        <span>成就与认证</span>
                    </div>
                    <div class="achievements">
                        <div class="achievement-item">
                            <i class="el-icon-medal"></i>
                            <span>优质创作者</span>
                        </div>
                    </div>
                </el-card>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'personalHome',
    data() {
        return {
            activeTab: 'activity'
        };
    },
    methods: {
        handleTabClick(tab) {
            this.activeTab = tab.name;
        },
        startCreation() {
            // 处理创作按钮点击
            this.$message.success('即将开始创作...');
        }
    }
};
</script>

<style scoped>
.profile-page {
    background-color: #f6f6f6;
    min-height: 100vh;
}

.profile-header {
    background-color: #fff;
    padding: 24px 0;
    border-bottom: 1px solid #e8e8e8;
}

.header-content {
    max-width: 1000px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px;
}

.profile-info {
    display: flex;
    align-items: center;
    gap: 24px;
}

.profile-picture {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    object-fit: cover;
}

.info-text {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.profile-name {
    font-size: 26px;
    margin: 0;
    color: #121212;
}

.profile-bio {
    color: #666;
    margin: 4px 0;
}

.profile-stats {
    display: flex;
    gap: 20px;
    color: #666;
}

.profile-content {
    max-width: 1000px;
    margin: 20px auto;
    padding: 0 20px;
    display: flex;
    gap: 20px;
}

.content-main {
    flex: 1;
    background: #fff;
    border-radius: 4px;
    padding: 20px;
}

.content-sidebar {
    width: 300px;
}

.creation-center {
    margin-bottom: 20px;
}

.creation-stats {
    display: flex;
    justify-content: space-around;
    margin: 16px 0;
}

.stat-item {
    text-align: center;
}

.stat-number {
    font-size: 24px;
    color: #121212;
    font-weight: 500;
}

.stat-label {
    font-size: 14px;
    color: #666;
    margin-top: 4px;
}

.create-button {
    width: 100%;
}

.achievement-card {
    margin-top: 16px;
}

.achievements {
    padding: 8px 0;
}

.achievement-item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 8px 0;
    color: #666;
}

.el-tabs ::v-deep .el-tabs__item {
    font-size: 16px;
    padding: 0 20px;
}

.el-tabs ::v-deep .el-tabs__item.is-active {
    color: #121212;
    font-weight: 500;
}

.el-tabs ::v-deep .el-tabs__active-bar {
    background-color: #121212;
}
</style>