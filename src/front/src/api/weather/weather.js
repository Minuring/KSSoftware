const codeMapping = {
    'baseDate': '날짜',
    'baseTime': '시간',
    'SKY': '날씨',
    'PTY': '강수형태',
    'REH': '습도',
    'T1H': '기온',
    'WSD': '풍속'
};

const PTY = {
    '0' : '비 안옴',
    '1' : '비',
    '2' : '비/눈',
    '3' : '눈',
    '5' : '빗방울',
    '6' : '빗방울눈날림',
    '7' : '눈날림'
};

export function getResult(responseText){
    let res = JSON.parse(responseText);
    let item = res.response.body.items.item;
    let keys = [];
    let values = [];

    let v = item[0].baseDate;
    v = v.substr(4,);
    v = ''.concat(v.substr(0,2), '월', v.substr(2,),'일');
    keys.push('날짜'); values.push(v);

    v = item[0].baseTime;
    v = v.substr(0, 2) + "시" + v.substr(2) + "분";
    keys.push('시간'); values.push(v);

    for (let i = 0; i < item.length; i++) {
        if (item[i].category in codeMapping) {
            let k = codeMapping[item[i].category];
            v = item[i].obsrValue;
            if(k== '강수형태') v = PTY[v];
            else if(k=='습도') v = v + '%';
            else if(k=='기온') v = v + '°C';
            else if(k=='풍속') v = v + 'm/s';

            keys.push(k); values.push(v);
        }

    }

    return [keys, values];
}

export function getTime() {
    var today = new Date();
    var week = new Array('일', '월', '화', '수', '목', '금', '토');
    var year = today.getFullYear();
    var month = today.getMonth() + 1;
    var day = today.getDate();
    var hours = today.getHours();
    var minutes = today.getMinutes();

    /*
    * 기상청 30분마다 발표
    * 30분보다 작으면, 한시간 전 hours 값
    */
    if (minutes < 30) {
        hours = hours - 1;
        if (hours < 0) {
            // 자정 이전은 전날로 계산
            today.setDate(today.getDate() - 1);
            day = today.getDate();
            month = today.getMonth() + 1;
            year = today.getFullYear();
            hours = 23;
        }
    }

    /* example
     * 9시 -> 09시 변경 필요
     */

    if (hours < 10) {
        hours = '0' + hours;
    }
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }

    today = year + "" + month + "" + day;

    return [today, hours];
}