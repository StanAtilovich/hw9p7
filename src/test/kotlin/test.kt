
import org.junit.After
import org.junit.Assert
import org.junit.Before

import org.junit.Test


class test{

    @get:Before
    private val userI = User(0, "Я")
    private val userSasha = User(1, "Сашок")
    private val userKatya = User(2, "Катя")
    private val userAnna = User(3, "Аня")

    fun init() {
        Chat.sendMessage(userI, userSasha, "пишу я сашку")
        Chat.sendMessage(userI, userKatya, "пишу кате")
        Chat.sendMessage(userI, userAnna, "пишу анне")
        Chat.sendMessage(userSasha, userI, "сашок написал")
        Chat.sendMessage(userKatya, userI, "катя написала")
        Chat.sendMessage(userAnna, userI, "анна написала")

        
    }

    @After
    fun clearData() {
        Chat.clearAllData()
    }
    @Test
    fun getAllMessagesFromChat() {
        val result = Chat.getAllMessage(1)
        Assert.assertEquals(ArrayList::class, result::class)
    }

    @Test
    fun addMessage() {
        val result = Chat.sendMessage(sendUser = User(0, nameUseer ="Я"), getUser = User(3,"Аня"), "Ваш ответ Анне")
        Assert.assertEquals(true, result)
    }


    @Test(expected = ChatNotFound::class)
    fun deleteNonexistentChat() {
        Chat.deleteChat(73)
    }

    @Test(expected = MessageNotFound::class)
    fun editNonexistentMessage() {
        Chat.editMessage(52, "Ваш ответ кате (редактировали)")
    }

    @Test(expected = MessageNotFound::class)
    fun deleteNonexistentMessage() {
        Chat.deleteMessage(85)
    }
    @Test
    fun unreadChatsCount() {
        val result = Chat.getUnreadChatsCount(userId = 1)
        Assert.assertEquals(Int::class, result::class)
    }


    @Test
    fun getMessagesFromChat() {
        val result = Chat.getAllMessage(1)
        Assert.assertEquals(0, result.size)
    }

    //Попробуем получить 5 сообщений из несуществующего чата
    @Test(expected = ChatNotFound::class)
    fun getMessagesFromNonexistentChat() {
        Chat.getMessagesFromChat(12, 0, 5)
    }
    



}
