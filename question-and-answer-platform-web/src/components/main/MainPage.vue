<template>
    <div class="main-page">
        <!-- 顶部导航栏 -->
        <el-header class="main-header">
            <div class="header-left">
                <div class="tab-buttons">
                    <el-button
                        :class="activeTab === 'recommend' ? 'active-tab' : ''"
                        type="text"
                        @click="activeTab = 'recommend'"
                    >
                        推荐
                    </el-button>
                    <el-button
                        :class="activeTab === 'following' ? 'active-tab' : ''"
                        type="text"
                        @click="activeTab = 'following'"
                    >
                        关注
                    </el-button>
                </div>
            </div>

            <div class="header-right">
                <el-button @click="goToMessages" type="text">消息</el-button>
                <el-button @click="goToPrivateMessages" type="text">私信</el-button>
                <el-dropdown trigger="hover" class="author-dropdown">
                      <span class="el-dropdown-link">
                        作者 <i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>个人主页</el-dropdown-item>
                        <el-dropdown-item @click.native ="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </el-header>

        <!-- 主内容区 -->
        <el-container>
            <el-main>
                <div v-if="activeTab === 'recommend'" class="content">推荐内容</div>
                <div v-if="activeTab === 'following'" class="content">关注内容</div>
            </el-main>

            <!-- 侧边栏 -->
            <!--            <el-aside width="300px" class="right-aside">
                            <div class="aside-section">
                                <h3>推荐作者</h3>
                                <div v-for="author in recommendedAuthors" :key="author.id" class="author-item">
                                    <img :src="author.avatar" alt="头像" class="author-avatar" />
                                    <div>
                                        <p class="author-name">{{ author.name }}</p>
                                        <p class="author-description">{{ author.description }}</p>
                                    </div>
                                </div>
                            </div>
                        </el-aside>-->
        </el-container>
    </div>
</template>

<script>
import axios from "axios";
import {baseUrl} from "@/config";

export default {
    data() {
        return {
            activeTab: 'recommend', // 当前选中的 Tab
            recommendedAuthors: [
                {id: 1, name: '作者A', avatar: 'https://via.placeholder.com/40', description: '关注领域：科技'},
                {id: 2, name: '作者B', avatar: 'https://via.placeholder.com/40', description: '关注领域：文学'},
                {id: 3, name: '作者C', avatar: 'https://via.placeholder.com/40', description: '关注领域：电影'},
            ],
        };
    },
    methods: {
        goToMessages() {
            this.$notify({type: 'info', title: '跳转到消息页面'});
        },
        goToPrivateMessages() {
            this.$notify({type: 'info', title: '跳转到私信页面'});
        },
        async logout() {
            try {
                // 调用后端退出登录接口
                await axios.post(baseUrl + '/api/users/logout')
                // 清理本地存储
                localStorage.removeItem('authToken');
                localStorage.removeItem('currentPathName');

                // 提示用户退出成功
                this.$notify({type: 'success', title: '退出成功', message: '您已成功退出登录', duration: 1000,});

                // 跳转到登录页面
                await this.$router.push('/LoginPage');
            } catch (error) {
                // 错误处理
                console.error('退出登录失败:', error);
                this.$notify({type: 'error', title: '退出失败', message: '无法退出登录，请稍后重试', duration: 2000,});
            }
        },
    },
};
</script>

<style scoped>
.main-page {
    height: 100vh;
    display: flex;
    flex-direction: column;
}

.main-header {
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    padding: 0 20px;
    border-bottom: 1px solid #ebeef5;
}

.header-left {
    display: flex;
    justify-content: center; /* 将内容居中 */
    align-items: center;
    flex: 1; /* 占据剩余空间 */
}

.tab-buttons {
    display: flex;
    gap: 20px; /* 按钮之间的间距 */
}

.el-button {
    font-size: 16px;
    color: #606266; /* 默认字体颜色 */
}

.el-button.active-tab {
    color: #409eff; /* 激活状态颜色 */
    font-weight: bold;
    border-bottom: 2px solid #409eff; /* 激活状态的下划线 */
    border-radius: 0;
}


.header-right {
    display: flex;
    align-items: center;
}

.header-right > * {
    margin-left: 20px;
}

.content {
    padding: 20px;
    background-color: #f2f2f2;
    min-height: 500px;
}

.right-aside {
    background-color: #f9f9f9;
    padding: 10px;
}

.aside-section {
    margin-bottom: 20px;
}

.author-item {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
}

.author-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
}

.author-name {
    font-weight: bold;
}

.author-description {
    font-size: 0.9em;
    color: #666;
}
</style>
