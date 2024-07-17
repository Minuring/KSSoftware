import { useState, useEffect, Suspense } from 'react';
import { getTime, getResult } from './weather.js';
import { Card } from 'react-bootstrap';

const st = {
    position : 'fixed',
    display : 'inline-block',
    float : 'left',
    right : '3%',
    top : '10%',
    width: '18rem',
    textAlign : 'center',
    fontSize : 'medium',
    justifyContent : 'center',
    opacity : '0.6'
}

function WeatherWidget() {

    const [data, setData] = useState(null);
    var [today, hours] = getTime();

    useEffect(() => {
        var xhr = new XMLHttpRequest();
        var url = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst'; /*URL*/
        var queryParams = '?' + encodeURIComponent('serviceKey') + '=' + 'xSrbbK33xy8wA8Tcy7oIKWhgyc6qIDBu%2FoaiZaYZoU6sK9Oe2O4ZV3zZrY7bKklHMoJtqrhYLephXjFG1yhP0A%3D%3D'; /*Service Key*/
        queryParams += '&' + encodeURIComponent('pageNo') + '=' + encodeURIComponent('1'); /**/
        queryParams += '&' + encodeURIComponent('numOfRows') + '=' + encodeURIComponent('1000'); /**/
        queryParams += '&' + encodeURIComponent('dataType') + '=' + encodeURIComponent('JSON'); /**/
        queryParams += '&' + encodeURIComponent('base_date') + '=' + encodeURIComponent(today.toString()); /**/
        queryParams += '&' + encodeURIComponent('base_time') + '=' + encodeURIComponent(hours + '00'); /**/
        queryParams += '&' + encodeURIComponent('nx') + '=' + encodeURIComponent('98'); /**/
        queryParams += '&' + encodeURIComponent('ny') + '=' + encodeURIComponent('75'); /**/
        xhr.open('GET', url + queryParams);

        xhr.onreadystatechange = async function () {
            if (this.readyState == 4) {
                let [keys, values] = await getResult(this.responseText);
                setData({k : keys, v : values});
            }
        };

        xhr.send('');
    }, []);

    return (
        <Card border='primary' style={st}>
            <Card.Img src='/img/weather.png' style={{width: '50%', margin: 'auto', display: 'block'}}/>
            <Card.Title>경성대학교 기상 상황</Card.Title>
            <Card.Body>
                {data ? data.k.map((k, idx) => (
                    <Card.Text key={idx}>
                        {k} : {data.v[idx]}
                    </Card.Text>
                )) : 'Loading'}
            </Card.Body>
        </Card>
    );
}

export default WeatherWidget;