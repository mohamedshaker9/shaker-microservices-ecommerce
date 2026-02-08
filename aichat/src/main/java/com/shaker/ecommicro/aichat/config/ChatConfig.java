package com.shaker.ecommicro.aichat.config;



import com.shaker.ecommicro.aichat.advisors.TokenUsageAuditorAdvisor;
import com.shaker.ecommicro.aichat.tools.AiChatTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.util.List;

@Configuration
public class ChatConfig {


    @Value("classpath:promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    @Bean
    RestClientCustomizer openAiBufferOnly() {
        return builder -> builder.requestFactory(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
        );
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 ChatMemory chatMemory,
                                 AiChatTools aiChatTools,
                                 RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
            Advisor loggerAdvisor = new SimpleLoggerAdvisor();
            Advisor tokenAuditorAdvisor = new TokenUsageAuditorAdvisor();
            Advisor chatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
           return  builder
                    .defaultAdvisors(List.of(loggerAdvisor, tokenAuditorAdvisor, chatMemoryAdvisor,
                            retrievalAugmentationAdvisor))
                    .defaultSystem(systemPromptTemplate)
//                   .defaultTools(aiChatTools)
                   .build();
    }


    @Bean
    public RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(
                        VectorStoreDocumentRetriever
                                .builder()
                                .topK(3)
                                .similarityThreshold(0.7)
                                .vectorStore(vectorStore)
                                .build()
                )
                .build();
    }


}
