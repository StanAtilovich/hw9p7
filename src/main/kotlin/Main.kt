fun main() {
    //2 создекм юзера
    val userI = User(0, "Я")
    val userSasha = User(1, "Сашок")
    val userKatya = User(2, "Катя")
    val userAnna = User(3, "Аня")


    //7 отправляем смс
    Chat.sendMessage(userI, userSasha, "сообщение сашку от меня")
    Chat.sendMessage(userI, userKatya, "сообщение кате от меня")
    Chat.sendMessage(userI, userAnna, " сообщения анне от меня")
    Chat.sendMessage(userSasha, userI, "ответ сашки")
    Chat.sendMessage(userKatya, userI, "ответ кати")
    Chat.sendMessage(userAnna, userI, "ответ ани")
    Chat.sendMessage(userSasha, userAnna, "переписка сашка и ани")

    println("Количество непрочитанных чатов: ${Chat.getUnreadChatsCount(userI.id)}")


    //15 распечатка всех смс
    printChatListMessages(userI)
    println("\n")
    printChatListMessages(userAnna)

    //18 удаление чата
    println("удаление чата Аннна")
    Chat.deleteChat(2)
    Chat.getChatUser(userAnna).forEach { println(it) }

    println("\nУдаление чата с Id = 3")
    Chat.deleteChat(3)


    //19 удаление смс
    println("Удалим смс")
   Chat.deleteMessage(2)
    printChatListMessages(userKatya)

//22 редактирование смс
    println("редактируем существующее сообщение")
    Chat.editMessage(1, "Ваш ответ сашку (редактировали)")
    printChatListMessages(userI)
}



