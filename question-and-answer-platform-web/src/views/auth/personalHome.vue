<template>
    <div class="profile-page">
        <el-container>
            <el-aside width="300px" class="profile-sidebar">
                <img src="https://via.placeholder.com/150" alt="Profile Picture" class="profile-picture"/>
                <h2 class="profile-name">久而久</h2>
                <el-button type="primary" class="edit-profile-button">编辑个人资料</el-button>
            </el-aside>

            <el-main class="profile-main">
                <el-tabs>
                    <el-tab-pane label="动态">
                        <div class="activity">
                            <h3>我的动态</h3>
                            <p>你做过什么最疯狂的事?</p>
                            <p>...</p>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="回答"></el-tab-pane>
                    <el-tab-pane label="视频"></el-tab-pane>
                    <el-tab-pane label="提问"></el-tab-pane>
                    <el-tab-pane label="文章"></el-tab-pane>
                    <el-tab-pane label="专栏"></el-tab-pane>
                    <el-tab-pane label="收藏"></el-tab-pane>
                    <el-tab-pane label="关注"></el-tab-pane>
                </el-tabs>
            </el-main>

            <el-aside width="300px" class="profile-right-sidebar">
                <el-card>
                    <h3>创作中心</h3>
                    <el-button type="primary">开始创作</el-button>
                </el-card>
            </el-aside>
        </el-container>
    </div>
</template>

<script>
export default {
    name: 'personalHome',
    data() {
        return {
        };
    },
    methods: {
    }
};

</script>

<style scoped>
.profile-page {
    display: flex;
    background-color: #f5f5f5;
    padding: 20px;
}

.profile-sidebar {
    background-color: #fff;
    padding: 20px;
    text-align: center;
}

.profile-picture {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    margin-bottom: 10px;
}

.profile-name {
    font-size: 24px;
    margin-bottom: 10px;
}

.edit-profile-button {
    margin-top: 10px;
}

.profile-main {
    flex: 1;
    padding: 20px;
    background-color: #fff;
}

.activity {
    margin-bottom: 20px;
}

.profile-right-sidebar {
    background-color: #fff;
    padding: 20px;
}
</style>