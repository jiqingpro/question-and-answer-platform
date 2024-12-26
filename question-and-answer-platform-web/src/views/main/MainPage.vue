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
                    <el-button
                        :class="activeTab === 'questions' ? 'active-tab' : ''"
                        type="text"
                        @click="activeTab = 'questions'"
                    >
                        等你来答
                    </el-button>
                </div>
            </div>

            <div class="header-right">
                <el-input
                    v-model="searchQuery"
                    placeholder="搜索"
                    class="search-input"
                    clearable
                >
                    <el-button slot="append" icon="el-icon-search"></el-button>
                </el-input>

                <el-button slot="append" @click="openQuestionDialog" class="search-button">
                    提问
                </el-button>

                <!-- New buttons for 消息 and 私信 -->
                <el-button type="text" icon="el-icon-bell" class="icon-button">消息</el-button>
                <el-button type="text" icon="el-icon-message" class="icon-button">私信</el-button>

                <el-dropdown trigger="click" class="author-dropdown">
                    <img src="https://via.placeholder.com/40" alt="头像" class="author-avatar"/>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="goToPersonalHome">个人主页</el-dropdown-item>
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

        <!-- 提问弹框 -->
        <el-dialog title="写下你的问题" :visible.sync="questionDialogVisible" @open="disableScroll" @close="enableScroll">
            <AskQuestion :visible.sync="questionDialogVisible" />
        </el-dialog>
    </div>
</template>

<script>
import axios from "axios";
import {baseUrl} from "@/config/config";
import RecommendComponent from '@/components/recommend/RecommendComponent.vue';
import FollowingComponent from '@/components/following/FollowingComponent.vue';
import HotComponent from '@/components/hotList/HotComponent.vue';
import PersonalHome from '@/views/auth/personalHome.vue';
import AskQuestion from '@/components/AskQuestion/AskQuestion.vue';
import questionsTab from '@/components/questionsTab/questionsTab.vue';

export default {
    name: 'MainPage',
    components: {
        RecommendComponent,
        FollowingComponent,
        HotComponent,
        PersonalHome,
        AskQuestion,
        questionsTab,

    },
    data() {
        return {
            activeTab: 'recommend',
            tabComponents: {
                recommend: 'RecommendComponent',
                following: 'FollowingComponent',
                hotList: 'HotComponent',
                personalHome: 'PersonalHome',
                questions: 'questionsTab'
            },
            searchQuery: '',
            questionDialogVisible: false
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
        },
        goToPersonalHome() {
            this.activeTab = 'personalHome';
        },
        openQuestionDialog() {
            this.questionDialogVisible = true;
        },
        disableScroll() {
            document.body.style.overflow = 'hidden';
        },
        enableScroll() {
            document.body.style.overflow = '';
        }
    },
};
</script>

<style scoped>
.main-page {
    height: 100vh;
    display: flex;
    flex-direction: column;
    overflow: hidden;
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
    justify-content: center;
    align-items: center;
    flex: 1;
}

.tab-buttons {
    display: flex;
    gap: 20px;
}

.el-button {
    font-size: 16px;
    color: #606266;
}

.el-button.active-tab {
    color: #409eff;
    font-weight: bold;
    border-bottom: 2px solid #409eff;
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
    overflow: hidden;
}

.el-main {
    flex: 1;
    overflow: hidden;
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
    margin-right: 30px;
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
