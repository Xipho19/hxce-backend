package com.example.hxce.api.service;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.hxce.api.dao.ExerciseDao;
import com.example.hxce.api.pojo.ExerciseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.hxce.api.data.QuizResult;
import io.lettuce.core.json.JsonObject;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service

public class ExerciseServiceImpl implements ExerciseService {
    @Resource
    private ExerciseDao exerciseDao;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    private  ChatClient chatClient;

    public ExerciseServiceImpl(ChatClient.Builder chatClientBuilder){
        this.chatClient=chatClientBuilder.build();
    }
    @Override
    public QuizResult create(Map<String, Object> params) {
        String difficulty = (String) params.get("difficulty");
        Integer questionNum=(Integer) params.get("questionNum");
        String knowledge = (String) params.get("knowledge");
        String uuid=(String) params.get("uuid");
        //告诉deepseek注意事项
        String systemPrompt = """
                你是一位专业的出题专家。
                请根据用户提供的知识点、难度和数量生成选择题。
                
                ⚠️ 重要约束：
                1. 必须且只能返回标准的 JSON 格式数据。
                2. 不要包含 markdown 标记 (如 ```json ... ```)，直接返回纯文本 JSON。
                3. JSON 结构必须严格符合以下格式：
                   {
                     "knowledge": "知识点名称",
                     "questions": [
                       {
                         "question": "题目描述",
                         "options": [{"key": "A", "value": "..."}, {"key": "B", "value": "..."}],
                         "answer": "A",
                         "explanation": "解析内容"
                       }
                     ]
                   }
                """;

        String userPrompt =String.format("知识点：%s,难度：%s，数量：%d道",knowledge,difficulty,questionNum);
        //调用deepseek
        ChatResponse response = chatClient.prompt().system(systemPrompt).user(userPrompt).call().chatResponse();
        //获取响应的纯文本内容
        String text=response.getResult().getOutput().getText();
        //把纯文本JSON格式的数据转换为Record对象
        QuizResult result=null;
        try {
            result=objectMapper.readValue(text,QuizResult.class);
            System.out.println(text);
            //把练习题缓存道Redis中
            redisTemplate.opsForValue().set(uuid,result,120, TimeUnit.MINUTES);
            //把练习题保存到数据表中
            params.put("exercise",objectMapper.writeValueAsString(result));
            ExerciseEntity entity= BeanUtil.toBean(params,ExerciseEntity.class);
            int rows=exerciseDao.insert(entity);
            if(rows==1){
                return result;
            }
            else{
                throw new RuntimeException("练习题插入失败");
            }


        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("JSON格式解析失败");

        }

    }

    @Override
    public JSONObject searchByUuid(String uuid) {
        //判断Redis缓存中是否存在
        if(redisTemplate.hasKey(uuid)){
            QuizResult quizResult =(QuizResult) redisTemplate.opsForValue().get(uuid);
            try{
                //把封装的数据转换为JSON字符串
                String jsonStr=objectMapper.writeValueAsString(quizResult);
                JSONObject jsonObj= JSONUtil.parseObj(jsonStr);
                return jsonObj;
            }catch(Exception e){
                throw new RuntimeException("JSON格式转换失败",e);
            }
        }
        else{
            String exercise=exerciseDao.searchByUuid(uuid);
            JSONObject jsonObj=new JSONObject(exercise);
            return jsonObj;
        }
    }
}

