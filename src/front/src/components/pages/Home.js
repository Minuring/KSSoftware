import React, {useEffect, useState} from 'react';
import PageCard from '../PageCard';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import WeatherWidget from '../../api/weather/WeatherCard';

const containerStyle = {
    //컨테이너
    marginTop: '2%',
    marginBottom: '5%',
    backgroundColor: 'dark',
    padding: '10px 10px 10px 10px',
    // border: '1px solid red',

    //카드들
    display: 'grid',
    justifyContent: 'center',
    gap: '3% 5%',
};

const Home = () => {

    // const [data, setData] = useState([]);
    //
    // useEffect(() => {
    //     fetch("/showMe2")
    //         .then((res) => {
    //             return res.json();
    //         })
    //         .then(function (result) {
    //             setData(result);
    //         })
    // },[]);

    return (
        <div>
            <Container style={containerStyle}>
                <Row class="row row-cols-2 row-cols-md-4 g-2">
                    <Col>
                        <PageCard title="게시판" text="1학년" src='/img/home/intro_coding.png' link="/Board/FreshmanBoard" />
                    </Col>
                    <Col>
                        <PageCard title="게시판" text="2학년" src='/img/home/intro_admission.png' link="/Board/SophomoreBoard" />
                    </Col>
                    <Col>
                        <PageCard title="게시판" text="3학년" src='/img/home/intro_curriculum.png' link="/Board/JuniorBoard" />
                    </Col>
                    <Col>
                        <PageCard title="게시판" text="4학년" src='/img/home/intro_curriculum.png' link="/Board/SeniorBoard" />
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <PageCard title="기타유틸" text="학점계산기" src='/img/home/intro_career.png' link="/Etc/CreditCalculator" />
                    </Col>
                    <Col>
                        <PageCard title="기타유틸" text="스터디" src='/img/home/intro_activity.png' link="/Etc/Study" />
                    </Col>
                    <Col>
                        <PageCard title="기타유틸" text="중고거래" src='/img/home/intro_community.png' link="/Etc/UsedTrade" />
                    </Col>
                </Row>
                {/*<ul>*/}
                {/*    {data.map((v,idx)=><li key={`${idx}-${v}`}>{v}</li>)}*/}
                {/*</ul>*/}
            </Container>
            {/*<WeatherWidget />*/}
        </div>
    );
}

export default Home;
