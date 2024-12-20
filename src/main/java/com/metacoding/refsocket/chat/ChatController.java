package com.metacoding.refsocket.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;
    private final SimpMessageSendingOperations sms;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("models",chatService.findAll());
        return "index";
    }

    @GetMapping("/save-form")
    public String saveForm(){
        return "save-form";
    }

    @GetMapping("/api")
    public ResponseEntity<?> api() {
        List<Chat> models = chatService.findAll();
        return ResponseEntity.ok(models);
    }
    
    // 작성한 채팅 뿌리기 
    @PostMapping("/chat")
    public String save(String msg) {
        Chat chat = chatService.save(msg);
        // 객체로 던져도 json으로 날아감
        sms.convertAndSend("/sub/chat", chat);
        return "redirect:/";
    }

    // /pub/room >> 설정파일에 prefix 설정해둬서 /pub 생략가능
//    @MessageMapping("/room")
//    public void pubTest1(String number) {
//        System.out.println("요청됨" + number);
//        sms.convertAndSend("/sub/"+number,"Hello World!" + number);
//    }

//    @SendTo("/sub")
//    @MessageMapping("/room")
//    public String pubTest2(String number) {
//        System.out.println("요청됨" + number);
//        return "Hello World!";
//    }
}
