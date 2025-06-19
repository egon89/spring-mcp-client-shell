package com.egon89.mcp_client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ChatCommand {

    private final ChatClient chatClient;

    public ChatCommand(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    
    @ShellMethod(key = "chat", value = "Send a chat message")
    public String chat(@ShellOption(defaultValue = "Hello MCP client!") String message) {
        return this.chatClient.prompt(message).call().content();
    }
}
