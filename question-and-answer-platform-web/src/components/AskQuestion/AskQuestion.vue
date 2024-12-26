<template>
    <div>
        <el-input
            v-model="questionTitle"
            placeholder="请输入问题标题"
            maxlength="100"
            show-word-limit
            class="title-input"
        ></el-input>
        <el-input
            type="textarea"
            v-model="questionContent"
            placeholder="请输入问题内容"
            rows="4"
            class="content-input"
        ></el-input>
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
.title-input {
    margin-bottom: 20px;
}

.title-input .el-input__inner {
    border: none;
    border-bottom: 2px solid #dcdfe6;
    font-size: 20px;
    font-weight: bold;
    padding: 10px 0;
}

.title-input .el-input__inner:focus {
    border-bottom-color: #409eff;
    box-shadow: none;
}

.content-input .el-input__inner {
    border: none;
    border-radius: 4px;
    background-color: #f5f5f5;
    padding: 10px;
}

.content-input .el-input__inner:focus {
    border: none;
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