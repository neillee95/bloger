package me.lee.bloger.admin.message

import kotlinx.coroutines.reactor.mono
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class MessageController(private val messageService: MessageService) {

    @GetMapping("/messages")
    fun getMessages(@RequestParam("page", required = false, defaultValue = "1") page: Int,
                    @RequestParam("size", required = false, defaultValue = "10") size: Int) =
            mono {
                messageService.getLeaveMessages(page, size)
            }

    @PutMapping("/message/{messageId:\\S+}/show/switch")
    fun switchShowState(@PathVariable messageId: String) =
            mono {
                messageService.switchShowState(messageId)
            }

}