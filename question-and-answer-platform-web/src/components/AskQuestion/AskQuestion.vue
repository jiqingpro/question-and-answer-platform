<template>
    <div>
        <div class="header">
            <img src="https://via.placeholder.com/40" alt="头像" class="author-avatar"/>
            <input
                v-model="questionTitle"
                placeholder="请输入问题标题"
                maxlength="100"
                class="title-input-field"
            />
        </div>
        <div class="content-input">
            <textarea
                v-model="questionContent"
                placeholder="请输入问题内容 (支持 Markdown)"
                rows="4"
                class="content-input-field"
            ></textarea>
        </div>
        <div class="dialog-footer">
            <el-button @click="closeDialog">取消</el-button>
            <el-button type="primary" @click="submitQuestion">发布问题</el-button>
        </div>
    </div>
</template>

<script>
import { post } from '@/utils/request';

export default {
    name: "AskQuestion",
    props: {
        visible: {
            type: Boolean,
            required: true
        }
    },
    data() {
        return {
            questionTitle: '',
            questionContent: ''
        };
    },
    methods: {
        closeDialog() {
            this.$emit('update:visible', false);
        },
        async submitQuestion() {
            if (this.questionTitle.trim() && this.questionContent.trim()) {
                try {
                    const response = await post('/api/ask/createQuestions', {
                        title: this.questionTitle,
                        description: this.questionContent,
                        userId: localStorage.getItem('userId')
                    });
                    if (response.code === 200) {
                        this.$message.success('问题已发布');
                        this.closeDialog();
                        this.questionTitle = '';
                        this.questionContent = '';
                    } else {
                        this.$message.error(response.msg || '发布问题失败，请稍后重试');
                    }
                } catch (error) {
                    console.error('发布问题失败:', error);
                    this.$message.error('发布问题失败，请稍后重试');
                }
            } else {
                this.$message.warning('请输入问题标题和内容');
            }
        }
    }
}
</script>

<style scoped>
.header {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
    border-bottom: 2px solid #dcdfe6;
    padding-bottom: 10px;
}

.author-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
}

.title-input-field {
    flex: 1;
    border: none;
    font-size: 20px;
    font-weight: bold;
    outline: none;
}

.title-input-field:focus {
    border-bottom-color: #409eff;
}

.content-input-field {
    width: 100%;
    border: none;
    border-radius: 4px;
    background-color: #f5f5f5;
    padding: 10px;
    outline: none;
    resize: vertical;
    font-family: inherit;
}

.content-input-field:focus {
    box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.dialog-footer {
    text-align: right;
    margin-top: 10px;
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}
</style>