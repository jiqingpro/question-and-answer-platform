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
                    <el-button
                        :class="activeTab === 'hotList' ? 'active-tab' : ''"
                        type="text"
                        @click="activeTab = 'hotList'"
                    >
                        热榜
                    </el-button>
                </div>
            </div>

            <div class="header-right">
                <el-input
                    v-model="searchQuery"
                    placeholder="搜索或提问"
                    class="search-input"
                    clearable
                >
                    <el-button slot="append" @click="handleSearch" class="search-button">
                        搜索
                    </el-button>
                </el-input>
                
                <!-- New buttons for 消息 and 私信 -->
                <el-button type="text" icon="el-icon-bell" class="icon-button">消息</el-button>
                <el-button type="text" icon="el-icon-message" class="icon-button">私信</el-button>
                
                <el-dropdown trigger="click" class="author-dropdown">
                    <img src="https://via.placeholder.com/40" alt="头像" class="author-avatar" />
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>个人主页</el-dropdown-item>
                        <el-dropdown-item>设置</el-dropdown-item>
                        <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </el-header>

        <!-- 主内容区 -->
        <el-container>
            <el-main>
                <component :is="currentComponent"></component>
            </el-main>

        </el-container>
    </div>
</template>

<script>
import axios from "axios";
import {baseUrl} from "@/config/config";
import RecommendComponent from '@/components/recommend/RecommendComponent.vue';
import FollowingComponent from '@/components/following/FollowingComponent.vue';
import HotComponent from '@/components/hotList/HotComponent.vue';

export default {
    name: 'MainPage',
    data() {
        return {
            activeTab: 'recommend',
            tabComponents: {
                recommend: 'RecommendComponent',
                following: 'FollowingComponent',
                hotList: 'HotComponent'
            },
            searchQuery: '',
        };
    },
    computed: {
        currentComponent() {
            return this.tabComponents[this.activeTab];
        }
    },
    methods: {
        async logout() {
            try {
                // 调用后端退出登录接口
                await axios.post(baseUrl + '/api/users/logout')
                // 清理本地存储
                localStorage.removeItem('authToken');
                localStorage.removeItem('currentPathName');

                // 提示用户退出成功
                this.$message({
                    message: '您已退出登录',
                    type: 'success'
                });

                // 跳转到登录页面
                await this.$router.push('/LoginPage');
            } catch (error) {
                // 错误处理
                console.error('退出登录失败:', error);
                this.$notify({type: 'error', title: '退出失败', message: '无法退出登录，请稍后重试', duration: 2000,});
            }
        },
        handleSearch() {
            if (this.searchQuery.trim()) {
                // Implement search logic here
                console.log('Searching for:', this.searchQuery);
                // You can route to a search results page or perform an API call
            }
        }
    },
    components: {
        RecommendComponent,
        FollowingComponent,
        HotComponent
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
    border-bottom: 2px solid #409eff; /* 激活状态的下线 */
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

.search-bar {
    display: flex;
    align-items: center;
}

.search-input {
    width: 300px;
    margin-right: 10px;
}

.search-button {
    background-color: #409eff;
    color: white;
    border: none;
    cursor: pointer;
}

.search-button:hover {
    background-color: #66b1ff;
}

.icon-button {
    margin-right: 10px;
    color: #606266;
}

.icon-button:hover {
    color: #409eff;
}
</style>
