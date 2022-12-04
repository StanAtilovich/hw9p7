data class Chat(
    val id: Int,//3 создали чат и его параметры
    val memberUserId: ChatPair
) {
    companion object {
        //8 создали обьект в нем будут все функции
        var chatList = mutableListOf<Chat>()// 11 создали лист в котором будут сохраняться чат
        var messageList = mutableListOf<Message>()  //14 создаем лист смс

        fun createChat(sendUser: User, getUser: User): Boolean { //12 создаем функцию создания чата
            val chaId = if (chatList.isEmpty()) 0 else chatList.last().id + 1
            return chatList.add(Chat(chaId, sendUser x getUser))
        }


        //9 первая функция отправка смс
        fun sendMessage(sendUser: User, getUser: User, text: String): Boolean {
            var filterChatList = getChatList(sendUser, getUser)
            if (filterChatList.isEmpty()) {
                createChat(sendUser, getUser)
                filterChatList = getChatList(sendUser, getUser)
            }
            val curentChat = filterChatList[0]

            val messageId = if (messageList.isEmpty()) 0 else messageList.last().id + 1
            val messageSend = messageList.add(Message(messageId, curentChat.id, sendUser.nameUseer, getUser.id, text))
            readMessage(curentChat.id, sendUser.id)
            return messageSend
        }

        private fun readMessage(id: Int, id1: Int) {//13 пишем функцию прочитки смс
            messageList.filter { it.chatId == id && !it.read && it.id == id }
                .forEach { it.read = true }
        }

        //10 функция получения отправителя и получателя
        fun getChatList(sendUser: User, getUser: User) =
//11 функция получения отправителя и получателя сохраниться в лис
            chatList.filter { it.memberUserId == sendUser x getUser || it.memberUserId == getUser x sendUser }

        //17 функция получения всех юзеров
        fun getChatUser(user: User) = chatList.filter { it.memberUserId.user1 == user || it.memberUserId.user2 == user }

        //18 создаем функцию получения всех смс
        fun getAllMessage(chatId: Int) = messageList.filter { it.chatId == chatId }

        //15 функция получения юзерв из чата
//17 удаление чата
        fun deleteChat(chatId: Int): Boolean {
            val filterChatList = chatList.filter { it.id == chatId }.toList()
            if (filterChatList.isEmpty()) throw ChatNotFound("чата нет")
            val filterMessageList = messageList.filter { it.chatId == chatId }.toList()
            filterChatList.forEach { chatList.remove(it) }
            return true
        }

        //20 функция удаления смс
        fun deleteMessage(message: Int): Boolean {
            val filterSmsList = messageList.filter { it.id == message }
            if (filterSmsList.isEmpty()) throw MessageNotFound("сообщение не найдено")
            val msg = filterSmsList[0]
            val filterMessageChat = messageList.filter { it.chatId == msg.chatId }
            //удалим все у хуям если нет ничего
            if (filterMessageChat.size == 1) {
                val filterChatList = chatList.filter { it.id == msg.chatId }
                println(" было последнее смс в чате удаляем чат ${filterChatList[1]} удаляем чат")
                deleteChat(msg.chatId)
                return true
            }
            println("смс удалили")
            return messageList.remove(msg)
        }

        fun editMessage(messageId: Int, textRed: String): Message {
            val filterMessageList = messageList.filter { it.id == messageId }

            if (filterMessageList.isEmpty()) throw MessageNotFound("сообщение не найдено")
            var msg = filterMessageList[0].copy(text = textRed)
            messageList[messageList.indexOf(filterMessageList[0])] = msg
            return messageList[messageList.indexOf(msg)]
        }

        fun getUnreadChatsCount(userId: Int): Int {
            var chatsIdList = listOf<Int>()
            println("\nНепрочитанные сообщения в чатах:")
            messageList.filter { it.getUser == userId && !it.read }.forEach {
                println("- ${it.text} [Не прочитано] (chatId: ${it.chatId})")
                if (chatsIdList.indexOf(it.chatId) == -1) chatsIdList += it.chatId
            }
            return chatsIdList.count()
        }

        fun getMessagesFromChat(chatId: Int, lastMessageId: Int, count: Int): List<Message> {
            if (chatList.filter { it.id == chatId }.isEmpty()) throw ChatNotFound("Чат не найден!")
            val filterMessagesList = messageList.filter { it.chatId == chatId }
            val totalCount = if (count < 0) filterMessagesList.size else count
            val countRec =
                if (filterMessagesList.size >= lastMessageId + totalCount) lastMessageId + totalCount else filterMessagesList.size
            return filterMessagesList.subList(lastMessageId, countRec)
        }

        fun clearAllData(){
            chatList.clear()
            messageList.clear()
        }
    }





}

class MessageNotFound(message: String) : RuntimeException(message)

class ChatNotFound(message: String) : RuntimeException(message)


//4 создали параметр пары
data class ChatPair(val user1: User, val user2: User)

//5 пара разделяеться инфиксом
infix fun User.x(that: User) = ChatPair(this, that)

//16 создание функции распечатки
fun printChatListMessages(user: User) {
    val allChatList = Chat.getChatUser(user)
    println("чат пользователя ${user.nameUseer}")
    allChatList.forEach {
        Chat.getAllMessage(it.id)
            .forEach { message -> println("${message.text} ${if (message.read) "смс прочитано" else "смс не прочитано"}") }
    }
}
