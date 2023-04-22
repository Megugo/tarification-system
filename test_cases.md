<h1 align="center">Тест кейсы для тарификатора</h1>
<h2 >METHOD: PATCH | Endpoint: ../abonent/pay</h2>
<h3>Позитивные</h3>
<ol >
        
<li>Номер длиной 11 цифр тип данных integer
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 11  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "money": 400
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 14 цифр тип данных integer
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 14  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "money": 400
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 11 цифр тип данных string
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 11  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "validTestNumber", 
        "money": 400
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 14 цифр тип данных string
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 14  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "validTestNumber", 
        "money": 400
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

</ol>

<h3>Негативные</h3>
<ol>

<li>Пустуя строка в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber":"", 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Ноль в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": 0, 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>-1 в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": -1, 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>нецелое число в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": randomFloat, 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": null, 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Без поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>


<li>Номер длиной 10 цифр тип данных integer phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 10  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": inValidTestNumber, 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 16 цифр тип данных integer phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 16  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": inValidTestNumber, 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 10 цифр тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 10  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber",  
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 16 цифр тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 16  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber", 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 11 буквенных символов тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 11</em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber", 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 11</em></li>
<li> Отправить JSON

    {
        "phoneNumber": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"', 
        "money": 400
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>


<li>Пустуя строка в значении поля money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber":validTestNumber, 
        "money": ""
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Ноль в значении поля money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber0, 
        "money": 0
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>-1 в значении поля money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "money": -1
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>нецелое число в значении поля money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "money": randomFloat
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null в значении поля money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "money": null
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Без поля money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Буквенная строка money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "money": randomString
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы money
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "money": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"',
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Большой тип данных long long int
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "money": longlontint
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Лишнее поле
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "money": 400,
        "randomString":"randomString"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

</ol>

<h2 >METHOD: GET| Endpoint: ../abonent/report/{numberPhone}</h2>
<h3>Позитивные</h3>

<ol>

<li>Отправить с валидным параметром
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером</em></li>
<li> Отправить на ресурс запрос с валидным номером    

<li><em>Постусловия: -</em></li>
</ul>
</li>

</ol>

<h3>Негативные</h3>

<ol>

<li>Пустое значения
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить запрос без параметра         

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Буквенное значение
<ul>
<li><em>Предусловия: сгенерировать случайную строку</em></li>
<li> Отправить запрос на ресурс с буквенным значением        

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>10 символов номера
<ul>
<li><em>Предусловия: сгенерировать случайное число длиной 10</em></li>
<li> Отправить запрос на ресурс с созданным параметром  

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>16 символов номера
<ul>
<li><em>Предусловия: сгенерировать случайное число длиной 16</em></li>
<li> Отправить запрос на ресурс с созданным параметром  

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы
<ul>
<li><em>Предусловия: сгенерировать случайное значение из (){}[]|`¬¦! £$%^&*<>:;#~_-+=,@" </em></li>
<li> Отправить запрос на ресурс с созданным параметром  

<li><em>Постусловия: -</em></li>
</ul>
</li>

</ol>

<h2 >METHOD: PATCH| Endpoint: ..manager/chaneTariff/</h2>
<h3>Позитивные</h3>

<ol >
        
<li>Номер длиной 11 цифр тип данных integer
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 11  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 14 цифр тип данных integer
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 14  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 11 цифр тип данных string
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 11  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "validTestNumber", 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 14 цифр тип данных string
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 14  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "validTestNumber", 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

</ol>

<h3>Негативные</h3>
<ol>

<li>Пустуя строка в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber":"", 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Ноль в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": 0, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>-1 в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": -1, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>нецелое число в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": randomFloat, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": null, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Без поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>


<li>Номер длиной 10 цифр тип данных integer phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 10  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": inValidTestNumber, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 16 цифр тип данных integer phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 16  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": inValidTestNumber, 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 10 цифр тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 10  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber",  
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 16 цифр тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 16  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber", 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 11 буквенных символов тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 11</em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber", 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 11</em></li>
<li> Отправить JSON

    {
        "phoneNumber": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"', 
        "tariff_id": "randomValidTariff"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>


<li>Пустуя строка в значении поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber":validTestNumber, 
        "tariff_id": ""
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Рандомное число в значении поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": randomInt
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null в значении поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": null
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Без поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Буквенная строка tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": randomString
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"',
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Лишнее поле
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": "randomValidTariff",
        "randomString":"randomString"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

</ol>

<h2 >METHOD: POST| Endpoint: ..manager/abonent/</h2>
<h3>Позитивные</h3>

<ol >
        
<li>Номер длиной 11 цифр тип данных integer
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 11  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 14 цифр тип данных integer
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 14  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 11 цифр тип данных string
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 11  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "validTestNumber", 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

<li>Номер длиной 14 цифр тип данных string
<ul>
<li><em>Предусловия: зарегистрировать нового пользователя с валидным номером длиной 14  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "validTestNumber", 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: удалить ранее зарегистрированного пользователя, если возможно </em></li>
</ul>
</li>

</ol>

<h3>Негативные</h3>
<ol>

<li>Пустуя строка в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber":"", 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Ноль в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": 0, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>-1 в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": -1, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>нецелое число в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": randomFloat, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null в значении поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": null, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Без поля phoneNumber
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Занятый номер
<ul>
<li><em>Предусловия: зарегестрировать пользователя с номером existNumber</em></li>
<li> Отправить JSON

    {
        "phoneNumber": existNumber,
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: удалить пользователя с номером existNumber</em></li>
</ul>
</li>

<li>Номер длиной 10 цифр тип данных integer phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 10  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": inValidTestNumber, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 16 цифр тип данных integer phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 16  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": inValidTestNumber, 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 10 цифр тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 10  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber",  
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 16 цифр тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 16  </em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber", 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Номер длиной 11 буквенных символов тип данных string phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 11</em></li>
<li> Отправить JSON

    {
        "phoneNumber": "inValidTestNumber", 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы phoneNumber
<ul>
<li><em>Предусловия: сгенерировать значение длиной 11</em></li>
<li> Отправить JSON

    {
        "phoneNumber": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"', 
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>


<li>Пустуя строка в значении поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber":validTestNumber, 
        "tariff_id": "",
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Рандомное число в значении поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": randomInt,
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null в значении поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": null,
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Без поля tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Буквенная строка tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": randomString,
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы tariff_id
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"',
        "ballance": randomValidBallance
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Пустуя строка в значении поля ballance
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber":validTestNumber, 
        "tariff_id": randomValidTariff,
        "ballance": ""
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Рандомное число в значении поля ballance
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": randomValidTariff,
        "ballance": randomInt
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null в значении поля ballance
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber, 
        "tariff_id": randomValidTariff,
        "ballance": null
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Без поля ballance
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": randomValidTariff
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Буквенная строка ballance
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": randomValidTariff,
        "ballance": randomString
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы ballance
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": randomValidTariff,
        "ballance": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"'
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Лишнее поле
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "phoneNumber": validTestNumber,
        "tariff_id": "randomValidTariff",
        "ballance": randomValidBallance
        "randomString":"randomString"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

</ol>

<h2 >METHOD: PATCH | Endpoint: ../manager/billing</h2>
<h3>Позитивные</h3>
<ol>

<li>Валидное значение
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "action": "run"
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

</ol>

<h3>Негативные</h3>
<ol>

<li>Пустая строка
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "action": ""
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>null
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "action": null
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Не строку
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "action": randomInt
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Рандомная строка
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "action": randomString
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Спец символы
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "action": '(){}[]|`¬¦! £$%^&*<>:;#~_-+=,@"'
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

<li>Лишнее поле
<ul>
<li><em>Предусловия: -</em></li>
<li> Отправить JSON

    {
        "action": "run",
        randomString:randomString
    }           

<li><em>Постусловия: -</em></li>
</ul>
</li>

</ol>