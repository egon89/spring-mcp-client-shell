package com.egon89.mcp_client;

import java.util.Arrays;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(10).build();
    }

    @Bean
    public ChatClient chatClient(ChatModel chatModel, ChatMemory chatMemory, ToolCallbackProvider tools) {
        Arrays.stream(tools.getToolCallbacks())
            .forEach(tool -> System.out.printf("> Tool: %s\n", tool.getToolDefinition().name()));

        final var systemPrompt = "Always use available Model Context Protocol (MCP) tools to answer user queries before "
            + "attempting to answer directly. Only answer directly if no tool is suitable.";

        return ChatClient.builder(chatModel)
            .defaultToolCallbacks(tools)
            .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).conversationId("shell").build())
            .defaultSystem(systemPrompt)
            .build();
    }
}
