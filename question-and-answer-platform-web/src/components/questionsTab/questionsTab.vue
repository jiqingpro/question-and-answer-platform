<template>
    <div class="questions-tab">
        <div class="tab-buttons">
            <button
                v-for="tab in tabs"
                :key="tab.name"
                :class="{ active: activeTab === tab.name }"
                @click="changeTab(tab.name)"
            >
                {{ tab.label }}
            </button>
        </div>
        <div class="questions-list">
            <div v-for="question in questions" :key="question.id" class="question-item">
                <div class="question-header">
                    <span class="question-tag">新问</span>
                    <span class="question-title">{{ question.title }}</span>
                </div>
                <div class="question-stats">
                    <span>{{ question.views }} 浏览</span>
                    <span>{{ question.answers }} 回答</span>
                    <span>{{ question.followers }} 关注</span>
                    <span>{{ question.time }} 提问</span>
                </div>
                <button class="answer-button">写回答</button>
            </div>
        </div>
    </div>
</template>

<script>
import { get } from '@/utils/request';

export default {
    name: "questionsTab",
    data() {
        return {
            activeTab: 'recommend',
            tabs: [
                { name: 'recommend', label: '为你推荐' },
                { name: 'invite', label: '邀请回答' },
                { name: 'latest', label: '最新问题' },
                { name: 'popular', label: '人气问题' }
            ],
            questions: []
        };
    },
    methods: {
        async changeTab(tabName) {
            this.activeTab = tabName;
            if (tabName === 'latest') {
                await this.fetchNewQuestions();
            }
        },
        async fetchNewQuestions() {
            try {
                const response = await get('/api/ask/getNewQuestions');
                if (response.code === 200) {
                    this.questions = response.data;
                } else {
                    this.$message.error(response.msg || '获取问题失败');
                }
            } catch (error) {
                console.error('获取问题失败:', error);
                this.$message.error('获取问题失败，请稍后重试');
            }
        }
    }
}
</script>

<style scoped>
.questions-tab {
    padding: 20px;
    width: 50%;
    margin: 0 auto;
}

.tab-buttons {
    display: flex;
    gap: 20px;
    margin-bottom: 20px;
}

.tab-buttons button {
    background: none;
    border: none;
    font-size: 16px;
    cursor: pointer;
    padding: 10px 20px;
}

.tab-buttons .active {
    color: #409eff;
    font-weight: bold;
    border-bottom: 2px solid #409eff;
}

.questions-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.question-item {
    padding: 15px;
    border: 1px solid #ebeef5;
    border-radius: 4px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.question-header {
    display: flex;
    align-items: center;
    gap: 10px;
}

.question-tag {
    background-color: #f56c6c;
    color: white;
    padding: 2px 6px;
    border-radius: 3px;
    font-size: 12px;
}

.question-title {
    font-weight: bold;
}

.question-stats {
    color: #909399;
    font-size: 14px;
}

.answer-button {
    background-color: #409eff;
    color: white;
    border: none;
    padding: 5px 10px;
    border-radius: 4px;
    cursor: pointer;
}

.answer-button:hover {
    background-color: #66b1ff;
}
</style>